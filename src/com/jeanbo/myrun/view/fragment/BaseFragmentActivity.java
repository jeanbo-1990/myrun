package com.jeanbo.myrun.view.fragment;


import com.jeanbo.myrun.util.ActivityCollection;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class BaseFragmentActivity extends FragmentActivity {
	 @Override
	    protected void onCreate(Bundle arg0)
	    {
	        super.onCreate(arg0);
	        ActivityCollection.addActivity(this);
	    }
	    @Override
	    protected void onDestroy()
	    {
	        super.onDestroy();
	        ActivityCollection.removeActivity(this);
	    }
	    
	    @Override
	    public void onBackPressed()
	    {
	        super.onBackPressed();
	        finish();
	    }
}
