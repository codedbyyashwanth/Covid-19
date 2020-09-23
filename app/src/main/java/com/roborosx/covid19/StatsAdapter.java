package com.roborosx.covid19;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StatsAdapter extends RecyclerView.Adapter<StatsAdapter.StatsViewHolder> {

    ArrayList<StatsInfo> arrayList;
    Context context;

    public StatsAdapter(ArrayList<StatsInfo> arrayList,Context context)
    {
        this.arrayList=arrayList;
        this.context=context;
    }


    @NonNull
    @Override
    public StatsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.stats_view,parent,false);
        return new StatsViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull StatsViewHolder holder, int position) {
        StatsInfo currentInfo = arrayList.get(position);
        holder.SState.setText(currentInfo.getSState());
        holder.confirmed.setText("" + currentInfo.getConfirmed());
        holder.discharged.setText("" + currentInfo.getDischarged());
        holder.death.setText("" +  currentInfo.getDeath());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class StatsViewHolder extends RecyclerView.ViewHolder{
        public TextView SState,confirmed,discharged,death;

        public StatsViewHolder(@NonNull View itemView) {
            super(itemView);

            SState = itemView.findViewById(R.id.SState);
            confirmed = itemView.findViewById(R.id.confirmed);
            discharged = itemView.findViewById(R.id.discharged);
            death = itemView.findViewById(R.id.deaths);
        }
    }
}
