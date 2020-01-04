package com.example.emergencyservices;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class VictumHelpActivity extends BaseActivity {
    Button alertFriends,alertRelativies,alertPolice,alertRescue,alertAmbulance,btnMissingPerson;

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
        SharedPreferences.Editor editor = getSharedPreferences("Log", MODE_PRIVATE).edit();
        editor.putBoolean("isLoggedIn", true );
        editor.commit();
        Intent i = new Intent(this , MyService.class);
        startService(i);

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
