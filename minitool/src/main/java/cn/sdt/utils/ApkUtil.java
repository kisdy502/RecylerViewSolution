package cn.sdt.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.RemoteException;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.content.pm.IPackageStatsObserver;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sdt13411 on 2018/1/11.
 */

public class ApkUtil {
    public final static int FILTER_USER = 1;
    public final static int FILTER_SYSTEM = 2;

    public static List<PackageInfo> getApkList(Context context) {
        PackageManager pm = context.getPackageManager(); // 获得PackageManager对象
        List<PackageInfo> packs = pm.getInstalledPackages(0);

        return packs;

    }

    public static List<PackageInfo> getSystemApkList(Context context) {
        List<PackageInfo> allList = getApkList(context);
        PackageInfo packageInfo;
        List<PackageInfo> sysApkList = new ArrayList<>();
        for (int i = 0, size = allList.size(); i < size; i++) {
            packageInfo = allList.get(i);
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) > 0) {
                sysApkList.add(packageInfo);
            }
        }
        return sysApkList;
    }

    public static List<PackageInfo> getUserApkList(Context context) {
        List<PackageInfo> allList = getApkList(context);
        PackageInfo packageInfo;
        List<PackageInfo> sysApkList = new ArrayList<>();
        for (int i = 0, size = allList.size(); i < size; i++) {
            packageInfo = allList.get(i);
            if (!((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) > 0)) {
                sysApkList.add(packageInfo);
            }
        }
        return sysApkList;
    }

    public static PackageInfo getApkInfo(Context context, String packageName) {
        PackageManager pm = context.getPackageManager(); // 获得PackageManager对象
        PackageInfo packageInfo = null;
        try {
            packageInfo = pm.getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo;
    }


    public static boolean isSystemApp(PackageInfo packageInfo) {
        return (packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) > 0;
    }


    public static void startApplication(Context context, PackageInfo packageInfo) {
        if (packageInfo == null) {
            return;
        }
        // 创建一个类别为CATEGORY_LAUNCHER的该包名的Intent
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(packageInfo.packageName);

        Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(packageInfo.packageName);
        if (launchIntent == null) {
            return;
        }
        resolveIntent.setComponent(launchIntent.getComponent());
        context.startActivity(resolveIntent);
    }


    public static void queryPacakgeSize(Context context, String pkgName) throws Exception {
        PackageManager pm = context.getPackageManager();  //得到pm对象
        try {
            //通过反射机制获得该隐藏函数
            Method getPackageSizeInfo = pm.getClass().getDeclaredMethod("getPackageSizeInfo", String.class, IPackageStatsObserver.class);
            //调用该函数，并且给其分配参数 ，待调用流程完成后会回调PkgSizeObserver类的函数
            getPackageSizeInfo.invoke(pm, pkgName, new PkgSizeObserver());
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;  // 抛出异常
        }
    }


    public static class PkgSizeObserver extends IPackageStatsObserver.Stub {

        @Override
        public void onGetStatsCompleted(PackageStats pStats, boolean succeeded)
                throws RemoteException {
            // TODO Auto-generated method stub
            long cachesize = pStats.cacheSize; //缓存大小
            long datasize = pStats.dataSize;  //数据大小
            long codesize = pStats.codeSize;  //应用程序大小
            long totalsize = cachesize + datasize + codesize;
            Log.i("Size", "cache:" + cachesize + " data:" + datasize + " code:" + codesize);
        }
    }
}