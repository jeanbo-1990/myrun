package com.jeanbo.myrun;

import com.baidu.mapapi.SDKInitializer;

import android.app.Application;

public class ProjectApplication extends Application {
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		//在使用SDK各组件之前初始化context信息，传入ApplicationContext  
        SDKInitializer.initialize(getApplicationContext());  
	}
}
