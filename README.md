# SuperDialog
一个类文件不到600行纯代码实现多功能Dialog，list、image、input输入框、Progress自定义View和可变参数button数量

Github:https://github.com/hnsugar/

http://itgowo.com

QQ:1264957104

Email:lujianchao@itgowo.com

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


如下全功能展示例子代码就构建了一个全功能的Dialog，如下图，需要注意的是，默认设置列表后就不会显示底部按钮，如果有特殊需要，只需要调整链式调用顺序即可。
还有需要注意的是获取ImageView前必须先show，show操作中增加了初始化操作，不然直接get是null。buttonText设置了有奇效，很多地方都可以混用，但是我希望
开发者按套路出牌，设置成正常的Dialog:-D。

1. 下方按钮是代码设置drawable，根据位置设置圆角位置
2. 相应类型方法不调用默认规则是不显示对应类型组件。
3. 部分方法会强制相应方法设置值，但是调整调用顺序可以重新覆盖为最新设置。
(栗子：显示列表时屏蔽下方按钮显示，但是先调用setDialogMenuItemList()后设置不显示下方按钮，如果后面调用setButtonTexts()则会把下方按钮显示出来，出现参数重置)
4. 自定义View可以实现更多功能。
5. 单个文件移植简单，需要定义什么字体颜色什么的自行更改，没必要提供过多的方法。


### 举个栗子：
#### 全功能展示（链式调用顺序影响具体显示和功能效果）

    public void test1(View view) {
        final SuperDialog superDialog = new SuperDialog(this);

        ArrayList<SuperDialog.DialogMenuItem> menuItems = new ArrayList<>();
        menuItems.add(new SuperDialog.DialogMenuItem("收藏", R.mipmap.ic_winstyle_favor));
        menuItems.add(new SuperDialog.DialogMenuItem("下载", R.mipmap.ic_winstyle_download));
        menuItems.add(new SuperDialog.DialogMenuItem("分享", R.mipmap.ic_winstyle_share));
        menuItems.add(new SuperDialog.DialogMenuItem("删除", R.mipmap.ic_winstyle_delete));
        menuItems.add(new SuperDialog.DialogMenuItem("歌手", R.mipmap.ic_winstyle_artist));
        menuItems.add(new SuperDialog.DialogMenuItem("专辑", R.mipmap.ic_winstyle_album));

        superDialog.setTitle("全功能展示Dialog").setContent("纯代码编写，没有使用XML.")
                .setListener(listener).setShowImage().setDialogMenuItemList(menuItems).setButtonTexts(new String[]{"按钮1", "按钮2", "按钮3", "按钮4"}).setImageListener(new SuperDialog.onDialogImageListener() {
            @Override
            public void onInitImageView(ImageView imageView) {
                Glide.with(imageView).load("https://www.baidu.com/img/fnj_96d95207b4a706738f1b8be3b41ea9f3.gif").into(imageView);
            }
        }).setCustomViewListener(new SuperDialog.onDialogCustomViewListener() {
            @Override
            public View onInitCustomView(LinearLayout viewGroup) {
                return new ProgressBar(viewGroup.getContext());
            }
        }).setInputListener(new SuperDialog.onDialogInputListener() {
            @Override
            public void onInitEditText(EditText inputView) {
                inputView.setHint("自定义设置");
            }

            @Override
            public void onComplete(int buttonIndex, String text) {
                Toast.makeText(getApplicationContext(), "输入框：" + text, Toast.LENGTH_SHORT).show();
            }
        }).show();

        superDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Toast.makeText(getApplicationContext(), "cancel", Toast.LENGTH_SHORT).show();
            }
        });
    }

![0.png](https://github.com/hnsugar/SuperDialog/blob/master/image/0.png)

#### 普通Dialog

    public void test2(View view) {
        SuperDialog superDialog = new SuperDialog(this);
        superDialog.setTitle("提示框类型Dialog").setContent("纯代码编写，没有使用XML").setListener(listener).show();
        superDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Toast.makeText(getApplicationContext(), "cancel", Toast.LENGTH_SHORT).show();
            }
        });
    }

!![1.png](https://github.com/hnsugar/SuperDialog/blob/master/image/1.png)

#### 无标题Dialog

    public void test3(View view) {
        final SuperDialog superDialog = new SuperDialog(this);
        superDialog.setContent("纯代码编写，没有使用XML\r\n没有标题").setListener(listener).show();
        superDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Toast.makeText(getApplicationContext(), "cancel", Toast.LENGTH_SHORT).show();
            }
        });
    }

![2.png](https://github.com/hnsugar/SuperDialog/blob/master/image/2.png)

#### 两个按钮

    public void test4(View view) {
        final SuperDialog superDialog = new SuperDialog(this);
        superDialog.setTitle("2个button").setContent("纯代码编写，没有使用XML").setListener(listener).setButtonTexts(new String[]{"按钮1", "按钮2"}).show();
        superDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Toast.makeText(getApplicationContext(), "cancel", Toast.LENGTH_SHORT).show();
            }
        });
    }

![3.png](https://github.com/hnsugar/SuperDialog/blob/master/image/3.png)

#### 三个按钮

    public void test5(View view) {
        final SuperDialog superDialog = new SuperDialog(this);
        superDialog.setTitle("3个button").setContent("纯代码编写，没有使用XML").setListener(listener).setButtonTexts(new String[]{"按钮1", "按钮2", "按钮3"}).show();
        superDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Toast.makeText(getApplicationContext(), "cancel", Toast.LENGTH_SHORT).show();
            }
        });
    }

