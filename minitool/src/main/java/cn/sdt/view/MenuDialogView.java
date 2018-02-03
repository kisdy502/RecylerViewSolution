package cn.sdt.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ScrollView;

import cn.sdt.cardview.R;

/**
 * Created by SDT13411 on 2018/1/17.
 */
public class MenuDialogView extends ScrollView implements View.OnClickListener {

    private WindowManager mWm;
    private WindowManager.LayoutParams mWmParams;

    public MenuDialogView(Context context) {
        super(context);
    }

    public MenuDialogView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MenuDialogView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildAt(0) instanceof ViewGroup) {
            ViewGroup vp = (ViewGroup) getChildAt(0);
            for (int i = 0; i < vp.getChildCount(); i++) {
                vp.getChildAt(i).setOnClickListener(this);
            }
        }
    }

    private void createView(int locationX, int locationY) {
        mWm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        mWmParams = new WindowManager.LayoutParams();
        mWmParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        mWmParams.gravity = Gravity.LEFT | Gravity.TOP;
        mWmParams.x = locationX;
        mWmParams.y = locationY;
        mWmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mWmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mWmParams.format = 1;
        mWm.addView(this, mWmParams);
    }

    public void ShowDialog(int locationX, int locationY) {
        createView(locationX, locationY);
    }

    public void dismissDialog() {
        if (mWm != null) {
            mWm.removeView(this);
            mWm = null;
        }
    }

    private OnMenuItemClickListener mOnMenuItemClickListener;

    @Override
    public void onClick(View v) {
        if (mOnMenuItemClickListener.onMenuItemClick(v)) {
            dismissDialog();
        }
    }

    public interface OnMenuItemClickListener {
        /**
         * 点击菜单回调
         *
         * @param view 当前点击的菜单item
         * @return 是否关闭菜单
         */
        boolean onMenuItemClick(View view);
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener mOnMenuItemClickListener) {
        this.mOnMenuItemClickListener = mOnMenuItemClickListener;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();
        if (action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
            dismissDialog();
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    public void updateLayout() {
        if (mWm != null && mWmParams != null) {
            mWmParams.x -= getResources().getDimensionPixelSize(R.dimen.w220);
            if (mWmParams.x < 0) {
                mWmParams.x = getResources().getDimensionPixelSize(R.dimen.w80);
            }
            mWm.updateViewLayout(this, mWmParams);
        }
    }
}