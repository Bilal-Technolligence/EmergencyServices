package com.example.emergencyservices;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class VictumHelpActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
       // setContentView( R.layout.activity_victum_help );
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
