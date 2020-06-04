package com.example.hamiltontourism;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderHolder>
{
	List<Order> mItems;

	OrderAdapter(List<Order> mItems)
	{
		this.mItems = mItems;
	}

	@Override
	public void onBindViewHolder(OrderHolder holder, final int position)
	{
		holder.setAs(mItems.get(position));
	}

	@Override
	public int getItemCount()
	{
		return mItems.size();
	}

	@NonNull
	@Override
	public OrderHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		return new OrderHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_order, parent, false));
	}
}