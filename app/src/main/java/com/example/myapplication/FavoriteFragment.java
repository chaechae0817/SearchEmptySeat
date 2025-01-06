package com.example.myapplication;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

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


public class FavoriteFragment extends Fragment {
    ArrayList<FavoriteData> dataList = new ArrayList<>();
    RecyclerView recyclerView;
    public FavoriteAdapter favoriteAdapter;
    LinearLayoutManager linearLayoutManager;
    MainActivity mainActivity;

    String url,imageUrl,favoriteUrl,userurl;
    String [] storeId,storeName,category,storeAddress,storeSeat_count,vacancy,store_info,view_count;
    String [] userId,favoriteStore,userSendId;
    String [] sequence;
    String [] getFavoriteStore;
    SharedPreferences preferences;
    String uName;
    String userSequence;
    int favoriteJsonLength,userJsonLength,storeJsonLength;
    public class sendUserRequest implements Runnable{
        @Override
        public void run() {
            userurl="http://122.34.93.216:8080/capstone/users_json.jsp";
            Log.v("1sendUserRequest","sendUserRequest");
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            StringRequest stringRequest = new StringRequest(Request.Method.GET, userurl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                             uName= MainActivity.uName;
                            Log.v("sendUser_resultValue1",response);
                            Log.v("sendUser_uName",uName);
                            try{
                                dataList = new ArrayList<>();
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray jsonArray = jsonObject.getJSONArray("users");
                                userJsonLength = jsonArray.length();
                                sequence = new String[jsonArray.length()];
                                userSendId = new String[jsonArray.length()];
                                for(int i=0;i<jsonArray.length();i++){
                                    JSONObject jsonObj = jsonArray.getJSONObject(i);
                                    sequence[i] = jsonObj.getString("sequence");
                                    userSendId[i] = jsonObj.getString("id");
                                    //Thread thread1 = new Thread(new sendRequest());
                                    //thread1.start();

                                }
                                favoriteUrl="http://122.34.93.216:8080/capstone/favorites_json.jsp";
                                Log.v("2sendFavoriteRequest","sendFavoriteRequest");
                                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                                StringRequest stringRequest = new StringRequest(Request.Method.GET, favoriteUrl,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                Log.v("Favorite_resultValue2",response);
                                                try{

                                                    JSONObject jsonObject = new JSONObject(response);
                                                    JSONArray jsonArray = jsonObject.getJSONArray("favorites");
                                                    favoriteJsonLength = jsonArray.length();
                                                    userId = new String[jsonArray.length()];
                                                    favoriteStore = new String[jsonArray.length()];
                                                    getFavoriteStore = new String[jsonArray.length()];
                                                    Log.v("gFS 길이", String.valueOf(getFavoriteStore.length));
                                                    // Log.v("userSequence",userSequence);
                                                    //sendRequest();
                                                    for(int i=0;i<jsonArray.length();i++) {
                                                        JSONObject jsonObj = jsonArray.getJSONObject(i);
                                                        favoriteStore[i] = jsonObj.getString("store_id");//store_id값
                                                        userId[i] = jsonObj.getString("user_id");//sequence 값
                                                        //========= User_id값에 해당하는 store_id를 저장하는 변수 getFavoriteStore에 값 담는 코드=====<<오류1>>=========

                                                    }
                                                    Log.v("3sendRequest","sendRequest");
                                                    url="http://122.34.93.216:8080/capstone/storeInformation_json.jsp";
                                                    RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                                                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                                                            new Response.Listener<String>() {
                                                                @Override
                                                                public void onResponse(String response) {
                                                                    Log.v("sendRequestresultValue3",response);
                                                                    try{
                                                                        JSONObject jsonObject = new JSONObject(response);
                                                                        JSONArray jsonArray = jsonObject.getJSONArray("storeInformation");
                                                                        storeJsonLength = jsonArray.length();
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
                                                                        for(int i=0;i<storeJsonLength;i++){
                                                                            Log.v("setData 시작5",uName);
                                            //============= 로그인한 user의 Id를 가져와 sequence 값으로 매칭 ===================

                                                                            for(int j=0;j<userJsonLength;j++){
                                                                                if(uName.equals(userSendId[j])){
                                                                                    userSequence = sequence[j];
                                                                                    Log.v("userSequence",userSequence);
                                                                                }
                                                                            }
                                                                            int k=0;
                                                                            for(int j=0;j<favoriteJsonLength;j++){
                                                                                Log.v("userSequence",userSequence);
                                                                                Log.v("k",String.valueOf(k));
                                                                                Log.v("userId[]",userId[j]);
                                                                                if(userSequence.equals(userId[j])){
                                                                                    getFavoriteStore[k] = favoriteStore[j];
                                                                                    k++;
                                                                                    //Log.v("getFavorite",getFavoriteStore[j]);
                                                                                }
                                                                            }
                                            // ======== ImageUrl에 들어갈 값 생성하는 코드 gFS = user_id에 해당하는 store_id값들 배열
                                                                            //// gfs={0,13,3라면

                                                                            for (int j = 0; j < getFavoriteStore.length; j++) {
                                                                                //Log.v("getFavorties",getFavoriteStore[j]);

                                                                                if (storeId[i].equals(getFavoriteStore[j])) {
                                                                                    String cat = category[i];
                                                                                    switch (cat) {
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
                                                                                    imageUrl = "http://122.34.93.216:8080/capstone/drawable/" + cat + storeId[i] + ".png";
                                                                                    Log.v("urlLog", imageUrl);
                                                                                    dataList.add(new FavoriteData(storeName[i], imageUrl));//가게이름, 이미지url
                                                                                    Log.v("dataList", String.valueOf(dataList));
                                                                                    //recyclerAttach();

                                                                                }
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



                                                } catch (JSONException e) {}

                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                });
                                requestQueue.add(stringRequest);

                                //Log.v("userSequence",userSequence);


                            } catch (JSONException e) {}

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

            requestQueue.add(stringRequest);
        }

    }
    public void recyclerAttach(){
        Log.v("recyclerAttack5","recyclerAttack");

        recyclerView.setLayoutManager(linearLayoutManager);

        mainActivity = (MainActivity)getActivity();

        favoriteAdapter = new FavoriteAdapter(dataList);

        favoriteAdapter.setOnItemClickListener(new FavoriteAdapter.OnItemClickListener() {
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
        recyclerView.setAdapter(favoriteAdapter);
    }
    public void storeClear(){
        preferences = getContext().getSharedPreferences("StoreInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        recyclerView = view.findViewById(R.id.favorite_recycler);
        linearLayoutManager = new LinearLayoutManager(view.getContext());

        Thread thread1 = new Thread(new sendUserRequest());

        thread1.start();






        return view;
    }
}