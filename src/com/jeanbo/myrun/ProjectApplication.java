package com.jeanbo.myrun;

import com.baidu.mapapi.SDKInitializer;

import android.app.Application;

public class ProjectApplication extends Application {
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		//��ʹ��SDK�����֮ǰ��ʼ��context��Ϣ������ApplicationContext  
        SDKInitializer.initialize(getApplicationContext());  
	}
}
