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


public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationView> {

    ArrayList<NotificationInfo> arrayList;
    Context context;

    public NotificationAdapter(ArrayList<NotificationInfo> arrayList, Context context){
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public NotificationView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_view,parent,false);
        return new NotificationView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationView holder, int position) {
        final NotificationInfo currentList=arrayList.get(position);
        holder.title.setText(currentList.getTitle());
        holder.date.setText(currentList.getDate());
        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(currentList.getLink()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class NotificationView extends RecyclerView.ViewHolder{
        public TextView title,date;
        public Button download;

        public NotificationView(@NonNull View itemView) {
            super(itemView);

            title=itemView.findViewById(R.id.title);
            date=itemView.findViewById(R.id.date);
            download=itemView.findViewById(R.id.download);
        }
    }
}
