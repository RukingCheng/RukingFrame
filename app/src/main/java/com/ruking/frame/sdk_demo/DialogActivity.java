package com.ruking.frame.sdk_demo;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.ruking.frame.library.base.RKBaseBackActivity;
import com.ruking.frame.library.base.RKTransitionMode;
import com.ruking.frame.library.bean.Choice;
import com.ruking.frame.library.widget.RKDialog;
import com.ruking.frame.library.widget.RKDialogButton;
import com.ruking.frame.library.widget.RKDialogCheckBox;
import com.ruking.frame.library.widget.RKDialogChoiceList;
import com.ruking.frame.library.widget.RKDialogProfile;
import com.ruking.frame.library.widget.RKDialogProgress;

import butterknife.OnClick;

/**
 * @author Ruking.Cheng
 * @descrilbe TODO
 * @email 495095492@qq.com
 * @tel 18075121944
 * @date on 2017/12/15 16:36
 */
public class DialogActivity extends RKBaseBackActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
    }

    @SuppressLint("InflateParams")
    @OnClick({R.id.but_01,
            R.id.but_02, R.id.but_03,
            R.id.but_04, R.id.but_05,
            R.id.but_06, R.id.but_07,
            R.id.but_08, R.id.but_09,
            R.id.but_10, R.id.but_11})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.but_01:
                new RKDialog.Builder(activity)
                        .setTitleGravity(Gravity.CENTER)
                        .setMessageGravity(Gravity.CENTER)
                        .setCancelable(false)
                        .setTitle("温馨提示")
                        .setMessage("南方的天气即将变冷，请注意添加衣物")
                        .addButton(new RKDialogButton(activity)
                                .setText("我知道了")
                                .setProfile(new RKDialogProfile()
                                        .setBackgroundColor(Color.parseColor("#ff0000"))
                                        .setTextColorRes(android.R.color.white))
                                .setOnClickListener((dialog, button) -> dialog.dismiss())
                        ).show();
                break;
            case R.id.but_02:
                new RKDialog.Builder(activity)
                        .setTitle("温馨提示")
                        .setMessage("南方的天气即将变冷，确定穿了秋裤吗？")
                        .addButton(new RKDialogButton(activity)
                                .setText("取消")
                                .setOnClickListener((dialog, button) -> dialog.dismiss()))
                        .addButton(new RKDialogButton(activity)
                                .setText("确定")
                                .setProfile(new RKDialogProfile()
                                        .setBackgroundColorRes(R.color.colorPrimary)
                                        .setTextColorRes(android.R.color.white))
                                .setOnClickListener((dialog, button) -> dialog.dismiss()))
                        .show();
                break;
            case R.id.but_03:
                new RKDialog.Builder(activity)
                        .setTitle("照片选择")
                        .setButtonLayoutOrientation(LinearLayout.VERTICAL)
                        .setBottomDisplay(true)
                        .addButton(new RKDialogButton(activity)
                                .setText("拍照")
                                .setProfile(new RKDialogProfile()
                                        .setBackgroundColorRes(R.color.colorPrimary)
                                        .setTextColorRes(android.R.color.white))
                                .setOnClickListener((dialog, button) -> dialog.dismiss())
                        )
                        .addButton(new RKDialogButton(activity)
                                .setText("相册")
                                .setProfile(new RKDialogProfile()
                                        .setBackgroundColorRes(R.color.colorPrimary)
                                        .setTextColorRes(android.R.color.white))
                                .setOnClickListener((dialog, button) -> dialog.dismiss())
                        )
                        .addButton(new RKDialogButton(activity)
                                .setText("取消")
                                .setProfile(new RKDialogProfile()
                                        .setStrokeColor(Color.parseColor("#aaaaaa"))
                                        .setTextColor(Color.parseColor("#aaaaaa")))
                                .setOnClickListener((dialog, button) -> dialog.dismiss())
                        ).show();
                break;
            case R.id.but_04:
                new RKDialog.Builder(activity)
                        .setTitle("温馨提示")
                        .setMessage("南方的天气即将变冷了")
                        .addCheckBox(new RKDialogCheckBox(activity)
                                .setProfile(new RKDialogProfile()
                                        .setTextColorRes(android.R.color.darker_gray)
                                        .setItmeColor(Color.parseColor("#64b54c")))
                                .setText("不再提醒").setTag("不再提醒")
                                .setOnCheckedChangeListener((dialog, checkBox) -> {
                                    //
                                    Log.e("demo", "b=" + checkBox.isChecked());
                                }))
                        .setOnCheckedChangeDismissListener(checkBoxes -> {
                            for (RKDialogCheckBox checkBox : checkBoxes) {
                                Log.e("demo", checkBox.getTag() + "=" + checkBox.isChecked());
                            }
                        })
                        .addButton(new RKDialogButton(activity)
                                .setText("我知道了")
                                .setProfile(new RKDialogProfile()
                                        .setBackgroundColorRes(R.color.colorPrimary)
                                        .setTextColorRes(android.R.color.white))
                                .setOnClickListener((dialog, button) -> {
                                    dialog.startOnCheckedChangeDismissListener();
                                    dialog.dismiss();
                                })
                        ).show();
                break;
            case R.id.but_05:
                new RKDialog.Builder(activity)
                        .setTitle("软件更新")
                        .icon(R.mipmap.ic_launcher)
                        .setDialogProgress(new RKDialogProgress(activity,
                                RKDialogProgress.ProgressType.PROGRESS)
                                .setText("加载中...").setProgress(0)
                                .setProfile(new RKDialogProfile()
                                        .setBackgroundColorRes(android.R.color.white)
                                        .setTextColorRes(android.R.color.darker_gray)
                                        .setItmeColor(Color.parseColor("#ff0000")))
                                .setOnShowListener((dialog, progress) -> new Thread(() -> {
                                    while (progress.getProgress() < progress.getMax()) {
//                                        if (!dialog.isShowing()) {
//                                            break;
//                                        }
                                        progress.setMax(progress.getMax() + 1);
                                        runOnUiThread(
                                                () -> progress.setProgress(progress.getProgress() + 2));
                                        try {
                                            Thread.sleep(50);
                                        } catch (InterruptedException e) {
                                            break;
                                        }
                                    }
                                    runOnUiThread(
                                            () -> progress.setText("OK")
                                    );
                                }).start()))
                        .addButton(new RKDialogButton(activity)
                                .setText("取消")
                                .setImageResource(R.mipmap.ic_launcher)
                                .setProfile(new RKDialogProfile()
                                        .setBackgroundColorRes(R.color.colorPrimary)
                                        .setTextColorRes(android.R.color.white))
                                .setOnClickListener((dialog, button) -> dialog.dismiss()))
                        .show();
                break;
            case R.id.but_06:
                new RKDialog.Builder(activity)
                        .addProfile(new RKDialogProfile(RKDialogProfile.DIALOG_BACKGROUND)
                                        .setStrokeWidth(0)
                                        .setBackgroundColorRes(android.R.color.transparent)
//                                .setRroundCorner(0)
                        )
                        .setDialogProgress(new RKDialogProgress(activity,
                                RKDialogProgress.ProgressType.INDETERMINATE)
                                .setText("加载中...")
                                .setIndicator("LineScalePulseOutIndicator")
                                .setProfile(new RKDialogProfile()
                                        .setBackgroundColor(Color.parseColor("#90000000"))
                                        .setTextColorRes(android.R.color.white)
                                        .setItmeColorRes(android.R.color.white)))
                        .show();
                break;
            case R.id.but_07:
                new RKDialog.Builder(activity)
                        .addProfile(new RKDialogProfile(RKDialogProfile.DIALOG_BACKGROUND)
                                .setStrokeWidth(0)
                                .setBackgroundColorRes(android.R.color.transparent)
                                .setRroundCorner(0)
                        )
                        .setDialogProgress(new RKDialogProgress(activity,
                                RKDialogProgress.ProgressType.INDETERMINATE_HORIZONTAL)
                                .setText("加载中...")
                                .setProfile(new RKDialogProfile()
                                        .setBackgroundColor(Color.parseColor("#90000000"))
                                        .setTextColorRes(android.R.color.white)
                                        .setItmeColorRes(android.R.color.white)))
                        .show();
                break;
            case R.id.but_08:
                new RKDialog.Builder(activity)
                        .setTitle("接口环境")
                        .addProfile(new RKDialogProfile(RKDialogProfile.DIALOG_BACKGROUND)
                                .setRroundCorner(0))
                        .setDialogChoiceList(
                                new RKDialogChoiceList(activity)
                                        .setRadioIconHide(false)
                                        .setChoices(new Choice("开发接口", "这是一个开发的接口"),
                                                new Choice("测试接口", "这是一个测试的接口"),
                                                new Choice("生产接口"))
                                        .setOnSingleChoiceSelectListener((dialog, choice, position) -> {
                                            Log.e("demo", "choice=" + choice.getTitle());
                                            dialog.dismiss();
                                        })
                        ).show();
                break;
            case R.id.but_09:
                new RKDialog.Builder(activity)
                        .setTitle("性别")
                        .addProfile(new RKDialogProfile(RKDialogProfile.DIALOG_BACKGROUND)
                                .setRroundCorner(0))
                        .setDialogChoiceList(
                                new RKDialogChoiceList(activity)
                                        .setChoices(new Choice("男", true),
                                                new Choice("女"),
                                                new Choice("保密")))
                        .setOnMultiselectChoiceSelectListener(choices -> {
                            for (Choice choice : choices)
                                Log.e("demo", choice.getTitle() + "=" + choice.isChecked());
                        })
                        .addButton(new RKDialogButton(activity)
                                .setText("取消")
                                .setOnClickListener((dialog, button) -> dialog.dismiss()))
                        .addButton(new RKDialogButton(activity)
                                .setText("确定")
                                .setProfile(new RKDialogProfile()
                                        .setBackgroundColorRes(R.color.colorPrimary)
                                        .setTextColorRes(android.R.color.white))
                                .setOnClickListener((dialog, button) -> {
                                    dialog.startOnMultiselectChoiceSelectListener();
                                    dialog.dismiss();
                                }))
                        .show();
                break;
            case R.id.but_10:
                new RKDialog.Builder(activity)
                        .setTitle("爱好")
                        .addProfile(new RKDialogProfile(RKDialogProfile.DIALOG_BACKGROUND)
                                .setRroundCorner(0))
                        .setDialogChoiceList(
                                new RKDialogChoiceList(activity)
                                        .setMultiselect(true)
                                        .setProfile(new RKDialogProfile()
                                                .setTextColorRes(android.R.color.darker_gray)
                                                .setItmeColor(Color.parseColor("#ff0000")))
                                        .setChoices(new Choice("打球"),
                                                new Choice("旅游"),
                                                new Choice("开车"),
                                                new Choice("旅游"),
                                                new Choice("看电影")))
                        .setOnMultiselectChoiceSelectListener(choices -> {
                            for (Choice choice : choices)
                                Log.e("demo", choice.getTitle() + "=" + choice.isChecked());
                        })
                        .addButton(new RKDialogButton(activity)
                                .setText("取消")
                                .setOnClickListener((dialog, button) -> dialog.dismiss()))
                        .addButton(new RKDialogButton(activity)
                                .setText("确定")
                                .setProfile(new RKDialogProfile()
                                        .setBackgroundColorRes(R.color.colorPrimary)
                                        .setTextColorRes(android.R.color.white))
                                .setOnClickListener((dialog, button) -> {
                                    dialog.startOnMultiselectChoiceSelectListener();
                                    dialog.dismiss();
                                }))
                        .show();
                break;
            case R.id.but_11:
                new RKDialog.Builder(activity)
                        .addProfile(new RKDialogProfile(RKDialogProfile.DIALOG_BACKGROUND)
                                .setStrokeWidth(0)
                                .setBackgroundColorRes(android.R.color.transparent)
                                .setRroundCorner(0))
                        .setCustomView(
                                LayoutInflater.from(activity)
                                        .inflate(R.layout.nav_header_main, null))
                        .show();
                break;
        }
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected int getStatusBarColor() {
        return ContextCompat.getColor(activity, R.color.colorPrimary);
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return true;
    }

    @Override
    protected RKTransitionMode getOverridePendingTransitionMode() {
        return RKTransitionMode.RIGHT;
    }
}
