package com.example.myapplication;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;


public class CategoryFragment extends Fragment {
    ArrayList<ItemData> dataList = new ArrayList<>();
    String url,imageUrl;

    ImageView hansik, jungsik, ilsik, yangsik, fastfood;
    MainActivity mainActivity ;
    SharedPreferences preferences;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_category, container, false);

        hansik =(ImageView) view.findViewById(R.id.hansikbtn);
        jungsik=(ImageView) view.findViewById(R.id.jungsikbtn);
        ilsik = (ImageView) view.findViewById(R.id.ilsikbtn);
        yangsik=(ImageView) view.findViewById(R.id.yangsikbtn);
        fastfood = (ImageView) view.findViewById(R.id.fastfoodbtn);

        HansikFragment hansikFragment;
        //JungsikFragment jungsikFragment;
        //IlsikFragment ilsikFragment;
        //YangsikFragment yangsikFragment;
        //FastfoodFragment fastfoodFragment;
        mainActivity = (MainActivity)getActivity();
        hansikFragment = new HansikFragment();
        preferences =getContext().getSharedPreferences("StoreInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =preferences.edit();
        hansik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("category","Korean Food");
                editor.commit();


                mainActivity.replaceFragment(hansikFragment);//->recyclerView가 있는 프래그먼트로 이동
            }
        });
        jungsik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("category","Chinese Food");
                editor.commit();


                mainActivity.replaceFragment(hansikFragment);//->recyclerView가 있는 프래그먼트로 이동
            }
        });
        ilsik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("category","Japan Food");
                editor.commit();


                mainActivity.replaceFragment(hansikFragment);//->recyclerView가 있는 프래그먼트로 이동
            }
        });
        yangsik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("category","Western Food");
                editor.commit();


                mainActivity.replaceFragment(hansikFragment);//->recyclerView가 있는 프래그먼트로 이동
            }
        });
        fastfood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("category","Fast Food");
                editor.commit();


                mainActivity.replaceFragment(hansikFragment);//->recyclerView가 있는 프래그먼트로 이동
            }
        });
        return view;
    }
}