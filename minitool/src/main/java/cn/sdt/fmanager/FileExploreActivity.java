package cn.sdt.fmanager;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import cn.sdt.BaseActivity;
import cn.sdt.cardview.R;
import cn.sdt.fmanager.fragment.BaseFragment;
import cn.sdt.fmanager.fragment.FileCategoryFragment;
import cn.sdt.fmanager.fragment.FileListFragment;
import cn.sdt.fmanager.fragment.FileRemoteFragment;

public class FileExploreActivity extends BaseActivity implements ViewPager.OnPageChangeListener, FileRemoteFragment.HandleEventListener {

    private ViewPager mViewPager;
    private List<BaseFragment> fragments;
    private MyFragmentPagerAdapter mAdapter;
    private RadioGroup mGroups;
    BaseFragment fileListFragment;
    BaseFragment mHandleEventFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        setContentView(R.layout.activity_file_explore);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mGroups = (RadioGroup) findViewById(R.id.rg_tab_bar);
        initFragments();
        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(this);
        mHandleEventFragment=fileListFragment;
    }

    private void initFragments() {
        fragments = new ArrayList<>();
        fileListFragment = new FileListFragment();
        fragments.add(fileListFragment);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        ((RadioButton) mGroups.getChildAt(position)).setChecked(true);
        mHandleEventFragment=fragments.get(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onBackPressed() {
        if (mHandleEventFragment != null) {
            if (!mHandleEventFragment.handleBackPressed()) {
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public void setHandleFragment(BaseFragment fragment) {
        mHandleEventFragment = fragment;
    }

    static class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        List<BaseFragment> fragments;

        public MyFragmentPagerAdapter(FragmentManager fm, List<BaseFragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public BaseFragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments != null ? fragments.size() : 0;
        }

        @Override
        public Object instantiateItem(ViewGroup vg, int position) {
            return super.instantiateItem(vg, position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }
    }
}
