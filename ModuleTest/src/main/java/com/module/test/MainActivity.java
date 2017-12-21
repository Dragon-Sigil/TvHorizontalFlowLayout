package com.module.test;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.module.test.adapter.GridVideoModuleAdapter;
import com.module.test.bean.Video;
import com.module.test.view.ModuleItemVideo;
import com.module.test.view.ModuleUIGrid;
import com.module.view.ModuleItem;
import com.module.view.ModuleUICustom;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Context mContext;
    private ModuleUICustom moduleUI = null;
    private ModuleUIGrid gridModuleUI = null;
    private boolean isAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        RelativeLayout theLatest = findViewById(R.id.relative_layout);
        if (theLatest != null) {
            isAdapter = true;
        } else {
            return;
        }


        List<Video> moduleItemInfos = new ArrayList<>();
        List<ModuleItem> itemList = new ArrayList<>();
        if (!isAdapter) {
            moduleUI = new ModuleUICustom(mContext);
            itemList.add(new ModuleItemVideo.Builder(mContext).setName("第一个").itemId(1).leftItemId(0).topItemId(0).leftMargin(30).build());
            itemList.add(new ModuleItemVideo.Builder(mContext).setName("第二个").itemId(2).leftItemId(1).topItemId(0).leftMargin(30).build());
            itemList.add(new ModuleItemVideo.Builder(mContext).setName("第三个").itemId(3).leftItemId(2).topItemId(0).leftMargin(30).build());
            itemList.add(new ModuleItemVideo.Builder(mContext).setName("第一个").itemId(4).leftItemId(0).topItemId(1).leftMargin(30).build());
            itemList.add(new ModuleItemVideo.Builder(mContext).setName("第二个").itemId(5).leftItemId(4).topItemId(2).leftMargin(30).build());
            itemList.add(new ModuleItemVideo.Builder(mContext).setName("第三个").itemId(6).leftItemId(5).topItemId(3).leftMargin(30).build());

            moduleUI.setOnItemClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, "点击了第" + view.getId() + "个item", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            Video video;
            for (int i = 0; i < 15; i++) {
                video = new Video();
                video.setName("第" + i + "个");
            }

            gridModuleUI = new ModuleUIGrid(mContext);
            gridModuleUI.setOnItemClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, "点击了第" + view.getId() + "个item", Toast.LENGTH_LONG).show();
                }
            });
        }

        if (!isAdapter) {
            theLatest.addView(
                    moduleUI.
                            createUI(itemList));
        } else {
            theLatest.addView(gridModuleUI.createUI(new GridVideoModuleAdapter(mContext, moduleItemInfos), 4, 0));
        }
    }
}
