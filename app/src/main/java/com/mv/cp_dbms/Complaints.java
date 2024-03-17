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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Complaints extends AppCompatActivity {


    public static List<ComplaintsClass> displayComplaints = new ArrayList<>();
    public static ComplaintsRecyclerAdapter adapter;

    RecyclerView complaintsRecyclerView;
    FloatingActionButton addComplaintButton;

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
        setContentView(R.layout.activity_complaints);


        // below line is used to get the
        // instance of our FIrebase database.
        firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference();



        complaintsRecyclerView = findViewById(R.id.complaintsRecyclerView);
        addComplaintButton = findViewById(R.id.addComplaintButton);





        sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        databaseReference.child("Complaints").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
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
                        Map<String, Object> complaintData = (Map<String, Object>) childSnapshot.getValue();
                        displayComplaints.add(new ComplaintsClass((String) complaintData.get("title"), (String) complaintData.get("description"), (Long) complaintData.get("time"), ((Long) complaintData.get("status")).intValue(), (String) complaintData.get("complainterPhoneNo"), (String) complaintData.get("complainterName")));
                        /*if((Long) noticeData.get("endTime") >= System.currentTimeMillis()){
                            guestsNew.add(new GuestsClass((String) noticeData.get("Name"), (Long) noticeData.get("startTime"), (Long) noticeData.get("endTime"), ((Long) noticeData.get("numberOfGuests")).intValue(), (Boolean) noticeData.get("parkingRequired"), ((Long) noticeData.get("parkingType")).intValue()));
                        }*/
                        //Toast.makeText(Notices.this, childSnapshot.getKey(), Toast.LENGTH_SHORT).show();
                    }
                    //Log.d("QWER", "Notices : "+ notices.get(3).title);
                    //adapter.notifyDataSetChanged();
                    //displayGuests.clear();
                    //displayGuests.addAll(guestsAll);
                    complaintsRecyclerView.setLayoutManager(new LinearLayoutManager(Complaints.this));
                    adapter = new ComplaintsRecyclerAdapter(Complaints.this, displayComplaints);
                    //adapter.setClickListener(GuestsActivity.this);
                    complaintsRecyclerView.setAdapter(adapter);
                }
            }
        });



        addComplaintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Complaints.this, "Creating A New Complaint!", Toast.LENGTH_SHORT).show();
                showCustomDialog(Complaints.this);
            }
        });
    }







    // Method to show AlertDialog
    public void showCustomDialog(Context context) {

        // Inflate the custom layout
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.complaints_alertdialog, null);

        // Initialize the views from the custom layout
        CardView cardView = dialogView.findViewById(R.id.cardView);
        TextView textView = dialogView.findViewById(R.id.textView);
        EditText editTextName = dialogView.findViewById(R.id.editTextName);
        EditText editTextDescription = dialogView.findViewById(R.id.editTextDescription);
        Button submitButton = dialogView.findViewById(R.id.submitButton);
        //submitButton.setEnabled(false);

        // Create the AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView);


        AlertDialog dialog = builder.create();

        // Set up the submit button click listener
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the submission here
                HashMap<String, Object> complaintDetails = new HashMap<>();
                complaintDetails.put("title", editTextName.getText().toString());
                complaintDetails.put("description", editTextDescription.getText().toString());
                complaintDetails.put("complainterName", sharedPreferences.getString("name", "-1"));
                complaintDetails.put("complainterPhoneNo", sharedPreferences.getString("phone number", "-1"));
                complaintDetails.put("status", ComplaintsClass.COMPLAINT_NOT_RESOLVED);
                complaintDetails.put("time", System.currentTimeMillis());


                databaseReference.child("Complaints").child(String.valueOf(System.currentTimeMillis())).updateChildren(complaintDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(context, "Details Updated!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        //TODO: Update List
                        /*displayGuests.clear();
                        guestsAll.clear();
                        refresh_listview();*/
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
        displayComplaints.clear();
        databaseReference.child("Complaints").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
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
                        Map<String, Object> complaintData = (Map<String, Object>) childSnapshot.getValue();
                        displayComplaints.add(new ComplaintsClass((String) complaintData.get("title"), (String) complaintData.get("description"), (Long) complaintData.get("time"), ((Long) complaintData.get("status")).intValue(), (String) complaintData.get("complainterPhoneNo"), (String) complaintData.get("complainterName")));
                        /*if((Long) noticeData.get("endTime") >= System.currentTimeMillis()){
                            guestsNew.add(new GuestsClass((String) noticeData.get("Name"), (Long) noticeData.get("startTime"), (Long) noticeData.get("endTime"), ((Long) noticeData.get("numberOfGuests")).intValue(), (Boolean) noticeData.get("parkingRequired"), ((Long) noticeData.get("parkingType")).intValue()));
                        }*/
                        //Toast.makeText(Notices.this, childSnapshot.getKey(), Toast.LENGTH_SHORT).show();
                    }
                    //Log.d("QWER", "Notices : "+ notices.get(3).title);
                    //adapter.notifyDataSetChanged();
                    //displayGuests.clear();
                    //displayGuests.addAll(guestsAll);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }



}