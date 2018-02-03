package cn.sdt.weiget.recycle;

import android.view.View;

/**
 * Created by SDT13411 on 2018/1/15.
 */

public interface OnItemLongClickListener {
    boolean onItemLongClick(BaseTvRecyclerView parent, View itemView, int position);
}
