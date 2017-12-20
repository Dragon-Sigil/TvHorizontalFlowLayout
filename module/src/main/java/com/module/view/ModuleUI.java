package com.module.view;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.RelativeLayout;

import com.module.utils.AnimationUtils;
import com.module.utils.ResourceUtil;

import java.util.List;

/**
 * Created by liuqiwu on 2017/7/28.
 * ModuleUI，模块UI的对象。根据itemList中每个item的位置信息实现代码构建UI。并设置相应监听。
 */

public abstract class ModuleUI implements View.OnFocusChangeListener, View.OnClickListener {
    private RelativeLayout moduleView = null;
    private Context mContext;

    protected ModuleUI(Context mContext) {
        this.mContext = mContext;
    }

    public RelativeLayout getModuleView() {
        return moduleView;
    }

    public void refreshViewFromList(List<ModuleItem> list) {
        creatViewFromList(list);
    }

    protected RelativeLayout creatViewFromList(List<ModuleItem> itemList) {
        if (moduleView == null) {
            moduleView = new RelativeLayout(mContext);
            moduleView.setClipChildren(false);
            moduleView.setClipToPadding(false);
            moduleView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        } else {
            if (moduleView.getChildCount() != 0) {
                moduleView.removeAllViews();
                moduleView.invalidate();
            }
        }

        ModuleItem moduleItem;
        for (int i = 0; i < itemList.size(); i++) {
            moduleItem = itemList.get(i);

            addViewToRelatielayout(moduleItem, moduleView);

            moduleItem.getItemView().setClickable(true);
            moduleItem.getItemView().setFocusable(true);
            moduleItem.getItemView().setFocusableInTouchMode(true);

            moduleItem.getItemView().setOnFocusChangeListener(this);
            moduleItem.getItemView().setOnClickListener(this);
            if (mOnKeyListener != null) {
                moduleItem.getItemView().setOnKeyListener(mOnKeyListener);
            }
        }
        moduleView.invalidate();
        return moduleView;
    }

    /**
     * 追加View
     *
     * @param itemList ModuleItem列表
     * @return 增添view后的RelativeLayout
     */
    protected RelativeLayout addViewsFromList(List<ModuleItem> itemList) {
        if (moduleView == null) {
            moduleView = new RelativeLayout(mContext);
            moduleView.setClipChildren(false);
            moduleView.setClipToPadding(false);
            moduleView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        }
        ModuleItem moduleItem;
        for (int i = 0; i < itemList.size(); i++) {
            moduleItem = itemList.get(i);

            addViewToRelatielayout(moduleItem, moduleView);

            moduleItem.getItemView().setClickable(true);
            moduleItem.getItemView().setFocusable(true);
            moduleItem.getItemView().setFocusableInTouchMode(true);

            moduleItem.getItemView().setOnFocusChangeListener(this);
            moduleItem.getItemView().setOnClickListener(this);
            if (mOnKeyListener != null) {
                moduleItem.getItemView().setOnKeyListener(mOnKeyListener);
            }
        }
        moduleView.invalidate();
        return moduleView;
    }

    protected RelativeLayout addBottomView(RelativeLayout moduleView, int count) {
        RelativeLayout.LayoutParams layoutparams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 30);
        layoutparams.addRule(RelativeLayout.BELOW, count);
        View view = new View(mContext);
        view.setFocusable(false);
        moduleView.addView(view, layoutparams);
        return moduleView;
    }

    private void addViewToRelatielayout(ModuleItem moduleItem, RelativeLayout relativeLayout) {
        RelativeLayout.LayoutParams layoutparams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        // 把ModuleItem放入relativeLayout
        relativeLayout.addView(moduleItem.getItemView(), setItemRule(moduleItem, layoutparams));
    }

    private RelativeLayout.LayoutParams setItemRule(ModuleItem moduleItem, RelativeLayout.LayoutParams layoutparams) {
        if (moduleItem.getLeftId() == ModuleItem.ITEMINEXISTENCE && moduleItem.getTopId() == ModuleItem.ITEMINEXISTENCE) { // 第一个item，设置在最左边
            layoutparams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        }

        // 上面有控件就放在此控件下面
        if (moduleItem.getTopId() != ModuleItem.ITEMINEXISTENCE) {
            layoutparams.addRule(RelativeLayout.BELOW, moduleItem.getTopId());
            moduleItem.getItemView().setNextFocusUpId(moduleItem.getTopId());
        } else {
            layoutparams.addRule(RelativeLayout.ALIGN_TOP, moduleItem.getLeftId());
        }

        // 左面有控件就放在此控件右面
        if (moduleItem.getLeftId() != ModuleItem.ITEMINEXISTENCE) {
            layoutparams.addRule(RelativeLayout.RIGHT_OF, moduleItem.getLeftId());
            moduleItem.getItemView().setNextFocusLeftId(moduleItem.getLeftId());
        } else if (moduleItem.getNextFocusLeftId() != ModuleItem.ITEMINEXISTENCE) {
            moduleItem.getItemView().setNextFocusLeftId(moduleItem.getNextFocusLeftId());
        } else {
            if (moduleItem.getTopId() != ModuleItem.ITEMINEXISTENCE) { // 左边没控件，上面有，设置focusLeft
                moduleItem.getItemView().setNextFocusLeftId(moduleItem.getItemId() - 1);
            }
        }

        if (moduleItem.getNextFocusRightId() != ModuleItem.ITEMINEXISTENCE) {
            moduleItem.getItemView().setNextFocusRightId(moduleItem.getNextFocusRightId());
        }

        layoutparams.leftMargin = (int) spToDip(moduleItem.getLeftMargin());
        layoutparams.rightMargin = (int) spToDip(moduleItem.getRightMargin());
        layoutparams.topMargin = (int) spToDip(moduleItem.getTopMargin());
        layoutparams.bottomMargin = (int) spToDip(moduleItem.getBottomMargin());
        return layoutparams;
    }

    private float spToDip(int sp) {
        float dip = 0;
        try {
            dip = mContext.getResources().getDimension(ResourceUtil.getInstance(mContext).getDimenId("dime_px2dip_" + sp));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
        return dip;
    }

    /**
     * item焦点的变换
     *
     * @param view    item
     * @param isFocus 是否获取到焦点
     */
    protected abstract void onFocused(View view, boolean isFocus);

    @Override
    public void onClick(View view) {
        if (mItemClickListener != null) {
            mItemClickListener.onClick(view);
        }
    }

    @Override
    public void onFocusChange(View view, boolean isFocus) {
        if (isFocus) {
            view.bringToFront();
            AnimationUtils.getInstance(mContext).zoomViewIn(view, 40f);
        } else {
            AnimationUtils.getInstance(mContext).zoomViewOn(view, 40f);
        }
        if (mItemFocusChangeListener != null) {
            mItemFocusChangeListener.onFocusChange(view, isFocus);
        }
        onFocused(view, isFocus);
    }

    private View.OnFocusChangeListener mItemFocusChangeListener;

    private View.OnClickListener mItemClickListener;

    private View.OnKeyListener mOnKeyListener;

    public void setOnItemFocusChangeListener(View.OnFocusChangeListener itemFocusChangeListener) {
        mItemFocusChangeListener = itemFocusChangeListener;
    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public void setOnKeyListener(View.OnKeyListener onKeyListener) {
        mOnKeyListener = onKeyListener;
    }
}
