package cn.sdt.fmanager.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import cn.sdt.cardview.R;
import cn.sdt.fmanager.ComparatorResultType;
import cn.sdt.fmanager.FileInfo;
import cn.sdt.fmanager.FileListAdapter;
import cn.sdt.fmanager.SDBroadcastReceiver;
import cn.sdt.utils.FileUtil;
import cn.sdt.view.AppOperationHelper;
import cn.sdt.view.FileOperateView;
import cn.sdt.weiget.recycle.BaseTvRecyclerView;
import cn.sdt.weiget.recycle.FocusFrameView;
import cn.sdt.weiget.recycle.OnItemClickListener;
import cn.sdt.weiget.recycle.OnItemListener;
import cn.sdt.weiget.recycle.OnItemLongClickListener;
import cn.sdt.weiget.recycle.TvLinearLayoutManager;
import cn.sdt.weiget.recycle.TvRecyclerView;

public class FileListFragment extends BaseFragment {

    private static final String TAG = "ListFragment";
    TvRecyclerView tvRecyclerView;
    TextView titleView;
    FileListAdapter mAdapter;
    List<FileInfo> fileInfoList;
    FocusFrameView mFocusFrame;
    FileInfo currentFileInfo;
    FileInfo rootFileInfo;           //设备根路径  /
    FileInfo sdCardFileInfo;         //SD开路径  /sdcard

    List<FileInfo> selectedFileInfoList = new ArrayList<>();

    private int mCurrentPosition;

