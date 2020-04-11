package com.example.post;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;

public class postMsgAd extends RecyclerView.Adapter<postMsgAd.postMsgVh> {

    ArrayList<postMsg> msg = new ArrayList<postMsg>();
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseRef;
    private ChildEventListener mChildEvent;
    String currentUserId;
    private FirebaseAuth mAuth;
    String v;


    public postMsgAd(){
      
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseRef = mFirebaseDatabase.getReference().child("Message People");
        mChildEvent = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                postMsg pp = dataSnapshot.getValue(postMsg.class);
                pp.setUid(dataSnapshot.getKey());
                msg.add(pp);
                notifyItemInserted(msg.size() - 1);
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
    public postMsgVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        Context context = parent.getContext();

        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.message_view, parent, false);

        return new postMsgVh(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull postMsgVh holder, int position) {

        postMsg post = msg.get(position);

        holder.a.setText(post.getName());
        holder.c.setText(post.getTime());

    }

    @Override
    public int getItemCount() {
        return msg.size();
    }

    public class postMsgVh extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView a, c;
        public postMsgVh(@NonNull View itemView) {
            super(itemView);

            a= itemView.findViewById(R.id.msg_list_name);
            c= itemView.findViewById(R.id.msg_list_time);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            postMsg msgclick = msg.get(position);
            Intent intent = new Intent(v.getContext(), recent_chats.class);
            intent.putExtra("Clickable", msgclick);
            v.getContext().startActivity(intent);
        }
    }
}
