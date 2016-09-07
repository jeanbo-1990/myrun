package com.jeanbo.myrun.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnDrawListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jeanbo.myrun.R;
import com.jeanbo.myrun.view.fragment.BaseFragmentActivity;
import com.jeanbo.myrun.view.fragment.GroupFragment;
import com.jeanbo.myrun.view.fragment.MineFragment;
import com.jeanbo.myrun.view.fragment.MsgFragment;
import com.jeanbo.myrun.view.fragment.RunFragment;
import com.jeanbo.myrun.view.fragment.YdFragemnt;

public class MainActivity extends BaseFragmentActivity {

	int bg_height = 136;
	int bg_width = 720;

	int bg_top_height = 24;

	/**
	 * 下方tab（外层大的）
	 */
	LinearLayout ll_tab;

	/**
	 * 下方开始跑步tab
	 */
	RelativeLayout rl_tab_run;

	/**
	 * 开始跑步
	 */
	ImageView img_tab_run;

	/**
	 * 下方跑步有道
	 */
	LinearLayout ll_tab_yd;
	/**
	 * 跑步有道图标
	 */
	ImageView img_tab_yd;
	/**
	 * 跑步有道文字
	 */
	TextView txt_tab_yd;

	/**
	 * 下方群组
	 */
	LinearLayout ll_tab_gp;

	/**
	 * 群组图标
	 */
	ImageView img_tab_gp;
	/**
	 * 群组文字
	 */
	TextView txt_tab_gp;

	/**
	 * 下方消息
	 */
	LinearLayout ll_tab_msg;

	/**
	 * 消息图标
	 */
	ImageView img_tab_msg;
	/**
	 * 消息文字
	 */
	TextView txt_tab_msg;

	/**
	 * 下方我的
	 */
	LinearLayout ll_tab_mine;

	/**
	 * 我的图标
	 */
	ImageView img_tab_mine;
	/**
	 * 我的文字
	 */
	TextView txt_tab_mine;

	/**
	 * frag
	 */
	private List<Fragment> fragments;

	/**
	 * 当前fragment索引
	 */
	private int currIndex = 2;

	/**
	 * 上一个fragment索引
	 */
	private int preIndex = 2;
	
