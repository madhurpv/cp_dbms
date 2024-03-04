package com.mv.cp_dbms;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VotingRecyclerAdapter extends RecyclerView.Adapter<VotingRecyclerAdapter.VotingRecyclerViewHolder> {

    private List<String> titles = new ArrayList<>();
    private List<Long> startTimes = new ArrayList<>();
    private List<Long> endTimes = new ArrayList<>();
    private List<String> details = new ArrayList<>();
    private List<List<String>> options = new ArrayList<>();
    private List<Integer> selections = new ArrayList<>();
    private List<Boolean> voted = new ArrayList<>();
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    VotingRecyclerAdapter(Context context, List<VotingClass> notices) {
        this.mInflater = LayoutInflater.from(context);
        for(int i=0; i<notices.size(); i++){
            this.titles.add(notices.get(i).title);
            this.startTimes.add(notices.get(i).startTime);
            this.endTimes.add(notices.get(i).endTime);
            this.options.add(notices.get(i).options);
            this.selections.add(notices.get(i).selection);
            this.voted.add(notices.get(i).voted);
            this.details.add(notices.get(i).details);
        }
    }

    // inflates the row layout from xml when needed
    @Override
    public VotingRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.votings_recyclerrow, parent, false);
        return new VotingRecyclerViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(VotingRecyclerViewHolder holder, int position) {

        String voteTitle = titles.get(position);
        holder.recyclerRowTitle.setText(voteTitle);
        Long startTime = startTimes.get(position);
        Long endTime = endTimes.get(position);
        LocalDateTime dateStart = LocalDateTime.ofInstant(Instant.ofEpochMilli(startTime), ZoneId.systemDefault());
        LocalDateTime dateEnd = LocalDateTime.ofInstant(Instant.ofEpochMilli(endTime), ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        holder.recyclerRowStartDate.setText(dateStart.format(formatter));
        holder.recyclerRowEndDate.setText(dateEnd.format(formatter));
        holder.recyclerRowDetails.setText(details.get(position));


        for (int i = 0; i < options.get(position).size(); i++) {
            RadioButton rdbtn = new RadioButton(holder.radioGroup.getContext());
            rdbtn.setId(View.generateViewId());
            rdbtn.setText(options.get(position).get(i));
            //rdbtn.setOnClickListener();
            holder.radioGroup.addView(rdbtn);
        }

        if(voted.get(holder.getAdapterPosition()) == VotingClass.VOTED){
            RadioButton chosenRadioButton = (RadioButton) holder.radioGroup.getChildAt(selections.get(holder.getAdapterPosition()));
            chosenRadioButton.setChecked(true);
            holder.voteButton.setEnabled(false);
            for (int i = 0; i < holder.radioGroup.getChildCount(); i++) {
                holder.radioGroup.getChildAt(i).setEnabled(false);
            }
            holder.recyclerHomeCardView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#999999")));
        }


        holder.voteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedRadioIndex = holder.radioGroup.getCheckedRadioButtonId();
                if(selectedRadioIndex == -1){
                    Toast.makeText(holder.radioGroup.getContext(), "First select an option!!", Toast.LENGTH_SHORT).show();
                }
                else{
                    int selectedRadioPosition = holder.radioGroup.indexOfChild(holder.radioGroup.findViewById(selectedRadioIndex));
                    //Toast.makeText(holder.radioGroup.getContext(), options.get(holder.getAdapterPosition()).get(selectedRadioPosition), Toast.LENGTH_SHORT).show();

                    // below line is used to get the
                    // instance of our FIrebase database.
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();;

                    SharedPreferences sharedPreferences = holder.radioGroup.getContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
                    HashMap<String, Object> selectedOption = new HashMap<>();
                    selectedOption.put("" + sharedPreferences.getInt("flatNo", -1), (long) selectedRadioPosition);
                    databaseReference.child("Votings").child(voteTitle).child("Voters").updateChildren(selectedOption).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(holder.radioGroup.getContext(), "Voting Successful!", Toast.LENGTH_SHORT).show();
                            holder.voteButton.setEnabled(false);
                            for (int i = 0; i < holder.radioGroup.getChildCount(); i++) {
                                holder.radioGroup.getChildAt(i).setEnabled(false);
                            }
                            holder.recyclerHomeCardView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#999999")));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(holder.radioGroup.getContext(), "Error!" + e, Toast.LENGTH_SHORT).show();
                            Log.d("QWER", "Error : " + e);

                        }
                    });
                }

            }
        });


    }

    // total number of rows
    @Override
    public int getItemCount() {
        return titles.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class VotingRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView recyclerRowTitle;
        TextView recyclerRowStartDate;
        TextView recyclerRowEndDate;
        TextView recyclerRowDetails;
        CardView recyclerHomeCardView;
        RadioGroup radioGroup;
        Button voteButton;

        VotingRecyclerViewHolder(View itemView) {
            super(itemView);
            recyclerRowTitle = itemView.findViewById(R.id.recyclerRowTitle);
            recyclerRowStartDate = itemView.findViewById(R.id.recyclerRowStartDate);
            recyclerRowEndDate = itemView.findViewById(R.id.recyclerRowEndDate);
            recyclerRowDetails = itemView.findViewById(R.id.recyclerRowDetails);
            recyclerHomeCardView = itemView.findViewById(R.id.recyclerHomeCardView);
            radioGroup = itemView.findViewById(R.id.radioGroup);
            voteButton = itemView.findViewById(R.id.voteButton);
            //recyclerRowTitle.setMovementMethod(new ScrollingMovementMethod());

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return titles.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
