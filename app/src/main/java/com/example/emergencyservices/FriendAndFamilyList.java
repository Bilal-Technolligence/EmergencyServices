package com.example.emergencyservices;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FriendAndFamilyList extends BaseActivity {
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    ArrayList<UserAttr> pacakgeAttrs;
    FriendFamilyAdapter adapter;
    String User;
    RecyclerView recyclerView;
    FloatingActionButton btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_friend_and_family_list);
        recyclerView=findViewById(R.id.ffList);
        Intent i = getIntent();
        User = i.getStringExtra("id");
        pacakgeAttrs = new ArrayList<UserAttr>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FriendAndFamilyList.this , AddFriendAndFamily.class);
                i.putExtra("id" , User);
                startActivity(i);
            }
        });
//        databaseReference.child("Relations").child(uid).child(user).child(Id).child("id").setValue(Id);

        databaseReference.child("Relations").child(uid).child(User).orderByChild("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pacakgeAttrs.clear();
                //profiledata.clear();
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    UserAttr p = dataSnapshot1.getValue(UserAttr.class);
                    pacakgeAttrs.add(p);
                }

                recyclerView.setAdapter(new FriendFamilyAdapter(pacakgeAttrs , getApplicationContext() , FriendAndFamilyList.this , User));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    int getContentViewId() {
        return R.layout.activity_friend_and_family_list;
    }

    @Override
    int getNavigationMenuItemId() {
        if(User.equals("Friends"))
            return R.id.nav_frindesList;

        else
            return R.id.nav_familyList;
    }
}
