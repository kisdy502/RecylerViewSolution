package cn.ikan.fragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.ikan.Content;
import cn.ikan.R;
import cn.ikan.SortBean;

/**
 * Created by SDT13411 on 2018/1/30.
 */

public class FreshAdapter extends RecyclerView.Adapter<FreshAdapter.ViewHolder> {

    Context mContext;
    List<SortBean> mList;

    public FreshAdapter(Context mContext, List<SortBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_fresh, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        SortBean bean = mList.get(i);
        viewHolder.tvFromId.setText(String.valueOf(bean.getFromId()));
        viewHolder.tvFromData.setText(bean.getSortContent());
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvFromId;
        private TextView tvFromData;

        public ViewHolder(View itemView) {
            super(itemView);
            tvFromId = (TextView) itemView.findViewById(R.id.from_id);
            tvFromData = (TextView) itemView.findViewById(R.id.from_data);

        }
    }



}
