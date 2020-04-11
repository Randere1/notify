package com.example.post;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class contactsAd extends RecyclerView.Adapter<contactsAd.contactsVh> {

    ArrayList<contactsgs> mcontactgs = new ArrayList<contactsgs>();
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseRef;
    private ChildEventListener mChildEvent;
    String currentUserId;
    private FirebaseAuth mAuth;

    public  contactsAd (){

        mAuth= FirebaseAuth.getInstance();
        currentUserId=mAuth.getCurrentUser().getUid();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseRef = mFirebaseDatabase.getReference().child("Contacts");
        mChildEvent = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                contactsgs pp = dataSnapshot.getValue(contactsgs.class);
                pp.setUid(dataSnapshot.getKey());
                if (!pp.getUid().equals(currentUserId)){
                    mcontactgs.add(pp);
                    notifyItemInserted( mcontactgs.size() - 1);
                }

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
    public contactsVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.contact, parent, false);

        return new contactsVh(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull contactsVh holder, int position) {
        contactsgs post = mcontactgs.get(position);


        holder.us.setText(post.getName());
        holder.st.setText(post.getStatus());


    }

    @Override
    public int getItemCount() {
        return mcontactgs.size();
    }

    public class contactsVh extends RecyclerView.ViewHolder  implements View.OnClickListener{
        TextView st, us;



        public contactsVh(@NonNull View itemView) {
            super(itemView);




            st = itemView.findViewById(R.id.contact_status);
            us = itemView.findViewById(R.id.contact_user_name);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            contactsgs   cont = mcontactgs.get(position);
            Intent intent = new Intent(v.getContext(), recent_contact.class);
            intent.putExtra("Clickable", cont);
            v.getContext().startActivity(intent);

        }
    }
}