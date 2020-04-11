package com.example.post;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class recent_chats extends AppCompatActivity {

    private String dataseuseid, compareid, text;
    private EditText Reciever_id, Sender_id, Test_id ,message;
    private String cReciever_id, cSender_id;
    private ImageButton send;
    private DatabaseReference RootRef;
    private String saveCurrentDate, saveCurrentTime;
    String currentUserId;
    private FirebaseAuth mAuth;
    private Toolbar mToolbar;
    private List<chat> mchat ;
    private LinearLayoutManager linearLayoutManager;
    private MessageAdapter messageAdapter;
    private RecyclerView recyclerView;
    private DatabaseReference Reff;
    private CircleImageView barprofileimage;
    private TextView barprofilename;
    private DatabaseReference MessageReff;
    String full;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_chats);

        readMessage();
        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();

        Reff= FirebaseDatabase.getInstance().getReference().child("users");
        MessageReff= FirebaseDatabase.getInstance().getReference().child("Message People");

        barprofileimage = findViewById(R.id.cr_dp);
        barprofilename = findViewById(R.id.cr_user);

        Calendar calFordTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
        saveCurrentTime = currentTime.format(calFordTime.getTime());

        send = findViewById(R.id.cr_msg);
        message = findViewById(R.id.cr_message);
        Sender_id = findViewById(R.id.cr_cid);
        Reciever_id = findViewById(R.id.cr_rid);


        recyclerView = findViewById(R.id.cr_messages_list_of_users);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager( this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);



        mToolbar = findViewById(R.id.cr_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);



        Intent intent = getIntent();
        final postMsg discpost = (postMsg) intent.getSerializableExtra("Clickable");
        Sender_id.setText(currentUserId);
        Reciever_id.setText(discpost.getUid());

        cSender_id  = Sender_id.getText().toString();
        cReciever_id  = Reciever_id.getText().toString();

        RootRef = FirebaseDatabase.getInstance().getReference();

        Reff.child(cReciever_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    full = dataSnapshot.child("FullName").getValue().toString();
                    String  Image = dataSnapshot.child("ProfileImage").getValue().toString();

                    barprofilename.setText(full);
                    Picasso.get().load(Image).placeholder(R.drawable.profile).into(barprofileimage);
                }else{
                    Toast.makeText(recent_chats.this, "please select profile image first" , Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                text= message.getText().toString();

                if (!text.equals("")){
                    sendMessage(cSender_id,cReciever_id,text,currentUserId,saveCurrentTime);

                    HashMap picpostmap = new HashMap();
                    picpostmap.put("Name", full);
                    picpostmap.put("Time", saveCurrentTime);
                    picpostmap.put("uid", cReciever_id);
                    MessageReff.child(cReciever_id).updateChildren(picpostmap);

                }else{
                    Toast.makeText(recent_chats.this, "first write your message...", Toast.LENGTH_SHORT).show();
                }
                message.setText("");
            }
        });
        //  fetchdata();




    }

    private void readMessage() {
        mchat=new ArrayList<>();
        DatabaseReference refrence = FirebaseDatabase.getInstance().getReference().child("chats");
        refrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot :dataSnapshot.getChildren()){
                    chat pp = snapshot.getValue(chat.class);
                    if (pp.getReciver() != null && pp.getSender() !=null && cReciever_id !=null && cSender_id != null && pp.getReciver().equals(cSender_id) && pp.getSender().equals(cReciever_id) ||
                            pp.getReciver().equals(cReciever_id) && pp.getSender().equals(cSender_id)){
                        mchat.add(pp);
                    }

                    messageAdapter = new MessageAdapter(recent_chats.this, mchat);
                    recyclerView.setAdapter(messageAdapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void sendMessage (String sender ,String receiver ,String message,String uid ,String time){

        DatabaseReference refrence = FirebaseDatabase.getInstance().getReference();

        HashMap <String,Object> hashMap =new HashMap<>();
        hashMap.put("sender",sender);
        hashMap.put("reciver",receiver);
        hashMap.put("message",message);
        hashMap.put("idetity",uid);
        hashMap.put("time",time);

        refrence.child("chats").push().setValue(hashMap);

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

        Intent e=new Intent(recent_chats.this,MainActivity.class);
        startActivity(e);

    }

}

