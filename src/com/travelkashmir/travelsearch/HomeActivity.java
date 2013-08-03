package com.travelkashmir.travelsearch;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.travelkashmir.travelsearch.adapter.HomeListAdaptor;
import com.travelkashmir.travelsearch.constant.Constant;
import com.travelkashmir.travelsearch.utils.Utility;

@SuppressLint("ResourceAsColor")
public class HomeActivity extends Activity {
	
	TextView txt_homeHeading;
	EditText edt_homeSearch;
	ListView list_category;
	private Bundle bundle;
	private String heading;
	private Context appContext;
	Intent intent;
	LinearLayout searchLayout;	
	int requestFor;
	private String jsonSubString;
	private ProgressDialog progressDialog;
	
	ArrayList<Categories> listOfCategories;
	Thread thread;
	public HashMap<String, Bitmap> hashImages;
	public String key_hashmap = "bitmap";
	private HomeListAdaptor adapter;
	Drawable drawable;
	String get_text;
	ArrayList<Categories> tempCatList;
	// for adding dynamically buttons according to city name
	LinearLayout parentLinear;
	
	HorizontalScrollView horizontalScrollView;
	Button btn_all;
	ArrayList<String> city_list;
	boolean allSelected;
	String cityForServices;
	int count = 1;
	Button btnSelected;
	boolean check;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		setContentView(R.layout.hotel_home);
		
	
		appContext = this;
		// for adding buttons
		city_list = new ArrayList<String>();

		bundle = getIntent().getExtras();
		if (bundle != null) {
			heading = bundle.getString("heading");
	
			requestFor = bundle.getInt("requestFor", 0);

		}
		listOfCategories = new ArrayList<Categories>();
		tempCatList = new ArrayList<Categories>();
		
		hashImages = new HashMap<String, Bitmap>();
	
		txt_homeHeading = (TextView) findViewById(R.id.headng_txt);
	
		edt_homeSearch = (EditText) findViewById(R.id.edt_homesearch);
		list_category = (ListView) findViewById(R.id.hotel_listview);
		
		
		// for adding dynamic buttons
		horizontalScrollView = (HorizontalScrollView) findViewById(R.id.categoryScroll);
		
		parentLinear = (LinearLayout) findViewById(R.id.categoryLayout);
		
		txt_homeHeading.setTypeface(Typeface.createFromAsset(appContext.getAssets(),
				"GeosansLight.ttf"));
		txt_homeHeading.setText(heading);
		
		
		
		horizontalScrollView.setHorizontalScrollBarEnabled(true);
		new GetHotelList().execute();
	
		// adding adapter to list
		adapter = new HomeListAdaptor(appContext, 0, tempCatList, hashImages,heading);
		list_category.setAdapter(adapter);

