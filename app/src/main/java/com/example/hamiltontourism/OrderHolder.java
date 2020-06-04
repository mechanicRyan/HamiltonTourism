package com.example.hamiltontourism;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class OrderHolder extends RecyclerView.ViewHolder
{
	private Context mContext;

	private ImageView mImage;
	private TextView mName;
	private TextView mPrice;
	private TextView mCheckIn;
	private TextView mCheckOut;
	private TextView mAmount;

	OrderHolder(View v)
	{
		super(v);

		mContext = v.getContext();
		mImage = v.findViewById(R.id.image);
		mName = v.findViewById(R.id.name);
		mPrice = v.findViewById(R.id.price);
		mCheckIn = v.findViewById(R.id.checkin);
		mCheckOut = v.findViewById(R.id.checkout);
		mAmount = v.findViewById(R.id.amount);
	}

	void setAs(Order order)
	{
		mImage.setImageDrawable(mContext.getDrawable(order.iDrawable));
		mName.setText(order.mName);
		mPrice.setText(String.format(Locale.UK, "Unit Price: $%.2f", order.dPrice));
		mCheckIn.setText(new SimpleDateFormat("yyyy/MM/dd", Locale.UK).format(new Date(order.mCheckIn)));
		mCheckOut.setText(new SimpleDateFormat("yyyy/MM/dd", Locale.UK).format(new Date(order.mCheckOut)));
		mAmount.setText(String.format(Locale.UK, "Amount: $%.2f", order.dPrice * ((order.mCheckOut - order.mCheckIn) / (3600 * 1000 * 24))));
	}
}