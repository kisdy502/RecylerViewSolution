package cn.ikan.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.ikan.R;
import cn.ikan.Schedule;
import cn.ikan.recycle.IKanRecyclerView;

/**
 * Created by SDT13411 on 2018/1/30.
 */

public class ContentFragment extends Fragment {
    private final static String TAG = "ContentFragment";
    private String title;
    private IKanRecyclerView mRecycleView;

    @Override
    public void onAttach(Context context) {
        Log.d(TAG, "onAttach...");
        super.onAttach(context);
        title = getArguments().getString("title");
        Log.d(TAG, "title" + title);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate...");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView...");
        View view = inflater.inflate(R.layout.fragment_content, container,
                false);
        TextView tvCurrent = (TextView) view.findViewById(R.id.current_info);
        mRecycleView = (IKanRecyclerView) view.findViewById(R.id.head_list);
        tvCurrent.setText("如何做到RecycleView滑动到最后一个Item时候，ViewPager不滑动到下一个Tab");
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated...");
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false);
        List<Schedule> mList = initList();
        ScheduleAdapter adapter = new ScheduleAdapter(getContext(), mList);
        mRecycleView.setLayoutManager(layoutManager);
        mRecycleView.setAdapter(adapter);
    }


    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy...");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "onDetach...");
        super.onDetach();
    }


    private List<Schedule> initList() {
        List<Schedule> list = new ArrayList<>();
        Schedule schedule;
        for (int i = 0; i < 10; i++) {
            schedule = new Schedule();
            schedule.setMainTitle("Main" + i);
            schedule.setSubTitle("sub" + i);
            list.add(schedule);
        }
        return list;
    }
}
