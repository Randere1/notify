package com.example.post;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class Ffriends_activity extends AppCompatActivity {
    TextView profilename, username, country, gender, birth, status;
    private RelativeLayout relativeLayout;
    ScrollView scrollView;
    CircleImageView profilepic;
    Button sendreq, sendtext, blocker;
    private String cReciever_id;
    private EditText Reciever_id;
    private DatabaseReference Reff;
    String full, image, pstatus, puname, pgender, pcountry, pbirth, s;
    private DatabaseReference friendsReff;
    String currentUserId;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ffriends_activity);

        friendsReff = FirebaseDatabase.getInstance().getReference().child("Friend Requests Received");

        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();

        profilename = findViewById(R.id.fprofile_name);
        username = findViewById(R.id.fprofile_username);
        country = findViewById(R.id.fprofile_country);
        gender = findViewById(R.id.fprofile_gender);
        birth = findViewById(R.id.fprofile_dob);
        status = findViewById(R.id.fprofile_status);
        scrollView = findViewById(R.id.fprofile);
        profilepic = findViewById(R.id.fProfile_pic);
        sendtext = findViewById(R.id.fprofile_SendMessage);
        sendreq = findViewById(R.id.fprofile_Sendrequest);
        blocker = findViewById(R.id.fprofile_Delete);
        Reciever_id = findViewById(R.id.fprofilev_cid);

        Reff = FirebaseDatabase.getInstance().getReference().child("users");

        Intent intent = getIntent();
        final friendsGs gs = (friendsGs) intent.getSerializableExtra("Clickable");
        Reciever_id.setText(gs.getUid());

        cReciever_id = Reciever_id.getText().toString();
        //  sendreq.setEnabled(true);

        if (!cReciever_id.equals(currentUserId)) {
            sendreq.setVisibility(View.VISIBLE);
            Toast.makeText(this, "Ids are same", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Check the code again", Toast.LENGTH_SHORT).show();
        }

        sendreq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap picpostmap = new HashMap();
                picpostmap.put("Name", full);
                picpostmap.put("Status", pstatus);
                picpostmap.put("profileImage", image);
                picpostmap.put("Accepted", "none");
                picpostmap.put("uid", s);

                friendsReff.child(cReciever_id).updateChildren(picpostmap).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            sendreq.setVisibility(View.INVISIBLE);
                        }
                    }
                });

            }
        });



        friendsReff.child(cReciever_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    //sendreq.setVisibility(View.INVISIBLE);


                } else {
                    //sendreq.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Reff.child(cReciever_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    full = dataSnapshot.child("FullName").getValue().toString();
                    image = dataSnapshot.child("ProfileImage").getValue().toString();
                    puname = dataSnapshot.child("username").getValue().toString();
                    pgender = dataSnapshot.child("gender").getValue().toString();
                    pcountry = dataSnapshot.child("Country").getValue().toString();
                    pstatus = dataSnapshot.child("status").getValue().toString();
                    pbirth = dataSnapshot.child("dob").getValue().toString();
                    s = dataSnapshot.child("uid").getValue().toString();


                    profilename.setText(full);
                    username.setText(puname);
                    country.setText(pcountry);
                    gender.setText(pgender);
                    birth.setText(pstatus);
                    status.setText(full);
                    Picasso.get().load(image).placeholder(R.drawable.profile).into(profilepic);
                } else {
                    Toast.makeText(Ffriends_activity.this, "User not found in database", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
