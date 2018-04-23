package com.ruking.frame.library.view.galleryWidget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.SoundEffectConstants;
import android.view.View;

/**
 * http://download.csdn.net/detail/heng615975867/6521189
 * 我们都知道，在iOS里面有一种控件------滚筒控件（Wheel View），这通常用于设置时间/日期，非常方便，但Android
 * SDK并没有提供类似的控件。这里介绍一下如何Android实现WheelView。
 * 
 * 
 */
public class WheelView extends TosGallery {
	/*
	 * The bound rectangle of selector.
	 */
	private Rect mSelectorBound = new Rect();
	/**
	 * The constructor method.
	 * 
	 * @param context
	 */
	public WheelView(Context context) {
		super(context);
		initialize();
	}

	/**
	 * The constructor method.
	 * 
	 * @param context
	 * @param attrs
	 */
	public WheelView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initialize();
	}

	/**
	 * The constructor method.
	 * 
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public WheelView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initialize();
	}

	/**
	 * Initialize.
	 *
	 */
	private void initialize() {
		this.setVerticalScrollBarEnabled(false);
		this.setSlotInCenter(true);
		this.setOrientation(TosGallery.VERTICAL);
		this.setGravity(Gravity.CENTER_HORIZONTAL);
		this.setUnselectedAlpha(1.0f);
		this.setWillNotDraw(false);
		this.setSoundEffectsEnabled(true);
	}

	/**
	 * Called by draw to draw the child views. This may be overridden by derived
	 * classes to gain control just before its children are drawn (but after its
	 * own view has been drawn).
	 */
	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
	}

	/**
	 * setOrientation
	 */
	@Override
	public void setOrientation(int orientation) {
		if (TosGallery.HORIZONTAL == orientation) {
			throw new IllegalArgumentException(
					"The orientation must be VERTICAL");
		}

		super.setOrientation(orientation);
	}

	/**
	 * Call when the ViewGroup is layout.
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		int galleryCenter = getCenterOfGallery();
		View v = this.getChildAt(0);
		int height = (null != v) ? v.getMeasuredHeight() : 50;
		int top = galleryCenter - height / 2;
		int bottom = top + height;
		mSelectorBound.set(getPaddingLeft(), top, getWidth()
				- getPaddingRight(), bottom);
	}

	@Override
	protected void selectionChanged() {
		super.selectionChanged();
		playSoundEffect(SoundEffectConstants.CLICK);
	}
}
