package com.ruking.frame.library.widget;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.annotation.UiThread;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ruking.frame.library.R;
import com.ruking.frame.library.view.animation.RKAnimationLinearLayout;
import com.ruking.frame.library.widget.adapter.DividerLineDecoration;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Ruking.Cheng
 * @descrilbe 自定义Dialog
 * @email 495095492@qq.com
 * @tel 18075121944
 * @date on 12/8 10:40
 */
public class RKDialog extends AlertDialog {
    private final Builder builder;
    private ImageView dialogIcon;
    private AutoLinearLayout dialogTitleLayout;
    private TextView dialogTitle;
    private View dialogTitleLine;
    private TextView dialogMessage;
    private ScrollView dialogMessageLayout;
    private RecyclerView dialogListMessage;
    private AutoLinearLayout dialogCustom;
    private AutoLinearLayout dialogCheckBox;
    private View dialogButtonLine;
    private AutoLinearLayout dialogButtonLayout;
    private RKAnimationLinearLayout dialogBackgroundLayout;

    private RKDialog(Builder builder) {
        super(builder.context);
        this.builder = builder;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rk_dialog);
        findViews();
        Window window = getWindow();
        if (builder.isBottomDisplay) {
            if (window != null) {
                window.setBackgroundDrawableResource(android.R.color.transparent);
                window.setGravity(Gravity.BOTTOM);
                window.setWindowAnimations(R.style.RKAnimationDialogStyle);
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            }
            dialogBackgroundLayout.getRKViewAnimationBase().setRroundCorner(0);
        } else {
            if (window != null) {
                window.setBackgroundDrawableResource(android.R.color.transparent);
                window.setWindowAnimations(R.style.RKDialogStyle);
            }
        }
        if (window != null && builder.typeSystemAlert != -1) {
            window.setType(builder.typeSystemAlert);
        }
        setCanceledOnTouchOutside(builder.canceledOnTouchOutside);
        setCancelable(builder.cancelable);
        if (builder.onDismissListener != null)
            setOnDismissListener(builder.onDismissListener);
        showProfile();
        addTitle();
        addMessageText();
        addMessageList();
        addCustomView();
        addDialogProgress();
        addCheckBoxs();
        addButtons();
    }

    /**
     * 添加list
     */
    private void addMessageList() {
        if (builder.dialogChoiceList != null) {
            showButtonLine();
            dialogListMessage.setVisibility(View.VISIBLE);
            dialogListMessage.setLayoutManager(new LinearLayoutManager(getContext()));
            dialogListMessage.addItemDecoration(new DividerLineDecoration(getContext()));
            dialogListMessage.getItemAnimator().setChangeDuration(0);
            dialogListMessage.setAdapter(builder.dialogChoiceList.getAdapter(this));
        }
    }

    /**
     * 添加自定义View
     */
    private void addCustomView() {
        if (builder.customView != null) {
            showButtonLine();
            dialogCustom.setVisibility(View.VISIBLE);
            dialogCustom.addView(builder.customView);
        }
    }

    /**
     * 添加进度条
     */
    private void addDialogProgress() {
        if (builder.dialogProgress != null) {
            showButtonLine();
            RKDialogProgress dialogProgress = builder.dialogProgress;
            if (dialogProgress.getType() == RKDialogProgress.ProgressType.PROGRESS) {
                if (dialogProgress.getOnShowListener() != null) {
                    dialogProgress.getOnShowListener().onShow(this, dialogProgress);
                }
            }
            dialogCustom.setVisibility(View.VISIBLE);
            dialogCustom.addView(dialogProgress.getView());
        }
    }

    /**
     * 添加标题
     */
    private void addTitle() {
        dialogTitleLayout.setGravity(builder.titleGravity);
        if (builder.icon != -1) {
            dialogTitleLayout.setVisibility(View.VISIBLE);
            dialogTitleLine.setVisibility(View.VISIBLE);
            dialogIcon.setVisibility(View.VISIBLE);
            dialogIcon.setImageResource(builder.icon);
        }
        if (builder.title != null && builder.title.length() > 0) {
            dialogTitleLine.setVisibility(View.VISIBLE);
            dialogTitleLayout.setVisibility(View.VISIBLE);
            dialogTitle.setVisibility(View.VISIBLE);
            dialogTitle.setText(builder.title);
        }
    }

    /**
     * 添加文本形式的内容体
     */
    private void addMessageText() {
        if (builder.message != null && builder.message.length() > 0) {
            showButtonLine();
            dialogMessageLayout.setVisibility(View.VISIBLE);
            dialogMessage.setText(builder.message);
            dialogMessage.setGravity(builder.messageGravity);
        }
    }

    /**
     * 添加底部按钮集合
     */
    private void addButtons() {
        if (builder.buttons.size() > 0) {
            if (builder.buttons.size() > 3) {
                builder.buttonLayoutOrientation = LinearLayout.VERTICAL;
            }
            dialogButtonLayout.setOrientation(builder.buttonLayoutOrientation);
            dialogButtonLayout.setVisibility(View.VISIBLE);
            for (RKDialogButton rkDialogButton : builder.buttons) {
                View button = rkDialogButton.getView();
                AutoLinearLayout.LayoutParams lp;
                if (builder.buttonLayoutOrientation == LinearLayout.HORIZONTAL) {
                    lp = new AutoLinearLayout.LayoutParams(
                            0, AutoUtils.getPercentWidthSizeBigger(rkDialogButton.getHeight()));
                    lp.weight = 1;
                } else {
                    lp = new AutoLinearLayout.LayoutParams(
                            AutoLinearLayout.LayoutParams.MATCH_PARENT,
                            AutoUtils.getPercentWidthSizeBigger(rkDialogButton.getHeight()));
                }
                int mar = AutoUtils.getPercentWidthSizeBigger(rkDialogButton.getMargins());
                lp.setMargins(mar, mar, mar, mar);
                button.setLayoutParams(lp);
                button.setOnClickListener(view -> {
                    if (rkDialogButton.getOnClickListener() != null)
                        rkDialogButton.getOnClickListener().onClick(this, rkDialogButton);
                });
                button.setOnLongClickListener(view ->
                        rkDialogButton.getOnLongClickListener() == null
                                || rkDialogButton.getOnLongClickListener().onLongClick(this, rkDialogButton)
                );
                dialogButtonLayout.addView(button);
            }
        }
    }

    /**
     * 添加复选框
     */
    private void addCheckBoxs() {
        if (builder.checkBoxes.size() > 0) {
            showButtonLine();
            if (builder.checkBoxes.size() > 3) {
                builder.checkBoxOrientation = LinearLayout.VERTICAL;
            }
            dialogCheckBox.setOrientation(builder.checkBoxOrientation);
            dialogCheckBox.setVisibility(View.VISIBLE);
            for (RKDialogCheckBox rkDialogCheckBox : builder.checkBoxes) {
                View checkBox = rkDialogCheckBox.getView();
                AutoLinearLayout.LayoutParams lp;
                if (builder.checkBoxOrientation == LinearLayout.HORIZONTAL) {
                    lp = new AutoLinearLayout.LayoutParams(
                            0, AutoLinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.weight = 1;
                } else {
                    lp = new AutoLinearLayout.LayoutParams(
                            AutoLinearLayout.LayoutParams.MATCH_PARENT,
                            AutoLinearLayout.LayoutParams.WRAP_CONTENT);
                }
                int mar = AutoUtils.getPercentWidthSizeBigger(rkDialogCheckBox.getMargins());
                lp.setMargins(mar, mar, mar, mar);
                checkBox.setLayoutParams(lp);
                checkBox.setOnClickListener(v -> {
                            if (rkDialogCheckBox.isClickable()) {
                                rkDialogCheckBox.setChecked(!rkDialogCheckBox.isChecked());
                                if (rkDialogCheckBox.getOnCheckedChangeListener() != null) {
                                    rkDialogCheckBox.getOnCheckedChangeListener().onCheckedChanged(this, rkDialogCheckBox);
                                }
                            }
                        }
                );
                dialogCheckBox.addView(checkBox);
            }
        }
    }

    /**
     * 当有复选框条目时，在确认关闭的时候需要调用此方法才能启动回调函数，否则不会调用
     */
    public void startOnCheckedChangeDismissListener() {
        if (builder.onCheckedChangeDismissListener != null && builder.checkBoxes.size() > 0)
            builder.onCheckedChangeDismissListener.onCheckedChangedDismiss(builder.checkBoxes);
    }

    /**
     * 如果列表是多选，需要在确认时调用
     */
    public void startOnMultiselectChoiceSelectListener() {
        if (builder.onMultiselectChoiceSelectListener != null && builder.dialogChoiceList != null)
            builder.onMultiselectChoiceSelectListener.onSelect(builder.dialogChoiceList.getChoices());
    }

    /**
     * 处理配置的一些背景颜色和文字颜色、大小
     */
    private void showProfile() {
        for (RKDialogProfile profile : builder.profiles) {
            switch (profile.getType()) {
                case RKDialogProfile.TITLE_BACKGROUND: {
                    profile.setBackgroundProfile(this.getContext(), dialogTitle);
                    profile.setTextProfile(this.getContext(), dialogTitle);
                    if (profile.getStrokeColor() != -1) {
                        dialogTitleLine.setBackgroundColor(profile.getStrokeColor());
                    } else if (profile.getStrokeColorRes() != -1) {
                        dialogTitleLine.setBackgroundColor(ContextCompat.getColor(this.getContext(), profile.getStrokeColorRes()));
                    }
                }
                break;
                case RKDialogProfile.MESSAGE_BACKGROUND: {
                    profile.setBackgroundProfile(this.getContext(), dialogMessageLayout);
                    profile.setBackgroundProfile(this.getContext(), dialogListMessage);
                    profile.setTextProfile(this.getContext(), dialogMessage);
                }
                break;
                case RKDialogProfile.CUSTOM_BACKGROUND: {
                    profile.setBackgroundProfile(this.getContext(), dialogCustom);
                }
                break;
                case RKDialogProfile.CHECKBOX_BACKGROUND: {
                    profile.setBackgroundProfile(this.getContext(), dialogCheckBox);
                }
                break;
                case RKDialogProfile.BUTTON_BACKGROUND: {
                    profile.setBackgroundProfile(this.getContext(), dialogButtonLayout);
                    if (profile.getStrokeColor() != -1) {
                        dialogButtonLine.setBackgroundColor(profile.getStrokeColor());
                    } else if (profile.getStrokeColorRes() != -1) {
                        dialogButtonLine.setBackgroundColor(ContextCompat.getColor(this.getContext(), profile.getStrokeColorRes()));
                    }
                }
                break;
                case RKDialogProfile.DIALOG_BACKGROUND: {
                    profile.setBackgroundProfile(this.getContext(), dialogBackgroundLayout);
                    profile.setRKViewProfile(this.getContext(), dialogBackgroundLayout.getRKViewAnimationBase());
                }
                break;
            }
        }
    }

    /**
     * 是否显示底部按钮上面的线（通过按钮集合的长度）
     */
    private void showButtonLine() {
        if (builder.buttons.size() > 0)
            dialogButtonLine.setVisibility(View.VISIBLE);
    }

    private void findViews() {
        dialogTitle = (TextView) findViewById(R.id.dialog_title);
        dialogTitleLine = findViewById(R.id.dialog_title_line);
        dialogMessage = (TextView) findViewById(R.id.dialog_message);
        dialogMessageLayout = (ScrollView) findViewById(R.id.dialog_message_layout);
        dialogListMessage = (RecyclerView) findViewById(R.id.dialog_list_message);
        dialogCustom = (AutoLinearLayout) findViewById(R.id.dialog_custom);
        dialogCheckBox = (AutoLinearLayout) findViewById(R.id.dialog_checkbox);
        dialogButtonLine = findViewById(R.id.dialog_button_line);
        dialogButtonLayout = (AutoLinearLayout) findViewById(R.id.dialog_button_layout);
        dialogBackgroundLayout = (RKAnimationLinearLayout) findViewById(R.id.dialog_background_layout);
        dialogIcon = (ImageView) findViewById(R.id.dialog_icon);
        dialogTitleLayout = (AutoLinearLayout) findViewById(R.id.dialog_title_layout);
    }

    public RecyclerView getMessageList() {
        return dialogListMessage;
    }

    public static class Builder {
        protected final Context context;
        protected boolean isBottomDisplay = false;//局中还是底部弹出
        //------
        protected List<RKDialogProfile> profiles = new ArrayList<>();//配置集合
        protected OnDismissListener onDismissListener;//监听对话框关闭
        //------
        protected CharSequence title;//标题
        protected int titleGravity = Gravity.CENTER;//标题栏的对齐方式
        protected int icon = -1;
        //------
        protected CharSequence message;//显示内容
        protected int messageGravity = Gravity.CENTER;//内容的对齐方式
        //------
        protected List<RKDialogButton> buttons = new ArrayList<>();//底部Button集合
        protected int buttonLayoutOrientation = LinearLayout.HORIZONTAL;//底部Button排列顺序
        //------
        protected boolean canceledOnTouchOutside = true;//false:dialog弹出后会点击屏幕不消失；点击物理返回键dialog消失
        protected boolean cancelable = true;//false:dialog弹出后会点击屏幕或物理返回键，dialog不消失
        protected int typeSystemAlert = -1;//支持自定义类别（一般在service里面会用到）
        //------
        protected List<RKDialogCheckBox> checkBoxes = new ArrayList<>();//CheckBox集合
        protected int checkBoxOrientation = LinearLayout.VERTICAL;//custom排列顺序
        protected RKDialogListener.OnCheckedChangeDismissListener onCheckedChangeDismissListener;
        //---
        protected RKDialogProgress dialogProgress;//添加进度条
        //---
        protected View customView;
        //---
        protected RKDialogChoiceList dialogChoiceList;
        protected RKDialogListener.OnMultiselectChoiceSelectListener onMultiselectChoiceSelectListener;

        public Builder(@NonNull Context context) {
            this.context = context;
        }

        public Builder setDialogChoiceList(RKDialogChoiceList dialogChoiceList) {
            this.dialogChoiceList = dialogChoiceList;
            return this;
        }

        public Builder setOnMultiselectChoiceSelectListener(RKDialogListener.OnMultiselectChoiceSelectListener onMultiselectChoiceSelectListener) {
            this.onMultiselectChoiceSelectListener = onMultiselectChoiceSelectListener;
            return this;
        }

        public Builder setOnDismissListener(OnDismissListener onDismissListener) {
            this.onDismissListener = onDismissListener;
            return this;
        }

        public Builder icon(int icon) {
            this.icon = icon;
            return this;
        }

        public Builder setCustomView(View customView) {
            this.customView = customView;
            return this;
        }

        public Builder setOnCheckedChangeDismissListener(RKDialogListener.OnCheckedChangeDismissListener onCheckedChangeDismissListener) {
            this.onCheckedChangeDismissListener = onCheckedChangeDismissListener;
            return this;
        }

        public Builder setCheckBoxOrientation(int checkBoxOrientation) {
            this.checkBoxOrientation = checkBoxOrientation;
            return this;
        }

        public Builder setDialogProgress(RKDialogProgress dialogProgress) {
            this.dialogProgress = dialogProgress;
            return this;
        }

        public Builder addCheckBox(RKDialogCheckBox checkBox) {
            checkBoxes.add(checkBox);
            return this;
        }

        public Builder setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
            this.canceledOnTouchOutside = canceledOnTouchOutside;
            return this;
        }

        public Builder setBottomDisplay(boolean bottomDisplay) {
            isBottomDisplay = bottomDisplay;
            return this;
        }

        public Builder setType(int typeSystemAlert) {
            this.typeSystemAlert = typeSystemAlert;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        public Builder setTitleGravity(int titleGravity) {
            this.titleGravity = titleGravity;
            return this;
        }

        public Builder setMessageGravity(int messageGravity) {
            this.messageGravity = messageGravity;
            return this;
        }


        public Builder setTitle(@StringRes int title) {
            if (title == 0) {
                return this;
            }
            setTitle(this.context.getText(title));
            return this;
        }

        public Builder setButtonLayoutOrientation(int buttonLayoutOrientation) {
            this.buttonLayoutOrientation = buttonLayoutOrientation;
            return this;
        }

        public Builder addButton(RKDialogButton button) {
            buttons.add(button);
            return this;
        }

        public Builder addProfile(RKDialogProfile profile) {
            profiles.add(profile);
            return this;
        }

        public Builder setTitle(@NonNull CharSequence title) {
            this.title = title;
            return this;
        }

        public Builder setMessage(@StringRes int message) {
            if (message == 0) {
                return this;
            }
            setMessage(this.context.getText(message));
            return this;
        }

        public Builder setMessage(@NonNull CharSequence message) {
            this.message = message;
            return this;
        }

        @UiThread
        public RKDialog build() {
            return new RKDialog(this);
        }

        @UiThread
        public RKDialog show() {
            RKDialog dialog = build();
            dialog.show();
            return dialog;
        }
    }
}
