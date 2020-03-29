package com.example.emergencyservices;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AlertHelper extends BaseActivity {
EditText message;
CardView alert;
DatabaseReference dref = FirebaseDatabase.getInstance().getReference();
    LocationManager locationManager;
    LocationListener locationListener;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_alert_helper);
        final String helper = getIntent().getStringExtra("id");
        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);
        message = findViewById(R.id.txtMessage);
        alert = findViewById(R.id.btnHelper);
        final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                try {
                    final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    final DatabaseReference dref = FirebaseDatabase.getInstance().getReference();
                    final String push = FirebaseDatabase.getInstance().getReference().child("Notification").push().getKey();
                    locationManager = (LocationManager) getSystemService( LOCATION_SERVICE );
                    locationListener = new LocationListener() {
                        @RequiresApi(api = Build.VERSION_CODES.P)
                        @Override
                        public void onLocationChanged(Location location) {

                            dref.child("Notification").child(helper).child(push).child("lon").setValue(String.valueOf(location.getLongitude()));
                            dref.child("Notification").child(helper).child(push).child("lat").setValue(String.valueOf(location.getLatitude()));
                            dref.child("Notification").child(helper).child(push).child("message").setValue(message.getText().toString());
                            dref.child("Notification").child(helper).child(push).child("status").setValue("Unread");
                            dref.child("Notification").child(helper).child(push).child("id").setValue(push);
                            // Toast.makeText(getApplicationContext(), "location change", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);

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
            }
        });

    }

    @Override
    int getContentViewId() {
        return R.layout.activity_alert_helper;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.nav_home;
    }
}
