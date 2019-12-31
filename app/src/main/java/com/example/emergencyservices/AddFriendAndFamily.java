package com.example.emergencyservices;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class AddFriendAndFamily extends BaseActivity {
    EditText searchtext;
    ArrayList<UserAttr> pacakgeAttrs;
    String User;
    SearchListAdapter adapter;
    RecyclerView recyclerView;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //   setContentView(R.layout.activity_add_friend_and_family);
        Intent i = getIntent();
         User = i.getStringExtra("id");
        searchtext= findViewById(R.id.find);
        pacakgeAttrs = new ArrayList<UserAttr>();
        recyclerView=findViewById(R.id.searchList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        initTextListener();
    }

    @Override
    int getContentViewId() {
        return R.layout.activity_add_friend_and_family;
    }

    @Override
    int getNavigationMenuItemId() {
        if(User.equals("Friends"))
            return R.id.nav_frindesList;

        else
        return R.id.nav_familyList;
    }

    private void initTextListener() {
        pacakgeAttrs.clear();
        searchtext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = searchtext.getText().toString();
                searchForMatch(text);
            }

        });
    }

    private void searchForMatch(String text) {
        pacakgeAttrs.clear();
        updatePostList();
        if(text.length() ==0)
        {
            return;
        }

        else
        {

            Query query = FirebaseDatabase.getInstance().getReference("Users")
                    .orderByChild("name")
                    .startAt(text)
                    .endAt(text+"\uf8ff");
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot)
                {
                    pacakgeAttrs.clear();
                    for(DataSnapshot singleSnapshot :  dataSnapshot.getChildren())
                    {
                        if(!pacakgeAttrs.contains(singleSnapshot.getValue(UserAttr.class)))
                        {
                            pacakgeAttrs.add(singleSnapshot.getValue(UserAttr.class));
                        }

                    }
                    try{
                        updatePostList();
                    }
                    catch (Exception ex)
                    {

                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }
    }

    private void updatePostList() {
        recyclerView.setAdapter(new SearchListAdapter(pacakgeAttrs , getApplicationContext() , AddFriendAndFamily.this , User ));

    }
}
