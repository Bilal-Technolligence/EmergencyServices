package com.example.emergencyservices;


import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class HelperNotificationAdapter extends RecyclerView.Adapter<HelperNotificationAdapter.MyViewHolder> {
    ArrayList<notificationAttr> addServiceAttrs;
    Activity context;
    String id;

    public HelperNotificationAdapter(ArrayList<notificationAttr> addServiceAttrs, Activity context, String id) {
        this.context = context;
        this.addServiceAttrs = addServiceAttrs;
        this.id = id;
    }

    @NonNull
    @Override
    public HelperNotificationAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notifitem, parent, false);
        return new HelperNotificationAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HelperNotificationAdapter.MyViewHolder holder, final int position) {
        holder.desc.setText(addServiceAttrs.get(position).getMessage());
        holder.datetime.setText(addServiceAttrs.get(position).getAddress());
        holder.title.setText(addServiceAttrs.get(position).getName());


        holder.itemView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Id = addServiceAttrs.get(position).getId();
                Intent intent = new Intent( context, DirectionOnMap2.class );
                intent.putExtra("id", Id);
                intent.putExtra("user", id);
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
