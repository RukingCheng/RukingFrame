package com.ruking.frame.library.view.lfrecyclerview;


import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ruking.frame.library.R;

/**
 * Created by limxing on 16/7/23.
 * <p>
 * https://github.com/limxing
 * Blog: http://www.leefeng.me
 */
public class LFRecyclerViewFooter extends LinearLayout {
    public final static int STATE_NORMAL = 0;
    public final static int STATE_READY = 1;
    public final static int STATE_LOADING = 2;

    private int mState;
    private View mContentView;
    private View mProgressBar;
    private TextView mHintView;
    private View lfrecyclerview_footer_state;

    public LFRecyclerViewFooter(Context context) {
        super(context);
        initView(context);
    }

    public LFRecyclerViewFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public void setContainerBackgroundColor(@ColorInt int color) {
        if (this.mContentView != null)
            this.mContentView.setBackgroundColor(color);
    }

    public int getmState() {
        return mState;
    }


    public void setState(int state) {
        mState = state;
        mHintView.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
        mHintView.setVisibility(View.INVISIBLE);
        if (state == STATE_READY) {
            mHintView.setVisibility(View.VISIBLE);
            mHintView.setText(R.string.lfrecyclerview_footer_hint_ready);
        } else if (state == STATE_LOADING) {
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mHintView.setVisibility(View.VISIBLE);
            mHintView.setText(R.string.lfrecyclerview_footer_hint_normal);
        }
    }

    public void setBottomMargin(int height) {
        if (height < 0) return;
        LayoutParams lp = (LayoutParams) mContentView.getLayoutParams();
        lp.bottomMargin = height;
        mContentView.setLayoutParams(lp);
    }

    public int getBottomMargin() {
        LayoutParams lp = (LayoutParams) mContentView.getLayoutParams();
        return lp.bottomMargin;
    }


    /**
     * normal status
     */
    public void normal() {
        mHintView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
    }


    /**
     * loading status
     */
    public void loading() {
        mHintView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    /**
     * hide footer when disable pull load more
     */
    public void hide() {
        mContentView.setVisibility(View.GONE);
    }

    /**
     * show footer
     */
    public void show() {
        mContentView.setVisibility(View.VISIBLE);
    }

    private void initView(Context context) {
        @SuppressLint("InflateParams")
        LinearLayout moreView = (LinearLayout) LayoutInflater.from(context).inflate(R.layout
                .lfrecyclerview_footer, null);
        addView(moreView);
        moreView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        lfrecyclerview_footer_state = moreView.findViewById(R.id.lfrecyclerview_footer_state);
        lfrecyclerview_footer_state.setVisibility(View.GONE);
        mContentView = moreView.findViewById(R.id.lfrecyclerview_footer_content);
        mProgressBar = moreView.findViewById(R.id.lfrecyclerview_footer_progressbar);
        mHintView = moreView.findViewById(R.id.lfrecyclerview_footer_hint_textview);
    }

    public TextView getmHintView() {
        return mHintView;
    }

    /**
     * 设置是否显示有无数据有好提示
     *
     * @param isNone isNone
     */
    public void setNoneDataState(boolean isNone) {
        if (isNone) {
            lfrecyclerview_footer_state.setVisibility(View.VISIBLE);
        } else {
            lfrecyclerview_footer_state.setVisibility(View.GONE);
        }

    }
}
