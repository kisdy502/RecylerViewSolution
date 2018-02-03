package cn.ikan.recycle;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by SDT13411 on 2018/1/30.
 */

public class IKanRecyclerView extends RecyclerView {

    private final static String TAG = "IKanRecycleView";

    public IKanRecyclerView(Context context) {
        super(context);
    }

    public IKanRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public IKanRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean canScrollHorizontally(int direction) {
        boolean canScroll = super.canScrollHorizontally(direction);
        Log.d(TAG, "canScroll h" + canScroll);
        return canScroll;
    }

    @Override
    public boolean canScrollVertically(int direction) {
        boolean canScroll = super.canScrollHorizontally(direction);
        Log.d(TAG, "canScroll v" + canScroll);
        return canScroll;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        Log.d(TAG,"onInterceptTouchEvent");
        return super.onInterceptTouchEvent(e);
    }
}
