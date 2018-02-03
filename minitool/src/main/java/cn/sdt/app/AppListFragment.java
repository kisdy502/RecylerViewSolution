package cn.sdt.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import cn.sdt.cardview.R;
import cn.sdt.fmanager.fragment.BaseFragment;
import cn.sdt.utils.ApkUtil;
import cn.sdt.utils.FileUtil;
import cn.sdt.utils.PackageUtils;
import cn.sdt.view.AppOperateView;
import cn.sdt.view.AppOperationHelper;
import cn.sdt.view.MenuDialogView;
import cn.sdt.weiget.recycle.FocusFrameView;
import cn.sdt.weiget.recycle.BaseTvRecyclerView;
import cn.sdt.weiget.recycle.OnItemClickListener;
import cn.sdt.weiget.recycle.OnItemListener;
import cn.sdt.weiget.recycle.OnItemLongClickListener;
import cn.sdt.weiget.recycle.TvLinearLayoutManager;
import cn.sdt.weiget.recycle.TvRecyclerView;

/**
 * Created by sdt13411 on 2018/1/11.
 */

public class AppListFragment extends BaseFragment {

    private final static String TAG = "AppListFragment";

    private int filter = 0;
    private TvRecyclerView mRecyclerView;
    private TvLinearLayoutManager mLayoutManager;
    private AppAdapter mAdapter;
    private List<PackageInfo> packageInfoList;
    private FocusFrameView mFocusFrame;
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
        View view = inflater.inflate(R.layout.fragment_app_list, container, false);
        mRecyclerView = (TvRecyclerView) view.findViewById(R.id.appList);
        mFocusFrame = ((FocusFrameView) view.findViewById(R.id.frame));
        mRecyclerView.initFrame(mFocusFrame, R.drawable.fly_border, getResources().getDimensionPixelOffset(R.dimen.w5));
        mRecyclerView.setOnItemListener(mOnItemListener);
        mRecyclerView.setOnItemClickListener(mOnItemClickListener);
        mRecyclerView.setOnItemLongClickListener(mOnItemLongClickListener);
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
    public boolean handleBackPressed() {
        return false;
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
        mLayoutManager = new TvLinearLayoutManager(getContext(), GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerView.setHasFixedSize(true);
        //创建并设置Adapter
        mAdapter = new AppAdapter(getContext(), packageInfoList);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void showMenuByManager() {
        AppOperationHelper mHelper = AppOperationHelper.getInstance(getContext());
        AppOperateView appOperateView = new AppOperateView(getContext());
        appOperateView.setHelper(mHelper);
        appOperateView.setOperateListener(mOperatingListener);
        mHelper.setOperateView(appOperateView);
        int[] location = new int[2];
        mFocusFrame.getLocationInWindow(location);
        mHelper.showOperateViewAt(location[0] + mFocusFrame.getMeasuredWidth() / 2, location[1]);

    }

    private AppOperateView.OnOperatingListener mOperatingListener = new AppOperateView.OnOperatingListener() {
        @Override
        public void onItemDelete() {
            if (ApkUtil.FILTER_USER == filter) {
                PackageUtils.uninstallNormal(getContext(), packageInfoList.get(currentPosition).packageName);
            } else {
                Toast.makeText(getContext(), "系统应用无法卸载", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onItemClearApp() {
            File sdcardDir = FileUtil.getInnerSdcardDirectory(getContext(), packageInfoList.get(currentPosition).packageName);
            Log.d(TAG, "sdcardDir:" + (sdcardDir != null ? sdcardDir.getAbsolutePath() : ""));
            File extCacheDir = FileUtil.getExternalCacheDirectory(getContext(), packageInfoList.get(currentPosition).packageName);
            Log.d(TAG, "extCacheDir:" + (extCacheDir != null ? extCacheDir.getAbsolutePath() : ""));
            if (sdcardDir != null) {
                FileUtil.deleteDir(sdcardDir);
            }
            if (extCacheDir != null) {
                FileUtil.deleteDir(extCacheDir);
            }
            Toast.makeText(getContext(), "清理成功", Toast.LENGTH_SHORT).show();
        }

    };

    private OnItemClickListener mOnItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(BaseTvRecyclerView parent, View itemView, int position) {
            PackageInfo packageInfo = packageInfoList.get(position);
            ApkUtil.startApplication(getContext(), packageInfo);
        }
    };

    private OnItemLongClickListener mOnItemLongClickListener = new OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(BaseTvRecyclerView parent, View itemView, int position) {
            showMenuByManager();
            return true;
        }
    };

    private OnItemListener mOnItemListener = new OnItemListener() {

        @Override
        public boolean onItemPreSelected(BaseTvRecyclerView parent, View itemView, int position) {
            return false;
        }

        @Override
        public boolean onItemSelected(BaseTvRecyclerView parent, View itemView, int position) {
            currentPosition = position;
            Log.i(TAG, "onItemSelected pos::" + currentPosition);
            return false;
        }

        @Override
        public boolean onReviseFocusFollow(BaseTvRecyclerView parent, View itemView, int position) {
            currentPosition = position;
            Log.i(TAG, "onReviseFocusFollow pos::" + currentPosition);
            return false;
        }
    };

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


