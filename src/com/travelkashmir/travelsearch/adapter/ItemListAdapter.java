package com.travelkashmir.travelsearch.adapter;

import java.util.ArrayList;
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
import com.travelkashmir.travelsearch.utils.Utility;

public class ItemListAdapter extends ArrayAdapter<JSONObject> {
	private LayoutInflater lflater;
	Context appContext;
	private ArrayList<JSONObject> jsonList;
	private int requestFor;

	public ItemListAdapter(Context context, int requested,
			ArrayList<JSONObject> list) {
		super(context, requested, list);
		// TODO Auto-generated constructor stub

		lflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.jsonList = list;

		this.appContext = context;
		requestFor = requested;
		System.out.println("req for ===>>>" + requestFor);
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		View holder = convertView;

		if (holder == null) {

			switch (requestFor) {

			case Constant.DEALS:
				holder = lflater.inflate(R.layout.hotel_listview, null);
				break;

			case Constant.REVIEW:
				holder = lflater.inflate(R.layout.review_list_layout, null);
				break;
			case Constant.COMMENT:
				holder = lflater.inflate(R.layout.review_list_layout, null);
				break;

			}
		} // fif ends

		try {
			JSONObject obj;
			obj = jsonList.get(position);
			if (obj != null) {

				switch (requestFor) {

				case Constant.DEALS:
					ImageView hotel_image = (ImageView) holder
							.findViewById(R.id.hotel_image);
					TextView hotel_title = (TextView) holder
							.findViewById(R.id.hotel_title);

					hotel_title.setTypeface(Typeface.createFromAsset(appContext.getAssets(),
							"Sansation_Regular.ttf"));
					String imagePath = obj.getString("image_file");
					String hotel_name = obj.getString("deal_title");

					Bitmap bpPhoto = Utility.getBitmap(imagePath);

					if (bpPhoto != null) {
						hotel_image.setImageBitmap(bpPhoto);
						bpPhoto = null;
					}
					hotel_title.setText(hotel_name);
					break;

				case Constant.REVIEW:

					TextView review_username = (TextView) holder
							.findViewById(R.id.hotel_username_review);
					TextView review_text = (TextView) holder
							.findViewById(R.id.hotel_desc_review_txt);
					TextView review_title = (TextView) holder
							.findViewById(R.id.hotel_title_review_txt);
					TextView review_date = (TextView) holder
							.findViewById(R.id.hotel_date_review_txt);
					
					ImageView rating_imgage= (ImageView) holder
							.findViewById(R.id.rating_image);
					
					TextView review_rating = (TextView) holder
							.findViewById(R.id.hotel_rating_review_txt);

					review_username.setTypeface(Typeface.createFromAsset(appContext.getAssets(),
							"Sansation_Regular.ttf"));
					review_text.setTypeface(Typeface.createFromAsset(appContext.getAssets(),
							"Sansation_Regular.ttf"));
					review_title.setTypeface(Typeface.createFromAsset(appContext.getAssets(),
							"Sansation_Regular.ttf"));
					review_date.setTypeface(Typeface.createFromAsset(appContext.getAssets(),
							"Sansation_Regular.ttf"));
					
					String title = obj.getString("review_title");
					String desc = obj.getString("review_text");
					String rating = obj.getString("rating");
					String usename = obj.getString("first_name");
					String date = obj.getString("date");

					if(rating.equals("1")){
						rating_imgage.setBackgroundResource(R.drawable.rating_1);
					}else if(rating.equals("2")){
						rating_imgage.setBackgroundResource(R.drawable.rating_2);
					}else if(rating.equals("3")){
						rating_imgage.setBackgroundResource(R.drawable.rating_3);
					}else if(rating.equals("4")){
						rating_imgage.setBackgroundResource(R.drawable.rating_4);
					}else if(rating.equals("5")){
						rating_imgage.setBackgroundResource(R.drawable.rating_5);
					}
					
					review_title.setText(title);
					review_text.setText(desc);
					review_rating.setText(rating);
					review_username.setText(usename);
					review_date.setText(date);

					break;

				case Constant.COMMENT:

					TextView comment_username = (TextView) holder
					.findViewById(R.id.hotel_username_review);
			TextView comment_text = (TextView) holder
					.findViewById(R.id.hotel_desc_review_txt);
			TextView comment_title = (TextView) holder
					.findViewById(R.id.hotel_title_review_txt);
			TextView comment_date = (TextView) holder
					.findViewById(R.id.hotel_date_review_txt);
			
			ImageView rating_imgage1= (ImageView) holder
					.findViewById(R.id.rating_image);
			
			TextView review_rating1 = (TextView) holder
					.findViewById(R.id.hotel_rating_review_txt);

			comment_username.setTypeface(Typeface.createFromAsset(appContext.getAssets(),
					"Sansation_Regular.ttf"));
			comment_text.setTypeface(Typeface.createFromAsset(appContext.getAssets(),
					"Sansation_Regular.ttf"));
			comment_title.setTypeface(Typeface.createFromAsset(appContext.getAssets(),
					"Sansation_Regular.ttf"));
			comment_date.setTypeface(Typeface.createFromAsset(appContext.getAssets(),
					"Sansation_Regular.ttf"));
			
			String commenttitle = obj.getString("title");
			String commentdesc = obj.getString("comment");
			String commentrating = obj.getString("rating");
			String commentusename = obj.getString("first_name");
			String commentdate = obj.getString("date");

			if(commentrating.equals("1")){
				rating_imgage1.setBackgroundResource(R.drawable.rating_1);
			}else if(commentrating.equals("2")){
				rating_imgage1.setBackgroundResource(R.drawable.rating_2);
			}else if(commentrating.equals("3")){
				rating_imgage1.setBackgroundResource(R.drawable.rating_3);
			}else if(commentrating.equals("4")){
				rating_imgage1.setBackgroundResource(R.drawable.rating_4);
			}else if(commentrating.equals("5")){
				rating_imgage1.setBackgroundResource(R.drawable.rating_5);
			}
			
			comment_title.setText(commenttitle);
			comment_text.setText(commentdesc);
			review_rating1.setText(commentrating);
			comment_username.setText(commentusename);
			comment_date.setText(commentdate);

					break;

				}
				// holder.setBackgroundResource(R.drawable.list_button_pressed);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return holder;
	}

}
