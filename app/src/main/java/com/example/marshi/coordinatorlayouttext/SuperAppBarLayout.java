package com.example.marshi.coordinatorlayouttext;

import android.content.Context;
import android.os.Parcelable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 *
 */
@CoordinatorLayout.DefaultBehavior(SuperAppBarLayout.SuperBehavior.class)
public class SuperAppBarLayout extends AppBarLayout {

    public SuperAppBarLayout(Context context) {
        super(context);
    }

    public SuperAppBarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public static class SuperBehavior extends AppBarLayout.Behavior {

        @Override
        public boolean onDependentViewChanged(CoordinatorLayout parent, AppBarLayout child,
                View dependency) {
            Log.i("behavior", "onDependentViewChanged");
            return super.onDependentViewChanged(parent, child, dependency);
        }

        @Override
        public void onNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child,
                View target,
                int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
            Toolbar toolbar = (Toolbar) child.findViewById(R.id.toolbar);
            ViewGroup.LayoutParams layoutParams = toolbar.getLayoutParams();
            int[] toolbarLocation = new int[2];
            toolbar.getLocationOnScreen(toolbarLocation);
            int[] targetLocation = new int[2];
            target.getLocationOnScreen(targetLocation);
            int toolbarBottomY = toolbarLocation[1] + toolbar.getHeight();
            int[] appbarLocation = new int[2];
            int nestedScrollViewTopY = targetLocation[1];
            child.getLocationOnScreen(appbarLocation);
            int appbarBottomY = child.getLayoutParams().height;
            Log.i("behavior", "onNestedScroll " + dxConsumed + " " + dyConsumed + " " + dxUnconsumed + " " + dyUnconsumed);

            Log.i("behavior", "onNestedScroll " + toolbarBottomY + " " + nestedScrollViewTopY + appbarBottomY);
            if (nestedScrollViewTopY <= toolbarBottomY) {
                Log.i("behavior", "top");
                SuperNestedScrollView superNestedScrollView = (SuperNestedScrollView)target;
                superNestedScrollView.setDisallowInterceptTouchEvent(true);
            } else if (appbarBottomY <= nestedScrollViewTopY) {
                Log.i("behavior", "bottom");
                SuperNestedScrollView superNestedScrollView = (SuperNestedScrollView)target;
//                superNestedScrollView.setDisallowInterceptTouchEvent(false);
            }

            super.onNestedScroll( coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        }

        @Override
        public boolean onStartNestedScroll(CoordinatorLayout parent, AppBarLayout child,
                View directTargetChild, View target, int nestedScrollAxes) {
            Log.i("behavior", "onStartNestedScroll");
            return super
                    .onStartNestedScroll(parent, child, directTargetChild, target,
                            nestedScrollAxes);
        }

        @Override
        public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child,
                View target, int dx, int dy, int[] consumed) {
            Log.i("behavior", "onNestedPreScroll");
            super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
        }

        @Override
        public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout abl,
                View target) {
            Log.i("behavior", "onStopNestedScroll");
            super.onStopNestedScroll(coordinatorLayout, abl, target);
        }

        @Override
        public boolean onNestedFling(CoordinatorLayout coordinatorLayout, AppBarLayout child,
                View target, float velocityX, float velocityY, boolean consumed) {
            Log.i("behavior", "onNestedFling");
            return super
                    .onNestedFling(coordinatorLayout, child, target, velocityX, velocityY,
                            consumed);
        }

        @Override
        public boolean onMeasureChild(CoordinatorLayout parent, AppBarLayout child,
                int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec,
                int heightUsed) {
            Log.i("behavior", "onMeasureChild");
            return super.onMeasureChild(parent, child, parentWidthMeasureSpec, widthUsed,
                    parentHeightMeasureSpec, heightUsed);
        }

        @Override
        public boolean onLayoutChild(CoordinatorLayout parent, AppBarLayout abl,
                int layoutDirection) {
            Log.i("behavior", "onLayoutChild");
            return super.onLayoutChild(parent, abl, layoutDirection);
        }

        @Override
        public Parcelable onSaveInstanceState(CoordinatorLayout parent, AppBarLayout abl) {
            Log.i("behavior", "onSaveInstanceState");
            return super.onSaveInstanceState(parent, abl);
        }

        @Override
        public void onRestoreInstanceState(CoordinatorLayout parent, AppBarLayout appBarLayout,
                Parcelable state) {
            Log.i("behavior", "onRestoreInstanceState");
            super.onRestoreInstanceState(parent, appBarLayout, state);
        }
    }

}
