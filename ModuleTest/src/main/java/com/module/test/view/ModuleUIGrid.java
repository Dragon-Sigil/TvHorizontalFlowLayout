package com.module.test.view;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.module.test.adapter.BaseAdapter;
import com.module.test.listener.RefreshListener;
import com.module.view.ModuleItem;
import com.module.view.ModuleUI;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuqiwu on 2017/7/31.
 * 网格布局的ModuleUI
 */

public class ModuleUIGrid extends ModuleUI {
    private BaseAdapter baseAdapter;
    private int columnNumber;
    private Context mContext;
    private int ensembleLeftMargin = 0;
    private int ensembleRightMargin = 0;
    private int ensembleTopMargin = 0;
    private int ensembleBottomMargin = 0;
    private int entityLeftMargin = 0;
    private int entityTopMargin = 0;
    // 如果是分页加载，请设置总数。这个数决定了谁是最后一行，以便设置底部边距。
    private int totalCount = 0;
    private boolean isHorizontal = false;

    public ModuleUIGrid(Context context) {
        super(context);
        this.mContext = context;
    }

    public void setEnsembleMargin(int leftMargin, int topMargin, int rightMargin, int bottomMargin) {
        this.ensembleLeftMargin = leftMargin;
        this.ensembleTopMargin = topMargin;
        this.ensembleBottomMargin = bottomMargin;
        this.ensembleRightMargin = rightMargin;
    }

    public void setEntityMargin(int leftMargin, int topMargin) {
        this.entityTopMargin = topMargin;
        this.entityLeftMargin = leftMargin;
    }

    public void setTotalCount(int counts) {
        this.totalCount = counts;
    }

    public void setHorizontal(boolean horizontal) {
        isHorizontal = horizontal;
    }

    private void setListener() {
        if (baseAdapter != null) {
            baseAdapter.setRefreshListener(new RefreshListener() {
                @Override
                public View refresh() {
                    return createUI(baseAdapter, columnNumber, fristItemLeftId);
                }
            });
        }
    }

    private int fristItemLeftId;

    public RelativeLayout createUI(BaseAdapter baseAdapter, int columnNumber, int leftId) {
        this.baseAdapter = baseAdapter;
        this.columnNumber = columnNumber;
        this.fristItemLeftId = leftId;

        if (baseAdapter == null || baseAdapter.getCount() == 0) {
            return null;
        }

        if (totalCount == 0) {
            totalCount = baseAdapter.getCount();
        }

        setListener();
        ModuleItemGrid.Builder itemBuilder;
        List<ModuleItem> itemList = new ArrayList<>();
        for (int i = 0; i < baseAdapter.getCount(); i++) {
            itemBuilder = new ModuleItemGrid.Builder(mContext);
            itemBuilder.itemId(i + 1);

            View itemView = baseAdapter.getView(i);
            itemView.setId(i + 1);
            itemBuilder.setBuilderView(itemView);
            itemList.add(setItemRule(i, itemBuilder).build());
        }
        RelativeLayout relativeLayout = addBottomView(creatViewFromList(itemList), baseAdapter.getCount());
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) relativeLayout.getLayoutParams();
        layoutParams.bottomMargin = ensembleBottomMargin;
        relativeLayout.setLayoutParams(layoutParams);
        return relativeLayout;
    }

    private ModuleItemGrid.Builder setItemRule(int i, ModuleItemGrid.Builder itemBuilder) {
        int remainderTotal = totalCount % columnNumber;
        int divisorTotal = totalCount / columnNumber;
        int divisorCurrent = i / columnNumber;
        int remainderCurrent = i % columnNumber;

        if (remainderCurrent == 0) { // 最左边
            if (i > 0 && !isHorizontal) {
                itemBuilder.nextFocusLeftId(i);
            } else if (i == 0) {
                itemBuilder.leftItemId(fristItemLeftId);
            }
            itemBuilder.leftMargin(ensembleLeftMargin);
        } else {
            itemBuilder.nextFocusLeftId(i);
            itemBuilder.leftItemId(i);
            itemBuilder.leftMargin(entityLeftMargin);
        }

        if (i < columnNumber) { // 最上边
            if (i == 0) { // 此处之所以这样判断，是因为，如果第一行每个都设置TopMargin就会成为阶梯状，右面的默认和左面的上对齐了。
                itemBuilder.topMargin(ensembleTopMargin);
            }
        } else {
            itemBuilder.topItemId((i + 1) - columnNumber);
            itemBuilder.topMargin(entityTopMargin);
        }

        if (remainderCurrent == (columnNumber - 1) && !isHorizontal) { // 最右边
            itemBuilder.rightMargin(ensembleRightMargin);
            itemBuilder.nextFocusRightId(i + 2);
        }

        if (isLastLine(remainderTotal, divisorTotal, divisorCurrent)) { // 是不是最后一行
            itemBuilder.bottomMargin(ensembleBottomMargin);
        }

        if (i == (totalCount - 1)) { // 最后一个，往右的id设为自身
            itemBuilder.nextFocusRightId(i + 1);
        }
        return itemBuilder;
    }

    /**
     * 判断当前item是不是位于最后一行，以便加距底部距离
     * id从零开始，所以列数要加一才能正好匹配实际的列数
     *
     * @param remainderTotal 总数%列数 = 总余数
     * @param divisorTotal   总数/列数 = 几列
     * @param divisorCurrent id/列数 = 当前位于第几列
     * @return 是否位于最后一行
     */
    private boolean isLastLine(int remainderTotal, int divisorTotal, int divisorCurrent) {
        return remainderTotal == 0 ? (divisorCurrent + 1 == divisorTotal) : (divisorCurrent == divisorTotal);
    }

    /**
     * 翻页加载更多
     *
     * @param addedCount  已加载的个数
     * @param baseAdapter 新加载的adapter
     * @return RelativeLayout
     */
    public RelativeLayout addModuleUI(int addedCount, BaseAdapter baseAdapter) {
        if (baseAdapter == null) {
            return null;
        }

        ModuleItemGrid.Builder itemBuilder;

        if (baseAdapter.getCount() == 0) {
            return null;
        }
        List<ModuleItem> itemList = new ArrayList<>();
        for (int i = addedCount; i < baseAdapter.getCount() + addedCount; i++) {
            itemBuilder = new ModuleItemGrid.Builder(mContext);
            itemBuilder.itemId(i + 1);

            View itemView = baseAdapter.getView(i - addedCount);
            itemView.setId(i + 1);
            itemBuilder.setBuilderView(itemView);
            itemList.add(setItemRule(i, itemBuilder).build());
        }

        if (addedCount + baseAdapter.getCount() == totalCount) {
            Log.d("ModuleUIGrid", "分页加载底部追加View");
            return addBottomView(addViewsFromList(itemList), totalCount);
        } else {
            return addViewsFromList(itemList);
        }
    }

    @Override
    protected void onFocused(View view, boolean isFocus) {
        baseAdapter.onItemFocusChange(view, isFocus);
    }
}
