package com.travelkashmir.travelsearch.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.net.http.AndroidHttpClient;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.Facebook;
import com.google.android.maps.GeoPoint;


public class Utility extends Application {
	public static final String PREFS_LOGIN_NAME = "TravelKashmir";
	public static Facebook mFacebook;
	public static AsyncFacebookRunner mAsyncRunner;
	public static JSONObject mFriendsList;
	public static String userUID = null;
	public static String objectID = null;
	public static Bitmap bm;
	// public static FriendsGetProfilePics model;
	public static AndroidHttpClient httpclient = null;
	public static Hashtable<String, String> currentPermissions = new Hashtable<String, String>();

	private static int MAX_IMAGE_DIMENSION = 720;
	public static final String HACK_ICON_URL = "http://www.facebookmobileweb.com/hackbook/img/facebook_icon_large.png";
	public static String postedDate;
	public static String getDated;
	public static Date date;
	public static int mMonth, mYear, mDate, mHour, mMinute, mSecond;
	public static int curDate,curMonth,curYear,curHour,curMinute,curSecond;

	
	
	
	public static void setUserIdPrefernce(Context context, String name,
			String value) {
		SharedPreferences settings = context.getSharedPreferences(
				PREFS_LOGIN_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(name, value);

		// Commit the edits!
		editor.commit();
	}

	public static String getUserIdPrefernce(Context context, String name) {
		SharedPreferences settings = context.getSharedPreferences(
				PREFS_LOGIN_NAME, 0);

		// / return settings.putString(name, null);
		return settings.getString(name, "0");
	}
	
	public static void setLoginPreferences(Context context, String name,
			boolean value) {
			SharedPreferences settings = context.getSharedPreferences(
					PREFS_LOGIN_NAME, 0);
			SharedPreferences.Editor editor = settings.edit();
			editor.putBoolean(name, value);
			// Commit the edits!
			editor.commit();
		}

	public static boolean getLoginPrefernce(Context context, String name) {
		SharedPreferences settings = context.getSharedPreferences(
				PREFS_LOGIN_NAME, 0);

		// / return settings.putString(name, null);
		return settings.getBoolean(name, false);
	}

	public static void setCurrentLattitudePrefernce(Context context,
			String name, String value) {
		SharedPreferences settings = context.getSharedPreferences(
				PREFS_LOGIN_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(name, value);

		// Commit the edits!
		editor.commit();
	}

	public static String getCurrentLattitudePrefernce(Context context,
			String name) {
		SharedPreferences settings = context.getSharedPreferences(
				PREFS_LOGIN_NAME, 0);

		// / return settings.putString(name, null);
		return settings.getString(name, "0");
	}

	public static void setCurrentLongitudePrefernce(Context context,
			String name, String value) {
		SharedPreferences settings = context.getSharedPreferences(
				PREFS_LOGIN_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(name, value);
		// Commit the edits!
		editor.commit();
	}

	public static void ShowAlertWithMessage(Context context, String title,
			String msg) {
		// Assign the alert builder , this can not be assign in Click events
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setCancelable(false);
		builder.setMessage(msg);
		builder.setIcon(com.travelkashmir.travelsearch.R.drawable.alert_dialog_icon);
		builder.setTitle(title);
		// Set behavior of negative button
		builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				// Cancel the dialog
				dialog.cancel();
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	public static String getCurrentLongitudePrefernce(Context context,
			String name) {
		SharedPreferences settings = context.getSharedPreferences(
				PREFS_LOGIN_NAME, 0);

		// / return settings.putString(name, null);
		return settings.getString(name, "0");
	}
	public static void setFavouriteprefence(Context context,
			String name,  boolean value) {
		SharedPreferences settings = context.getSharedPreferences(
				PREFS_LOGIN_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean(name, value);
		// Commit the edits!
		editor.commit();
	}
	public static boolean getFavouritePrefernce(Context context,
			String name) {
		SharedPreferences settings = context.getSharedPreferences(
				PREFS_LOGIN_NAME, 0);

		// / return settings.putString(name, null);
		return settings.getBoolean(name, false);
	}
	public static void showAlert(Context mContext, String title, String msg) {
		/*
		 * CommonUtility.ShowAlertWithMessageForAction(mContext,
		 * "You are not logged In", "Please log In first");
		 */
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setCancelable(false);
		builder.setTitle(title);
		builder.setMessage(msg);
		// Set behavior of negative button

		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
		});

		AlertDialog alert = builder.create();
		alert.show();
	}

	public static String findJSONFromUrl(String url) {
		// TODO Auto-generated method stub
		JSONObject jObj = new JSONObject();
		String result = "";

		System.out.println("URL comes in jsonparser class is:  " + url);
		try {
			int TIMEOUT_MILLISEC = 100000; // = 10 seconds
			HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams,
					TIMEOUT_MILLISEC);
			HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url);
			// httpGet.setURI(new URI(url));

			HttpResponse httpResponse = httpClient.execute(httpGet);
			int status = httpResponse.getStatusLine().getStatusCode();

			InputStream is = httpResponse.getEntity().getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");

			}

			is.close();
			result = sb.toString();
			System.out.println("result  in jsonparser class ........" + result);

		} catch (Exception e) {
			System.out.println("exception in jsonparser class ........");
			e.printStackTrace();
			return null;
		}
		return result;
	}

