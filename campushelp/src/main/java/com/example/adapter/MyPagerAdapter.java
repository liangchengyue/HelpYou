package com.example.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 90603 on 2017/5/11.
 */

public class MyPagerAdapter extends PagerAdapter {
    private List<View> allViews = new ArrayList<View>();
    public  MyPagerAdapter(List<View> allViews){
        this.allViews = allViews;
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(allViews.get(position));
        return allViews.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeViewAt(position);
    }

    @Override
    public int getCount() {
        return allViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
}
