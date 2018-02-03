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

public class FileOperateView extends RelativeLayout implements View.OnClickListener {

    private static final String TAG = "FileOperateView";
    private boolean enabledCopyView;
    private boolean enabledPasteView;

    View operateDel;
    View operateCat;
    View operateCopy;
    View operatePaste;

    public void setEnabledCopyView(boolean enabledCopyView) {
        this.enabledCopyView = enabledCopyView;
        operateCopy.setEnabled(enabledCopyView);

    }

    public void setEnabledPasteView(boolean enabledPasteView) {
        this.enabledPasteView = enabledPasteView;
        operatePaste.setEnabled(enabledPasteView);
    }

    public void setHelper(AppOperationHelper mHelper) {
        this.mHelper = mHelper;
    }

    AppOperationHelper mHelper;


    private OnFileOperatingListener mListener;

    public FileOperateView(Context context) {
        this(context, null);
    }

    public FileOperateView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public FileOperateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    protected void init() {
        setCanFocus(this);
        //先分发给Child View进行处理，如果所有的Child View都沒有处理，則自己再处理
        setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
        inflate(getContext(), R.layout.fileoperate_view_layout, this);
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
        operateDel = findViewById(R.id.operate_delete);
        if (operateDel != null) {
            setCanFocus(operateDel);
            operateDel.setOnClickListener(this);
        }
        operateCat = findViewById(R.id.operate_cat);
        if (operateCat != null) {
            setCanFocus(operateCat);
            operateCat.setOnClickListener(this);
        }

        operateCopy = findViewById(R.id.operate_copy);
        if (operateCopy != null) {
            setCanFocus(operateCopy);
            operateCopy.setOnClickListener(this);
        }

        operatePaste = findViewById(R.id.operate_paste);
        if (operatePaste != null) {
            setCanFocus(operatePaste);
            operatePaste.setOnClickListener(this);
        }
        operateCopy.setEnabled(enabledCopyView);
        operatePaste.setEnabled(enabledPasteView);
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
        } else if (id == R.id.operate_cat) {
            mHelper.dismissOperateView();
            mListener.onItemCat();
        } else if (id == R.id.operate_copy) {
            mHelper.dismissOperateView();
            mListener.onItemCopy();
        } else if (id == R.id.operate_paste) {
            mHelper.dismissOperateView();
            mListener.onItemPaste();
        }
    }

    public void setOperateListener(OnFileOperatingListener mListener) {
        this.mListener = mListener;
    }


    public interface OnFileOperatingListener {

        void onItemDelete();

        void onItemCat();

        void onItemCopy();

        void onItemPaste();
    }

}
