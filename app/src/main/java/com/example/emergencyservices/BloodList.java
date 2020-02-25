package com.example.emergencyservices;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class BloodList extends BaseActivity {
    RecyclerView bloodlist;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    ArrayList<BloodAttr> bloodAttrs;
    BloodListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    //    setContentView(R.layout.activity_blood_list);

        bloodlist = findViewById(R.id.bloodList);
        bloodlist.setLayoutManager(new LinearLayoutManager(this));
        bloodAttrs = new ArrayList<BloodAttr>();
        databaseReference.child("BloodNeeder").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bloodAttrs.clear();
                //profiledata.clear();
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    BloodAttr p = dataSnapshot1.getValue(BloodAttr.class);
                    bloodAttrs.add(p);
                }
                Collections.reverse(bloodAttrs);
                bloodlist.setAdapter(new BloodListAdapter(bloodAttrs , getApplicationContext()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    int getContentViewId() {
        return R.layout.activity_blood_aactivity;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.nav_bloodDonation;
    }
}
