package com.mv.cp_dbms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Voting extends AppCompatActivity implements VotingRecyclerAdapter.ItemClickListener{



    public static List<VotingClass> votings = new ArrayList<>();

    public static VotingRecyclerAdapter adapter;
    RecyclerView votingsRecyclerView;


    // creating a variable for our
    // Firebase Database.
    FirebaseDatabase firebaseDatabase;

    // creating a variable for our Database
    // Reference for Firebase.
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voting);

        List<String> voting1options = new ArrayList<>();
        voting1options.add("Yes");voting1options.add("No");voting1options.add("Maybe");
        votings.add(new VotingClass("Voting1", 1708227286000L, 1708228286000L, "Vote for humanity", voting1options, -1, VotingClass.NOT_VOTED));




        votingsRecyclerView = findViewById(R.id.votingRecyclerView);
        votingsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        votingsRecyclerView.setNestedScrollingEnabled(true);

        adapter = new VotingRecyclerAdapter(Voting.this, votings);
        adapter.setClickListener(Voting.this);
        votingsRecyclerView.setAdapter(adapter);



        // below line is used to get the
        // instance of our FIrebase database.
        firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference();




    }




    @Override
    public void onItemClick(View view, int position) {

        //Toast.makeText(this, "Position : " + position, Toast.LENGTH_SHORT).show();



        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.notices_dialog, null);

        TextView tvHeading = v.findViewById(R.id.tv_heading);
        TextView tvText = v.findViewById(R.id.tv_text);
        ImageView ivClose = v.findViewById(R.id.iv_close);
        TextView date_text_view = v.findViewById(R.id.date_text_view);

        // Set your desired heading and text
        /*tvHeading.setText(notices.get(position).title);
        tvText.setText(notices.get(position).details);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date(notices.get(position).time);
        date_text_view.setText(sdf.format(date));*/

        builder.setView(v);

        final AlertDialog dialog = builder.create();

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();





        databaseReference.child("Notices").setValue("12345").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                //Toast.makeText(Notices.this, "Successful!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Toast.makeText(Notices.this, "Error!" + e, Toast.LENGTH_SHORT).show();
                Log.d("QWER", "Error : " + e);

            }
        });




    }
}