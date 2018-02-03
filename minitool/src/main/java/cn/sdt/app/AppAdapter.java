package cn.sdt.app;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.sdt.cardview.R;
import cn.sdt.utils.ApkUtil;
import cn.sdt.utils.PackageUtils;

/**
 * Created by SDT13411 on 2018/1/17.
 */

public class AppAdapter extends RecyclerView.Adapter<AppAdapter.ViewHolder> {

    Context mContext;
    List<PackageInfo> packageInfoList;
    PackageManager packageManager;
    PackageInfo packageInfo;

    public AppAdapter(Context context, List<PackageInfo> packageInfoList) {
        mContext = context;
        this.packageInfoList = packageInfoList;
        packageManager = mContext.getPackageManager();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.app_item_layout, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        packageInfo = packageInfoList.get(position);
        long time = packageInfo.lastUpdateTime;
        String dir=packageInfo.applicationInfo.dataDir;
        try {
//            ApkUtil.queryPacakgeSize(mContext,packageInfo.packageName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.apkIcon.setBackground(packageInfo.
                applicationInfo.loadIcon(packageManager));
        holder.packageName.setText(packageInfo.packageName);
        Intent launchIntent = packageManager.getLaunchIntentForPackage(packageInfo.packageName);
        holder.startActivity.setText(launchIntent != null ? "/".concat(launchIntent.getComponent().getClassName()) : "/null");
        holder.apkName.setText(packageInfo.
                applicationInfo.loadLabel(packageManager));
        holder.versionName.setText("[" + packageInfo.versionName + "]");
        holder.signSHA1.setText("MD5:" + PackageUtils.getFingerprintSha1(mContext, packageInfo.packageName));
        holder.signMD5.setText("SHA1:" + PackageUtils.getFingerprintMd5(mContext, packageInfo.packageName));
    }

    @Override
    public int getItemCount() {
        return packageInfoList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView apkIcon;
        TextView apkName;
        TextView versionName;
        TextView packageName;
        TextView startActivity;
        TextView signMD5;
        TextView signSHA1;

        public ViewHolder(View itemView) {
            super(itemView);
            apkIcon = (ImageView) itemView.findViewById(R.id.apkIcon);
            apkName = (TextView) itemView.findViewById(R.id.apkName);
            versionName = (TextView) itemView.findViewById(R.id.versionName);
            packageName = (TextView) itemView.findViewById(R.id.packageName);
            startActivity = (TextView) itemView.findViewById(R.id.startActivity);
            signMD5 = (TextView) itemView.findViewById(R.id.md5Sign);
            signSHA1 = (TextView) itemView.findViewById(R.id.sha1Sign);

        }
    }
}
