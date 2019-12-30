package com.example.emergencyservices;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddFriendAndFamily extends AppCompatActivity {
    EditText searchtext;
    //ArrayList<AddPacakgeAttr> pacakgeAttrs;
    SearchListAdapter adapter;
    ListView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend_and_family);
        searchtext= findViewById(R.id.find);
        //pacakgeAttrs = new ArrayList<AddPacakgeAttr>();
        recyclerView=findViewById(R.id.searchList);

        initTextListener();
    }

    private void initTextListener() {
        //pacakgeAttrs.clear();
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
        //pacakgeAttrs.clear();
        updatePostList();
        if(text.length() ==0)
        {
            return;
        }

        else
        {

            Query query = FirebaseDatabase.getInstance().getReference("Packages")
                    .orderByChild("packageType")
                    .startAt(text)
                    .endAt(text+"\uf8ff");
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot)
                {
                    //pacakgeAttrs.clear();
                    for(DataSnapshot singleSnapshot :  dataSnapshot.getChildren())
                    {
//                        if(!pacakgeAttrs.contains(singleSnapshot.getValue(AddPacakgeAttr.class)))
//                        {
//                            pacakgeAttrs.add(singleSnapshot.getValue(AddPacakgeAttr.class));
//                        }

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
    }
}
