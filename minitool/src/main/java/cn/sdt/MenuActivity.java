package cn.sdt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.sdt.app.AppListActivity;
import cn.sdt.cardview.R;
import cn.sdt.fmanager.FileExploreActivity;
import cn.sdt.weiget.recycle.BaseTvRecyclerView;
import cn.sdt.weiget.recycle.FocusFrameView;
import cn.sdt.weiget.recycle.OnItemClickListener;
import cn.sdt.weiget.recycle.OnItemListener;
import cn.sdt.weiget.recycle.TvRecyclerView;

public class MenuActivity extends BaseActivity {


    private TvRecyclerView mTvList;
    private FocusFrameView mFocusFrame;
    private GridLayoutManager mGridLayoutManager;

    private static List<MenuItem> mDatas = new ArrayList<>();

    static {
        mDatas.add(new MenuItem("应用管理", 0));
        mDatas.add(new MenuItem("文件管理", 1));
        mDatas.add(new MenuItem("文件分类", 2));
        mDatas.add(new MenuItem("远程管理", 2));
        mDatas.add(new MenuItem("内存清理", 2));
        mDatas.add(new MenuItem("大文件管理", 2));
    }

    ;
    private MenuAdapter mAdapter;
    private OnItemListener itemListener = new OnItemListener() {
        @Override
        public boolean onItemPreSelected(BaseTvRecyclerView parent, View itemView, int position) {
            return false;
        }

        @Override
        public boolean onItemSelected(BaseTvRecyclerView parent, View itemView, int position) {
            return false;
        }

        @Override
        public boolean onReviseFocusFollow(BaseTvRecyclerView parent, View itemView, int position) {
            return false;
        }
    };
    private OnItemClickListener itemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(BaseTvRecyclerView parent, View itemView, int position) {
            startBypos(position);
        }
    };

    private void startBypos(int position) {
        if (position == 0) {
            Intent intent = new Intent(this, AppListActivity.class);
            startActivity(intent);
        } else if (position == 1) {
            Intent intent = new Intent(this, FileExploreActivity.class);
            startActivity(intent);
        } else if (position == 2) {
        } else if (position == 3) {

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        initView();
    }


    private void initView() {
        mTvList = ((TvRecyclerView) findViewById(R.id.trv));
        mFocusFrame = ((FocusFrameView) findViewById(R.id.frame));
        mTvList.initFrame(mFocusFrame, R.drawable.fly_border, getResources().getDimensionPixelSize(R.dimen.h6));
        mGridLayoutManager = new GridLayoutManager(this, 3, GridLayoutManager.HORIZONTAL, false);
        mGridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int spansize = 1;
                switch (mAdapter.getItemViewType(position)) {
                    case 0:
                        spansize = 3;
                        break;
                    case 1:
                        spansize = 2;
                        break;
                    case 2:
                    default:
                        spansize = 1;
                        break;
                }
                return spansize;
            }
        });

        mTvList.setLayoutManager(mGridLayoutManager);
        mTvList.setOnItemListener(itemListener);
        mTvList.setOnItemClickListener(itemClickListener);
        mAdapter = new MenuAdapter();
        mTvList.setAdapter(mAdapter);
    }


    class MenuAdapter extends TvRecyclerView.Adapter<MenuActivity.MenuHolder> {
        @Override
        public MenuHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView;
            LayoutInflater inflater = LayoutInflater.from(MenuActivity.this);
            switch (viewType) {
                case 0:
                    itemView = inflater.inflate(R.layout.menu_item_high_layout, parent, false);
                    break;
                case 1:
                    itemView = inflater.inflate(R.layout.menu_item_mid_layout, parent, false);
                    break;
                case 2:
                default:
                    itemView = inflater.inflate(R.layout.menu_item_layout, parent, false);
                    break;
                //设置监听
            }

            return new MenuHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MenuHolder holder, int position) {
            ((TextView) holder.itemView).setText(mDatas.get(position).data);
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        @Override
        public int getItemViewType(int position) {
            return mDatas.get(position).itemType;
        }
    }

    static class MenuItem {

        public MenuItem(String data, int itemType) {
            this.data = data;
            this.itemType = itemType;
        }

        String data;
        int itemType;
    }

    class MenuHolder extends RecyclerView.ViewHolder {
        public MenuHolder(View itemView) {
            super(itemView);
        }
    }
}
