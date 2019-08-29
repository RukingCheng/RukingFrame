package com.zhy.autolayout;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;

import com.zhy.autolayout.config.AutoLayoutConifg;

public class AutoLayoutActivity extends AppCompatActivity {
    private static final String LAYOUT_LINEARLAYOUT = "LinearLayout";
    private static final String LAYOUT_FRAMELAYOUT = "FrameLayout";
    private static final String LAYOUT_RELATIVELAYOUT = "RelativeLayout";
    private static final String LAYOUT_RECYCLERVIEW = "android.support.v7.widget.RecyclerView";
    private static final String LAYOUT_CONSTRAINTLAYOUT = "android.support.constraint.ConstraintLayout";
    private static final String LAYOUT_TOOLBAR = "android.support.v7.widget.Toolbar";
    private static final String LAYOUT_RADIOGROUP = "RadioGroup";
    private static final String LAYOUT_VIEWGROUP = "ViewGroup";
    private static final String ACTION_MENU_ITEM_VIEW = "android.support.v7.view.menu.ActionMenuItemView";
    private static final String TAB_LAYOUT = "android.support.design.widget.TabLayout";


    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        View view = null;
        if (name.equals(LAYOUT_FRAMELAYOUT)) {
            view = new AutoFrameLayout(context, attrs);
        }
        if (name.equals(LAYOUT_LINEARLAYOUT)) {
            view = new AutoLinearLayout(context, attrs);
        }
        if (name.equals(LAYOUT_RELATIVELAYOUT)) {
            view = new AutoRelativeLayout(context, attrs);
        }
        if (name.equals(LAYOUT_RECYCLERVIEW)) {
            view = new AutoRecyclerView(context, attrs);
        }
        if (name.equals(LAYOUT_CONSTRAINTLAYOUT)) {
            view = new AutoConstraintLayout(context, attrs);
        }
        if (name.equals(LAYOUT_TOOLBAR)) {
            view = new AutoToolbar(context, attrs);
        }
        if (name.equals(LAYOUT_RADIOGROUP)) {
            view = new AutoRadioGroup(context, attrs);
        }
        if (name.equals(LAYOUT_VIEWGROUP)) {
            view = new AutoViewGroup(context, attrs);
        }
        if (name.equals(ACTION_MENU_ITEM_VIEW)) {
            view = new AutoActionMenuItemView(context, attrs);
        }
        if (name.equals(TAB_LAYOUT)) {
            view = new AutoTabLayout(context, attrs);
        }

        if (view != null) return view;
        return super.onCreateView(name, context, attrs);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        AutoLayoutConifg.newInstance(this);
    }
}
