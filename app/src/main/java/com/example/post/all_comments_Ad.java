package com.example.post;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class all_comments_Ad extends RecyclerView.Adapter<all_comments_Ad.all_comments_vh> {

    List<all_commentsGs> comg ;
    private Context mContext;
    private FirebaseAuth mAuth;
    FirebaseUser fuser;

    public  all_comments_Ad(Context mContext , List<all_commentsGs>comg) {
        this.comg =comg;
        this.mContext =mContext;

    }


    @NonNull
    @Override
    public all_comments_vh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.comment_layout, parent, false);
        return new all_comments_vh(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull  all_comments_vh holder, int position) {

        fuser = FirebaseAuth.getInstance().getCurrentUser();
        all_commentsGs post = comg.get(position);

        holder.description.setText(post.getComment());
        holder.userName.setText(post.getName());
        holder.time.setText(post.getTime());
        holder.date.setText(post.getDate());
        Picasso.get().load(post.getProfile()).into(holder.profileImage);

    }

    @Override
    public int getItemCount() {
        return comg.size();

    }


    public class  all_comments_vh extends RecyclerView.ViewHolder  {



        TextView date, time, userName, description;
        CircleImageView profileImage;

       public  all_comments_vh(@NonNull View itemView) {
            super(itemView);

           date = itemView.findViewById(R.id.nopost_date);
           time = itemView.findViewById(R.id.nopost_time);
           userName = itemView.findViewById(R.id.nopost_user_name);
           description = itemView.findViewById(R.id.nopost_comment);
           profileImage = itemView.findViewById(R.id.nopost_profile_image);



        }
    }
}