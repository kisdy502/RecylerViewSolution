package cn.sdt.test;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import cn.sdt.BaseActivity;
import cn.sdt.app.AppListActivity;
import cn.sdt.app.AppListFragment;
import cn.sdt.cardview.R;
import cn.sdt.utils.ApkUtil;

public class TestActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    public final static String TAG = "AppListActivity";
    ViewPager mViewPager;
    List<Fragment> fragments;
    MyFragmentPagerAdapter mAdapter;
    RadioGroup mRGTabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        setContentView(R.layout.activity_test);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mRGTabs= (RadioGroup) findViewById(R.id.rg_tab_bar);
        initFragments();
        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(this);

    }


    private void initFragments() {
        fragments = new ArrayList<>();
        Fragment userFragment = new TestFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("filter", ApkUtil.FILTER_USER);
        userFragment.setArguments(bundle);
        Fragment sysFragment = new TestFragment();
        bundle = new Bundle();
        bundle.putInt("filter", ApkUtil.FILTER_SYSTEM);
        sysFragment.setArguments(bundle);

        fragments.add(userFragment);
        fragments.add(sysFragment);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        ((RadioButton)mRGTabs.getChildAt(position)).setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    static class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        List<Fragment> fragments;

        public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
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
