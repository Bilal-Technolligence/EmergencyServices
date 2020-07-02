package com.example.emergencyservices;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DonateBlood extends BaseActivity {
    CardView button,btnCancel;
    Spinner blood;
    String type = "";
    final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    String name, phone, address,age ,email,img;
    TextView txtTime;
    CountDownTimer cTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_donate_blood);

        button = findViewById(R.id.confirm);
        blood = findViewById(R.id.spinnerBlood);
        txtTime = findViewById(R.id.time);
        txtTime.setVisibility(View.GONE);
        btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setVisibility(View.GONE);

        blood.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                type = "A+";
            }
        });
        databaseReference.child("Users").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                name = dataSnapshot.child("name").getValue().toString();
                phone = dataSnapshot.child("contact").getValue().toString();
                address = dataSnapshot.child("address").getValue().toString();
                age = dataSnapshot.child("age").getValue().toString();
                email = dataSnapshot.child("email").getValue().toString();
                img = dataSnapshot.child("imageUrl").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Toast.makeText(getApplicationContext() , "Request cancelled " ,  Toast.LENGTH_LONG).show();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cTimer = new CountDownTimer(7000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        btnCancel.setVisibility(View.VISIBLE);
                        txtTime.setVisibility(View.VISIBLE);
                        txtTime.setText("Message will send in " + millisUntilFinished / 1000 + " sec");
                    }
                    public void onFinish() {
                        btnCancel.setVisibility(View.GONE);
                        txtTime.setVisibility(View.GONE);
                final String push = FirebaseDatabase.getInstance().getReference().child("BloodNeeder").push().getKey();
                databaseReference.child("BloodNeeder").child(push).child("id").setValue(push);
                databaseReference.child("BloodNeeder").child(push).child("name").setValue(name);
                databaseReference.child("BloodNeeder").child(push).child("user").setValue(uid);
                databaseReference.child("BloodNeeder").child(push).child("phone").setValue(phone);
                databaseReference.child("BloodNeeder").child(push).child("address").setValue(address);
                databaseReference.child("BloodNeeder").child(push).child("age").setValue(age);
                databaseReference.child("BloodNeeder").child(push).child("email").setValue(email);
                databaseReference.child("BloodNeeder").child(push).child("imgurl").setValue(img);
                databaseReference.child("BloodNeeder").child(push).child("bloodgroup").setValue(type);

                Toast.makeText(getApplicationContext(),
                        "Thanks for donating blood.", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(DonateBlood.this ,BloodList.class ));
                    }
                };
                cTimer.start();
            }
        });
    }

    @Override
    int getContentViewId() {
        return R.layout.activity_donate_blood;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.nav_bloodDonation;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(cTimer!=null)
            cTimer.cancel();
    }
}
