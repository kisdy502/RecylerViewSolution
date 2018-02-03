package cn.sdt.weiget.recycle;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import cn.sdt.weiget.BaseEffect;
import cn.sdt.weiget.TvRecycleEffect;

/**
 * Created by SDT13411 on 2018/1/12.
 */

public class BaseTvRecyclerView extends RecyclerView {

    final static String TAG = "BaseTvRecyclerView";

    OnItemClickListener onItemClickListener;
    OnItemLongClickListener onItemLongClickListener;
    OnItemListener onItemListener;
    ViewClickListener mViewClickListener;
    ViewFocusListener mViewFocusListener;

    private final static float DEFAULT_SCALE = 1.00f;
    private float mScale = DEFAULT_SCALE;
    protected int mOffset;
    protected int mSaveFocusPosition;
    protected BaseEffect mEffect;
    private FocusFrameView mFocusFrameView;          //飞框


    final static int MSG_START = 0;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

        }
    };

    private void sendMessage(Message msg) {
        mHandler.removeMessages(msg.what);
        mHandler.sendMessage(msg);
    }


    public BaseTvRecyclerView(Context context) {
        this(context, null);
    }

    public BaseTvRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseTvRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setDescendantFocusability(FOCUS_AFTER_DESCENDANTS);
        setChildrenDrawingOrderEnabled(true);
        setWillNotDraw(true);
        setHasFixedSize(false);
        setOverScrollMode(View.OVER_SCROLL_NEVER);
        setClipChildren(false);
        setClipToPadding(false);
        setClickable(false);
        setFocusable(true);
        setFocusableInTouchMode(true);
        initListener();
    }

    private void initListener() {
        mViewClickListener = new ViewClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(BaseTvRecyclerView.this, view, getChildLayoutPosition(view));
                }
            }

            @Override
            public boolean onLongClick(View view) {
                if (onItemLongClickListener != null) {
                    return onItemLongClickListener.onItemLongClick(BaseTvRecyclerView.this, view, getChildLayoutPosition(view));
                }
                return false;
            }
        };

        mViewFocusListener = new ViewFocusListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (null != onItemListener) {
                    if (view != null) {
                        view.setSelected(hasFocus);
                        if (hasFocus) {
                            Log.d(TAG, "SCROLL_STATE:" + getScrollState());
                            mSaveFocusPosition = getChildLayoutPosition(view);
                            //当返回true表示用户处理，不需要RecyclerView自动处理
                            boolean result = onItemListener.onItemSelected(BaseTvRecyclerView.this, view, getChildLayoutPosition(view));
                            if (!result) {
                                mEffect.onFocusView(view, mScale, mScale);
                            }
                        } else {
                            Log.d(TAG, "SCROLL_STATE:" + getScrollState());
                            boolean result = onItemListener.onItemPreSelected(BaseTvRecyclerView.this, view, getChildLayoutPosition(view));
                            if (!result) {
                                mEffect.onUnFocusView(view);
                            }
                        }
                    }
                }
            }
        };
    }

    @Override
    public void onChildAttachedToWindow(View child) {
        if (!ViewCompat.hasOnClickListeners(child)) {
            child.setOnClickListener(mViewClickListener);
        }
        child.setOnLongClickListener(mViewClickListener);
        if (child.getOnFocusChangeListener() == null) {
            child.setOnFocusChangeListener(mViewFocusListener);
        }
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        //用来微调位置
        if (RecyclerView.SCROLL_STATE_IDLE == state) {
            mOffset = -1;
            boolean result = onItemListener.onReviseFocusFollow(this, getFocusedChild(), getChildLayoutPosition(getFocusedChild()));
            if (!result) {
                mEffect.onFocusView(getFocusedChild(), mScale, mScale);
            }
        }
    }


    public void initFrame(FocusFrameView view, Drawable frameDrawable, int padding) {
        initFrame(view, frameDrawable, new Rect(padding, padding, padding, padding));
    }

    public void initFrame(FocusFrameView view, int frameResId, int padding) {
        initFrame(view, getResources().getDrawable(frameResId), new Rect(padding, padding, padding, padding));
    }

    public void initFrame(FocusFrameView view, int frameResId, Rect rect) {
        initFrame(view, getResources().getDrawable(frameResId), rect);
    }

    /**
     * 初始化飞框
     *
     * @param view
     * @param frameDrawable
     * @param rect
     */
    public void initFrame(FocusFrameView view, Drawable frameDrawable, Rect rect) {
        mFocusFrameView = view;
        mEffect = new TvRecycleEffect();
        mFocusFrameView.setEffect(mEffect);
        mEffect.setFocusDrawable(frameDrawable);
        mFocusFrameView.setFocusFramePadding(rect);
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public void setOnItemListener(OnItemListener onItemListener) {
        this.onItemListener = onItemListener;
    }

    public void setmScale(float mScale) {
        this.mScale = mScale;
    }

    public void setmEffect(BaseEffect mEffect) {
        this.mEffect = mEffect;
    }

    /**
     * 获取选中ITEM的滚动偏移量
     *
     * @return 预选中的item由于recyclerView的滚动，产生的偏移量，主要用于修正焦点框
     */
    public int getSelectedItemScrollOffset() {
        return mOffset;
    }
}
