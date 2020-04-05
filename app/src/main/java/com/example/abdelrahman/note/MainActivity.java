package com.example.abdelrahman.note;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    DatabaseReference databaseReference_user;
    FirebaseAuth firebaseAuth;
    RecyclerView recyclerView;
    ArrayList<item> arraylist;
    TextView tv_user_name, tv_user_email;
    DatabaseReference databaseReference;
    SearchView searchView;
    DrawerLayout drawer;
    LinearLayout linearLayout_toolbar, layout_search, layout_homepage;
    ImageButton btn_nav, ibtn_search;
    ProgressBar progressBar;
    boolean doubleBackToExitPressedOnce = false;

    de.hdodenhof.circleimageview.CircleImageView profile_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        searchView = (SearchView) findViewById(R.id.rearch);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        btn_nav = (ImageButton) findViewById(R.id.btn_nav_draw);
        progressBar = findViewById(R.id.progressMainActivity);
        //ibtn_search = (ImageButton) findViewById(R.id.ibtn_search);
        String id_uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        linearLayout_toolbar = (LinearLayout) findViewById(R.id.layout_toolbar);
        //layout_search = (LinearLayout) findViewById(R.id.layout_search);
        //layout_homepage = (LinearLayout) findViewById(R.id.layout_Homepage);
        progressBar.setVisibility(View.VISIBLE);
        FloatingActionButton add_button = (FloatingActionButton) findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //      .setAction("Action", null).show();


                Intent intent = new Intent(MainActivity.this, Activity_add.class);
                startActivity(intent);
                finish();
            }
        });


//this.getSupportActionBar().setTitle("MY Note");
//this.getSupportActionBar().hide();
        //databaseReference.child("Note").child(id_uid).child().removeValue();
        arraylist = new ArrayList<>();
        //textView = (TextView) findViewById(R.id.nd_tv_name);

        recyclerView = (RecyclerView) findViewById(R.id.recycle);

        final Adapter adapter = new Adapter(this, arraylist, arraylist);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("Note").child(id_uid);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    String note = data.child("note").getValue(String.class);
                    String title = data.child("title").getValue(String.class);
                    String date = data.child("date").getValue(String.class);
                    String key = data.child("key").getValue(String.class);


                    arraylist.add(new item(title, note, date, key));
                    progressBar.setVisibility(View.GONE);
                    adapter.notifyDataSetChanged();


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


//        ibtn_search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
        //  layout_homepage.setVisibility(View.INVISIBLE);
        //layout_search.setVisibility(View.VISIBLE);

        // }
        //});
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {

                //      layout_homepage.setVisibility(View.VISIBLE);
                //    layout_search.setVisibility(View.INVISIBLE);
                return false;
            }
        });

        btn_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                drawer.openDrawer(Gravity.START);
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });


//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        final View header = navigationView.getHeaderView(0);


        databaseReference_user = FirebaseDatabase.getInstance().getReference("users").child(firebaseAuth.getCurrentUser().getUid());

        databaseReference_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String name = dataSnapshot.child("name").getValue(String.class);
                String email = dataSnapshot.child("email").getValue(String.class);
//                String image=dataSnapshot.child("image").getValue(String.class);
                tv_user_email = (TextView) header.findViewById(R.id.nd_email);
                tv_user_name = (TextView) header.findViewById(R.id.nd_tv_name);
//                profile_image=(de.hdodenhof.circleimageview.CircleImageView)header.findViewById(R.id.nd_image_profile);
                tv_user_name.setText(name);
                tv_user_email.setText(email);
//                Picasso.with(MainActivity.this).load(image).into(profile_image);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Toast.makeText(this, "double click to exite ", Toast.LENGTH_SHORT).show();
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;

            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_add) {

            Intent intent = new Intent(MainActivity.this, Activity_add.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_all) {
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);
            finish();


        } else if (id == R.id.logout) {
            logout();


        } else if (id == R.id.nav_profile) {
            startActivity(new Intent(MainActivity.this, profile.class));
            finish();

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void logout() {

        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setMessage("Are You want to Logout");
        alert.setIcon(R.drawable.ic_power_settings_new_black_24dp);
        alert.setTitle("Logout");
        alert.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                firebaseAuth.signOut();
                Intent intent = new Intent(MainActivity.this, Activity_signin.class);
                startActivity(intent);
            }
        });

        alert.setPositiveButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        alert.show();
    }


}