    public FileListFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        Log.d(TAG, "onStart");
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_file_list, container, false);
        titleView = (TextView) view.findViewById(R.id.current);
        mFocusFrame = ((FocusFrameView) view.findViewById(R.id.frame));
        tvRecyclerView = (TvRecyclerView) view.findViewById(R.id.file_list);
        tvRecyclerView.initFrame(mFocusFrame, R.drawable.fly_border, getResources().getDimensionPixelOffset(R.dimen.w5));
        tvRecyclerView.setOnItemListener(new OnItemListener() {
            @Override
            public boolean onItemPreSelected(BaseTvRecyclerView parent, View itemView, int position) {

                return false;
            }

            @Override
            public boolean onItemSelected(BaseTvRecyclerView parent, View itemView, int position) {
                mCurrentPosition = position;
                return false;
            }

            @Override
            public boolean onReviseFocusFollow(BaseTvRecyclerView parent, View itemView, int position) {
                return false;
            }
        });
        tvRecyclerView.setOnItemClickListener(mOnItemClickListener);
        tvRecyclerView.setOnItemLongClickListener(mOnItemLongClickListener);
        return view;
    }

    private FileOperateView.OnFileOperatingListener mOperatingListener = new FileOperateView.OnFileOperatingListener() {
        @Override
        public void onItemDelete() {
            FileInfo bean = fileInfoList.get(mCurrentPosition);
            if (bean != null) {
                File file = new File(bean.fullPath);
                if (file.canRead() && file.canWrite()) {
                    if (FileUtil.deleteDir(file, true)) {
                        fileInfoList.remove(mCurrentPosition);
                        mAdapter.notifyItemRangeRemoved(mCurrentPosition, 1);
                    } else {
                        Toast.makeText(getContext(), "删除出错", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "删除失败,权限不足", Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onItemCat() {

        }

        @Override
        public void onItemCopy() {
            FileInfo bean = fileInfoList.get(mCurrentPosition);
            Log.d(TAG, "copy bean:" + bean.fullPath);
            selectedFileInfoList.clear();
            selectedFileInfoList.add(bean);

        }

        @Override
        public void onItemPaste() {
            if (selectedFileInfoList != null && selectedFileInfoList.size() > 0) {
                FileInfo fileInfo;

                for (int i = 0; i < selectedFileInfoList.size(); i++) {
                    fileInfo = selectedFileInfoList.get(i);
                    File srcFile = new File(fileInfo.fullPath);
                    Log.d(TAG, "srcFile:" + srcFile.getAbsolutePath());
                    String destFilePath = currentFileInfo.fullPath + File.separator + srcFile.getName();
                    Log.d(TAG, "destPath:" + destFilePath);
                    File destFile = new File(destFilePath);
                    if (srcFile.getParentFile().equals(srcFile)) {
                        continue;
                    }
                    if (fileInfo.isDirectry) {
                        FileUtil.copyDir(srcFile, new File(currentFileInfo.fullPath));
                        FileInfo bean = new FileInfo();
                        bean.isDirectry = destFile.isDirectory();
                        bean.fileName = destFile.getName();
                        bean.fullPath = destFilePath;
                        setParent(bean, destFile);
                        fileInfoList.add(0, bean); //头部
                        mAdapter.notifyItemRangeInserted(0, 1);
                    } else {
                        if (!destFile.exists()) {
                            FileUtil.copyFile(srcFile, destFile);
                            FileInfo bean = new FileInfo();
                            bean.isDirectry = destFile.isDirectory();
                            bean.fileName = destFile.getName();
                            bean.fullPath = destFilePath;
                            setParent(bean, destFile);
                            fileInfoList.add(0, bean); //头部
                            mAdapter.notifyItemRangeInserted(0, 1);
                        } else {
                            Toast.makeText(getContext(), "文件已经存在，是否覆盖", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                selectedFileInfoList.clear();
            }
        }
    };


    public void showMenuByManager() {
        AppOperationHelper mHelper = AppOperationHelper.getInstance(getContext());
        FileOperateView fileOperateView = new FileOperateView(getContext());
        fileOperateView.setEnabledCopyView(selectedFileInfoList.size() == 0);
        fileOperateView.setEnabledPasteView(selectedFileInfoList.size() > 0);
        fileOperateView.setHelper(mHelper);
        fileOperateView.setOperateListener(mOperatingListener);
        mHelper.setOperateView(fileOperateView);
        int[] location = new int[2];
        mFocusFrame.getLocationInWindow(location);
        mHelper.showOperateViewAt(location[0] + mFocusFrame.getMeasuredWidth() - fileOperateView.getMeasuredWidth(), location[1]);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initSdCard();
        currentFileInfo = sdCardFileInfo;
        titleView.setText(currentFileInfo.fullPath);
        fileInfoList = initData(currentFileInfo);
        TvLinearLayoutManager layoutManager = new TvLinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        tvRecyclerView.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        tvRecyclerView.setHasFixedSize(true);
        mAdapter = new FileListAdapter(getContext(), fileInfoList);
        tvRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        register();
    }

    private void initSysRoot() {
        rootFileInfo = new FileInfo();
        rootFileInfo.fullPath = "/";
        rootFileInfo.fileName = "/";
        rootFileInfo.isDirectry = true;
    }

    private void initSdCard() {
        String sdPath = getString(R.string.default_primary_folder);
        Log.d(TAG, "sdPath:" + sdPath);
        sdCardFileInfo = new FileInfo();
        File sdcardFile = new File(sdPath);
        sdcardFile.setReadable(true);
        sdcardFile.setWritable(true);
        Log.d(TAG, "sdcardFile:" + sdcardFile.canRead() + "," + sdcardFile.canWrite());
        sdCardFileInfo.fullPath = sdPath;
        sdCardFileInfo.isDirectry = sdcardFile.isDirectory();
        sdCardFileInfo.fileName = sdcardFile.getName();
        setParent(sdCardFileInfo, sdcardFile);
        Log.d(TAG, sdCardFileInfo.toString());
    }

    private void setParent(FileInfo bean, File beanFile) {
        FileInfo parentBean;
        if (beanFile != null) {
            File parentFile = beanFile.getParentFile();
            parentBean = new FileInfo();
            bean.fullPath = beanFile.getAbsolutePath();
            bean.isDirectry = beanFile.isDirectory();
            bean.fileName = beanFile.getName();
            bean.parent = parentBean;
            setParent(bean.parent, parentFile);
        }

    }

    @Override
    public void onDetach() {
        unRegister();
        super.onDetach();
    }

    @Override
    public boolean handleBackPressed() {
        if (currentFileInfo == sdCardFileInfo) {
            return false;
        }
        if (currentFileInfo != null && currentFileInfo.isDirectry && currentFileInfo.parent != null && currentFileInfo.parent.isDirectry) {
            currentFileInfo = currentFileInfo.parent;
            Log.d(TAG, "currentFileInfo" + currentFileInfo.toString());
            fileInfoList = initData(currentFileInfo);
            mAdapter = new FileListAdapter(getContext(), fileInfoList);
            tvRecyclerView.setAdapter(mAdapter);
            titleView.setText(currentFileInfo.fullPath);
            Log.d(TAG, "currentFileInfo" + currentFileInfo.fullPath);
            return true;
        } else {
            return false;
        }
    }

    SDBroadcastReceiver mReceiver;

    private void register() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_MEDIA_MOUNTED);
        filter.addAction(Intent.ACTION_MEDIA_UNMOUNTED);
        filter.addDataScheme("file");
        getContext().registerReceiver(mReceiver, filter);
    }

    private void unRegister() {
        if (mReceiver != null)
            getContext().unregisterReceiver(mReceiver);
    }


    private List<FileInfo> initData(FileInfo currentBean) {
        File rootFile = new File(currentBean.fullPath);
        currentBean.isDirectry = rootFile.isDirectory();
        currentBean.fileName = rootFile.getName();
        currentBean.fullPath = rootFile.getAbsolutePath();
        setParent(currentBean, rootFile);
        List<FileInfo> list = new LinkedList<>();
        File[] files = rootFile.listFiles();

        FileInfo fileBean;
        if (files != null) {
            for (File f : files) {
                fileBean = new FileInfo();
                fileBean.isDirectry = f.isDirectory();
                fileBean.parent = currentBean;
                fileBean.fullPath = f.getAbsolutePath();
                fileBean.fileName = f.getName();
                fileBean.childDirCount = f.listFiles() != null ? f.listFiles(new FileFilter() {
                    @Override
                    public boolean accept(File pathname) {
                        return pathname.isDirectory();
                    }
                }).length : 0;
                fileBean.childFileCount = f.listFiles() != null ? f.listFiles(new FileFilter() {
                    @Override
                    public boolean accept(File pathname) {
                        return pathname.isFile();
                    }
                }).length : 0;
                list.add(fileBean);
            }
        }
        Collections.sort(list, new ComparatorResultType());
        return list;
    }


    private OnItemClickListener mOnItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(BaseTvRecyclerView parent, View itemView, int position) {
            FileInfo temp = fileInfoList.get(position);
            File tempF = new File(temp.fullPath);
            if (tempF.canRead()) {
                if (tempF.isDirectory()) {
                    currentFileInfo = temp;
                    fileInfoList = initData(currentFileInfo);
                    mAdapter = new FileListAdapter(getContext(), fileInfoList);
                    tvRecyclerView.setAdapter(mAdapter);
                    titleView.setText(currentFileInfo.fullPath);
                } else if (tempF.isFile()) {
                    //尝试打开文件
                }
            } else {
                Log.d(TAG, "没有权限");
            }
        }
    };

    private OnItemLongClickListener mOnItemLongClickListener = new OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(BaseTvRecyclerView parent, View itemView, int position) {
            showMenuByManager();
            return true;
        }
    };
}
