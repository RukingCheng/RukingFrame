package com.ruking.frame.library.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ruking.frame.library.R;


public class ToastUtil {
    private static Toast toast;

    public static void setGravity(int gravity) {
        ToastUtil.gravity = gravity;
    }

    private static int gravity = Gravity.CENTER;

    public static void show(Context context, String info) {
        LayoutInflater inflater = LayoutInflater.from(context);
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.toast_view, null);
        TextView toast_tv = view.findViewById(R.id.toast_tv);
        toast_tv.setText(info);
        toast_tv.getBackground().setAlpha(166);
        if (toast != null) {
            toast.cancel();
        }
        toast = new Toast(context);
        toast.setGravity(gravity, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }

    public static void show(Context context, int info) {
        show(context, context.getResources().getString(info));
    }


}
