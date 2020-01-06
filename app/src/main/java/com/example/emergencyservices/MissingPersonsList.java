package com.example.emergencyservices;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MissingPersonsList extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_missing_persons_list);
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
