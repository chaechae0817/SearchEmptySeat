package com.example.myapplication;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import net.daum.mf.map.api.CalloutBalloonAdapter;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapReverseGeoCoder;
import net.daum.mf.map.api.MapView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class MainFragment extends Fragment implements MapView.MapViewEventListener,MapView.CurrentLocationEventListener,MapReverseGeoCoder.ReverseGeoCodingResultListener,MapView.POIItemEventListener{
    MainActivity activity;
    MapView mapView;
    private MapPOIItem marker;
    private MapPOIItem[] mapPOIItems;
    Geocoder geocoder;
    String address,url;
    Double latitude,longitude;
    Button westBtn,chiBtn,korBtn,japBtn,fastBtn,btn_to_store;
    TextView dn,da,dc,dv;
    public static String [] storeId,storeName,category,storeAddress,storeSeat_count,vacancy,store_info,view_count;
    SharedPreferences preferences;
    private MarkerEventListener markerEventListener = new MarkerEventListener();
    Context context;
    public void storeClear(){
        context = activity.getApplicationContext();
        preferences = context.getSharedPreferences("StoreInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }
    class MarkerEventListener implements MapView.POIItemEventListener{

        @Override
        public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {
            return;
        }

        @Override
        public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {

        }

        @Override
        public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {

           /* AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            for(int i =0;i<storeAddress.length;i++){
                if(mapPOIItem.getItemName().equals(storeName[i])){
                    builder.setTitle(mapPOIItem.getItemName()).setMessage("빈자리 : " + vacancy[i]+"\n" + storeAddress[i]);
                    break;
                }else builder.setTitle(mapPOIItem.getItemName()).setMessage("빈자리 : 파악중입니다...");


            }
            AlertDialog alertDialog = builder.create();

            alertDialog.show();*/
            activity = (MainActivity)getActivity();
            View dialogView = getLayoutInflater().inflate(R.layout.marker_dialog,null);
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setView(dialogView);

            final AlertDialog alertDialog = builder.create();
            alertDialog.show();
            dn = (TextView)dialogView.findViewById(R.id.dialog_name);
            da = (TextView) dialogView.findViewById(R.id.dialog_address);
            dc= (TextView) dialogView.findViewById(R.id.dialog_count);
            dv = (TextView) dialogView.findViewById(R.id.dialog_vacancy);
            btn_to_store = (Button) dialogView.findViewById(R.id.btn_to_store);
            for(int i =0;i<storeAddress.length;i++) {
                if (mapPOIItem.getItemName().equals(storeName[i])) {
                    dn.setText(storeName[i]);
                    da.setText(storeAddress[i]);
                    dc.setText(storeSeat_count[i]);
                    dv.setText(vacancy[i]);
                    break;
                }else {
                    dn.setText(mapPOIItem.getItemName());
                    da.setText(mapPOIItem.getItemName());
                    dc.setText("파악 중 입니다...");
                    dv.setText("파악 중 입니다.... ㅠㅠ");
                }
            }
            btn_to_store.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();
                    preferences = getContext().getSharedPreferences("StoreInfo",MODE_PRIVATE);
                    SharedPreferences.Editor editor =preferences.edit();
                    storeClear();
                    editor.putString("storeName",mapPOIItem.getItemName());
                    Log.v("클릭시 storeName", mapPOIItem.getItemName());
                    editor.commit();
                    StoreFragment storeFragment = new StoreFragment();
                    activity.replaceFragment(storeFragment);
                }
            });


        }

        @Override
        public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

        }
    }
    class CustomCalloutBalloonAdapter implements CalloutBalloonAdapter {
        private final View mCalloutBalloon;

        public CustomCalloutBalloonAdapter() {
            mCalloutBalloon = getLayoutInflater().inflate(R.layout.custom_callout_balloon, null);
        }

        @Override
        public View getCalloutBalloon(MapPOIItem poiItem) {
            ((TextView) mCalloutBalloon.findViewById(R.id.ball_tv_name)).setText(poiItem.getItemName());
            for(int i =0;i<storeAddress.length;i++){
                if(poiItem.getItemName().equals(storeName[i])){
                    ((TextView) mCalloutBalloon.findViewById(R.id.ball_tv_vacancy)).setText("빈자리 : " +vacancy[i]);
                    break;
                }else ((TextView) mCalloutBalloon.findViewById(R.id.ball_tv_vacancy)).setText("빈자리 : 파악중입니다..");

            }

            return mCalloutBalloon;
        }

        @Override
        public View getPressedCalloutBalloon(MapPOIItem poiItem) {
            return mCalloutBalloon;
        }
    }
    public void storeInformationDialog(View v){
        View dialogView = getLayoutInflater().inflate(R.layout.marker_dialog,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setView(dialogView);

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();


    }
    //가게 고유의 ID 값, 가게이름, 카테고리, 주소, 총 테이블, 남은 테이블, 가게정보, 검색 수
    public void searchAddress(String str){
        String ss = str;
        List<Address> addressList = null;
        geocoder = new Geocoder(getContext());
        try {
            addressList = geocoder.getFromLocationName(str, 30); // 최대 검색 결과 개수
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        String []splitStr = addressList.get(0).toString().split(",");
        address = splitStr[0].substring(splitStr[0].indexOf("\"") + 1,splitStr[0].length() - 2); // 주소
        latitude = Double.valueOf(splitStr[10].substring(splitStr[10].indexOf("=") + 1)); // 위도
        longitude = Double.valueOf(splitStr[12].substring(splitStr[12].indexOf("=") + 1)); // 경도

    }
    public void sendRequest() {
        url="http://122.34.93.216:8080/capstone/storeInformation_json.jsp";
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("MF_resultValue",response);

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
                                Log.v("MF_storeAddress",storeAddress[i]);

                            }
                            //marker를띄우기 위해 하는 코드
                            for(int i=0;i<storeAddress.length;i++){
                                searchAddress(storeAddress[i]);
                                marker = new MapPOIItem();
                                marker.setItemName(storeName[i]);
                                switch (category[i]){
                                    case "Western Food":
                                        marker.setTag(0);
                                        break;
                                    case "Chinese Food":
                                        marker.setTag(1);break;
                                    case "Korean Food":
                                        marker.setTag(2);break;
                                    case "Japan Food" :
                                        marker.setTag(3);break;
                                    case "Fast Food" :
                                        marker.setTag(4);break;
                                    default:break;
                                }
                                marker.setMapPoint(MapPoint.mapPointWithGeoCoord(latitude,longitude));
                                marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                mapView.setCalloutBalloonAdapter(new CustomCalloutBalloonAdapter());
                                mapView.setPOIItemEventListener(markerEventListener);
                                mapView.addPOIItem(marker);


                            }
                            marker = new MapPOIItem();
                            marker.setItemName("학교");
                            marker.setTag(0);//특정 값을 찾기 위한 식별자로 사용 가능
                            marker.setMapPoint(MapPoint.mapPointWithGeoCoord(37.5846, 126.9256 ));
                            marker.setMarkerType(MapPOIItem.MarkerType.YellowPin);// yellow red blu pin 가능 뭐 절반이상 차면 레드 이런식 가능할듯?
                            mapView.addPOIItem(marker);


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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        activity = (MainActivity)getActivity();

        westBtn = (Button)view.findViewById(R.id.WestBtn); //양식 - 0
        chiBtn = (Button)view.findViewById(R.id.ChiBtn);  //중식  - 1
        korBtn = (Button)view.findViewById(R.id.KorBtn);  //한식  - 2
        japBtn = (Button)view.findViewById(R.id.JapBtn);  //일식  - 3
        fastBtn = (Button)view.findViewById(R.id.FastBtn); //패스트푸드 - 4
        mapView = view.findViewById(R.id.map_view);

        mapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(37.5846, 126.9256), 5, true);
        mapView.setCurrentLocationEventListener(this);
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);//현재 위치 찍어줌

        mapView.setMapViewEventListener(this);
        mapView.setPOIItemEventListener(this);
        mapView.setPOIItemEventListener(markerEventListener);

        westBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                westBtn.setBackgroundResource(R.drawable.button_click);
                chiBtn.setBackgroundResource(R.drawable.button_basic);
                korBtn.setBackgroundResource(R.drawable.button_basic);
                japBtn.setBackgroundResource(R.drawable.button_basic);
                fastBtn.setBackgroundResource(R.drawable.button_basic);

                mapView.removeAllPOIItems(); //현재 맵에 있는 모든 마커 삭제하는 메소드
                for(int i=0;i<storeAddress.length;i++){
                    searchAddress(storeAddress[i]); //주소 매핑 메소드
                    marker = new MapPOIItem(); //새로운 마커 생성
                    marker.setItemName(storeName[i]); //마커 클릭시 나오는 말풍선에 들어갈 내용
                    if(category[i].equals("Western Food")){ //카테고리가 '양식' 일때만 표시
                        marker.setMapPoint(MapPoint.mapPointWithGeoCoord(latitude,longitude));
                        marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                        mapView.setCalloutBalloonAdapter(new CustomCalloutBalloonAdapter());
                        //mapView.setPOIItemEventListener(new MarkerEventListener());
                        mapView.addPOIItem(marker);
                    }
                }
            }
        });
        chiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                westBtn.setBackgroundResource(R.drawable.button_basic);
                chiBtn.setBackgroundResource(R.drawable.button_click);
                korBtn.setBackgroundResource(R.drawable.button_basic);
                japBtn.setBackgroundResource(R.drawable.button_basic);
                fastBtn.setBackgroundResource(R.drawable.button_basic);

                mapView.removeAllPOIItems();
                for(int i=0;i<storeAddress.length;i++){
                    searchAddress(storeAddress[i]);
                    marker = new MapPOIItem();
                    marker.setItemName(storeName[i]);
                    if(category[i].equals("Chinese Food")){
                        marker.setMapPoint(MapPoint.mapPointWithGeoCoord(latitude,longitude));
                        marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                        mapView.setCalloutBalloonAdapter(new CustomCalloutBalloonAdapter());
                        // mapView.setPOIItemEventListener(new MarkerEventListener());
                        mapView.addPOIItem(marker);
                    }
                }
            }
        });
        korBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                westBtn.setBackgroundResource(R.drawable.button_basic);
                chiBtn.setBackgroundResource(R.drawable.button_basic);
                korBtn.setBackgroundResource(R.drawable.button_click);
                japBtn.setBackgroundResource(R.drawable.button_basic);
                fastBtn.setBackgroundResource(R.drawable.button_basic);

                mapView.removeAllPOIItems();
                for(int i=0;i<storeAddress.length;i++){
                    searchAddress(storeAddress[i]);
                    marker = new MapPOIItem();
                    marker.setItemName(storeName[i]);
                    if(category[i].equals("Korean Food")){
                        marker.setMapPoint(MapPoint.mapPointWithGeoCoord(latitude,longitude));
                        marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                        mapView.setCalloutBalloonAdapter(new CustomCalloutBalloonAdapter());
                        //mapView.setPOIItemEventListener(new MarkerEventListener());
                        mapView.addPOIItem(marker);
                    }
                }
            }
        });
        japBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //sendRequest();
                westBtn.setBackgroundResource(R.drawable.button_basic);
                chiBtn.setBackgroundResource(R.drawable.button_basic);
                korBtn.setBackgroundResource(R.drawable.button_basic);
                japBtn.setBackgroundResource(R.drawable.button_click);
                fastBtn.setBackgroundResource(R.drawable.button_basic);

                mapView.removeAllPOIItems();
                for(int i=0;i<storeAddress.length;i++){
                    searchAddress(storeAddress[i]);
                    marker = new MapPOIItem();
                    marker.setItemName(storeName[i]);
                    if(category[i].equals("Japan Food")){
                        marker.setMapPoint(MapPoint.mapPointWithGeoCoord(latitude,longitude));
                        marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                        mapView.setCalloutBalloonAdapter(new CustomCalloutBalloonAdapter());
                        //mapView.setPOIItemEventListener(new MarkerEventListener());
                        mapView.addPOIItem(marker);
                    }
                }
            }
        });
        fastBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //sendRequest();
                westBtn.setBackgroundResource(R.drawable.button_basic);
                chiBtn.setBackgroundResource(R.drawable.button_basic);
                korBtn.setBackgroundResource(R.drawable.button_basic);
                japBtn.setBackgroundResource(R.drawable.button_basic);
                fastBtn.setBackgroundResource(R.drawable.button_click);
                mapView.removeAllPOIItems();
                for(int i=0;i<storeAddress.length;i++){
                    searchAddress(storeAddress[i]);
                    marker = new MapPOIItem();
                    marker.setItemName(storeName[i]);
                    if(category[i].equals("Fast Food")){
                        marker.setMapPoint(MapPoint.mapPointWithGeoCoord(latitude,longitude));
                        marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                        mapView.setCalloutBalloonAdapter(new CustomCalloutBalloonAdapter());
                        //mapView.setPOIItemEventListener(new MarkerEventListener());
                        mapView.addPOIItem(marker);
                    }
                }
            }
        });

        return view;

    }


    public void onMapViewInitialized(MapView mapView) {
        sendRequest();

    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }


    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {
        // 중심점 변경 이벤트 처리
    }


    public void onMapViewZoomLevelChanged(MapView mapView, int i) {
        // 줌 레벨 변경 이벤트 처리
    }

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {

    }
    //MapView.CurrentLocationEventListener
    @Override
    public void onCurrentLocationUpdate(MapView mapView, MapPoint mapPoint, float v) {

    }

    @Override
    public void onCurrentLocationDeviceHeadingUpdate(MapView mapView, float v) {

    }

    @Override
    public void onCurrentLocationUpdateFailed(MapView mapView) {

    }

    @Override
    public void onCurrentLocationUpdateCancelled(MapView mapView) {

    }

    @Override
    public void onReverseGeoCoderFoundAddress(MapReverseGeoCoder mapReverseGeoCoder, String s) {
        mapReverseGeoCoder.toString();
        onFinishReverseGeoCoding(s);
    }

    @Override
    public void onReverseGeoCoderFailedToFindAddress(MapReverseGeoCoder mapReverseGeoCoder) {
        onFinishReverseGeoCoding("Fail");

    }
    private void onFinishReverseGeoCoding(String result) {
        Toast.makeText(getContext(), "Reverse Geo-coding : " + result, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {

    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

    }
}
