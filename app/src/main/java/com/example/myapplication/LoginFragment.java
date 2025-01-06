package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class LoginFragment extends Fragment {
    MainActivity activity;
    EditText loginIdEdit, loginPwdEdit;
    Button loginbtn,westbtn;
    String type,userMemory;
    Integer intMemory;//userId[i]의 i를 기억하는 용도 -> 로그인 설정시 필요
    SharedPreferences preferences;
    public static String[] sequence,userId,userPwd,userType,userName,phonenumber;
    public static int userJsonArray;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_login, container, false);
        loginIdEdit=(EditText) view.findViewById(R.id.LoginIdEdit);
        loginPwdEdit=(EditText) view.findViewById(R.id.LoginPwdEdit);
        loginbtn = (Button) view.findViewById(R.id.login);
        westbtn = (Button) view.findViewById(R.id.WestBtn);

        //회원용 root,1234
        //사업자용 boot,4321
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        RadioGroup radioGroup = (RadioGroup)view.findViewById(R.id.membershipRG);
        RadioButton radioButton =(RadioButton) view.findViewById(R.id.businessBtn);
        RadioButton radioButton2 =(RadioButton) view.findViewById(R.id.businessBtn);
        loginIdEdit.setFocusable(false);
        loginPwdEdit.setFocusable(false);
        MainActivity.url="http://122.34.93.216:8080/capstone/users_json.jsp";
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, MainActivity.url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("LG_resultValue",response);

                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("users");
                            userJsonArray = jsonArray.length();
                            userName = new String[jsonArray.length()];
                            userPwd = new String[jsonArray.length()];
                            userType = new String[jsonArray.length()];
                            userId = new String[jsonArray.length()];
                            sequence = new String[jsonArray.length()];
                            phonenumber = new String[jsonArray.length()];
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObj = jsonArray.getJSONObject(i);
                                sequence[i] = jsonObj.getString("sequence");
                                userId[i] = jsonObj.getString("id");
                                userPwd[i] = jsonObj.getString("password");
                                userName[i] = jsonObj.getString("name");
                                userType[i] = jsonObj.getString("userType");
                                phonenumber[i] = jsonObj.getString("phoneNumber");
                            }
                        } catch (JSONException e) {}

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId){
                    case R.id.businessBtn://사업자 로그인 버튼
                        type = "Business";
                        loginIdEdit.setFocusableInTouchMode (true);
                        loginIdEdit.setFocusable(true);
                        loginPwdEdit.setFocusableInTouchMode (true);
                        loginPwdEdit.setFocusable(true);
                        break;
                    case R.id.userBtn://회원 로그인 버튼
                        type = "User";
                        loginIdEdit.setFocusableInTouchMode (true);
                        loginIdEdit.setFocusable(true);
                        loginPwdEdit.setFocusableInTouchMode (true);
                        loginPwdEdit.setFocusable(true);
                        break;
                }
            }
        });


        loginIdEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(radioButton.isChecked()||radioButton2.isChecked())){
                    // edt1.setInputType(InputType.TYPE_NULL);
                    Toast.makeText(getContext(),"로그인 유형을 골라주세요!",Toast.LENGTH_SHORT).show();
                }else{

                }
            }
        });
        loginPwdEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(radioButton.isChecked()||radioButton2.isChecked())){
                    // edt1.setInputType(InputType.TYPE_NULL);
                    Toast.makeText(getContext(),"로그인 유형을 골라주세요!",Toast.LENGTH_SHORT).show();
                }else{

                }
            }
        });
        activity = (MainActivity)getActivity();
        //Fragment에서 Fragment로의 직접적인 화면 전환은 불가능하므로 MainActivity의 activity를 선언한 뒤 MainActivity의 replaceFragment 메소드를 실행하여
        //Fragment -> Activity -> Fragment 형식으로 화면 전환
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preferences =getContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor =preferences.edit();
                //editor.clear();
                for(int i=0;i<userName.length;i++){
                    if(userId[i].equals(loginIdEdit.getText().toString()) && userPwd[i].equals(loginPwdEdit.getText().toString())){
                        editor.putString("Id",loginIdEdit.getText().toString());
                        editor.putString("Phone",phonenumber[i]);
                        editor.putString("User_Type",userType[i]);
                        editor.commit();
                        userMemory = userId[i];// userMemory를 통해 userId 값을 기억함.
                        intMemory = i;

                        Toast.makeText(getContext(), "로그인 성공!! 아이디 : " + loginIdEdit.getText().toString() + "비번 : " + loginPwdEdit.getText().toString(), Toast.LENGTH_SHORT).show();

                        MainFragment mainFragment = new MainFragment();
                        activity.replaceFragment(mainFragment);

                    }

                }
                //Toast.makeText(getContext(), "로그인 실패!! \n아이디 : " + loginIdEdit.getText().toString() + "\n비번 : " + loginPwdEdit.getText().toString(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(getContext(),"아이디 또는 비밀번호가 틀렸습니다.",Toast.LENGTH_SHORT).show();
            }
        });
        westbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MembershipFragment membershipFragment = new MembershipFragment();
                activity.replaceFragment(membershipFragment);
            }
        });



        return view;
    }
}