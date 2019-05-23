package com.fabricio.challenge.view.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class ViewAdapter extends PagerAdapter {

    private List<View> views = new ArrayList<>();
    private Context context;

    public ViewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = views.get(position);
        container.addView(view);
        return view;
    }

    @Override
    public int getItemPosition(Object object) {
        for(int index = 0 ; index < getCount() ; index++){
            if((View)object == views.get(index)) {
                return index;
            }
        }
        return POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "";
    }


    public View getView(int position) {
        return views.get(position);
    }

    /**
     * Add new view to the pager
     * @param view
     */
    public void addView(View view){
        views.add(view);
        notifyDataSetChanged();
    }
}
