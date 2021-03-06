package com.example.roisindonovan.myapplication;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

/**
 * Created by Roisin Donovan on 19/06/2017.
 */

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private FirebaseAuth mAuth;
    private ViewPager mViewPager;
    private SectionPagerAdapter mSectionPagerAdapter;

    private DatabaseReference mUserRef;
    private TabLayout mTabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //database reference
        mAuth = FirebaseAuth.getInstance();

        mToolbar = (Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Spot");

        if(mAuth.getCurrentUser()!=null) {
            mUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());
        }
        mViewPager = (ViewPager) findViewById(R.id.main_tabPager);
        mSectionPagerAdapter = new SectionPagerAdapter(getSupportFragmentManager());

        mViewPager.setAdapter(mSectionPagerAdapter);

        mTabLayout = (TabLayout) findViewById(R.id.main_tabs);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    //if user is online
    @Override
    public void onStart() {
        super.onStart();

       FirebaseUser currentUser = mAuth.getCurrentUser();

       if (currentUser == null) {

            sendToStart();

        }else {

            mUserRef.child("online").setValue(true);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser !=null) {

            mUserRef.child("online").setValue(ServerValue.TIMESTAMP);
        }
    }

    private void sendToStart() {
        Intent startIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(startIntent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }
    //dropdown menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.main_logout) {

            FirebaseAuth.getInstance().signOut();
            sendToStart();
        }
        if(item.getItemId()== R.id.main_settings_btn){
            Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(settingsIntent);
        }
        if(item.getItemId()==R.id.all_users_btn){
            Intent settingsIntent = new Intent(MainActivity.this, UserActivity.class);
            startActivity(settingsIntent);
        }
        return true;
    }
}

