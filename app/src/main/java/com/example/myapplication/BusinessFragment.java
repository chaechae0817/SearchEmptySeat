package com.example.myapplication;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class BusinessFragment extends Fragment {
    Button menusetting, batchsetting, storeinformation;
    SharedPreferences preferences;
    String url, getStoreName, store_Id;
    TextView store_Name;
    String [] storeId,storeName;
    MainActivity mainActivity;
    public void sendStoreRequest() {
        url="http://122.34.93.216:8080/capstone/storeInformation_json.jsp";
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("Store_resultValue",response);

                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("storeInformation");
                            storeId = new String[jsonArray.length()];
                            storeName = new String[jsonArray.length()];
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObj = jsonArray.getJSONObject(i);
                                storeId[i] = jsonObj.getString("store_id");
                                storeName[i] = jsonObj.getString("store_name");
                            }
                            //store 값 최신화 시키는 코드
                            preferences = getContext().getSharedPreferences("StoreInfo",MODE_PRIVATE);
                            getStoreName = preferences.getString("storeName","");

                            for(int i=0;i<jsonArray.length();i++){

                                if(getStoreName.equals(storeName[i])){
                                    store_Id = storeId[i];
                                    store_Name.setText(storeName[i]);//가게 이름 바꾸기
                                    //Log.v("preference에서 불러온 카테고리",categoryInfo);
                                    Log.v("가게이름", storeName[i]);
                                }
                            }
                        } catch (JSONException e) {}

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_business, container, false);

        store_Name = (TextView)view.findViewById(R.id.storename);

        menusetting =(Button) view.findViewById(R.id.menusetting);
        batchsetting = (Button)view.findViewById(R.id.batchsetting);
        storeinformation = (Button)view.findViewById(R.id.storeinformation);

        sendStoreRequest();

        MenusettingFragment menusettingFragment;
        SitbatchFragment sitbatchFragment;
        StoreinfoFragment storeinfoFragment;

        menusettingFragment = new MenusettingFragment(); //메뉴세팅 프래그먼트 연결
        sitbatchFragment = new SitbatchFragment();
        storeinfoFragment = new StoreinfoFragment();
        mainActivity=(MainActivity)getActivity();

        menusetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.replaceFragment(menusettingFragment);
            }
        });
        batchsetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mainActivity.replaceFragment(sitbatchFragment);
            }
        });
        storeinformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mainActivity.replaceFragment(storeinfoFragment);
            }
        });

        return view;
    }
}