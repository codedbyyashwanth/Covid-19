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

public class CollegesAdapter extends RecyclerView.Adapter<CollegesAdapter.CollegeViewHolder> {

    ArrayList<CollegesInfo> arrayList;
    Context context;

    public CollegesAdapter(ArrayList<CollegesInfo> arrayList,Context context){
        this.arrayList=arrayList;
        this.context=context;
    }

    @NonNull
    @Override
    public CollegeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.colleges_view,parent,false);
        return new CollegeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CollegeViewHolder holder, int position) {
        final CollegesInfo currentList = arrayList.get(position);

        holder.College.setText(currentList.getCollege());
        holder.State.setText(currentList.getState());
        holder.OwnerShip.setText(currentList.getType());
        holder.SeeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seeMore(currentList);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void seeMore(CollegesInfo currentList) {
        TextView city,beds,admission;
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        View view=View.inflate(context,R.layout.colleges_see_more,null);
        builder.setView(view);
        city=view.findViewById(R.id.City);
        beds=view.findViewById(R.id.CBeds);
        admission=view.findViewById(R.id.Admission);
        city.setText("City: " + currentList.getCity());
        beds.setText("Beds: " + currentList.getBeds());
        admission.setText("Admission Capacity: " + currentList.getCapacity());
        Button button=view.findViewById(R.id.CClose);
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

    public static class CollegeViewHolder extends RecyclerView.ViewHolder{

        public TextView College,State,OwnerShip;
        public Button SeeMore;

        public CollegeViewHolder(@NonNull View itemView) {
            super(itemView);

            College=itemView.findViewById(R.id.College);
            State=itemView.findViewById(R.id.CState);
            OwnerShip=itemView.findViewById(R.id.ownership);
            SeeMore=itemView.findViewById(R.id.SeeMore);
        }
    }
}
