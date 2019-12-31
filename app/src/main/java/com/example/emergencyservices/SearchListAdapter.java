package com.example.emergencyservices;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.security.AccessControlContext;
import java.util.ArrayList;

public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.ViewHolder> {
    ArrayList<UserAttr> addServiceAttrs;
    private Context context;
    public SearchListAdapter(ArrayList<UserAttr> addServiceAttrs, Context context){
        this.context=context;
        this.addServiceAttrs = addServiceAttrs;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.search,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Picasso.get().load(addServiceAttrs.get(position).getImageUrl()).into(holder.profileImage);
        holder.name.setText(addServiceAttrs.get(position).getName());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Id = null;
                Id = addServiceAttrs.get(position).getId();
                Toast.makeText(context , Id , Toast.LENGTH_SHORT).show();
//                Intent i = new Intent(context , ServiceDetail.class);
//                i.putExtra("Id" , serviceId);
//                context.startActivity(i);

            }
        });

    }

    @Override
    public int getItemCount() {
        return addServiceAttrs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profileImage;
        TextView name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.imgProfile);
            name = itemView.findViewById(R.id.txtName);


        }
    }
}
