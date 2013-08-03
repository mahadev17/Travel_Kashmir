package com.travelkashmir.travelsearch;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.travelkashmir.travelsearch.constant.Constant;

public class MainHomeActivity extends Activity {
	private Context mContext;
	
	public ImageButton btn_restaurant, btn_deals, btn_travel,
			btn_Feedback, btn_Contact, btn_hotels;
	LocationManager manager;
	String current_lat, current_long;
	/*private LinearLayout horizontalOuterLayout;
	private HorizontalScrollView horizontalScrollview;
	private int scrollMax;
	private int scrollPos = 0;
	private Timer scrollTimer = null;
	private TimerTask scrollerSchedule;
	private int[] imageNameArray = { R.drawable.squid, R.drawable.shutterstock,R.drawable.shutterstocksrinagar,
			R.drawable.yellow_fish, R.drawable.coral_reef, R.drawable.pic_home };*/
	TextView txt_feedback;
	//  	txtCat.setTypeface(Typeface.createFromAsset(mContext.getAssets(),"font/lsansdi.ttf" ));
	
	
	//ScrollView
	private LinearLayout  horizontalOuterLayout;
	private HorizontalScrollView horizontalScrollview;
	private int scrollMax;
	private int scrollPos =	0;
	private TimerTask clickSchedule;
	private TimerTask scrollerSchedule;
	private TimerTask faceAnimationSchedule;
	private Button clickedButton	=	null;
	private Timer scrollTimer		=	null;
	private Timer clickTimer		=	null;
	private Timer faceTimer         =   null;
	private Boolean isFaceDown      =   true;
	private String[] imageNameArray = {"apple", "banana", "grapes", "orange", "strawberry","apple"};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		setContentView(R.layout.favouritedetail);
		setContentView(R.layout.home_view);
		mContext = this;

		btn_deals = (ImageButton) findViewById(R.id.imgBtn_Deals);
		btn_Contact = (ImageButton) findViewById(R.id.imgBtn_contact);
		btn_hotels = (ImageButton) findViewById(R.id.imgBtn_Hotel);
		btn_Feedback = (ImageButton) findViewById(R.id.imgBtn_Feedback);
		btn_restaurant = (ImageButton) findViewById(R.id.imgBtn_restaurant);
		btn_travel = (ImageButton) findViewById(R.id.imgBtn_Iterative);
		TextView tx1 = (TextView) findViewById(R.id.txt1);
		TextView tx0 = (TextView) findViewById(R.id.nav_bar_txt);
		TextView tx2 = (TextView) findViewById(R.id.txt2);
		txt_feedback = (TextView) findViewById(R.id.txt_feedback);
		/*
		 * txt_feedback.setTypeface(Typeface.createFromAsset(mContext.getAssets()
		 * ,"MavenProLight_300_0.otf" ));
		 */
		tx0.setTypeface(Typeface.createFromAsset(mContext.getAssets(),
				"GeosansLight.ttf"));
		tx1.setTypeface(Typeface.createFromAsset(mContext.getAssets(),
				"Sansation_Regular.ttf"));
		tx2.setTypeface(Typeface.createFromAsset(mContext.getAssets(),
				"Sansation_Regular.ttf"));
		// setting a single listener on img buttons
		btn_deals.setOnClickListener(onclickListener);
		btn_Contact.setOnClickListener(onclickListener);
		btn_hotels.setOnClickListener(onclickListener);
		btn_Feedback.setOnClickListener(onclickListener);
		btn_restaurant.setOnClickListener(onclickListener);
		btn_travel.setOnClickListener(onclickListener);
		// getting current lattitude and longitude
		
		// for main page image scrolling
		horizontalScrollview = (HorizontalScrollView) findViewById(R.id.horiztonal_scrollview_id);
		horizontalOuterLayout = (LinearLayout) findViewById(R.id.horiztonal_outer_layout_id);
		
		horizontalScrollview.setHorizontalScrollBarEnabled(false);
		addImagesToView();

