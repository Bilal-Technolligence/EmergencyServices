package com.example.emergencyservices;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class VictumHelpActivity extends BaseActivity {
    Button alertFriends,alertRelativies,alertPolice,alertRescue,alertAmbulance,btnMissingPerson,btnMissingPersonList;
    DatabaseReference dref = FirebaseDatabase.getInstance().getReference();
    ImageView img;
    TextView txt;
    BottomNavigationView bottomNavigationView;
    ProgressBar prg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
       // setContentView( R.layout.activity_victum_help );

        alertFriends = (Button)findViewById( R.id.btnFridends );
        alertRelativies = (Button)findViewById( R.id.btnRelatives );
        alertPolice = (Button)findViewById( R.id.btnPolice );
        alertRescue = (Button)findViewById( R.id.btnRescue );
        alertAmbulance = (Button)findViewById( R.id.btnAbulsnce );
        btnMissingPerson = (Button)findViewById( R.id.btnMissingPerson );
        btnMissingPersonList = (Button)findViewById( R.id.btnMissingPersonList );
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigationView);
        img = (ImageView) findViewById( R.id.imgN );
        txt = (TextView) findViewById( R.id.txt );
        prg = (ProgressBar) findViewById(R.id.prg);
        prg.setVisibility(View.VISIBLE);
        img.setVisibility(View.GONE);
        txt.setVisibility(View.GONE);
        alertFriends.setVisibility(View.GONE);
        alertRelativies.setVisibility(View.GONE);
        alertPolice.setVisibility(View.GONE);
        alertRescue.setVisibility(View.GONE);
        alertAmbulance.setVisibility(View.GONE);
        btnMissingPerson.setVisibility(View.GONE);
        btnMissingPersonList.setVisibility(View.GONE);
        bottomNavigationView.setVisibility(View.GONE);
        SharedPreferences.Editor editor = getSharedPreferences("Log", MODE_PRIVATE).edit();
        editor.putBoolean("isLoggedIn", true );
        editor.putBoolean("cat", false );
        editor.commit();
        Intent i = new Intent(this , MyService.class);
        startService(i);
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        dref.child("Users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int status = Integer.parseInt(dataSnapshot.child("status").getValue().toString());
                if(status == 0){
//                    startActivity(new Intent(VictumHelpActivity.this,MainActivity.class));
//                    finish();
                    img.setVisibility(View.VISIBLE);
                    txt.setVisibility(View.VISIBLE);
                    alertFriends.setVisibility(View.GONE);
                    alertRelativies.setVisibility(View.GONE);
                    alertPolice.setVisibility(View.GONE);
                    alertRescue.setVisibility(View.GONE);
                    alertAmbulance.setVisibility(View.GONE);
                    btnMissingPerson.setVisibility(View.GONE);
                    btnMissingPersonList.setVisibility(View.GONE);
                    bottomNavigationView.setVisibility(View.GONE);
                    prg.setVisibility(View.GONE);
                }
                else {
                    alertFriends.setVisibility(View.VISIBLE);
                    alertRelativies.setVisibility(View.VISIBLE);
                    alertPolice.setVisibility(View.VISIBLE);
                    alertRescue.setVisibility(View.VISIBLE);
                    alertAmbulance.setVisibility(View.VISIBLE);
                    btnMissingPerson.setVisibility(View.VISIBLE);
                    btnMissingPersonList.setVisibility(View.VISIBLE);
                    bottomNavigationView.setVisibility(View.VISIBLE);
                    prg.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        alertFriends.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VictumHelpActivity.this,AlertFriendsActivity.class));

            }
        } );

        btnMissingPerson.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VictumHelpActivity.this,MissingPersonActivity.class));

            }
        } );
        alertRelativies.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VictumHelpActivity.this,AlertRelativiesActivity.class));

            }
        } );


        btnMissingPersonList.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VictumHelpActivity.this,MissingPersonsList.class));

            }
        } );

    }

    @Override
    int getContentViewId() {
         return R.layout.activity_victum_help;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.nav_home;
    }
}
