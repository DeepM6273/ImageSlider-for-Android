package com.example.imageslider;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLayoutChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.imageslider.MyScrollView.OnScrollStoppedListener;

public class MainActivity extends Activity {

	private MyScrollView hsv;
	private LinearLayout llParent, llChild;
	private LinearLayout llHsv;
	private Button btnFirst, btnLast;
	private LinearLayout[] llRound;
	private ImageView[] ivRound;
	private int[] arrayDrawavle = {R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher};
	
	public static int RoundImageWidth = 60;
	public static int TotalTeams = 20;
	private static int BIG_IMAGE_SIZE = 80, MEDIUM_IMAGE_SIZE = 50, SMALL_IMAGE_SIZE = 30;
	public static int TeamNo = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		try {
			llParent = (LinearLayout) findViewById(R.id.llParent);
			llChild = (LinearLayout) findViewById(R.id.llChild);
			llHsv = (LinearLayout) findViewById(R.id.llHsv);
			hsv = (MyScrollView) findViewById(R.id.hsv);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		calculateScreenSize();
		
		hsv.setOnTouchListener(new OnTouchListener()
		{
			public boolean onTouch(View v, MotionEvent event)
			{				
				if (event.getAction() == MotionEvent.ACTION_UP)
				{

					hsv.startScrollerTask();
				}

				return false;
			}
		});
		
		hsv.setOnScrollStoppedListener(new OnScrollStoppedListener()
		{

			public void onScrollStopped()
			{
				try {
					TeamNo = ((hsv.getScrollX() + (RoundImageWidth / 2)) / RoundImageWidth) + 1;
						if (TeamNo == TotalTeams)
						{
							ivRound[TotalTeams - 1].setLayoutParams(new LinearLayout.LayoutParams(BIG_IMAGE_SIZE, BIG_IMAGE_SIZE, Gravity.CENTER));
							ivRound[TotalTeams - 2].setLayoutParams(new LinearLayout.LayoutParams(MEDIUM_IMAGE_SIZE, MEDIUM_IMAGE_SIZE, Gravity.CENTER));
							for (int i = 0; i < TotalTeams - 2; i++)
							{
								ivRound[i].setLayoutParams(new LinearLayout.LayoutParams(SMALL_IMAGE_SIZE, SMALL_IMAGE_SIZE, Gravity.CENTER));
							}
						}
						else if (TeamNo == 1)
						{
							ivRound[0].setLayoutParams(new LinearLayout.LayoutParams(BIG_IMAGE_SIZE, BIG_IMAGE_SIZE, Gravity.CENTER));
							ivRound[1].setLayoutParams(new LinearLayout.LayoutParams(MEDIUM_IMAGE_SIZE, MEDIUM_IMAGE_SIZE, Gravity.CENTER));

							for (int i = 2; i < TotalTeams; i++)
							{
								ivRound[i].setLayoutParams(new LinearLayout.LayoutParams(SMALL_IMAGE_SIZE, SMALL_IMAGE_SIZE, Gravity.CENTER));
							}
						}
						else
						{
							for (int i = 0; i < (TeamNo - 2); i++)
							{
								ivRound[i].setLayoutParams(new LinearLayout.LayoutParams(SMALL_IMAGE_SIZE, SMALL_IMAGE_SIZE, Gravity.CENTER));
							}
							ivRound[TeamNo - 2].setLayoutParams(new LinearLayout.LayoutParams(MEDIUM_IMAGE_SIZE, MEDIUM_IMAGE_SIZE, Gravity.CENTER));
							ivRound[TeamNo - 1].setLayoutParams(new LinearLayout.LayoutParams(BIG_IMAGE_SIZE, BIG_IMAGE_SIZE, Gravity.CENTER));
							ivRound[TeamNo].setLayoutParams(new LinearLayout.LayoutParams(MEDIUM_IMAGE_SIZE, MEDIUM_IMAGE_SIZE, Gravity.CENTER));
							for (int i = TeamNo + 1; i < TotalTeams; i++)
							{
								ivRound[i].setLayoutParams(new LinearLayout.LayoutParams(SMALL_IMAGE_SIZE, SMALL_IMAGE_SIZE, Gravity.CENTER));
							}
						} 
						hsv.smoothScrollTo((TeamNo - 1) * RoundImageWidth, (int) hsv.getScrollY());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig)
	{
		super.onConfigurationChanged(newConfig);
		calculateScreenSize();
	}

	private void calculateScreenSize()
	{
		try {
			if (android.os.Build.VERSION.SDK_INT >= 11)
			{
				llParent.addOnLayoutChangeListener(new OnLayoutChangeListener()
				{

					@SuppressLint("NewApi")
					@Override
					public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom)
					{
						afterLayoutLoad();
						llParent.removeOnLayoutChangeListener(this);
					}
				});
			}
			else
			{
				llParent.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener()
				{

					@Override
					public void onGlobalLayout()
					{
						afterLayoutLoad();
						llParent.getViewTreeObserver().removeGlobalOnLayoutListener(this);
					}
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void afterLayoutLoad()
	{
		try {
			llChild.removeAllViews();
			RoundImageWidth = (int) (llHsv.getWidth() / 5);
			
			LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(RoundImageWidth * 2, LinearLayout.LayoutParams.WRAP_CONTENT);
			btnFirst = new Button(MainActivity.this);
			btnFirst.setLayoutParams(params1);
			btnFirst.setBackground(null);
			llChild.addView(btnFirst);
			if (TotalTeams == 0)
			{
				llRound = new LinearLayout[9];
				ivRound = new ImageView[9];
				for (int i = 0; i < 9; i++)
				{
					llRound[i] = new LinearLayout(MainActivity.this);
					llRound[i].setLayoutParams(new LinearLayout.LayoutParams(RoundImageWidth, RoundImageWidth));
					llRound[i].setOrientation(LinearLayout.VERTICAL);
					llRound[i].setGravity(Gravity.CENTER);

					ivRound[i] = new ImageView(MainActivity.this);
					ivRound[i].setLayoutParams(new LinearLayout.LayoutParams(RoundImageWidth, RoundImageWidth, Gravity.CENTER));
//					ivRound[i].setBackgroundResource(arrayDrawavle[i]);
					ivRound[i].setBackground(getResources().getDrawable(arrayDrawavle[i]));

					llRound[i].addView(ivRound[i]);

					llChild.addView(llRound[i]);
				}
			}
			else
			{
				llRound = new LinearLayout[TotalTeams];
				ivRound = new ImageView[TotalTeams];
				for (int i = 0; i < TotalTeams; i++)
				{
					llRound[i] = new LinearLayout(MainActivity.this);
					llRound[i].setLayoutParams(new LinearLayout.LayoutParams(RoundImageWidth, RoundImageWidth));
					llRound[i].setOrientation(LinearLayout.VERTICAL);
					llRound[i].setGravity(Gravity.CENTER);

					ivRound[i] = new ImageView(MainActivity.this);
					ivRound[i].setLayoutParams(new LinearLayout.LayoutParams(RoundImageWidth, RoundImageWidth, Gravity.CENTER));
//					ivRound[i].setBackgroundResource(arrayDrawavle[i]);
					ivRound[i].setBackground(getResources().getDrawable(arrayDrawavle[i]));

					llRound[i].addView(ivRound[i]);

					llChild.addView(llRound[i]);
				}
			}

			btnLast = new Button(MainActivity.this);
			btnLast.setLayoutParams(params1);
			btnLast.setBackgroundDrawable(null);
			llChild.addView(btnLast);
			
			setScrollByLevel(TeamNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setScrollByLevel(final int level)
	{
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				try {
					SetTextSizeBasedonLevel(level);		
					hsv.requestFocus();
					llRound[level-1].performClick();					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, 1500);
	}

	public void SetTextSizeBasedonLevel(final int level)
	{
		try {
			if (level == 1)
			{
				ivRound[0].setLayoutParams(new LinearLayout.LayoutParams(BIG_IMAGE_SIZE, BIG_IMAGE_SIZE, Gravity.CENTER));			
				ivRound[1].setLayoutParams(new LinearLayout.LayoutParams(MEDIUM_IMAGE_SIZE, MEDIUM_IMAGE_SIZE, Gravity.CENTER));			

				for (int i = 2; i < TotalTeams; i++)
				{
					ivRound[i].setLayoutParams(new LinearLayout.LayoutParams(SMALL_IMAGE_SIZE, SMALL_IMAGE_SIZE, Gravity.CENTER));
				}
			}
			else if (level == TotalTeams)
			{
				ivRound[TotalTeams - 1].setLayoutParams(new LinearLayout.LayoutParams(BIG_IMAGE_SIZE, BIG_IMAGE_SIZE, Gravity.CENTER));
				ivRound[TotalTeams - 2].setLayoutParams(new LinearLayout.LayoutParams(MEDIUM_IMAGE_SIZE, MEDIUM_IMAGE_SIZE, Gravity.CENTER));

				for (int i = 0; i < TotalTeams - 2; i++)
				{
					ivRound[i].setLayoutParams(new LinearLayout.LayoutParams(SMALL_IMAGE_SIZE, SMALL_IMAGE_SIZE, Gravity.CENTER));
				}
			}
			else
			{
				for (int i = 2; i < level - 2; i++)
				{
					ivRound[i].setLayoutParams(new LinearLayout.LayoutParams(SMALL_IMAGE_SIZE, SMALL_IMAGE_SIZE, Gravity.CENTER));
				}
				ivRound[level - 2].setLayoutParams(new LinearLayout.LayoutParams(MEDIUM_IMAGE_SIZE, MEDIUM_IMAGE_SIZE, Gravity.CENTER));
				ivRound[level - 1].setLayoutParams(new LinearLayout.LayoutParams(BIG_IMAGE_SIZE, BIG_IMAGE_SIZE, Gravity.CENTER));
				ivRound[level].setLayoutParams(new LinearLayout.LayoutParams(MEDIUM_IMAGE_SIZE, MEDIUM_IMAGE_SIZE, Gravity.CENTER));

				for (int i = level + 1; i < TotalTeams; i++)
				{
					ivRound[i].setLayoutParams(new LinearLayout.LayoutParams(SMALL_IMAGE_SIZE, SMALL_IMAGE_SIZE, Gravity.CENTER));
				}
				if(level == 3)
				{
					ivRound[0].setLayoutParams(new LinearLayout.LayoutParams(SMALL_IMAGE_SIZE, SMALL_IMAGE_SIZE, Gravity.CENTER));
				}
			}
			
//			for (int i = 0; i < TotalTeams; i++)
//			{
//				llRound[i].setTag(i);
//				llRound[i].setOnClickListener(clickListener);
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
