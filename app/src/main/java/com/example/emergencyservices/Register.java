package com.example.emergencyservices;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.Calendar;

public class Register extends AppCompatActivity {
    CardView btnRegister;
    ProgressDialog progressDialog;
    ImageView profileImage;
    private Uri imagePath;
    int count=0;
    private String selection ="User";

    EditText name,contact,age,address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name=(EditText) findViewById(R.id.name);
        contact=(EditText) findViewById(R.id.contact);
        profileImage = (ImageView) findViewById(R.id.profileImage);
        age=(EditText) findViewById(R.id.age);
        btnRegister=(CardView) findViewById(R.id.register);
        address=(EditText) findViewById(R.id.address);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Registering..... ");
        final FirbaseAuthenticationClass firbaseAuthenticationClass=new FirbaseAuthenticationClass();
        final String Email=getIntent().getStringExtra("Email");
        final String Password=getIntent().getStringExtra("Password");
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 2);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = name.getText().toString().toUpperCase();
                String Age = age.getText().toString();
                String Contact = contact.getText().toString();
                String Address = address.getText().toString();

                if (!Name.matches("[a-zA-Z ]+" )){
                    name.setError("Enter Valid Name");
                    name.setFocusable(true);
                } else if (Age.equals("")){
                    age.setError("Enter Valid Age");
                    age.setFocusable(true);
                } else if (Contact.equals("")){
                    contact.setError("Enter Valid Contact Number");
                    contact.setFocusable(true);
                } else if (Address.equals("") ){
                    address.setError("Enter Valid Address");
                    address.setFocusable(true);
                } else if (count==0){
                    Snackbar.make(v, "Please Select Image", Snackbar.LENGTH_LONG).show();
                } else {
                    progressDialog.show();
                    firbaseAuthenticationClass.RegisterUser(Email, Password,Contact,Name,Age,Address,imagePath,selection,Register.this,progressDialog);


                }
            }
        });



    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(Register.this,Signup.class);
        startActivity(intent);
        finish();
    }
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==requestCode&&resultCode==resultCode
                &&data!=null && data.getData()!=null){

            imagePath=data.getData();
            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(),imagePath);
                profileImage.setImageBitmap(bitmap);
                count=1;

            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }
}
