package com.module.test.view;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.module.test.adapter.BaseAdapter;


/**
 * Created by liuqiwu on 2017/8/18.
 * 分页加载
 * 1、先new
 * 2、再设置监听
 * 3、需要重置的时候调用reset()
 * 4、获取数据成功或失败，调用onSuccess()或onFail()
 */

public class ModuleUIPagingLoad {
    private static final String TAG = "Main";
    /**
     * 列数
     */
    private int columnsNum = 4;
    /**
     * 每页行数
     */
    private int mOnePageLines = 3;
    /**
     * 每页个数  = gridNumColumns * mOnePageLines
     */
    private int mOnePageSize = columnsNum * mOnePageLines;
    /**
     * 总行数 = mTotalCount % gridNumColumns != 0 ? mTotalCount / gridNumColumns + 1: mTotalCount / gridNumColumns;
     */
    private int mTotalLines = 0;
    /**
     * 已有数据的行数 = posterList.size() % gridNumColumns != 0 ? posterList.size() / gridNumColumns + 1 : posterList.size() / gridNumColumns;
     */
    private int mCurrTotalLines;
    /**
     * 总个数
     */
    private int mTotalCount = 0;
    private ViewGroup parentView;
    private int currentCount = 0;
    private ModuleUIGrid moduleUIGrid;

    public int getOnePageSize() {
        return mOnePageSize;
    }

    public ModuleUIPagingLoad(ViewGroup parentView, ModuleUIGrid moduleUIGrid, int columnsNum) {
        this.parentView = parentView;
        this.moduleUIGrid = moduleUIGrid;
        this.columnsNum = columnsNum;
        mOnePageSize = columnsNum * mOnePageLines;
    }

    public void onSuccess(BaseAdapter baseAdapter, int totalCount) {
        if (baseAdapter == null || baseAdapter.getCount() == 0 || moduleUIGrid == null) {
            return;
        }
        mTotalCount = totalCount;
        // 计算总行数
        mTotalLines = mTotalCount % columnsNum != 0 ? mTotalCount / columnsNum + 1 : mTotalCount / columnsNum;

        moduleUIGrid.setTotalCount(totalCount);
        if (currentCount == 0) {
            // 第一页
            parentView.addView(moduleUIGrid.createUI(baseAdapter, columnsNum, 0));

            moduleUIGrid.setOnItemFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean isFocus) {
                    if (isFocus) {
                        if (mPagingLoadListener != null) {
                            mPagingLoadListener.onFocusChange(view, isFocus);
                        }
                        // 开始翻页逻辑
                        pagingSelectedItem(view.getId() - 1);
                    }
                }
            });
        } else {
            // 其他页
            moduleUIGrid.addModuleUI(currentCount, baseAdapter);
        }
        Log.d(TAG, "currentCount前:" + currentCount);
        // 添加数据
        currentCount += baseAdapter.getCount();
        Log.d(TAG, "currentCount后:" + currentCount);

        // 计算已有数据的行数
        mCurrTotalLines = currentCount % columnsNum != 0 ? currentCount / columnsNum + 1 : currentCount / columnsNum;
    }

    public void onFile() {
        if (currentCount == 0 && mPagingLoadListener != null) { // 当前没有数据，加载推荐数据
            mPagingLoadListener.onNoData();
        }
    }

    public void reset() {
        Log.d("Main", "reset()");

        mTotalLines = 0;
        mTotalCount = 0;
        currentCount = 0;

        // 刷UI
        if (parentView.getChildCount() > 0) {
            parentView.removeAllViews();
        }

        if (mPagingLoadListener != null) {
            mPagingLoadListener.setCurrentLine("", "");
        }
    }

    /**
     * 翻页逻辑 ———— page数据里selected Item时，所做操作
     *
     * @param position page数据里当前选中的position
     */
    private void pagingSelectedItem(int position) {
        /**
         * 设置当前行数
         */
        if (mPagingLoadListener != null) {
            mPagingLoadListener.setCurrentLine(Integer.toString((position / columnsNum) + 1), Integer.toString(mTotalLines));
        }

        /**
         * 分页加载(1、如果当前页是最后一页，则不加载；2、position位于当前页的倒数第二行时，加载下页数据)
         * 更改为：
         * 1、如果当前已经加载完数据，则不加载；2、position位于当前页的倒数第二行时，加载下页数据
         */
        if (currentCount == mTotalCount) return;
        if (position > (((mCurrTotalLines - 2) * columnsNum) - 1) && position < ((mCurrTotalLines - 1) * columnsNum) && mPagingLoadListener != null) {
            // 获取下页数据
            // startSeq是从0开始还是1
            Log.i("Main", "翻页逻辑    加载下一页:" + (currentCount + 1));
            mPagingLoadListener.requestData(currentCount + 1, mOnePageSize);
        }
    }

    public interface PagingLoadListener {
        void setCurrentLine(String currentLine, String lines);

        void requestData(int startSeq, int count);

        void onFocusChange(View view, boolean isFocus);

        void onNoData();
    }

    private PagingLoadListener mPagingLoadListener;

    public void setPagingLoadListener(PagingLoadListener pagingLoadListener) {
        mPagingLoadListener = pagingLoadListener;
    }
}
