package com.ruking.frame.library.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ruking.frame.library.R;
import com.wang.avi.AVLoadingIndicatorView;

import me.zhanghai.android.materialprogressbar.HorizontalProgressDrawable;
import me.zhanghai.android.materialprogressbar.IndeterminateHorizontalProgressDrawable;

/**
 * @author Ruking.Cheng
 * @descrilbe 对话框添加Progress
 * @email 495095492@qq.com
 * @tel 18075121944
 * @date on 12/8 16:46
 */
public class RKDialogProgress {
    protected final Context context;
    protected final ProgressType type;
    private TextView mdContent;
    private View progressView;
    private TextView mdLabel;
    private TextView mdMinMax;
    private View layout;
    private RKDialogProfile profile;
    private Object tag;
    private int max = 100;
    private boolean isShowMinMax = true;
    private int progress;
    private RKDialogListener.OnShowListener onShowListener;


    @SuppressLint("InflateParams")
    public RKDialogProgress(@NonNull Context context, @NonNull ProgressType type) {
        this.context = context;
        this.type = type;
        switch (type) {
            case PROGRESS:
                layout = LayoutInflater.from(context).inflate(R.layout.rk_dialog_progress, null);
                break;
            case INDETERMINATE:
                layout = LayoutInflater.from(context).inflate(R.layout.rk_dialog_progress_indeterminate, null);
                break;
            default:
                layout = LayoutInflater.from(context).inflate(R.layout.rk_dialog_progress_indeterminate_horizontal, null);
                break;
        }
        try {
            mdContent = layout.findViewById(R.id.md_content);
            progressView = layout.findViewById(android.R.id.progress);
            mdLabel = layout.findViewById(R.id.md_label);
            mdMinMax = layout.findViewById(R.id.md_minMax);
        } catch (Exception ignored) {
        }
    }

    public ProgressType getType() {
        return type;
    }

    public RKDialogProgress setIndicator(String indicatorName) {
        if (progressView instanceof AVLoadingIndicatorView)
            ((AVLoadingIndicatorView) progressView).setIndicator(indicatorName);
        return this;
    }

