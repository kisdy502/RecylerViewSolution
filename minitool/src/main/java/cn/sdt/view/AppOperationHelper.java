package cn.sdt.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import java.util.List;

import cn.sdt.weiget.recycle.TvRecyclerView;

/**
 * 操作管理类
 * Created by SDT13411 on 2018/1/16.
 */

public class AppOperationHelper {
    private final LayoutInflater mLayoutInflater;
    private Context mContext;
    View mOperateView;

    private boolean mHadShow;

    public boolean isShowDialog() {
        return mHadShow;
    }

    private WindowManager mWm;
    private WindowManager.LayoutParams mWmParams;
    private RecyclerView.LayoutManager mLayoutManager;

    private static AppOperationHelper instance;

    public static AppOperationHelper getInstance(Context context) {
        if (instance == null) {
            synchronized (AppOperationHelper.class) {
                if (instance == null) {
                    instance = new AppOperationHelper(context);
                }
            }
        }
        return instance;
    }

    public AppOperationHelper(Context mContext) {
        this.mContext = mContext;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }


    public View setOperateView(View operateView) {
        mOperateView = operateView;
        return operateView;
    }


    public void dismissOperateView() {
        if (mOperateView == null) {
            throw new IllegalStateException("mOperateView can't be null!!!");
        }
        if (mWm != null) {
            mWm.removeView(mOperateView);
            mWm = null;
        }
        mHadShow = false;
    }

    public void showOperateViewAt(int locationX, int locationY) {
        dismissOperateView();
        if (mOperateView == null) {
            throw new IllegalStateException("mOperateView can't be null!!!");
        }
        mWm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        mWmParams = new WindowManager.LayoutParams();
        mWmParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        mWmParams.gravity = Gravity.START | Gravity.TOP;
        mOperateView.measure(0, 0);
        if (locationX == 0 && locationY == 0) {
            DisplayMetrics outMetrics = new DisplayMetrics();
            mWm.getDefaultDisplay().getMetrics(outMetrics);
            locationX = outMetrics.widthPixels / 2 - mOperateView.getMeasuredWidth() / 2;
            locationY = outMetrics.heightPixels / 2 - mOperateView.getMeasuredHeight() / 2;
        }
        mWmParams.x = locationX;
        mWmParams.y = locationY;
        mWmParams.width = mOperateView.getMeasuredWidth();
        mWmParams.height = mOperateView.getMeasuredHeight();
        mWmParams.format = 1;
        mWm.addView(mOperateView, mWmParams);
        mHadShow = true;
    }

}