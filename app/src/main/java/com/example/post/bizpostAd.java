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

public class bizpostAd extends RecyclerView.Adapter<bizpostAd.MyViewHolder> {

    ArrayList<bizpostGS> bizpost = new ArrayList<bizpostGS>();
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseRef;
    private ChildEventListener mChildEvent;

    public bizpostAd(){
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseRef = mFirebaseDatabase.getReference().child("Biz Post");
        mChildEvent = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                bizpostGS pp = dataSnapshot.getValue(bizpostGS.class);
                pp.setUid(dataSnapshot.getKey());
                bizpost.add(pp);
                notifyItemInserted(bizpost.size() - 1);
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
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.biz_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        bizpostGS post = bizpost.get(position);

        holder.description.setText(post.getDescription());
        holder.userName.setText(post.getFullName());
        holder.time.setText(post.getTime());
        holder.date.setText(post.getDate());
        holder.price.setText(post.getValue());
        holder.contact.setText(post.getContact());
        holder.place.setText(post.getSaleVenue());
        holder.name.setText(post.getProductName());
        Picasso.get().load(post.getPic()).into(holder.postimage);
        Picasso.get().load(post.getProfileImage()).into(holder.profileImage);



        if (post.getPic() != null) {
            holder.postimage.setVisibility(View.VISIBLE);

        } else {
            holder.postimage.setVisibility(View.GONE);
        } if (post.getDescription() != null) {
            holder.description.setVisibility(View.VISIBLE);

        } else {
            holder.description.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return bizpost.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView date, time, userName, description,price,place,name,contact,msg;
        CircleImageView profileImage;
        ImageView postimage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            msg = itemView.findViewById(R.id.housevv_reach);
            contact = itemView.findViewById(R.id.biz_contact);
            date = itemView.findViewById(R.id.biz_date);
            time = itemView.findViewById(R.id.biz_time);
            userName = itemView.findViewById(R.id.biz_user_name);
            description = itemView.findViewById(R.id.biz_more);
            name = itemView.findViewById(R.id.biz_product_name);
            price = itemView.findViewById(R.id.biz_price);
            place = itemView.findViewById(R.id.biz_place);
            profileImage = itemView.findViewById(R.id.biz_profile_image);
            postimage=(ImageView)itemView.findViewById(R.id.biz_image);

            itemView.setOnClickListener(this);

            msg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                   bizpostGS discposmsg = bizpost.get(position);
                    Intent a = new Intent(v.getContext(), bizmsgs.class);
                    a.putExtra("Clickable",  discposmsg);
                    v.getContext().startActivity(a);
                }
            });
        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            bizpostGS discposmsg = bizpost.get(position);
            Intent a = new Intent(v.getContext(), msgs.class);
            a.putExtra("Clickable",  discposmsg);
            v.getContext().startActivity(a);

        }
    }

}
