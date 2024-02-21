package com.mv.cp_dbms;

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
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Notices extends AppCompatActivity implements NoticesRecyclerAdapter.ItemClickListener{


    public static List<NoticesClass> notices = new ArrayList<>();

    public static NoticesRecyclerAdapter adapter; // Edited from AddMatch.java
    RecyclerView noticesRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notices);

        notices.add(new NoticesClass("Title1", 1708227286000L, "12345678987654321`123456tfdszxcvfd`````~`~`````reovkoerkverkrokevekvdsk5454545454545454"));
        notices.add(new NoticesClass("Title2", 1710732886000L, "Details here"));
        notices.add(new NoticesClass("Title3", 1742268886000L, "This is a notice.\nDo not pay any attention\nStop Reading\nI said Stop!\nByebye!\nSee You!"));

        noticesRecyclerView = findViewById(R.id.noticesRecyclerView);
        noticesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        noticesRecyclerView.setNestedScrollingEnabled(true);
        adapter = new NoticesRecyclerAdapter(this, notices);
        adapter.setClickListener(this);
        noticesRecyclerView.setAdapter(adapter);



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





    }


}