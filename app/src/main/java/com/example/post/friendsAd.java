package com.example.post;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
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
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class friendsAd extends RecyclerView.Adapter<friendsAd .friendsVh> implements Filterable {
    Context mContext;
    ArrayList<friendsGs> arrayList = new ArrayList<friendsGs>();
    ArrayList<friendsGs> exampleListFull = new ArrayList<friendsGs>();
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseRef;
    private ChildEventListener mChildEvent;

    public  friendsAd(Context mContext, ArrayList<friendsGs> arrayList){
     this.mContext =mContext;
     this.arrayList=arrayList;
     this.exampleListFull= new ArrayList<>(arrayList);
    }

    @NonNull
    @Override
    public friendsVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.friends, parent, false);

        return new friendsVh(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull friendsVh holder, int position) {
        friendsGs post = arrayList.get(position);


        holder.userName.setText(post.getFullName());
        holder.time.setText(post.getStatus());
        Picasso.get().load(post.getProfileImage()).into(holder.profileImage);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<friendsGs> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(exampleListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for ( friendsGs item : exampleListFull) {
                    if (item.getFullName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
           arrayList.clear();
            arrayList.addAll((List)  results.values);
            notifyDataSetChanged();
        }
    };


    public class friendsVh extends RecyclerView.ViewHolder  implements View.OnClickListener{
        TextView  time, userName;
        CircleImageView profileImage;
        ImageView postimage;


        public friendsVh(@NonNull View itemView) {
            super(itemView);




            time = itemView.findViewById(R.id.friend_status);
            userName = itemView.findViewById(R.id.friend_user_name);
            profileImage = itemView.findViewById(R.id.friend_profile_image);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            friendsGs discpostClick = arrayList.get(position);
            Intent intent = new Intent(v.getContext(), Ffriends_activity.class);
            intent.putExtra("Clickable", discpostClick);
            v.getContext().startActivity(intent);

        }
    }
}

