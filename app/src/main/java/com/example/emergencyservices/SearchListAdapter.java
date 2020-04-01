package com.example.emergencyservices;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.security.AccessControlContext;
import java.util.ArrayList;

public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.ViewHolder> {
    ArrayList<UserAttr> addServiceAttrs;
    private Context context;
    Activity Addfriendfamily;
    String user;
    Activity activity;
    public SearchListAdapter(Activity activity,ArrayList<UserAttr> addServiceAttrs, Context context, AddFriendAndFamily addFriendAndFamily, String user){
        this.context=context;
        this.activity=activity;
        this.addServiceAttrs = addServiceAttrs;
        this.Addfriendfamily = addFriendAndFamily;
        this.user = user;
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
                final  String Id = addServiceAttrs.get(position).getId();
                //Toast.makeText(context , Id , Toast.LENGTH_SHORT).show();

                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Addfriendfamily);
                alertDialogBuilder.setMessage("Are you sure to add in "+user+"?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                        DatabaseReference databaseReference = firebaseDatabase.getReference();
                        databaseReference.child("Relations").child(uid).child(user).child(Id).child("id").setValue(Id);
                        databaseReference.child("Relations").child(uid).child(user).child(Id).child("name").setValue(addServiceAttrs.get(position).getName());
                        Toast.makeText(context , "Relation added" , Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        Intent intent=new Intent(activity,FriendAndFamilyList.class);
                        intent.putExtra("id" , user);
                        activity.startActivity(intent);
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
//                Intent i = new Intent(context , FriendAndFamilyList.class);
//                i.putExtra("id" , user);
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
