package com.example.emergencyservices;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MissingPersonsList extends BaseActivity {
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    ArrayList<MissingPersonAttributes> pacakgeAttrs;
    MissingPersonsList adapter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_missing_persons_list);
        recyclerView=findViewById(R.id.ffList);
        pacakgeAttrs = new ArrayList<MissingPersonAttributes>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseReference.child("MissingPerson").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pacakgeAttrs.clear();
                //profiledata.clear();
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    MissingPersonAttributes p = dataSnapshot1.getValue(MissingPersonAttributes.class);
                    pacakgeAttrs.add(p);
                }

                recyclerView.setAdapter(new MissingList(pacakgeAttrs ,MissingPersonsList.this ));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    int getContentViewId() {
        return R.layout.activity_missing_persons_list;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.nav_home;
    }
}
