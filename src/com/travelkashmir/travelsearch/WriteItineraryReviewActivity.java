package com.travelkashmir.travelsearch;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.Facebook.DialogListener;
import com.travelkashmir.travelsearch.constant.Constant;
import com.travelkashmir.travelsearch.utils.Utility;

public class WriteItineraryReviewActivity  extends Activity{
	Button btn_review;
	String key_login="isLogin";
	EditText edt_title, edt_text;
	String  ratingCount="0";
	boolean isLogin=false;
	ImageButton img_rating1,img_rating2,img_rating3,img_rating4,img_rating5;
	String userId="0";
	String hotel_id;
	Bundle bundle;
	String title , comment;
	ProgressDialog applicationDialog;
	TextView txt_heading;
	String heading;
	Context appContext;
	private int count;
	//facebook login                485559964831759
	private static String APP_ID = "557115797665123"; // Replace with your App ID

	// Instance of Facebook Class
	private Facebook facebook = new Facebook(APP_ID);
	private AsyncFacebookRunner mAsyncRunner;
	String FILENAME = "AndroidSSO_data";
	private SharedPreferences mPrefs;
	private String first_name,last_name;
	private String email;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		setContentView(R.layout.add_review_layout);
		appContext=this;
		bundle =getIntent().getExtras();
		if(bundle!=null)
		{
			hotel_id =bundle.getString("hotelid");
			heading = bundle.getString("heading");
		}
		btn_review=(Button)findViewById(R.id.btn_writeReview_Rvw);
		edt_title=(EditText)findViewById(R.id.edt_writeReview_title);
		edt_text=(EditText)findViewById(R.id.edt_writeReview_review);
		img_rating1=(ImageButton)findViewById(R.id.ratingbar1);
		img_rating2=(ImageButton)findViewById(R.id.ratingbar2);
		img_rating3=(ImageButton)findViewById(R.id.ratingbar3);
		img_rating4=(ImageButton)findViewById(R.id.ratingbar4);
		img_rating5=(ImageButton)findViewById(R.id.ratingbar5);
		txt_heading=(TextView)findViewById(R.id.txt_writeReview_heading);
	
		img_rating1.setOnClickListener(onmyListener);
		img_rating2.setOnClickListener(onmyListener);
		img_rating3.setOnClickListener(onmyListener);
		img_rating4.setOnClickListener(onmyListener);
		img_rating5.setOnClickListener(onmyListener);
		
		btn_review.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				isLogin = Utility.getLoginPrefernce(appContext,
						key_login);
				userId = Utility.getUserIdPrefernce(appContext,
						"userId");
				
			title = edt_title.getText().toString();
			comment =edt_text.getText().toString();
			count=Integer.parseInt(ratingCount);
			
