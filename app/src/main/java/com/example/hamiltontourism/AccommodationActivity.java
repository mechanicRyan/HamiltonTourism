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
		//  If switch to the shopping cart activity, it is necessary to pass the email to send e-mail after finished checking out
		Intent intent = new Intent(AccommodationActivity.this, ShoppingCartActivity.class);
		intent.putExtra("email", EMail);
		startActivity(intent);
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

		DatePickerDialog dpDialog = new DatePickerDialog(AccommodationActivity.this, OnDateSet, mCheckIn.get(Calendar.YEAR), mCheckIn.get(Calendar.MONTH), mCheckIn.get(Calendar.DAY_OF_MONTH));
		dpDialog.setCancelable(false);
		dpDialog.show();
	}

	DatePickerDialog.OnDateSetListener OnDateSet = new DatePickerDialog.OnDateSetListener()
	{
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
		{
			if (mCursor == mCheckIn)
			{
				mCheckIn.set(Calendar.YEAR, year);
				mCheckIn.set(Calendar.MONTH, monthOfYear);
				mCheckIn.set(Calendar.DAY_OF_MONTH, dayOfMonth);

				mCursor = mCheckOut;

				DatePickerDialog dpDialog = new DatePickerDialog(AccommodationActivity.this, OnDateSet, mCheckOut.get(Calendar.YEAR), mCheckOut.get(Calendar.MONTH), mCheckOut.get(Calendar.DAY_OF_MONTH));
				dpDialog.setCancelable(false);
				//  Limit date to be later than the checkin date
				dpDialog.getDatePicker().setMinDate(mCheckIn.getTimeInMillis() + 3600 * 24 * 1000);
				dpDialog.show();
			}
			else
			{
				mCheckOut.set(Calendar.YEAR, year);
				mCheckOut.set(Calendar.MONTH, monthOfYear);
				mCheckOut.set(Calendar.DAY_OF_MONTH, dayOfMonth);

				try
				{
					Order order = new Order(mItem, mCheckIn, mCheckOut);

					JSONArray jsonArray;

					//  Read existing order information
					String persistOrders = StaticFuncs.ReadSharedPref(getBaseContext(), "Orders");

					if (persistOrders.isEmpty())
					{
						jsonArray = new JSONArray();
					}
					else
					{
						jsonArray = new JSONArray(persistOrders);
					}


					jsonArray.put(order);
					//  Write order information to the local data persistence
					persistOrders = jsonArray.toString();
					StaticFuncs.WriteSharedPref(getBaseContext(), "Orders", persistOrders);

					long days = (mCheckOut.getTimeInMillis() - mCheckIn.getTimeInMillis()) / (3600 * 1000 * 24);

					Toast.makeText(getBaseContext(), String.format(Locale.UK, "You have booked %s for %d days.", mItem.mName, days), Toast.LENGTH_LONG).show();
				}
				catch (JSONException e)
				{
					e.printStackTrace();
				}
			}
		}
	};
}