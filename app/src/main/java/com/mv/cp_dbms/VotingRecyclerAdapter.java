package com.mv.cp_dbms;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

        String animal = titles.get(position);
        holder.recyclerRowTitle.setText(animal);
        Long selectedTime = startTimes.get(position);
        LocalDateTime date = LocalDateTime.ofInstant(Instant.ofEpochMilli(selectedTime), ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        holder.recyclerRowDate.setText(date.format(formatter));
        holder.recyclerRowDetails.setText(details.get(position));
        //if(wins.get(position) == MatchClass.MATCH_LOSE){ // LOSS
            holder.recyclerHomeCardView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF7979")));
        //}
        //else if(wins.get(position) == MatchClass.MATCH_WIN){ // WIN
            holder.recyclerHomeCardView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#96FF79")));
            //Log.d("QWER", position + " Match Win!");
        //}
        //else { // ELSE
            holder.recyclerHomeCardView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#CACACA")));
        //}
        //Log.d("QWER", "Title : " + titles.get(position));

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return titles.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class VotingRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView recyclerRowTitle;
        TextView recyclerRowDate;
        TextView recyclerRowDetails;
        CardView recyclerHomeCardView;

        VotingRecyclerViewHolder(View itemView) {
            super(itemView);
            recyclerRowTitle = itemView.findViewById(R.id.recyclerRowTitle);
            recyclerRowDate = itemView.findViewById(R.id.recyclerRowDate);
            recyclerRowDetails = itemView.findViewById(R.id.recyclerRowDetails);
            recyclerHomeCardView = itemView.findViewById(R.id.recyclerHomeCardView);
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
