package com.example.emergencyservices;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FriendFamilyAdapter extends RecyclerView.Adapter<FriendFamilyAdapter.ViewHolder> {
    ArrayList<UserAttr> addServiceAttrs;
    private Context context;
    Activity friendfamily;
    String user;

    public FriendFamilyAdapter(ArrayList<UserAttr> addServiceAttrs, Context context, FriendAndFamilyList friendAndFamilyList, String user) {
        this.context = context;
        this.addServiceAttrs = addServiceAttrs;
        this.friendfamily = friendAndFamilyList;
        this.user = user;
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

        final String id = addServiceAttrs.get(position).getId();
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(friendfamily);
                alertDialogBuilder.setMessage("Are you sure to remove relation?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                        DatabaseReference databaseReference = firebaseDatabase.getReference();
                        databaseReference.child("Relations").child(uid).child(user).child(id).setValue(null);
                        dialog.dismiss();

                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();

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
        ImageView deleteBtn;
        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            deleteBtn = (ImageView) itemView.findViewById(R.id.imgDelete);
            name = (TextView) itemView.findViewById(R.id.txtName);


        }
    }
}