		ViewTreeObserver vto = horizontalOuterLayout.getViewTreeObserver();
		vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				horizontalOuterLayout.getViewTreeObserver()
						.removeGlobalOnLayoutListener(this);
				getScrollMaxAmount();
				startAutoScrolling();
			}
		});
	        
	
	} // on create ends

	public void getScrollMaxAmount() {
		int actualWidth = (horizontalOuterLayout.getMeasuredWidth() - 512);
		scrollMax = actualWidth;
	}



	public void moveScrollView() {
		scrollPos = (int) (horizontalScrollview.getScrollX() + 1.0);
		if (scrollPos >= scrollMax) {
			scrollPos = 0;
		}
		horizontalScrollview.scrollTo(scrollPos, 0);

	}
	
	private OnClickListener onclickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int id = v.getId();
			Intent intent;
			switch (id) {
			case R.id.imgBtn_Hotel:
				intent = new Intent(mContext, HomeActivity.class);
				intent.putExtra("requestFor", Constant.HOTEL);
				intent.putExtra("heading", "Hotels");
				startActivity(intent);
				break;
			case R.id.imgBtn_restaurant:
				intent = new Intent(mContext, HomeActivity.class);
				intent.putExtra("requestFor", Constant.RESTAURANT);
				intent.putExtra("heading", "Restaurants");
				startActivity(intent);
				break;
			case R.id.imgBtn_contact:
				
				Intent it = new Intent(mContext,WebActivity.class);
				it.putExtra("url","http://www.travelkashmirnow.com/pages/contact");
				startActivity(it);
				break;
			case R.id.imgBtn_Feedback:

				intent = new Intent(mContext, WebActivity.class);
				intent.putExtra("url","http://travelkashmirnow.com/index.php/pages/Mfeedback");
				startActivity(intent);
				break;
				
			case R.id.imgBtn_Deals:

				intent = new Intent(mContext,DealsActivity.class);
				intent.putExtra("requestFor", Constant.DEALS);
				intent.putExtra("heading","Deals");
				startActivity(intent);
				break;	
				
			case R.id.imgBtn_Iterative:
				
				intent = new Intent(mContext,ItenararyActivity.class);
				intent.putExtra("requestFor", Constant.ITINERARY);
				intent.putExtra("heading","Itineraries");
				startActivity(intent);
				break;

			}
		}
	};
	
	
    
    public void startAutoScrolling(){
		if (scrollTimer == null) {
			scrollTimer					=	new Timer();
			final Runnable Timer_Tick 	= 	new Runnable() {
			    public void run() {
			    	moveScrollView();
			    }
			};
			
			if(scrollerSchedule != null){
				scrollerSchedule.cancel();
				scrollerSchedule = null;
			}
			scrollerSchedule = new TimerTask(){
				@Override
				public void run(){
					runOnUiThread(Timer_Tick);
				}
			};
			
			scrollTimer.schedule(scrollerSchedule, 80, 80);
		}
	}
    

    
    /** Adds the images to view. */
    public void addImagesToView(){
    	int[] imageArray = { R.drawable.squid, R.drawable.shutterstock,R.drawable.shutterstocksrinagar,
				R.drawable.yellow_fish, R.drawable.coral_reef, R.drawable.pic_home };
    	for (int i=0;i<imageNameArray.length;i++){
			final Button imageButton =	new Button(this);
			int imageResourceId		 =	imageArray[i];
			Drawable image 			 =	this.getResources().getDrawable(imageResourceId);
		    imageButton.setBackgroundDrawable(image);
		    imageButton.setTag(i);
		  
			LinearLayout.LayoutParams params 	=	new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT);
			//params.setMargins(0, 25, 0, 25);
			imageButton.setLayoutParams(params);
			horizontalOuterLayout.addView(imageButton);
		}
	}
    
    public Animation scaleFaceUpAnimation(){
		Animation scaleFace = new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
		scaleFace.setDuration(500);
		scaleFace.setFillAfter(true);
		scaleFace.setInterpolator(new AccelerateInterpolator());
		Animation.AnimationListener	scaleFaceAnimationListener = new Animation.AnimationListener() {			
			@Override
			public void onAnimationStart(Animation arg0) {
				isFaceDown = false;
			}			
			@Override
			public void onAnimationRepeat(Animation arg0) {}			
			@Override
			public void onAnimationEnd(Animation arg0) {
				if(faceTimer != null){
					faceTimer.cancel();
					faceTimer = null;
				}
				
				faceTimer = new Timer();
				if(faceAnimationSchedule != null){
					faceAnimationSchedule.cancel();
					faceAnimationSchedule = null;
				}
				faceAnimationSchedule = new TimerTask() {					
					@Override
					public void run() {
						faceScaleHandler.sendEmptyMessage(0); 										
					}
				};
				
				faceTimer.schedule(faceAnimationSchedule, 1000);				
			}
		}; 
		scaleFace.setAnimationListener(scaleFaceAnimationListener);
		return scaleFace;
	}
    
    private Handler faceScaleHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
        	if(clickedButton.isSelected() == true)
        		clickedButton.startAnimation(scaleFaceDownAnimation(3000));		
        }
	};
	
	public Animation scaleFaceDownAnimation(int duration){
		Animation scaleFace = new ScaleAnimation(1.2f, 1.0f, 1.2f, 1.0f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
		scaleFace.setDuration(duration);
		scaleFace.setFillAfter(true);
		scaleFace.setInterpolator(new AccelerateInterpolator());
		Animation.AnimationListener	scaleFaceAnimationListener = new Animation.AnimationListener() {			
			@Override
			public void onAnimationStart(Animation arg0) {}			
			@Override
			public void onAnimationRepeat(Animation arg0) {}			
			@Override
			public void onAnimationEnd(Animation arg0) {
				isFaceDown = true;				
			}
		}; 
		scaleFace.setAnimationListener(scaleFaceAnimationListener);
		return scaleFace;
	}
    
    public void stopAutoScrolling(){
		if (scrollTimer != null) {
			scrollTimer.cancel();
			scrollTimer	=	null;
		}
	}
    
    public void onBackPressed(){
		super.onBackPressed();
		
	}
	
	public void onPause() {
		super.onPause();
		
	}
	
	public void onDestroy(){
		clearTimerTaks(clickSchedule);
		clearTimerTaks(scrollerSchedule);
		clearTimerTaks(faceAnimationSchedule);		
		clearTimers(scrollTimer);
		clearTimers(clickTimer);
		clearTimers(faceTimer);
		
		clickSchedule         = null;
		scrollerSchedule      = null;
		faceAnimationSchedule = null;
		scrollTimer           = null;
		clickTimer            = null;
		faceTimer             = null;
		super.onDestroy();
	}
	
	private void clearTimers(Timer timer){
	    if(timer != null) {
		    timer.cancel();
	        timer = null;
	    }
	}
	
	private void clearTimerTaks(TimerTask timerTask){
		if(timerTask != null) {
			timerTask.cancel();
			timerTask = null;
		}
	}
	

}
