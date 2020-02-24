package com.example.emergencyservices;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class BloodAactivity extends BaseActivity {
    final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

    CardView donner,donateBlood;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
      //  setContentView( R.layout.activity_blood_aactivity );
        donner = findViewById(R.id.donner);
        donateBlood = findViewById(R.id.donateBlood);


        databaseReference.child("Users").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                name = dataSnapshot.child("name").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void dialogBox() {

        AlertDialog.Builder dialog = new AlertDialog.Builder( BloodAactivity.this );
        dialog.setCancelable( false );
        dialog.setTitle( "Please Enter Blood Group" );
        final EditText input = new EditText(BloodAactivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        dialog.setView(input); // uncomment this line
        dialog.setPositiveButton("Send",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String bloodGroup = input.getText().toString();

                        if (bloodGroup.equals("")) {
                            Toast.makeText(getApplicationContext(),
                                    "Please Enter BloodGroup", Toast.LENGTH_SHORT).show();
                                     dialogBox();

                        } else {
                            final String push = FirebaseDatabase.getInstance().getReference().child("BloodNeeder").push().getKey();
                            databaseReference.child("BloodNeeder").child(push).child("id").setValue(push);
                            databaseReference.child("BloodNeeder").child(push).child("name").setValue(name);
                            databaseReference.child("BloodNeeder").child(push).child("user").setValue(uid);
                            databaseReference.child("BloodNeeder").child(push).child("bloodgroup").setValue(bloodGroup);

                            Toast.makeText(getApplicationContext(),
                                    "Your Request Has Been Submitted", Toast.LENGTH_SHORT).show();

                        }
                    }



                });

        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(BloodAactivity.this, BloodAactivity.class));
                finish();
            }
        });
        dialog.show();
    }

    @Override
    int getContentViewId() {
        return R.layout.activity_blood_aactivity;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.nav_bloodDonation;
    }
}
