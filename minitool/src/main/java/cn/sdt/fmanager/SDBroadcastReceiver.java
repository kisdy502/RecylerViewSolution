package cn.sdt.fmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by SDT13411 on 2018/1/20.
 */

public class SDBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_MEDIA_MOUNTED == intent.getAction()) {

        } else if (Intent.ACTION_MEDIA_UNMOUNTED == intent.getAction()) {

        }
    }
}
