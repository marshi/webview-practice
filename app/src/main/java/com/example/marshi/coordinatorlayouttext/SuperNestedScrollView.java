package com.example.marshi.coordinatorlayouttext;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 *
 */

public class SuperNestedScrollView extends NestedScrollView {

    private boolean isDisallowInterceptTouchEvent = false;

    public SuperNestedScrollView(Context context) {
        super(context);
    }

    public SuperNestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SuperNestedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setDisallowInterceptTouchEvent(boolean disallowInterceptTouchEvent) {
        isDisallowInterceptTouchEvent = disallowInterceptTouchEvent;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i("nestedscroll", "start dispatch " + ev.getActionMasked());
        if (isDisallowInterceptTouchEvent) {
            requestDisallowInterceptTouchEvent(true);
        }
        boolean b = super.dispatchTouchEvent(ev);
        Log.i("nestedscroll", "end dispatch " + b + " " + ev.getActionMasked());
        return b;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i("nestedscroll", "start intercept " + ev.getActionMasked());
        boolean b = super.onInterceptTouchEvent(ev);
        Log.i("nestedscroll", "end intercept " + b + " " + ev.getActionMasked());
        return b;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.i("nestedscroll", "start touch " + ev.getActionMasked());
        boolean b = super.onTouchEvent(ev);
        Log.i("nestedscroll", "end touch " + b + " " + ev.getActionMasked());
        return b;
    }

    @Override
    public void onStopNestedScroll(View target) {
        Log.i("superNestedScroll", "on stop nested scroll");
        super.onStopNestedScroll(target);
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        Log.i("superNestedScroll", "on start nested scroll");
        return super.onStartNestedScroll(child, target, nestedScrollAxes);
    }
}
