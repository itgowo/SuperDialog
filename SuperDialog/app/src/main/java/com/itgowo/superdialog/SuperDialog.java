package com.itgowo.superdialog;

import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by lujianchao on 2018/3/6.
 * Github：https://github.com/hnsugar/
 * WebSite：http://itgowo.com
 * <p>
 * 链式调用顺序决定显示样式，例如显示List形式，默认会禁止下面button显示，如果需要全部显示，则需要先设置list后设置button，这样参数会覆盖。
 * 这只是一个Demo，大家可以自行更改样式，因为未使用XML定义，所以只适合Android中高级开发人员练手，帮助大家学习代码控制生成drawable、selector等。
 */

public class SuperDialog extends Dialog {
    protected Context context;
    protected DisplayMetrics displayMetrics;


    private String title;
    private String content;
    private String[] buttonTexts;
    private List<DialogMenuItem> dialogMenuItemList;
    private onDialogClickListener listener;
    private onDialogInputListener inputListener;
    private onDialogImageListener imageListener;
    private onDialogCustomViewListener customViewListener;


    private boolean isShowTitle;
    private boolean isShowProgress;
    private boolean isShowContent;
    private boolean isShowImage;
    private boolean isShowInput;
    private boolean isShowList;
    private boolean isShowButtonLayout = true;


    private TextView titleView;
    private EditText inputView;
    private View titleLineView;
    private ImageView imageView;
    private TextView contentView;
    private ListView listView;
    private ListDialogAdapter dialogAdapter;


    private int dialogBackground = Color.WHITE;
    private int buttonBackgroundNormalColor = Color.WHITE;
    private int buttonBackgroundPressedColor = Color.parseColor("#110000FF");
    private int titleTextSize = 25;
    private int titleTextColor = Color.parseColor("#56A2E7");
    private int contentTextSize = 15;
    private int contentTextColor = Color.DKGRAY;
    private int buttonTextSize = 14;
    private int buttonTextColor = Color.DKGRAY;
    private ColorStateList colorStateList;

    public SuperDialog(@NonNull Context context) {
        super(context);
        this.context = context;
        init();
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.width = displayMetrics.widthPixels / 3 * 2;
        getWindow().setAttributes(layoutParams);
        getWindow().setBackgroundDrawable(null);
    }

    @Override
    public void show() {
        initView();
        super.show();
    }

    private void initView() {
        if (title == null) {
            title = "";
        }
        if (isShowButtonLayout && (buttonTexts == null || buttonTexts.length == 0)) {
            buttonTexts = new String[]{"确定"};
        }

        /**
         * 根部局
         */
        LinearLayout viewRoot = new LinearLayout(context);
        viewRoot.setOrientation(LinearLayout.VERTICAL);

        if (isShowTitle) {
            initTitleView(viewRoot);
        }


        if (isShowTitle) {
            initTitleLineView(viewRoot);
        }


        if (isShowImage) {
            initImageView(viewRoot);
        }

        if (isShowProgress) {
            initProgressView(viewRoot);
        }
        if (isShowContent) {
            initContentView(viewRoot);
        }

        if (isShowInput) {
            initInputView(viewRoot);
        }

        if (isShowList && dialogMenuItemList != null) {
            initListView(viewRoot);
        }


        if (isShowButtonLayout) {
            initButtonLayout(viewRoot);
        }


        viewRoot.setBackgroundDrawable(getDialogBackground(new float[]{dp2px(6), dp2px(6), dp2px(6), dp2px(6), dp2px(6), dp2px(6), dp2px(6), dp2px(6)}));
        setContentView(viewRoot);
    }

    /**
     * 等待进度
     *
     * @param viewRoot
     */
    private void initProgressView(LinearLayout viewRoot) {
        LinearLayout root = new LinearLayout(context);
        root.setGravity(Gravity.CENTER);
        root.setPadding(dp2px(10), dp2px(10), dp2px(10), dp2px(10));
        if (customViewListener != null) {
            View view = customViewListener.onInitCustomView(root);
            if (view != null) {
                root.addView(view);
                viewRoot.addView(root);
            }
        }
    }

