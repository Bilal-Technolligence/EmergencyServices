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

import java.util.ArrayList;

public class FFAdapter extends RecyclerView.Adapter<FFAdapter.ViewHolder> {
    ArrayList<UserAttr> addServiceAttrs;
    private Context context;

    public FFAdapter(ArrayList<UserAttr> addServiceAttrs, Context context) {
        this.context = context;
        this.addServiceAttrs = addServiceAttrs;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friendfamilylist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.name.setText(addServiceAttrs.get(position).getName());
        holder.deleteBtn.setVisibility(View.GONE);

    }

    @Override
    public int getItemCount() {
        return addServiceAttrs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView deleteBtn;
        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            deleteBtn = (ImageView) itemView.findViewById(R.id.imgDelete);
            name = (TextView) itemView.findViewById(R.id.txtName);


        }
    }
}
