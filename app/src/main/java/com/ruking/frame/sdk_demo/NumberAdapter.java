package com.ruking.frame.sdk_demo;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ruking.frame.library.view.galleryWidget.TosGallery;
import com.ruking.frame.library.view.galleryWidget.WheelTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ruking.Cheng
 * @descrilbe TODO
 * @email 495095492@qq.com
 * @tel 18075121944
 * @date on 2018/4/17 下午9:45
 */
public class NumberAdapter extends BaseAdapter {
    private int mHeight = 50;
    private List<String> data;
    private Activity context;

    public NumberAdapter(Activity context) {
        this.context = context;
        this.data = new ArrayList<>();
        mHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mHeight,
                context.getResources().getDisplayMetrics());
    }

    public void setData(List<String> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return getView(position, null, null);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        WheelTextView textView = null;
        if (null == convertView) {
            convertView = new WheelTextView(context);
            convertView.setLayoutParams(new TosGallery.LayoutParams(-1, mHeight));
            textView = (WheelTextView) convertView;
            textView.setTextColor(ContextCompat.getColor(context, android.R.color.black));
            textView.setTextSize(16);
            textView.setGravity(Gravity.CENTER);
        }
        String text = data.get(position);
        if (null == textView) {
            textView = (WheelTextView) convertView;
        }
        textView.setText(text);
        return convertView;
    }
}
