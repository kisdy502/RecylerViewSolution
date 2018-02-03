package cn.sdt;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;

public class BaseActivity extends FragmentActivity {
    public final static String TAG = "BaseActivity";
    public static final int EATKEYEVENT = 5;         // MSGID
    private static final int keyEventTime = 50;      //最短的按键事件应该是在50ms
    private static boolean eatKeyEvent = false;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case EATKEYEVENT:
                    eatKeyEvent = false;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK || event.getKeyCode() == KeyEvent.KEYCODE_DPAD_CENTER) {
            return super.dispatchKeyEvent(event);
        }

        if (eatKeyEvent) {
            return true;
        } else {
            if (event.getRepeatCount() >= 2) {
                Log.d(TAG, "repeat:" + event.getRepeatCount());
                eatKeyEvent = true;
                mHandler.removeMessages(EATKEYEVENT);
                Message msg = mHandler.obtainMessage(EATKEYEVENT);
                mHandler.sendMessageDelayed(msg, keyEventTime);
            }
        }
        return super.dispatchKeyEvent(event);
    }


    @Override
    protected void onDestroy() {
        mHandler.removeMessages(EATKEYEVENT);
        super.onDestroy();
    }
}
