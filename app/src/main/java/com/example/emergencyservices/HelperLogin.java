package com.example.emergencyservices;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class HelperLogin extends AppCompatActivity {
    EditText helperId, helperPassword;
    Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helper_login);
        helperId=(EditText) findViewById(R.id.id);
        helperPassword=(EditText) findViewById(R.id.password);
        buttonLogin=(Button) findViewById(R.id.login);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(helperId.getText().toString().equals("rescue") && helperPassword.getText().toString().equals("1122")){
                    startActivity(new Intent(getApplicationContext(),RescueActivity.class));
                }else if(helperId.getText().toString().equals("police") && helperPassword.getText().toString().equals("15")){}
            }
        });
    }
}
