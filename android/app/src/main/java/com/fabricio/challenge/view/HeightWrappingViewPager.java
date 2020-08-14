package com.fabricio.challenge.view;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Custom ViewPager component that have 3 features:
 * 1 - Automatically adjust the height of the component based on the children added;
 * 2 - Enable/Disable scrolling with touch drag events;
 * 3 - Change the paging orientation to vertical instead horizontal (default);
 * @author Fabricio Godoi
 */
public class HeightWrappingViewPager extends ViewPager {

    private boolean isPagingEnabled = true;
    private boolean scrollVertical = false;

    public HeightWrappingViewPager(Context context) {
        super(context);
    }

    public HeightWrappingViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        // Unspecified means that the ViewPager is in a ScrollView WRAP_CONTENT.
        // At Most means that the ViewPager is not in a ScrollView WRAP_CONTENT.
        if (mode == MeasureSpec.UNSPECIFIED || mode == MeasureSpec.AT_MOST) {
            // super has to be called in the beginning so the child views can be initialized.
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            int height = 0;
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                int h = child.getMeasuredHeight();
                if (h > height) height = h;
            }
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        }
        // super has to be called again so the new specs are treated as exact measurements
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(scrollVertical) {
            return this.isPagingEnabled && super.onTouchEvent(swapXY(event));
        }
        else {
            return this.isPagingEnabled && super.onTouchEvent(event);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if(scrollVertical) {
            boolean intercepted = super.onInterceptTouchEvent(swapXY(event));
            swapXY(event); // return touch coordinates to original reference frame for any child views
            return this.isPagingEnabled && intercepted;
        }
        else {
            return this.isPagingEnabled && super.onInterceptTouchEvent(event);
        }
    }

    public void setPagingEnabled(boolean b) {
        this.isPagingEnabled = b;
    }

    /**
     * Set the scroll view to be pageable vertically instead of horizontally (default)
     * @param b true to enable vertical scroll, false to disable vertical scrolling
     */
    public void setScrollVertical(boolean b) {
        scrollVertical = b;
        // The majority of the magic happens here
        setPageTransformer(true, new VerticalPageTransformer());
        // The easiest way to get rid of the overscroll drawing that happens on the left and right
//        setOverScrollMode(OVER_SCROLL_NEVER);
    }

    /**
     * This handles if the ViewPager will handler motion from left/right (horizontal) or up/down (vertical)
     */
    private class VerticalPageTransformer implements ViewPager.PageTransformer {

        @Override
        public void transformPage(View view, float position) {

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 1) { // [-1,1]
                view.setAlpha(1);

                // Counteract the default slide transition
                view.setTranslationX(view.getWidth() * -position);

                //set Y position to swipe in from top
                float yPosition = position * view.getHeight();
                view.setTranslationY(yPosition);

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }

    /**
     * Swaps the X and Y coordinates of your touch event for vertical scrolling.
     */
    private MotionEvent swapXY(MotionEvent ev) {
        float width = getWidth();
        float height = getHeight();

        float newX = (ev.getY() / height) * width;
        float newY = (ev.getX() / width) * height;

        ev.setLocation(newX, newY);

        return ev;
    }

}