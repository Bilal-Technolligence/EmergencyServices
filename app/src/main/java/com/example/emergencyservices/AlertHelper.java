package com.example.emergencyservices;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class AlertHelper extends BaseActivity {
EditText message;
CardView alert;
DatabaseReference dref = FirebaseDatabase.getInstance().getReference();
    LocationManager locationManager;
    LocationListener locationListener;
    ProgressBar progressBar;
    String name, address1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_alert_helper);
        final String helper = getIntent().getStringExtra("id");
        final String helper2 = getIntent().getStringExtra("id2");
        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);
        message = findViewById(R.id.txtMessage);
        alert = findViewById(R.id.btnHelper);
        final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        dref.child("Users").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                name = dataSnapshot.child("name").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                try {
                    final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    final DatabaseReference dref = FirebaseDatabase.getInstance().getReference();
                    final String push = FirebaseDatabase.getInstance().getReference().child("Notification").push().getKey();
                    dref.child("Notification").child(helper).child(push).child("status").setValue("Unread");
                    if(helper2 !=null) {
                        dref.child("Notification").child(helper2).child(push).child("status").setValue("Unread");
                    }

                    locationManager = (LocationManager) getSystemService( LOCATION_SERVICE );
                    locationListener = new LocationListener() {
                        @RequiresApi(api = Build.VERSION_CODES.P)
                        @Override
                        public void onLocationChanged(Location location) {
                            Geocoder geoCoder = new Geocoder(AlertHelper.this, Locale.getDefault());
                            StringBuilder builder = new StringBuilder();
                            try {
                                List<Address> address = geoCoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                int maxLines = address.get(0).getMaxAddressLineIndex();
                                for (int i = 0; i < maxLines; i++) {
                                    String addressStr = address.get(0).getAddressLine(i);
                                    builder.append(addressStr);
                                    builder.append(" ");
                                }
                                if (address.size() > 0) {
                                    System.out.println(address.get(0).getLocality());
                                    System.out.println(address.get(0).getCountryName());
                                    address1 = address.get(0).getAddressLine(0);
                                    // Toast.makeText(getApplicationContext() , address.get(0).getAddressLine(0) , Toast.LENGTH_LONG).show();
                                }
                                String finalAddress = builder.toString(); //This is the complete address.


//                                addressText.setText(address.get(0).getAddressLine(0)); //This will display the final address.
//                                addressString = address.get(0).getAddressLine(0);
                            } catch (IOException e) {
                                // Handle IOException
                                //                                addressText.setText(address.get(0).getAddressLine(0)); //This will display the final address.


                            }
                            dref.child("Notification").child(helper).child(push).child("lon").setValue(String.valueOf(location.getLongitude()));
                            dref.child("Notification").child(helper).child(push).child("lat").setValue(String.valueOf(location.getLatitude()));
                            dref.child("Notification").child(helper).child(push).child("address").setValue(address1);
                            dref.child("Notification").child(helper).child(push).child("name").setValue(name);
                            dref.child("Notification").child(helper).child(push).child("message").setValue(message.getText().toString());
                            dref.child("Notification").child(helper).child(push).child("id").setValue(push);


                            if(helper2 !=null){

                                dref.child("Notification").child(helper2).child(push).child("lon").setValue(String.valueOf(location.getLongitude()));
                                dref.child("Notification").child(helper2).child(push).child("lat").setValue(String.valueOf(location.getLatitude()));
                                dref.child("Notification").child(helper2).child(push).child("address").setValue(address1);
                                dref.child("Notification").child(helper2).child(push).child("name").setValue(name);
                                dref.child("Notification").child(helper2).child(push).child("message").setValue(message.getText().toString());
                                dref.child("Notification").child(helper2).child(push).child("id").setValue(push);
                            }

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



                Snackbar.make(v,"Alert Sent",Snackbar.LENGTH_LONG).show();
                startActivity(new Intent(AlertHelper.this,VictumHelpActivity.class));
               // Toast.makeText(getApplicationContext(), "You alert has sent", Toast.LENGTH_LONG).show();
                finish();
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