	public static Bitmap getbitmapInSmallSize(String imageurl) {
		
		Bitmap myBitmap = null;
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet request = new HttpGet(imageurl);
		HttpResponse response;
		try {
			response = httpClient.execute(request);
			InputStream is = response.getEntity().getContent();
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inPurgeable = true;
			options.outHeight = 30;
			options.outWidth = 30;
			options.inSampleSize = 2;
			myBitmap = BitmapFactory.decodeStream(is, null, options); // DecodeFile(is,
																		// options);
		myBitmap = Bitmap.createScaledBitmap(myBitmap, 100, 100, true);

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (OutOfMemoryError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		return myBitmap;
	}

	public static Bitmap getBitmap(String url) {
		Bitmap imageBitmap = null;
		try {
			URL aURL = new URL(url);
			URLConnection conn = aURL.openConnection();
			conn.connect();
			InputStream is = conn.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			try {
				imageBitmap = BitmapFactory.decodeStream(new FlushedInputStream(is));
			} catch (OutOfMemoryError error) {
				error.printStackTrace();
				System.out.println("exception in get bitma putility");
			}

			bis.close();
			is.close();
			final int IMAGE_MAX_SIZE = 50;
			// Decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			int scale = 1;
			while ((o.outWidth * o.outHeight) * (1 / Math.pow(scale, 2)) > IMAGE_MAX_SIZE) {
				scale++;
			}
			if (scale > 1) {
				scale--;
				// scale to max possible inSampleSize that still yields an image
				// larger than target
				o = new BitmapFactory.Options();
				o.inSampleSize = scale;
				// b = BitmapFactory.decodeStream(in, null, o);

				// resize to desired dimensions
				int height = imageBitmap.getHeight();
				int width = imageBitmap.getWidth();

				double y = Math.sqrt(IMAGE_MAX_SIZE
						/ (((double) width) / height));
				double x = (y / height) * width;

				Bitmap scaledBitmap = Bitmap.createScaledBitmap(imageBitmap, (int) x,
						(int) y, true);
				imageBitmap.recycle();
				imageBitmap= scaledBitmap;

				System.gc();
			} else {
				// b = BitmapFactory.decodeStream(in);
			}

		} catch (OutOfMemoryError error) {
			error.printStackTrace();
			System.out.println("exception in get bitma putility");
		} catch (Exception e) {
			System.out.println("exception in get bitma putility");
			e.printStackTrace();
		}
		return imageBitmap;
	}
	
	static class FlushedInputStream extends FilterInputStream {
		public FlushedInputStream(InputStream inputStream) {
			super(inputStream);
		}
	}

	public static GeoPoint getPoint(double lat, double lon) {
		return (new GeoPoint((int) (lat * 1000000.0), (int) (lon * 1000000.0)));
	}

	public static String getAddressFromLtdLng() {
		return null;
	}

	public static String postParamsAndfindJSON(String url,
			ArrayList<NameValuePair> params) {
		// TODO Auto-generated method stub
		JSONObject jObj = new JSONObject();
		String result = "";

		System.out.println("URL comes in jsonparser class is:  " + url);
		try {
			int TIMEOUT_MILLISEC = 100000; // = 10 seconds
			HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams,
					TIMEOUT_MILLISEC);
			HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);

			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(new UrlEncodedFormEntity(params));
			// httpGet.setURI(new URI(url));

			HttpResponse httpResponse = httpClient.execute(httpPost);
			int status = httpResponse.getStatusLine().getStatusCode();

			InputStream is = httpResponse.getEntity().getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
				
			}

			is.close();
			result = sb.toString();

		} catch (Exception e) {
			System.out.println("exception in jsonparser class ........");
			e.printStackTrace();
			return null;
		}
		return result;
	}

	static Uri pngUri;

	public static Uri saveToSd(Bitmap bm, String name) {

		if (bm != null) {

			String sdPath = Environment.getExternalStorageDirectory()
					.getAbsolutePath();

			File dest = new File(sdPath, name);

			try {
				FileOutputStream out = new FileOutputStream(dest);
				bm.compress(Bitmap.CompressFormat.PNG, 90, out);
				out.flush();
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			pngUri = Uri.fromFile(dest);

			return pngUri;
		} else {

		}
		return null;

	}

	public static byte[] scaleImage(Context context, Uri photoUri)
			throws IOException {
		InputStream is = context.getContentResolver().openInputStream(photoUri);
		BitmapFactory.Options dbo = new BitmapFactory.Options();
		dbo.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(is, null, dbo);
		is.close();

		int rotatedWidth, rotatedHeight;
		int orientation = 0;// getOrientation(context, photoUri);

		if (orientation == 90 || orientation == 270) {
			rotatedWidth = dbo.outHeight;
			rotatedHeight = dbo.outWidth;
		} else {
			rotatedWidth = dbo.outWidth;
			rotatedHeight = dbo.outHeight;
		}

		Bitmap srcBitmap;
		is = context.getContentResolver().openInputStream(photoUri);
		if (rotatedWidth > MAX_IMAGE_DIMENSION
				|| rotatedHeight > MAX_IMAGE_DIMENSION) {
			float widthRatio = ((float) rotatedWidth)
					/ ((float) MAX_IMAGE_DIMENSION);
			float heightRatio = ((float) rotatedHeight)
					/ ((float) MAX_IMAGE_DIMENSION);
			float maxRatio = Math.max(widthRatio, heightRatio);

			// Create the bitmap from file
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = (int) maxRatio;
			srcBitmap = BitmapFactory.decodeStream(is, null, options);
		} else {
			srcBitmap = BitmapFactory.decodeStream(is);
		}
		is.close();

		/*
		 * if the orientation is not 0 (or -1, which means we don't know), we
		 * have to do a rotation.
		 */
		if (orientation > 0) {
			Matrix matrix = new Matrix();
			matrix.postRotate(orientation);

			srcBitmap = Bitmap.createBitmap(srcBitmap, 0, 0,
					srcBitmap.getWidth(), srcBitmap.getHeight(), matrix, true);
		}

		String type = context.getContentResolver().getType(photoUri);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		srcBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
		/*
		 * if (type.equals("image/png")) {
		 * srcBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos); } else if
		 * (type.equals("image/jpg") || type.equals("image/jpeg")) {
		 * srcBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); }
		 */
		byte[] bMapArray = baos.toByteArray();
		baos.close();
		return bMapArray;
	}

	public static byte[] resizeImage(Context context, Uri photoUri) {
		byte[] data = null;
		try {
			ContentResolver cr = context.getContentResolver();
			InputStream inputStream = cr.openInputStream(photoUri);
			Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
			data = baos.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return data;

	}

	public static int getOrientation(Context context, Uri photoUri) {
		/* it's on the external media. */
		Cursor cursor = context.getContentResolver().query(photoUri,
				new String[] { MediaStore.Images.ImageColumns.ORIENTATION },
				null, null, null);

		if (cursor.getCount() != 1) {
			return -1;
		}

		cursor.moveToFirst();
		return cursor.getInt(0);
	}

	public static void setBitmap(Bitmap b) {
		if (b != null)
			bm = b;
	}

	public static Bitmap getBitmapImage() {
		return bm;
	}

	public static byte[] scaleImage(Context context, int resId)
			throws IOException {
		// InputStream is =
		// context.getContentResolver().openInputStream(photoUri);
		resId = com.travelkashmir.travelsearch.R.drawable.arrow_icon_red;
		BitmapFactory.Options dbo = new BitmapFactory.Options();
		dbo.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(context.getResources(), resId, dbo);
		// BitmapFactory.decodeStream(is, null, dbo);
		// is.close();

		int rotatedWidth, rotatedHeight;
		// int orientation = getOrientation(context, photoUri);

		// if (orientation == 90 || orientation == 270) {
		// rotatedWidth = dbo.outHeight;
		// rotatedHeight = dbo.outWidth;
		// } else {
		// rotatedWidth = dbo.outWidth;
		// rotatedHeight = dbo.outHeight;
		// }
		rotatedHeight = dbo.outHeight;
		rotatedWidth = dbo.outWidth;
		Bitmap srcBitmap;
		// is = context.getContentResolver().openInputStream(photoUri);
		if (rotatedWidth > MAX_IMAGE_DIMENSION
				|| rotatedHeight > MAX_IMAGE_DIMENSION) {
			float widthRatio = ((float) rotatedWidth)
					/ ((float) MAX_IMAGE_DIMENSION);
			float heightRatio = ((float) rotatedHeight)
					/ ((float) MAX_IMAGE_DIMENSION);
			float maxRatio = Math.max(widthRatio, heightRatio);

			// Create the bitmap from file
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = (int) maxRatio;
			srcBitmap = BitmapFactory.decodeResource(context.getResources(),
					resId, dbo);
		} else {
			srcBitmap = BitmapFactory.decodeResource(context.getResources(),
					resId, dbo);
		}
		if (srcBitmap != null) {
			Log.i("Utility", "image");
		}
		// is.close();

		/*
		 * if the orientation is not 0 (or -1, which means we don't know), we
		 * have to do a rotation.
		 */
		int orientation = 1;
		if (orientation > 0) {
			Matrix matrix = new Matrix();
			matrix.postRotate(orientation);

			srcBitmap = Bitmap.createBitmap(srcBitmap, 0, 0,
					srcBitmap.getWidth(), srcBitmap.getHeight(), matrix, true);
		}

		// String type = context.getContentResolver().getType(photoUri);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		srcBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
		// srcBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
		/*
		 * if (type.equals("image/png")) {
		 * srcBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos); } else if
		 * (type.equals("image/jpg") || type.equals("image/jpeg")) {
		 * srcBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); }
		 */
		byte[] bMapArray = baos.toByteArray();
		baos.close();
		return bMapArray;
	}
	public static String secretImageInfo(String dateSend) {
/*			JSONObject data = shareList.get(screen);
			try {
				postedDate = data.getString("DateCreated").toString();
				System.out.println("post date " + postedDate);

			} catch (Exception e) {
				e.printStackTrace();
			}
*/		
		postedDate=dateSend;
			if (postedDate.contains("0000-00-00 00:00:00")) {
				return "Invailed Date";
			} else {
				date = convertdate(postedDate);
				mMonth = date.getMonth();
				mMonth = mMonth + 1;
				mYear = date.getYear();
				mYear = mYear + 1900;
				mDate = date.getDate();
				mHour = date.getHours();
				mMinute = date.getMinutes();
				mSecond = date.getSeconds();

				Calendar c = Calendar.getInstance();
				curMonth = c.get(Calendar.MONTH);
				curMonth = curMonth + 1;
				curYear = c.get(Calendar.YEAR);
				curDate = c.get(Calendar.DATE);
				curHour = c.get(Calendar.HOUR_OF_DAY);
				curMinute = c.get(Calendar.MINUTE);
				curSecond = c.get(Calendar.SECOND);
				System.out.println("current " + curMonth + " post Month " + mMonth);
				System.out.println("current " + curYear + " post year  " + mYear);

				int pMonth = 0, pYear = 0, pDate = 0, pHour = 0, pMinute = 0, pSecond = 0;
				pMonth = (curMonth >= mMonth) ? curMonth - mMonth : (12-(mMonth
						- curMonth));
				System.out.println("pMonth " + pMonth);
				pDate = (curDate >= mDate) ? (curDate - mDate) : (mDate - curDate);
				pYear = (curYear >= mYear) ? (curYear - mYear) : (mYear - curYear);
				Log.v("pYear", " " + pYear);
				pHour = (curHour >= mHour) ? (curHour - mHour) : (mHour - curHour);
				pMinute = (curMinute >= mMinute) ? (curMinute - mMinute)
						: (mMinute - curMinute);
				pSecond = (curSecond >= mSecond) ? (curSecond - mSecond)
						: (mSecond - curSecond);
				getDated = imagePostedOn(pMonth, pDate, 0, pHour, pMinute, pSecond)
						+ " ago";
				if(getDated==null){
					
				}
			}
			return getDated;

		}
	
	public static Date convertdate(String stringDate) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date convertDate;
		try {
			convertDate = sdf.parse(stringDate);
			return convertDate;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public static String imagePostedOn(int month, int date, int year, int hour,
			int minute, int second) {
		String orignalTime = null;
		if (year > 0) {
			if (month == 1) {
				if (curDate >= mDate) {
					orignalTime = "" + year + " Year";
				} else {
					orignalTime = "" + (31 - date) + " Day";
				}
				return orignalTime;
			}

		} else if (month > 0) {
			switch (month) {
			case 1: {
				if (month == 1) {
					if (curDate >= mDate) {
						orignalTime = "" + month + " Month";
					} else {
						orignalTime = "" + (31 - date) + " Day";
					}
					return orignalTime;

				} else {
					orignalTime = "" + month + " Month";
				}
				return orignalTime;
			}
			case 2: {
				if (month == 2) {
					if (curDate >= mDate) {
						orignalTime = "" + month + " Month";
					} else {
						orignalTime = "" + (28 - date) + " Day";
					}
					return orignalTime;
				} else {
					orignalTime = "" + month + " Month";
				}
				return orignalTime;
			}
			case 3: {
				if (month == 3) {
					if (curDate >= mDate) {
						orignalTime = "" + month + " Month";
					} else {
						orignalTime = "" + (31 - date) + " Day";
					}
					return orignalTime;
				} else {
					orignalTime = "" + month + " Month";
				}
				return orignalTime;
			}
			case 4: {
				if (month == 4) {
					if (curDate >= mDate) {
						orignalTime = "" + month + " Month";
					} else {
						orignalTime = "" + (30 - date) + " Day";
					}
					return orignalTime;
				} else {
					orignalTime = "" + month + " Month";
				}
				return orignalTime;
			}
			case 5: {
				if (month == 5) {
					if (curDate >= mDate) {
						orignalTime = "" + month + " Month";
					} else {
						orignalTime = "" + (31 - date) + " Day";
					}
					return orignalTime;
				} else {
					orignalTime = "" + month + " Month";
				}
				return orignalTime;
			}
			case 6: {
				if (month == 6) {
					if (curDate >= mDate) {
						orignalTime = "" + month + " Month";
					} else {
						orignalTime = "" + (30 - date) + " Day";
					}
					return orignalTime;
				} else {
					orignalTime = "" + month + " Month";
				}
				return orignalTime;
			}
			case 7: {
				if (month == 7) {
					if (curDate >= mDate) {
						orignalTime = "" + month + " Month";
					} else {
						orignalTime = "" + (31 - date) + " Day";
					}
					return orignalTime;
				} else {
					orignalTime = "" + month + " Month";
				}
				return orignalTime;
			}
			case 8: {
				if (month == 8) {
					if (curDate >= mDate) {
						orignalTime = "" + month + " Month";
					} else {
						orignalTime = "" + (31 - date) + " Day";
					}
					return orignalTime;
				} else {
					orignalTime = "" + month + " Month";
				}
				return orignalTime;
			}
			case 9: {
				if (month == 9) {
					if (curDate >= mDate) {
						orignalTime = "" + month + " Month";
					} else {
						orignalTime = "" + (30 - date) + " Day";
					}
					return orignalTime;
				} else {
					orignalTime = "" + month + " Month";
				}
				return orignalTime;
			}
			case 10: {
				if (month == 10) {
					if (curDate >= mDate) {
						orignalTime = "" + month + " Month";
					} else {
						orignalTime = "" + (31 - date) + " Day";
					}
					return orignalTime;
				} else {
					orignalTime = "" + month + " Month";
				}
				return orignalTime;
			}
			case 11: {
				if (month == 11) {
					if (curDate >= mDate) {
						orignalTime = "" + month + " Month";
					} else {
						orignalTime = "" + (30 - date) + " Day";
					}
					return orignalTime;
				}
				break;
			}
			case 12: {
				if (month == 12) {
					if (curDate >= mDate) {
						orignalTime = "" + month + " Month";
					} else {
						orignalTime = "" + (31 - date) + " Day";
					}
					return orignalTime;
				} else {
					orignalTime = "" + month + " Month";
				}
				return orignalTime;
			}

			}

		} else if (date > 0) {
			orignalTime = "" + date + " Day";
			return orignalTime;
		} else if (hour > 0) {
			orignalTime = "" + hour + " Hours";
			return orignalTime;
		} else if (minute > 0) {
			orignalTime = "" + minute + " Minutes";
			return orignalTime;
		} else if (second >= 0) {
			orignalTime = "" + second + " Seconds";
			return orignalTime;
		}

		return orignalTime;

	}
	
	 public static  JSONObject getLocationInfo(String address) {
	        StringBuilder stringBuilder = new StringBuilder();
	        try {

	        address = address.replaceAll(" ","%20");    

	        HttpPost httppost = new HttpPost("http://maps.google.com/maps/api/geocode/json?address=" + address + "&sensor=false");
	        HttpClient client = new DefaultHttpClient();
	        HttpResponse response;
	        stringBuilder = new StringBuilder();


	            response = client.execute(httppost);
	            HttpEntity entity = response.getEntity();
	            InputStream stream = entity.getContent();
	            int b;
	            while ((b = stream.read()) != -1) {
	                stringBuilder.append((char) b);
	            }
	        } catch (ClientProtocolException e) {
	        } catch (IOException e) {
	        }

	        JSONObject jsonObject = new JSONObject();
	        try {
	            jsonObject = new JSONObject(stringBuilder.toString());
	        } catch (JSONException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }

	        return jsonObject;
	    } 

} // class ends here
