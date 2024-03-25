package com.mv.cp_dbms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Notices extends AppCompatActivity implements NoticesRecyclerAdapter.ItemClickListener{


    public static List<NoticesClass> notices = new ArrayList<>();

    public static NoticesRecyclerAdapter adapter;
    RecyclerView noticesRecyclerView;


    // creating a variable for our
    // Firebase Database.
    FirebaseDatabase firebaseDatabase;

    // creating a variable for our Database
    // Reference for Firebase.
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notices);

        //notices.add(new NoticesClass("Title1", 1708227286000L, "12345678987654321`123456tfdszxcvfd`````~`~`````reovkoerkverkrokevekvdsk5454545454545454"));
        //notices.add(new NoticesClass("Title2", 1710732886000L, "Details here"));
        //notices.add(new NoticesClass("Title3", 1742268886000L, "This is a notice.\nDo not pay any attention\nStop Reading\nI said Stop!\nByebye!\nSee You!"));

        noticesRecyclerView = findViewById(R.id.noticesRecyclerView);
        noticesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        noticesRecyclerView.setNestedScrollingEnabled(true);




        // below line is used to get the
        // instance of our FIrebase database.
        firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference();

        databaseReference.child("Notices").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
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
                        notices.add(new NoticesClass(childSnapshot.getKey(), (Long) noticeData.get("Time"), (String) noticeData.get("Details")));
                        //Toast.makeText(Notices.this, childSnapshot.getKey(), Toast.LENGTH_SHORT).show();
                    }
                    //Log.d("QWER", "Notices : "+ notices.get(3).title);
                    //adapter.notifyDataSetChanged();
                    adapter = new NoticesRecyclerAdapter(Notices.this, notices);
                    adapter.setClickListener(Notices.this);
                    noticesRecyclerView.setAdapter(adapter);
                }
            }
        });

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
        tvHeading.setText(notices.get(position).title);
        tvText.setText(notices.get(position).details);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date(notices.get(position).time);
        date_text_view.setText(sdf.format(date));

        builder.setView(v);

        final AlertDialog dialog = builder.create();

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();





        /*databaseReference.child("Notices").setValue("12345").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(Notices.this, "Successful!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Notices.this, "Error!" + e, Toast.LENGTH_SHORT).show();
                Log.d("QWER", "Error : " + e);

            }
        });*/




    }






}