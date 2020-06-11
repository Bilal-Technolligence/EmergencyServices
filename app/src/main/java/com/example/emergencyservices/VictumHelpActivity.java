package com.example.emergencyservices;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class VictumHelpActivity extends BaseActivity {
    CardView alertFriends,alertRelativies,alertPolice,alertRescue,alertAmbulance,btnMissingPerson,btnMissingPersonList,btnlogOut;
    DatabaseReference dref = FirebaseDatabase.getInstance().getReference();
    ImageView img;
    TextView txt;
    BottomNavigationView bottomNavigationView;
    ProgressBar prg;
    private ViewGroup rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
       // setContentView( R.layout.activity_victum_help );
    //RelativeLayout relativeLayout = findViewById(R.id.victumlayout);
        rootView = (ViewGroup) findViewById(R.id.victumlayout);
    AnimationDrawable animationDrawable = (AnimationDrawable) rootView.getBackground();
    animationDrawable.setEnterFadeDuration(2000);
    animationDrawable.setExitFadeDuration(4000);
    animationDrawable.start();

        alertFriends = (CardView) findViewById( R.id.btnFridends );
        //btnlogOut = (CardView)findViewById( R.id.btnlogOut );
        alertRelativies = (CardView)findViewById( R.id.btnRelatives );
        alertPolice = (CardView)findViewById( R.id.btnPolice );
      //  alertRescue = (CardView)findViewById( R.id.btnRescue );
        alertAmbulance = (CardView)findViewById( R.id.btnAbulsnce );
        btnMissingPerson = (CardView)findViewById( R.id.btnMissingPerson );
        btnMissingPersonList = (CardView)findViewById( R.id.btnMissingPersonList );
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigationView);
        img = (ImageView) findViewById( R.id.imgN );
        txt = (TextView) findViewById( R.id.txt );
        prg = (ProgressBar) findViewById(R.id.prg);
        prg.setVisibility(View.VISIBLE);
        img.setVisibility(View.GONE);
        txt.setVisibility(View.GONE);
//        btnlogOut.setVisibility(View.GONE);
        alertFriends.setVisibility(View.GONE);
        alertRelativies.setVisibility(View.GONE);
        alertPolice.setVisibility(View.GONE);
      //  alertRescue.setVisibility(View.GONE);
        alertAmbulance.setVisibility(View.GONE);
        btnMissingPerson.setVisibility(View.GONE);
        btnMissingPersonList.setVisibility(View.GONE);
        bottomNavigationView.setVisibility(View.GONE);
        SharedPreferences.Editor editor = getSharedPreferences("Log", MODE_PRIVATE).edit();
        editor.putBoolean("isLoggedIn", true );
        editor.putBoolean("cat", false );
        editor.commit();
        Intent i = new Intent(this , MyService.class);
        startService(i);
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        dref.child("Users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    int status = Integer.parseInt(dataSnapshot.child("status").getValue().toString());
                    if (status == 0) {
//                    startActivity(new Intent(VictumHelpActivity.this,MainActivity.class));
//                    finish();
                        img.setVisibility(View.VISIBLE);
                        txt.setVisibility(View.VISIBLE);
                       // btnlogOut.setVisibility(View.VISIBLE);
                        alertFriends.setVisibility(View.GONE);
                        alertRelativies.setVisibility(View.GONE);
                        alertPolice.setVisibility(View.GONE);
                    //    alertRescue.setVisibility(View.GONE);
                        alertAmbulance.setVisibility(View.GONE);
                        btnMissingPerson.setVisibility(View.GONE);
                        btnMissingPersonList.setVisibility(View.GONE);
                        bottomNavigationView.setVisibility(View.GONE);
                        prg.setVisibility(View.GONE);
                    } else {
                        alertFriends.setVisibility(View.VISIBLE);
                        alertRelativies.setVisibility(View.VISIBLE);
                        alertPolice.setVisibility(View.VISIBLE);
                      //  alertRescue.setVisibility(View.VISIBLE);
                        alertAmbulance.setVisibility(View.VISIBLE);
                        btnMissingPerson.setVisibility(View.VISIBLE);
                        btnMissingPersonList.setVisibility(View.VISIBLE);
                      //  btnlogOut.setVisibility(View.VISIBLE);
                        bottomNavigationView.setVisibility(View.VISIBLE);
                        prg.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        alertPolice.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i =new Intent(VictumHelpActivity.this,AlertHelper.class);
//                i.putExtra("id" , "police");
//                startActivity(i);
//            }
//        });
        alertPolice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i =new Intent(VictumHelpActivity.this,AlertHelper.class);
//                i.putExtra("id" , "rescue");
//                startActivity(i);

                    LayoutInflater layoutInflater = LayoutInflater.from(VictumHelpActivity.this);
                    View promptView = layoutInflater.inflate(R.layout.check, null);

                    final AlertDialog alertD = new AlertDialog.Builder(VictumHelpActivity.this).create();

                    final CheckBox ch1 = (CheckBox) promptView.findViewById(R.id.checkboxpolice);
                final CheckBox ch2 = (CheckBox) promptView.findViewById(R.id.checkboxrescue);
                    Button btnAlert = (Button) promptView.findViewById(R.id.btnAlertBoth);
                btnAlert.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            try {
                                if(ch1.isChecked() && ch2.isChecked()){
                                    Intent i =new Intent(VictumHelpActivity.this,AlertHelper.class);
                                    i.putExtra("id" , "police");
                                    i.putExtra("id2" , "rescue");
                                    startActivity(i);
                                } else if(ch1.isChecked())
                                {
                                    Intent i =new Intent(VictumHelpActivity.this,AlertHelper.class);
                                    i.putExtra("id" , "police");
                                   // i.putExtra("idRescue" , "rescue");
                                    startActivity(i);


                                } else if(ch2.isChecked())
                                {
                                    Intent i =new Intent(VictumHelpActivity.this,AlertHelper.class);
                                   // i.putExtra("idPolice" , "police");
                                     i.putExtra("id" , "rescue");
                                    startActivity(i);

                                }
                                else {
                                    Toast.makeText(VictumHelpActivity.this, "CheckBox cannot be empty", Toast.LENGTH_SHORT).show();

                                }


                            }catch(Exception e) {
                                Toast.makeText(getApplicationContext(),"Unable to Connect Try Again...",
                                        Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                            }
                        }
                    });


                    alertD.setView(promptView);

                    alertD.show();

                }



        });
        alertAmbulance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(VictumHelpActivity.this,AlertHelper.class);
                i.putExtra("id" , "ambulance");
                startActivity(i);
            }
        });
        alertFriends.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VictumHelpActivity.this,AlertFriendsActivity.class));

            }
        } );

        btnMissingPerson.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VictumHelpActivity.this,MissingPersonActivity.class));

            }
        } );
        alertRelativies.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VictumHelpActivity.this,AlertRelativiesActivity.class));

            }
        } );


        btnMissingPersonList.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VictumHelpActivity.this,MissingPersonsList.class));

            }
        } );

//        btnlogOut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SharedPreferences settings = getSharedPreferences("Log", MODE_PRIVATE);
//                SharedPreferences.Editor editor = settings.edit();
//                editor.remove("isLoggedIn");
//                editor.remove("cat");
//                editor.commit();
//
//                Intent intent = new Intent(VictumHelpActivity.this , Login.class);
//                startActivity(intent);
//                finish();
//            }
//        });

    }

    @Override
    int getContentViewId() {
         return R.layout.activity_victum_help;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.nav_home;
    }
}
