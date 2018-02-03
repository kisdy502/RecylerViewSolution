package cn.ikan.fragment;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cn.ikan.Block;
import cn.ikan.Content;
import cn.ikan.R;
import cn.ikan.recycle.IKanRecyclerView;

/**
 * Created by SDT13411 on 2018/1/30.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    Context mContext;
    List<Block> blockList;

    public ListAdapter(Context context, List<Block> list) {
        this.mContext = context;
        this.blockList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = null;
        switch (i) {
            case 0:
                view = LayoutInflater.from(mContext).inflate(R.layout.item_list1, viewGroup, false);
                break;
            default:
            case 1:
                view = LayoutInflater.from(mContext).inflate(R.layout.item_list1, viewGroup, false);
                break;
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Block block = blockList.get(i);
        List<Content> contentList = block.getContentList();
        ContentAdapter adapter = new ContentAdapter(mContext, contentList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext,
                LinearLayoutManager.HORIZONTAL, false);
        viewHolder.mRecycleView.setLayoutManager(layoutManager);
        viewHolder.mRecycleView.setAdapter(adapter);

    }

    @Override
    public int getItemCount() {
        return blockList != null ? blockList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        IKanRecyclerView mRecycleView;

        public ViewHolder(View itemView) {
            super(itemView);
            mRecycleView = (IKanRecyclerView) itemView.findViewById(R.id.block_item);
        }
    }
}
