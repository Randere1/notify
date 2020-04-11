package com.example.post;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class nav_Message extends AppCompatActivity {
    private FrameLayout friensFrame;
    private Button newbtn;
    private Toolbar mToolbar;
    private LinearLayoutManager linearLayoutManager;
    private MessageAdapter messageAdapter;
    private RecyclerView recyclerView,recyclerView1;
    ImageButton create;
    RelativeLayout relativeLayout;
    TextView textView;
    EditText editText;
    ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav__message);


        create=findViewById(R.id.create);

        mToolbar = findViewById(R.id.nav_msg_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        relativeLayout=findViewById(R.id.nav_msg_lay);
        textView=findViewById(R.id.nav_msg_close);
        imageButton =findViewById(R.id.nav_msg_search);
        editText =findViewById(R.id.nav_msg_search_box);

        postMsgAd adapter = new postMsgAd();
        recyclerView = findViewById(R.id.texted);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager( this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        contactsAd adapter1 = new contactsAd();
        recyclerView1 = findViewById(R.id.nav_msg_result);
        recyclerView1.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager( this);
        linearLayoutManager1.setReverseLayout(true);
        linearLayoutManager1.setStackFromEnd(true);
        recyclerView1.setLayoutManager(linearLayoutManager1);
        recyclerView1.setAdapter(adapter1);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relativeLayout.setVisibility(View.GONE);
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             relativeLayout.setVisibility(View.VISIBLE);
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if(id == android.R.id.home ){
            SendUserToMain();
        }
        return super.onOptionsItemSelected(item);



    }

    private void SendUserToMain() {

        Intent e=new Intent(nav_Message.this,MainActivity.class);
        startActivity(e);

    }
}
