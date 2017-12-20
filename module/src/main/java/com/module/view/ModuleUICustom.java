package com.module.view;

import android.content.Context;
import android.util.Log;
import android.view.View;

import java.util.List;

/**
 * Created by liuqiwu on 2017/8/1.
 * 自定义布局的ModuleUI
 */

public class ModuleUICustom extends ModuleUI {
    public ModuleUICustom(Context mContext) {
        super(mContext);
    }

    @Override
    protected void onFocused(View view, boolean isFocus) {
        Log.d("ModuleUICustom", "");
    }

    public View createUI(List<ModuleItem> itemList) {
        return super.creatViewFromList(itemList);
    }
}
