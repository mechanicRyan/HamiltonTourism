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