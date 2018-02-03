package cn.ikan.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.ikan.R;
import cn.ikan.SortBean;
import cn.ikan.recycle.IKanRecyclerView;

/**
 * 解决RecycleView notifyDataSetChanged方法导致闪烁问题
 * 该方式并非是万能的，需要分场合使用
 * notifyDataSetChanged方法导致闪烁的原因,ViewHolder无法复用，导致创建新的ViewHolder时，重新measure控件，闪烁
 * 如何规避   Adapter.setHasStableIds(true); 并且重新Adapter getItemId方法
 * Created by Administrator on 2018/2/1.
 */

public class FreshFragment extends Fragment {

    private TextView tvCurrent;
    private IKanRecyclerView mRecycleView;
    FreshAdapter mAdapter;
    List<SortBean> list;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fresh, container,
                false);
        tvCurrent = (TextView) view.findViewById(R.id.content);
        mRecycleView = (IKanRecyclerView) view.findViewById(R.id.data_list);
        tvCurrent.setText("notifyDataSetChanged 做到不让界面闪烁");
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list = initData();
        mAdapter = new FreshAdapter(getContext(), list);
        mAdapter.setHasStableIds(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        mRecycleView.setLayoutManager(layoutManager);
        mRecycleView.setAdapter(mAdapter);

        tvCurrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = list.size() + new Random().nextInt(1);
                list.add(new SortBean(index, "from the parent" + index * index));
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }


    private List<SortBean> initData() {
        int i;
        List<SortBean> sortBeanList = new ArrayList<>();
        SortBean bean;
        for (i = 0; i < 12; i++) {
            bean = new SortBean(i, "from the parent" + i * i);
            sortBeanList.add(bean);
        }
        return sortBeanList;
    }

}
