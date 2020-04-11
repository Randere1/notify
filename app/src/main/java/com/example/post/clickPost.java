package com.example.post;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class clickPost extends AppCompatActivity {

    private TextView postdescription,uid,cid;
    private Button Edit, Delete;
    private ImageView postimage;
    private String postkey, dataseuseid,compareid, pimage, description;
    private String saveCurrentDate,SaveCurrentTime,PostRandomName;
    private DatabaseReference Reff;
    String currentUserId;
    private FirebaseAuth mAuth;
    discpost discpost;
    Uri uri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_post);


        //  user=FirebaseAuth.getInstance().getCurrentUser();
        //  t=user.getUid();


        Edit = (Button) findViewById(R.id.enable_edit);
        Delete = (Button) findViewById(R.id.enable_delete);
        postimage = (ImageView) findViewById(R.id.enable_image);
        postdescription = (TextView) findViewById(R.id.enable_description);



        Intent intent = getIntent();
        final discpost discpost = (discpost) intent.getSerializableExtra("Clickable");
        postdescription.setText(discpost.getDescription());
        Picasso.get().load(discpost.getPic()).into(postimage);
        dataseuseid = discpost.getUid();
        compareid= discpost.getCompare();


        if(dataseuseid == compareid) {
            Delete.setVisibility((View.VISIBLE));
            Edit.setVisibility((View.VISIBLE));
        }

    }
}

