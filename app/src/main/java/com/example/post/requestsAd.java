package com.example.post;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class requestsAd extends RecyclerView.Adapter<requestsAd .requestVh> {

    ArrayList<requestGs> mrequestGs = new ArrayList<requestGs>();
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseRef;
    private ChildEventListener mChildEvent;
    private String accept,user,name,status,image ;
    private DatabaseReference contactsReff;
    RecyclerView recyclerView;
    requestGs pp;


    public  requestsAd (){

        accept = "Accept";
        contactsReff= FirebaseDatabase.getInstance().getReference().child("Contacts");
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseRef = mFirebaseDatabase.getReference().child("Friend Requests Received");
        mChildEvent = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                pp = dataSnapshot.getValue(requestGs.class);
                pp.setUid(dataSnapshot.getKey());
                mrequestGs.add(pp);
                notifyItemInserted( mrequestGs.size() - 1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mDatabaseRef.addChildEventListener(mChildEvent);
    }

    @NonNull
    @Override
    public requestVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.request, parent, false);

        return new requestVh(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull requestVh holder, int position) {
        requestGs post = mrequestGs.get(position);


        holder.q.setText(post.getName());
        holder.g.setText(post.getAccepted());
        holder.z.setText(post.getStatus());
        holder.y.setText(post.getUid());
        Picasso.get().load(post.getProfileImage()).into(holder.x);


    }

    @Override
    public int getItemCount() {
        return mrequestGs.size();
    }

    public class requestVh extends RecyclerView.ViewHolder  implements View.OnClickListener{
        TextView q, z;
        CircleImageView x;
       EditText y,g;
        Button j ;


        public requestVh(@NonNull View itemView) {
            super(itemView);




            q = itemView.findViewById(R.id.request_user_name);
            z = itemView.findViewById(R.id.request_status);
            x= itemView.findViewById(R.id.request_profile_image);
            j= itemView.findViewById(R.id.requst_Accept);
            y= itemView.findViewById(R.id.request_id);
            g= itemView.findViewById(R.id.request_accept_status);

            itemView.setOnClickListener(this);


            j.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pp.notify();
                        j.setText("working on it");
                    user  = y.getText().toString();
                    name = q.getText().toString();
                    status =z.getText().toString();

                    HashMap picpostmap = new HashMap();
                    picpostmap.put("Accepted", accept);
                    mDatabaseRef.child(user).updateChildren(picpostmap).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (task.isSuccessful()){
                                HashMap contact = new HashMap();
                                contact.put("Name", name);
                                contact.put("Status", status);
                                contact.put("uid", user);
                                contactsReff.child(user).updateChildren(contact).addOnCompleteListener(new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task) {
                                        if (task.isSuccessful())
                                            mDatabaseRef.child(user).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()){
                                                        mrequestGs.add(pp);

                                                    }
                                                }
                                            });
                                    }
                                });
                            }
                        }
                    });


                }
            });

        }

        @Override
        public void onClick(View v) {



        }
    }
}

