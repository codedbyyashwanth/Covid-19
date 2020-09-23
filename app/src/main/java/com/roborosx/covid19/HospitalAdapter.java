package com.roborosx.covid19;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HospitalAdapter extends RecyclerView.Adapter<HospitalAdapter.HospitalViewHolder> {

    ArrayList<HospitalInfo> arrayList;
    Context context;

    public HospitalAdapter(ArrayList<HospitalInfo> list,Context context)
    {
        arrayList=list;
        this.context=context;
    }

    @NonNull
    @Override
    public HospitalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.hospital_view,parent,false);
        return new HospitalViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull HospitalViewHolder holder, int position) {
        final HospitalInfo currentItem=arrayList.get(position);
        holder.state.setText(currentItem.getState());
        holder.hospital1.setText("" + currentItem.getRHospital());
        holder.hospital2.setText("" + currentItem.getUHospital());
        holder.bed1.setText("" + currentItem.getRBed());
        holder.bed2.setText("" + currentItem.getUBed());
        holder.BTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog(currentItem);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void alertDialog(HospitalInfo currentItem) {
        int TotalHospital,TotalBeds;
        TotalHospital=currentItem.getRHospital() + currentItem.getUHospital();
        TotalBeds=currentItem.getRBed() + currentItem.getUBed();
        TextView HText,BText;
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        View view=View.inflate(context,R.layout.hospital_dialog,null);
        builder.setView(view);
        HText=view.findViewById(R.id.THospital);
        BText=view.findViewById(R.id.TBeds);
        HText.setText("Total Hospital: " + TotalHospital);
        BText.setText("Total Beds: " + TotalBeds);
        Button button=view.findViewById(R.id.close);
        builder.create();
        final AlertDialog dialog = builder.show();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class HospitalViewHolder extends RecyclerView.ViewHolder{
        public TextView state,hospital1,hospital2,bed1,bed2;
        public Button BTotal;
        public HospitalViewHolder(@NonNull View itemView) {
            super(itemView);

            state=itemView.findViewById(R.id.HState);
            hospital1=itemView.findViewById(R.id.SHospital1);
            hospital2=itemView.findViewById(R.id.SHospital2);
            bed1=itemView.findViewById(R.id.SBed1);
            bed2=itemView.findViewById(R.id.SBed2);
            BTotal=itemView.findViewById(R.id.BTotal);
        }
    }
}
