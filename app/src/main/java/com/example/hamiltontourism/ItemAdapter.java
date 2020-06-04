package com.example.hamiltontourism;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemHolder>
{
	List<Item> mItems;
	private ItemInterface mInterface;

	ItemAdapter(List<Item> mItems, ItemInterface mClick)
	{
		this.mItems = mItems;
		this.mInterface = mClick;
	}

	@Override
	public void onBindViewHolder(ItemHolder holder, final int position)
	{
		holder.setAs(mItems.get(position));

		holder.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (mInterface != null)
					mInterface.onItem(position);
			}
		});
	}

	@Override
	public int getItemCount()
	{
		return mItems.size();
	}

	@NonNull
	@Override
	public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		return new ItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item, parent, false));
	}
}