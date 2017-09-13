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

import java.text.MessageFormat;

public class SuperObservableWebView extends ObservableWebView implements NestedScrollingChild {

    private NestedScrollingChildHelper childHelper;

    private int lastTouchY;
    private int touchSlop;
    private boolean isBeingDragged;

    private int nestedYOffset;
    private final int[] scrollConsumed = new int[2];
    private final int[] scrollOffset = new int[2];

    private boolean isCollapsed = false;

    private int scrollingMode = SCROLLING_NOTHING_MODE;
    private final static int SCROLLING_NOTHING_MODE = 0;
    private final static int SCROLLING_APPBAR_MODE = 1;
    private final static int SCROLLING_WEBVIEW_MODE = 2;


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
        Log.i("webview", "start intercept " + ev.getActionMasked());
        final int action = MotionEventCompat.getActionMasked(ev);
        switch(action) {
            case MotionEvent.ACTION_DOWN:
                lastTouchY = (int) ev.getY();
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
        boolean b = super.onInterceptTouchEvent(ev);
        Log.i("webview", "end intercept " + b + " " + ev.getActionMasked());
        return b;
//        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.i("webview", "start touch " + ev.getActionMasked());
        final int actionMasked = MotionEventCompat.getActionMasked(ev);
        if (actionMasked == MotionEvent.ACTION_DOWN) {
            nestedYOffset = 0;
        }
        switch(actionMasked) {
            case MotionEvent.ACTION_DOWN:
                lastTouchY = (int)ev.getY();
                startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL);
                break;
            case MotionEvent.ACTION_MOVE:
                final int y = (int)ev.getY();
                int yDiff = lastTouchY - y;
                if (dispatchNestedPreScroll(0, yDiff, scrollConsumed, scrollOffset)) {
                    yDiff -= scrollConsumed[1];
                    Log.i("webview", MessageFormat.format("pre yDiff = {0} scrollConsumed y {1}, scrollOffset y {2}", yDiff, scrollConsumed[1], scrollOffset[1]));
                }
                if (isBeingDragged) {
                    lastTouchY = y - scrollOffset[1];
                    if (scrollingMode == SCROLLING_WEBVIEW_MODE) {
                        yDiff = 0;
                    }
                    if(dispatchNestedScroll(0, 0, 0, yDiff, scrollOffset)) {
                        Log.i("webview", MessageFormat.format("    yDiff = {0}, scrollOffset y {2}", yDiff, scrollOffset[1]));
                        lastTouchY -= scrollOffset[1];
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
        Log.i("webview", MessageFormat.format("ev {0}, {1}, {2}", ev.getY(), ev.getYPrecision(), ev.getRawY()));
        StringBuilder builder = new StringBuilder("");
        for (int i = 0; i < ev.getHistorySize(); i++) {
            builder.append(ev.getHistoricalY(i) + " ");
        }
        Log.i("webview", "history " + builder.toString());
        if (actionMasked == MotionEvent.ACTION_MOVE) {
            if (this.isCollapsed) {
                Log.i("webview", "ev collapsed");
                boolean b = super.onTouchEvent(ev);
                Log.i("webview", MessageFormat.format("end touch {0} {1}", ev.getActionMasked(), b));
                return b;
            } else {
                return true;
            }
        }
        return super.onTouchEvent(ev);
    }

    private void init() {
        this.childHelper = new NestedScrollingChildHelper(this);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(getContext());
        this.touchSlop = viewConfiguration.getScaledTouchSlop();
        setNestedScrollingEnabled(true);

    }


    @Override
    public void flingScroll(int vx, int vy) {
        Log.i("webview", MessageFormat.format("vx = {0}, vy = {1}", vx, vy));
        super.flingScroll(vx, vy);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        Log.i("webview", MessageFormat.format("l = {0}, t = {1}, oldl = {2}, oldt = {3}", l, t, oldl, oldt));
        super.onScrollChanged(l, t, oldl, oldt);
    }

    public void onChangeCollapseToolbar(boolean b, int dy) {
        this.isCollapsed = b;
        boolean W = getScrollY() == 0;
        boolean A = b;
        boolean S = 0 < dy;
        if (W && !(A && S)) {
            scrollingMode = SCROLLING_APPBAR_MODE;
            Log.i("webview", MessageFormat.format("scrollingMode webview {0} W={1} A={2} S={3}", scrollingMode, W, A, S));
        } else if (A && (S && W || !S && !W)) {
            scrollingMode = SCROLLING_WEBVIEW_MODE;
            Log.i("webview", MessageFormat.format("scrollingMode appbar {0} W={1} A={2} S={3}", scrollingMode, W, A, S));
        } else {
            scrollingMode = SCROLLING_NOTHING_MODE;
            Log.i("webview", MessageFormat.format("scrollingMode can not run this code. W={0} A={1} S={2}", W, A, S));
        }

//        Log.i("webview", MessageFormat.format("toolbar collapsing is changed. {0} {1}", b, dy));
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
