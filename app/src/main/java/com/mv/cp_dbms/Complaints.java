package com.mv.cp_dbms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
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
        //TODO: Edit
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
            }
        });
    }
}