![4.png](https://github.com/hnsugar/SuperDialog/blob/master/image/4.png)

#### 可变参数设置多个按钮

    public void test6(View view) {
        final SuperDialog superDialog = new SuperDialog(this);
        superDialog.setTitle("可变参数N个button").setContent("纯代码编写，没有使用XML\r\n.setButtonTexts(\"按钮1\", \"按钮2\", \"按钮3\", \"按钮4\", \"按钮5\", \"按钮6\")").setListener(listener).setButtonTexts("按钮1", "按钮2", "按钮3", "按钮4", "按钮5", "按钮6").show();
        superDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Toast.makeText(getApplicationContext(), "cancel", Toast.LENGTH_SHORT).show();
            }
        });
    }


![5.png](https://github.com/hnsugar/SuperDialog/blob/master/image/5.png)


#### 带图片文字
###### 布局addView后回调onInitImageView(),返回ImageView可以自己处理事件和效果

    public void test7(View view) {
        final SuperDialog superDialog = new SuperDialog(this);
        superDialog.setTitle("带图片Dialog").setContent("纯代码编写，没有使用XML").setListener(listener).setShowImage().setImageListener(new SuperDialog.onDialogImageListener() {
            @Override
            public void onInitImageView(ImageView imageView) {
                Glide.with(imageView).load("https://www.baidu.com/img/fnj_96d95207b4a706738f1b8be3b41ea9f3.gif").into(imageView);
            }
        }).setButtonTexts(new String[]{"按钮1", "按钮2", "按钮3", "按钮4"}).show();

        superDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Toast.makeText(getApplicationContext(), "cancel", Toast.LENGTH_SHORT).show();
            }
        });
    }

![6.png](https://github.com/hnsugar/SuperDialog/blob/master/image/6.png)


#### 列表

    public void test8(View view) {
        final SuperDialog superDialog = new SuperDialog(this);

        ArrayList<SuperDialog.DialogMenuItem> menuItems = new ArrayList<>();
        menuItems.add(new SuperDialog.DialogMenuItem("收藏", R.mipmap.ic_winstyle_favor));
        menuItems.add(new SuperDialog.DialogMenuItem("下载", R.mipmap.ic_winstyle_download));
        menuItems.add(new SuperDialog.DialogMenuItem("分享", R.mipmap.ic_winstyle_share));
        menuItems.add(new SuperDialog.DialogMenuItem("删除", R.mipmap.ic_winstyle_delete));
        menuItems.add(new SuperDialog.DialogMenuItem("歌手", R.mipmap.ic_winstyle_artist));
        menuItems.add(new SuperDialog.DialogMenuItem("专辑", R.mipmap.ic_winstyle_album));


        superDialog.setTitle("列表的Dialog").setContent("纯代码编写，没有使用XML").setListener(listener).setDialogMenuItemList(menuItems).show();
        superDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Toast.makeText(getApplicationContext(), "cancel", Toast.LENGTH_SHORT).show();
            }
        });
    }

![7.png](https://github.com/hnsugar/SuperDialog/blob/master/image/7.png)

#### 单按钮默认样式输入Dialog
###### 布局addView前回调onInitEditText(),返回edittext可以设置事件及效果。
###### 未设置setButtonTexts()则进入判断，如果是输入模式且文本数组为空，则默认文本数组为{"确定"}

    public void test9(View view) {
        final SuperDialog superDialog = new SuperDialog(this);


        superDialog.setTitle("输入框Dialog").setContent("纯代码编写，没有使用XML").setInputListener(new SuperDialog.onDialogInputListener() {
            @Override
            public void onInitEditText(EditText inputView) {
                inputView.setHint("请输入文字");
            }

            @Override
            public void onComplete(int buttonIndex, String text) {
                Toast.makeText(getApplicationContext(), "输入框：" + text, Toast.LENGTH_SHORT).show();
            }
        }).show();
        superDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Toast.makeText(getApplicationContext(), "cancel", Toast.LENGTH_SHORT).show();
            }
        });
    }

![8.png](https://github.com/hnsugar/SuperDialog/blob/master/image/8.png)


#### 自定义多按钮输入Dialog
###### 按钮受setButtonTexts()影响，设置数据会按顺序显示，onComplete()回调会返回文本和点击按钮位置，顺序与设置一致。

    public void test10(View view) {
        final SuperDialog superDialog = new SuperDialog(this);


        superDialog.setTitle("输入框Dialog").setContent("纯代码编写，没有使用XML").setButtonTexts("取消", "更改").setInputListener(new SuperDialog.onDialogInputListener() {
            @Override
            public void onInitEditText(EditText inputView) {
                inputView.setHint("请输入文字");
            }

            @Override
            public void onComplete(int buttonIndex, String text) {
                if (buttonIndex == 0) {
                    Toast.makeText(getApplicationContext(), "取消输入框：" + text, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "输入框：" + text, Toast.LENGTH_SHORT).show();
                }
            }
        }).show();
        superDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Toast.makeText(getApplicationContext(), "cancel", Toast.LENGTH_SHORT).show();
            }
        });
    }

![9.png](https://github.com/hnsugar/SuperDialog/blob/master/image/9.png)


#### 等待/进度自定义ViewDialog

    public void test11(View view) {
        final SuperDialog superDialog = new SuperDialog(this);

        superDialog.setTitle("进度/等待，自定义View Dialog").setContent("处理进度55%").setShowButtonLayout(false).setCustomViewListener(new SuperDialog.onDialogCustomViewListener() {
            @Override
            public View onInitCustomView(LinearLayout viewGroup) {
                return new ProgressBar(viewGroup.getContext());
            }
        }).show();
        superDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Toast.makeText(getApplicationContext(), "cancel", Toast.LENGTH_SHORT).show();
            }
        });
    }

![10.png](https://github.com/hnsugar/SuperDialog/blob/master/image/10.png)
