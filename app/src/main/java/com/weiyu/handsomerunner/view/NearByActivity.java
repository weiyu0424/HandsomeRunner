package com.weiyu.handsomerunner.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.LogoPosition;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.weiyu.handsomerunner.Config;
import com.weiyu.handsomerunner.R;

import java.util.List;


public class NearByActivity extends AppCompatActivity implements BDLocationListener, OnGetPoiSearchResultListener, View.OnClickListener {
    private TextureMapView mapView = null;
    private LocationClient mLocationClient = null;
    private BaiduMap mBaiduMap = null;
    private PoiSearch poiSearch = null;
    private boolean isFirstLocation = true;
    private EditText etSearch = null;
    private ImageView ivSearch = null;

    private ImageView ivBackOfNearBy = null;
    private LatLng ll = null;
    /**
     * marker icons
     */
    private int[] markers = {R.drawable.icon_marka,R.drawable.icon_markb,R.drawable.icon_markc,R.drawable.icon_markd,R.drawable.icon_marke,R.drawable.icon_markf,R.drawable.icon_markg,R.drawable.icon_markh,R.drawable.icon_marki,R.drawable.icon_markj};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_near_by);
        initView();
        initMap();
        initEvent();

    }

    private void initView() {
        mapView = (TextureMapView) findViewById(R.id.bmapView);
        ivBackOfNearBy = (ImageView) findViewById(R.id.iv_back_near_by);
        etSearch = (EditText) findViewById(R.id.et_search);
        ivSearch = (ImageView) findViewById(R.id.iv_search);
    }

    /**
     * init Baidu Map Widget
     */
    private void initMap() {
        mBaiduMap = mapView.getMap();

        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mBaiduMap.setTrafficEnabled(true);
        mapView.setLogoPosition(LogoPosition.logoPostionRightBottom);

        mBaiduMap.setMyLocationEnabled(true);
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(this);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // open the gps
        option.setCoorType("bd09ll"); // set coordination type
        option.setScanSpan(1000);
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }


    private void initEvent() {
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Config.toast(NearByActivity.this,marker.getTitle());
                return false;
            }
        });

        ivBackOfNearBy.setOnClickListener(this);
        ivSearch.setOnClickListener(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        poiSearch.destroy();
        mapView.onDestroy();
        mLocationClient.stop();
        mBaiduMap.setMyLocationEnabled(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }


    /**
     * auto locate the position of a user, in this method, the app can get the concrete coordinator
     * @param location
     */
    @Override
    public void onReceiveLocation(BDLocation location) {
        if (location == null || mapView == null) {
            return;
        }
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(location.getRadius())
                // set orientation information，clockwise 0-360
                .direction(100).latitude(location.getLatitude())
                .longitude(location.getLongitude()).build();
        mBaiduMap.setMyLocationData(locData);

        if(isFirstLocation) {
            isFirstLocation = false;
            ll = new LatLng(location.getLatitude(),
                    location.getLongitude());
            MapStatus.Builder builder = new MapStatus.Builder();
            builder.target(ll).zoom(17.0f);
            mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            poiSearch = PoiSearch.newInstance();
            searchByKeyWords("公园");
        }

    }


    /**
     * search places by keyword in the Baidu Map
     * @param keyword:
     */
    private void searchByKeyWords(String keyword) {
        mBaiduMap.clear();
        poiSearch.setOnGetPoiSearchResultListener(this);
        poiSearch.searchNearby(new PoiNearbySearchOption().location(ll).keyword(keyword).pageNum(0).radius(5000));
    }

    @Override
    public void onGetPoiResult(PoiResult poiResult) {
        List<PoiInfo> poiInfos = poiResult.getAllPoi();
        System.out.println("size:" + poiInfos.size());

        for(int i = 0;i < poiInfos.size();i++){
            markSearchResult(poiInfos.get(i),i);
        }
        /*for (PoiInfo poiInfo : poiInfos) {
//            String address = poiInfo.address;
//            String name = poiInfo.name;
//            System.out.println(address + ":" + name);
//            LatLng location = poiInfo.location;
            markSearchResult(poiInfo);
        }*/
    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
        System.out.println("detail:    " + poiDetailResult.getName() + ":" + poiDetailResult.getAddress());
    }


    /**
     * this method can be used to create a marker
     * @param point: the given position which need to be created a marker
     * @param index: the marker index, different marker has different icon
     */
    public void markSearchResult(PoiInfo point,int index) {
//        LatLng point = new LatLng(39.963175, 116.400244);
        //build a Marker icon
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(markers[index]);

        //create a MarkerOption, which can be used to add marker
        OverlayOptions option = new MarkerOptions()
                .position(point.location)
                .icon(bitmap)
                .draggable(true)
                .zIndex(9);

        //add Marker in the Baidu Map and display it in the map
        Marker marker = (Marker) (mBaiduMap.addOverlay(option));
        marker.setTitle(point.name + ":" + point.address);
        marker.setPosition(point.location);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back_near_by:
                finish();
                break;
            case R.id.iv_search:
                /**
                 * if edit text is not visible, set edit text visible
                 */
                if(etSearch.getVisibility() == View.GONE){
                    etSearch.setVisibility(View.VISIBLE);
                }else{
                    String searchContent = etSearch.getText().toString().trim();
                    searchByKeyWords(searchContent);
                }
                break;
        }
    }
}
