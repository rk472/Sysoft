package com.studio.smarters.sysoft;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.studio.smarters.sysoft.Fragments.AboutFragment;
import com.studio.smarters.sysoft.Fragments.ContactFragment;
import com.studio.smarters.sysoft.Fragments.CoursesFragment;
import com.studio.smarters.sysoft.Fragments.EnquiryFragment;
import com.studio.smarters.sysoft.Fragments.FacultiesFragment;
import com.studio.smarters.sysoft.Fragments.GalleryFragment;
import com.studio.smarters.sysoft.Fragments.HomeFragment;
import com.studio.smarters.sysoft.Fragments.InternshipFragment;
import com.studio.smarters.sysoft.Fragments.SuccessFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FragmentTransaction fragmentTransaction;
    private Boolean exit = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_container,new HomeFragment(),"home");
        fragmentTransaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(getSupportFragmentManager().findFragmentById(R.id.main_container).getTag().equals("home")) {
                if (exit) {
                    finish(); // finish activity
                } else {
                    Toast.makeText(this, "Press Back again to Exit.",
                            Toast.LENGTH_SHORT).show();
                    exit = true;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            exit = false;
                        }
                    }, 3 * 1000);
                }
            }else{
                Fragment f=new HomeFragment();
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_container, f,"home");
                fragmentTransaction.commit();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_profile) {
            startActivity(new Intent(this,LoginActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Fragment f = null;
        int id = item.getItemId();
        String tag = null;
        if (id == R.id.nav_home) {
            f = new HomeFragment();
            tag="home";
        } else if (id == R.id.nav_faculties) {
            f = new FacultiesFragment();
            tag="other";
        } else if (id == R.id.nav_courses) {
            f = new CoursesFragment();
            tag="other";
        } else if (id == R.id.nav_enquiry) {
            f = new EnquiryFragment();
            tag="other";
        } else if (id == R.id.nav_about) {
            f = new AboutFragment();
            tag="other";
        } else if (id == R.id.nav_contact) {
            f = new ContactFragment();
            tag="other";
        } else if (id == R.id.nav_internship) {
            f = new InternshipFragment();
            tag="other";
        } else if (id == R.id.nav_gallery) {
            f = new GalleryFragment();
            tag="other";
        }else if (id == R.id.nav_success) {
            f = new SuccessFragment();
            tag="other";
        }
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_container, f,tag);
        fragmentTransaction.commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void fb(View view) {
        Intent i;
        i= new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/Sysoft-Technology-517843175087542/?ref=br_rs"));
        startActivity(i);
    }
    public void map(View v){
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?daddr=20.284285,85.809221"));
        startActivity(intent);
    }
    public void call(View v){
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CALL_PHONE}, 1);
            return;
        }
        Intent i = new Intent(Intent.ACTION_CALL);
        i.setData(Uri.parse("tel:+918114979688"));
        startActivity(i);
    }
}
