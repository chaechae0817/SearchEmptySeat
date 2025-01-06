package com.example.myapplication;

import static android.content.Context.MODE_PRIVATE;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment {
    private List<String> list;
    int [] arr ;
    int len;
    String url,view_url;
    String [] storeId,storeName,category,storeAddress,storeSeat_count,vacancy,store_info,view_count,sortStore;
    Button btn_search;
    StoreFragment storeFragment;
    MainActivity mainActivity;
    SharedPreferences preferences;
    Context context;
    ArrayList<SearchData> dataList = new ArrayList<>();
    RecyclerView recyclerView;
    public SearchAdapater searchAdapater;
    LinearLayoutManager linearLayoutManager;
    public void arrSort(){
        Log.v("arrSort","arrSort 조회");
        for(int i=0; i<arr.length; i++) {
            for(int j=i+1; j<arr.length; j++) {
                if(arr[i] < arr[j]) { //내림차순
                    int tmp = arr[i];
                    String str = sortStore[i];
                    arr[i] = arr[j];
                    sortStore[i]=sortStore[j];
                    arr[j] = tmp;
                    sortStore[j]=str;
                }
            }
            Log.v(i+"번째 arrSort", String.valueOf(arr[i]));
            Log.v(i+"번째 sortStore", String.valueOf(sortStore[i]));
        }

    }

    public void storeClear(){
        context = mainActivity.getApplicationContext();
        preferences = context.getSharedPreferences("StoreInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }
    public void SortList(){
        arrSort();
    }
    public void sendRequest() {
        url="http://122.34.93.216:8080/capstone/storeInformation_json.jsp";
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("SF_resultValue",response);

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
                            arr = new int[jsonArray.length()];
                            sortStore = new String[jsonArray.length()];
                            len = jsonArray.length();
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
                                sortStore[i] = storeName[i];
                                arr[i] = Integer.parseInt(view_count[i]);

                            }
                            dataList = new ArrayList<>();
                            for(int i=0;i<jsonArray.length();i++){
                                list.add(storeName[i]);
                                Log.v(i+"view_count의 값",view_count[i]);
                                Log.v(i+"arr의 값", String.valueOf(arr[i]));
                                SortList();
                                for (int j=0;j<jsonArray.length();j++){

                                }
                                dataList.add(new SearchData(sortStore[i],i+1));
                                recyclerAttach();
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
        searchAdapater = new SearchAdapater(dataList);
        recyclerView.setAdapter(searchAdapater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        mainActivity = (MainActivity)getActivity();
        list = new ArrayList<String>();
        btn_search = (Button) view.findViewById(R.id.searchButton);
        sendRequest();
        recyclerView = view.findViewById(R.id.searchRecycler);
        linearLayoutManager = new LinearLayoutManager(view.getContext());

        final android.widget.AutoCompleteTextView autoCompleteTextView = (android.widget.AutoCompleteTextView) view.findViewById(R.id.autoCompleteTextView);

        // AutoCompleteTextView 에 어뎁터를 연결한다.
        autoCompleteTextView.setAdapter(new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_dropdown_item_1line,  list ));


        RequestQueue requestQueue2 = Volley.newRequestQueue(getContext());
        view_url="http://122.34.93.216:8080/capstone/store_add_view_count.jsp";
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for(int i=0;i<storeAddress.length;i++){//search로 검색할 때마다 view_count를 ++해주는 코드
                    String searchContents = autoCompleteTextView.getText().toString();
                    if(searchContents.equals(storeName[i])){
                        Integer plus = Integer.parseInt(view_count[i])+1;//플러스에 view_count값을 가져오는데 +1을 해서 가져옴.
                        String stringPlus = plus.toString();
                        String url2 = view_url + "?store_id=" +storeId[i] + "&view_count=" + stringPlus;
                        preferences = getContext().getSharedPreferences("StoreInfo",MODE_PRIVATE);
                        StringRequest stringRequest = new StringRequest(Request.Method.GET, url2,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                        SharedPreferences.Editor editor =preferences.edit();
                                        storeClear();
                                        editor.putString("storeName",searchContents);
                                        Log.v("클릭시 storeName", searchContents);
                                        editor.commit();
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        });
                        requestQueue2.add(stringRequest);


                        break;
                    }
                }
                storeFragment = new StoreFragment();
                mainActivity.replaceFragment(storeFragment);


            }
        });


        return view;
    }

}