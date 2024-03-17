
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

public class ComplaintsRecyclerAdapter extends RecyclerView.Adapter<ComplaintsRecyclerAdapter.ComplaintsRecyclerViewHolder> {

    private List<String> titles = new ArrayList<>();
    private List<String> descriptions = new ArrayList<>();
    private List<String> complaint_maker_numbers = new ArrayList<>();
    private List<String> complaint_maker_names = new ArrayList<>();
    private List<Integer> complaint_states = new ArrayList<>();
    private List<Long> times = new ArrayList<>();
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    ComplaintsRecyclerAdapter(Context context, List<ComplaintsClass> notices) {
        this.mInflater = LayoutInflater.from(context);
        for(int i=0; i<notices.size(); i++){
            this.titles.add(notices.get(i).title);
            this.times.add(notices.get(i).time);
            this.descriptions.add(notices.get(i).description);
            this.complaint_maker_numbers.add(notices.get(i).complaint_maker_number);
            this.complaint_maker_names.add(notices.get(i).complaint_maker_name);
            this.complaint_states.add(notices.get(i).complaint_state);
        }
    }

    // inflates the row layout from xml when needed
    @Override
    public ComplaintsRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.complaints_recyclerrow, parent, false);
        return new ComplaintsRecyclerViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ComplaintsRecyclerViewHolder holder, int position) {

        holder.recyclerRowTitle.setText(titles.get(position));
        Long selectedTime = times.get(position);
        LocalDateTime date = LocalDateTime.ofInstant(Instant.ofEpochMilli(selectedTime), ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm  dd-MM");
        holder.recyclerRowDate.setText(date.format(formatter));
        holder.recyclerRowDetails.setText(descriptions.get(position));
        holder.recyclerRowName.setText(complaint_maker_names.get(position));
        if(complaint_states.get(position) == ComplaintsClass.COMPLAINT_RESOLVED){ // LOSS
            holder.recyclerHomeCardView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF7979")));
        }
        //else if(wins.get(position) == MatchClass.MATCH_WIN){ // WIN
            //holder.recyclerHomeCardView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#96FF79")));
            //Log.d("QWER", position + " Match Win!");
        //}
        //else { // ELSE
            //holder.recyclerHomeCardView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#CACACA")));
        //}
        //Log.d("QWER", "Title : " + titles.get(position));

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return titles.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ComplaintsRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView recyclerRowTitle;
        TextView recyclerRowDate;
        TextView recyclerRowDetails;
        TextView recyclerRowName;
        CardView recyclerHomeCardView;

        ComplaintsRecyclerViewHolder(View itemView) {
            super(itemView);
            recyclerRowTitle = itemView.findViewById(R.id.recyclerRowTitle);
            recyclerRowDate = itemView.findViewById(R.id.recyclerRowDate);
            recyclerRowDetails = itemView.findViewById(R.id.recyclerRowDetails);
            recyclerRowName = itemView.findViewById(R.id.recyclerRowName);
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
