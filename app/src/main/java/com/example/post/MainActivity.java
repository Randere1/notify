package com.example.post;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

import static java.security.AccessController.getContext;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private RecyclerView picpostRecycler;
    private NavigationView navigationView;
    private Toolbar mToolbar;
    private ActionBarDrawerToggle mToggle;
    private FirebaseAuth mAuth, eAuth;
    private DatabaseReference Reff,friendReff;
    String currentUserId;
    private ImageButton post, friend_search, friend_searchi, countdown,msg;
    EditText text1, namesearch;
    private ImageView image;
    private CircleImageView navprofileimage;
    private TextView navprofilename, navid, close, close2;
    String uniqueId = UUID.randomUUID().toString();
    private String saveCurrentDate, SaveCurrentTime, PostRandomName, downloadUri;
    String identity;
    RelativeLayout relativeLayout, relativeLayout1;
    private RecyclerView recyclerView, recyclerView1, recyclerView2;
    SearchView searchView;
    friendsAd friendsAd;
    ArrayList<friendsGs> arrayList;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        friendsDisplay();


        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();

        Calendar calFordDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
        saveCurrentDate = currentDate.format(calFordDate.getTime());

        Calendar calFordTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
        SaveCurrentTime = currentTime.format(calFordTime.getTime());

        PostRandomName = saveCurrentDate + SaveCurrentTime;
        Reff = FirebaseDatabase.getInstance().getReference().child("users").child(currentUserId);
      //  friendReff = FirebaseDatabase.getInstance().getReference().child("users");
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseRef = mFirebaseDatabase.getReference().child("Friend Requests Received");


        post = (ImageButton) findViewById(R.id.main_post);
        msg = (ImageButton) findViewById(R.id.friend_msg);
        close = findViewById(R.id.friends_close);
        searchView =findViewById(R.id.search_view);
        // namesearch =findViewById(R.id.friends_search_box);
        image = (ImageView) findViewById(R.id.imageView2);
        drawerLayout = findViewById(R.id.main_drawer);
        mToolbar = findViewById(R.id.main_page_bar);
        navigationView = findViewById(R.id.main_nav);
        friend_search = findViewById(R.id.friends_search1);
        countdown = findViewById(R.id.friends_count);
        relativeLayout = findViewById(R.id.friend_lay);
        relativeLayout1 = findViewById(R.id.post_lay);


        close2 = findViewById(R.id.accpt_rqst);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToggle = new ActionBarDrawerToggle(this, drawerLayout, mToolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);


        navprofileimage = (CircleImageView) navigationView.getHeaderView(0).findViewById(R.id.nav_dp);
        navprofilename = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_user);
        navid = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_id);

        identity = navid.getText().toString();


        recyclerView2 = findViewById(R.id.friends_result);
        recyclerView2.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(this);
        linearLayoutManager3.setReverseLayout(true);
        linearLayoutManager3.setStackFromEnd(true);
        recyclerView2.setLayoutManager(linearLayoutManager3);



        requestsAd adapter2 = new requestsAd();
        recyclerView1 = findViewById(R.id.acceptreq);
        recyclerView1.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        linearLayoutManager2.setReverseLayout(true);
        linearLayoutManager2.setStackFromEnd(true);
        recyclerView1.setLayoutManager(linearLayoutManager2);
        recyclerView1.setAdapter(adapter2);

        discpostAd adapter = new discpostAd();
        picpostRecycler = findViewById(R.id.Posts);
        picpostRecycler.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        picpostRecycler.setLayoutManager(linearLayoutManager);
        picpostRecycler.setAdapter(adapter);

      /*  recyclerView = findViewById(R.id.private_messages_list_of_users);
        recyclerView.setHasFixedSize(true); */

     /*   friendsAd adapter1 = new friendsAd();
        recyclerView = findViewById(R.id.private_messages_list_of_users);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager( this);
        linearLayoutManager1.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager1);
        recyclerView.setAdapter(adapter1);*/

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
              friendsAd.getFilter().filter(newText);
                return false;
            }
        });



            close2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerView1.setVisibility(View.GONE);
                    close2.setVisibility(View.GONE);

                }
            });
            msg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent ui = new Intent(MainActivity.this, nav_Message.class);
                    startActivity(ui);

                }
            });
        friend_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final PopupMenu popupMenu = new PopupMenu(MainActivity.this , friend_search);
                popupMenu.getMenuInflater().inflate(R.menu.pop_up_menu , popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.btn_fiend:
                                relativeLayout.setVisibility(View.VISIBLE);
                                popupMenu.dismiss();
                                break;
                            case R.id.btn_post:
                                relativeLayout1.setVisibility(View.VISIBLE);
                                popupMenu.dismiss();
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relativeLayout.setVisibility(View.GONE);
            }
        });


        countdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDatabaseRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                            close2.setVisibility(View.VISIBLE);
                            recyclerView1.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });


        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pin = new Intent(v.getContext(), allpost.class);
                pin.putExtra("myid",identity);
                startActivity(pin);
                finish();
            }
        });

        Reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    String  s = dataSnapshot.child("uid").getValue().toString();
                    String  full = dataSnapshot.child("FullName").getValue().toString();
                    String  Image = dataSnapshot.child("ProfileImage").getValue().toString();

                    navid.setText(s);
                    navprofilename.setText(full);
                    Picasso.get().load(Image).placeholder(R.drawable.profile).into(navprofileimage);

                }else {
                    Toast.makeText(MainActivity.this, "please select profile image first" , Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void friendsDisplay() {
        arrayList=new ArrayList<>();
        DatabaseReference refrence = FirebaseDatabase.getInstance().getReference().child("users");
        refrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList = new ArrayList<>();
                for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) {
                        arrayList.add(eventSnapshot.getValue(friendsGs.class));
                    }
                friendsAd = new friendsAd(MainActivity.this ,arrayList);
                recyclerView2.setAdapter(friendsAd);
                friendsAd.notifyDataSetChanged();

                }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    protected void onStart() {

        FirebaseUser onlineuser = mAuth.getCurrentUser();
        if (onlineuser == null) {
            DirectUserToLoginActivity();
        } else {
            isUserInDataBase();
        }

        super.onStart();
    }

    private void isUserInDataBase() {

        final String current_user_id = mAuth.getCurrentUser().getEmail();
        Reff.child(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String  Image = dataSnapshot.child("uid").getValue().toString();
                    if (!Image.equals(current_user_id))
                        userMovedToSetUpActivity();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void userMovedToSetUpActivity() {
        Intent ra = new Intent(MainActivity.this, setprofile.class);
        startActivity(ra);
        finish();
    }

    private void DirectUserToLoginActivity() {
        Intent inIntent = new Intent(MainActivity.this, login.class);
        startActivity(inIntent);
        finish();

    }

    @Override
        public boolean onNavigationItemSelected (@NonNull MenuItem item){

        Menu menu =navigationView.getMenu();
        switch (item.getItemId()) {

            case R.id.nav_logout:
                mAuth.signOut();
                DirectUserToLoginActivity();
                break;
            case R.id.nav_jon:
                Intent a = new Intent(MainActivity.this, job.class);
                startActivity(a);

                break;
            case R.id.nav_chief:
                Intent u = new Intent(MainActivity.this, chief.class);
                startActivity(u);

                break;
            case R.id.nav_house:
                Intent k = new Intent(MainActivity.this, house.class);
                startActivity(k);

                break;

            case R.id.nav_Bussines:
                Intent b = new Intent(MainActivity.this, biz.class);
                b.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(b);
                break;

            case R.id.nav_Advertisement:
                menu.setGroupVisible(R.id.nav_add,true);
                break;






            case R.id.nav_Messages:
                Intent q = new Intent(MainActivity.this, nav_Message.class);
                q.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(q);


                break;



            default:
                break;
        }

        return false;
        }


    }

