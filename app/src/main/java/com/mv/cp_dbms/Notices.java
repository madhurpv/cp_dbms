package com.mv.cp_dbms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Notices extends AppCompatActivity implements NoticesRecyclerAdapter.ItemClickListener{


    public static List<String> titles = new ArrayList<>();
    public static List<Long> times = new ArrayList<>();
    public static List<String> details = new ArrayList<>();
    public static List<NoticesClass> notices = new ArrayList<>();

    public static NoticesRecyclerAdapter adapter; // Edited from AddMatch.java
    RecyclerView noticesRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notices);

        notices.add(new NoticesClass("Title1", -1, "12345678987654321`123456tfdszxcvfd`````~`~`````reovkoerkverkrokevekvdsk5454545454545454"));
        notices.add(new NoticesClass("Title2", -1, "Details here"));
        notices.add(new NoticesClass("Title3", -1, "This is a notice.\nDo not pay any attention\nStop Reading\nI said Stop!\nByebye!\nSee You!"));

        noticesRecyclerView = findViewById(R.id.noticesRecyclerView);
        noticesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        noticesRecyclerView.setNestedScrollingEnabled(true);
        adapter = new NoticesRecyclerAdapter(this, notices);
        adapter.setClickListener(this);
        noticesRecyclerView.setAdapter(adapter);



    }



    @Override
    public void onItemClick(View view, int position) {

        Toast.makeText(this, "Position : " + position, Toast.LENGTH_SHORT).show();

    }


}