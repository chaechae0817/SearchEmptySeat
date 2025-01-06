package com.example.myapplication;

import static android.content.Context.MODE_PRIVATE;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;


public class MenusettingFragment extends Fragment /*implements MyRecyclerAdapter.MyRecyclerViewClickListener*/{
    ArrayList<ItemData> dataList = new ArrayList<>();
    View dialogView;
    EditText menu, price;
    String url,user_type, store_id;
    //final MyRecyclerAdapter adapter = new MyRecyclerAdapter(dataList);
    static int i = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menusetting, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.rcy);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        //dataList.add(new ItemData(cat[0], "김밥천국", String.format("", 1))); //title에 메뉴이름과 String format에 가격 적어주면좋을듯
        //dataList.add(new ItemData(cat[1], "봉구스 밥버거", String.format("", 1)));
        //dataList.add(new ItemData(cat[2], "허니돈", String.format("", 1)));
        //recyclerView.setAdapter(adapter);
        //adapter.setOnClickListener(this);
        //recyclerView.setVisibility(View.GONE);  // 처음에 숨깁니다.
        Button button = (Button) view.findViewById(R.id.plusmenu);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogView = (View)View.inflate(getContext(),R.layout.menu_dialog,null);

                SharedPreferences pref = getContext().getSharedPreferences("userInfo", MODE_PRIVATE);
                store_id = pref.getString("my_store_id","");
                user_type = pref.getString("User_Type", "");

                AlertDialog.Builder dlg = new AlertDialog.Builder(getContext());

                dlg.setTitle("우리 매장 메뉴 등록");
                dlg.setView(dialogView);
                dlg.setPositiveButton("추가하기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                        url = "http://122.34.93.216:8080/capstone/menu_Insert.jsp";
                        menu = (EditText) dialogView.findViewById(R.id.menuName);
                        price = (EditText) dialogView.findViewById(R.id.menuPrice);

                        if(user_type.equals("Business")){
                            // ?store_id=&menu=&price=
                            String url2 = url + "?store_id=" + store_id + "&menu=" + menu.getText().toString() + "&price=" + price.getText().toString();
                            StringRequest stringRequest = new StringRequest(Request.Method.GET,
                                    url2,
                                    new Response.Listener<String>() {
                                        public void onResponse(String response) {
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                        }
                                    }
                            );
                            requestQueue.add(stringRequest);
                        }
                    }
                });
                dlg.setNegativeButton("취소", null);
                dlg.show();
            }
        });
        return view;
    }

    /*@Override //Title,content,이미지를 누르면 이동할 수 있게 intent사용
    public void onItemClicked(int position) {

    }
    @Override
    public void onItemClicked(View v, int position) {

    }

    public void onTitleClicked(int position) {
    }

    public void onContentClicked(int position) {

    }

    @Override
    public void onImageViewClicked(int position) {

    }*/

   /* public void onItemLongClicked(int position) {
        adapter.remove(position);
        Toast.makeText(getContext(),
                dataList.get(position).getTitle()+" is removed",Toast.LENGTH_SHORT).show();
    }*/


}