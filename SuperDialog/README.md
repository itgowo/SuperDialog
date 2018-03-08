# SuperDialog
一个类纯代码实现多功能Dialog，list、image和可变参数button数量

### 为什么写纯代码Dialog？

    我比较追求少而精，同时不想引入太多东西，添加容易，删除是很让人郁闷的，例如
    源码导入别人的库到自己项目，我们还要对应放入各种资源文件，久而久之
    是不是文件超多，甚至都不知道是干什么的，想清理都难，如果只有一个类就可以了，
    那管理起来是不是就方便了许多呢，最重要的是，不用XML定义是很考技术的😁。
    
  本人对大量引入第三方库比较反感，会造成APP体积增大，同时存在兼容性隐患，所以我开发的
项目原则是降低给别人造成麻烦，例如https://github.com/hnsugar/android-debugdata-webtool，
这个库是我为测试和开发写的，直接依赖就可使用，可以查看运行时APP内的数据库和共享参数及
data目录文件管理，就这样一个库同时具备web服务器和数据接口功能，其中数据使用Json格式。

*但是*，除了java和Android基本类是没有引用到第三方Json库的，不过确实用了第三方库，
那就不得不提我另一个库https://github.com/hnsugar/IntelligentTool，自动查找第三方库并使用。
这样我就解决了给使用者带来更多不同版本库的烦恼。


### 正题

            final SuperDialog superDialog = new SuperDialog(this);
            ArrayList<SuperDialog.DialogMenuItem> menuItems = new ArrayList<>();
            menuItems.add(new SuperDialog.DialogMenuItem("收藏", R.mipmap.ic_winstyle_favor));
            menuItems.add(new SuperDialog.DialogMenuItem("下载", R.mipmap.ic_winstyle_download));
            menuItems.add(new SuperDialog.DialogMenuItem("分享", R.mipmap.ic_winstyle_share));
            menuItems.add(new SuperDialog.DialogMenuItem("删除", R.mipmap.ic_winstyle_delete));
            menuItems.add(new SuperDialog.DialogMenuItem("歌手", R.mipmap.ic_winstyle_artist));
            menuItems.add(new SuperDialog.DialogMenuItem("专辑", R.mipmap.ic_winstyle_album));
            
            superDialog.setTitle(title).setContent(content).setButtonTexts(buttonStrs).setListener(listener).setShowImage().setDialogMenuItemList(menuItems).setButtonTexts(buttonStrs).show();
            Glide.with(superDialog.getImageView()).load("https://www.baidu.com/img/fnj_96d95207b4a706738f1b8be3b41ea9f3.gif").into(superDialog.getImageView()););
            
            
如上代码就构建了一个全功能的Dialog，

![](https://github.com/hnsugar/SuperDialog/blob/master/image/0.png)

 <img src="https://github.com/hnsugar/SuperDialog/blob/master/image/0.png" width = "40%" height = "40%" />