	private FrameLayout fl_content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		rl_tab_run.performClick();
	}

	private void initView() {
		WindowManager wm = (WindowManager) this
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics dm = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(dm);
		// 动态调整tab背景高度
		ll_tab = (LinearLayout) findViewById(R.id.ll_tab);
		final int bg_rel_height = dm.widthPixels * bg_height / bg_width;
		FrameLayout.LayoutParams ll_tab_parms = new FrameLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, bg_rel_height);
		ll_tab_parms.gravity=Gravity.BOTTOM;
		ll_tab.setLayoutParams(ll_tab_parms);
		

		// 开始跑步，动态调整开始跑步图标居于大背景中间
		rl_tab_run = (RelativeLayout) findViewById(R.id.rl_tab_run);
		final int bg_rel_top_height = bg_rel_height * bg_top_height / bg_height;

		int run_image_margin = (int) ((bg_rel_height - bg_rel_top_height - (28 * dm.density + 0.5f)) / 2 + bg_rel_top_height);
		LinearLayout.LayoutParams rl_tab_run_Params = (android.widget.LinearLayout.LayoutParams) rl_tab_run
				.getLayoutParams();
		rl_tab_run_Params.setMargins(rl_tab_run_Params.leftMargin,
				run_image_margin, rl_tab_run_Params.rightMargin,
				rl_tab_run_Params.bottomMargin);
		rl_tab_run.setLayoutParams(rl_tab_run_Params);
		
		//调整内容页的margin
		fl_content=(FrameLayout) findViewById(R.id.fl_content);
		FrameLayout.LayoutParams lp_content=(LayoutParams) fl_content.getLayoutParams();
		lp_content.setMargins(0, 0, 0, bg_rel_height - bg_rel_top_height);
		fl_content.setLayoutParams(lp_content);
		
		
		
		

		// tab点击切换
		rl_tab_run.setOnClickListener(new TabChangeEvent());
		ll_tab_yd = (LinearLayout) findViewById(R.id.ll_tab_yd);
		ll_tab_gp = (LinearLayout) findViewById(R.id.ll_tab_gp);
		ll_tab_msg = (LinearLayout) findViewById(R.id.ll_tab_msg);
		ll_tab_mine = (LinearLayout) findViewById(R.id.ll_tab_mine);
		
		//调整tab选项的margin
		
		ll_tab_yd.setOnClickListener(new TabChangeEvent());
		ll_tab_yd.getViewTreeObserver().addOnGlobalLayoutListener(new ViewDrawEvent(ll_tab_yd,bg_rel_height,bg_rel_top_height));
		ll_tab_gp.setOnClickListener(new TabChangeEvent());
		ll_tab_gp.getViewTreeObserver().addOnGlobalLayoutListener(new ViewDrawEvent(ll_tab_gp,bg_rel_height,bg_rel_top_height));
		ll_tab_msg.setOnClickListener(new TabChangeEvent());
		ll_tab_msg.getViewTreeObserver().addOnGlobalLayoutListener(new ViewDrawEvent(ll_tab_msg,bg_rel_height,bg_rel_top_height));
		ll_tab_mine.setOnClickListener(new TabChangeEvent());
		ll_tab_mine.getViewTreeObserver().addOnGlobalLayoutListener(new ViewDrawEvent(ll_tab_mine,bg_rel_height,bg_rel_top_height));

		img_tab_run = (ImageView) findViewById(R.id.img_tab_run);
		img_tab_yd = (ImageView) findViewById(R.id.img_tab_yd);
		img_tab_gp = (ImageView) findViewById(R.id.img_tab_gp);
		img_tab_msg = (ImageView) findViewById(R.id.img_tab_msg);
		img_tab_mine = (ImageView) findViewById(R.id.img_tab_mine);

		txt_tab_yd = (TextView) findViewById(R.id.txt_tab_yd);
		txt_tab_gp = (TextView) findViewById(R.id.txt_tab_gp);
		txt_tab_msg = (TextView) findViewById(R.id.txt_tab_msg);
		txt_tab_mine = (TextView) findViewById(R.id.txt_tab_mine);

		fragments = new ArrayList<Fragment>();
		fragments.add(new YdFragemnt());
		fragments.add(new GroupFragment());
		fragments.add(new RunFragment());
		fragments.add(new MsgFragment());
		fragments.add(new MineFragment());
	}
	
	class ViewDrawEvent implements OnGlobalLayoutListener{
		LinearLayout view;
		int bg_rel_height;
		int bg_rel_top_height;
		public ViewDrawEvent(LinearLayout view,int bg_rel_height,int bg_rel_top_height){
			this.view=view;
			this.bg_rel_height=bg_rel_height;
			this.bg_rel_top_height=bg_rel_top_height;
		}

		@Override
		public void onGlobalLayout() {
			// TODO Auto-generated method stub
			view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
			int tab_child_margin=(bg_rel_height - bg_rel_top_height- ll_tab_yd.getHeight())/2;
			if(tab_child_margin>0){
				LinearLayout.LayoutParams lp= (android.widget.LinearLayout.LayoutParams) view.getLayoutParams();
				lp.setMargins(0, tab_child_margin, 0, tab_child_margin);
//				view.setPadding(0, tab_child_margin, 0, tab_child_margin);
			}
		}
		
	}
	

	/**
	 * 清除所有样式
	 */
	private void clearAllType() {
		img_tab_run.setBackgroundResource(R.drawable.run_tab_nor);
		img_tab_yd.setBackgroundResource(R.drawable.yd_tab);
		img_tab_gp.setBackgroundResource(R.drawable.gp_tab);
		img_tab_msg.setBackgroundResource(R.drawable.msg_tab);
		img_tab_mine.setBackgroundResource(R.drawable.mine_tab);

		txt_tab_yd.setTextColor(Color.parseColor("#86837E"));
		txt_tab_gp.setTextColor(Color.parseColor("#86837E"));
		txt_tab_msg.setTextColor(Color.parseColor("#86837E"));
		txt_tab_mine.setTextColor(Color.parseColor("#86837E"));
	}

	class TabChangeEvent implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			clearAllType();
			preIndex = currIndex;
			switch (v.getId()) {
			// 跑步有道
			case R.id.ll_tab_yd:
				currIndex = 0;
				img_tab_yd.setBackgroundResource(R.drawable.yd__tab_press);
				txt_tab_yd.setTextColor(Color.parseColor("#00ADC5"));
				break;
			// 群组
			case R.id.ll_tab_gp:
				currIndex = 1;
				img_tab_gp.setBackgroundResource(R.drawable.gp_tab_press);
				txt_tab_gp.setTextColor(Color.parseColor("#00ADC5"));
				break;
			// 开始跑步
			case R.id.rl_tab_run:
				currIndex = 2;
				img_tab_run.setBackgroundResource(R.drawable.run_tab_press);
				break;
			// 消息
			case R.id.ll_tab_msg:
				currIndex = 3;
				img_tab_msg.setBackgroundResource(R.drawable.msg_tab_press);
				txt_tab_msg.setTextColor(Color.parseColor("#00ADC5"));
				break;
			// 我的
			case R.id.ll_tab_mine:
				currIndex = 4;
				img_tab_mine.setBackgroundResource(R.drawable.mine_tab_press);
				txt_tab_mine.setTextColor(Color.parseColor("#00ADC5"));
				break;
			}

			// 暂停之前的fragment
			fragments.get(preIndex).onPause();
			FragmentTransaction ft = getSupportFragmentManager()
					.beginTransaction();
			if (currIndex > preIndex) {
				ft.setCustomAnimations(R.anim.slide_left_in,
						R.anim.slide_left_out);
			} else {
				ft.setCustomAnimations(R.anim.slide_right_in,
						R.anim.slide_right_out);
			}

			// 当前fragment
			Fragment curFag = fragments.get(currIndex);
			if (curFag.isAdded()) {
				curFag.onResume();
			} else {
				ft.add(R.id.fl_content, curFag);
			}
			for (int i = 0; i < fragments.size(); i++) {
				Fragment fragment = fragments.get(i);
				if (i != currIndex) {
					ft.hide(fragment);
				} else {
					ft.show(fragment);
				}
			}
			ft.commit();
		}
	}
}
