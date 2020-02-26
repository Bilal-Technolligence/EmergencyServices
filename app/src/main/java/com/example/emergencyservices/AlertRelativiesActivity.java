package com.example.emergencyservices;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AlertRelativiesActivity extends BaseActivity {
    private LocationManager locationManager;
    FusedLocationProviderClient mFusedLocationClient;
    Double latitude = 0.0, longitude = 0.0;
    String provider ,lati, loni;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    ArrayList<UserAttr> pacakgeAttrs;
    FriendFamilyAdapter adapter;
    RecyclerView recyclerView;
    CardView btnFriends;
    EditText txtMessage;
    String message ,address1 ,name;
    LocationListener locationListener;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
       // setContentView( R.layout.activity_alert_relativies );
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location1) {
                        // Got last known location. In some rare situations this can be null.
                        if (location1 != null) {
                            // Logic to handle location object

                            longitude = location1.getLongitude();
                            latitude = location1.getLatitude();
                            lati = (String.valueOf(latitude));
                            loni = (String.valueOf(longitude));
                            //Toast.makeText(getApplicationContext() , lati + " " + loni ,  Toast.LENGTH_LONG).show();
                            Geocoder geoCoder = new Geocoder(AlertRelativiesActivity.this, Locale.getDefault());
                            StringBuilder builder = new StringBuilder();
                            try {
                                List<Address> address = geoCoder.getFromLocation(latitude, longitude, 1);
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
                                    //Toast.makeText(getApplicationContext() , address.get(0).getAddressLine(0) , Toast.LENGTH_LONG).show();
                                }
                                String finalAddress = builder.toString(); //This is the complete address.


//                                addressText.setText(address.get(0).getAddressLine(0)); //This will display the final address.
//                                addressString = address.get(0).getAddressLine(0);
                            } catch (IOException e) {
                                // Handle IOException
                            } catch (NullPointerException e) {
                                // Handle NullPointerException
                            }
                        } else {
                            Toast.makeText(getApplicationContext() ,"Please enable your location" , Toast.LENGTH_LONG).show();
                        }
                    }
                });
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            recreate();
            return;
        }


        pacakgeAttrs = new ArrayList<UserAttr>();
        recyclerView=findViewById(R.id.ffList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference.child("Users").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                name = dataSnapshot.child("name").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        databaseReference.child("Relations").child(uid).child("Family").orderByChild("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pacakgeAttrs.clear();
                //profiledata.clear();
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    UserAttr p = dataSnapshot1.getValue(UserAttr.class);
                    pacakgeAttrs.add(p);
                }

                recyclerView.setAdapter(new FFAdapter(pacakgeAttrs , getApplicationContext()));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        btnFriends = findViewById(R.id.btnFriends);
        txtMessage = findViewById(R.id.txtMessage);
        btnFriends.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                message = txtMessage.getText().toString();
                databaseReference.child("Relations").child(uid).child("Family").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds: dataSnapshot.getChildren()){
                            final String id = ds.child("id").getValue().toString();
                            final String push = FirebaseDatabase.getInstance().getReference().child("Notification").push().getKey();
                            notificationAttr notificationAttr = new notificationAttr();
                            notificationAttr.setId(uid);
                            notificationAttr.setLat(lati);
                            notificationAttr.setLon(loni);
                            notificationAttr.setAddress(address1);
                            notificationAttr.setMessage(message);
                            notificationAttr.setName(name);
                            notificationAttr.setStatus("Unread");
                            databaseReference.child("Notification").child(id).child(uid).setValue(notificationAttr);
                            Snackbar.make(v,"Alert Sent",Snackbar.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



            }
        } );

    }

    @Override
    int getContentViewId() {
        return R.layout.activity_alert_relativies;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.nav_home;
    }
}
