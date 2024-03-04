package com.mv.cp_dbms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends AppCompatActivity {


    CardView votingCardView, guestsCardView, noticesCardView, feesCardView, complaintsCardView, profileCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        votingCardView = findViewById(R.id.votingCardView);
        guestsCardView = findViewById(R.id.guestsCardView);
        noticesCardView = findViewById(R.id.noticesCardView);
        feesCardView = findViewById(R.id.feesCardView);
        complaintsCardView = findViewById(R.id.complaintsCardView);
        profileCardView = findViewById(R.id.profileCardView);


        votingCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, Voting.class);
                startActivity(i);
            }
        });

        guestsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        noticesCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, Notices.class);
                startActivity(i);
            }
        });

        feesCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        complaintsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        profileCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}