package com.example.marshi.coordinatorlayouttext;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 *
 */

@CoordinatorLayout.DefaultBehavior(SuperViewPager.Behavior.class)
public class SuperViewPager extends ViewPager {

    public SuperViewPager(Context context) {
        super(context);
    }

    public SuperViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean b = super.onInterceptTouchEvent(ev);
        return b;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        boolean b = super.onTouchEvent(ev);
        return b;
    }

    public static class Behavior extends CoordinatorLayout.Behavior<SuperViewPager> {

        @Override
        public boolean layoutDependsOn(
                CoordinatorLayout parent,
                SuperViewPager child,
                View dependency
        ) {
            return super.layoutDependsOn(parent, child, dependency);
        }

        @Override
        public boolean onDependentViewChanged(
                CoordinatorLayout parent,
                SuperViewPager child,
                View dependency
        ) {
            if (dependency instanceof AppBarLayout) {
                AppBarLayout barLayout = (AppBarLayout)dependency;
            }
            return false;
        }
    }

}
