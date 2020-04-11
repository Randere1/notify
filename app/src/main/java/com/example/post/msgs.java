package com.example.post;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
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
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class msgs extends AppCompatActivity {


    private String dataseuseid, compareid, text;
    private EditText Reciever_id, Sender_id, Test_id ,message;
    private String cReciever_id, cSender_id;
    private ImageButton send;
    private DatabaseReference RootRef;
    private String saveCurrentDate, saveCurrentTime;
    String currentUserId;
    private FirebaseAuth mAuth;
    private Toolbar mToolbar;
    private final  ArrayList <Messages> messagesList = new ArrayList<Messages>() ;
    private LinearLayoutManager linearLayoutManager;
    private MessageAdapter messageAdapter;
    private RecyclerView recyclerView;
    private DatabaseReference Reff;
    private CircleImageView barprofileimage;
    private TextView barprofilename;
    private DatabaseReference MessageReff;
    String full;
    List<chat> mchat;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseRef;
    private ChildEventListener mChildEvent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msgs);

        readmessage();

        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();

        Reff= FirebaseDatabase.getInstance().getReference().child("users");
        MessageReff= FirebaseDatabase.getInstance().getReference().child("Message People");

        barprofileimage = findViewById(R.id.msg_dp);
        barprofilename = findViewById(R.id.msg_user);

        Calendar calFordTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
        saveCurrentTime = currentTime.format(calFordTime.getTime());

        send = findViewById(R.id.send_msg);
        message = findViewById(R.id.input_message);
        Sender_id = findViewById(R.id.msg_cid);
        Reciever_id = findViewById(R.id.msg_rid);

      //  messageAdapter = new MessageAdapter();
        recyclerView = findViewById(R.id.private_messages_list_of_users);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager( this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
      //  recyclerView.setAdapter(messageAdapter);


        mToolbar = findViewById(R.id.msg_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);



        Intent intent = getIntent();
        final discpost discpost = (discpost) intent.getSerializableExtra("Clickable");
        Sender_id.setText(currentUserId);
        Reciever_id.setText(discpost.getCompare());

        cSender_id  = Sender_id.getText().toString();
        cReciever_id  = Reciever_id.getText().toString();



      Reff.child(cReciever_id).addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              if (dataSnapshot.exists()){
                  full = dataSnapshot.child("FullName").getValue().toString();
                  String  Image = dataSnapshot.child("ProfileImage").getValue().toString();

                  barprofilename.setText(full);
                  Picasso.get().load(Image).placeholder(R.drawable.profile).into(barprofileimage);
              }else{
                  Toast.makeText(msgs.this, "please select profile image first" , Toast.LENGTH_LONG).show();
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
                    Toast.makeText(msgs.this, "first write your message...", Toast.LENGTH_SHORT).show();
                }
                message.setText("");
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (cSender_id.equals(cReciever_id)){
            Toast.makeText(this, "Sorry you cant send yourself a massage", Toast.LENGTH_SHORT).show();
            Intent r=new Intent(msgs.this,MainActivity.class);
            startActivity(r);
        }
    }

    private void readmessage() {
        mchat=new ArrayList<>();
        DatabaseReference refrence = FirebaseDatabase.getInstance().getReference().child("chats");
        refrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              //  mchat.clear();
                for (DataSnapshot snapshot :dataSnapshot.getChildren()){
                    chat pp = snapshot.getValue(chat.class);
                    if (pp.getReciver() != null && pp.getSender() !=null && cReciever_id !=null && cSender_id != null && pp.getReciver().equals(cSender_id) && pp.getSender().equals(cReciever_id) ||
                      pp.getReciver().equals(cReciever_id) && pp.getSender().equals(cSender_id)){
                         mchat.add(pp);
                    }

                    messageAdapter = new MessageAdapter(msgs.this, mchat);
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

        Intent e=new Intent(msgs.this,MainActivity.class);
        startActivity(e);

    }

}





    /*    send.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                text= message.getText().toString();
                cSender_id  = Sender_id.getText().toString();
                cReciever_id  = Reciever_id.getText().toString();


                if (TextUtils.isEmpty(text))
                {
                    Toast.makeText(msgs.this, "first write your message...", Toast.LENGTH_SHORT).show();
                }
                else {
                    String messageSenderRef = "Messages/" + cSender_id  + "/" + cReciever_id ;
                    String messageReceiverRef = "Messages/" + cReciever_id  + "/" + cSender_id;

                    Calendar calFordDate = Calendar.getInstance();
                    SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
                    saveCurrentDate = currentDate.format(calFordDate.getTime());

                    Calendar calFordTime = Calendar.getInstance();
                    SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
                    saveCurrentTime = currentTime.format(calFordTime.getTime());




                    DatabaseReference userMessageKeyRef = RootRef.child("Messages")
                            .child(cSender_id).child(cReciever_id).push();

                    String messagePushID = userMessageKeyRef.getKey();

                    Map messageTextBody = new HashMap();
                    messageTextBody.put("message", text);
                    messageTextBody.put("uid", currentUserId);
                    messageTextBody.put("type", "text");
                    messageTextBody.put("from", cSender_id);
                    messageTextBody.put("to", cReciever_id);
                    messageTextBody.put("messageID", messagePushID);
                    messageTextBody.put("time", saveCurrentTime);
                    messageTextBody.put("date", saveCurrentDate);

                    Map messageBodyDetails = new HashMap();
                    messageBodyDetails.put(messageSenderRef + "/" + messagePushID, messageTextBody);
                    messageBodyDetails.put( messageReceiverRef + "/" + messagePushID, messageTextBody);

                    RootRef.updateChildren(messageBodyDetails).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(msgs.this, "Message Sent Successfully...", Toast.LENGTH_SHORT).show();
                                message.setText("");

                            }
                            else
                            {
                                Toast.makeText(msgs.this, "Error", Toast.LENGTH_SHORT).show();
                                message.setText("");
                            }

                        }
                    });

                }



                }
        });
        
        




    }

    private void fetchdata() {


        RootRef.child("Messages").child(cSender_id).child(cReciever_id)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s)
                    {
                        Messages messages = dataSnapshot.getValue(Messages.class);

                        messagesList.add(messages);

                        messageAdapter.notifyDataSetChanged();

                        userMessagesList.smoothScrollToPosition(userMessagesList.getAdapter().getItemCount());
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
}
     */
