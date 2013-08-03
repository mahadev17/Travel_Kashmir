package com.travelkashmir.travelsearch;

import java.util.ArrayList;
import java.util.HashMap;

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
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import com.travelkashmir.travelsearch.adapter.DealsItemAdapter;
import com.travelkashmir.travelsearch.constant.Constant;
import com.travelkashmir.travelsearch.utils.Utility;

public class ItenararyActivity extends Activity {
	private Context appContext;
	// private ArrayList<HashMap<String, String>> hashMaps;
	private ArrayList<JSONObject> arrayJsonList;
	private DealsItemAdapter itemListAdapter;
	private ListView list_view;
	private int requestFor;
	private ProgressDialog progressDialog;
	private Bundle requestBundle;
	private String jsonSubString;
	private String heading;
	private TextView heading_txt;

	
	Thread thread;
	public HashMap<String, Bitmap> hashImages;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.deals_view);

		appContext = this;

		arrayJsonList = new ArrayList<JSONObject>();
		hashImages = new HashMap<String, Bitmap>();
		
		requestBundle = getIntent().getExtras();
		if (requestBundle != null) {
			requestFor = requestBundle.getInt("requestFor");
			heading = requestBundle.getString("heading");
		}

		list_view = (ListView) findViewById(R.id.hotel_listview);
		heading_txt = (TextView) findViewById(R.id.headng_txt);

		heading_txt.setTypeface(Typeface.createFromAsset(
				appContext.getAssets(), "GeosansLight.ttf"));
		heading_txt.setText(heading);

		new GetDeals().execute();
		
		itemListAdapter = new DealsItemAdapter(appContext, requestFor,arrayJsonList,hashImages,heading);
		list_view.setAdapter(itemListAdapter);
		

		list_view.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				Intent intent = new Intent(appContext,
						ItineraryDetailsActivity.class);
				intent.putExtra("heading", "Itinerary");
				intent.putExtra("Deals", arrayJsonList.get(pos).toString());
				startActivity(intent);
			}
		});

	}

	public class GetDeals extends AsyncTask<String, Void, String> {

		String response = null;
		String serverUrl;

		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = ProgressDialog.show(appContext, "",
					"Please wait...", true, true, new OnCancelListener() {

						public void onCancel(DialogInterface dialog) {
							// TODO Auto-generated method stub
							GetDeals.this.cancel(true);
						}
					});
		}

		@Override
		protected String doInBackground(String... params) {
			{
				// TODO Auto-generated method stub

				switch (requestFor) {

				case Constant.ITINERARY:
					serverUrl = Constant.serverUrl + "GetIteneries";
					jsonSubString = "itenerie";
					break;
				}

			}
			String response = Utility.findJSONFromUrl(serverUrl);

			if (response == null) {
				return null;
			}else{

				try {

					JSONObject jsonObject = new JSONObject(response);
					JSONArray jsonArray = jsonObject.getJSONArray("iteneries");

					if (jsonArray != null) {
						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject jsonResponse = (JSONObject)jsonArray.get(i);
							jsonResponse = jsonResponse.getJSONObject(jsonSubString);
							arrayJsonList.add(jsonResponse);
						}
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				System.out.println("Result is  " + response);
				return response;
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
				thread = new Thread(downloadImages);
				thread.start();
				progressDialog.dismiss();
			}

		}
	}
	
	public Runnable runOnMain = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			itemListAdapter.notifyDataSetChanged();
		}
	};
	
	Runnable downloadImages = new Runnable() {
		Bitmap bitmapImage;
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			for (int i = 0; i < arrayJsonList.size(); i++) {
				
				try {
					bitmapImage = Utility.getBitmap(arrayJsonList.get(i).getString("image_file"));
					hashImages.put(arrayJsonList.get(i).getString("iteneries_id"), bitmapImage);
					runOnUiThread(runOnMain);
					
				} catch (OutOfMemoryError e) {
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					runOnUiThread(runOnMain);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		}
	};

} // final class ends here