    /**
     * 输入框
     */
    private void initInputView(LinearLayout viewRoot) {
        inputView = new EditText(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(dp2px(15), dp2px(5), dp2px(15), dp2px(5));
        inputView.setLayoutParams(layoutParams);
        inputView.setTextColor(contentTextColor);
        inputView.setTextSize(contentTextSize);
        if (inputListener != null) {
            inputListener.onInitEditText(inputView);
        }
        viewRoot.addView(inputView);
    }

    /**
     * 标题
     */
    private void initTitleView(LinearLayout viewRoot) {
        titleView = new TextView(context);
        titleView.setText(title);
        titleView.setGravity(Gravity.CENTER);
        titleView.setTextSize(titleTextSize);
        titleView.setTextColor(titleTextColor);
        titleView.setPadding(dp2px(5), dp2px(5), dp2px(5), dp2px(5));
        viewRoot.addView(titleView);
    }

    /**
     * 标题下面分割线
     */
    private void initTitleLineView(LinearLayout viewRoot) {
        titleLineView = new View(context);
        titleLineView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp2px(1)));
        titleLineView.setBackgroundColor(Color.LTGRAY);
        viewRoot.addView(titleLineView);
    }

    /**
     * 标题下方，内容文本上方的图片
     */
    private void initImageView(ViewGroup viewRoot) {
        imageView = new ImageView(context);
        imageView.setAdjustViewBounds(true);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(dp2px(15), dp2px(5), dp2px(15), dp2px(5));
        imageView.setLayoutParams(layoutParams);
        viewRoot.addView(imageView);
        if (imageListener != null) {
            imageListener.onInitImageView(imageView);
        }
    }

    /**
     * 内容显示
     */
    private void initContentView(ViewGroup viewRoot) {
        contentView = new TextView(context);
        contentView.setText(content);
        contentView.setGravity(Gravity.CENTER);
        contentView.setTextSize(contentTextSize);
        contentView.setTextColor(contentTextColor);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(dp2px(15), dp2px(15), dp2px(15), dp2px(15));
        contentView.setLayoutParams(layoutParams);
        viewRoot.addView(contentView);
    }

    /**
     * 列表显示，显示列表屏蔽按钮
     */
    private void initListView(ViewGroup viewRoot) {
        listView = new ListView(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(dp2px(15), dp2px(5), dp2px(15), dp2px(5));
        listView.setLayoutParams(layoutParams);
        listView.setCacheColorHint(Color.TRANSPARENT);
        listView.setFadingEdgeLength(0);
        listView.setVerticalScrollBarEnabled(false);
        listView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        listView.setDivider(new ColorDrawable(Color.LTGRAY));
        listView.setDividerHeight(dp2px(0.8f));
        if (dialogAdapter == null) {
            dialogAdapter = new ListDialogAdapter();
        }
        listView.setAdapter(dialogAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dismiss();
                if (listener != null) {
                    listener.click(false, position);
                }
            }
        });

        listView.setLayoutAnimation(getLayoutAnimation());
        viewRoot.addView(listView);
    }

    /**
     * 按钮布局
     */
    private void initButtonLayout(ViewGroup viewRoot) {
        LinearLayout buttonRoot = new LinearLayout(context);
        buttonRoot.setOrientation(LinearLayout.HORIZONTAL);
        buttonRoot.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, dp2px(5), 0, 0);
        buttonRoot.setLayoutParams(lp);
        for (int i = 0; i < buttonTexts.length; i++) {
            TextView textView = new TextView(context);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
            layoutParams.weight = 1;
//                layoutParams.setMargins(10, 10, 10, 10);
            textView.setPadding(0, 20, 0, 20);
            textView.setLayoutParams(layoutParams);
            textView.setBackgroundColor(Color.RED);
            textView.setMinHeight(50);
            textView.setTextColor(buttonTextColor);
            textView.setTextSize(buttonTextSize);
            if (buttonTexts.length == 1) {
                textView.setBackgroundDrawable(getButtonBackground(true, true, true));
            } else {
                if (i == 0) {
                    textView.setBackgroundDrawable(getButtonBackground(true, false, true));
                } else if (i == buttonTexts.length - 1) {
                    textView.setBackgroundDrawable(getButtonBackground(false, true, true));
                } else {
                    textView.setBackgroundDrawable(getButtonBackground(false, false, true));
                }
            }
            textView.setGravity(Gravity.CENTER);
            textView.setText(buttonTexts[i]);
            final int finalI = i;
            final int finalI1 = i;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    if (listener != null && !isShowInput) {
                        listener.click(true, finalI);
                    }
                    if (inputListener != null && isShowInput) {
                        inputListener.onComplete(finalI1, inputView.getText().toString());
                    }
                }
            });
            buttonRoot.addView(textView);
        }
        viewRoot.addView(buttonRoot);
    }

    private LayoutAnimationController getLayoutAnimation() {
        TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 2f, Animation.RELATIVE_TO_SELF,
                0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.setDuration(550);
        LayoutAnimationController layoutAnimationController = new LayoutAnimationController(animation, 0.12f);
        layoutAnimationController.setInterpolator(new DecelerateInterpolator());
        return layoutAnimationController;
    }

    private GradientDrawable getButtonBackground(boolean isFirstButton, boolean isLastButton, boolean isStroke) {
        GradientDrawable gd = new GradientDrawable();
        if (isFirstButton) {
            if (isLastButton) {
                gd.setCornerRadii(new float[]{0, 0, 0, 0, dp2px(6), dp2px(6), dp2px(6), dp2px(6)});
            } else {
                gd.setCornerRadii(new float[]{0, 0, 0, 0, 0, 0, dp2px(6), dp2px(6)});
            }
        } else if (isLastButton) {
            gd.setCornerRadii(new float[]{0, 0, 0, 0, dp2px(6), dp2px(6), 0, 0});
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            gd.setColor(colorStateList);
        } else {
            gd.setColor(buttonBackgroundNormalColor);
        }
        if (isStroke) {
            gd.setStroke(1, Color.LTGRAY);
        }
        return gd;
    }

    private GradientDrawable getDialogBackground(float[] corner) {
        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadii(corner);
        gd.setColor(dialogBackground);
        return gd;
    }

    private void init() {
        displayMetrics = context.getResources().getDisplayMetrics();

        int[] colors = new int[]{buttonBackgroundPressedColor, buttonBackgroundNormalColor};
        int[][] states = new int[2][];
        states[0] = new int[]{android.R.attr.state_pressed};
        states[1] = new int[]{};
        colorStateList = new ColorStateList(states, colors);
    }

    public SuperDialog setTitle(String title) {
        this.title = title;
        isShowTitle = isNotEmpty(title);
        return this;
    }


    public SuperDialog setInputListener(onDialogInputListener inputListener) {
        isShowInput = true;
        this.inputListener = inputListener;
        return this;
    }


    public SuperDialog setImageListener(onDialogImageListener imageListener) {
        isShowImage = true;
        this.imageListener = imageListener;
        return this;
    }


    public SuperDialog setCustomViewListener(onDialogCustomViewListener customViewListener) {
        isShowProgress = true;
        this.customViewListener = customViewListener;
        return this;
    }

    public SuperDialog setShowImage() {
        isShowImage = true;
        return this;
    }

    public SuperDialog setShowButtonLayout(boolean showButtonLayout) {
        isShowButtonLayout = showButtonLayout;
        return this;
    }


    public SuperDialog setContent(String content) {
        this.content = content;
        isShowContent = isNotEmpty(content);
        return this;
    }


    public SuperDialog setDialogMenuItemList(List<DialogMenuItem> dialogMenuItemList) {
        isShowList = true;
        isShowButtonLayout = false;
        this.dialogMenuItemList = dialogMenuItemList;
        return this;
    }


    public SuperDialog setButtonTexts(String... buttonTexts) {
        this.buttonTexts = buttonTexts;
        isShowButtonLayout = true;
        return this;
    }

    public SuperDialog setListener(onDialogClickListener listener) {
        this.listener = listener;
        return this;
    }


    public SuperDialog setTitleTextSize(int titleTextSize) {
        this.titleTextSize = titleTextSize;
        return this;
    }


    public SuperDialog setTitleTextColor(int titleTextColor) {
        this.titleTextColor = titleTextColor;
        return this;
    }


    public SuperDialog setContentTextSize(int contentTextSize) {
        this.contentTextSize = contentTextSize;
        return this;
    }


    public SuperDialog setContentTextColor(int contentTextColor) {
        this.contentTextColor = contentTextColor;
        return this;
    }


    public SuperDialog setButtonTextSize(int buttonTextSize) {
        this.buttonTextSize = buttonTextSize;
        return this;
    }


    public SuperDialog setButtonTextColor(int buttonTextColor) {
        this.buttonTextColor = buttonTextColor;
        return this;
    }

    protected int dp2px(float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    private boolean isNotEmpty(String s) {
        if (s == null) {
            return false;
        }
        if (s.trim().length() == 0) {
            return false;
        }
        return true;
    }

    public interface onDialogClickListener {
        void click(boolean isButtonClick, int position);
    }

    public interface onDialogInputListener {
        void onInitEditText(EditText inputView);

        void onComplete(int buttonIndex, String text);
    }


    public interface onDialogImageListener {
        void onInitImageView(ImageView imageView);
    }

    public interface onDialogCustomViewListener {
        View onInitCustomView(LinearLayout viewGroup);
    }

    class ListDialogAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return dialogMenuItemList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final DialogMenuItem item = dialogMenuItemList.get(position);
            TextView menuItemView = new TextView(context);
            menuItemView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            menuItemView.setSingleLine(true);
            menuItemView.setTextColor(contentTextColor);
            menuItemView.setTextSize(TypedValue.COMPLEX_UNIT_SP, contentTextSize);
            menuItemView.setBackgroundDrawable(getButtonBackground(false, false, false));
            if (item.icon != 0) {
                Drawable drawable = context.getResources().getDrawable(item.icon);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                menuItemView.setCompoundDrawables(drawable, null, null, null);
                menuItemView.setCompoundDrawablePadding(dp2px(5));
            }
            menuItemView.setPadding(dp2px(3), dp2px(3), dp2px(3), dp2px(3));
            menuItemView.setText(item.itemName);
            return menuItemView;
        }
    }

    public static class DialogMenuItem {
        public String itemName;
        public int icon;

        public DialogMenuItem(String itemName, int icon) {
            this.itemName = itemName;
            this.icon = icon;
        }
    }
}
