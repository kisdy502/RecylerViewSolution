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

/**
 * Created by SDT13411 on 2018/1/30.
 */

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ViewHolder> {

    Context mContext;
    List<Content> mList;

    public ContentAdapter(Context mContext, List<Content> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_content, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Content content = mList.get(i);
        viewHolder.tvTitle.setText(content.getContentTitle());
        viewHolder.tvDesc.setText(content.getContentDesc());
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private TextView tvDesc;
        private TextView tvLevel;
        private TextView tvTag;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.title);
            tvDesc = (TextView) itemView.findViewById(R.id.desc);
            tvLevel = (TextView) itemView.findViewById(R.id.level);
            tvTag = (TextView) itemView.findViewById(R.id.tag);
        }
    }
}
