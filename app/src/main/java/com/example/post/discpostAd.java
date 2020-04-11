package com.example.post;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class discpostAd extends RecyclerView.Adapter<discpostAd.DiscPostViewHolder>{
    public static final int MSG_RACHABLE = 0;
    public static final int MSG_NOT_REACHABLE =1;
    ArrayList<discpost> discposts = new ArrayList<discpost>();
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseRef,likesReff,deslikereff, Reff,creff;
    private ChildEventListener mChildEvent;
    Boolean likecheker =false;
    Boolean disclikecker =false;
    String currentUserId;
    private FirebaseAuth mAuth;
    FirebaseUser fuser;
    int dbcount;
    int dbbcount;
    int dbbbcount;
    String likesum;
    String a ;
    int i = 0;
    private String cReciever_id;



    public discpostAd() {

        mAuth=FirebaseAuth.getInstance();
        currentUserId=mAuth.getCurrentUser().getUid();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        creff=FirebaseDatabase.getInstance().getReference().child("Contacts");
        Reff = FirebaseDatabase.getInstance().getReference().child("user");
        mDatabaseRef = mFirebaseDatabase.getReference().child("Description Post");
        likesReff = FirebaseDatabase.getInstance().getReference().child("likes");
        deslikereff = FirebaseDatabase.getInstance().getReference().child("Description likes");
        mChildEvent = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            final    discpost pp = dataSnapshot.getValue(discpost.class);
                pp.setUid(dataSnapshot.getKey());
                a = pp.getPk();
                creff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                       if (dataSnapshot.hasChild(currentUserId)){
                           discposts.add(pp);
                           notifyItemInserted(discposts.size() - 1);
                       }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

               /* Reff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                            String  s = dataSnapshot.child("uid").getValue().toString();
                            if (s.equals(pp.getUid())){

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });*/

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
    public DiscPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        View view = LayoutInflater.from(context)
                .inflate(R.layout.nopicpostlayout, parent, false);
        return new DiscPostViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull DiscPostViewHolder holder, int position) {


        //   final  String key = discposts.get(position).getKey;
        fuser = FirebaseAuth.getInstance().getCurrentUser();

        discpost post = discposts.get(position);
        holder.description.setText(post.getDescription());
        holder.userName.setText(post.getFullName());
        holder.time.setText(post.getTime());
        holder.date.setText(post.getDate());
        Picasso.get().load(post.getPic()).into(holder.postimage);
        Picasso.get().load(post.getProfileImage()).into(holder.profileImage);

        if (post.getPic() != null) {
            holder.postimage.setVisibility(View.VISIBLE);

        } else {
            holder.postimage.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return discposts.size();

    }


    public class DiscPostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView date, time, userName, description,nolikes,count,msg;
        CircleImageView profileImage;
        ImageView postimage;
        ImageButton like,coment,dislike;


        public DiscPostViewHolder(@NonNull View itemView) {
            super(itemView);

         /*   mDatabaseRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 if (dataSnapshot.hasChild(a)){
                     mDatabaseRef.child(a).child("likes").addValueEventListener(new ValueEventListener() {
                         @Override
                         public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                             like.setImageResource(R.drawable.rheart);
                             dbbcount = (int)dataSnapshot.getChildrenCount();
                             count.setText(Integer.toString(dbbcount));
                         }

                         @Override
                         public void onCancelled(@NonNull DatabaseError databaseError) {

                         }
                     });
                 }else {
                     count.setText("0");
                     like.setImageResource(R.drawable.wheart);
                 }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            mDatabaseRef.child(a).child("likes").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()){

                        like.setImageResource(R.drawable.rheart);
                        dbbcount = (int)dataSnapshot.getChildrenCount();
                        count.setText(Integer.toString(dbbcount));
                    }else{
                        count.setText("0");
                        like.setImageResource(R.drawable.wheart);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            }); */


            msg = itemView.findViewById(R.id.noPost_reach);
            count = itemView.findViewById(R.id.noPost_value_count);
            like = itemView.findViewById(R.id.noPost_like);
            coment = itemView.findViewById(R.id.nopost_comment);
            nolikes = itemView.findViewById(R.id.noPost_like_count);
            date = itemView.findViewById(R.id.nopost_date);
            time = itemView.findViewById(R.id.nopost_time);
            userName = itemView.findViewById(R.id.nopost_user_name);
            description = itemView.findViewById(R.id.nopost_description);
            profileImage = itemView.findViewById(R.id.nopost_profile_image);
            postimage=(ImageView)itemView.findViewById(R.id.post_image);

            itemView.setOnClickListener(this);

            msg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();
                    discpost discposmsg = discposts.get(position);
                    Intent a = new Intent(v.getContext(), msgs.class);
                    a.putExtra("Clickable",  discposmsg);
                    v.getContext().startActivity(a);


                }
            });
            coment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();
                    discpost discpostClick = discposts.get(position);
                    Intent intent = new Intent(v.getContext(), coment.class);
                    intent.putExtra("Clickable", discpostClick);
                    v.getContext().startActivity(intent);

                }
            });

            like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    i++;
                    Handler handler =new Handler();
                    Runnable runn = new Runnable() {
                        @Override
                        public void run() {
                            i = 0 ;
                        }
                    };
                    if(i==1){
                        if (a != null && currentUserId != null){
                            HashMap picpostmap = new HashMap();
                            picpostmap.put("uid", currentUserId);
                            mDatabaseRef.child(a).child("likes").child(currentUserId).updateChildren(picpostmap).addOnCompleteListener(new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {
                                    if (task.isSuccessful()){
                                        like.setImageResource(R.drawable.rheart);
                                        if (a != null){
                                            mDatabaseRef.child(a).child("likes").addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    dbcount = (int)dataSnapshot.getChildrenCount();
                                                    count.setText(Integer.toString(dbcount));
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });
                                        }
                                    }
                                }
                            });
                        }

                    }else if (i==2){
                        mDatabaseRef.child(a).child("likes").child(currentUserId).removeValue();
                        like.setImageResource(R.drawable.wheart);
                        mDatabaseRef.child(a).child("likes").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()){
                                    dbbbcount = (int)dataSnapshot.getChildrenCount();
                                    count.setText(Integer.toString(dbbbcount));
                                    i=0;
                                }else{
                                    count.setText("0");
                                    i=0;
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }
                }
            });
         /*   like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (a != null && currentUserId != null){
                        likecheker =true ;
                        HashMap picpostmap = new HashMap();
                        picpostmap.put("uid", currentUserId);
                        mDatabaseRef.child(a).child("likes").updateChildren(picpostmap).addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                if (task.isSuccessful()){
                                    like.setImageResource(R.drawable.rheart);
                                    likecheker =false;
                                    if (a != null){
                                        mDatabaseRef.child(a).child("likes").addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                dbcount = (int)dataSnapshot.getChildrenCount();
                                                count.setText(Integer.toString(dbcount));
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    }

                                }
                            }
                        });
                    }
                }
            });*/


        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            discpost discpostClick = discposts.get(position);
            Intent intent = new Intent(v.getContext(), clickPost.class);
            intent.putExtra("Clickable", discpostClick);
            v.getContext().startActivity(intent);



        }
    }
}
