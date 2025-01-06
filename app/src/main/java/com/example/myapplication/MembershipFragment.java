package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


public class MembershipFragment extends Fragment {
    String url,type;
    Button submitBtn,idBtn;
    TextView phoneTv,idTv,pwdTv,pwdTestTv,nameTv;
    RadioGroup radioGroup;
    RadioButton businessBtn,userBtn;
    MainActivity mainActivity;
    int USE =0; //중복체크 버튼눌렀는지 확인 . 0은 미사용 1은 사용
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_membership, container, false);
        submitBtn = (Button)view.findViewById(R.id.submit);
        idBtn = (Button)view.findViewById(R.id.idbutton);
        phoneTv = (TextView)view.findViewById(R.id.writephone);
        idTv = (TextView)view.findViewById(R.id.writeid);
        pwdTv = (TextView)view.findViewById(R.id.writepassword);
        pwdTestTv = (TextView)view.findViewById(R.id.writepassword);
        nameTv = (TextView)view.findViewById(R.id.writename);
        radioGroup = (RadioGroup)view.findViewById(R.id.membershipRG);
        businessBtn = (RadioButton)view.findViewById(R.id.businessBtn);
        userBtn = (RadioButton)view.findViewById(R.id.userBtn);
        mainActivity = (MainActivity)getActivity();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId){
                    case R.id.businessBtn://사업자 로그인 버튼
                        type = "Business";

                        break;
                    case R.id.userBtn://회원 로그인 버튼
                        type = "User";

                        break;
                }
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        url="http://122.34.93.216:8080/capstone/register_db1.jsp";
        idBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0;i<LoginFragment.userName.length;i++){
                    if(idTv.getText().toString().equals(LoginFragment.userName[i])){//idTv의 값이 userName과 같으면
                        Toast.makeText(getContext(),"사용할 수 없는 아이디입니다.",Toast.LENGTH_SHORT).show();
                        break;
                    }
                    else {
                        Toast.makeText(getContext(),"사용할 수 있는 아이디입니다.",Toast.LENGTH_SHORT).show();
                        USE = 1;
                        break;
                    }
                }
            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(USE ==0){
                    Toast.makeText(getContext(),"중복체크를 해주세요",Toast.LENGTH_SHORT).show();
                }
                if(idTv.getText().toString().length()==0|pwdTv.getText().toString().length()==0|nameTv.getText().toString().length()==0|
                        type.length()==0|phoneTv.getText().toString().length()==0){
                    Toast.makeText(getContext(),"빈칸이 있어서는 안됩니다",Toast.LENGTH_SHORT).show();
                }
                else {
                    String url2 = url + "?id=" + idTv.getText().toString() + "&password=" + pwdTv.getText().toString() + "&name=" + nameTv.getText().toString()
                            + "&userType=" + type + "&phoneNumber=" + phoneTv.getText().toString();
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url2,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Toast.makeText(getActivity(), "회원가입 성공!", Toast.LENGTH_SHORT).show();
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                    requestQueue.add(stringRequest);
                    Intent intent = new Intent(getActivity(),MainActivity.class);
                    startActivity(intent);

                }

            }
        });
        return view;

    }
}