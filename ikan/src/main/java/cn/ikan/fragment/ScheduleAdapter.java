package cn.ikan.fragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.ikan.R;
import cn.ikan.Schedule;

/**
 * Created by SDT13411 on 2018/1/30.
 */

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {

    Context mContext;
    List<Schedule> mList;

    public ScheduleAdapter(Context mContext, List<Schedule> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_schedule, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Schedule schedule = mList.get(i);
        viewHolder.tvTitle.setText(schedule.getMainTitle());
        viewHolder.tvSubTitle.setText(schedule.getSubTitle());
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private TextView tvSubTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.main_title);
            tvSubTitle = (TextView) itemView.findViewById(R.id.sub_title);
        }
    }
}
