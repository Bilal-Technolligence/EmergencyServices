package com.example.emergencyservices;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class BloodAactivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
      //  setContentView( R.layout.activity_blood_aactivity );
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
