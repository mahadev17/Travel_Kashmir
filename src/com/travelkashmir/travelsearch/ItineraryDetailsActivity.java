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
import android.graphics.Color;
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

public class ItineraryDetailsActivity extends Activity implements
		OnClickListener {
	private Context appContext;
	private Bundle bundle;
	private Button detail_btn, facility_btn, review_btn, shared_btn,
			favourate_btn;
	String hotel_desc, hotel_facility, hotel_id;
	TextView hotel_desc_txt,hotel_address_txt,hotel_contact_txt,hotel_email_txt;
	ListView review_list;
	String URLImage, desc,address,email,phone,facility;
	String itinararyId;
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
	View view1,view2,view3;
	Typeface tf;
	String itineraryTitle;
	boolean refreshList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.hotel_detail);

		
		
		appContext = this;
		imageLoader = new ImageLoader(appContext);
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
		hotel_address_txt = (TextView) findViewById(R.id.hotel_address_txt);
		hotel_contact_txt=(TextView) findViewById(R.id.hotel_contact_txt);
		hotel_email_txt=(TextView) findViewById(R.id.hotel_email_txt);
		review_list = (ListView) findViewById(R.id.hotel_review_listview);
		view1=findViewById(R.id.view1);
		view2=findViewById(R.id.view2);
		view3=findViewById(R.id.view3);
	
		hotel_image = (ImageView) findViewById(R.id.hotel_imageview);
		hotel_image.setBackgroundResource(R.drawable.itineriris_btn);
		detail_btn = (Button) findViewById(R.id.detail_btn);
		facility_btn = (Button) findViewById(R.id.facility_btn);
		review_btn = (Button) findViewById(R.id.review_btn);
		shared_btn = (Button) findViewById(R.id.shared_btn);
		favourate_btn = (Button) findViewById(R.id.favourate_btn);

		detail_btn.setOnClickListener(this);
		facility_btn.setOnClickListener(this);
		review_btn.setOnClickListener(this);
		shared_btn.setOnClickListener(this);
		favourate_btn.setOnClickListener(this);
	
		heading_txt.setTypeface(Typeface.createFromAsset(
				appContext.getAssets(), "GeosansLight.ttf"));
		item_heading_txt.setTypeface(Typeface.createFromAsset(
				appContext.getAssets(), "Sansation_Regular.ttf"));
		hotel_desc_txt.setTypeface(Typeface.createFromAsset(
				appContext.getAssets(), "Sansation_Regular.ttf"));
		hotel_address_txt.setTypeface(Typeface.createFromAsset(
				appContext.getAssets(), "Sansation_Regular.ttf"));
		
		hotel_contact_txt.setTypeface(Typeface.createFromAsset(appContext.getAssets(),
				"Sansation_Regular.ttf"));
		hotel_email_txt.setTypeface(Typeface.createFromAsset(appContext.getAssets(),
				"Sansation_Regular.ttf"));
		
		tf=Typeface.createFromAsset(appContext.getAssets(),
				"Sansation_Regular.ttf");
		
		hotel_contact_txt.setVisibility(View.GONE);
		view2.setVisibility(View.GONE);
		

		heading_txt.setText(heading);

		try {
			jsonObject = new JSONObject(jsonString);
			itineraryTitle=jsonObject.getString("iteneries_title");
			item_heading_txt.setText(itineraryTitle);
			desc = jsonObject.getString("iteneries_desc").trim();
			address = jsonObject.getString("address").trim();
			email=jsonObject.getString("iteneries_email").trim();
			facility=jsonObject.getString("facility").trim();

			if(desc.length()>0){
				hotel_desc_txt.setText(desc);
				
			}else{
				hotel_desc_txt.setText("Itinerary description is not available");
			}
			if(address.length()>0){
				address="Address :- "+address;
				hotel_address_txt.setText(address);
				
			}else{
				address="Itinerary address is not available";
				hotel_address_txt.setText(address);
			}
			if(email.length()>0){
				email="Email address :- "+email;
				hotel_email_txt.setText(email);
				
			}else{
				email="Email address is not available";
				hotel_email_txt.setText(email);
			}
			
			itinararyId = jsonObject.getString("iteneries_id");
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
			hotel_desc_txt.setTextSize(16);
			hotel_desc_txt.setTypeface(tf, Typeface.NORMAL);
			hotel_email_txt.setVisibility(View.VISIBLE);
			hotel_desc_txt.setTextColor(Color.BLACK);
			hotel_address_txt.setVisibility(View.VISIBLE);
			view1.setVisibility(View.VISIBLE);
			view3.setVisibility(View.VISIBLE);
			review_list.setVisibility(View.GONE);
			hotel_desc_txt.setText(desc);
			hotel_email_txt.setText(email);
			hotel_address_txt.setText(address);
		} else if (v.getId() == R.id.facility_btn) {
			hotel_desc_txt.setVisibility(View.VISIBLE);
			hotel_address_txt.setVisibility(View.GONE);
			hotel_desc_txt.setTextSize(18);
			hotel_desc_txt.setTypeface(tf, Typeface.BOLD);
			hotel_email_txt.setVisibility(View.GONE);
			hotel_desc_txt.setTextColor(Color.BLUE);
			view1.setVisibility(View.GONE);
			view3.setVisibility(View.GONE);
			review_list.setVisibility(View.GONE);
			String fac="";
			if(facility.length()==0){
				hotel_desc_txt.setText("Hotel facility is not available");
			}else{
				String facility1[] =facility.split(",");
				for(int i=0;i<facility1.length;i++){
					String fac1="■ "+facility1[i];
					fac=fac+fac1+"\n";
					
				}
				hotel_desc_txt.setText(fac);
			}
			
		} else if (v.getId() == R.id.review_btn) {
			hotel_desc_txt.setVisibility(View.GONE);
			view3.setVisibility(View.GONE);
			hotel_email_txt.setVisibility(View.GONE);
			view1.setVisibility(View.GONE);
			hotel_address_txt.setVisibility(View.GONE);
			review_list.setVisibility(View.VISIBLE);
			requestFor = Constant.REVIEW;
			if (arrayJsonList.size() == 0) {
				new GetReviewList().execute();
			}

			itemListAdapter = new ItemListAdapter(appContext, requestFor,
					arrayJsonList);
			review_list.setAdapter(itemListAdapter);

		} else if (v.getId() == R.id.shared_btn) {
			Intent i=new Intent(appContext,ShareOnFacebook.class);
			i.putExtra("imageUrl", URLImage);
			i.putExtra("hotelTitle",itineraryTitle);
			i.putExtra("hotelDesc", desc);
			startActivity(i);
		} else if (v.getId() == R.id.favourate_btn) {
			Intent i = new Intent(appContext, WriteItineraryReviewActivity.class);
			i.putExtra("hotelid",itinararyId);
			i.putExtra("heading", "Itineraries");
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

			switch (requestFor) {

			case Constant.REVIEW:
				serverUrl = Constant.serverUrl + "GetIteneries_Review&place_id="+itinararyId;
				jsonSubString = "Review";
				break;

			}
			String response = Utility.findJSONFromUrl(serverUrl);

			if (response != null) {

				try {
					arrayJsonList.clear();
					jsonObject = new JSONObject(response);
					jsonArray = jsonObject.getJSONArray("GetIteneries_Reviews");
					System.out.println("Array  " + jsonArray);

					if (jsonArray != null) {
						for (int i = 0; i < jsonArray.length(); i++) {
							jsonObject = jsonArray.getJSONObject(i);
							jsonObject = jsonObject.getJSONObject("GetIteneries_Review");
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
			hotel_address_txt.setVisibility(View.GONE);
			view1.setVisibility(View.GONE);
			view3.setVisibility(View.GONE);
			hotel_email_txt.setVisibility(View.GONE);
			review_list.setVisibility(View.VISIBLE);
			requestFor=Constant.REVIEW;
			new GetReviewList().execute();
			itemListAdapter = new ItemListAdapter(appContext, requestFor,
					arrayJsonList);
			review_list.setAdapter(itemListAdapter);
		}
		
	}
}
