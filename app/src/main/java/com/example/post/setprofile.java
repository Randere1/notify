package com.example.post;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.service.autofill.SaveInfo;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class setprofile extends AppCompatActivity {
    private FirebaseDatabase mFirebaseDatabase;
    private EditText usrname, position, names, county, country;
    private Button save;
    private CircleImageView circle;
    private FirebaseAuth mAuth,eAuth;
    private DatabaseReference Reff,contactsReff;
    private ProgressDialog progress;
    private Uri imageuri;
    SaveInfo saveInfo;
    final static int gallary_pick = 1;
    FirebaseStorage storage;
    StorageReference UserProfileImageRef;
    String uniqueId = UUID.randomUUID().toString();

    String currentUserId;
    String currentUserEId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setprofile);
        mAuth=FirebaseAuth.getInstance();
        currentUserId=mAuth.getCurrentUser().getUid();
        eAuth=FirebaseAuth.getInstance();
        currentUserEId=mAuth.getCurrentUser().getEmail();


        storage =FirebaseStorage.getInstance();
        UserProfileImageRef= storage.getReference().child("Profile Images");
        Reff= FirebaseDatabase.getInstance().getReference().child("users").child(currentUserId);
        contactsReff= FirebaseDatabase.getInstance().getReference().child("Contacts");

        usrname = (EditText) findViewById(R.id.set_username);
        position = (EditText) findViewById(R.id.set_position);
        names = (EditText) findViewById(R.id.set_fullname);
        country = (EditText) findViewById(R.id.set_country);
        county = (EditText) findViewById(R.id.set_county);
        save = (Button) findViewById(R.id.set_save);
        circle = (CircleImageView) findViewById(R.id.set_image);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAccSetupInfo();
            }
        });
        circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallaryin = new Intent();
                gallaryin.setAction(Intent.ACTION_GET_CONTENT);
                gallaryin.setType("image/*");
                startActivityForResult(gallaryin, gallary_pick);
            }
        });
        Reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    String  Image = dataSnapshot.child("ProfileImage").getValue().toString();

                    Picasso.get().load(Image).placeholder(R.drawable.profile).into(circle);


                }else {
                    Toast.makeText(setprofile.this, "please select profile image first" , Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    private void saveAccSetupInfo() {
        String Usernam = usrname.getText().toString();
        String Position = position.getText().toString();
        String Names = names.getText().toString();
        String Country = country.getText().toString();
        String County = county.getText().toString();

        if (TextUtils.isEmpty(Usernam)) {
            Toast.makeText(this, "please write your username", Toast.LENGTH_SHORT).show();
        }if (TextUtils.isEmpty(Position)) {
            Toast.makeText(this, "please fill your position", Toast.LENGTH_SHORT).show();
        }if (TextUtils.isEmpty(Names)) {
            Toast.makeText(this, "please write your full identification names", Toast.LENGTH_SHORT).show();
        }if (TextUtils.isEmpty(Country)) {
            Toast.makeText(this, "please write your country", Toast.LENGTH_SHORT).show();
        }if (TextUtils.isEmpty(County)) {
            Toast.makeText(this, "please write your county", Toast.LENGTH_SHORT).show();
        }
        else{
            HashMap userMap = new HashMap();
            userMap.put("username", Usernam);
            userMap.put("FullName", Names);
            userMap.put("Country", Country);
            userMap.put("County", County);
            userMap.put("Position", Position);
            userMap.put("uid", currentUserId);
            userMap.put("status", "Hey there");
            userMap.put("gender", "none");
            userMap.put("dob", "none");
            userMap.put("Hobby", "none");
            userMap.put("Education", "none");
            userMap.put("relationship status", "none");

            Reff.updateChildren(userMap).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        HashMap contact = new HashMap();
                        contact.put("Name", usrname);
                        contact.put("Status", "hey there");
                        contact.put("uid", currentUserId);
                        contactsReff.child(currentUserId).updateChildren(contact);
                        SendUserToMainActivity();
                        Toast.makeText(setprofile.this, "saved succesfully", Toast.LENGTH_LONG).show();
                        progress.dismiss();
                    } else{
                        String msg = task.getException().getMessage();
                        Toast.makeText(setprofile.this, "error occured" + msg, Toast.LENGTH_LONG).show();
                        progress.dismiss();
                    }
                }
            });



        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == gallary_pick && resultCode == RESULT_OK && data != null) {

            Uri ImageUri = data.getData();

            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .start(this);

        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode==RESULT_OK){

                final Uri resultUri = result.getUri();
                final StorageReference filepath =UserProfileImageRef.child( currentUserId + ".jpg");
                filepath.putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                final String downloadUri =uri.toString();
                                Reff.child("ProfileImage").setValue(downloadUri);
                                Toast.makeText(setprofile.this, "upload done", Toast.LENGTH_LONG).show();

                            }
                        });

                    }
                });
            }
        }
    }


    private void SendUserToMainActivity() {
        Intent menIntent=new Intent(setprofile .this,MainActivity.class);
        menIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(menIntent);
    }

}
