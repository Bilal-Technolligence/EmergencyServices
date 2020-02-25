package com.example.emergencyservices;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_splash_screen );
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            recreate();
            return;
        }
        ImageView imageView = findViewById( R.id.imagelogo );
        Animation animation = AnimationUtils.loadAnimation( getApplicationContext(),R.anim.fade );
        imageView.startAnimation( animation );

        Thread timer = new Thread(  ) {
            @Override
            public void run() {
                try {
                    sleep( 4000 );

                    SharedPreferences prefs = getSharedPreferences("Log", MODE_PRIVATE);
                    boolean isLoggedIn = prefs.getBoolean("isLoggedIn", false);
                    boolean cat = prefs.getBoolean("cat", false);
                    if (isLoggedIn) {
                        if(cat){
                            Intent intent = new Intent(getApplicationContext(), HelperActivity.class );
                            startActivity( intent );
                            finish();
                        }
                        else{
                            Intent intent = new Intent(getApplicationContext(), VictumHelpActivity.class );
                            startActivity( intent );
                            finish();
                        }
                    } else {
                        Intent intent = new Intent(getApplicationContext(), Login.class );
                        startActivity( intent );
                        finish();
                    }



                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        timer.start();
    }
}
