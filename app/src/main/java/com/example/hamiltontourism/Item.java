package com.example.hamiltontourism;

import org.json.JSONException;
import org.json.JSONObject;

public class Item
{
	int iDrawable;
	String mName;
	String mDescription;
	double dPrice;

	Item(){}

	//  Constructor takes an json string as argument to construct the instance
	Item(String jstring)
	{
		try
		{
			JSONObject jobject = new JSONObject(jstring);

			iDrawable = jobject.getInt("drawable");
			mName = jobject.getString("name");
			mDescription = jobject.getString("description");
			dPrice = jobject.getDouble("price");
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
	}
}