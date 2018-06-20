package com.ruking.frame.sdk_demo;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.ruking.frame.library.base.RKBaseActivity;
import com.ruking.frame.library.base.RKTransitionMode;
import com.ruking.frame.library.view.galleryWidget.WheelTextView;
import com.ruking.frame.library.view.galleryWidget.WheelView;
import com.zhy.autolayout.AutoLinearLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Ruking.Cheng
 * @descrilbe TODO
 * @email 495095492@qq.com
 * @tel 18075121944
 * @date on 2018/4/15 下午6:34
 */
@SuppressLint("SimpleDateFormat")
public class CategoryActivity extends RKBaseActivity {
    @BindView(R.id.time_start)
    TextView timeStart;
    @BindView(R.id.time_star_x)
    View timeStarX;
    @BindView(R.id.time_end)
    TextView timeEnd;
    @BindView(R.id.time_end_x)
    View timeEndX;
    @BindView(R.id.wheel_y)
    WheelView wheelY;
    @BindView(R.id.wheel_h)
    WheelView wheelH;
    @BindView(R.id.wheel_d)
    WheelView wheelD;
    @BindView(R.id.wheel_layout)
    AutoLinearLayout wheelLayout;
    private boolean start, end;
    private List<String> years, months, days;
    private String year = "", month = "", day = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        ButterKnife.bind(this);
        setSlidr();
        timeStart.setText("开始时间");
        timeEnd.setText("结束时间");
        showWheel();
    }

    private void showWheel() {
        SimpleDateFormat y = new SimpleDateFormat("yyyy");
        SimpleDateFormat m = new SimpleDateFormat("MM");
        SimpleDateFormat d = new SimpleDateFormat("dd");
        year = y.format(new Date());
        month = m.format(new Date());
        day = d.format(new Date());
        getYears();
        getMonths();
        getDays();
        wheelY.setScrollCycle(false);
        wheelH.setScrollCycle(false);
        wheelD.setScrollCycle(false);
        // 获取当前的年
        NumberAdapter hA = new NumberAdapter(activity);
        NumberAdapter mA = new NumberAdapter(activity);
        NumberAdapter tA = new NumberAdapter(activity);
        wheelY.setAdapter(hA);
        wheelH.setAdapter(mA);
        wheelD.setAdapter(tA);
        hA.setData(years);
        mA.setData(months);
        tA.setData(days);
        wheelY.setSelection(years.size() - 1, true);
        wheelH.setSelection(months.size() - 1, true);
        wheelD.setSelection(days.size() - 1, true);
        year = years.get(wheelY.getSelectedItemPosition());
        month = months.get(wheelH.getSelectedItemPosition());
        day = days.get(wheelD.getSelectedItemPosition());
        wheelY.setUnselectedAlpha(0.5f);
        wheelH.setUnselectedAlpha(0.5f);
        wheelD.setUnselectedAlpha(0.5f);
        if (wheelY.getSelectedView() != null)
            ((WheelTextView) wheelY.getSelectedView()).setTextSize(16);
        if (wheelH.getSelectedView() != null)
            ((WheelTextView) wheelH.getSelectedView()).setTextSize(16);
        if (wheelD.getSelectedView() != null)
            ((WheelTextView) wheelD.getSelectedView()).setTextSize(16);
        wheelY.setOnEndFlingListener(v -> {
            year = years.get(wheelY.getSelectedItemPosition());
            getMonths();
            getDays();
            mA.setData(months);
            tA.setData(days);
            month = months.get(wheelH.getSelectedItemPosition() > months.size() - 1 ? months.size() -
                    1 : wheelH.getSelectedItemPosition());
            day = days.get(wheelD.getSelectedItemPosition() > days.size() - 1 ? days.size() - 1 : wheelD.getSelectedItemPosition());
            showWheelTv();
        });
        wheelH.setOnEndFlingListener(v -> {
            month = months.get(wheelH.getSelectedItemPosition());
            getDays();
            tA.setData(days);
            day = days.get(wheelD.getSelectedItemPosition() > days.size() - 1 ? days.size() - 1 : wheelD.getSelectedItemPosition());
            showWheelTv();
        });
        wheelD.setOnEndFlingListener(v -> {
            day = days.get(wheelD.getSelectedItemPosition());
            showWheelTv();
        });
    }

    @SuppressLint("SetTextI18n")
    private void showWheelTv() {
        if (start) {
            timeStart.setText(year + "-" + month + "-" + day);
        } else if (end) {
            timeEnd.setText(year + "-" + month + "-" + day);
        }
        showTimeText();
    }

    private void showTimeText() {
        showTimeText(timeStart, timeStarX, start);
        showTimeText(timeEnd, timeEndX, end);
    }

    private void showTimeText(TextView tv, View view, boolean b) {
        if (b) {
            tv.setTextColor(ContextCompat.getColor(activity, R.color.colorAccent));
            view.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorAccent));
        } else {
            tv.setTextColor(Color.parseColor("#bec8d9"));
            view.setBackgroundColor(Color.parseColor("#bec8d9"));
        }
    }

    public void getYears() {
        SimpleDateFormat y = new SimpleDateFormat("yyyy");
        int yy = Integer.valueOf(y.format(new Date()));
        years = new ArrayList<>();
        for (int i = yy - 9; i <= yy; i++) {
            years.add(i + "");
        }
    }

    public void getMonths() {
        SimpleDateFormat y = new SimpleDateFormat("yyyy");
        SimpleDateFormat m = new SimpleDateFormat("MM");
        int yy = Integer.valueOf(y.format(new Date()));
        int mm = Integer.valueOf(m.format(new Date()));
        int mmm = 12;
        if (Integer.valueOf(year) == yy) {
            mmm = mm;
        }
        months = new ArrayList<>();
        for (int i = 1; i <= mmm; i++) {
            months.add(i + "");
        }
    }

    public void getDays() {
        SimpleDateFormat y = new SimpleDateFormat("yyyy");
        SimpleDateFormat m = new SimpleDateFormat("MM");
        SimpleDateFormat d = new SimpleDateFormat("dd");
        int yy = Integer.valueOf(y.format(new Date()));
        int mm = Integer.valueOf(m.format(new Date()));
        int dd = Integer.valueOf(d.format(new Date()));
        int ddd = getDaysByYearMonth(Integer.valueOf(year), Integer.valueOf(month));
        if (Integer.valueOf(year) == yy && Integer.valueOf(month) == mm) {
            ddd = dd;
        }
        days = new ArrayList<>();
        for (int i = 1; i <= ddd; i++) {
            days.add(i + "");
        }
    }

    public int getDaysByYearMonth(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        return a.get(Calendar.DATE);
    }

    @Override
    public boolean isRxBusHere() {
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

    @OnClick({R.id.time_start_layout, R.id.time_end_layout, R.id.img_d})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.time_start_layout:
                start = true;
                end = false;
                showWheelTv();
                wheelLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.time_end_layout:
                start = false;
                end = true;
                showWheelTv();
                wheelLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.img_d:
                if (start) {
                    timeStart.setText("开始时间");
                } else if (end) {
                    timeEnd.setText("结束时间");
                }
                start = false;
                end = false;
                showTimeText();
                wheelLayout.setVisibility(View.INVISIBLE);
                break;
        }
    }
}
