package com.jeanbo.myrun.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ZoomControls;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapStatusChangeListener;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.jeanbo.myrun.R;

public class RunFragment extends Fragment {
	private View view;
	MapView mMapView = null;
	BaiduMap mBaiduMap;
	boolean isFirstLoc = true; // 是否首次定位
	ImageButton ib_location;//手动定位

	public LocationClient mLocationClient = null;
	public BDLocationListener myListener = new MyLocationListener();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.activity_run, container, false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		// 获取地图控件引用
		mMapView = (MapView) view.findViewById(R.id.bmapView);
		ib_location=(ImageButton) view.findViewById(R.id.ib_location);
		ib_location.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				isFirstLoc=true;
				mLocationClient.start();
			}
		});
		// 隐藏logo
		View child = mMapView.getChildAt(1);
		if (child != null
				&& (child instanceof ImageView || child instanceof ZoomControls)) {
			child.setVisibility(View.INVISIBLE);
		}
		// 隐藏地图上比例尺
		mMapView.showScaleControl(false);
		mBaiduMap = mMapView.getMap();
		// 开启定位图层
		mBaiduMap.setMyLocationEnabled(true);
		mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
				MyLocationConfiguration.LocationMode.NORMAL, true, null));
		mMapView.showZoomControls(true);
		mBaiduMap.setMapStatus(MapStatusUpdateFactory
				.newMapStatus(new MapStatus.Builder().zoom(
						mBaiduMap.getMapStatus().zoom - 1).build()));
		initLocation();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		// 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
		if (mMapView != null)
			mMapView.onDestroy();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
		if (mMapView != null)
			mMapView.onResume();
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		// 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
		if (mMapView != null)
			mMapView.onPause();
	}

	private void initLocation() {
		mLocationClient = new LocationClient(getActivity());
		mLocationClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true); // 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000);
		mLocationClient.setLocOption(option);
		mLocationClient.start();
	}

	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null || mMapView == null) {
				return;
			}
			MyLocationData locData = new MyLocationData.Builder()
					.accuracy(0)
					// 此处设置开发者获取到的方向信息，顺时针0-360
					.direction(location.getDirection())
					.latitude(location.getLatitude())
					.longitude(location.getLongitude()).build();
			mBaiduMap.setMyLocationData(locData);
			if (isFirstLoc) {
				
				LatLng ll = new LatLng(location.getLatitude(),
						location.getLongitude());
				MapStatus.Builder builder = new MapStatus.Builder();
				builder.target(ll).zoom(18.0f);
				mBaiduMap.animateMapStatus(MapStatusUpdateFactory
						.newMapStatus(builder.build()));
				
				mBaiduMap.setOnMapStatusChangeListener(new OnMapStatusChangeListener() {
					
					@Override
					public void onMapStatusChangeStart(MapStatus arg0) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onMapStatusChangeFinish(MapStatus arg0) {
						// TODO Auto-generated method stub
						if(ib_location.getVisibility()==View.GONE){
							ib_location.setVisibility(View.VISIBLE);
						}
						if(isFirstLoc){
							ib_location.setVisibility(View.GONE);
						}
						isFirstLoc = false;
					}
					
					@Override
					public void onMapStatusChange(MapStatus arg0) {
						// TODO Auto-generated method stub
					}
				});
			}
		}
	}
}
