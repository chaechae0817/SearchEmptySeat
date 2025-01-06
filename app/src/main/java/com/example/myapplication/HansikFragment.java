package com.example.myapplication;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class HansikFragment extends Fragment {
    ArrayList<ItemData> dataList = new ArrayList<>();
    RecyclerView recyclerView;
    public CustomAdapter customAdapter;
    LinearLayoutManager linearLayoutManager;
    MainActivity mainActivity;

    String url,imageUrl;
    String [] storeId,storeName,category,storeAddress,storeSeat_count,vacancy,store_info,view_count;
    SharedPreferences preferences;
    public void sendRequest() {
        url="http://122.34.93.216:8080/capstone/storeInformation_json.jsp";
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("HF_resultValue",response);

                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("storeInformation");
                            storeId = new String[jsonArray.length()];
                            storeName = new String[jsonArray.length()];
                            category = new String[jsonArray.length()];
                            storeAddress = new String[jsonArray.length()];
                            storeSeat_count = new String[jsonArray.length()];
                            vacancy = new String[jsonArray.length()];
                            store_info = new String[jsonArray.length()];
                            view_count = new String[jsonArray.length()];
                            for(int i=0;i<jsonArray.length();i++) {
                                JSONObject jsonObj = jsonArray.getJSONObject(i);
                                storeId[i] = jsonObj.getString("store_id");
                                storeName[i] = jsonObj.getString("store_name");
                                category[i] = jsonObj.getString("category");
                                storeAddress[i] = jsonObj.getString("address");
                                storeSeat_count[i] = jsonObj.getString("seat_count");
                                vacancy[i] = jsonObj.getString("vacancy");
                                store_info[i] = jsonObj.getString("store_info");
                                view_count[i] = jsonObj.getString("view_count");
                            }
                            preferences = getContext().getSharedPreferences("StoreInfo",MODE_PRIVATE);
                            String categoryInfo = preferences.getString("category","");
                            String categoryId[] = new String[MainFragment.storeAddress.length];
                            for(int i=0;i<MainFragment.storeAddress.length;i++){
                                if(MainFragment.category[i].equals(categoryInfo)){
                                    String category="";
                                    switch (categoryInfo){
                                        case "Korean Food":
                                            category = "kor";
                                            break;
                                        case "Chinese Food":
                                            category = "chi";
                                            break;
                                        case "Western Food":
                                            category = "west";
                                            break;
                                        case "Japan Food":
                                            category = "jap";
                                            break;
                                        case "Fast Food":
                                            category = "fast";
                                            break;
                                    }
                                    imageUrl = "http://122.34.93.216:8080/capstone/drawable/"+category+MainFragment.storeId[i]+".png";
                                    Log.v("urlLog",imageUrl);
                                    dataList.add(new ItemData(MainFragment.storeName[i],MainFragment.store_info[i],imageUrl));//가게이름, 가게소개, 이미지url
                                    Log.v("dataList", String.valueOf(dataList));
                                    recyclerAttach();
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
    public void recyclerAttach(){
        recyclerView.setLayoutManager(linearLayoutManager);

        mainActivity = (MainActivity)getActivity();

        customAdapter = new CustomAdapter(dataList);

        customAdapter.setOnItemClickListener(new CustomAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int position, String title) {
                preferences = getContext().getSharedPreferences("StoreInfo",MODE_PRIVATE);
                SharedPreferences.Editor editor =preferences.edit();
                storeClear();
                editor.putString("storeName",title);
                editor.commit();
                StoreFragment storeFragment = new StoreFragment();
                mainActivity.replaceFragment(storeFragment);
            }
        });
        recyclerView.setAdapter(customAdapter);
    }
    public void storeClear(){
        preferences = getContext().getSharedPreferences("StoreInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hansik, container, false);
        recyclerView = view.findViewById(R.id.recycler);
        linearLayoutManager = new LinearLayoutManager(view.getContext());
        sendRequest();
        Log.v("msg","SendRequest");









        return view;
    }
}