package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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


public class StoreinfoFragment extends Fragment {
    private EditText address, name, info;
    private Button insertBtn;
    private RadioGroup categoryGroup;
    private String url = "http://122.34.93.216:8080/capstone/store_Insert.jsp";
    private String categoryString, userSequence, store_id;
    private String [] store_id_list,storeName,storeAddress,category,storeSeat_count, vacancy,store_info,view_count, store_id_draw;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_storeinfo, container, false);
        //SharedPreferences pref = getSharedPreferences("userInfo", MODE_PRIVATE);
        //userSequence = pref.getString("sequence","");
        userSequence = "7";             //나중에 지우기

        address = (EditText)view.findViewById(R.id.storeAdress);
        name = (EditText) view.findViewById(R.id.storeName);
        info = (EditText) view.findViewById(R.id.storeInfo);

        categoryGroup = (RadioGroup) view.findViewById(R.id.categoryGroup);

        insertBtn = (Button) view.findViewById(R.id.insertBtn);

        categoryGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.korean:
                        categoryString = "Korean Food";
                        break;
                    case R.id.western:n:
                    categoryString = "Western Food";
                        break;
                    case R.id.japan:
                        categoryString = "Japan Food";
                        break;
                    case R.id.chinese:n:
                    categoryString = "Chinese Food";
                        break;
                    case R.id.fast:n:
                    categoryString = "Fast Food";
                        break;
                }
            }
        });
        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if(categoryString != null){
                            System.out.println("카테고리 : " + categoryString);
                            // ?store_name= &category= &address= &store_info=
                            String url2 = url + "?store_name=" + name.getText().toString() + "&category=" + categoryString + "&address=" + address.getText().toString() + "&store_info=" + info.getText().toString();
                            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                            StringRequest stringRequest = new StringRequest(Request.Method.GET,
                                    url2,
                                    new Response.Listener<String>() {
                                        public void onResponse(String response) {
                                            Toast.makeText(getContext(),"수정 완료!", Toast.LENGTH_SHORT).show();
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(getContext(),"수정 실패", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                            );
                            requestQueue.add(stringRequest);
                        }
                        else {
                            Toast.makeText(getContext(),"카테고리를 선택해주세요", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).start();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
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
                                                if(name.getText().toString().equals(storeName[i])){
                                                    store_id = store_id_list[i];
                                                    System.out.println("스토어 아이디" + store_id);
                                                    String myStoreIdUrl = "http://122.34.93.216:8080/capstone/myStoreId_update.jsp?store_id="+store_id+"&user_sequence="+userSequence;
                                                    System.out.println("업데이트url : "+myStoreIdUrl);
                                                    RequestQueue requestQueuemyStoreId = Volley.newRequestQueue(getContext());
                                                    StringRequest stringRequestmyStoreId = new StringRequest(Request.Method.GET,
                                                            myStoreIdUrl,
                                                            new Response.Listener<String>() {
                                                                public void onResponse(String response) {
                                                                    System.out.println("vacancy_update"+myStoreIdUrl);
                                                                }
                                                            },
                                                            new Response.ErrorListener() {
                                                                @Override
                                                                public void onErrorResponse(VolleyError error) {
                                                                }
                                                            }
                                                    );
                                                    requestQueuemyStoreId.add(stringRequestmyStoreId);
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
                }).start();
            }
        });
        return view;
    }
}