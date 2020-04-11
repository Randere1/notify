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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

public class allpost extends AppCompatActivity {
    private Toolbar mToolbar;
    private Button UpdatePostButton,UpdatePostButtoni;
    private EditText PostDescription,PostDescriptioni, PostDescriptionii,known;
    private ImageButton postimage,postimagei;
    private FrameLayout frame,layout;
    private String Description,Descriptioni,in;
    private DatabaseReference picpostreff,  discriptionreff;
    private TextView hint,hinti;
    private String saveCurrentDate,SaveCurrentTime,PostRandomName,downloadUri;
    private Uri ImageUri;
    final static int gallary_pick = 1;
    FirebaseStorage storage;
    StorageReference storagereffi;
    private DatabaseReference Reff;
    String currentUserId;
    FirebaseUser user;
    String n;
    String uniqueId = UUID.randomUUID().toString();
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allpost);
        UpdatePostButton =findViewById(R.id.Post_button);
        PostDescription =(EditText)findViewById(R.id.post_something);
        postimage =findViewById(R.id.post_image);
        postimagei =findViewById(R.id.post_imagei);
        hint =findViewById(R.id.post_click);
        frame=(FrameLayout)findViewById(R.id.frame_i);


        mAuth=FirebaseAuth.getInstance();
        currentUserId=mAuth.getCurrentUser().getUid();
        user=FirebaseAuth.getInstance().getCurrentUser();
        n=user.getUid();
        discriptionreff = FirebaseDatabase.getInstance().getReference().child("Description Post");
        Reff= FirebaseDatabase.getInstance().getReference().child("users").child(currentUserId);

        storage = FirebaseStorage.getInstance();
        storagereffi= storage.getReference().child("post pic");

        mToolbar = findViewById(R.id.all_post_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

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

            final StorageReference filepath = storagereffi.child(uniqueId + ".jpg");
            filepath.putFile(ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            final String downloadUri =uri.toString();
                            discriptionreff.child(uniqueId).child("Pic").setValue(downloadUri);
                            Toast.makeText(allpost.this, "upload done", Toast.LENGTH_LONG).show();
                            SavePicPostInfo();

                        }
                    });
                }
            });

        } else{
            SavePicPostInfo();
        }
    }

    private void SavePicPostInfo() {
        Reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String  full = dataSnapshot.child("FullName").getValue().toString();
                    String  Image = dataSnapshot.child("ProfileImage").getValue().toString();
                    String  s = dataSnapshot.child("uid").getValue().toString();
                    Description  = PostDescription.getText().toString();
                    if (TextUtils.isEmpty(Description)) {
                        Toast.makeText(allpost.this, "please write something", Toast.LENGTH_LONG).show();
                    }else{
                        Calendar calFordDate = Calendar.getInstance();
                        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
                        saveCurrentDate = currentDate.format(calFordDate.getTime());

                        Calendar calFordTime = Calendar.getInstance();
                        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
                        SaveCurrentTime = currentTime.format(calFordTime.getTime());

                        PostRandomName = saveCurrentDate + SaveCurrentTime;


                        HashMap picpostmap = new HashMap();
                        picpostmap.put("uid", currentUserId);
                        picpostmap.put("compare", s);
                        picpostmap.put("date", saveCurrentDate);
                        picpostmap.put("time",SaveCurrentTime );
                        picpostmap.put("description", Description);
                        picpostmap.put("fullName", full);
                        picpostmap.put("pk",uniqueId);
                        picpostmap.put("pkk",uniqueId);
                        picpostmap.put("ProfileImage", Image);
                        discriptionreff.child(uniqueId).updateChildren(picpostmap).addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                if (task.isSuccessful()) {
                                    SendUserToMain();
                                    Toast.makeText(allpost.this, "saved succesfully", Toast.LENGTH_LONG).show();

                                } else{
                                    String msg = task.getException().getMessage();
                                    Toast.makeText(allpost.this, "error occured" + msg, Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


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

        Intent e=new Intent(allpost.this,MainActivity.class);
        startActivity(e);

    }
}
