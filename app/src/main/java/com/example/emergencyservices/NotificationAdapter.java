package com.example.emergencyservices;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {
    ArrayList<notificationAttr> addServiceAttrs;
    Activity context;

    public NotificationAdapter(ArrayList<notificationAttr> addServiceAttrs, Activity context) {
        this.context = context;
        this.addServiceAttrs = addServiceAttrs;
    }

    @NonNull
    @Override
    public NotificationAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notifitem, parent, false);
        return new NotificationAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.MyViewHolder holder, final int position) {
        holder.desc.setText(addServiceAttrs.get(position).getMessage());
        holder.datetime.setText(addServiceAttrs.get(position).getAddress());
        holder.title.setText(addServiceAttrs.get(position).getName());

        holder.itemView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String latitude = addServiceAttrs.get(position).getLat();
                final String longitude = addServiceAttrs.get(position).getLon();

                Intent intent = new Intent( context, DirectionOnMap.class );
                intent.putExtra("Latitude", latitude);
                intent.putExtra("Longitude", longitude);
                context.startActivity( intent );

            }
        } );

    }

    @Override
    public int getItemCount() {
        return addServiceAttrs.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView desc,datetime,title;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.notification_title);
            desc = (TextView) itemView.findViewById(R.id.notification_description);
            datetime=(TextView) itemView.findViewById( R.id.notification_datetime );
        }
    }

}
