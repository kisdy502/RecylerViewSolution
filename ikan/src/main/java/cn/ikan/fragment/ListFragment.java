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

import cn.ikan.Block;
import cn.ikan.Content;
import cn.ikan.R;
import cn.ikan.recycle.BetterRecyclerView;

/**
 * Created by SDT13411 on 2018/1/30.
 */

public class ListFragment extends Fragment {
    RecyclerView mRecycleView;

    List<Block> blockList;

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
        View view = inflater.inflate(R.layout.fragment_list, container,
                false);
        TextView tvCurrent = (TextView) view.findViewById(R.id.content);
        mRecycleView = (BetterRecyclerView) view.findViewById(R.id.content_list);
        tvCurrent.setText("竖向RecycleView 嵌套横向RecylerView滑动冲突问题解决");
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        initData();
        ListAdapter adapter = new ListAdapter(getContext(), blockList);
        mRecycleView.setLayoutManager(layoutManager);
        mRecycleView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    private void initData() {
        blockList = new ArrayList<>();

        Content content1 = new Content("大爱", "世上无情草", 1, "热门");
        Content content2 = new Content("大爱", "世上无情草", 1, "热门");
        Content content3 = new Content("大爱", "世上无情草", 1, "热门");
        Content content4 = new Content("大爱", "世上无情草", 1, "热门");
        Content content5 = new Content("大爱", "世上无情草", 1, "热门");
        Content content6 = new Content("大爱", "世上无情草", 1, "热门");
        Content content7 = new Content("大爱", "世上无情草", 1, "热门");
        Content content8 = new Content("大爱", "世上无情草", 1, "热门");
        Content content9 = new Content("大爱", "世上无情草", 1, "热门");
        Content content10 = new Content("大爱", "世上无情草", 1, "热门");
        Content content11 = new Content("大爱", "世上无情草", 1, "热门");
        Content content12 = new Content("大爱", "世上无情草", 1, "热门");
        Content content13 = new Content("大爱", "世上无情草", 1, "热门");
        Content content14 = new Content("大爱", "世上无情草", 1, "热门");
        Content content15 = new Content("大爱", "世上无情草", 1, "热门");
        Content content16 = new Content("大爱", "世上无情草", 1, "热门");
        Content content17 = new Content("大爱", "世上无情草", 1, "热门");
        Content content18 = new Content("大爱", "世上无情草", 1, "热门");
        Content content19 = new Content("大爱", "世上无情草", 1, "热门");
        Content content20 = new Content("大爱", "世上无情草", 1, "热门");
        Content content21 = new Content("大爱", "世上无情草", 1, "热门");
        Content content22 = new Content("大爱", "世上无情草", 1, "热门");
        Content content23 = new Content("大爱", "世上无情草", 1, "热门");
        Content content24 = new Content("大爱", "世上无情草", 1, "热门");

        List<Content> contentList = new ArrayList<>();
        contentList.add(content1);
        contentList.add(content2);
        contentList.add(content3);
        contentList.add(content4);
        contentList.add(content5);
        contentList.add(content6);
        contentList.add(content7);
        contentList.add(content8);
        contentList.add(content9);
        contentList.add(content10);
        contentList.add(content11);
        contentList.add(content12);
        contentList.add(content13);
        contentList.add(content14);
        contentList.add(content15);
        contentList.add(content16);
        contentList.add(content17);
        contentList.add(content18);
        contentList.add(content19);
        contentList.add(content20);
        contentList.add(content21);
        contentList.add(content22);
        contentList.add(content23);
        contentList.add(content24);

        Block block1 = new Block();
        block1.setContentList(contentList);
        blockList.add(block1);

        Block block2 = new Block();
        block2.setContentList(contentList);
        blockList.add(block2);

        Block block3 = new Block();
        block3.setContentList(contentList);
        blockList.add(block3);

        Block block4 = new Block();
        block4.setContentList(contentList);
        blockList.add(block4);

        Block block5 = new Block();
        block5.setContentList(contentList);
        blockList.add(block5);


    }
}
