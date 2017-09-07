package com.example.marshi.coordinatorlayouttext;

import com.github.ksoichiro.android.observablescrollview.ObservableWebView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

public class SuperObservableWebView extends ObservableWebView {

    public SuperObservableWebView(Context context) {
        super(context);
    }

    public SuperObservableWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SuperObservableWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
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

}
