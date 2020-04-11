package com.example.post;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class chiefAd extends RecyclerView.Adapter<chiefAd .chiefVh> {

    ArrayList<chiefGs> mchiefGs = new ArrayList<chiefGs>();
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseRef;
    private ChildEventListener mChildEvent;

    public chiefAd (){

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseRef = mFirebaseDatabase.getReference().child("Chief Post");
        mChildEvent = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                chiefGs pp = dataSnapshot.getValue(chiefGs.class);
                pp.setUid(dataSnapshot.getKey());
                mchiefGs.add(pp);
                notifyItemInserted( mchiefGs.size() - 1);
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
    public chiefVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.chief_view_layout, parent, false);

        return new chiefAd.chiefVh(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull chiefVh holder, int position) {
        chiefGs post = mchiefGs.get(position);

        holder.description.setText(post.getDescription());
        holder.userName.setText(post.getFullName());
        holder.time.setText(post.getTime());
        holder.date.setText(post.getDate());
        holder.name.setText(post.getTittle());
        Picasso.get().load(post.getPic()).into(holder.postimage);
        Picasso.get().load(post.getProfileImage()).into(holder.profileImage);

    }

    @Override
    public int getItemCount() {
        return mchiefGs.size();
    }

    public class chiefVh extends RecyclerView.ViewHolder{
        TextView date, time, userName, description,price,place,name,contact;
        CircleImageView profileImage;
        ImageView postimage;


        public chiefVh(@NonNull View itemView) {
            super(itemView);



            date = itemView.findViewById(R.id.chiefv_date);
            time = itemView.findViewById(R.id.chiefv_time);
            userName = itemView.findViewById(R.id.chiefv_user_name);
            description = itemView.findViewById(R.id.chiefv_more);
            name = itemView.findViewById(R.id.chiefv_title);
            profileImage = itemView.findViewById(R.id.chiefv_profile_image);
            postimage=(ImageView)itemView.findViewById(R.id.chiefv_image);
        }
    }
}
