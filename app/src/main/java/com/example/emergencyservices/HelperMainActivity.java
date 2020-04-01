package com.example.emergencyservices;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class HelperMainActivity extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    ArrayList<notificationAttr> pacakgeAttrs;
    NotificationAdapter adapter;
    RecyclerView recyclerView;
    CardView logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rescue);
        recyclerView=findViewById(R.id.nList);
        pacakgeAttrs = new ArrayList<notificationAttr>();
        logout = (CardView) findViewById(R.id.cardLogOut);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(HelperMainActivity.this);
                alertDialogBuilder.setMessage("Are you want to logout?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(HelperMainActivity.this , Login.class);
                        startActivity(intent);
                        finish();

                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final String id = getIntent().getStringExtra("id");
        Intent i=new Intent(this,HelperService.class);
        i.putExtra("id",id);
        startService(i);
        databaseReference.child("Notification").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pacakgeAttrs.clear();
                //profiledata.clear();
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    notificationAttr p = dataSnapshot1.getValue(notificationAttr.class);
                    pacakgeAttrs.add(p);
                }
                Collections.reverse(pacakgeAttrs);
                recyclerView.setAdapter(new HelperNotificationAdapter(pacakgeAttrs , HelperMainActivity.this , id));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
