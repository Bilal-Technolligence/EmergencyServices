package com.example.emergencyservices;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DirectionOnMap2 extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String longitude , latitude , id,user;
    double LocLongitude;
    double LocLatitude;
    MarkerOptions destination;
    DatabaseReference dref = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction_on_map2);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        user = intent.getStringExtra("user");


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        dref.child("Notification").child(user).child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mMap.clear();
                latitude = dataSnapshot.child("lat").getValue().toString();
                longitude = dataSnapshot.child("lon").getValue().toString();
                destination = new MarkerOptions().position(new LatLng(Double.parseDouble(latitude) , Double.parseDouble(longitude)));
                LocLatitude = Double.parseDouble(latitude);
                LocLongitude = Double.parseDouble(longitude);
                mMap.addMarker(new MarkerOptions().position(new LatLng(LocLatitude , LocLongitude))).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));

                LatLng loc = new LatLng(LocLatitude, LocLongitude);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
                CameraPosition camPos = new CameraPosition.Builder()
                        .target(loc)
                        .zoom(20)
                        .build();
                CameraUpdate camUpd3 = CameraUpdateFactory.newCameraPosition(camPos);
                mMap.animateCamera(camUpd3);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }
}
