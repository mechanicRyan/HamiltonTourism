package com.example.hamiltontourism;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AccommodationActivity extends AppCompatActivity implements ItemInterface
{
	private List<Item> mItems;

	private Calendar mCursor;
	private Calendar mCheckIn;
	private Calendar mCheckOut;

	private Item mItem;

	private String EMail;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_accommodation);

		//  Acquire the e-mail address
		Intent intent = getIntent();
		EMail = intent.getStringExtra("email");
	}

	@Override
	protected void onStart()
	{
		super.onStart();

		//  Prepare data for RecyclerView
		initData();
		//  Init the RecyclerView itself
		initRecyclerView();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		//  Init the menu bar at the top-right corner of the screen
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.checkoutbar, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item)
	{
		return super.onOptionsItemSelected(item);
	}

	private void initData()
	{
		try
		{
			mItems = new ArrayList<>();

			//  Read locally persisted data
			String ordersJson = StaticFuncs.ReadSharedPref(getBaseContext(), "Accommodation");

			//  If the string is empty, need to initialize the available accommodation list
			//  If not empty, just initialize data
			//  Each item is represented by a json string, a JSON string
			//  The persisted data is actually an array of json string
			if (!ordersJson.isEmpty())
			{
				JSONArray jlist = new JSONArray(ordersJson);

				for (int i = 0; i < jlist.length(); i++)
				{
					mItems.add(new Item(jlist.getString(i)));
				}
			}
			else
			{
				mItems = new ArrayList<>();

				Item item0 = new Item();
				item0.iDrawable = R.mipmap.island2;
				item0.mName = "Qualia";
				item0.mDescription = "Qualia Description";
				item0.dPrice = 300;

				Item item1 = new Item();
				item1.iDrawable = R.mipmap.island1;
				item1.mName = "Melbourne";
				item1.mDescription = "Melbourne Description";
				item1.dPrice = 500;

				mItems.add(item0);
				mItems.add(item1);
			}
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
	}

	private void initRecyclerView()
	{
		//  Reused code from NewsApp
		RecyclerView mRecyclerView = findViewById(R.id.recyclerView);
		LinearLayoutManager layoutManager = new LinearLayoutManager(this);
		mRecyclerView.setLayoutManager(layoutManager);
		layoutManager.setOrientation(RecyclerView.VERTICAL);
		mRecyclerView.setAdapter(new ItemAdapter(mItems, this));
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());
	}

	@Override
	public void onItem(int i)
	{
		mItem = mItems.get(i);

		mCheckIn = Calendar.getInstance();
		mCheckOut = Calendar.getInstance();

		mCursor = mCheckIn;
	}
}