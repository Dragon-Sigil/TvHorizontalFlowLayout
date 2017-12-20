package com.module.view;

import android.content.Context;
import android.view.View;

/**
 * Created by liuqiwu on 2017/7/27.
 * ModuleItem的基类，负责存储item的UI和位置信息
 */

public abstract class ModuleItem {
    public static final int ITEMINEXISTENCE = 0; // 表示某个方向不存在组件
    private int topMargin = 0;
    private int leftMargin = 0;
    private int rightMargin = 0;
    private int bottomMargin = 0;
    // 只有最左边的item设置NextFocusLeftId
    private int nextFocusLeftId = 0;
    // 只有最右边的item设置NextFocusRightId
    private int nextFocusRightId = 0;

    private int itemId;
    private int leftId;
    private int topId;

    private int itemWidth;
    private int itemHeight;

    protected View itemView;
    protected Context mContext;

    public ModuleItem(Context context, int topMargin, int leftMargin, int rightMargin, int bottomMargin, int itemId, int leftId, int topId,
                      int itemWidth, int itemHeight, int nextFocusLeftId, int nextFocusRightId) {
        this.topMargin = topMargin;
        this.leftMargin = leftMargin;
        this.rightMargin = rightMargin;
        this.bottomMargin = bottomMargin;
        this.itemId = itemId;
        this.leftId = leftId;
        this.topId = topId;
        this.itemWidth = itemWidth;
        this.itemHeight = itemHeight;
        this.nextFocusLeftId = nextFocusLeftId;
        this.nextFocusRightId = nextFocusRightId;
        this.mContext = context;
    }

    abstract void createItemView(Context context);

    protected int getTopMargin() {
        return topMargin;
    }

    protected int getLeftMargin() {
        return leftMargin;
    }

    protected int getRightMargin() {
        return rightMargin;
    }

    protected int getBottomMargin() {
        return bottomMargin;
    }

    protected int getItemId() {
        return itemId;
    }

    protected int getLeftId() {
        return leftId;
    }

    protected int getTopId() {
        return topId;
    }

    protected int getItemWidth() {
        return itemWidth;
    }

    protected int getItemHeight() {
        return itemHeight;
    }

    protected int getNextFocusLeftId() {
        return nextFocusLeftId;
    }

    protected int getNextFocusRightId() {
        return nextFocusRightId;
    }

    protected View getItemView() {
        return itemView;
    }

    public abstract static class Builder {
        protected Context builderContext;
        protected int builderTopMargin;
        protected int builderLeftMargin;
        protected int builderRightMargin;
        protected int builderBottomMargin;
        protected int builderId;
        protected int builderLeftId;
        protected int builderTopId;
        protected int builderWidth;
        protected int builderHeight;
        protected int builderNextFocusLeftId;
        protected int builderNextFocusRightId;

        protected Builder(Context context) {
            this.builderContext = context.getApplicationContext();
        }

        public Builder topMargin(int topMargin) {
            this.builderTopMargin = topMargin;
            return this;
        }

        public Builder leftMargin(int leftMargin) {
            this.builderLeftMargin = leftMargin;
            return this;
        }

        public Builder rightMargin(int rightMargin) {
            this.builderRightMargin = rightMargin;
            return this;
        }

        public Builder bottomMargin(int bottomMargin) {
            this.builderBottomMargin = bottomMargin;
            return this;
        }

        public Builder itemId(int id) {
            this.builderId = id;
            return this;
        }

        public Builder leftItemId(int leftItemId) {
            this.builderLeftId = leftItemId;
            return this;
        }

        public Builder topItemId(int topItemId) {
            this.builderTopId = topItemId;
            return this;
        }

        public Builder setWidth(int width) {
            this.builderWidth = width;
            return this;
        }

        public Builder setHeight(int height) {
            this.builderHeight = height;
            return this;
        }

        public Builder nextFocusLeftId(int nextFocusLeftId) {
            this.builderNextFocusLeftId = nextFocusLeftId;
            return this;
        }

        public Builder nextFocusRightId(int nextFocusRightId) {
            this.builderNextFocusRightId = nextFocusRightId;
            return this;
        }

        public abstract ModuleItem build();
    }
}
