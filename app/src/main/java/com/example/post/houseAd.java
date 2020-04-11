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

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class houseAd extends RecyclerView.Adapter<houseAd.houseVh> {

    ArrayList<houseGs> mhouseGs = new ArrayList<houseGs>();
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseRef;
    private ChildEventListener mChildEvent;

    public houseAd(){

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseRef = mFirebaseDatabase.getReference().child("house Post");
        mChildEvent = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                houseGs pp = dataSnapshot.getValue(houseGs.class);
                pp.setUid(dataSnapshot.getKey());
                mhouseGs.add(pp);
                notifyItemInserted( mhouseGs.size() - 1);
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
    public houseVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.house_view_layout, parent, false);

        return new houseAd.houseVh(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull houseVh holder, int position) {
        houseGs post = mhouseGs.get(position);

        holder.description.setText(post.getDescription());
        holder.userName.setText(post.getFullName());
        holder.time.setText(post.getTime());
        holder.date.setText(post.getDate());
        holder.contact.setText(post.getContact());
        holder.place.setText(post.getLocation());
        holder.name.setText(post.getHouseName());
        Picasso.get().load(post.getPic()).into(holder.postimage);
        Picasso.get().load(post.getProfileImage()).into(holder.profileImage);

    }

    @Override
    public int getItemCount() {
        return mhouseGs.size();
    }

    public class houseVh extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView date, time, userName, description,price,place,name,contact,reach,  msg;
        CircleImageView profileImage;
        ImageView postimage;


        public houseVh(@NonNull View itemView) {
            super(itemView);

            msg = itemView.findViewById(R.id.housevv_reach);
            contact = itemView.findViewById(R.id.housev_contact);
            date = itemView.findViewById(R.id.housev_date);
            price = itemView.findViewById(R.id.housev_price);
            time = itemView.findViewById(R.id.housev_time);
            userName = itemView.findViewById(R.id.housev_user_name);
            description = itemView.findViewById(R.id.housev_more);
            name = itemView.findViewById(R.id.housev_title);
            place = itemView.findViewById(R.id.housev_place);
            profileImage = itemView.findViewById(R.id.housev_profile_image);
            postimage=(ImageView)itemView.findViewById(R.id.housev_image);
            reach=itemView.findViewById(R.id.housevv_reach);

            itemView.setOnClickListener(this);


            msg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    houseGs discposmsg = mhouseGs.get(position);
                    Intent a = new Intent(v.getContext(), housemsgs.class);
                    a.putExtra("Clickable",  discposmsg);
                    v.getContext().startActivity(a);
                }
            });
        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            houseGs discposmsg = mhouseGs.get(position);
            Intent a = new Intent(v.getContext(), msgs.class);
            a.putExtra("Clickable",  discposmsg);
            v.getContext().startActivity(a);

        }
    }
}