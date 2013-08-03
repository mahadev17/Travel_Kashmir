package com.travelkashmir.travelsearch;
/*

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;

public class ShareOnFacebook extends Activity {

	private static final String APP_ID = "280302138769470";
	private static final String[] PERMISSIONS = new String[] { "publish_stream" };
	String response;
	Context appContext;

	private static final String TOKEN = "access_token";
	private static final String EXPIRES = "expires_in";
	private static final String KEY = "facebook-credentials";

	private Facebook facebook;
	private String messageToPost;
	//private ShowDetailsActivity sd;
	//private PostRewards sr;
	
	public boolean saveCredentials(Facebook facebook) {
		Editor editor = this.getSharedPreferences(KEY, Context.MODE_PRIVATE)
				.edit();
		editor.putString(TOKEN, facebook.getAccessToken());
		editor.putLong(EXPIRES, facebook.getAccessExpires());
		return editor.commit();
	}

	public boolean restoreCredentials(Facebook facebook) {
		SharedPreferences facebookSession = this.getPreferences(MODE_PRIVATE); // this.getSharedPreferences(KEY,
																				// Context.MODE_PRIVATE);
		facebook.setAccessToken(facebookSession.getString(TOKEN, null));
		facebook.setAccessExpires(facebookSession.getLong(EXPIRES, 0));
		return facebook.isSessionValid();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		facebook = new Facebook(APP_ID);
		restoreCredentials(facebook);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.facebook_dialog);

		appContext = this;
		String facebookMessage = getIntent().getStringExtra("facebookMessage");
		byte[] videoShare=getIntent().getByteArrayExtra("Video");
		byte[] imageShare=getIntent().getByteArrayExtra("Image");
		if (facebookMessage == null) {
			facebookMessage = "To friends and family My film has been nominated to be screened for this years selection with Nexgen Mobile Film Festival 2013 and would like to envite you to participate in on the screening and voting of my film, to do so please download app and join in on the festival..";
		}
		messageToPost = facebookMessage;
		
		
		
	}

	public void doNotShare(View button) {
		finish();
	}

	public void share(View button) {

		try {
			if (!facebook.isSessionValid()) {
				loginAndPostToWall();
			} else {
				postToWall(messageToPost);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public void loginAndPostToWall() {
		// facebook.authorize(this, PERMISSIONS, Facebook.FORCE_DIALOG_AUTH, new
		// LoginDialogListener());
		facebook.authorize(this, PERMISSIONS, new LoginDialogListener());
	}

	@SuppressWarnings("deprecation")
	public void postToWall(String message) {

		new PostStatus(message).execute();
		finish();
		final Bundle parameters = new Bundle();
		parameters.putString("message", message);
		
		parameters.putString("description", "topic share");
		Thread postStatusThread = new Thread(new Runnable() {

			public void run() {
				// TODO Auto-generated method stub

				try {

					facebook.request("me");

					response = facebook.request("me/feed", parameters, "POST");
					Log.d("Tests", "got response: " + response);
					finish();

				} catch (Exception e) {
					response = "1";
					e.printStackTrace();
					finish();
				}
			}
		});
		
		 * if (response == null || response.equals("") ||
		 * response.equals("false")) {
		 * System.out.println("response is : "+response);
		 * showToast("Blank response."); } else if (response == "1") {
		 * System.out.println("response is : "+response);
		 * showToast("Failed to post to wall"); } else {
		 * System.out.println("response is : "+response);
		 * showToast("Message posted to your facebook wall!"); }
		 
		// postStatusThread.start();

	}

	class LoginDialogListener implements DialogListener {
		public void onComplete(Bundle values) {
			saveCredentials(facebook);
			//if (messageToPost != null && videoToPost!=null && imageToPost!=null) {
				postToWall(messageToPost);
			//}
		}

		public void onFacebookError(FacebookError error) {
			showToast("Authentication with Facebook failed!");
			finish();
		}

		public void onError(DialogError error) {
			showToast("Authentication with Facebook failed!");
			finish();
		}

		public void onCancel() {
			showToast("Authentication with Facebook cancelled!");
			finish();
		}
	}

	private void showToast(String message) {
		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT)
				.show();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		facebook.authorizeCallback(requestCode, resultCode, data);
	}

	public class PostStatus extends AsyncTask<Void, Void, String> {
		String message;

		public PostStatus(String msg) {
			message = msg;
		}

		public void onPrexecute() {
			super.onPreExecute();

		}

		@Override
		protected String doInBackground(Void... arg0) {
			// TODO Auto-generated method stub

			final Bundle parameters = new Bundle();
			parameters.putString("message", message);
//			parameters.putString("message", "");
			parameters.putString("description", "topic share");
			//parameters.putByteArray("video", data);

			try {

				facebook.request("me");

				response = facebook.request("me/feed", parameters, "POST");
				
				Log.d("Tests", "got response: " + response);
			} catch (Exception e) {
				response = "1";
				e.printStackTrace();
			}
			return response;
		}

		public void onPostExecute(String response) {

			if (response == null || response.equals("")
					|| response.equals("false")) {
				System.out.println("response is : " + response);
				showToast("Blank response.");
			} else if (response == "1") {
				System.out.println("response is : " + response);
				showToast("Failed to post to wall");
			} else {
				System.out.println("response is : " + response);
				
			//	sr.execute();
				showToast("Message posted to your facebook wall!");
			}

		}

	}
}


*/


