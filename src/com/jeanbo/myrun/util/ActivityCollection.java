package com.jeanbo.myrun.util;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

public class ActivityCollection {
	/**
     * ��ȡ����
     */
    public static List<Activity> activities = new ArrayList<Activity>();
    
    /**
     * @Title: addActivity
     * @Description: ����һ���
     * @param @param activity �趨�ļ�
     * @return void ��������
     * @throws
     */
    public static void addActivity(Activity activity)
    {
        activities.add(activity);
    }
    
    /**
     * @Title: removeActivity
     * @Description: �Ƴ�һ���
     * @param @param activity �趨�ļ�
     * @return void ��������
     * @throws
     */
    public static void removeActivity(Activity activity)
    {
        activities.remove(activity);
    }
    
    /**
     * @Title: finishAll
     * @Description:�������еĻ
     * @param �趨�ļ�
     * @return void ��������
     * @throws
     */
    public static void finishAll()
    {
        for (Activity activity : activities)
        {
            if (!activity.isFinishing())
            {
                activity.finish();
            }
        }
    }
}
