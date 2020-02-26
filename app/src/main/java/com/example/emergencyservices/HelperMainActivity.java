package com.example.emergencyservices;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rescue);
        recyclerView=findViewById(R.id.nList);
        pacakgeAttrs = new ArrayList<notificationAttr>();

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
