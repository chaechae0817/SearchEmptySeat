package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MainActivity extends AppCompatActivity {
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    View navHeader;
    TextView tvUname,tvUNum;
    public static String uName,uNum,uType;
    public static String[] sequence,userId,userPwd,userType,userName,phonenumber;
    public static String url;
    SharedPreferences preferences;
    NavigationView navigationView;
    Menu menu;
    MenuItem menuItem;
    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);// Fragment로 사용할 MainActivity내의 layout공간을 선택합니다.
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate (R.menu.searchmenu , menu); //searchMenu 와 연결
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(toggle.onOptionsItemSelected(item)){
            preferences = getApplicationContext().getSharedPreferences("userInfo",MODE_PRIVATE);
            uName = preferences.getString("Id","");
            uNum = preferences.getString("Phone","");
            uType = preferences.getString("User_Type","");
            tvUname = (TextView) findViewById(R.id.naviName);//이름
            tvUNum = (TextView) findViewById(R.id.naviNum);//전화번호
            if(!uName.equals("")){
                tvUname.setText(uName+"님");
                tvUNum.setText("전화번호 : 0" +uNum+"");
                navigationView = (NavigationView) findViewById(R.id.nav_view);
                navHeader = navigationView.getHeaderView(0);

                menu = navigationView.getMenu();
                // group = navigationView.findViewById(R.id.myPageGroup);
                menu.setGroupVisible(0,true);
                menuItem = menu.findItem(R.id.login);
                menuItem.setVisible(false);

            }else if(uName.equals("")){
                tvUname.setText("");
                tvUNum.setText("");
                navigationView = (NavigationView) findViewById(R.id.nav_view);
                navHeader = navigationView.getHeaderView(0);
                menu = navigationView.getMenu();
                menu.setGroupVisible(0,false);
                menuItem = menu.findItem(R.id.login);
                MenuItem menuItem1 = menu.findItem(R.id.userhome);
                MenuItem menuItem2 = menu.findItem(R.id.category);
                menuItem.setVisible(true);
                menuItem1.setVisible(true);
                menuItem2.setVisible(true);
            }
            return false;


        }

        if(item.getItemId()==R.id.search){

            SearchFragment searchFragment = new SearchFragment();
            replaceFragment(searchFragment);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("음식점을 확인하세요");
        drawer = (DrawerLayout) findViewById(R.id.main_drawer);
        FragmentManager fragmentManager;
        LoginFragment loginFragment;
        MainFragment mainFragment;
        FavoriteFragment favoriteFragment;
        CategoryFragment categoryFragment;
        UserMypageFragment userMypageFragment;
        BusinessFragment businessFragment;
        //getSupportActionBar().setDisplayShowTitleEnabled(false);//액션바에 타이틀 안보이게
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toggle = new ActionBarDrawerToggle(this,drawer,R.string.d_open,R.string.d_close);
        toggle.syncState();


        favoriteFragment = new FavoriteFragment();
        userMypageFragment = new UserMypageFragment();
        categoryFragment = new CategoryFragment();
        loginFragment = new LoginFragment();
        mainFragment = new MainFragment();
        businessFragment = new BusinessFragment();
        replaceFragment(mainFragment); // mainFragment로 container 채움

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navHeader = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                if(id==R.id.login){
                    drawer.closeDrawers();
                    replaceFragment(loginFragment);
                }else if (id==R.id.userhome){
                    drawer.closeDrawers();
                    replaceFragment(mainFragment);
                }else if (id==R.id.favorite){
                    drawer.closeDrawers();
                    replaceFragment(favoriteFragment);
                }else if (id==R.id.mypage) {
                    drawer.closeDrawers();
                    //#####mypage이동 변환 type별로 걸어줘야함//
                    if(uType.equals("User"))
                        replaceFragment(userMypageFragment);
                    else replaceFragment(businessFragment);
                }else if (id==R.id.category) {
                    drawer.closeDrawers();
                    replaceFragment(categoryFragment);
                }else if(id==R.id.logout){
                    drawer.closeDrawers();
                    preferences =getApplicationContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor =preferences.edit();
                    editor.clear();
                    editor.commit();
                }

                return false;
            }
        });
    }
}