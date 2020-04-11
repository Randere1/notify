package com.example.post;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


 public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    List<chat> mchat ;
    private  Context mContext;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseRef, likesReff, deslikereff;
    private ChildEventListener mChildEvent;
    Boolean likecheker = false;
    Boolean disclikecker = false;
    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT =1;
    String currentUserId;
    private FirebaseAuth mAuth;
    FirebaseUser fuser;
    String likesum;
    String a;
    private String cReciever_id, cSender_id;

public  MessageAdapter(Context mContext , List<chat>mChat) {
    this.mchat =mChat;
    this.mContext =mContext;

     }


    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.message_layout_user, parent, false);
        return new MessageViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull  MessageViewHolder holder, int position) {

        fuser = FirebaseAuth.getInstance().getCurrentUser();
          chat post = mchat.get(position);
          holder.receiverMessageText.setText(post.getMessage());
        holder.senderMessageText.setText(post.getMessage());

        holder.receiverMessageText.setVisibility(View.INVISIBLE);
     holder.senderMessageText.setVisibility(View.INVISIBLE);


        if (post.getSender() !=null && fuser.getUid() != null && post.getSender().equals(fuser.getUid()))
        {
            holder.senderMessageText.setVisibility(View.VISIBLE);

            holder.senderMessageText.setBackgroundResource(R.drawable.sender_text_backgrnd);
            holder.senderMessageText.setTextColor(Color.BLACK);
            holder.senderMessageText.setText(post.getMessage() + "\n \n" + post.getTime() + " - " );
        }
        else
        {

            holder.receiverMessageText.setVisibility(View.VISIBLE);

            holder.receiverMessageText.setBackgroundResource(R.drawable.receiver_text_background);
            holder.receiverMessageText.setTextColor(Color.BLACK);
            holder.receiverMessageText.setText(post.getMessage() + "\n \n" + post.getTime() + " - " );
        }

    }

    @Override
    public int getItemCount() {
        return mchat.size();

    }


    public class  MessageViewHolder extends RecyclerView.ViewHolder  {



        public TextView senderMessageText, receiverMessageText;
        public ImageView messageSenderPicture, messageReceiverPicture;


        public  MessageViewHolder(@NonNull View itemView) {
            super(itemView);

            senderMessageText = (TextView) itemView.findViewById(R.id.send_msg_txt);
            receiverMessageText = (TextView) itemView.findViewById(R.id.receiver_msg_txt);




        }
    }
}
/*

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT =;
    ArrayList<chat> mchat = new ArrayList<chat>();
    private FirebaseAuth mAuth;
    private Context mContext;
    FirebaseUser fuser;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseRef;
    private ChildEventListener mChildEvent;



    public MessageAdapter() {
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseRef = mFirebaseDatabase.getReference().child("chats");
        mChildEvent = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                chat c =dataSnapshot.getValue(chat.class);
                mchat.add(c);
                notifyItemInserted(mchat.size() - 1);
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


    public class MessageViewHolder extends RecyclerView.ViewHolder {
        public TextView show_msg;


        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);

            show_msg = (TextView) itemView.findViewById(R.id.show_msg);


        }
    }

 /*   @Override
    public int getItemViewType(int position) {
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        if (mchat.get(position).getSender().equals(fuser.getUid())) {
            return MSG_TYPE_RIGHT;

        } else {
            return MSG_TYPE_LEFT;
        }
    }*/
/*
    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
     //   if (i == MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(context)
                    .inflate(R.layout.chet_item_right, viewGroup, false);
            return new MessageViewHolder(view);
      /*  } else {
            View view = LayoutInflater.from(context)
                    .inflate(R.layout.chat_item_left, viewGroup, false);

            return new MessageViewHolder(view);
        }*/
/*
    }


    @Override
    public void onBindViewHolder(@NonNull final MessageViewHolder messageViewHolder, int i) {

        chat c = mchat.get(i);
        messageViewHolder.show_msg.setText(c.getMessage());
    }


    @Override
    public int getItemCount() {
        return mchat.size();
    }
} */

/*
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    private FirebaseAuth mAuth;
    ArrayList<Messages> userMessagesList = new ArrayList<Messages>();
    private FirebaseDatabase mFirebaseDatabase ;
    private DatabaseReference mDatabaseRef , vDatabase;
    private ChildEventListener mChildEvent;
    FirebaseUser fuser;
    String p , j , c_rec ,sender, messageSenderId;



    public class MessageViewHolder extends RecyclerView.ViewHolder {
        public TextView senderMessageText, receiverMessageText;
        public ImageView messageSenderPicture, messageReceiverPicture;


        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);

            senderMessageText = (TextView) itemView.findViewById(R.id.send_msg_txt);
            receiverMessageText = (TextView) itemView.findViewById(R.id.receiver_msg_txt);


        }
    }




    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.message_layout_user, viewGroup, false);

        mAuth = FirebaseAuth.getInstance();



        return new MessageViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull final MessageViewHolder messageViewHolder, int i)
    {
         messageSenderId = mAuth.getCurrentUser().getUid();
        Messages messages = userMessagesList.get(i);


       String fromUserId = messages.getFrom();
        String fromMessageType = messages.getType();
        p = messages.getTo();
        j = messages.getFrom();


        messageViewHolder.receiverMessageText.setVisibility(View.VISIBLE);
        messageViewHolder.senderMessageText.setVisibility(View.VISIBLE);


        if (fromMessageType.equals("text"))
        {
            if (fromUserId.equals(messageSenderId))
            {
                messageViewHolder.senderMessageText.setVisibility(View.VISIBLE);

                messageViewHolder.senderMessageText.setBackgroundResource(R.drawable.sender_text_backgrnd);
                messageViewHolder.senderMessageText.setTextColor(Color.BLACK);
                messageViewHolder.senderMessageText.setText(messages.getMessage() + "\n \n" + messages.getTime() + " - " + messages.getDate());
            }
            else
            {

                messageViewHolder.receiverMessageText.setVisibility(View.VISIBLE);

                messageViewHolder.receiverMessageText.setBackgroundResource(R.drawable.receiver_text_background);
                messageViewHolder.receiverMessageText.setTextColor(Color.BLACK);
                messageViewHolder.receiverMessageText.setText(messages.getMessage() + "\n \n" + messages.getTime() + " - " + messages.getDate());
            }
        }
    }




    @Override
    public int getItemCount()
    {
        return userMessagesList.size();
    }

}
*/
