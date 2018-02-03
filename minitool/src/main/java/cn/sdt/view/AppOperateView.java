package cn.sdt.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import cn.sdt.cardview.R;

/**
 * Created by SDT13411 on 2018/1/16.
 */

public class AppOperateView extends RelativeLayout implements View.OnClickListener {

    private static final String TAG = "AppOperateView";

    public void setHelper(AppOperationHelper mHelper) {
        this.mHelper = mHelper;
    }

    AppOperationHelper mHelper;


    private OnOperatingListener mListener;

    public AppOperateView(Context context) {
        this(context,null);
    }

    public AppOperateView(Context context, AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public AppOperateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    protected void init() {
        setCanFocus(this);
        //先分发给Child View进行处理，如果所有的Child View都沒有处理，則自己再处理
        setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
        inflate(getContext(), R.layout.appoperate_view_layout, this);
        initView();
    }

    public void setCanFocus(View view) {
        view.setClickable(true);
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    protected void initView() {
        //客户端使用的view根据需要添加相应的子view的id为下面的其中之一
        View operateDel = findViewById(R.id.operate_delete);
        if (operateDel != null) {
            setCanFocus(operateDel);
            operateDel.setOnClickListener(this);
        }
        View operateClearCache = findViewById(R.id.operate_clear_cache);
        if (operateClearCache != null) {
            setCanFocus(operateClearCache);
            operateClearCache.setOnClickListener(this);
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            mHelper.dismissOperateView();
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public void onClick(View view) {
        if (mListener == null) {
            Log.e(TAG, "you should set setOnOperatingListener first !!!");
            return;
        }
        int id = view.getId();
        if (id == R.id.operate_delete) {
            mHelper.dismissOperateView();
            mListener.onItemDelete();
        } else if (id == R.id.operate_clear_cache) {
            mHelper.dismissOperateView();
            mListener.onItemClearApp();
        }
    }

    public void setOperateListener(OnOperatingListener mListener) {
        this.mListener = mListener;
    }


    public interface OnOperatingListener {
        void onItemDelete();
        void onItemClearApp();
    }

}
