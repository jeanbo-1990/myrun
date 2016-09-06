package com.jeanbo.myrun.util;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

public class ActivityCollection {
	/**
     * 获取集合
     */
    public static List<Activity> activities = new ArrayList<Activity>();
    
    /**
     * @Title: addActivity
     * @Description: 加入一个活动
     * @param @param activity 设定文件
     * @return void 返回类型
     * @throws
     */
    public static void addActivity(Activity activity)
    {
        activities.add(activity);
    }
    
    /**
     * @Title: removeActivity
     * @Description: 移除一个活动
     * @param @param activity 设定文件
     * @return void 返回类型
     * @throws
     */
    public static void removeActivity(Activity activity)
    {
        activities.remove(activity);
    }
    
    /**
     * @Title: finishAll
     * @Description:结束所有的活动
     * @param 设定文件
     * @return void 返回类型
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
