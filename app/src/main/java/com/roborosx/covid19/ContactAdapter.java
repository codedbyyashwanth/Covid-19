package com.roborosx.covid19;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private ArrayList<ContactInformation> information;
    private Context context;

    public ContactAdapter(ArrayList<ContactInformation> info, Context view)
    {
        information = info;
        context = view;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_layout,parent,false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ContactViewHolder holder, int position) {
        final ContactInformation currentInfo = information.get(position);

        holder.state.setText(currentInfo.getState());
        holder.number.setText(currentInfo.getNumber());
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("tel:" + currentInfo.getNumber()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return information.size();
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder{

        public TextView state,number;
        public Button button;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            state = itemView.findViewById(R.id.state);
            number = itemView.findViewById(R.id.number);
            button = itemView.findViewById(R.id.call);
        }
    }
}
