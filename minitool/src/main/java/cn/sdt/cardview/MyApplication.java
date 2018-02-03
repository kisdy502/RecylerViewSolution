package cn.sdt.cardview;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

/**
 * Created by SDT13411 on 2018/1/9.
 */

public class MyApplication extends Application {

    public static MyApplication instances;

    @Override
    public void onCreate() {
        super.onCreate();
        instances=this;
        WindowManager wm = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        int height = outMetrics.heightPixels;
        float density = outMetrics.density;
        Log.d("app","width:"+width);
        Log.d("app","height:"+height);
        Log.d("app","density:"+density);


    }


}
