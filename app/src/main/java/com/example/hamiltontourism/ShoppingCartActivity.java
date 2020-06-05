package com.example.hamiltontourism;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ShoppingCartActivity extends AppCompatActivity
{
	private List<Order> mItems;
	private String EMail;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shopping_cart);
		setTitle("Check Out");

		Intent intent = getIntent();
		EMail = intent.getStringExtra("email");

		initData();
		initRecyclerView();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.checkoutbar, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		String persistOrders = StaticFuncs.ReadSharedPref(getBaseContext(), "Orders");

		if (!persistOrders.isEmpty())
		{
			try
			{
				//  The user has made payment. The application sends detailed information of the order to the user's e-mail address
				JSONArray jsonArray = new JSONArray(persistOrders);
				StringBuilder sb = new StringBuilder();
				sb.append("You have booked the following items:\n");
				for (int i = 0; i < jsonArray.length(); i++)
				{
					Order order = new Order(jsonArray.getString(i));
					sb.append(String.format(Locale.UK, "Item %02d\n", i + 1));
					sb.append("==============================================\n");
					sb.append(String.format(Locale.UK, "Name: %s\n", order.mName));
					sb.append(String.format(Locale.UK, "From: %s\n", new SimpleDateFormat("yyyy/MM/dd", Locale.UK).format(new Date(order.mCheckIn))));
					sb.append(String.format(Locale.UK, "To: %s\n", new SimpleDateFormat("yyyy/MM/dd", Locale.UK).format(new Date(order.mCheckOut))));
					sb.append(String.format(Locale.UK, "Unit Price: %.2f\n", order.dPrice));
					sb.append(String.format(Locale.UK, "Quantity: %d\n", ((order.mCheckOut - order.mCheckIn) / (3600 * 1000 * 24))));
					sb.append(String.format(Locale.UK, "Amount: %.2f\n", order.dPrice * ((order.mCheckOut - order.mCheckIn) / (3600 * 1000 * 24))));
					sb.append("\n");
				}
				new MailT(EMail, "Order Info", sb.toString()).start();
			}
			catch (JSONException e)
			{
				e.printStackTrace();
			}

		}

		//  Clear un-paid orders
		StaticFuncs.WriteSharedPref(getBaseContext(), "Orders", "");
		finish();
		return super.onOptionsItemSelected(item);
	}

	private void initData()
	{
		try
		{
			mItems = new ArrayList<>();
			String ordersJson = StaticFuncs.ReadSharedPref(getBaseContext(), "Orders");

			if (!ordersJson.isEmpty())
			{
				JSONArray jsonArray = new JSONArray(ordersJson);

				for (int i = 0; i < jsonArray.length(); i++)
				{
					mItems.add(new Order(jsonArray.getString(i)));
				}
			}
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
	}

	private void initRecyclerView()
	{
		RecyclerView mRecyclerView = findViewById(R.id.recyclerView);
		LinearLayoutManager layoutManager = new LinearLayoutManager(this);
		mRecyclerView.setLayoutManager(layoutManager);
		layoutManager.setOrientation(RecyclerView.VERTICAL);
		mRecyclerView.setAdapter(new OrderAdapter(mItems));
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());
	}
}