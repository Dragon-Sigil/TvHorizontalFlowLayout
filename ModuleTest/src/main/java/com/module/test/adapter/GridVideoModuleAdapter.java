package com.module.test.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.module.test.R;
import com.module.test.bean.Video;
import com.module.test.view.MarqueeText;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuqiwu on 2017/7/31.
 * 视频网格布局的适配器
 */

public class GridVideoModuleAdapter extends BaseAdapter {
    private List<Video> itemInfoList = new ArrayList<>();
    private LayoutInflater mInflater;
    private DisplayImageOptions options;

    public GridVideoModuleAdapter(Context context, List<Video> itemInfoList) {
        this.itemInfoList = itemInfoList;
        this.mInflater = LayoutInflater.from(context);
        if (options == null) {
            options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.mipmap.home_videoc_loading)
                    .showImageOnFail(R.mipmap.home_videoc_loaded_error)
                    .showImageForEmptyUri(R.mipmap.home_videoc_loaded_error).cacheInMemory(true)
                    .cacheOnDisk(true).bitmapConfig(Bitmap.Config.RGB_565).build();
        }
    }

    @Override
    public int getCount() {
        return itemInfoList == null ? 0 : itemInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemInfoList.get(position);
    }

    @Override
    public View getView(int position) {
        View view = mInflater.inflate(R.layout.item_grid_video, null);
        ImageView imageView = view.findViewById(R.id.item_grid_video_img);
        MarqueeText textView = view.findViewById(R.id.item_grid_video_name);

        Video video = itemInfoList.get(position);
        textView.setText(video.getName());
        // TODO 加载图片
//        LoadImageManager.getInstance().displayImage(video.getImageUrl(), imageView, LoadImageManager.POLY_IMG, options);
        view.setTag(video);
        return view;
    }

    @Override
    public void onItemFocusChange(View view, boolean isFocus) {
        if (isFocus) {
            view.findViewById(R.id.item_grid_video_img).setBackgroundResource(R.mipmap.home_select_c);
            ImageView imageView = view.findViewById(R.id.item_grid_video_type);
            imageView.setPadding(0, 0, 0, 0);

            MarqueeText textView = view.findViewById(R.id.item_grid_video_name);
            if (textView != null) {
                textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            }
        } else {
            view.findViewById(R.id.item_grid_video_img).setBackgroundColor(Color.TRANSPARENT);
            ImageView imageView = view.findViewById(R.id.item_grid_video_type);
            imageView.setPadding(3, 3, 3, 3);

            MarqueeText textView = view.findViewById(R.id.item_grid_video_name);
            if (textView != null) {
                textView.setEllipsize(TextUtils.TruncateAt.END);
            }
        }
    }
}
