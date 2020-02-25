package com.example.emergencyservices;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

public class MyLocation extends Service {
    LocationManager locationManager;
    LocationListener locationListener;
    String id , uid;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onCreate() {
        super.onCreate();

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        id = intent.getStringExtra("id");
        uid = intent.getStringExtra("uid");
        try {
            final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            final DatabaseReference dref = FirebaseDatabase.getInstance().getReference();
            locationManager = (LocationManager) getSystemService( LOCATION_SERVICE );
            locationListener = new LocationListener() {
                @RequiresApi(api = Build.VERSION_CODES.P)
                @Override
                public void onLocationChanged(Location location) {
                    dref.child("Notification").child(id).child(uid).child("lon").setValue(location.getLongitude());
                    dref.child("Notification").child(id).child(uid).child("lat").setValue(location.getLatitude());
                    // Toast.makeText(getApplicationContext(), "location change", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                    // Toast.makeText(getApplicationContext(), "status change", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onProviderEnabled(String provider) {
                    // Toast.makeText(getApplicationContext(), "provider enable", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onProviderDisabled(String provider) {
                    //  Toast.makeText(getApplicationContext(), "provider disable", Toast.LENGTH_SHORT).show();
//                    Intent i = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS );
//                    startActivity( i );

                }
            };

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    Activity#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for Activity#requestPermissions for more details.
                }
            }
            locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
