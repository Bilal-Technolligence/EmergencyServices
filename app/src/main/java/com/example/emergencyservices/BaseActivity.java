package com.example.emergencyservices;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public abstract class BaseActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    protected BottomNavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());


        navigationView = (BottomNavigationView) findViewById(R.id.navigationView);
        navigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateNavigationBarState();
    }
    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.nav_home) {
            startActivity(new Intent(this, VictumHelpActivity.class));
        } else if (itemId == R.id.nav_frindesList) {
            Intent intent=new Intent(this, FriendAndFamilyList.class);
            intent.putExtra( "id","Friends" );
            startActivity(intent);
        } else if (itemId == R.id.nav_familyList) {
            Intent intent=new Intent(this, FriendAndFamilyList.class);
            intent.putExtra( "id","Family" );
            startActivity(intent);
        }
        else if (itemId == R.id.nav_bloodDonation) {
            startActivity(new Intent(this, BloodAactivity.class));
        }
        else if (itemId == R.id.nav_reportCrime) {
            startActivity(new Intent(this, MainActivity.class));
        }

        return true;
    }
    private void updateNavigationBarState(){
        int actionId = getNavigationMenuItemId();
        selectBottomNavigationBarItem(actionId);
    }

    void selectBottomNavigationBarItem(int itemId) {
        Menu menu = navigationView.getMenu();
        for (int i = 0, size = menu.size(); i < size; i++) {
            MenuItem item = menu.getItem(i);
            boolean shouldBeChecked = item.getItemId() == itemId;
            if (shouldBeChecked) {
                item.setChecked(true);
                break;
            }
        }
    }

    abstract int getContentViewId();

    abstract int getNavigationMenuItemId();




}