import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Toast;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.travelkashmir.travelsearch.utils.Utility;

public class ShareOnFacebook extends Activity {

	private static final String App_ID = "557115797665123";
	private static final String[] PERMISSIONS = new String[] { "publish_stream","offline_access" };
	private static final String TOKEN = "access_token";
	private static final String EXPIRES = "expires_in";
	private static final String KEY = "facebook-credentials";
	private Facebook facebook;
	private String messageToPost;
	private String response;
	private Context appContext;
	private byte[] image;
	String imageP;
	Bitmap bitmap;
	String hotelDesc ;
	String facebookMessage ;
	byte[] imageShare;
	public boolean saveCredentials(Facebook facebook) {
		Editor editor = this.getSharedPreferences(KEY, Context.MODE_PRIVATE)
				.edit();
		editor.putString(TOKEN, facebook.getAccessToken());
		editor.putLong(EXPIRES, facebook.getAccessExpires());
		return editor.commit();
	}

	public boolean restoreCredentials(Facebook facebook) {
		SharedPreferences facebookSession = this.getPreferences(MODE_PRIVATE);
		facebook.setAccessToken(facebookSession.getString(TOKEN, null));
		facebook.setAccessExpires(facebookSession.getLong(EXPIRES, 0));
		return facebook.isSessionValid();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		facebook = new Facebook(App_ID);
		restoreCredentials(facebook);

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.facebook_dialog);

		appContext = this;
		 facebookMessage = getIntent().getStringExtra("hotelTitle");
		 hotelDesc = getIntent().getStringExtra("hotelDesc");
		 imageShare = getIntent().getByteArrayExtra("Image");
		if (facebookMessage == null) {
			facebookMessage = "Hotel Name Not Available ";
		}
	
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					
					
					if (imageShare == null) {
						 imageP=getIntent().getStringExtra("imageUrl");
						//Bitmap bmp = Utility.getBitmap(imageP);
						 bitmap = Utility.getBitmap(imageP);
						Uri myUri = saveToSd(bitmap);
						try {
							imageShare = Utility.scaleImage(appContext, myUri);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					image = imageShare;
					messageToPost = facebookMessage+"\n\n\n"+hotelDesc;
					
				} catch (OutOfMemoryError e) {
					e.printStackTrace();
				}
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							//hotel_image.setImageBitmap(bitmap);
						} catch (OutOfMemoryError e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		thread.start();

		findViewById(R.id.FacebookShareNotButton).setOnClickListener(No);
		findViewById(R.id.FacebookShareButton).setOnClickListener(Yes);

		
		
		

		
	}

