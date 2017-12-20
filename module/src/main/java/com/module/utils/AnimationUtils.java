package com.module.utils;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import com.module.R;


/**
 * Created by liuqiwu on 2017/7/24.
 */

public class AnimationUtils {
    private Context mContext;
    private Animation animation;
    private static AnimationUtils instance;

    private AnimationUtils(Context mContext) {
        this.mContext = mContext;
    }

    public static AnimationUtils getInstance(Context context) {
        if (instance == null) {
            instance = new AnimationUtils(context);
        }
        return instance;
    }

    public void zoomIn(View view) {
        animation = android.view.animation.AnimationUtils.loadAnimation(mContext, R.anim.scale_in_10_13);
        view.startAnimation(animation);
    }

    public void zoomOn(View view) {
        animation = android.view.animation.AnimationUtils.loadAnimation(mContext, R.anim.scale_in_13_10);
        view.startAnimation(animation);
    }

    /**
     * 属性动画
     *
     * @param view 作用控件
     * @param a    初始值
     * @param b    过渡值
     * @param c    最终值
     */
    public void zoom(View view, float a, float b, float c) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "scaleY", a, b, c);
        animator.setDuration(400);
        animator.start();
        ObjectAnimator animators = ObjectAnimator.ofFloat(view, "scaleX", a, b, c);
        animators.setDuration(400);
        animators.start();
    }

    /**
     * 带弹性的放大效果
     *
     * @param i 在原有基础上放大i个单位
     */
    public void zoomIn(final View view, float i) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        int w = params.width;
        float j = (w + i) / w;
        float z = (w + i + i / 3) / w;
        zoom(view, 1.0f, z, j);
    }

    /**
     * 带弹性的缩小效果
     *
     * @param i 在原有基础上缩小i个单位
     */
    public void zoomOn(final View view, float i) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        int w = params.width;
        float j = (w + i) / w;
        float z = (w - i / 3) / w;
        zoom(view, j, z, 1.0f);
    }

    /**
     * 带弹性的放大效果
     *
     * @param i 在原有基础上放大i个单位
     */
    public void zoomViewIn(final View view, float i) {
        int w = view.getWidth();
        float j = (w + i) / w;
        float z = (w + i + i / 3) / w;
        zoom(view, 1.0f, z, j);
    }

    /**
     * 带弹性的缩小效果
     *
     * @param i 在原有基础上缩小i个单位
     */
    public void zoomViewOn(final View view, float i) {
        int w = view.getWidth();
        float j = (w + i) / w;
        float z = (w - i / 3) / w;
        zoom(view, j, z, 1.0f);
    }

}
