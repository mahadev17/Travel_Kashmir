package com.travelkashmir.travelsearch;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.travelkashmir.travelsearch.adapter.ItemListAdapter;
import com.travelkashmir.travelsearch.constant.Constant;
import com.travelkashmir.travelsearch.utils.ImageLoader;
import com.travelkashmir.travelsearch.utils.Utility;

public class DetailsActivity extends Activity implements OnClickListener {
	private Context appContext;
	private Bundle bundle;
	private Button detail_btn,review_btn, shared_btn,
			favourate_btn;
	String hotel_desc, hotel_facility, hotel_id;
	TextView hotel_desc_txt,hotel_email_txt;
	ListView review_list;
	String URLImage, desc,email;
	ImageLoader imageLoader;
	private ArrayList<JSONObject> arrayJsonList;
	private ItemListAdapter itemListAdapter;
	private int requestFor;
	private ProgressDialog progressDialog;
	private Bundle requestBundle;
	private String jsonSubString;
	JSONObject jsonObject;
	JSONArray jsonArray;
	// Categories catObject;
	Bitmap bitmap = null;
	ImageView hotel_image;
	String jsonString;
	String heading;
	String dealTitle;
	View view1;
	String dealId;
	boolean refreshList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.deal_details);

		appContext = this;
		imageLoader=new ImageLoader(appContext);
		bundle = getIntent().getExtras();
		if (bundle != null) {
			jsonString = bundle.getString("Deals");
			heading=bundle.getString("heading");
		}

		arrayJsonList = new ArrayList<JSONObject>();
		Utility.setLoginPreferences(appContext, "ReviewList", false);
		
		TextView heading_txt = (TextView) findViewById(R.id.headng_txt);
		TextView item_heading_txt = (TextView) findViewById(R.id.item_headng_txt);
		hotel_desc_txt = (TextView) findViewById(R.id.hotel_desc_txt);
		hotel_email_txt = (TextView) findViewById(R.id.hotel_email_txt);
		review_list = (ListView) findViewById(R.id.hotel_review_listview);
		view1=findViewById(R.id.view1);
		
		hotel_image = (ImageView) findViewById(R.id.hotel_imageview);
		hotel_image.setBackgroundResource(R.drawable.deals_btn);

		detail_btn = (Button) findViewById(R.id.detail_btn);
		
		review_btn = (Button) findViewById(R.id.review_btn);
		shared_btn = (Button) findViewById(R.id.shared_btn);
		favourate_btn = (Button) findViewById(R.id.favourate_btn);

		detail_btn.setOnClickListener(this);
		review_btn.setOnClickListener(this);
		shared_btn.setOnClickListener(this);
		favourate_btn.setOnClickListener(this);
	
		heading_txt.setTypeface(Typeface.createFromAsset(
				appContext.getAssets(), "GeosansLight.ttf"));
		item_heading_txt.setTypeface(Typeface.createFromAsset(
				appContext.getAssets(), "Sansation_Regular.ttf"));
		hotel_desc_txt.setTypeface(Typeface.createFromAsset(
				appContext.getAssets(), "Sansation_Regular.ttf"));
		
		hotel_email_txt.setTypeface(Typeface.createFromAsset(
				appContext.getAssets(), "Sansation_Regular.ttf"));

		heading_txt.setText(heading);

		try {
			jsonObject = new JSONObject(jsonString);
			dealTitle=jsonObject.getString("deal_title");
			dealId=jsonObject.getString("dealid");
			item_heading_txt.setText(dealTitle);
			desc = jsonObject.getString("deal_desc");
			email = jsonObject.getString("deal_email");
			if(desc.length()>0){
				hotel_desc_txt.setText(desc);
			}else{
				desc="Deal description is not available";
				hotel_desc_txt.setText(desc);
			}
			if(email.length()>0){
				email="Email address:- "+email;
				hotel_email_txt.setText(email);
			}else{
				desc="Deal email is not available";
				hotel_email_txt.setText(email);
			}
			
			URLImage = jsonObject.getString("image_file");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					bitmap = Utility.getBitmap(URLImage);
				} catch (OutOfMemoryError e) {
					e.printStackTrace();
				}
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							hotel_image.setImageBitmap(bitmap);
							bitmap = null;
						} catch (OutOfMemoryError e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		thread.start();

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		if (v.getId() == R.id.detail_btn) {
			hotel_desc_txt.setVisibility(View.VISIBLE);
			hotel_email_txt.setVisibility(View.VISIBLE);
			review_list.setVisibility(View.GONE);
			view1.setVisibility(View.VISIBLE);
			hotel_desc_txt.setText(desc);
			hotel_email_txt.setText(email);
		}else if (v.getId() == R.id.review_btn) {
			hotel_desc_txt.setVisibility(View.GONE);
			view1.setVisibility(View.GONE);
			hotel_email_txt.setVisibility(View.GONE);
			review_list.setVisibility(View.VISIBLE);
			requestFor = Constant.COMMENT;
			if (arrayJsonList.size() == 0) {
				new GetReviewList().execute();
			}

			itemListAdapter = new ItemListAdapter(appContext, requestFor,
					arrayJsonList);
			review_list.setAdapter(itemListAdapter);

		} else if (v.getId() == R.id.shared_btn) {
			
			Intent i=new Intent(appContext,ShareOnFacebook.class);
			i.putExtra("imageUrl", URLImage);
			i.putExtra("hotelTitle",dealTitle);
			i.putExtra("hotelDesc", desc);
			startActivity(i);
		} else if (v.getId() == R.id.favourate_btn) {
			Intent i = new Intent(appContext, WriteDealCommentActivity.class);
			i.putExtra("hotelid", dealId);
			i.putExtra("heading", "Deals");
			startActivity(i);
		}
	}

	public class GetReviewList extends AsyncTask<String, Void, String> {


		String response = null;
		String serverUrl;

		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = ProgressDialog.show(appContext, "",
					"Please wait ! Loading", true, true,
					new OnCancelListener() {

						public void onCancel(DialogInterface dialog) {
							// TODO Auto-generated method stub
							GetReviewList.this.cancel(true);
						}
					});
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub


				serverUrl = Constant.serverUrl + "GetComment&dealid="+dealId;
				jsonSubString = "comments";

			String response = Utility.findJSONFromUrl(serverUrl);

			if (response != null) {

				try {
					arrayJsonList.clear();
					jsonObject = new JSONObject(response);
					jsonArray = jsonObject.getJSONArray("comments");
					System.out.println("Array  " + jsonArray);

					if (jsonArray != null) {
						for (int i = 0; i < jsonArray.length(); i++) {
							jsonObject = jsonArray.getJSONObject(i);
							jsonObject = jsonObject.getJSONObject("Deal");
							arrayJsonList.add(jsonObject);
						}
					}
					jsonArray = null;
					jsonObject = null;
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return response;
			} else {
				return null;
			}

		}

		protected void onPostExecute(String result) {

			if (result == null) {
				progressDialog.dismiss();
				Toast.makeText(appContext, "Please Check Network",
						Toast.LENGTH_SHORT).show();
			} else if (arrayJsonList.size() == 0) {
				progressDialog.dismiss();
				Toast.makeText(appContext, "Data Not Found", Toast.LENGTH_SHORT)
						.show();
			} else {
				itemListAdapter.notifyDataSetChanged();
				progressDialog.dismiss();
			}

		}
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		refreshList=Utility.getLoginPrefernce(appContext, "ReviewList");
		if(refreshList==true){
			hotel_desc_txt.setVisibility(View.GONE);
			hotel_email_txt.setVisibility(View.GONE);
			review_list.setVisibility(View.VISIBLE);
			view1.setVisibility(View.GONE);
			requestFor=Constant.COMMENT;
			new GetReviewList().execute();
			itemListAdapter = new ItemListAdapter(appContext, requestFor,
					arrayJsonList);
			review_list.setAdapter(itemListAdapter);
		}
		
	}
}
