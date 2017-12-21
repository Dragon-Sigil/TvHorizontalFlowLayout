package com.module.test.adapter;

import android.view.View;

import com.module.test.listener.RefreshListener;

/**
 * Created by liuqiwu on 2017/7/31.
 * 给ModuleUIGrid做适配的Adapter
 */

public abstract class BaseAdapter {
    private RefreshListener mRefreshListener;

    public abstract int getCount();

    public abstract Object getItem(int position);

    public abstract View getView(int position);

    public abstract void onItemFocusChange(View view, boolean isFocus);

    public void setRefreshListener(RefreshListener refreshListener) {
        mRefreshListener = refreshListener;
    }

    public View refresh() {
        if (mRefreshListener != null) {
            return mRefreshListener.refresh();
        } else {
            return null;
        }
    }
}
