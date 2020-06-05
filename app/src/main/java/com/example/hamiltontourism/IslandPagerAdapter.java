package com.example.hamiltontourism;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class IslandPagerAdapter extends PagerAdapter
{
	private Context mContext;
	private List<Integer> mViewList;

	IslandPagerAdapter(Context mContext, List<Integer> mViewList)
	{
		this.mContext = mContext;
		this.mViewList = mViewList;
	}

	@Override
	public int getCount()
	{
		return mViewList.size();
	}

	@Override
	public boolean isViewFromObject(@NonNull View view, @NonNull Object object)
	{
		return view == object;
	}

	@NonNull
	@Override
	public Object instantiateItem(ViewGroup container, int position)
	{
		ImageView iv = new ImageView(mContext);
		iv.setScaleType(ImageView.ScaleType.FIT_XY);
		iv.setImageResource(mViewList.get(position));
		container.addView(iv);
		return iv;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object)
	{
		container.removeView((View) object);
	}
}