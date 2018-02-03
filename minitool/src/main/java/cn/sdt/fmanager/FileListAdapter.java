package cn.sdt.fmanager;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.sdt.cardview.R;
import cn.sdt.utils.FileIconHelper;

/**
 * Created by SDT13411 on 2018/1/14.
 */

public class FileListAdapter extends RecyclerView.Adapter<FileListAdapter.FileVideHolder> {
    Context mContext;
    List<FileInfo> fileBeans;
    LayoutInflater inflater;

    public FileListAdapter(Context context, List<FileInfo> fileBeans) {
        this.mContext = context;
        this.fileBeans = fileBeans;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public FileVideHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.file_item, parent, false);
        FileVideHolder vh = new FileVideHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(FileVideHolder holder, int position) {
        FileInfo fileBean = fileBeans.get(position);
        if (fileBean.isDirectry) {
            holder.imgFileType.setBackgroundResource(R.drawable.folder);
            holder.fileInfo.setVisibility(View.VISIBLE);
            holder.fileInfo.setText(fileBean.childDirCount + "个文件夹" + "," + fileBean.childFileCount + "个文件");
        } else {
            holder.fileInfo.setVisibility(View.GONE);
            String ext= FileIconHelper.getExtion(fileBean.fileName);
            int resId=FileIconHelper.getFileIcon(ext);
            holder.imgFileType.setBackgroundResource(resId);
        }
        holder.fileName.setText(fileBean.fileName);

    }

    @Override
    public int getItemCount() {
        return fileBeans != null ? fileBeans.size() : 0;
    }

    static class FileVideHolder extends RecyclerView.ViewHolder {
        private ImageView imgFileType;
        private TextView fileName;
        private TextView fileInfo;

        public FileVideHolder(View itemView) {
            super(itemView);
            imgFileType = (ImageView) itemView.findViewById(R.id.file_type);
            fileName = (TextView) itemView.findViewById(R.id.file_name);
            fileInfo = (TextView) itemView.findViewById(R.id.file_info);
        }
    }

}
