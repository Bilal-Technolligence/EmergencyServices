package com.example.emergencyservices;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FriendAndFamilyList extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    //ArrayList<AddPacakgeAttr> pacakgeAttrs;
    FriendFamilyAdapter adapter;
    ListView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_and_family_list);
        recyclerView=findViewById(R.id.searchList);
        //addServiceAttrs = new ArrayList<AddServiceAttr>();
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseReference.child("Services").orderByChild("rating").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //addServiceAttrs.clear();
                //profiledata.clear();
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
//                    AddServiceAttr p = dataSnapshot1.getValue(AddServiceAttr.class);
//                    addServiceAttrs.add(p);
                }

                //recyclerView.setAdapter(new ViewServiceAdapter(addServiceAttrs , getApplicationContext()));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
