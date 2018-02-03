package cn.sdt.test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cn.sdt.app.AppAdapter;
import cn.sdt.cardview.R;
import cn.sdt.utils.ApkUtil;
import cn.sdt.view.MenuDialogView;

/**
 * Created by sdt13411 on 2018/1/11.
 */

public class TestFragment extends Fragment {

    private final static String TAG = "AppListFragment";

    private int filter = 0;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private AppAdapter mAdapter;
    private List<PackageInfo> packageInfoList;
    private MenuDialogView mMenuDialog;
    private AppReceiver mReceiver;
    private int currentPosition;
    private PackageManager packageManager;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        filter = getArguments().getInt("filter");
        Log.e(TAG, "onAttach filter::" + filter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView::");
        View view = inflater.inflate(R.layout.fragment_test, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.appList);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onViewCreated::");
        initData();
        init();
        packageManager = getContext().getPackageManager();
        mReceiver = new AppReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_PACKAGE_ADDED);
        filter.addAction(Intent.ACTION_PACKAGE_REPLACED);
        filter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        filter.addDataScheme("package");
        getContext().registerReceiver(mReceiver, filter);

    }

    @Override
    public void onDetach() {
        Log.e(TAG, "onDetach::");
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy::");
        getContext().unregisterReceiver(mReceiver);
        if (mMenuDialog != null) {
            mMenuDialog.dismissDialog();
            mMenuDialog = null;
        }
        super.onDestroy();
    }


    private void initData() {
        if (filter == 1) {
            packageInfoList = ApkUtil.getUserApkList(getContext());
        } else if (filter == 2) {
            packageInfoList = ApkUtil.getSystemApkList(getContext());
        } else {
            packageInfoList = ApkUtil.getApkList(getContext());
        }
    }

    private void init() {
        //创建默认的线性LayoutManager
        mLayoutManager = new LinearLayoutManager(getContext(), GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerView.setHasFixedSize(true);
        //创建并设置Adapter
        mAdapter = new AppAdapter(getContext(), packageInfoList);
        mRecyclerView.setAdapter(mAdapter);
    }

    public int indexInList(String packageName) {
        int i, size = packageInfoList.size();
        for (i = 0; i < size; i++) {
            if (packageInfoList.get(i).packageName.equals(packageName))
                return i;
        }
        return -1;
    }

    class AppReceiver extends BroadcastReceiver {
        private final String TAG = this.getClass().getSimpleName();

        @Override
        public void onReceive(Context context, Intent intent) {
            if (TextUtils.equals(intent.getAction(), Intent.ACTION_PACKAGE_ADDED)) {
                String packageName = intent.getData().getSchemeSpecificPart();
                PackageInfo packageInfo = ApkUtil.getApkInfo(context, packageName);
                boolean isSameType = (ApkUtil.isSystemApp(packageInfo) && filter == ApkUtil.FILTER_SYSTEM)
                        || (!ApkUtil.isSystemApp(packageInfo) && filter == ApkUtil.FILTER_USER);
                Log.d(TAG, "add packageName::" + packageName);
                Log.d(TAG, "add isSametype::" + isSameType);

                if (isSameType) {
                    int index = indexInList(packageName);
                    if (index < 0) {                                   //避免重复添加
                        packageInfoList.add(0, packageInfo);
                        mAdapter.notifyItemRangeInserted(0, 1);
                    }
                }
            } else if (TextUtils.equals(intent.getAction(), Intent.ACTION_PACKAGE_REPLACED)) {
                String packageName = intent.getData().getSchemeSpecificPart();
                Log.d(TAG, "replace packageName::" + packageName);
            } else if (TextUtils.equals(intent.getAction(), Intent.ACTION_PACKAGE_REMOVED)) {
                String packageName = intent.getData().getSchemeSpecificPart();
                Log.d(TAG, "remove packageName::" + packageName);
                int index = indexInList(packageName);
                Log.d(TAG, "remove index::" + index);
                if (index >= 0) {
                    packageInfoList.remove(index);
                    mAdapter.notifyItemRangeRemoved(index, 1);
                }
            }

        }


    }
}


