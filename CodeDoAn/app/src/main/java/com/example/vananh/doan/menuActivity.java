package com.example.vananh.doan;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.vananh.doan.ChucNang.FlagDangNhap;
import com.example.vananh.doan.ChucNang.FragCauHoi;
import com.example.vananh.doan.ChucNang.FragDiemThi;
import com.example.vananh.doan.ChucNang.Home;
import com.example.vananh.doan.ChucNang.HuongDan;
import com.example.vananh.doan.ChucNang.LamBaiThi;

public class menuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //default load login

        Intent intent = getIntent();
        int ketQua = intent.getIntExtra("ketQua", 0);
        if(ketQua == 0){
            FlagDangNhap DangNhap = new FlagDangNhap();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content_main, DangNhap, DangNhap.getTag()).commit();
        }
        else {
            Home flagHome = new Home();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content_main, flagHome, flagHome.getTag()).addToBackStack(null).commit();
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Home flagHome = new Home();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content_main, flagHome, flagHome.getTag()).addToBackStack(null).commit();

            // Handle the camera action
        } else if (id == R.id.nav_LamBaiThi) {
            LamBaiThi flag = new LamBaiThi();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content_main, flag, flag.getTag()).addToBackStack(null).commit();

        } else if (id == R.id.nav_Diem) {
            FragDiemThi frag = new FragDiemThi();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content_main, frag, frag.getTag()).addToBackStack(null).commit();

        } else if (id == R.id.nav_HuongDan) {
            HuongDan flagHuongDan = new HuongDan();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content_main, flagHuongDan, flagHuongDan.getTag()).commit();


        } else if (id == R.id.nav_DangNhap) {
            FlagDangNhap DangNhap = new FlagDangNhap();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content_main, DangNhap, DangNhap.getTag()).commit();

        } else if (id == R.id.nav_view) {

        }
//        else  if (id==R.id.nav_CauHoi){
//            FragCauHoi fragCauHoi = new FragCauHoi();
//            FragmentManager manager = getSupportFragmentManager();
//            manager.beginTransaction().replace(R.id.content_main, fragCauHoi, fragCauHoi.getTag()).commit();
//        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
