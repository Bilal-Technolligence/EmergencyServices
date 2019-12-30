package com.example.emergencyservices;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class FirbaseAuthenticationClass extends AppCompatActivity {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();



    public void LoginUser(String EMAIL, String PASSWORD, final Activity activity, final ProgressDialog progressDialog) {
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(EMAIL, PASSWORD)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            activity.startActivity(new Intent(activity, MainActivity.class));
                           activity.finish();
                            progressDialog.dismiss();

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(activity, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
    public void RegisterUser(final String Email, String Password, final String Contact, final String Name, final String Age, final String Address,final Uri imagePath, final Activity activity, final ProgressDialog progressDialog) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            final String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
                            FirebaseDatabase database= FirebaseDatabase.getInstance();

                            final DatabaseReference reference=database.getReference("Users");
                            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/" + FirebaseDatabase.getInstance().getReference().child("Users").push().getKey());
                            storageReference.putFile(imagePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                    while (!uriTask.isSuccessful());
                                    Uri downloadUri = uriTask.getResult();

                                    UserAttr userAttr=new UserAttr();
                                    userAttr.setName(Name);
                                    userAttr.setAge(Age);
                                    userAttr.setContact(Contact);
                                    userAttr.setEmail(Email);
                                    userAttr.setId(uid);
                                    userAttr.setAddress(Address);
                                    userAttr.setImageUrl(downloadUri.toString());
//
//
//
//                                    reference.child(uid).child( "Name" ).setValue(Name);
//                                    reference.child(uid).child( "Email" ).setValue(Email);
//                                    reference.child(uid).child( "Contact" ).setValue(Contact);
//                                    reference.child(uid).child( "Id" ).setValue(uid);
//                                    reference.child(uid).child( "Age" ).setValue(Age);
//                                    reference.child(uid).child( "Address" ).setValue(Address);
//                                    reference.child(uid).child( "ImageUrl" ).setValue(downloadUri.toString());
                                    reference.child(uid).setValue(userAttr);
                                    activity.startActivity(new Intent(activity, MainActivity.class));
                                    Toast.makeText(activity, "Account Created", Toast.LENGTH_SHORT).show();
                                    activity.finish();
                                    progressDialog.dismiss();

                                }
                            });



                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(activity, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
}
