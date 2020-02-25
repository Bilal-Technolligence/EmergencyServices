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

public class MissingList extends RecyclerView.Adapter<MissingList.ViewHolder> {
    ArrayList<MissingPersonAttributes> missingPersonAttributes;
    private Context context;
    Activity friendfamily;
    String user;

    public MissingList(ArrayList<MissingPersonAttributes> missingPersonAttributes, MissingPersonsList friendAndFamilyList) {
        this.context = context;
        this.missingPersonAttributes = missingPersonAttributes;
        this.friendfamily = friendAndFamilyList;
        this.user = user;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.missing, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.name.setText(missingPersonAttributes.get(position).getName());
        holder.fName.setText(missingPersonAttributes.get(position).getFatherName());
        holder.age.setText(missingPersonAttributes.get(position).getAge());
        holder.address.setText(missingPersonAttributes.get(position).getAddress());
        holder.relation.setText(missingPersonAttributes.get(position).getRelation());
        Picasso.get().load(missingPersonAttributes.get(position).getImgurl()).into(holder.img);


    }

    @Override
    public int getItemCount() {
        return missingPersonAttributes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name,fName,age,address,relation;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.imgProfile);
            name = (TextView) itemView.findViewById(R.id.txtName);
            fName = (TextView) itemView.findViewById(R.id.txtfName);
            age = (TextView) itemView.findViewById(R.id.txtage);
            address = (TextView) itemView.findViewById(R.id.txtaddress);
            relation = (TextView) itemView.findViewById(R.id.txtrelation);


        }
    }
}
