package com.mv.cp_dbms;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Complaints extends AppCompatActivity {


    FloatingActionButton addComplaintButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints);

        addComplaintButton = findViewById(R.id.addComplaintButton);
        addComplaintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Complaints.this, "Creating A New Complaint!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}