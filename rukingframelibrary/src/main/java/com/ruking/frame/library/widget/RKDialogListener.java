package com.ruking.frame.library.widget;

import android.support.annotation.NonNull;

import com.ruking.frame.library.bean.Choice;

import java.util.List;

/**
 * @author Ruking.Cheng
 * @descrilbe 操作事件集
 * @email 495095492@qq.com
 * @tel 18075121944
 * @date on 12/11 13:42
 */
public interface RKDialogListener {
    /**
     * 点击按钮事件
     */
    interface OnClickListener {
        void onClick(@NonNull RKDialog dialog, @NonNull RKDialogButton button);
    }

    /**
     * 长按事件
     */
    interface OnLongClickListener {
        boolean onLongClick(@NonNull RKDialog dialog, @NonNull RKDialogButton button);
    }

    /**
     * 选择事件
     */
    interface OnCheckedChangeListener {
        void onCheckedChanged(@NonNull RKDialog dialog, @NonNull RKDialogCheckBox checkBox);
    }

    /**
     * 结束后选择项回调给调用方
     */
    interface OnCheckedChangeDismissListener {
        void onCheckedChangedDismiss(@NonNull List<RKDialogCheckBox> checkBoxes);
    }

    /**
     * 进度条回调
     */
    interface OnShowListener {
        void onShow(@NonNull RKDialog dialog, @NonNull RKDialogProgress progress);
    }

    /**
     * 列表单个选择的时候
     */
    interface OnSingleChoiceSelectListener {
        void onSelect(@NonNull RKDialog dialog, @NonNull Choice choice, int position);
    }

    /**
     * 列表多个选择的时候
     */
    interface OnMultiselectChoiceSelectListener {
        void onSelect(@NonNull List<Choice> choices);
    }
}
