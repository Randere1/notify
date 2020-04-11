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

public class jobPost extends AppCompatActivity {
    private Button UpdatePostButton;
    private EditText jobDescriptin,name,place,cont;
    private String Description,pname,pplace,price,contact;
    private FrameLayout frame;
    private Toolbar mtoolbar;
    private TextView hint;
    private ImageButton postimage,postimagei;
    private ScrollView scrollView;
    FirebaseStorage storage;
    StorageReference storagereffi;
    private DatabaseReference jobpostreff;
    private DatabaseReference Reff;
    private FirebaseAuth mAuth;
    String currentUserId;
    String uniqueId = UUID.randomUUID().toString();
    private String saveCurrentDate,SaveCurrentTime,PostRandomName;
    final static int gallary_pick = 1;
    private Uri ImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_post);

        hint=findViewById(R.id.job_click);
        postimage=findViewById(R.id.job_image);
        postimagei=findViewById(R.id.job_imagei);
        frame=findViewById(R.id.job_i);
        scrollView=findViewById(R.id.biz_scroll);
        UpdatePostButton=findViewById(R.id.job_Post_button);
        jobDescriptin=findViewById(R.id.job_moredisc);
        name=findViewById(R.id.job_something);
        place=findViewById(R.id.job_place);
        cont=findViewById(R.id.job_contact);
        mAuth= FirebaseAuth.getInstance();
        currentUserId=mAuth.getCurrentUser().getUid();
        mtoolbar=findViewById(R.id.job_post_bar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Calendar calFordDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
        saveCurrentDate = currentDate.format(calFordDate.getTime());

        Calendar calFordTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
        SaveCurrentTime = currentTime.format(calFordTime.getTime());

        PostRandomName = saveCurrentDate + SaveCurrentTime;


        mAuth= FirebaseAuth.getInstance();
        currentUserId=mAuth.getCurrentUser().getUid();
        storage =FirebaseStorage.getInstance();
        storagereffi= storage.getReference().child("job post pic");
        jobpostreff = FirebaseDatabase.getInstance().getReference().child("job Post");
        Reff= FirebaseDatabase.getInstance().getReference().child("users");




        UpdatePostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storeimagetofirebase();

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

    private void storeimagetofirebase() {
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
                            jobpostreff.child(uniqueId +PostRandomName).child("Pic").setValue(downloadUri);
                            Toast.makeText(jobPost.this, "upload done", Toast.LENGTH_LONG).show();
                            storedata();

                        }
                    });
                }
            });


        }else{
            storedata();

        }

    }

    private void openGallary() {
        Intent gallaryin = new Intent();
        gallaryin.setAction(Intent.ACTION_GET_CONTENT);
        gallaryin.setType("image/*");
        startActivityForResult(gallaryin, gallary_pick);
    }

    private void storedata() {
        Reff.child(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String  full = dataSnapshot.child("FullName").getValue().toString();
                    String  Image = dataSnapshot.child("ProfileImage").getValue().toString();
                    String  s = dataSnapshot.child("uid").getValue().toString();

                    pname  =  name.getText().toString();
                    pplace  =  place.getText().toString();
                    Description = jobDescriptin.getText().toString();
                    contact = cont.getText().toString();

                    if (TextUtils.isEmpty( pname)) {
                        Toast.makeText(jobPost.this, "please write products name", Toast.LENGTH_LONG).show();
                    }
                    if (TextUtils.isEmpty(  pplace )) {
                        Toast.makeText(jobPost.this, "please write place ", Toast.LENGTH_LONG).show();

                    } if (TextUtils.isEmpty(  Description)) {
                        Toast.makeText(jobPost.this, "please write price", Toast.LENGTH_LONG).show();
                    }
                    else{
                        HashMap picpostmap = new HashMap();
                        picpostmap.put("uid", currentUserId);
                        picpostmap.put("date", saveCurrentDate);
                        picpostmap.put("time",SaveCurrentTime);
                        picpostmap.put("description", Description);
                        picpostmap.put("location", pplace);
                        picpostmap.put("compare", s);
                        picpostmap.put("jobtype", pname);
                        picpostmap.put("adress", contact);
                        picpostmap.put("fullName", full);
                        picpostmap.put("ProfileImage", Image);
                        jobpostreff.child(uniqueId +PostRandomName).updateChildren(picpostmap).addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                if (task.isSuccessful()) {
                                    SendUserToBiz();
                                    Toast.makeText(jobPost.this, "saved succesfully", Toast.LENGTH_LONG).show();

                                } else{
                                    String msg = task.getException().getMessage();
                                    Toast.makeText(jobPost.this, "error occured" + msg, Toast.LENGTH_LONG).show();
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

        Intent b=new Intent(jobPost.this,job.class);
        startActivity(b);
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
        Intent e=new Intent(jobPost.this,MainActivity.class);
        startActivity(e);
    }
}


 /*       mAuth=FirebaseAuth.getInstance();
        currentUserId=mAuth.getCurrentUser().getUid();

        Reff= FirebaseDatabase.getInstance().getReference().child("users");
        jobpostreff = FirebaseDatabase.getInstance().getReference().child("Biz Post");

        UpdatePostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveinfo();
            }
        });
    }

    private void saveinfo() {
        Reff.child(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String  full = dataSnapshot.child("Full Name").getValue().toString();
                    String  Image = dataSnapshot.child("Profile Image").getValue().toString();
                    String  s = dataSnapshot.child("uid").getValue().toString();

                    pname  =  name.getText().toString();
                    pplace  =  place.getText().toString();
                    Description = jobDescriptin.getText().toString();
                    contact = cont.getText().toString();

                    Calendar calFordDate = Calendar.getInstance();
                    SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
                    saveCurrentDate = currentDate.format(calFordDate.getTime());

                    Calendar calFordTime = Calendar.getInstance();
                    SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
                    SaveCurrentTime = currentTime.format(calFordTime.getTime());

                    PostRandomName = saveCurrentDate + SaveCurrentTime;

                    if (TextUtils.isEmpty( pname)) {
                        Toast.makeText(jobPost.this, "please write products name", Toast.LENGTH_LONG).show();
                    }
                    if (TextUtils.isEmpty(  pplace )) {
                        Toast.makeText(jobPost.this, "please write place ", Toast.LENGTH_LONG).show();

                    } if (TextUtils.isEmpty(contact)) {
                        Toast.makeText(jobPost.this, "please write price", Toast.LENGTH_LONG).show();
                    }
                    else{  HashMap picpostmap = new HashMap();
                        picpostmap.put("uid", currentUserId);
                        picpostmap.put("date", saveCurrentDate);
                        picpostmap.put("time",SaveCurrentTime);
                        picpostmap.put("description", Description);
                        picpostmap.put("place", pplace);
                        picpostmap.put("contact", cont);
                        picpostmap.put("job Name", pname);
                        picpostmap.put("compare", s);
                        picpostmap.put("fullName", full);
                        picpostmap.put("ProfileImage", Image);
                        jobpostreff.child(uniqueId +PostRandomName).updateChildren(picpostmap).addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                if (task.isSuccessful()) {
                                    SendUserTojob();
                                    Toast.makeText(jobPost.this, "saved succesfully", Toast.LENGTH_LONG).show();

                                } else{
                                    String msg = task.getException().getMessage();
                                    Toast.makeText(jobPost.this, "error occured" + msg, Toast.LENGTH_LONG).show();
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

    private void SendUserTojob() {
        Intent b=new Intent(jobPost.this,job.class);
        startActivity(b);

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
        Intent e=new Intent(jobPost.this,MainActivity.class);
        startActivity(e);
    }
} */
