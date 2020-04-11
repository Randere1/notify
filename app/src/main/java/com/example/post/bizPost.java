package com.example.post;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;

public class bizPost extends AppCompatActivity {
    private Button UpdatePostButton;
    private EditText PostDescriptin,postname,postplace,Postprice,call;
    private String Description,pname,pplace,price,contact;
    private TextView hint;
    private ImageButton postimage,postimagei;
    private FrameLayout frame;
    private ScrollView scrollView;
    private Toolbar mtoolbar;
    final static int gallary_pick = 1;
    private Uri ImageUri;
    private String saveCurrentDate,SaveCurrentTime,PostRandomName,downloadUri;
    FirebaseStorage storage;
    StorageReference storagereffi;
    private DatabaseReference Bizpostreff;
    private DatabaseReference Reff;
    private FirebaseAuth mAuth;
    String currentUserId;
    String uniqueId = UUID.randomUUID().toString();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biz_post);

        UpdatePostButton=findViewById(R.id.biz_Post_button);
        PostDescriptin=findViewById(R.id.biz_moredisc);
        postname=findViewById(R.id.biz_something);
        postplace=findViewById(R.id.biz_place);
        Postprice=findViewById(R.id.biz_price);
        hint=findViewById(R.id.biz_click);
        postimage=findViewById(R.id.biz_image);
        postimagei=findViewById(R.id.biz_imagei);
        frame=findViewById(R.id.biz_i);
        scrollView=findViewById(R.id.biz_scroll);
        mtoolbar=findViewById(R.id.biz_post_bar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        mAuth= FirebaseAuth.getInstance();
        currentUserId=mAuth.getCurrentUser().getUid();
        storage =FirebaseStorage.getInstance();
        storagereffi= storage.getReference().child("Biz post pic");
        Bizpostreff = FirebaseDatabase.getInstance().getReference().child("Biz Post");
        Reff= FirebaseDatabase.getInstance().getReference().child("users");

        UpdatePostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storingDatatoFirebase();
            }
        });


        postimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                postimagei.setVisibility(View.VISIBLE);
                postimagei.setEnabled(true);
                openGallary();
            }
        });

    }

    private void storingDatatoFirebase() {
        if (ImageUri != null) {
            Calendar calFordDate = Calendar.getInstance();
            SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
            saveCurrentDate = currentDate.format(calFordDate.getTime());

            Calendar calFordTime = Calendar.getInstance();
            SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
            SaveCurrentTime = currentTime.format(calFordTime.getTime());

            PostRandomName = saveCurrentDate + SaveCurrentTime;

            final StorageReference filepath = storagereffi.child(uniqueId + PostRandomName + ".jpg");
            filepath.putFile(ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            final String downloadUri =uri.toString();
                            Bizpostreff.child(uniqueId +PostRandomName).child("Pic").setValue(downloadUri);
                            Toast.makeText(bizPost.this, "upload done", Toast.LENGTH_LONG).show();
                            SavePicPostInfo();

                        }
                    });
                }
            });


        }else{
            SavePicPostInfo();

        }

    }

    private void SavePicPostInfo() {
        Reff.child(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String  full = dataSnapshot.child("FullName").getValue().toString();
                    String  Image = dataSnapshot.child("ProfileImage").getValue().toString();

                    pname  =  postname.getText().toString();
                    pplace  =  postplace.getText().toString();
                    price  =    Postprice.getText().toString();
                    Description = PostDescriptin.getText().toString();

                    if (TextUtils.isEmpty( pname)) {
                        Toast.makeText(bizPost.this, "please write products name", Toast.LENGTH_LONG).show();
                    }
                    if (TextUtils.isEmpty(  pplace )) {
                        Toast.makeText(bizPost.this, "please write place ", Toast.LENGTH_LONG).show();

                    } if (TextUtils.isEmpty(  price)) {
                        Toast.makeText(bizPost.this, "please write price", Toast.LENGTH_LONG).show();
                    }
                    else{
                        HashMap picpostmap = new HashMap();
                        picpostmap.put("uid", currentUserId);
                        picpostmap.put("date", saveCurrentDate);
                        picpostmap.put("time",SaveCurrentTime);
                        picpostmap.put("description", Description);
                        picpostmap.put("saleVenue", pplace);
                        picpostmap.put("productName", pname);
                        picpostmap.put("value", price);
                        picpostmap.put("fullName", full);
                        picpostmap.put("ProfileImage", Image);
                        Bizpostreff.child(uniqueId +PostRandomName).updateChildren(picpostmap).addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                if (task.isSuccessful()) {
                                    SendUserToBiz();
                                    Toast.makeText(bizPost.this, "saved succesfully", Toast.LENGTH_LONG).show();

                                } else{
                                    String msg = task.getException().getMessage();
                                    Toast.makeText(bizPost.this, "error occured" + msg, Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void SendUserToBiz() {

        Intent b=new Intent(bizPost.this,MainActivity.class);
        startActivity(b);
    }

    private void openGallary() {

        Intent gallaryin = new Intent();
        gallaryin.setAction(Intent.ACTION_GET_CONTENT);
        gallaryin.setType("image/*");
        startActivityForResult(gallaryin, gallary_pick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == gallary_pick && resultCode == RESULT_OK && data != null) {

            ImageUri = data.getData();

            Picasso.get().load(ImageUri).fit().centerCrop().into(postimagei);
        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home ){
            SendUserToMain();
        }
        return super.onOptionsItemSelected(item);
    }

    private void SendUserToMain() {
        Intent e=new Intent(bizPost.this,MainActivity.class);
        startActivity(e);
    }
}


        /*
        UpdatePostButton=findViewById(R.id.biz_Post_button);
        PostDescriptin=findViewById(R.id.biz_moredisc);
        postname=findViewById(R.id.biz_something);
        postplace=findViewById(R.id.biz_place);
        call=findViewById(R.id.biz_contact);
        Postprice=findViewById(R.id.biz_price);
        hint=findViewById(R.id.biz_click);
        postimage=findViewById(R.id.biz_image);
        postimagei=findViewById(R.id.biz_imagei);
        frame=findViewById(R.id.biz_i);
        scrollView=findViewById(R.id.biz_scroll);
        mtoolbar=findViewById(R.id.biz_post_bar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        mAuth=FirebaseAuth.getInstance();
        currentUserId=mAuth.getCurrentUser().getUid();
        storage =FirebaseStorage.getInstance();
        storagereffi= storage.getReference().child("Biz post pic");
        Bizpostreff = FirebaseDatabase.getInstance().getReference().child("Biz Post");
        Reff= FirebaseDatabase.getInstance().getReference().child("users").child(currentUserId);

        UpdatePostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storingDatatoFirebase();
            }
        });


        postimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                postimagei.setVisibility(View.VISIBLE);
                postimagei.setEnabled(true);
                openGallary();
            }
        });

    }

    private void storingDatatoFirebase() {
        if (ImageUri != null) {
            Calendar calFordDate = Calendar.getInstance();
            SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
            saveCurrentDate = currentDate.format(calFordDate.getTime());

            Calendar calFordTime = Calendar.getInstance();
            SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
            SaveCurrentTime = currentTime.format(calFordTime.getTime());

            PostRandomName = saveCurrentDate + SaveCurrentTime;

            final StorageReference filepath = storagereffi.child(uniqueId + PostRandomName + ".jpg");
            filepath.putFile(ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            final String downloadUri =uri.toString();
                            Bizpostreff.child(uniqueId +PostRandomName).child("Pic").setValue(downloadUri);
                            Toast.makeText(bizPost.this, "upload done", Toast.LENGTH_LONG).show();
                            SavePicPostInfo();

                        }
                    });
                }
            });

        }else{
            SavePicPostInfo();
        }

    }

    private void SavePicPostInfo() {
        Reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String  full = dataSnapshot.child("Full Name").getValue().toString();
                    String  Image = dataSnapshot.child("Profile Image").getValue().toString();
                    String  s = dataSnapshot.child("uid").getValue().toString();

                    pname  =  postname.getText().toString();
                    pplace  =  postplace.getText().toString();
                    price  =    Postprice.getText().toString();
                    Description = PostDescriptin.getText().toString();
                    contact = call.getText().toString();

                    if (TextUtils.isEmpty( pname)) {
                        Toast.makeText(bizPost.this, "please write products name", Toast.LENGTH_LONG).show();
                    }
                    if (TextUtils.isEmpty(  pplace )) {
                        Toast.makeText(bizPost.this, "please write place ", Toast.LENGTH_LONG).show();

                    } if (TextUtils.isEmpty(  price)) {
                        Toast.makeText(bizPost.this, "please write price", Toast.LENGTH_LONG).show();
                    }
                    else{
                        HashMap picpostmap = new HashMap();
                        picpostmap.put("uid", currentUserId);
                        picpostmap.put("date", saveCurrentDate);
                        picpostmap.put("time",SaveCurrentTime);
                        picpostmap.put("description", Description);
                        picpostmap.put("saleVenue", pplace);
                        picpostmap.put("contact", call);
                        picpostmap.put("productName", pname);
                        picpostmap.put("compare", s);
                        picpostmap.put("value", price);
                        picpostmap.put("fullName", full);
                        picpostmap.put("ProfileImage", Image);
                        Bizpostreff.child(uniqueId +PostRandomName).updateChildren(picpostmap).addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                if (task.isSuccessful()) {
                                    SendUserToBiz();
                                    Toast.makeText(bizPost.this, "saved succesfully", Toast.LENGTH_LONG).show();

                                } else{
                                    String msg = task.getException().getMessage();
                                    Toast.makeText(bizPost.this, "error occured" + msg, Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void SendUserToBiz() {

        Intent b=new Intent(bizPost.this,MainActivity.class);
        startActivity(b);
    }

    private void openGallary() {

        Intent gallaryin = new Intent();
        gallaryin.setAction(Intent.ACTION_GET_CONTENT);
        gallaryin.setType("image/*");
        startActivityForResult(gallaryin, gallary_pick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == gallary_pick && resultCode == RESULT_OK && data != null) {

            ImageUri = data.getData();

            Picasso.get().load(ImageUri).fit().centerCrop().into(postimagei);
        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home ){
            SendUserToMain();
        }
        return super.onOptionsItemSelected(item);
    }

    private void SendUserToMain() {
        Intent e=new Intent(bizPost.this,MainActivity.class);
        startActivity(e);
    }
}*/
