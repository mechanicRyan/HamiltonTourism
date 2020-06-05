package com.example.hamiltontourism;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Order implements Serializable
{
	int iDrawable;
	String mName;
	double dPrice;
	long mCheckIn;
	long mCheckOut;

	Order(Item mItem, Calendar mCheckIn, Calendar mCheckOut)
	{
		this.iDrawable = mItem.iDrawable;
		this.mName = mItem.mName;
		this.dPrice = mItem.dPrice;
		this.mCheckIn = mCheckIn.getTimeInMillis();
		this.mCheckOut = mCheckOut.getTimeInMillis();
	}

	Order(String jsonString)
	{
		try
		{
			JSONObject jsonObject = new JSONObject(jsonString);

			iDrawable = jsonObject.getInt("drawable");
			mName = jsonObject.getString("name");
			dPrice = jsonObject.getDouble("price");
			mCheckIn = jsonObject.getLong("checkin");
			mCheckOut = jsonObject.getLong("checkout");
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
	}

	@NonNull
	@Override
	public String toString()
	{
		try
		{
			JSONObject json = new JSONObject();
			json.put("drawable", iDrawable);
			json.put("name", mName);
			json.put("price", dPrice);
			json.put("checkin", mCheckIn);
			json.put("checkout", mCheckOut);

			return json.toString();
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}

		return "";
	}
}