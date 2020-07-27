package com.ruking.frame.library.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ruking.frame.library.R;
import com.ruking.frame.library.bean.LoggerTag;
import com.ruking.frame.library.view.ToastUtil;
import com.ruking.frame.library.view.animation.RKAnimationButton;
import com.zhy.autolayout.AutoFrameLayout;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.List;

/**
 * @author Ruking.Cheng
 * @descrilbe 日志可视化
 * @email 495095492@qq.com
 * @tel 18075121944
 * @date on 2020-07-27 17:01
 */
public class RKLoggerViewUtil {
    private Activity mActivity;
    private TextView mFrameTagText;
    private ScrollView mFrameTagScroll;
    private AutoLinearLayout mFrameTagLayout;
    private LoggerAdapter mLoggerAdapter;

    @SuppressLint("HandlerLeak")
    private Handler eventProcessingHandler = new Handler() {
        public void handleMessage(Message msg) {
            mActivity.runOnUiThread(() -> {
                List<LoggerTag> loggerTags = Logger.getLoggerTags();
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < loggerTags.size(); i++) {
                    LoggerTag loggerTag = loggerTags.get(i);
                    sb.append(loggerTag.getTag()).append("：").append(loggerTag.getMsg());
                    if (i != loggerTags.size() - 1) {
                        sb.append("\n");
                    }
                }
                mFrameTagText.setText(sb.toString());
                mFrameTagScroll.scrollTo(0, mFrameTagText.getBottom());
            });
        }
    };

    @SuppressLint("ClickableViewAccessibility")
    public RKLoggerViewUtil(Activity activity) {
        mActivity = activity;
        RKAnimationButton frameTagBut01 = activity.findViewById(R.id.frame_tag_but01);
        RKAnimationButton frameTagBut02 = activity.findViewById(R.id.frame_tag_but02);
        AutoFrameLayout frameTagTipLayout = activity.findViewById(R.id.frame_tag_tip_layout);
        mFrameTagText = activity.findViewById(R.id.frame_tag_text);
        mFrameTagScroll = activity.findViewById(R.id.frame_tag_scroll);
        mFrameTagLayout = activity.findViewById(R.id.frame_tag_layout);
        frameTagBut01.setOnClickListener(v -> Logger.clearLoggerTagList());
        frameTagBut02.setOnClickListener(v -> {
            String text = mFrameTagText.getText().toString().trim();
            if (!TextUtils.isEmpty(text)) {
                ClipboardManager cm = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
                if (cm != null) {
                    // 创建普通字符型ClipData
                    ClipData mClipData = ClipData.newPlainText("Label", text);
                    // 将ClipData内容放到系统剪贴板里。
                    cm.setPrimaryClip(mClipData);
                    ToastUtil.show(activity, "复制成功");
                }
            }
        });
        int h = RKWindowUtil.getScreenHeight(activity);
        frameTagTipLayout.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                Logger.viewHeight = h - (int) event.getRawY();
                if (Logger.viewHeight < 0) Logger.viewHeight = 0;
                mFrameTagScroll.setLayoutParams(new AutoLinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Logger.viewHeight));
            }
            return true;
        });
        mLoggerAdapter = loggerTags -> {
            eventProcessingHandler.removeMessages(1);
            eventProcessingHandler.sendEmptyMessageDelayed(1, 300);
        };
    }

    public void onResume() {
        if (Logger.isLogEnableView()) {
            Logger.addAdapter(mLoggerAdapter);
            mFrameTagLayout.setVisibility(View.VISIBLE);
            mFrameTagScroll.setLayoutParams(new AutoLinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Logger.viewHeight));
        } else {
            mFrameTagLayout.setVisibility(View.GONE);
        }
    }

    public void onPause() {
        if (Logger.isLogEnableView()) {
            Logger.removeAdapter(mLoggerAdapter);
        }
    }
}
