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

public class jobAd extends RecyclerView.Adapter<jobAd.jobVh> {

    ArrayList<jobGs> mjobGs = new ArrayList<jobGs>();
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseRef;
    private ChildEventListener mChildEvent;

    public jobAd(){

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseRef = mFirebaseDatabase.getReference().child("job Post");
        mChildEvent = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                jobGs pp = dataSnapshot.getValue(jobGs.class);
                pp.setUid(dataSnapshot.getKey());
                mjobGs.add(pp);
                notifyItemInserted( mjobGs.size() - 1);
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
    public jobVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.job_view_layout, parent, false);

        return new jobAd.jobVh(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull jobVh holder, int position) {
        jobGs post = mjobGs.get(position);

        holder.description.setText(post.getDescription());
        holder.userName.setText(post.getFullName());
        holder.time.setText(post.getTime());
        holder.date.setText(post.getDate());
        holder.contact.setText(post.getAdress());
        holder.place.setText(post.getLocation());
        holder.name.setText(post.getJobtype());
        Picasso.get().load(post.getPic()).into(holder.postimage);
        Picasso.get().load(post.getProfileImage()).into(holder.profileImage);

    }

    @Override
    public int getItemCount() {
        return mjobGs.size();
    }

    public class jobVh extends RecyclerView.ViewHolder{
        TextView date, time, userName, description,price,place,name,contact;
        CircleImageView profileImage;
        ImageView postimage;


        public jobVh(@NonNull View itemView) {
            super(itemView);


            contact = itemView.findViewById(R.id.jobv_contact);
            date = itemView.findViewById(R.id.jobv_date);
            time = itemView.findViewById(R.id.jobv_time);
            userName = itemView.findViewById(R.id.jobv_user_name);
            description = itemView.findViewById(R.id.jobv_more);
            name = itemView.findViewById(R.id.jobv_title);
            place = itemView.findViewById(R.id.jobv_place);
            profileImage = itemView.findViewById(R.id.jobv_profile_image);
            postimage=(ImageView)itemView.findViewById(R.id.jobv_image);
        }
        }
    }

