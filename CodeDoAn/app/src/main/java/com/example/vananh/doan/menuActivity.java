package com.example.vananh.doan;

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
            getSupportActionBar().setTitle("Trang chủ");

            // Handle the camera action
        } else if (id == R.id.nav_LamBaiThi) {
            LamBaiThi flag = new LamBaiThi();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content_main, flag, flag.getTag()).addToBackStack(null).commit();
            getSupportActionBar().setTitle("Làm bài thi");
        } else if (id == R.id.nav_Diem) {

        } else if (id == R.id.nav_HuongDan) {
            HuongDan flagHuongDan = new HuongDan();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content_main, flagHuongDan, flagHuongDan.getTag()).commit();
            getSupportActionBar().setTitle("Hướng dẫn thi thực hành");

        } else if (id == R.id.nav_DangNhap) {
            FlagDangNhap DangNhap = new FlagDangNhap();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content_main, DangNhap, DangNhap.getTag()).commit();
            getSupportActionBar().setTitle("Đăng nhập");

        } else if (id == R.id.nav_view) {

        }
        else  if (id==R.id.nav_CauHoi){
            FragCauHoi fragCauHoi = new FragCauHoi();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content_main, fragCauHoi, fragCauHoi.getTag()).commit();
            getSupportActionBar().setTitle("Danh sách câu hỏi");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
