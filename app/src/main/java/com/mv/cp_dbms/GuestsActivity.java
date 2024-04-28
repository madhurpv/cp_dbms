package com.mv.cp_dbms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class GuestsActivity extends AppCompatActivity {




    public static List<GuestsClass> guestsAll = new ArrayList<>();
    public static List<GuestsClass> guestsNew = new ArrayList<>();
    public static List<GuestsClass> displayGuests = new ArrayList<>();
    public static GuestsRecyclerAdapter adapter;

    RecyclerView guestsRecyclerView;
    Button addGuestButton;
    Switch showPastGuestsSwitch;


    SharedPreferences sharedPreferences;


    // creating a variable for our
    // Firebase Database.
    FirebaseDatabase firebaseDatabase;

    // creating a variable for our Database
    // Reference for Firebase.
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guests);


        /*guestsAll.add(new GuestsClass("Mr Kumar", 1710150730419L, 1710160730419L, 2, GuestsClass.PARKING_REQUIRED, GuestsClass.TWO_WHEELER));
        guestsAll.add(new GuestsClass("Mr Rupesh", 1710250730419L, 1710260730419L, 4, GuestsClass.PARKING_REQUIRED, GuestsClass.FOUR_WHEELER));
        guestsAll.add(new GuestsClass("Mr Parth", 1710256730419L, 1710266730419L, 4, GuestsClass.PARKING_NOT_REQUIRED, GuestsClass.NO_VEHICLE));*/


        guestsRecyclerView = findViewById(R.id.guestsRecyclerView);


        // below line is used to get the
        // instance of our FIrebase database.
        firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference();


        addGuestButton = findViewById(R.id.addGuestButton);

        addGuestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomDialog(GuestsActivity.this);
            }
        });



        //showPastGuestsSwitch = findViewById(R.id.showPastGuestsSwitch);
        /*showPastGuestsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    guestsRecyclerView.invalidate();
                    displayGuests.clear();
                    //displayGuests.addAll(guestsNew);
                    adapter.notifyDataSetChanged();
                    Log.d("QWER", "GuestsDisplay = " + displayGuests.size() + " " +  adapter.getItemCount());
                    Toast.makeText(GuestsActivity.this, "Switch On!", Toast.LENGTH_SHORT).show();
                }
                else{
                    displayGuests.clear();
                    displayGuests.addAll(guestsAll);
                    adapter.notifyDataSetChanged();
                    Log.d("QWER", "GuestsDisplay = " + displayGuests.size());
                }
            }
        });*/








        sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);

        databaseReference.child("Guests").child(sharedPreferences.getString("phone number", "error")).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("QWER", "Error getting data", task.getException());
                }
                else {
                    Log.d("QWER", "Got Notices data", task.getException());
                    Iterator<DataSnapshot> iterator = task.getResult().getChildren().iterator();
                    while (iterator.hasNext()) {
                        DataSnapshot childSnapshot = iterator.next();
                        Map<String, Object> noticeData = (Map<String, Object>) childSnapshot.getValue();
                        guestsAll.add(new GuestsClass((String) noticeData.get("Name"), (Long) noticeData.get("startTime"), (Long) noticeData.get("endTime"), ((Long) noticeData.get("numberOfGuests")).intValue(), (Boolean) noticeData.get("parkingRequired"), ((Long) noticeData.get("parkingType")).intValue()));
                        if((Long) noticeData.get("endTime") >= System.currentTimeMillis()){
                            guestsNew.add(new GuestsClass((String) noticeData.get("Name"), (Long) noticeData.get("startTime"), (Long) noticeData.get("endTime"), ((Long) noticeData.get("numberOfGuests")).intValue(), (Boolean) noticeData.get("parkingRequired"), ((Long) noticeData.get("parkingType")).intValue()));
                        }
                        //Toast.makeText(Notices.this, childSnapshot.getKey(), Toast.LENGTH_SHORT).show();
                    }
                    //Log.d("QWER", "Notices : "+ notices.get(3).title);
                    //adapter.notifyDataSetChanged();
                    displayGuests.clear();
                    displayGuests.addAll(guestsAll);
                    guestsRecyclerView.setLayoutManager(new LinearLayoutManager(GuestsActivity.this));
                    adapter = new GuestsRecyclerAdapter(GuestsActivity.this, displayGuests);
                    //adapter.setClickListener(GuestsActivity.this);
                    guestsRecyclerView.setAdapter(adapter);
                }
            }
        });



        /*adapter = new GuestsRecyclerAdapter(GuestsActivity.this, guests);
        //adapter.setClickListener(GuestsActivity.this);
        guestsRecyclerView.setAdapter(adapter);*/
    }




    // Method to show AlertDialog
    public void showCustomDialog(Context context) {

        final Calendar[] startDate = {null};
        final Calendar[] endDate = {null};

        // Inflate the custom layout
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.guests_alertdialog, null);

        // Initialize the views from the custom layout
        CardView cardView = dialogView.findViewById(R.id.cardView);
        TextView textView = dialogView.findViewById(R.id.textView);
        EditText editTextName = dialogView.findViewById(R.id.editTextName);
        EditText editTextNumberOfGuests = dialogView.findViewById(R.id.editTextNumberOfGuests);
        Button pickStartTimeButton = dialogView.findViewById(R.id.pickStartTimeButton);
        Button pickEndTimeButton = dialogView.findViewById(R.id.pickEndTimeButton);
        Button submitButton = dialogView.findViewById(R.id.submitButton);
        submitButton.setEnabled(false);

        // Create the AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView);

        pickStartTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar currentDate = Calendar.getInstance();
                startDate[0] = Calendar.getInstance();
                new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        startDate[0].set(year, monthOfYear, dayOfMonth);
                        new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                startDate[0].set(Calendar.HOUR_OF_DAY, hourOfDay);
                                startDate[0].set(Calendar.MINUTE, minute);
                                Log.v("QWER", "The choosen one " + startDate[0].getTime());
                                pickStartTimeButton.setText("Chosen Date : " + startDate[0].getTime());
                                if(endDate[0] != null && startDate[0].compareTo(endDate[0]) < 0 && !editTextName.getText().toString().equals("") && !editTextNumberOfGuests.getText().toString().equals("")){
                                    submitButton.setEnabled(true);
                                }
                            }
                        }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), false).show();
                    }
                }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
            }
        });


        pickEndTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar currentDate = Calendar.getInstance();
                endDate[0] = Calendar.getInstance();
                new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        endDate[0].set(year, monthOfYear, dayOfMonth);
                        new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                endDate[0].set(Calendar.HOUR_OF_DAY, hourOfDay);
                                endDate[0].set(Calendar.MINUTE, minute);
                                Log.v("QWER", "The choosen one " + endDate[0].getTime());
                                pickEndTimeButton.setText("Chosen Date : " + endDate[0].getTime());
                                if(startDate[0] != null && startDate[0].compareTo(endDate[0]) < 0 && !editTextName.getText().toString().equals("") && !editTextNumberOfGuests.getText().toString().equals("")){
                                    submitButton.setEnabled(true);
                                }
                            }
                        }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), false).show();
                    }
                }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
            }
        });

        AlertDialog dialog = builder.create();

        // Set up the submit button click listener
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the submission here
                HashMap<String, Object> guestsDetails = new HashMap<>();
                guestsDetails.put("Name", editTextName.getText().toString());
                guestsDetails.put("numberOfGuests", Integer.parseInt(editTextNumberOfGuests.getText().toString()));
                guestsDetails.put("parkingRequired", GuestsClass.PARKING_NOT_REQUIRED);
                guestsDetails.put("parkingType", GuestsClass.NO_VEHICLE);
                guestsDetails.put("startTime", startDate[0].getTimeInMillis());
                guestsDetails.put("endTime", endDate[0].getTimeInMillis());


                databaseReference.child("Guests").child(sharedPreferences.getString("phone number", "-1")).child(editTextName.getText().toString()).updateChildren(guestsDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(context, "Details Updated!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        displayGuests.clear();
                        guestsAll.clear();
                        refresh_listview();
                        //finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        //Toast.makeText(SignUpActivity.this, "Some Error Occured", Toast.LENGTH_SHORT).show();
                        Log.d("QWER", "Error : " + e);

                    }
                });
            }
        });

        // Create and show the AlertDialog
        dialog.show();
    }



    public void refresh_listview(){
        databaseReference.child("Guests").child(sharedPreferences.getString("phone number", "error")).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("QWER", "Error getting data", task.getException());
                }
                else {
                    Log.d("QWER", "Got Notices data", task.getException());
                    Iterator<DataSnapshot> iterator = task.getResult().getChildren().iterator();
                    while (iterator.hasNext()) {
                        DataSnapshot childSnapshot = iterator.next();
                        Map<String, Object> noticeData = (Map<String, Object>) childSnapshot.getValue();
                        guestsAll.add(new GuestsClass((String) noticeData.get("Name"), (Long) noticeData.get("startTime"), (Long) noticeData.get("endTime"), ((Long) noticeData.get("numberOfGuests")).intValue(), (Boolean) noticeData.get("parkingRequired"), ((Long) noticeData.get("parkingType")).intValue()));
                        if((Long) noticeData.get("endTime") >= System.currentTimeMillis()){
                            guestsNew.add(new GuestsClass((String) noticeData.get("Name"), (Long) noticeData.get("startTime"), (Long) noticeData.get("endTime"), ((Long) noticeData.get("numberOfGuests")).intValue(), (Boolean) noticeData.get("parkingRequired"), ((Long) noticeData.get("parkingType")).intValue()));
                        }
                        //Toast.makeText(Notices.this, childSnapshot.getKey(), Toast.LENGTH_SHORT).show();
                    }
                    //Log.d("QWER", "Notices : "+ notices.get(3).title);
                    //adapter.notifyDataSetChanged();
                    displayGuests.clear();
                    displayGuests.addAll(guestsAll);
                    guestsRecyclerView.setLayoutManager(new LinearLayoutManager(GuestsActivity.this));
                    adapter = new GuestsRecyclerAdapter(GuestsActivity.this, displayGuests);
                    //adapter.setClickListener(GuestsActivity.this);
                    guestsRecyclerView.setAdapter(adapter);
                }
            }
        });
    }




    @Override
    protected void onStop() {
        super.onStop();
        guestsAll.clear();
        guestsNew.clear();
        displayGuests.clear();
        adapter.notifyDataSetChanged();
    }
}