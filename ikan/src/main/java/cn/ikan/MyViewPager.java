package cn.ikan;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by SDT13411 on 2018/1/30.
 */

public class MyViewPager extends ViewPager {
    private final static String TAG = "MyViewPager";

    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {

        if (v instanceof RecyclerView &&
                ((RecyclerView) v).getLayoutManager() instanceof LinearLayoutManager
                && ((LinearLayoutManager) ((RecyclerView) v).getLayoutManager()).getOrientation() == LinearLayoutManager.HORIZONTAL) {
            return true;
        } else {
            return super.canScroll(v, checkV, dx, x, y);
        }
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(TAG,"onInterceptTouchEvent");
        return super.onInterceptTouchEvent(ev);
    }
}
