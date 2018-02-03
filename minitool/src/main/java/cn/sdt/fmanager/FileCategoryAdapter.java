package cn.sdt.fmanager;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import cn.sdt.cardview.R;

/**
 * Created by SDT13411 on 2018/1/23.
 */

public class FileCategoryAdapter extends RecyclerView.Adapter<FileCategoryAdapter.ViewHolder> {

    private Context mContext;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView categroy_icon;
        TextView category_name;
        TextView category_count;

        public ViewHolder(View itemView) {
            super(itemView);
            categroy_icon= (ImageView) itemView.findViewById(R.id.categoty_icon);
            category_name= (TextView) itemView.findViewById(R.id.category_name);
            category_count= (TextView) itemView.findViewById(R.id.category_count);
        }
    }
}
