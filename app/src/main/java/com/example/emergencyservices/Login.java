package com.example.emergencyservices;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatTextView;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.snackbar.Snackbar;

public class Login extends AppCompatActivity {
    Button btnLogin , helperLogin,btnRcoverPassword;
    TextView btnSignup;
    EditText email,password;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin=(Button) findViewById(R.id.login);
        helperLogin=(Button) findViewById(R.id.loginHelper);
        btnSignup=(TextView) findViewById(R.id.sigup);
        btnRcoverPassword=(Button) findViewById(R.id.forGetPassword);

        email=(EditText) findViewById(R.id.email);
        password=(EditText) findViewById(R.id.password);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Logging In..... ");
        final FirbaseAuthenticationClass firbaseAuthenticationClass=new FirbaseAuthenticationClass();
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,Signup.class));
                finish();
            }
        });

        btnRcoverPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,ForgetPassword.class);
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String EMAIL = email.getText().toString().trim();
                String PASSWORD = password.getText().toString().trim();
                if (!Patterns.EMAIL_ADDRESS.matcher(EMAIL).matches()){
                    email.setError("Invalid email");
                    email.setFocusable(true);
                }else {
                    progressDialog.show();
                    firbaseAuthenticationClass.LoginUser(EMAIL,PASSWORD, Login.this, progressDialog);

                }
            }
        });
        helperLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext() , HelperLogin.class));
            }
        });
    }

}