			if(title.length()==0){
				Utility.showAlert(appContext, "Alert",
						"Title can't be blank");
			}else if(comment.length()<=40){
				Utility.showAlert(appContext, "Alert",
						"Review should be greater than 40 characters");
			}else if(count==0){
				Utility.showAlert(appContext, "Alert",
						"Please Select rating");
			}else{
				loginToFacebook();
				mAsyncRunner = new AsyncFacebookRunner(facebook);
			}
			}
		});
		txt_heading.setText(heading);
		txt_heading.setTypeface(Typeface.createFromAsset(appContext.getAssets(),"GeosansLight.ttf"));
		//txt_heading.setTypeface(null,Typeface.BOLD);
	}// on creates ends here 

	public OnClickListener onmyListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int id = v.getId();
			switch (id) {
			case R.id.ratingbar1:
				img_rating1.setImageResource(R.drawable.star_clicked);
				img_rating2.setImageResource(R.drawable.star_unclicked);
				img_rating3.setImageResource(R.drawable.star_unclicked);
				img_rating4.setImageResource(R.drawable.star_unclicked);
				img_rating5.setImageResource(R.drawable.star_unclicked);
				ratingCount = "1";
				break;
			case R.id.ratingbar2:
				img_rating1.setImageResource(R.drawable.star_clicked);
				img_rating2.setImageResource(R.drawable.star_clicked);
				img_rating3.setImageResource(R.drawable.star_unclicked);
				img_rating4.setImageResource(R.drawable.star_unclicked);
				img_rating5.setImageResource(R.drawable.star_unclicked);
				ratingCount = "2";
				break;
			case R.id.ratingbar3:
				img_rating1.setImageResource(R.drawable.star_clicked);
				img_rating2.setImageResource(R.drawable.star_clicked);
				img_rating3.setImageResource(R.drawable.star_clicked);
				img_rating4.setImageResource(R.drawable.star_unclicked);
				img_rating5.setImageResource(R.drawable.star_unclicked);
				ratingCount = "3";
				break;
			case R.id.ratingbar4:
				img_rating1.setImageResource(R.drawable.star_clicked);
				img_rating2.setImageResource(R.drawable.star_clicked);
				img_rating3.setImageResource(R.drawable.star_clicked);
				img_rating4.setImageResource(R.drawable.star_clicked);
				img_rating4.setImageResource(R.drawable.star_clicked);
				img_rating5.setImageResource(R.drawable.star_unclicked);
				ratingCount = "4";
				break;
			case R.id.ratingbar5:
				img_rating1.setImageResource(R.drawable.star_clicked);
				img_rating2.setImageResource(R.drawable.star_clicked);
				img_rating3.setImageResource(R.drawable.star_clicked);
				img_rating4.setImageResource(R.drawable.star_clicked);
				img_rating5.setImageResource(R.drawable.star_clicked);
				ratingCount = "5";
				break;

			}
		}
	};
	
	public class PostReview extends AsyncTask<Void, Void, String> {
		String serverUrl;

		protected void onPreExecute() {
			super.onPreExecute();
			applicationDialog = ProgressDialog.show(appContext, "",
					"Please Wait...");
			applicationDialog.setCancelable(true);

		}

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			serverUrl = Constant.serverUrl+ "AddIteneries_Review";
			ArrayList<NameValuePair> listParams = new ArrayList<NameValuePair>();
			listParams.add(new BasicNameValuePair("user_id",userId));
			listParams.add(new BasicNameValuePair("place_id",hotel_id));
			listParams.add(new BasicNameValuePair("review_title",title));
			listParams.add(new BasicNameValuePair("rating", ratingCount));
			listParams.add(new BasicNameValuePair("review_text",comment));
			
			String result = Utility.postParamsAndfindJSON(serverUrl, listParams);

			return result;
		}

		protected void onPostExecute(String result) {
			if (result != null) {
				applicationDialog.cancel();
				try {
					if (result.contains("1")) {
						/*Utility.showAlert(appContext, "",
								"Succesfully Posted Comment");*/
						Toast.makeText(appContext, "Your review succesfully Posted.", Toast.LENGTH_SHORT).show();
						
						edt_text.setText("");
						edt_title.setText("");
						img_rating1.setImageResource(R.drawable.star_unclicked);
						img_rating2.setImageResource(R.drawable.star_unclicked);
						img_rating3.setImageResource(R.drawable.star_unclicked);
						img_rating4.setImageResource(R.drawable.star_unclicked);
						img_rating5.setImageResource(R.drawable.star_unclicked);
						Utility.setLoginPreferences(appContext, "ReviewList", true);
						finish();
						
						
					} else {
						Utility.showAlert(appContext, "Failed",
								"Unsuccesful ! please try again");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				applicationDialog.cancel();
				Utility.showAlert(appContext, "Failure",
						"Please Check your Internet Connection");
			}

		}
	}// asyntask class ends here
	
	
	public class AddUserInfo extends AsyncTask<Void, Void, String> {
		String serverUrl;

		protected void onPreExecute() {
			super.onPreExecute();
			applicationDialog = ProgressDialog.show(appContext, "",
					"Please Wait  ");
			applicationDialog.setCancelable(true);

		}

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			serverUrl = Constant.serverUrl+ "SocialSignIn";
			ArrayList<NameValuePair> listParams = new ArrayList<NameValuePair>();
			
			listParams.add(new BasicNameValuePair("first_name",first_name ));
			listParams.add(new BasicNameValuePair("last_name",last_name));
			listParams.add(new BasicNameValuePair("username",email));
			
			
			String result = Utility.postParamsAndfindJSON(serverUrl, listParams);

			return result;
		}

		protected void onPostExecute(String result) {
			if (result != null) {
				
				System.out.println("This is Result  "+result);
				Utility.setUserIdPrefernce(appContext, "userId", result);
				applicationDialog.cancel();
				try {
					if (result.equals("0")) {
						Utility.showAlert(appContext, "Failed",
								"Unsuccesful ! please try again");
						
					} else {
						userId=result;
						System.out.println("response resulte is  "+result);
						new PostReview().execute();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				applicationDialog.cancel();
				Utility.showAlert(appContext, "Failure",
						"Please Check your Internet Connection");
			}

		}
	}// asyntask class ends here
	
	public void loginToFacebook() {

		mPrefs = getPreferences(MODE_PRIVATE);
		String access_token = mPrefs.getString("access_token", null);
		long expires = mPrefs.getLong("access_expires", 0);

		if (access_token != null) {
			facebook.setAccessToken(access_token);
			


			Log.d("FB Sessions", "" + facebook.isSessionValid());
		}

		if (expires != 0) {
			facebook.setAccessExpires(expires);
		}

		if (!facebook.isSessionValid()) {
			facebook.authorize(this,
					new String[] { "email", "publish_stream","offline_access"},
					new DialogListener() {

						@Override
						public void onCancel() {
							// Function to handle cancel event
						}

						@Override
						public void onComplete(Bundle values) {
							// Function to handle complete event
							// Edit Preferences and update facebook acess_token
							SharedPreferences.Editor editor = mPrefs.edit();
							editor.putString("access_token",
									facebook.getAccessToken());
							editor.putLong("access_expires",
									facebook.getAccessExpires());
							editor.commit();
											
							getProfileInformation();
							
							/*Intent i=new Intent(AndroidFacebookConnectActivity.this,HomeActivity.class);
							i.putExtra("name",username);
							startActivity(i);*/
						}

						@Override
						public void onError(DialogError error) {
							// Function to handle error

						}

						@Override
						public void onFacebookError(FacebookError fberror) {
							// Function to handle Facebook errors

						}

					});
		}else{

			if (title.trim().length() > 0 && comment.trim().length() > 5
					&& count > 0) {
				new PostReview().execute();
			} else {
				Utility.showAlert(appContext, "Do not Left Blank",
						"Review should atleast 40 characters long");
			}
			 
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		facebook.authorizeCallback(requestCode, resultCode, data);
		
		
	}


	/**
	 * Get Profile information by making request to Facebook Graph API
	 * */
	public void getProfileInformation() {
		mAsyncRunner.request("me", new RequestListener() {
			@Override
			public void onComplete(String response, Object state) {
				Log.d("Profile", response);
				String json = response;
				try {
					// Facebook Profile JSON data
					JSONObject profile = new JSONObject(json);
					
					System.out.println("Profile JsonObject  "+profile);
					
										
					// getting name of the user
					first_name = profile.getString("first_name");
					last_name = profile.getString("last_name");
					email= profile.getString("email");
					Utility.setUserIdPrefernce(getApplicationContext(), "first_name", first_name);
					Utility.setUserIdPrefernce(getApplicationContext(), "last_name", last_name);
					Utility.setUserIdPrefernce(getApplicationContext(), "email", email);
					// getting email of the user
					
					
					
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							
							Toast.makeText(appContext, "Process Completed  "+first_name, Toast.LENGTH_SHORT).show();
							System.out.println("Process Completed  "+first_name);
							isLogin=true;
							Utility.setLoginPreferences(getApplicationContext(), key_login, isLogin);
							new AddUserInfo().execute();
						}

					});

					
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onIOException(IOException e, Object state) {
			}

			@Override
			public void onFileNotFoundException(FileNotFoundException e,
					Object state) {
			}

			@Override
			public void onMalformedURLException(MalformedURLException e,
					Object state) {
			}

			@Override
			public void onFacebookError(FacebookError e, Object state) {
			}
		});
	}

		/**
	 * Function to show Access Tokens
	 * */
	public void showAccessTokens() {
		String access_token = facebook.getAccessToken();

		Toast.makeText(getApplicationContext(),
				"Access Token: " + access_token, Toast.LENGTH_LONG).show();
	}
	
	/**
	 * Function to Logout user from Facebook
	 * */
	public void logoutFromFacebook() {
		mAsyncRunner.logout(this, new RequestListener() {
			@Override
			public void onComplete(String response, Object state) {
				Log.d("Logout from Facebook", response);
				if (Boolean.parseBoolean(response) == true) {
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							finish();
						}

					});

				}
			}

			@Override
			public void onIOException(IOException e, Object state) {
			}

			@Override
			public void onFileNotFoundException(FileNotFoundException e,
					Object state) {
			}

			@Override
			public void onMalformedURLException(MalformedURLException e,
					Object state) {
			}

			@Override
			public void onFacebookError(FacebookError e, Object state) {
			}
		});
	}
	
}// class ends here 
