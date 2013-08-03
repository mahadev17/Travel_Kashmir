package com.travelkashmir.travelsearch;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class SplashScreen  extends Activity{
	protected boolean _active = true, isLogin;
	protected int _splashTime = 2000; // Splash screen time
	Context applicationContex;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash_screen);

		applicationContex = this;

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		Thread splashTread = new Thread() {
			@Override
			public void run() {
				try {
					int waited = 0;
					while (_active && (waited < _splashTime)) {
						sleep(100);
						if (_active) {
							waited += 100;
					//		crateSightings();

						}
					}
				} catch (InterruptedException e) {
					// do nothing
				} finally {

				}
				runOnUiThread(endSplashThread);
			}
		};
		splashTread.start();
	
	}// on creates ends here 
	private Runnable endSplashThread = new Runnable() {
		public void run() {
			finish();
		
			Intent intent = new Intent(applicationContex, MainHomeActivity.class);
			overridePendingTransition(R.anim.slide_in_right,
					R.anim.slide_out_left);
			startActivity(intent);
			
		}
	};

} // class endshere 
