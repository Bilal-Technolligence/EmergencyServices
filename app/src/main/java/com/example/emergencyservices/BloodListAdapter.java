package com.example.emergencyservices;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BloodListAdapter extends RecyclerView.Adapter<BloodListAdapter.ViewHolder> {
    ArrayList<BloodAttr> bloodAttrs;
    private Context context;

    public BloodListAdapter(ArrayList<BloodAttr> bloodAttrs, Context context) {
        this.context = context;
        this.bloodAttrs = bloodAttrs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.blood, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.name.setText(bloodAttrs.get(position).getName());
        holder.phone.setText(bloodAttrs.get(position).getPhone());
        holder.age.setText(bloodAttrs.get(position).getAge());
        holder.address.setText(bloodAttrs.get(position).getAddress());
        holder.email.setText(bloodAttrs.get(position).getEmail());
        holder.type.setText(bloodAttrs.get(position).getBloodgroup());
        Picasso.get().load(bloodAttrs.get(position).getImgurl()).into(holder.profile);


    }

    @Override
    public int getItemCount() {
        return bloodAttrs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profile;
        TextView name , phone , age , email , address , type;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profile = (ImageView) itemView.findViewById(R.id.imgProfile);
            name = (TextView) itemView.findViewById(R.id.txtName);
            phone = (TextView) itemView.findViewById(R.id.txtContact);
            age = (TextView) itemView.findViewById(R.id.txtAge);
            email = (TextView) itemView.findViewById(R.id.txtEmail);
            address = (TextView) itemView.findViewById(R.id.txtAddress);
            type = (TextView) itemView.findViewById(R.id.txtBlood);

        }
    }
}
