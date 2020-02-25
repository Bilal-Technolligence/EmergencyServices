package com.example.emergencyservices;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HelperActivity extends AppCompatActivity {
CardView logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helper);
        SharedPreferences.Editor editor = getSharedPreferences("Log", MODE_PRIVATE).edit();
        editor.putBoolean("isLoggedIn", true );
        editor.putBoolean("cat", true );
        editor.commit();
        logout = (CardView) findViewById(R.id.btnLogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences settings = getSharedPreferences("Log", MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.remove("isLoggedIn");
                editor.remove("cat");
                editor.commit();

                Intent intent = new Intent(HelperActivity.this , Login.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
