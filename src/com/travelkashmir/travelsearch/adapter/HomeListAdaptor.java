package com.travelkashmir.travelsearch.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.travelkashmir.travelsearch.Categories;
import com.travelkashmir.travelsearch.R;

public class HomeListAdaptor extends ArrayAdapter<Categories> {
	private Context appContext;
	LayoutInflater inflater;
	HashMap<String, Bitmap> hmImages;
	
	private ArrayList<Categories> listOfCategories;
	String heading;
	public HomeListAdaptor(Context context, int textViewResourceId,
			ArrayList<Categories> list, HashMap<String, Bitmap> _hmImages,String category) {
		super(context, textViewResourceId, list);
		appContext = context;
		listOfCategories = list;
		hmImages = _hmImages;
		heading= category;
		inflater = (LayoutInflater) appContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		if (rowView == null) {
			
			rowView = inflater.inflate(R.layout.hotel_listview, null);
		}
		ImageView img_mainCategory = (ImageView) rowView
				.findViewById(R.id.hotel_image);
		if(heading.equalsIgnoreCase("Hotels")){
			img_mainCategory.setBackgroundResource(R.drawable.place_holder);
		}else if(heading.equalsIgnoreCase("Restaurants")){
			img_mainCategory.setBackgroundResource(R.drawable.place_holder_resturants);
		}
		
		TextView txt_mainCategory = (TextView) rowView
				.findViewById(R.id.hotel_title);
		txt_mainCategory.setTypeface(Typeface.createFromAsset(appContext.getAssets(),
				"Sansation_Regular.ttf"));
		ImageView img_arrow = (ImageView) rowView
				.findViewById(R.id.arrow_image);
		img_arrow.setImageResource(R.drawable.arrow_icon_red);
		Categories category = listOfCategories.get(position);
		if (category != null) {
			txt_mainCategory.setText(category.hotel_title);
			txt_mainCategory.setTypeface(Typeface.createFromAsset(appContext.getAssets(),
					"Sansation_Regular.ttf"));
		}
		try {
			Log.d("Photo id is",">>>>  "+hmImages.get(category.hotelid));
			Bitmap bitmapPhoto  = hmImages.get(category.hotelid);
			
			if (bitmapPhoto == null) {
				if(heading.equalsIgnoreCase("Hotels")){
					img_mainCategory.setBackgroundResource(R.drawable.place_holder);
				}else if(heading.equalsIgnoreCase("Restaurants")){
					img_mainCategory.setBackgroundResource(R.drawable.place_holder_resturants);
				}

			} else {
				img_mainCategory.setImageBitmap(bitmapPhoto);
				
			}
		} catch (OutOfMemoryError e) {

			e.printStackTrace();
		}

		return rowView;
	}
}
