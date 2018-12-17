package com.ruking.frame.sdk_demo.recycleviewdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ruking.frame.library.base.RKBaseActivity;
import com.ruking.frame.library.base.RKTransitionMode;
import com.ruking.frame.library.view.lfrecyclerview.LFRecyclerView;
import com.ruking.frame.library.view.lfrecyclerview.OnItemClickListener;
import com.ruking.frame.library.widget.adapter.DividerLineDecoration;
import com.ruking.frame.sdk_demo.R;

import java.util.ArrayList;


/**
 * Created by limxing on 16/7/23.
 * <p/>
 * https://github.com/limxing
 * Blog: http://www.leefeng.me
 */
public class LFRecyclerViewActivity extends RKBaseActivity implements OnItemClickListener, LFRecyclerView
        .LFRecyclerViewListener, LFRecyclerView.LFRecyclerViewScrollChange {
    private LFRecyclerView recycleview;
    private boolean b;
    private ArrayList<String> list;
    private MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        setSlidr();
        list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("leefeng.me" + i);
        }

        recycleview = findViewById(R.id.recycleview);
        recycleview.addItemDecoration(new DividerLineDecoration(activity));
        recycleview.getItemAnimator().setChangeDuration(0);
        recycleview.setLoadMore(true);
        recycleview.setRefresh(true);
        recycleview.setNoDateShow();
        recycleview.setAutoLoadMore(true);
        recycleview.setOnItemClickListener(this);
        recycleview.setLFRecyclerViewListener(this);
        recycleview.setScrollChangeListener(this);
        recycleview.setItemAnimator(new DefaultItemAnimator());
        adapter = new MainAdapter(list);
        recycleview.setAdapter(adapter);

        TextView tv = new TextView(LFRecyclerViewActivity.this);
        tv.setText("这是头部");
        tv.setTextColor(Color.WHITE);
        tv.setGravity(Gravity.CENTER);
        tv.setBackgroundColor(Color.RED);
        recycleview.setHeaderView(tv);
        tv = new TextView(LFRecyclerViewActivity.this);
        tv.setText("这是底部");
        tv.setTextColor(Color.WHITE);
        tv.setGravity(Gravity.CENTER);
        tv.setBackgroundColor(Color.RED);
        recycleview.setFootView(tv);
    }

    @Override
    public void onClick(int position) {
        Toast.makeText(this, "" + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLongClick(int po) {
        Toast.makeText(this, "Long:" + po, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean isBindEventBusHere() {
        return false;
    }

    @Override
    public int getStatusBarColor() {
        return ContextCompat.getColor(activity, R.color.colorPrimaryDark);
    }

    @Override
    public int getStatusBarPlaceColor() {
        return 0;
    }

    @Override
    public boolean isShowStatusBarPlaceColor() {
        return false;
    }

    @Override
    public boolean isWindowSetting() {
        return false;
    }

    @Override
    public boolean toggleOverridePendingTransition() {
        return true;
    }

    @Override
    public RKTransitionMode getOverridePendingTransitionMode() {
        return RKTransitionMode.RIGHT;
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(() -> {
            b = !b;
            list.add(0, "leefeng.me" + "==onRefresh");
            recycleview.stopRefresh(b);
            adapter.notifyItemInserted(0);
            adapter.notifyItemRangeChanged(0, list.size());

//            recycleview.setLoadMore(true);

        }, 2000);
    }

    @Override
    public void onLoadMore() {
        new Handler().postDelayed(() -> {
            recycleview.stopLoadMore();
            list.add(list.size(), "leefeng.me" + "==onLoadMore");
//                list.add(list.size(), "leefeng.me" + "==onLoadMore");
            adapter.notifyItemRangeInserted(list.size() - 1, 1);

//            recycleview.setLoadMore(false);
        }, 2000);
    }

    @Override
    public void onRecyclerViewScrollChange(View view, int i, int i1) {

    }
}
