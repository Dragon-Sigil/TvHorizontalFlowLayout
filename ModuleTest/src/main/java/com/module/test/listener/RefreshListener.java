package com.module.test.listener;

import android.view.View;

/**
 * Created by liuqiwu on 2017/8/4.
 * BaseAdapter的刷新监听。由ModuleUIGrid设置给BaseAdapter，在ModuleUIGrid中做处理。
 */

public interface RefreshListener {
    View refresh();
}
