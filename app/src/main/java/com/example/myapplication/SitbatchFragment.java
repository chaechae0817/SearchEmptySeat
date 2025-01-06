package com.example.myapplication;

import static android.content.Context.MODE_PRIVATE;
import static androidx.core.content.ContextCompat.getDrawable;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class SitbatchFragment extends Fragment {
    private ImageView draggableImage, draggableImage1, draggableImage2;
    private ImageView T [] = new ImageView[25];
    private int seatCount[] = new int[25];
    private Button btn, btn2;
    private String url;
    private TableLayout tableLayout;
    private View.DragShadowBuilder dragShadowBuilder;
    private LinearLayout imageContainer;
    private Button addImageButton;
    private SharedPreferences sharedPreferences;
    private int imageCounter;
    private final String IMAGE_COUNTER_KEY = "image_counter_key";
    private String [] store_id_list,storeName,storeAddress,category,storeSeat_count, vacancy,store_info,view_count, store_id_draw, t0, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22, t23, t24;
    private int ManagementMod = 0;
    private boolean drop = false;
    private boolean defaultValue = false;
    private int total = 0;
    private int defaultNum = 0;
    private int nowVacancy = 0;
    private int storeSeat = 0;
    private String store_id;
    private String reseturl = "http://122.34.93.216:8080/capstone/seatCountInsert.jsp?store_id=";
    private String vacancy_url;
    private class resetThread implements Runnable{
        @Override
        public void run() {
            System.out.println("총좌석 조회 시작");
            Thread sThread = new Thread(new vacancyThread());
            sThread.start();
            RequestQueue requestQueueReset = Volley.newRequestQueue(getActivity());
            StringRequest stringRequestReset = new StringRequest(Request.Method.GET,
                    reseturl,
                    new Response.Listener<String>() {
                        public void onResponse(String response) {
                            System.out.println("총좌석 리셋"+storeSeat);
                            System.out.println(reseturl);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    }
            );
            requestQueueReset.add(stringRequestReset);
        }
    }
    private class drawThread implements Runnable{
        @Override
        public void run() {
            String draw_json_url="http://122.34.93.216:8080/capstone/table_draw_json.jsp";
            RequestQueue requestQueue5 = Volley.newRequestQueue(getContext());
            StringRequest stringRequest5 = new StringRequest(Request.Method.GET, draw_json_url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.v("resultValue",response);
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
                                    if(store_id_draw[i].equals(store_id)){
                                        defaultValue = true;
                                        defaultNum = i;
                                    }
                                    System.out.println(defaultValue);
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
                                            T[finall].setImageResource(R.drawable.seat2);
                                        } else if(seatCount[i]==4){
                                            T[finall].setImageResource(R.drawable.seat4);
                                        } else if(seatCount[i]==5) {
                                            T[finall].setImageResource(R.drawable.seat5);
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
            requestQueue5.add(stringRequest5);
        }
    }
    private class thread implements Runnable{
        @Override
        public void run() {
            url = "http://122.34.93.216:8080/capstone/";
            for(int i = 0; i < 25; i ++) {
                final int finall = i;
                Drawable seat2 = getDrawable(getContext(), R.drawable.seat2);
                Drawable seat4 = getDrawable(getContext(), R.drawable.seat4);
                Drawable seat5 = getDrawable(getContext(), R.drawable.seat5);
                Bitmap seat2Bitmap = ((BitmapDrawable) seat2).getBitmap();
                Bitmap seat4Bitmap = ((BitmapDrawable) seat4).getBitmap();
                Bitmap seat5Bitmap = ((BitmapDrawable) seat5).getBitmap();

                if (T[finall].getDrawable() != null) {
                    Drawable tImage = T[finall].getDrawable();
                    Bitmap tImageBitmap = ((BitmapDrawable) tImage).getBitmap();

                    if (sameAs(tImageBitmap, seat2Bitmap)) {
                        seatCount[finall] = 2;
                        total += 2;
                    }
                    else if (sameAs(tImageBitmap, seat4Bitmap)) {
                        seatCount[finall] = 4;
                        total += 4;
                    }
                    else if (sameAs(tImageBitmap, seat5Bitmap)) {
                        seatCount[finall] = 5;
                        total += 5;
                    }
                    else {
                        seatCount[finall] = 0;
                    }
                }
            }
            String json_url="http://122.34.93.216:8080/capstone/storeInformation_json.jsp";
            RequestQueue requestQueue2 = Volley.newRequestQueue(getContext());

            StringRequest stringRequest2 = new StringRequest(Request.Method.GET, json_url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.v("resultValue",response);

                            try{
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray jsonArray = jsonObject.getJSONArray("storeInformation");
                                store_id_list = new String[jsonArray.length()];
                                storeName = new String[jsonArray.length()];
                                category = new String[jsonArray.length()];
                                storeAddress = new String[jsonArray.length()];
                                storeSeat_count = new String[jsonArray.length()];
                                vacancy = new String[jsonArray.length()];
                                store_info = new String[jsonArray.length()];
                                view_count = new String[jsonArray.length()];
                                for(int i=0;i<jsonArray.length();i++){
                                    JSONObject jsonObj = jsonArray.getJSONObject(i);
                                    store_id_list[i] = jsonObj.getString("store_id");
                                    storeName[i] = jsonObj.getString("store_name");
                                    category[i] = jsonObj.getString("category");
                                    storeAddress[i] = jsonObj.getString("address");
                                    storeSeat_count[i] = jsonObj.getString("seat_count");
                                    vacancy[i] = jsonObj.getString("vacancy");
                                    store_info[i] = jsonObj.getString("store_info");
                                    view_count[i] = jsonObj.getString("view_count");
                                }
                                for(int i = 0; i < store_id_list.length; i++){
                                    if(store_id.equals(store_id_list[i])){
                                        drop=true;
                                    }
                                }
                                if(drop==true){
                                    String drop_url="http://122.34.93.216:8080/capstone/draw_drop.jsp?store_id=" + store_id;
                                    RequestQueue requestQueue3 = Volley.newRequestQueue(getContext());
                                    StringRequest stringRequest3 = new StringRequest(Request.Method.GET,
                                            drop_url,
                                            new Response.Listener<String>() {
                                                public void onResponse(String response) {
                                                    System.out.println("드롭"+total);

                                                }
                                            },
                                            new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                }
                                            }
                                    );
                                    requestQueue3.add(stringRequest3);
                                }
                                //?store_id=&seat_count=
                                String url2 = url + "seatCountInsert.jsp" + "?store_id=" + store_id + "&seat_count=" + Integer.toString(total);
                                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                                StringRequest stringRequest = new StringRequest(Request.Method.GET,
                                        url2,
                                        new Response.Listener<String>() {
                                            public void onResponse(String response) {
                                                System.out.println("총좌석 인설트"+total);
                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                            }
                                        }
                                );
                                requestQueue.add(stringRequest);
                                String url3 = url + "draw_Insert.jsp" + "?store_id=" + store_id;
                                for(int i = 0; i < seatCount.length; i++){
                                    url3 = url3 + "&T" + Integer.toString(i) + "=" + seatCount[i];
                                }
                                RequestQueue requestQueue4 = Volley.newRequestQueue(getContext());
                                StringRequest stringRequest4 = new StringRequest(Request.Method.GET,
                                        url3,
                                        new Response.Listener<String>() {
                                            public void onResponse(String response) {
                                                System.out.println("그림 인설트");
                                                total=0;
                                                System.out.println("마지막"+total);
                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                /**/
                                            }
                                        }
                                );
                                requestQueue4.add(stringRequest4);
                            } catch (JSONException e) {}

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });
            requestQueue2.add(stringRequest2);
        }
    }
    private class vacancyThread implements Runnable{
        @Override
        public void run() {
            System.out.println("현재 좌석 조회 시작");
            String json_url="http://122.34.93.216:8080/capstone/storeInformation_json.jsp";
            RequestQueue requestQueueStoreInfo = Volley.newRequestQueue(getContext());
            StringRequest stringRequestStoreInfo = new StringRequest(Request.Method.GET, json_url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.v("resultValue",response);
                            try{
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray jsonArray = jsonObject.getJSONArray("storeInformation");
                                store_id_list = new String[jsonArray.length()];
                                storeName = new String[jsonArray.length()];
                                category = new String[jsonArray.length()];
                                storeAddress = new String[jsonArray.length()];
                                storeSeat_count = new String[jsonArray.length()];
                                vacancy = new String[jsonArray.length()];
                                store_info = new String[jsonArray.length()];
                                view_count = new String[jsonArray.length()];
                                for(int i=0;i<jsonArray.length();i++){
                                    JSONObject jsonObj = jsonArray.getJSONObject(i);
                                    store_id_list[i] = jsonObj.getString("store_id");
                                    storeName[i] = jsonObj.getString("store_name");
                                    category[i] = jsonObj.getString("category");
                                    storeAddress[i] = jsonObj.getString("address");
                                    storeSeat_count[i] = jsonObj.getString("seat_count");
                                    vacancy[i] = jsonObj.getString("vacancy");
                                    store_info[i] = jsonObj.getString("store_info");
                                    view_count[i] = jsonObj.getString("view_count");
                                }
                                for(int i = 0; i < store_id_list.length; i++){
                                    if(store_id.equals(store_id_list[i])){
                                        nowVacancy=Integer.parseInt(vacancy[i]);
                                        System.out.println("현재좌석" + nowVacancy);
                                        storeSeat = Integer.parseInt(storeSeat_count[i]);
                                        System.out.println("총 좌석 " + storeSeat);
                                        reseturl = "http://122.34.93.216:8080/capstone/seatCountInsert.jsp?store_id=" + store_id + "&seat_count=" + Integer.toString(storeSeat);

                                    }
                                }
                            } catch (JSONException e) {}

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });
            requestQueueStoreInfo.add(stringRequestStoreInfo);
        }
    }
    private void tableUpdate(String store_id,String T, int num){
        String url = "http://122.34.93.216:8080/capstone/tableUpdate.jsp?store_id="+store_id+"&T="+T+"&num="+num;
        RequestQueue requestQueueReset = Volley.newRequestQueue(getActivity());
        StringRequest stringRequestReset = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    public void onResponse(String response) {
                        System.out.println("table 업데이트 "+url);
                        System.out.println(url);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        );
        requestQueueReset.add(stringRequestReset);

    }
    private boolean sameAs(Bitmap bitmap1, Bitmap bitmap2) {

        ByteBuffer buffer1 = ByteBuffer.allocate(bitmap1.getHeight() * bitmap1.getRowBytes());

        bitmap1.copyPixelsToBuffer(buffer1);



        ByteBuffer buffer2 = ByteBuffer.allocate(bitmap2.getHeight() * bitmap2.getRowBytes());

        bitmap2.copyPixelsToBuffer(buffer2);

        return Arrays.equals(buffer1.array(), buffer2.array());

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sitbatch, container, false);
        btn = view.findViewById(R.id.btn);
        btn2 = view.findViewById(R.id.btn2);


        draggableImage = view.findViewById(R.id.seat2);
        draggableImage1 = view.findViewById(R.id.seat4);
        draggableImage2 = view.findViewById(R.id.seat5);

        SharedPreferences pref = getContext().getSharedPreferences("userInfo", MODE_PRIVATE);
        store_id = pref.getString("my_store_id","");

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
        Thread drawThread = new Thread(new drawThread());
        drawThread.start();

        tableLayout = view.findViewById(R.id.tableLayout);

        sharedPreferences = getContext().getSharedPreferences("MyPreferences", MODE_PRIVATE);
        imageCounter = sharedPreferences.getInt(IMAGE_COUNTER_KEY, 0);



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(new thread());

                thread.start();

                Toast.makeText(getContext(), "좌석을 등록하였습니다.", Toast.LENGTH_SHORT).show();

            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ManagementMod == 0){
                    ManagementMod = 1;
                    Toast.makeText(getContext(), "좌석 관리 모드가 켜졌습니다.", Toast.LENGTH_SHORT).show();
                    for(int i = 0; i < 25; i ++){
                        final int finall = i;
                        Drawable seat2 = getDrawable(getContext(), R.drawable.seat2);
                        Drawable seat4 = getDrawable(getContext(), R.drawable.seat4);
                        Drawable seat5 = getDrawable(getContext(), R.drawable.seat5);
                        Bitmap seat2Bitmap = ((BitmapDrawable)seat2).getBitmap();
                        Bitmap seat4Bitmap = ((BitmapDrawable)seat4).getBitmap();
                        Bitmap seat5Bitmap = ((BitmapDrawable)seat5).getBitmap();

                        if(T[finall].getDrawable() != null){
                            Drawable tImage = T[finall].getDrawable();
                            Bitmap tImageBitmap = ((BitmapDrawable)tImage).getBitmap();

                            if(sameAs(tImageBitmap, seat2Bitmap)){
                                T[finall].setImageResource(R.drawable.seat2green);
                            }
                            if(sameAs(tImageBitmap, seat4Bitmap)){
                                T[finall].setImageResource(R.drawable.seat4green);
                            }
                            if(sameAs(tImageBitmap, seat5Bitmap)){
                                T[finall].setImageResource(R.drawable.seat5green);
                            }
                        }

                        T[i].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Drawable tImage = T[finall].getDrawable();
                                Bitmap tImageBitmap = ((BitmapDrawable)tImage).getBitmap();

                                Drawable seat2green = getDrawable(getContext(), R.drawable.seat2green);
                                Drawable seat4green = getDrawable(getContext(), R.drawable.seat4green);
                                Drawable seat5green = getDrawable(getContext(), R.drawable.seat5green);
                                Drawable seat2red = getDrawable(getContext(), R.drawable.seat2red);
                                Drawable seat4red = getDrawable(getContext(), R.drawable.seat4red);
                                Drawable seat5red = getDrawable(getContext(), R.drawable.seat5red);
                                Bitmap seat2BitmapGreen = ((BitmapDrawable)seat2green).getBitmap();
                                Bitmap seat4BitmapGreen = ((BitmapDrawable)seat4green).getBitmap();
                                Bitmap seat5BitmapGreen = ((BitmapDrawable)seat5green).getBitmap();
                                Bitmap seat2BitmapRed = ((BitmapDrawable)seat2red).getBitmap();
                                Bitmap seat4BitmapRed = ((BitmapDrawable)seat4red).getBitmap();
                                Bitmap seat5BitmapRed = ((BitmapDrawable)seat5red).getBitmap();

                                if(sameAs(tImageBitmap, seat2BitmapGreen)){
                                    T[finall].setImageResource(R.drawable.seat2red);
                                    Thread resultThread = new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            System.out.println("현재 좌석 조회 시작");
                                            String json_url="http://122.34.93.216:8080/capstone/storeInformation_json.jsp";
                                            RequestQueue requestQueueStoreInfo = Volley.newRequestQueue(getContext());
                                            StringRequest stringRequestStoreInfo = new StringRequest(Request.Method.GET, json_url,
                                                    new Response.Listener<String>() {
                                                        @Override
                                                        public void onResponse(String response) {
                                                            Log.v("resultValue",response);
                                                            try{
                                                                JSONObject jsonObject = new JSONObject(response);
                                                                JSONArray jsonArray = jsonObject.getJSONArray("storeInformation");
                                                                store_id_list = new String[jsonArray.length()];
                                                                storeName = new String[jsonArray.length()];
                                                                category = new String[jsonArray.length()];
                                                                storeAddress = new String[jsonArray.length()];
                                                                storeSeat_count = new String[jsonArray.length()];
                                                                vacancy = new String[jsonArray.length()];
                                                                store_info = new String[jsonArray.length()];
                                                                view_count = new String[jsonArray.length()];
                                                                for(int i=0;i<jsonArray.length();i++){
                                                                    JSONObject jsonObj = jsonArray.getJSONObject(i);
                                                                    store_id_list[i] = jsonObj.getString("store_id");
                                                                    storeName[i] = jsonObj.getString("store_name");
                                                                    category[i] = jsonObj.getString("category");
                                                                    storeAddress[i] = jsonObj.getString("address");
                                                                    storeSeat_count[i] = jsonObj.getString("seat_count");
                                                                    vacancy[i] = jsonObj.getString("vacancy");
                                                                    store_info[i] = jsonObj.getString("store_info");
                                                                    view_count[i] = jsonObj.getString("view_count");
                                                                }
                                                                for(int i = 0; i < store_id_list.length; i++){
                                                                    if(store_id.equals(store_id_list[i])){
                                                                        nowVacancy=Integer.parseInt(vacancy[i]);
                                                                        storeSeat = Integer.parseInt(storeSeat_count[i]);
                                                                        vacancy_url = "http://122.34.93.216:8080/capstone/vacancy_update.jsp?store_id="+store_id;
                                                                        nowVacancy -= 2;
                                                                        System.out.println("현재좌석 마이너스 : "+nowVacancy);
                                                                        String vacancy_url2 = vacancy_url + "&vacancy=" + (nowVacancy);
                                                                        System.out.println("현재좌석 url : "+vacancy_url2);
                                                                        RequestQueue requestQueueVacancy = Volley.newRequestQueue(getContext());
                                                                        StringRequest stringRequestVacancy = new StringRequest(Request.Method.GET,
                                                                                vacancy_url2,
                                                                                new Response.Listener<String>() {
                                                                                    public void onResponse(String response) {
                                                                                        System.out.println("vacancy_update"+vacancy_url2);
                                                                                    }
                                                                                },
                                                                                new Response.ErrorListener() {
                                                                                    @Override
                                                                                    public void onErrorResponse(VolleyError error) {
                                                                                    }
                                                                                }
                                                                        );
                                                                        requestQueueVacancy.add(stringRequestVacancy);
                                                                        String s = "T"+String.valueOf(finall);
                                                                        tableUpdate(store_id, s, 1);

                                                                    }
                                                                }
                                                            } catch (JSONException e) {}

                                                        }
                                                    }, new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                }
                                            });
                                            requestQueueStoreInfo.add(stringRequestStoreInfo);
                                        }
                                    });
                                    resultThread.start();
                                } else if(sameAs(tImageBitmap, seat2BitmapRed)){
                                    T[finall].setImageResource(R.drawable.seat2green);
                                    Thread resultThread = new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            System.out.println("현재 좌석 조회 시작");
                                            String json_url="http://122.34.93.216:8080/capstone/storeInformation_json.jsp";
                                            RequestQueue requestQueueStoreInfo = Volley.newRequestQueue(getContext());
                                            StringRequest stringRequestStoreInfo = new StringRequest(Request.Method.GET, json_url,
                                                    new Response.Listener<String>() {
                                                        @Override
                                                        public void onResponse(String response) {
                                                            Log.v("resultValue",response);
                                                            try{
                                                                JSONObject jsonObject = new JSONObject(response);
                                                                JSONArray jsonArray = jsonObject.getJSONArray("storeInformation");
                                                                store_id_list = new String[jsonArray.length()];
                                                                storeName = new String[jsonArray.length()];
                                                                category = new String[jsonArray.length()];
                                                                storeAddress = new String[jsonArray.length()];
                                                                storeSeat_count = new String[jsonArray.length()];
                                                                vacancy = new String[jsonArray.length()];
                                                                store_info = new String[jsonArray.length()];
                                                                view_count = new String[jsonArray.length()];
                                                                for(int i=0;i<jsonArray.length();i++){
                                                                    JSONObject jsonObj = jsonArray.getJSONObject(i);
                                                                    store_id_list[i] = jsonObj.getString("store_id");
                                                                    storeName[i] = jsonObj.getString("store_name");
                                                                    category[i] = jsonObj.getString("category");
                                                                    storeAddress[i] = jsonObj.getString("address");
                                                                    storeSeat_count[i] = jsonObj.getString("seat_count");
                                                                    vacancy[i] = jsonObj.getString("vacancy");
                                                                    store_info[i] = jsonObj.getString("store_info");
                                                                    view_count[i] = jsonObj.getString("view_count");
                                                                }
                                                                for(int i = 0; i < store_id_list.length; i++){
                                                                    if(store_id.equals(store_id_list[i])){
                                                                        nowVacancy=Integer.parseInt(vacancy[i]);
                                                                        storeSeat = Integer.parseInt(storeSeat_count[i]);
                                                                        vacancy_url = "http://122.34.93.216:8080/capstone/vacancy_update.jsp?store_id="+store_id;
                                                                        nowVacancy += 2;;
                                                                        String vacancy_url2 = vacancy_url + "&vacancy=" + (nowVacancy);
                                                                        RequestQueue requestQueueVacancy = Volley.newRequestQueue(getContext());
                                                                        StringRequest stringRequestVacancy = new StringRequest(Request.Method.GET,
                                                                                vacancy_url2,
                                                                                new Response.Listener<String>() {
                                                                                    public void onResponse(String response) {
                                                                                        System.out.println("vacancy_update"+vacancy_url2);
                                                                                    }
                                                                                },
                                                                                new Response.ErrorListener() {
                                                                                    @Override
                                                                                    public void onErrorResponse(VolleyError error) {
                                                                                    }
                                                                                }
                                                                        );
                                                                        requestQueueVacancy.add(stringRequestVacancy);
                                                                        requestQueueVacancy.add(stringRequestVacancy);
                                                                        String s = "T"+String.valueOf(finall);
                                                                        tableUpdate(store_id, s, 2);

                                                                    }
                                                                }
                                                            } catch (JSONException e) {}

                                                        }
                                                    }, new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                }
                                            });
                                            requestQueueStoreInfo.add(stringRequestStoreInfo);
                                        }
                                    });
                                    resultThread.start();
                                } else if(sameAs(tImageBitmap, seat4BitmapGreen)){
                                    T[finall].setImageResource(R.drawable.seat4red);
                                    Thread resultThread = new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            System.out.println("현재 좌석 조회 시작");
                                            String json_url="http://122.34.93.216:8080/capstone/storeInformation_json.jsp";
                                            RequestQueue requestQueueStoreInfo = Volley.newRequestQueue(getContext());
                                            StringRequest stringRequestStoreInfo = new StringRequest(Request.Method.GET, json_url,
                                                    new Response.Listener<String>() {
                                                        @Override
                                                        public void onResponse(String response) {
                                                            Log.v("resultValue",response);
                                                            try{
                                                                JSONObject jsonObject = new JSONObject(response);
                                                                JSONArray jsonArray = jsonObject.getJSONArray("storeInformation");
                                                                store_id_list = new String[jsonArray.length()];
                                                                storeName = new String[jsonArray.length()];
                                                                category = new String[jsonArray.length()];
                                                                storeAddress = new String[jsonArray.length()];
                                                                storeSeat_count = new String[jsonArray.length()];
                                                                vacancy = new String[jsonArray.length()];
                                                                store_info = new String[jsonArray.length()];
                                                                view_count = new String[jsonArray.length()];
                                                                for(int i=0;i<jsonArray.length();i++){
                                                                    JSONObject jsonObj = jsonArray.getJSONObject(i);
                                                                    store_id_list[i] = jsonObj.getString("store_id");
                                                                    storeName[i] = jsonObj.getString("store_name");
                                                                    category[i] = jsonObj.getString("category");
                                                                    storeAddress[i] = jsonObj.getString("address");
                                                                    storeSeat_count[i] = jsonObj.getString("seat_count");
                                                                    vacancy[i] = jsonObj.getString("vacancy");
                                                                    store_info[i] = jsonObj.getString("store_info");
                                                                    view_count[i] = jsonObj.getString("view_count");
                                                                }
                                                                for(int i = 0; i < store_id_list.length; i++){
                                                                    if(store_id.equals(store_id_list[i])){
                                                                        nowVacancy=Integer.parseInt(vacancy[i]);
                                                                        System.out.println("현재좌석" + nowVacancy);
                                                                        storeSeat = Integer.parseInt(storeSeat_count[i]);
                                                                        System.out.println("총 좌석 " + storeSeat);
                                                                        vacancy_url = "http://122.34.93.216:8080/capstone/vacancy_update.jsp?store_id="+store_id;
                                                                        nowVacancy -= 4;
                                                                        System.out.println("현재좌석 마이너스 : "+nowVacancy);
                                                                        String vacancy_url2 = vacancy_url + "&vacancy=" + (nowVacancy);
                                                                        System.out.println("현재좌석 url : "+vacancy_url2);
                                                                        RequestQueue requestQueueVacancy = Volley.newRequestQueue(getContext());
                                                                        StringRequest stringRequestVacancy = new StringRequest(Request.Method.GET,
                                                                                vacancy_url2,
                                                                                new Response.Listener<String>() {
                                                                                    public void onResponse(String response) {
                                                                                        System.out.println("vacancy_update"+vacancy_url2);
                                                                                    }
                                                                                },
                                                                                new Response.ErrorListener() {
                                                                                    @Override
                                                                                    public void onErrorResponse(VolleyError error) {
                                                                                    }
                                                                                }
                                                                        );
                                                                        requestQueueVacancy.add(stringRequestVacancy);
                                                                        requestQueueVacancy.add(stringRequestVacancy);
                                                                        String s = "T"+String.valueOf(finall);
                                                                        tableUpdate(store_id, s, 3);

                                                                    }
                                                                }
                                                            } catch (JSONException e) {}

                                                        }
                                                    }, new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                }
                                            });
                                            requestQueueStoreInfo.add(stringRequestStoreInfo);
                                        }
                                    });
                                    resultThread.start();
                                } else if(sameAs(tImageBitmap, seat4BitmapRed)){
                                    T[finall].setImageResource(R.drawable.seat4green);
                                    Thread resultThread = new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            System.out.println("현재 좌석 조회 시작");
                                            String json_url="http://122.34.93.216:8080/capstone/storeInformation_json.jsp";
                                            RequestQueue requestQueueStoreInfo = Volley.newRequestQueue(getContext());
                                            StringRequest stringRequestStoreInfo = new StringRequest(Request.Method.GET, json_url,
                                                    new Response.Listener<String>() {
                                                        @Override
                                                        public void onResponse(String response) {
                                                            Log.v("resultValue",response);
                                                            try{
                                                                JSONObject jsonObject = new JSONObject(response);
                                                                JSONArray jsonArray = jsonObject.getJSONArray("storeInformation");
                                                                store_id_list = new String[jsonArray.length()];
                                                                storeName = new String[jsonArray.length()];
                                                                category = new String[jsonArray.length()];
                                                                storeAddress = new String[jsonArray.length()];
                                                                storeSeat_count = new String[jsonArray.length()];
                                                                vacancy = new String[jsonArray.length()];
                                                                store_info = new String[jsonArray.length()];
                                                                view_count = new String[jsonArray.length()];
                                                                for(int i=0;i<jsonArray.length();i++){
                                                                    JSONObject jsonObj = jsonArray.getJSONObject(i);
                                                                    store_id_list[i] = jsonObj.getString("store_id");
                                                                    storeName[i] = jsonObj.getString("store_name");
                                                                    category[i] = jsonObj.getString("category");
                                                                    storeAddress[i] = jsonObj.getString("address");
                                                                    storeSeat_count[i] = jsonObj.getString("seat_count");
                                                                    vacancy[i] = jsonObj.getString("vacancy");
                                                                    store_info[i] = jsonObj.getString("store_info");
                                                                    view_count[i] = jsonObj.getString("view_count");
                                                                }
                                                                for(int i = 0; i < store_id_list.length; i++){
                                                                    if(store_id.equals(store_id_list[i])){
                                                                        nowVacancy=Integer.parseInt(vacancy[i]);
                                                                        System.out.println("현재좌석" + nowVacancy);
                                                                        storeSeat = Integer.parseInt(storeSeat_count[i]);
                                                                        System.out.println("총 좌석 " + storeSeat);
                                                                        vacancy_url = "http://122.34.93.216:8080/capstone/vacancy_update.jsp?store_id="+store_id;
                                                                        nowVacancy += 4;
                                                                        System.out.println("현재좌석 마이너스 : "+nowVacancy);
                                                                        String vacancy_url2 = vacancy_url + "&vacancy=" + (nowVacancy);
                                                                        System.out.println("현재좌석 url : "+vacancy_url2);
                                                                        RequestQueue requestQueueVacancy = Volley.newRequestQueue(getContext());
                                                                        StringRequest stringRequestVacancy = new StringRequest(Request.Method.GET,
                                                                                vacancy_url2,
                                                                                new Response.Listener<String>() {
                                                                                    public void onResponse(String response) {
                                                                                        System.out.println("vacancy_update"+vacancy_url2);
                                                                                    }
                                                                                },
                                                                                new Response.ErrorListener() {
                                                                                    @Override
                                                                                    public void onErrorResponse(VolleyError error) {
                                                                                    }
                                                                                }
                                                                        );
                                                                        requestQueueVacancy.add(stringRequestVacancy);
                                                                        requestQueueVacancy.add(stringRequestVacancy);
                                                                        String s = "T"+String.valueOf(finall);
                                                                        tableUpdate(store_id, s, 4);
                                                                    }
                                                                }
                                                            } catch (JSONException e) {}

                                                        }
                                                    }, new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                }
                                            });
                                            requestQueueStoreInfo.add(stringRequestStoreInfo);
                                        }
                                    });
                                    resultThread.start();
                                } else if(sameAs(tImageBitmap, seat5BitmapGreen)){
                                    T[finall].setImageResource(R.drawable.seat5red);
                                    Thread resultThread = new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            System.out.println("현재 좌석 조회 시작");
                                            String json_url="http://122.34.93.216:8080/capstone/storeInformation_json.jsp";
                                            RequestQueue requestQueueStoreInfo = Volley.newRequestQueue(getContext());
                                            StringRequest stringRequestStoreInfo = new StringRequest(Request.Method.GET, json_url,
                                                    new Response.Listener<String>() {
                                                        @Override
                                                        public void onResponse(String response) {
                                                            Log.v("resultValue",response);
                                                            try{
                                                                JSONObject jsonObject = new JSONObject(response);
                                                                JSONArray jsonArray = jsonObject.getJSONArray("storeInformation");
                                                                store_id_list = new String[jsonArray.length()];
                                                                storeName = new String[jsonArray.length()];
                                                                category = new String[jsonArray.length()];
                                                                storeAddress = new String[jsonArray.length()];
                                                                storeSeat_count = new String[jsonArray.length()];
                                                                vacancy = new String[jsonArray.length()];
                                                                store_info = new String[jsonArray.length()];
                                                                view_count = new String[jsonArray.length()];
                                                                for(int i=0;i<jsonArray.length();i++){
                                                                    JSONObject jsonObj = jsonArray.getJSONObject(i);
                                                                    store_id_list[i] = jsonObj.getString("store_id");
                                                                    storeName[i] = jsonObj.getString("store_name");
                                                                    category[i] = jsonObj.getString("category");
                                                                    storeAddress[i] = jsonObj.getString("address");
                                                                    storeSeat_count[i] = jsonObj.getString("seat_count");
                                                                    vacancy[i] = jsonObj.getString("vacancy");
                                                                    store_info[i] = jsonObj.getString("store_info");
                                                                    view_count[i] = jsonObj.getString("view_count");
                                                                }
                                                                for(int i = 0; i < store_id_list.length; i++){
                                                                    if(store_id.equals(store_id_list[i])){
                                                                        nowVacancy=Integer.parseInt(vacancy[i]);
                                                                        System.out.println("현재좌석" + nowVacancy);
                                                                        storeSeat = Integer.parseInt(storeSeat_count[i]);
                                                                        System.out.println("총 좌석 " + storeSeat);
                                                                        vacancy_url = "http://122.34.93.216:8080/capstone/vacancy_update.jsp?store_id="+store_id;
                                                                        nowVacancy -= 5;
                                                                        System.out.println("현재좌석 마이너스 : "+nowVacancy);
                                                                        String vacancy_url2 = vacancy_url + "&vacancy=" + (nowVacancy);
                                                                        System.out.println("현재좌석 url : "+vacancy_url2);
                                                                        RequestQueue requestQueueVacancy = Volley.newRequestQueue(getContext());
                                                                        StringRequest stringRequestVacancy = new StringRequest(Request.Method.GET,
                                                                                vacancy_url2,
                                                                                new Response.Listener<String>() {
                                                                                    public void onResponse(String response) {
                                                                                        System.out.println("vacancy_update"+vacancy_url2);
                                                                                    }
                                                                                },
                                                                                new Response.ErrorListener() {
                                                                                    @Override
                                                                                    public void onErrorResponse(VolleyError error) {
                                                                                    }
                                                                                }
                                                                        );
                                                                        requestQueueVacancy.add(stringRequestVacancy);
                                                                        requestQueueVacancy.add(stringRequestVacancy);
                                                                        String s = "T"+String.valueOf(finall);
                                                                        tableUpdate(store_id, s, 6);
                                                                    }
                                                                }
                                                            } catch (JSONException e) {}

                                                        }
                                                    }, new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                }
                                            });
                                            requestQueueStoreInfo.add(stringRequestStoreInfo);
                                        }
                                    });
                                    resultThread.start();
                                } else if(sameAs(tImageBitmap, seat5BitmapRed)){
                                    T[finall].setImageResource(R.drawable.seat5green);
                                    Thread resultThread = new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            System.out.println("현재 좌석 조회 시작");
                                            String json_url="http://122.34.93.216:8080/capstone/storeInformation_json.jsp";
                                            RequestQueue requestQueueStoreInfo = Volley.newRequestQueue(getContext());
                                            StringRequest stringRequestStoreInfo = new StringRequest(Request.Method.GET, json_url,
                                                    new Response.Listener<String>() {
                                                        @Override
                                                        public void onResponse(String response) {
                                                            Log.v("resultValue",response);
                                                            try{
                                                                JSONObject jsonObject = new JSONObject(response);
                                                                JSONArray jsonArray = jsonObject.getJSONArray("storeInformation");
                                                                store_id_list = new String[jsonArray.length()];
                                                                storeName = new String[jsonArray.length()];
                                                                category = new String[jsonArray.length()];
                                                                storeAddress = new String[jsonArray.length()];
                                                                storeSeat_count = new String[jsonArray.length()];
                                                                vacancy = new String[jsonArray.length()];
                                                                store_info = new String[jsonArray.length()];
                                                                view_count = new String[jsonArray.length()];
                                                                for(int i=0;i<jsonArray.length();i++){
                                                                    JSONObject jsonObj = jsonArray.getJSONObject(i);
                                                                    store_id_list[i] = jsonObj.getString("store_id");
                                                                    storeName[i] = jsonObj.getString("store_name");
                                                                    category[i] = jsonObj.getString("category");
                                                                    storeAddress[i] = jsonObj.getString("address");
                                                                    storeSeat_count[i] = jsonObj.getString("seat_count");
                                                                    vacancy[i] = jsonObj.getString("vacancy");
                                                                    store_info[i] = jsonObj.getString("store_info");
                                                                    view_count[i] = jsonObj.getString("view_count");
                                                                }
                                                                for(int i = 0; i < store_id_list.length; i++){
                                                                    if(store_id.equals(store_id_list[i])){
                                                                        nowVacancy=Integer.parseInt(vacancy[i]);
                                                                        System.out.println("현재좌석" + nowVacancy);
                                                                        storeSeat = Integer.parseInt(storeSeat_count[i]);
                                                                        System.out.println("총 좌석 " + storeSeat);
                                                                        vacancy_url = "http://122.34.93.216:8080/capstone/vacancy_update.jsp?store_id="+store_id;
                                                                        nowVacancy += 5;
                                                                        System.out.println("현재좌석 마이너스 : "+nowVacancy);
                                                                        String vacancy_url2 = vacancy_url + "&vacancy=" + (nowVacancy);
                                                                        System.out.println("현재좌석 url : "+vacancy_url2);
                                                                        RequestQueue requestQueueVacancy = Volley.newRequestQueue(getContext());
                                                                        StringRequest stringRequestVacancy = new StringRequest(Request.Method.GET,
                                                                                vacancy_url2,
                                                                                new Response.Listener<String>() {
                                                                                    public void onResponse(String response) {
                                                                                        System.out.println("vacancy_update"+vacancy_url2);
                                                                                    }
                                                                                },
                                                                                new Response.ErrorListener() {
                                                                                    @Override
                                                                                    public void onErrorResponse(VolleyError error) {
                                                                                    }
                                                                                }
                                                                        );
                                                                        requestQueueVacancy.add(stringRequestVacancy);
                                                                        requestQueueVacancy.add(stringRequestVacancy);
                                                                        String s = "T"+String.valueOf(finall);
                                                                        tableUpdate(store_id, s, 5);
                                                                    }
                                                                }
                                                            } catch (JSONException e) {}

                                                        }
                                                    }, new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                }
                                            });
                                            requestQueueStoreInfo.add(stringRequestStoreInfo);
                                        }
                                    });
                                    resultThread.start();
                                }
                            }
                        });
                    }
                }
                else if(ManagementMod == 1){
                    ManagementMod = 0;
                    Thread resetThread = new Thread(new resetThread());
                    resetThread.start();
                    for(int i = 0; i < 25; i ++){
                        final int finall = i;
                        Drawable seat2green = getDrawable(getContext(), R.drawable.seat2green);
                        Drawable seat4green = getDrawable(getContext(), R.drawable.seat4green);
                        Drawable seat5green = getDrawable(getContext(), R.drawable.seat5green);
                        Drawable seat2red = getDrawable(getContext(), R.drawable.seat2red);
                        Drawable seat4red = getDrawable(getContext(), R.drawable.seat4red);
                        Drawable seat5red = getDrawable(getContext(), R.drawable.seat5red);
                        Bitmap seat2BitmapGreen = ((BitmapDrawable)seat2green).getBitmap();
                        Bitmap seat4BitmapGreen = ((BitmapDrawable)seat4green).getBitmap();
                        Bitmap seat5BitmapGreen = ((BitmapDrawable)seat5green).getBitmap();
                        Bitmap seat2BitmapRed = ((BitmapDrawable)seat2red).getBitmap();
                        Bitmap seat4BitmapRed = ((BitmapDrawable)seat4red).getBitmap();
                        Bitmap seat5BitmapRed = ((BitmapDrawable)seat5red).getBitmap();
                        if(T[finall].getDrawable() != null){
                            Drawable tImage = T[finall].getDrawable();
                            Bitmap tImageBitmap = ((BitmapDrawable)tImage).getBitmap();
                            if(sameAs(tImageBitmap, seat2BitmapGreen)){
                                T[finall].setImageResource(R.drawable.seat2);
                            } else if(sameAs(tImageBitmap, seat2BitmapRed)){
                                T[finall].setImageResource(R.drawable.seat2);
                                String s = "T"+String.valueOf(finall);
                                tableUpdate(store_id, s, 2);
                            } else if(sameAs(tImageBitmap, seat4BitmapGreen)){
                                T[finall].setImageResource(R.drawable.seat4);
                            } else if(sameAs(tImageBitmap, seat4BitmapRed)){
                                T[finall].setImageResource(R.drawable.seat4);
                                String s = "T"+String.valueOf(finall);
                                tableUpdate(store_id, s, 4);
                            } else if(sameAs(tImageBitmap, seat5BitmapGreen)){
                                T[finall].setImageResource(R.drawable.seat5);
                            } else if(sameAs(tImageBitmap, seat5BitmapRed)){
                                T[finall].setImageResource(R.drawable.seat5);
                                String s = "T"+String.valueOf(finall);
                                tableUpdate(store_id, s, 5);
                            }
                        }
                        Toast.makeText(getContext(), "좌석 관리 모드가 꺼졌습니다.", Toast.LENGTH_SHORT).show();
                        T[i].setOnClickListener(null);
                    }

                }
            }
        });


        draggableImage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                dragShadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(null, dragShadowBuilder, view, 0);
                return true;
            }
        });
        draggableImage1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                dragShadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(null, dragShadowBuilder, view, 0);
                return true;
            }
        });
        draggableImage2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                dragShadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(null, dragShadowBuilder, view, 0);
                return true;
            }
        });

        tableLayout.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DROP:
                        View draggedView = (View) event.getLocalState();
                        ImageView targetImageView = findTargetImageView(event.getX(), event.getY()+200);  //좌석을 드래그 했을때 좌표(x,y+200)위치로 +200없으면 좌석이 위로 올라감
                        if (targetImageView != null) {
                            Drawable draggedDrawable = ((ImageView) draggedView).getDrawable();
                            targetImageView.setImageDrawable(draggedDrawable);
                        }
                        break;
                }
                return true;
            }
        });
        return view;
    }
    private ImageView findTargetImageView(float x, float y) {
        for (int i = 0; i < tableLayout.getChildCount(); i++) {
            View row = tableLayout.getChildAt(i);
            if (row instanceof TableRow) {
                for (int j = 0; j < ((TableRow) row).getChildCount(); j++) {
                    View view = ((TableRow) row).getChildAt(j);
                    if (view instanceof ImageView) {
                        int[] locationInWindow = new int[2];
                        view.getLocationInWindow(locationInWindow);
                        if (x >= locationInWindow[0] && x <= locationInWindow[0] + view.getWidth()
                                && y >= locationInWindow[1] && y <= locationInWindow[1] + view.getHeight()) {
                            return (ImageView) view;
                        }
                    }
                }
            }
        }
        return null;
    }
}