	void AnchorText() {

	}

	OnClickListener No = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			finish();
		}
	};
	OnClickListener Yes = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			try {
				if (!facebook.isSessionValid()) {
					loginAndPostToWall();
				} else {
					postToWall(messageToPost, image);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	};

	public void loginAndPostToWall() {
		facebook.authorize(this, PERMISSIONS, new LoginDialogListener());
	}

	@SuppressWarnings("deprecation")
	public void postToWall(String message, byte[] image2) {

		new PostStatus(message, image2).execute();
		finish();
		final Bundle parameters = new Bundle();
		parameters.putString("message", message);
		parameters.putString("description", "topic share");
		parameters.putByteArray("Image", image2);
		Thread postStatusThread = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				try {

					facebook.request("me");
					response = facebook
							.request("me/photos", parameters, "POST");
					Log.d("Tests", "got response: " + response);
					finish();

				} catch (Exception e) {
					response = "1";
					e.printStackTrace();
					finish();
				}
			}
		});
	}

	class LoginDialogListener implements DialogListener {
		@Override
		public void onComplete(Bundle values) {
			saveCredentials(facebook);
			postToWall(messageToPost, image);
		}

		@Override
		public void onFacebookError(FacebookError error) {
			showToast("Authentication with Facebook failed!");
			finish();
		}

		@Override
		public void onError(DialogError error) {
			showToast("Authentication with Facebook failed!");
			finish();
		}

		@Override
		public void onCancel() {
			showToast("Authentication with Facebook cancelled!");
			finish();
		}
	}

	private void showToast(String message) {
		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT)
				.show();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		facebook.authorizeCallback(requestCode, resultCode, data);
	}

	public Bitmap StringToBitMap(String url1) {
		String url = url1;
		Bitmap mIcon11 = null;
		try {
			
			InputStream in = new java.net.URL(url).openStream();
			mIcon11 = BitmapFactory.decodeStream(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mIcon11;

	}

	Uri pngUri;

	private Uri saveToSd(Bitmap bm) {

		if (bm != null) {
			String filename = "ads.png";
			File sd = Environment.getExternalStorageDirectory();
			Log.d("pathsd", " " + sd.getAbsolutePath().toString());
			File dest = new File(sd, filename);
			Log.d("pathdest", " " + dest.getAbsolutePath().toString());
			try {

				FileOutputStream out = new FileOutputStream(dest);
				bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
				out.flush();
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			String path = Environment.getExternalStorageDirectory().toString();
			File file = new File(path, "ads.png");
			pngUri = Uri.fromFile(file);
			return pngUri;
		}
		return null;

	}

	public class PostStatus extends AsyncTask<Void, Void, String> {
		String message;
		byte[] img;

		public PostStatus(String msg, byte[] img2) {
			message = msg;
			img = img2;
		}

		public void onPrexecute() {
			super.onPreExecute();

		}

		@Override
		protected String doInBackground(Void... arg0) {
			// TODO Auto-generated method stub

			final Bundle parameters = new Bundle();
			parameters.putByteArray("photo", img);
			parameters.putString("message", message);
			try {
				facebook.request("me");
				response = facebook.request("me/photos", parameters, "POST");
				Log.d("Tests", "got response: " + response);
			} catch (Exception e) {
				response = "1";
				e.printStackTrace();
			}
			return response;
		}

		@Override
		public void onPostExecute(String response) {
			if (response == null || response.equals("")
					|| response.equals("false")) {
				System.out.println("response is : " + response);
				showToast("Blank response.");
			} else if (response == "1") {
				System.out.println("response is : " + response);
				showToast("Failed to post to wall");
			} else {
				System.out.println("response is : " + response);
				showToast("Message posted to your facebook wall!");
			}

		}

	}

	
	
	
	
	
}