package com.mv.cp_dbms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;

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
    }
}