    @SuppressLint("ObsoleteSdkInt")
    public View getView() {
        if (profile != null) {
            profile.setBackgroundProfile(context, layout);
            profile.setTextProfile(context, mdContent);
            if (mdLabel != null && mdMinMax != null) {
                profile.setTextProfile(context, mdLabel);
                profile.setTextProfile(context, mdMinMax);
                if (profile.getTextSize() != -1) {
                    mdLabel.setTextSize(profile.getTextSize() * 3 / 4);
                    mdMinMax.setTextSize(profile.getTextSize() * 3 / 4);
                }
            }
        }
        if (progressView instanceof ProgressBar) {
            ((ProgressBar) progressView).setMax(max);
        }
        if (mdMinMax != null)
            if (isShowMinMax) {
                mdMinMax.setVisibility(View.VISIBLE);
            } else {
                mdMinMax.setVisibility(View.INVISIBLE);
            }
        if (type == ProgressType.INDETERMINATE) {
            if (profile != null && profile.getItmeColor() != -1) {
                ((AVLoadingIndicatorView) progressView).setIndicatorColor(profile.getItmeColor());
            } else if (profile != null && profile.getItmeColorRes() != -1) {
                ((AVLoadingIndicatorView) progressView).setIndicatorColor(
                        ContextCompat.getColor(context, profile.getItmeColorRes()));
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                if (type == ProgressType.INDETERMINATE_HORIZONTAL) {
                    IndeterminateHorizontalProgressDrawable d =
                            new IndeterminateHorizontalProgressDrawable(context);
                    if (profile != null && profile.getItmeColor() != -1) {
                        d.setTint(profile.getItmeColor());
                    } else if (profile != null && profile.getItmeColorRes() != -1) {
                        d.setTint(ContextCompat.getColor(context, profile.getItmeColorRes()));
                    }
                    ((ProgressBar) progressView).setIndeterminate(true);
                    ((ProgressBar) progressView).setProgressDrawable(d);
                    ((ProgressBar) progressView).setIndeterminateDrawable(d);
                } else {
                    HorizontalProgressDrawable d = new HorizontalProgressDrawable(context);
                    if (profile != null && profile.getItmeColor() != -1) {
                        d.setTint(profile.getItmeColor());
                    } else if (profile != null && profile.getItmeColorRes() != -1) {
                        d.setTint(ContextCompat.getColor(context, profile.getItmeColorRes()));
                    }
                    ((ProgressBar) progressView).setProgressDrawable(d);
                    ((ProgressBar) progressView).setIndeterminateDrawable(d);
                }
            } else {
                if (profile != null && profile.getItmeColorRes() != -1) {
                    profile.setItmeColor(ContextCompat.getColor(context, profile.getItmeColorRes()));
                }
                if (profile != null && profile.getItmeColor() != -1) {
                    ColorStateList sl = ColorStateList.valueOf(profile.getItmeColor());
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        ((ProgressBar) progressView).setProgressTintList(sl);
                        ((ProgressBar) progressView).setSecondaryProgressTintList(sl);
                        ((ProgressBar) progressView).setIndeterminateTintList(sl);
                    } else {
                        PorterDuff.Mode mode = PorterDuff.Mode.SRC_IN;
                        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD_MR1) {
                            mode = PorterDuff.Mode.MULTIPLY;
                        }
                        if (((ProgressBar) progressView).getIndeterminateDrawable() != null) {
                            ((ProgressBar) progressView).getIndeterminateDrawable().setColorFilter(profile.getItmeColor(), mode);
                        }
                        if (((ProgressBar) progressView).getProgressDrawable() != null) {
                            ((ProgressBar) progressView).getProgressDrawable().setColorFilter(profile.getItmeColor(), mode);
                        }
                    }
                }
            }
        }
        return layout;
    }

    @SuppressLint("SetTextI18n")
    public RKDialogProgress setProgress(int progress) {
        this.progress = progress;
        if (type == ProgressType.PROGRESS) {
            int p = (int) ((double) progress / (double) max * 100.0);
            mdLabel.setText(p + "%");
            mdMinMax.setText(progress + "/" + max);
            if (progressView instanceof ProgressBar) {
                ((ProgressBar) progressView).setProgress(progress);
            }
        }
        return this;
    }

    public RKDialogListener.OnShowListener getOnShowListener() {
        return onShowListener;
    }

    public RKDialogProgress setOnShowListener(RKDialogListener.OnShowListener onShowListener) {
        this.onShowListener = onShowListener;
        return this;
    }

    public int getProgress() {
        return progress;
    }

    public RKDialogProgress setContentGravity(int gravity) {
        mdContent.setGravity(gravity);
        return this;
    }

    public int getMax() {
        return max;
    }

    public RKDialogProgress setMax(int max) {
        this.max = max;
        if (progressView instanceof ProgressBar) {
            ((ProgressBar) progressView).setMax(max);
        }
        return this;
    }

    public boolean isShowMinMax() {
        return isShowMinMax;
    }

    public RKDialogProgress setShowMinMax(boolean showMinMax) {
        isShowMinMax = showMinMax;
        return this;
    }

    public RKDialogProfile getProfile() {
        return profile;
    }

    public RKDialogProgress setProfile(RKDialogProfile profile) {
        this.profile = profile;
        return this;
    }

    public RKDialogProgress setText(@StringRes int text) {
        if (text == 0) {
            return this;
        }
        setText(this.context.getText(text));
        return this;
    }

    public RKDialogProgress setText(@NonNull CharSequence text) {
        mdContent.setText(text);
        return this;
    }

    public Object getTag() {
        return tag;
    }

    public RKDialogProgress setTag(Object tag) {
        this.tag = tag;
        return this;
    }

    public enum ProgressType {
        PROGRESS, INDETERMINATE, INDETERMINATE_HORIZONTAL
    }
}
