package com.itgowo.superdialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private SuperDialog.onDialogClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listener = new SuperDialog.onDialogClickListener() {
            @Override
            public void click(boolean isButtonClick, int position) {
                Toast.makeText(getApplicationContext(), (isButtonClick ? "button:" : "list:") + "点击了第" + position + "个", Toast.LENGTH_SHORT).show();
            }
        };
    }

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

    /**
     * 用可变参数写多个按钮
     *
     * @param view
     */
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

    /**
     * 带图片的Dialog
     *
     * @param view
     */
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

    /**
     * 有列表的Dialog
     *
     * @param view
     */
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

    /**
     * 输入框
     *
     * @param view
     */
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

    /**
     * 带取消按钮的输入框
     *
     * @param view
     */
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

    /**
     * 进度/等待，自定义View
     *
     * @param view
     */
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
}
