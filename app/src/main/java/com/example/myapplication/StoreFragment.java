package com.example.myapplication;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;


public class StoreFragment extends Fragment {
    ArrayList<StoreData> dataList = new ArrayList<>();
    RecyclerView recyclerView;
    public StoreAdapter storeAdapter;
    LinearLayoutManager linearLayoutManager;
    MainActivity mainActivity;

    SharedPreferences preferences;
    String getStoreName,url,userurl,imageUrl,categoryInfo;
    String favoriteUrl,store_Id,userSequence,menuUrl;
    ImageView storeImage;
    CheckBox checkBox;
    TextView store_Name,storeInfo;
    String [] store_id_draw,storeId,storeName,category,storeAddress,storeSeat_count,vacancy,store_info,view_count,t0, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22, t23, t24;;//store 정보
    String[] sequence,userId;//user페이지 정보
    String[] menuName,menuPrice,menuId;
    private ImageView T [] = new ImageView[25];
    private Handler handler = null;
    private boolean defaultValue = false;
    private int seatCount[] = new int[25];
    private int defaultNum = 0;
    private boolean condition = true;
    public void recyclerAttach(){
        Log.v("recyclerAttack5","recyclerAttack");

        recyclerView.setLayoutManager(linearLayoutManager);

        mainActivity = (MainActivity)getActivity();

        storeAdapter = new StoreAdapter(dataList);


        recyclerView.setAdapter(storeAdapter);
    }
    public void sendMenuRequest(){
        menuUrl="http://122.34.93.216:8080/capstone/menus_json.jsp";
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, menuUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("Menu_resultValue",response);

                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("menus");
                            menuName = new String[jsonArray.length()];
                            menuPrice = new String[jsonArray.length()];
                            menuId = new String[jsonArray.length()];
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObj = jsonArray.getJSONObject(i);
                                menuName[i] = jsonObj.getString("menu");
                                menuPrice[i] = jsonObj.getString("price");
                                menuId[i] = jsonObj.getString("store_id");
                                if(menuId[i].equals(store_Id)){
                                    dataList.add(new StoreData(menuName[i],menuPrice[i]));//가게이름, 이미지url
                                }
                            }
                            recyclerAttach();



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
    public void onDestroy( ) {
        super.onDestroy();
        condition = false;
    }
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
                            category = new String[jsonArray.length()];
                            storeAddress = new String[jsonArray.length()];
                            storeSeat_count = new String[jsonArray.length()];
                            vacancy = new String[jsonArray.length()];
                            store_info = new String[jsonArray.length()];
                            view_count = new String[jsonArray.length()];
                            for(int i=0;i<jsonArray.length();i++){
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
                            //store 값 최신화 시키는 코드
                            preferences = getContext().getSharedPreferences("StoreInfo",MODE_PRIVATE);
                            getStoreName = preferences.getString("storeName","");
                            categoryInfo = preferences.getString("category","");

                            for(int i=0;i<jsonArray.length();i++){

                                if(getStoreName.equals(storeName[i])){
                                    store_Id = storeId[i];
                                    store_Name.setText(storeName[i]);//가게 이름 바꾸기
                                    storeInfo.setText(store_info[i]);
                                    //Log.v("preference에서 불러온 카테고리",categoryInfo);
                                    Log.v("가게이름", storeName[i]);
                                    Log.v("가게설명", store_info[i]);
                                    String cat="";
                                    switch (category[i]){
                                        case "Korean Food":
                                            cat = "kor";
                                            break;
                                        case "Chinese Food":
                                            cat = "chi";
                                            break;
                                        case "Western Food":
                                            cat = "west";
                                            break;
                                        case "Japan Food":
                                            cat = "jap";
                                            break;
                                        case "Fast Food":
                                            cat = "fast";
                                            break;
                                    }
                                    imageUrl = "http://122.34.93.216:8080/capstone/drawable/"+cat+storeId[i]+".png";
                                    Log.v("이미지 url",imageUrl);


                                    Glide.with(getActivity()).load(imageUrl).into(storeImage);
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
    public void sendUserRequest(){
        userurl="http://122.34.93.216:8080/capstone/users_json.jsp";
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, userurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("LG_resultValue",response);

                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("users");

                            sequence = new String[jsonArray.length()];
                            userId = new String[jsonArray.length()];
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObj = jsonArray.getJSONObject(i);
                                sequence[i] = jsonObj.getString("sequence");
                                userId[i] = jsonObj.getString("id");
                                String uName = MainActivity.uName;
                                if(uName.equals(userId[i])){
                                    userSequence = sequence[i];
                                }
                            }
                            //preferences2 = getContext().getSharedPreferences("userInfo",MODE_PRIVATE);
                            //String uName = preferences.getString("Id","");

                        } catch (JSONException e) {}

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }
    private class drawThread implements Runnable{
        @Override
        public void run() {
            while (condition){
                try {
                    String draw_json_url="http://122.34.93.216:8080/capstone/table_draw_json.jsp";
                    RequestQueue requestQueueDraw = Volley.newRequestQueue(getActivity());
                    StringRequest stringRequestDraw = new StringRequest(Request.Method.GET, draw_json_url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try{
                                        JSONObject jsonObject = new JSONObject(response);
                                        JSONArray jsonArray = jsonObject.getJSONArray("table_draw");
                                        store_id_draw = new String[jsonArray.length()];
                                        t0 = new String[jsonArray.length()];
                                        t1 = new String[jsonArray.length()];
                                        t2 = new String[jsonArray.length()];
                                        t3 = new String[jsonArray.length()];
                                        t4 = new String[jsonArray.length()];
                                        t5 = new String[jsonArray.length()];
                                        t6 = new String[jsonArray.length()];
                                        t7 = new String[jsonArray.length()];
                                        t8 = new String[jsonArray.length()];
                                        t9 = new String[jsonArray.length()];
                                        t10 = new String[jsonArray.length()];
                                        t11 = new String[jsonArray.length()];
                                        t12 = new String[jsonArray.length()];
                                        t13 = new String[jsonArray.length()];
                                        t14 = new String[jsonArray.length()];
                                        t15 = new String[jsonArray.length()];
                                        t16 = new String[jsonArray.length()];
                                        t17 = new String[jsonArray.length()];
                                        t18 = new String[jsonArray.length()];
                                        t19 = new String[jsonArray.length()];
                                        t20 = new String[jsonArray.length()];
                                        t21 = new String[jsonArray.length()];
                                        t22 = new String[jsonArray.length()];
                                        t23 = new String[jsonArray.length()];
                                        t24 = new String[jsonArray.length()];

                                        for(int i=0;i<jsonArray.length();i++){
                                            JSONObject jsonObj = jsonArray.getJSONObject(i);
                                            store_id_draw[i] = jsonObj.getString("store_id");
                                            t0[i] = jsonObj.getString("T0");
                                            t1[i] = jsonObj.getString("T1");
                                            t2[i] = jsonObj.getString("T2");
                                            t3[i] = jsonObj.getString("T3");
                                            t4[i] = jsonObj.getString("T4");
                                            t5[i] = jsonObj.getString("T5");
                                            t6[i] = jsonObj.getString("T6");
                                            t7[i]= jsonObj.getString("T7");
                                            t8[i] = jsonObj.getString("T8");
                                            t9[i] = jsonObj.getString("T9");
                                            t10[i] = jsonObj.getString("T10");
                                            t11[i] = jsonObj.getString("T11");
                                            t12[i] = jsonObj.getString("T12");
                                            t13[i] = jsonObj.getString("T13");
                                            t14[i] = jsonObj.getString("T14");
                                            t15[i] = jsonObj.getString("T15");
                                            t16[i] = jsonObj.getString("T16");
                                            t17[i] = jsonObj.getString("T17");
                                            t18[i] = jsonObj.getString("T18");
                                            t19[i] = jsonObj.getString("T19");
                                            t20[i] = jsonObj.getString("T20");
                                            t21[i] = jsonObj.getString("T21");
                                            t22[i] = jsonObj.getString("T22");
                                            t23[i] = jsonObj.getString("T23");
                                            t24[i] = jsonObj.getString("T24");
                                        }
                                        for(int i = 0; i < store_id_draw.length; i++){
                                            if(store_id_draw[i].equals(store_Id)){
                                                defaultValue = true;
                                                defaultNum = i;
                                            }

                                        }
                                        if(defaultValue==true){
                                            seatCount[0] = Integer.parseInt(t0[defaultNum]);
                                            seatCount[1] = Integer.parseInt(t1[defaultNum]);
                                            seatCount[2] = Integer.parseInt(t2[defaultNum]);
                                            seatCount[3] = Integer.parseInt(t3[defaultNum]);
                                            seatCount[4] = Integer.parseInt(t4[defaultNum]);
                                            seatCount[5] = Integer.parseInt(t5[defaultNum]);
                                            seatCount[6] = Integer.parseInt(t6[defaultNum]);
                                            seatCount[7] = Integer.parseInt(t7[defaultNum]);
                                            seatCount[8] = Integer.parseInt(t8[defaultNum]);
                                            seatCount[9] = Integer.parseInt(t9[defaultNum]);
                                            seatCount[10] = Integer.parseInt(t10[defaultNum]);
                                            seatCount[11] = Integer.parseInt(t11[defaultNum]);
                                            seatCount[12] = Integer.parseInt(t12[defaultNum]);
                                            seatCount[13] = Integer.parseInt(t13[defaultNum]);
                                            seatCount[14] = Integer.parseInt(t14[defaultNum]);
                                            seatCount[15] = Integer.parseInt(t15[defaultNum]);
                                            seatCount[16] = Integer.parseInt(t16[defaultNum]);
                                            seatCount[17] = Integer.parseInt(t17[defaultNum]);
                                            seatCount[18] = Integer.parseInt(t18[defaultNum]);
                                            seatCount[19] = Integer.parseInt(t19[defaultNum]);
                                            seatCount[20] = Integer.parseInt(t20[defaultNum]);
                                            seatCount[21] = Integer.parseInt(t21[defaultNum]);
                                            seatCount[22] = Integer.parseInt(t22[defaultNum]);
                                            seatCount[23] = Integer.parseInt(t23[defaultNum]);
                                            seatCount[24] = Integer.parseInt(t24[defaultNum]);

                                            for(int i = 0; i < T.length; i++){
                                                final int finall = i;
                                                if(seatCount[i]==2){
                                                    T[finall].setImageResource(R.drawable.seat2green);
                                                } else if(seatCount[i]==1){
                                                    T[finall].setImageResource(R.drawable.seat2red);
                                                } else if(seatCount[i]==4){
                                                    T[finall].setImageResource(R.drawable.seat4green);
                                                }else if(seatCount[i]==3){
                                                    T[finall].setImageResource(R.drawable.seat4red);
                                                } else if(seatCount[i]==5) {
                                                    T[finall].setImageResource(R.drawable.seat5green);
                                                }else if(seatCount[i]==6){
                                                    T[finall].setImageResource(R.drawable.seat5red);
                                                }
                                            }
                                        }
                                    } catch (JSONException e) {}

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    });
                    requestQueueDraw.add(stringRequestDraw);
                    try {
                        Thread.sleep(500);
                    } catch(InterruptedException e) {}
                }catch (Exception e){
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_store, container, false);
        storeImage = (ImageView)view.findViewById(R.id.storeImage);
        store_Name = (TextView)view.findViewById(R.id.storejang);
        storeInfo = (TextView)view.findViewById(R.id.storeExplain);
        checkBox = (CheckBox) view.findViewById(R.id.checkBox);

        recyclerView = view.findViewById(R.id.store_recycler);
        linearLayoutManager = new LinearLayoutManager(view.getContext());


        for(int i = 0; i < 25; i ++){
            T[i] = view.findViewById(getResources().getIdentifier("T" + i,"id","com.example.myapplication"));
            final int finall = i;
            T[i].setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    T[finall].setBackgroundColor(Color.WHITE);
                    T[finall].setImageDrawable(null);
                    return true;
                }
            });
        }

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        sendStoreRequest();
        sendUserRequest();
        sendMenuRequest();
        favoriteUrl = "http://122.34.93.216:8080/capstone/favorites_Insert.jsp";
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox.isChecked()){
                    String url2 = favoriteUrl + "?user_id="+userSequence+"&store_id="+store_Id;
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url2,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Toast.makeText(getActivity(), "값이 저장되었습니다."+userSequence+": store" +store_Id , Toast.LENGTH_SHORT).show();
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                    requestQueue.add(stringRequest);

                }
                else {
                    //즐겨찾기에서 삭제하는 메소드 jsp 페이지가 필요!
                }

            }
        });
        Thread drawThread = new Thread(new drawThread());
        drawThread.start();

        return view;
    }
}