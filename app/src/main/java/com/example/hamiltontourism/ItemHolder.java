package com.example.hamiltontourism;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View.OnClickListener;

import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import java.util.Locale;

class ItemHolder extends ViewHolder
{
	private Context mContext;

	private ImageView mImage;
	private TextView mName;
	private TextView mDescription;
	private TextView mPrice;
	private AppCompatButton mButton;

	ItemHolder(View v)
	{
		super(v);

		mContext = v.getContext();
		mImage = v.findViewById(R.id.image);
		mName = v.findViewById(R.id.name);
		mDescription = v.findViewById(R.id.description);
		mPrice = v.findViewById(R.id.price);
		mButton = v.findViewById(R.id.book);
	}

	void setAs(Item item)
	{
		mImage.setImageDrawable(mContext.getDrawable(item.iDrawable));
		mName.setText(item.mName);
		mDescription.setText(item.mDescription);
		mPrice.setText(String.format(Locale.UK, "$%.2f per night", item.dPrice));
	}

	void setOnClickListener(OnClickListener listener)
	{
		mButton.setOnClickListener(listener);
	}
}