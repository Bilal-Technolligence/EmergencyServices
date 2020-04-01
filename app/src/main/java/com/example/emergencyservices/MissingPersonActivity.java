package com.example.emergencyservices;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class MissingPersonActivity extends BaseActivity {
    CardView btnSubmit;

    ImageView profileImage;
    private Uri imagePath;
    int count = 0;
    private String selection;
    private StorageReference StorageRef;
    EditText name, age, address, relation, fName;
    ProgressDialog progressDialog;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Report Missing Person");
        //  setContentView(R.layout.activity_missing_person);

        name = (EditText) findViewById(R.id.name);
        profileImage = (ImageView) findViewById(R.id.profileImage);
        age = (EditText) findViewById(R.id.age);
        btnSubmit = (CardView) findViewById(R.id.submitData);
        address = (EditText) findViewById(R.id.address);
        relation = (EditText) findViewById(R.id.relation);
        fName = (EditText) findViewById(R.id.fName);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Submitting..... ");

        StorageRef = FirebaseStorage.getInstance().getReference();
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 2);
            }
        });


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Name = name.getText().toString().toUpperCase();
                String fatherName = fName.getText().toString();
                String Age = age.getText().toString();
                String Address = address.getText().toString();
                String Relation = relation.getText().toString();

                if (!Name.matches("^[A-Za-z ]+$") ) {
                    name.setError("Please fill name(e.g Ali)");
                    name.setFocusable(true);
                } else if (Age.equals("")) {
                    age.setError("This field cannot be empty");
                    age.setFocusable(true);
                } else if (!fatherName.matches("^[A-Za-z ]+$")) {
                    fName.setError("Please fill fname(e.g Ali)");
                    fName.setFocusable(true);
                } else if (Address.equals("")) {
                    address.setError("Please fill (e.g Rwp)");
                    address.setFocusable(true);
                } else if (!Relation.matches("^[A-Za-z ]+$")) {
                    relation.setError("Please fill (e.g Friend)");
                    relation.setFocusable(true);
                } else if (count == 0) {
                    Snackbar.make(v, "Please Select Image", Snackbar.LENGTH_LONG).show();
                } else {
                    progressDialog.show();

                    if (count == 1) {
                            final String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            final String push = FirebaseDatabase.getInstance().getReference().child("Missing").push().getKey();
                            StorageReference fileReference = StorageRef.child("images/" + push);
                            fileReference.putFile(imagePath)
                                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            //Toast.makeText(getApplicationContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                                            if (imagePath != null) {
                                                MissingPersonAttributes missingPerson = new MissingPersonAttributes();
                                                missingPerson.setUid(id);
                                                missingPerson.setId(push);
                                                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                                while (!uriTask.isSuccessful());
                                                Uri downloadUri = uriTask.getResult();
                                                databaseReference.child("MissingPerson").child(push).child("id").setValue(push);
                                                databaseReference.child("MissingPerson").child(push).child("imgurl").setValue(downloadUri.toString());
                                                databaseReference.child("MissingPerson").child(push).child("name").setValue(name.getText().toString());
                                                databaseReference.child("MissingPerson").child(push).child("fatherName").setValue(fName.getText().toString());
                                                databaseReference.child("MissingPerson").child(push).child("age").setValue(age.getText().toString());
                                                databaseReference.child("MissingPerson").child(push).child("address").setValue(address.getText().toString());
                                                databaseReference.child("MissingPerson").child(push).child("uid").setValue(id);
                                                databaseReference.child("MissingPerson").child(push).child("relation").setValue(relation.getText().toString());
//
                                                Toast.makeText(getApplicationContext(), "Inserted", Toast.LENGTH_LONG).show();

                                                progressDialog.dismiss();
                                            }
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getApplicationContext(), "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });


                    }else {
                        Toast.makeText(getApplicationContext(), "Please upload an image.", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }

    @Override
    int getContentViewId() {
        return R.layout.activity_missing_person;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.nav_home;
    }
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == requestCode && resultCode == resultCode
                && data != null && data.getData() != null) {

            imagePath = data.getData();
            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), imagePath);
                profileImage.setImageBitmap(bitmap);
                count = 1;

            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }
}
