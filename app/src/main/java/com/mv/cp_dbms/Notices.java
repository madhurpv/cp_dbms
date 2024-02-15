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
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class Notices extends AppCompatActivity implements NoticesRecyclerAdapter.ItemClickListener{


    public static List<String> titles = new ArrayList<>();
    public static List<Long> times;
    public static List<String> details;

    public static NoticesRecyclerAdapter adapter; // Edited from AddMatch.java
    RecyclerView noticesRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notices);

        titles.add("Title1"); details.add("12345678987654321`123456tfdszxcvfd`````~`~`````reovkoerkverkrokevekvdsk");
        titles.add("Title2"); details.add("Details here");
        titles.add("Title3"); details.add("This is a notice.\nDo not pay any attention\nStop Reading\nI said Stop!");

        noticesRecyclerView = findViewById(R.id.noticesRecyclerView);
        noticesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NoticesRecyclerAdapter(this, titles, times, details);
        adapter.setClickListener(this);
        noticesRecyclerView.setAdapter(adapter);
    }



    @Override
    public void onItemClick(View view, int position) {

    }


}