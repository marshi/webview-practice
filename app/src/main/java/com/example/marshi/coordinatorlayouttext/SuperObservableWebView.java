package com.example.marshi.coordinatorlayouttext;

import com.github.ksoichiro.android.observablescrollview.ObservableWebView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.Size;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

public class SuperObservableWebView extends ObservableWebView implements NestedScrollingChild {

    private final NestedScrollingChildHelper childHelper;

    public SuperObservableWebView(Context context) {
        super(context);
        childHelper = new NestedScrollingChildHelper(this);
    }

    public SuperObservableWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        childHelper = new NestedScrollingChildHelper(this);
    }

    public SuperObservableWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        childHelper = new NestedScrollingChildHelper(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i("webview", "start dispatch " + ev.getActionMasked());
        boolean b = super.dispatchTouchEvent(ev);
        Log.i("webview", "end dispatch " + b + " " + ev.getActionMasked());
        return b;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i("webview", "start intercept " + ev.getActionMasked());
        boolean b = super.onInterceptTouchEvent(ev);
        Log.i("webview", "end intercept " + b + " " + ev.getActionMasked());
        return b;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.i("webview", "start touch " + ev.getActionMasked());
        boolean b = super.onTouchEvent(ev);
        Log.i("viewpager", "touch in setontouch");
        return b;
    }

    //NestedScrollingChild

    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        childHelper.setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return childHelper.isNestedScrollingEnabled();
    }

    @Override
    public boolean startNestedScroll(int axes) {
        return childHelper.startNestedScroll(axes);
    }

    @Override
    public void stopNestedScroll() {
        childHelper.stopNestedScroll();
    }

    @Override
    public boolean hasNestedScrollingParent() {
        return childHelper.hasNestedScrollingParent();
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed,
            int dyUnconsumed, @Nullable @Size(value = 2) int[] offsetInWindow) {
        return childHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed,
                offsetInWindow);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy,
            @Nullable @Size(value = 2) int[] consumed,
            @Nullable @Size(value = 2) int[] offsetInWindow) {
        return childHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return childHelper.dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return childHelper.dispatchNestedPreFling(velocityX, velocityY);
    }

}
