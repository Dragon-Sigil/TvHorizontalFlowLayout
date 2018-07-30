# TvHorizontalFlowLayout
在Tv中，launcher基本是一页页的pageUI组成的(有横向和竖向两种滚动方式)。
1、每个page页的布局是不规则的且都不一样，导致无法复用。
2、有线上更新布局的需求或下拉加载(通过接口拿到图片，并且图片的大小可能会变动)
导致page的布局不能在xml中写死，但是通过代码一个个addView又很麻烦。
为此，我着手做了这个东东。

![首页排版并不固定，尤其后期更新，所以不能写死](https://img-blog.csdn.net/20180730102342279?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1ZhbHVlX0xpdQ==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

首先，确定你是什么Item，不同的item除了公共的参数(如：长宽，位置信息)可能有各自的特性，比如：Image需要加载url，video需要播放url，这个里面分别自定义了Video的Item和图片的Item

```
通过builder模式构建。参数分别是名称、viewId、左边item的Id、上面item的Id、边距。(边距上下左右都有，看需求往里设置，这也是为什么用builder模式的原因)

new ModuleItemVideo.Builder(mContext).setName("第一个").itemId(1).leftItemId(0).topItemId(0).leftMargin(30).build()
```

然后，创建ModuleUI，设置item点击事件。

```
List<ModuleItem> itemList = new ArrayList<>();
ModuleUICustom moduleUI = new ModuleUICustom(mContext);

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
```

最后，把ModuleView添加在ViewPager或需要的父控件即可

```
RelativeLayout theLatest = findViewById(R.id.relative_layout);
theLatest.addView(moduleUI.createUI(itemList));
```
