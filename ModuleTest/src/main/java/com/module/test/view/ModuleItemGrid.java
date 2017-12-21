package com.module.test.view;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.module.view.ModuleItem;

/**
 * Created by liuqiwu on 2017/7/31.
 * 网格布局的item对象.
 */

public class ModuleItemGrid extends ModuleItem {
    public ModuleItemGrid(Context context, int topMargin, int leftMargin, int rightMargin, int bottomMargin, int itemId, int leftId, int topId, int itemWidth, int itemHeight, int nextFocusLeftId, int nextFocusRightId, View view) {
        super(context, topMargin, leftMargin, rightMargin, bottomMargin, itemId, leftId, topId, itemWidth, itemHeight, nextFocusLeftId, nextFocusRightId);
        itemView = view;
    }

    @Override
    public void createItemView(Context context) {
        Log.d("ModuleItemGrid", "");
    }

    public static final class Builder extends ModuleItem.Builder {
        private View builderView;

        public Builder(Context context) {
            super(context);
        }

        public Builder setBuilderView(View view) {
            this.builderView = view;
            return this;
        }

        @Override
        public ModuleItem build() {
            return new ModuleItemGrid(this.builderContext, this.builderTopMargin, this.builderLeftMargin, this.builderRightMargin, this.builderBottomMargin,
                    this.builderId, this.builderLeftId, this.builderTopId, this.builderWidth, this.builderHeight, this.builderNextFocusLeftId,
                    this.builderNextFocusRightId, this.builderView);
        }
    }
}
