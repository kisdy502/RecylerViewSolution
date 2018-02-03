package cn.ikan;

import android.support.design.widget.TabLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import cn.ikan.fragment.ContentFragment;
import cn.ikan.fragment.FreshFragment;
import cn.ikan.fragment.ListFragment;

public class MainActivity extends FragmentActivity {

    String[] strTitles = new String[]{
            "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"
    };

    Fragment[] fragments = new Fragment[12];

    TabLayout tabLayout;
    MyViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout = (TabLayout) findViewById(R.id.tab_titles);
        viewPager = (MyViewPager) findViewById(R.id.view_pager);
        initTitles();
    }

    private void initTitles() {

        tabLayout.addTab(tabLayout.newTab().setText("一月"));
        tabLayout.addTab(tabLayout.newTab().setText("二月"));
        tabLayout.addTab(tabLayout.newTab().setText("三月"));
        tabLayout.addTab(tabLayout.newTab().setText("四月"));
        tabLayout.addTab(tabLayout.newTab().setText("五月"));
        tabLayout.addTab(tabLayout.newTab().setText("六月"));
        tabLayout.addTab(tabLayout.newTab().setText("七月"));
        tabLayout.addTab(tabLayout.newTab().setText("八月"));
        tabLayout.addTab(tabLayout.newTab().setText("九月"));
        tabLayout.addTab(tabLayout.newTab().setText("十月"));
        tabLayout.addTab(tabLayout.newTab().setText("十一月"));
        tabLayout.addTab(tabLayout.newTab().setText("十二月"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tabLayout.getSelectedTabPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        Fragment fragment;
        Bundle bundle;
        for (int i = 0, size = strTitles.length; i < size; i++) {
            if (i % 3 == 0) {
                fragment = new ContentFragment();
            } else if (i % 3 == 1) {
                fragment = new FreshFragment();
            } else if (i % 3 == 2) {
                fragment = new ListFragment();
            } else {
                fragment = new ListFragment();
            }
            bundle = new Bundle();
            bundle.putString("title", strTitles[i]);
            fragment.setArguments(bundle);
            fragments[i] = fragment;
        }

        MyAdapter adapter = new MyAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                tabLayout.getTabAt(i).select();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }
}
