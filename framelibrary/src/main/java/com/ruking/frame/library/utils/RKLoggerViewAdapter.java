package com.ruking.frame.library.utils;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruking.frame.library.R;
import com.ruking.frame.library.bean.LoggerTag;
import com.ruking.frame.library.view.ToastUtil;
import com.zhy.autolayout.AutoLinearLayout;

/**
 * @author Ruking.Cheng
 * @descrilbe 日志
 * @email 495095492@qq.com
 * @tel 18075121944
 * @date on 2020-07-28 13:20
 */
public class RKLoggerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;

    public RKLoggerViewAdapter(@NonNull Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.rk_dialog_checkbox, parent, false);
        return new DescViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final DescViewHolder descHolder = (DescViewHolder) holder;
        LoggerTag item = Logger.getLoggerTags().get(position);
        descHolder.mItemImag.setVisibility(View.GONE);
        descHolder.mItemText.setText(item.getTag() + "：" + item.getMsg());
        switch (item.getPriority()) {
            case 4:
                descHolder.mItemText.setTextColor(Color.parseColor("#F44336"));
                break;
            case 3:
                descHolder.mItemText.setTextColor(Color.parseColor("#FF3B4B"));
                break;
            case 2:
                descHolder.mItemText.setTextColor(Color.parseColor("#FFEB3B"));
                break;
            case 1:
                descHolder.mItemText.setTextColor(Color.parseColor("#2196F3"));
                break;
            default:
                descHolder.mItemText.setTextColor(Color.parseColor("#FFFFFF"));
                break;
        }
        descHolder.mItemLayout.setOnClickListener(v -> {
            ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
            if (cm != null) {
                // 创建普通字符型ClipData
                ClipData mClipData = ClipData.newPlainText("Label", descHolder.mItemText.getText());
                // 将ClipData内容放到系统剪贴板里。
                cm.setPrimaryClip(mClipData);
                ToastUtil.show(mContext, "复制成功");
            }
        });
    }

    @Override
    public int getItemCount() {
        return Logger.getLoggerTags().size();
    }


    private static class DescViewHolder extends RecyclerView.ViewHolder {
        private ImageView mItemImag;
        private TextView mItemText;
        private AutoLinearLayout mItemLayout;

        @SuppressLint("CutPasteId")
        DescViewHolder(View itemView) {
            super(itemView);
            mItemImag = itemView.findViewById(R.id.item_imag);
            mItemText = itemView.findViewById(R.id.item_text);
            mItemLayout = itemView.findViewById(R.id.item_layout);
        }
    }

}
