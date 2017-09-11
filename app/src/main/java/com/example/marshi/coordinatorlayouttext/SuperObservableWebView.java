package com.example.marshi.coordinatorlayouttext;

import com.github.ksoichiro.android.observablescrollview.ObservableWebView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.Size;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

public class SuperObservableWebView extends ObservableWebView implements NestedScrollingChild {

    private NestedScrollingChildHelper childHelper;

    private int lastTouchY;
    private int initialTouchY;
    private int touchSlop;
    private boolean isBeingDragged;

    private int nestedYOffset;
    private final int[] scrollConsumed = new int[2];
    private final int[] scrollOffset = new int[2];

    public SuperObservableWebView(Context context) {
        super(context);
        init();
    }

    public SuperObservableWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SuperObservableWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i("webview", "-----------------------");
        Log.i("webview", "start dispatch " + ev.getActionMasked());
        boolean b = super.dispatchTouchEvent(ev);
        Log.i("webview", "end dispatch " + b + " " + ev.getActionMasked());
        Log.i("webview", "-----------------------");
        return b;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = MotionEventCompat.getActionMasked(ev);
        switch(action) {
            case MotionEvent.ACTION_DOWN:
                initialTouchY = lastTouchY = (int) ev.getY();
                startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL);
                isBeingDragged = true;
                break;
            case MotionEvent.ACTION_MOVE:
                final int y = (int)ev.getY();
                final int yDiff = Math.abs(y - lastTouchY);
                if (yDiff > touchSlop) {
                    lastTouchY = y;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                stopNestedScroll();
                break;
        }
//        Log.i("webview", "start intercept " + ev.getActionMasked());
//        boolean b = super.onInterceptTouchEvent(ev);
//        Log.i("webview", "end intercept " + b + " " + ev.getActionMasked());
//        return b;
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        MotionEvent obtainedEv = MotionEvent.obtain(ev);
        final int actionMasked = MotionEventCompat.getActionMasked(ev);
        if (actionMasked == MotionEvent.ACTION_DOWN) {
            nestedYOffset = 0;
        }
        obtainedEv.offsetLocation(0, nestedYOffset);
        switch(actionMasked) {
            case MotionEvent.ACTION_DOWN:
                initialTouchY = lastTouchY = (int)ev.getY();
                startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL);
                break;
            case MotionEvent.ACTION_MOVE:
                final int y = (int)ev.getY();
                int yDiff = lastTouchY - y;
                if (dispatchNestedPreScroll(0, yDiff, scrollConsumed, scrollOffset)) {
                    yDiff -= scrollConsumed[1];
                    obtainedEv.offsetLocation(0, scrollOffset[1]);
                }
                if (isBeingDragged) {
                    lastTouchY = y - scrollOffset[1];
                    //ここのconsumedYには何を渡せば？
                    if(dispatchNestedScroll(0, 0, 0, yDiff, scrollOffset)) {
                        lastTouchY -= scrollOffset[1];
                        obtainedEv.offsetLocation(0, scrollOffset[1]);
                        nestedYOffset += scrollOffset[1];
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                isBeingDragged = false;
                stopNestedScroll();
                break;
        }
        obtainedEv.recycle();
        return super.onTouchEvent(ev);
//        Log.i("webview", "start touch " + ev.getActionMasked());
//        boolean b = super.onTouchEvent(ev);
//        Log.i("viewpager", "touch in setontouch");
//        return b;

    }

    private void init() {
        this.childHelper = new NestedScrollingChildHelper(this);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(getContext());
        this.touchSlop = viewConfiguration.getScaledTouchSlop();
        setNestedScrollingEnabled(true);

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
