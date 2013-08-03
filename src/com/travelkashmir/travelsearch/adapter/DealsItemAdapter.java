package com.travelkashmir.travelsearch.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONObject;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.travelkashmir.travelsearch.R;
import com.travelkashmir.travelsearch.constant.Constant;
import com.travelkashmir.travelsearch.utils.ImageLoader;

public class DealsItemAdapter extends ArrayAdapter<JSONObject> {
	private LayoutInflater lflater;
	Context appContext;
	private ArrayList<JSONObject> jsonList;
	private int requestFor;
	ImageLoader imageLoader;
	HashMap<String, Bitmap> hmImages;
	String heading;
	public DealsItemAdapter(Context context, int requested,
			ArrayList<JSONObject> list,HashMap<String, Bitmap> _hmImages,String heading) {
		super(context,requested, list);
		// TODO Auto-generated constructor stub
		
		jsonList = list;
		hmImages = _hmImages;
		heading= heading;
		
		System.out.println("Json List is    in adapter   "+jsonList);
		appContext = context;
		requestFor = requested;
		lflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		View holder = convertView;
		//imageLoader = new ImageLoader(appContext);
		if (holder == null) {
			
			switch(requestFor){
			
			case Constant.DEALS:
				holder = lflater.inflate(R.layout.hotel_listview, null);
				break;
				
			case Constant.REVIEW:
				holder = lflater.inflate(R.layout.review_list_layout, null);
				break;		
			case Constant.ITINERARY:
				holder = lflater.inflate(R.layout.hotel_listview, null);
				break;		
			}
		} // fif ends
		
		try {
			JSONObject obj;
			obj = jsonList.get(position);
			if (obj != null) {
			
				switch(requestFor){
				
				case Constant.DEALS:
					ImageView hotel_image = (ImageView) holder
							.findViewById(R.id.hotel_image);
					
					hotel_image.setBackgroundResource(R.drawable.place_holder_deals);
					TextView hotel_title = (TextView) holder
							.findViewById(R.id.hotel_title);
					
					hotel_title.setTypeface(Typeface.createFromAsset(appContext.getAssets(),
							"Sansation_Regular.ttf"));
					String hotel_name = obj.getString("deal_title");
					hotel_title.setText(hotel_name);
					
					try {

						Bitmap bitmapPhoto = hmImages.get(obj.getString("dealid"));
						if (bitmapPhoto == null) {
						} else {
							hotel_image.setImageBitmap(bitmapPhoto);
						}
					} catch (OutOfMemoryError e) {

						e.printStackTrace();
					}
					
					break;
			
				case Constant.REVIEW:
					
					TextView review_title = (TextView) holder
							.findViewById(R.id.hotel_title_review_txt);
					TextView review_text = (TextView) holder
							.findViewById(R.id.hotel_desc_review_txt);
					TextView review_rating = (TextView) holder
							.findViewById(R.id.hotel_rating_review_txt);
					
					review_text.setTypeface(Typeface.createFromAsset(appContext.getAssets(),
							"Sansation_Regular.ttf"));
					review_title.setTypeface(Typeface.createFromAsset(appContext.getAssets(),
							"Sansation_Regular.ttf"));
					
					String title = obj.getString("first_name");
					String desc = obj.getString("review_text");
					String rating = obj.getString("rating");
					
					review_title.setText(title);
					review_text.setText(desc);
					review_rating.setText(rating);
					
					break;	
				case Constant.ITINERARY:
					ImageView hotel_image1 = (ImageView) holder
							.findViewById(R.id.hotel_image);
					
					hotel_image1.setBackgroundResource(R.drawable.place_holder_itineraries);
					TextView hotel_title1 = (TextView) holder
							.findViewById(R.id.hotel_title);
					
					String hotel_name1 = obj.getString("iteneries_title");
					hotel_title1.setTypeface(Typeface.createFromAsset(appContext.getAssets(),
							"Sansation_Regular.ttf"));
					
					hotel_title1.setText(hotel_name1);
										
					try {
						
						Bitmap bitmapPhoto  = hmImages.get(obj.getString("iteneries_id"));
						
						if (bitmapPhoto == null) {
					
							
						} else {
							hotel_image1.setImageBitmap(bitmapPhoto);
							
						}
					} catch (OutOfMemoryError e) {

						e.printStackTrace();
					}
					
					break;
				}
			 //holder.setBackgroundResource(R.drawable.list_button_pressed);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return holder;
	}

	
	

}
