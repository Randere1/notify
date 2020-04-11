package com.example.post;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class coment extends AppCompatActivity {

    private String saveCurrentDate,SaveCurrentTime,PostRandomName,downloadUri;
    private DatabaseReference discriptionreff;
    String uniqueId = UUID.randomUUID().toString();
    private FirebaseAuth mAuth;
    ImageButton imageButton;
    EditText editText, pk;
    private RecyclerView recyclerView;
    String j;
    private DatabaseReference Reff;
    String currentUserId;
    String uuid ,uidd, mm ,you;
    List<all_commentsGs> comg;
    all_comments_Ad allCommentsAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coment);

     // readcomment();
        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();

        discriptionreff = FirebaseDatabase.getInstance().getReference().child("Description Post");
        Reff = FirebaseDatabase.getInstance().getReference().child("users").child(currentUserId);



        recyclerView =findViewById(R.id.comments_list);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager( this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        editText =findViewById(R.id.comment);
        imageButton =findViewById(R.id.send_comment);
        pk =findViewById(R.id.pk);

        Calendar calFordDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
        saveCurrentDate = currentDate.format(calFordDate.getTime());

        Calendar calFordTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
        SaveCurrentTime = currentTime.format(calFordTime.getTime());

        PostRandomName = saveCurrentDate + SaveCurrentTime;

        Intent intent = getIntent();
        final discpost discpost = (discpost) intent.getSerializableExtra("Clickable");
         uuid=discpost.getPk();
         pk.setText(discpost.getPk());
        uidd=discpost.getPkk();
         mm = pk.getText().toString();

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getdata();

            }
        });

        discriptionreff.child(uuid).child("Comments").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               if (dataSnapshot.exists()){
                   Toast.makeText(coment.this, "exists", Toast.LENGTH_SHORT).show();
                   comg = new ArrayList<>();
                   for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                       all_commentsGs pp = snapshot.getValue(all_commentsGs.class);
                       comg.add(pp);
                       allCommentsAd = new all_comments_Ad(coment.this, comg);
                       recyclerView.setAdapter(allCommentsAd);

                   }
               }else{
                   Toast.makeText(coment.this, "no exist", Toast.LENGTH_SHORT).show();
               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

   private void readcomment() {

   if( uidd != null){
       Toast.makeText(this, "pk not null", Toast.LENGTH_SHORT).show();
       comg = new ArrayList<>();

       discriptionreff.child(mm).child("Comments").addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               //  mchat.clear();
               for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                   all_commentsGs pp = snapshot.getValue(all_commentsGs.class);
                   comg.add(pp);
                   allCommentsAd = new all_comments_Ad(coment.this, comg);
                   recyclerView.setAdapter(allCommentsAd);

               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });
   }else{
       Toast.makeText(this, "pk  null", Toast.LENGTH_SHORT).show();
   }

    }

    private void getdata() {
      Reff.addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              if (dataSnapshot.exists()){
                  String  Image = dataSnapshot.child("ProfileImage").getValue().toString();
                  String  full = dataSnapshot.child("FullName").getValue().toString();

                  j=editText.getText().toString();

                  HashMap picpostmap = new HashMap();
                  picpostmap.put("Name", full);
                  picpostmap.put("profile", Image);
                  picpostmap.put("comment", j);
                  picpostmap.put("uuid", uuid);
                  picpostmap.put("Time", SaveCurrentTime);
                  picpostmap.put("Date", saveCurrentDate);

                  discriptionreff.child(uuid).child("Comments").push().setValue(picpostmap).addOnCompleteListener(new OnCompleteListener<Void>() {
                      @Override
                      public void onComplete(@NonNull Task<Void> task) {
                          if (task.isSuccessful()){
                              Toast.makeText(coment.this, "saved succesfully", Toast.LENGTH_LONG).show();

                          }
                          editText.setText("");
                      }
                  });

              }
          }

          @Override
          public void onCancelled(@NonNull DatabaseError databaseError) {

          }
      });
    }
}
