package com.module.test.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.module.test.R;
import com.module.view.ModuleItem;

/**
 * Created by liuqiwu on 2017/7/28.
 * 视频样式的ModuleItem，用于网格布局
 */

public class ModuleItemVideo extends ModuleItem {
    private String itemName = "";

    protected ModuleItemVideo(Context context, int topMargin, int leftMargin, int rightMargin, int bottomMargin, int itemId, int leftId, int topId, int itemWidth, int itemHeight,
                              int nextFocusLeftId, int nextFocusRightId, String itemName) {
        super(context, topMargin, leftMargin, rightMargin, bottomMargin, itemId, leftId, topId, itemWidth, itemHeight, nextFocusLeftId, nextFocusRightId);
        this.itemName = itemName;
        createItemView(mContext);
    }


    public void createItemView(Context context) {
        LayoutInflater mInflater = LayoutInflater.from(context);
        itemView = mInflater.inflate(R.layout.item_module_video, null);
        itemView.setId(this.getItemId());
        TextView textView = itemView.findViewById(R.id.module_item_video_name);
        textView.setText(itemName);
    }

    public static final class Builder extends ModuleItem.Builder {
        private String builderName;

        public Builder(Context context) {
            super(context);
        }

        public Builder setName(String name) {
            this.builderName = name;
            return this;
        }

        public ModuleItemVideo build() {
            return new ModuleItemVideo(this.builderContext, this.builderTopMargin, this.builderLeftMargin, this.builderRightMargin, this.builderBottomMargin,
                    this.builderId, this.builderLeftId, this.builderTopId, this.builderWidth, this.builderHeight, this.builderNextFocusLeftId,
                    this.builderNextFocusRightId, this.builderName);
        }
    }
}
