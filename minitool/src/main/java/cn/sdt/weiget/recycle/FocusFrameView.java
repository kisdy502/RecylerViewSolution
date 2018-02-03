package cn.sdt.weiget.recycle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import cn.sdt.weiget.BaseEffect;

/**
 * Created by SDT13411 on 2018/1/15.
 */

public class FocusFrameView extends FrameLayout {

    private BaseEffect mEffect;

    public BaseEffect getEffect() {
        return mEffect;
    }

    public void setEffect(BaseEffect effect) {
        if (effect == null) {
            throw new RuntimeException("parameter effect can'not is null");
        }
        this.mEffect = effect;
        mEffect.setFocusFrameView(this);
        mEffect.init(getContext());
    }

    public FocusFrameView(Context context) {
        this(context, null);
    }

    public FocusFrameView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FocusFrameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setWillNotDraw(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mEffect == null) {
            throw new RuntimeException("mEffect can'not is null");
        }
        if (!mEffect.onFocusViewDraw(canvas)) {
            super.onDraw(canvas);
        }
    }

    public void setFocusFramePadding(int padding) {
        if (mEffect == null) {
            throw new RuntimeException("mEffect can'not is null");
        }
        mEffect.setFocusFramePadding(new Rect(padding, padding, padding, padding));
        invalidate();
    }

    public void setFocusFramePadding(Rect padding) {
        if (mEffect == null) {
            throw new RuntimeException("mEffect can'not is null");
        }
        mEffect.setFocusFramePadding(padding);
    }


}