		// setting the visibility of various layout
	
		
		list_category.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(appContext, HotelDetailActivity.class);
				Categories catObject = tempCatList.get(position);
				intent.putExtra("categories", catObject);
				intent.putExtra("heading", heading);
				startActivity(intent);

			}
		});

		
		edt_homeSearch.addTextChangedListener( new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				Categories getCate = null;
				String check_text=null; // for comparing city name in small letters 
				get_text = edt_homeSearch.getText().toString().toLowerCase();
				if (tempCatList.size() > 0) {
					tempCatList.clear();
				}
				for (int i = 0; i < listOfCategories.size(); i++) {
					getCate = listOfCategories.get(i);
					/*if (getCate.name.equalsIgnoreCase(get_text)) {
						tempCatList.add(getCate);
					}*/
					check_text = getCate.hotel_title.toString().toLowerCase();
					if (check_text.contains(get_text)) {
						tempCatList.add(getCate);
					}
					
					
			
				}
			
				adapter.notifyDataSetChanged();
			}/// ending method 
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		
	} // on create ends here


	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		
	}

	public class GetHotelList extends AsyncTask<Void, Void, Void> {
		String responseString = null;
		String serverUrl;
		

		protected void onPreExecute() {
			super.onPreExecute();

			progressDialog = ProgressDialog.show(appContext, "",
					"Please wait...", true, true,
					new OnCancelListener() {

						public void onCancel(DialogInterface dialog) {
							// TODO Auto-generated method stub
							GetHotelList.this.cancel(true);
						}
					});
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			switch (requestFor) {

			case Constant.HOTEL:
				serverUrl = Constant.serverUrl + "GetHotels";
				jsonSubString = "hotel";
				break;

			case Constant.RESTAURANT:
				serverUrl = Constant.serverUrl + "GetRestaurants";
				jsonSubString = "Restaurant";
				break;

			}
			
			responseString = Utility.findJSONFromUrl(serverUrl);

			if (responseString == null) {
				return null;
			} else {
				city_list.add("ALL");
				try {
					JSONObject jobject = new JSONObject(responseString);
					JSONArray array = jobject.getJSONArray("hotels");
					for (int i = 0; i < array.length(); i++) {
						JSONObject obj = array.getJSONObject(i);
						JSONObject getjson = obj.getJSONObject(jsonSubString);

						Categories category = new Categories(
								getjson.getString("hotelid"),
								getjson.getString("hotel_title"),
								getjson.getString("address"),
								getjson.getString("locationid"),
								getjson.getString("telephone"),
								getjson.getString("fax"),
								getjson.getString("email"),
								getjson.getString("website"),
								getjson.getString("conact_person"),
								getjson.getString("image_file"),
								getjson.getString("userid"),
								getjson.getString("cat_id"),
								getjson.getString("desc"),
								getjson.getString("facility"),
								getjson.getString("menu"));
						// if (count == 1)
						listOfCategories.add(category);
						tempCatList.add(category);
						String strCityName = getjson.getString("locationid").toUpperCase();
						if (!city_list.contains(strCityName)) {
							city_list.add(strCityName);
						}
						category = null;
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			if (responseString == null) {

				progressDialog.cancel();
				Utility.showAlert(appContext, "No Categories Found",
						"Check Your Network Connection ");

			} else {

				adapter.notifyDataSetChanged();
				addcityButton();
				Button btnCheck = (Button) parentLinear.getChildAt(0);
				btnCheck.setBackgroundResource(R.drawable.selected_tab);
				btnCheck.setTextColor(getResources().getColor(R.color.white));

				thread = new Thread(downloadImages);
				thread.start();

				progressDialog.cancel();
			
			}
			

		}

	}// asyntask class ends here

	@SuppressLint("ResourceAsColor")
	public void addcityButton() {

		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.setMargins(0, 0, 0, 0);

		for (int i = 0; i < city_list.size(); i++) {
			String city = city_list.get(i);
		
			Button btn = new Button(appContext);
			btn.setBackgroundResource(R.drawable.unselected_tab);
			btn.setTextColor(getResources().getColor(R.color.black));

			btn.setText(city);
			btn.setTag(i);
			btn.setLayoutParams(params);

		
			parentLinear.addView(btn);
			btn.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					for (int i = 0; i < parentLinear.getChildCount(); i++) {
						Button btnCheck = (Button) parentLinear.getChildAt(i);
						btnCheck.setBackgroundResource(R.drawable.unselected_tab);
						btnCheck.setTextColor(getResources().getColor(R.color.black));
					}

					btnSelected = (Button) v;
					btnSelected.setBackgroundResource(R.drawable.selected_tab);
					btnSelected.setTextColor(getResources().getColor(R.color.white));
					btnSelected.setTextSize(15);
					btnSelected.setTypeface(null,Typeface.BOLD);
					String cityForServices1 = (String) btnSelected.getText();
					if (cityForServices1.equalsIgnoreCase("All")) {
						// Add ALL btn filter function
						btnSelected.setBackgroundResource(R.drawable.selected_tab);
						btnSelected.setTextColor(getResources().getColor(R.color.white));
						tempCatList.clear();
						for (int i = 0; i < listOfCategories.size(); i++) {
							tempCatList.add(listOfCategories.get(i));
					
							adapter.notifyDataSetChanged();
							adapter.setNotifyOnChange(true);
						}
					} else {
						
						tempCatList.clear();
				
						for (int i = 0; i < listOfCategories.size(); i++) {

							Categories cat = listOfCategories.get(i);
							if (cat.locationid.equalsIgnoreCase(cityForServices1)) {
								tempCatList.add(cat);

							}
						}

					}
				
					adapter.notifyDataSetChanged();
					

					// new FetchCategoriesList().execute();

				}
			});
		}

	}

	public Runnable runOnMain = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			adapter.notifyDataSetChanged();
		}
	};
	Runnable downloadImages = new Runnable() {
		Bitmap bitmapImage;
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			for (int i = 0; i < tempCatList.size(); i++) {
				Categories obj = tempCatList.get(i);
				try {
				

					bitmapImage = Utility.getBitmap(obj.image_file);
					hashImages.put(obj.hotelid, bitmapImage);
					runOnUiThread(runOnMain);
					
				} catch (OutOfMemoryError e) {
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

