package com.example.emergencyservices;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

public class HelperActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helper);
        SharedPreferences.Editor editor = getSharedPreferences("Log", MODE_PRIVATE).edit();
        editor.putBoolean("isLoggedIn", true );
        editor.putBoolean("cat", true );
        editor.commit();
    }
}
