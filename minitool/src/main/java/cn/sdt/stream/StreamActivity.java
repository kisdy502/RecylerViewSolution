package cn.sdt.stream;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import cn.sdt.cardview.R;

public class StreamActivity extends Activity {
    RelativeLayout relativeLayout;
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream);

    }

    public void viewClick(View view) {
        byte[] data = "StreamActivity".getBytes();
        FileUtil.readBytes(data);
    }
}
