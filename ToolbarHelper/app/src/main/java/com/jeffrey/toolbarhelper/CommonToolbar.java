package com.jeffrey.toolbarhelper;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Li on 2017/2/7.
 */

public class CommonToolbar extends RelativeLayout implements OnMenuClickListener{


    private TextView tvLeft;
    private TextView tvTitle;
    private ImageView ivLeft;
    private int leftTextColor;
    private float leftTextSize;


    private boolean showBackImg;
    private String  leftText;
    private RelativeLayout rlLeft;


    private String  title;
    private float     titleSize;
    private int   titleColor = Color.WHITE;

    private LinearLayout llRight;
    private ArrayList<RightMenu>  menus;
    private int menuTextColor;
    private float menuTextSize;


    private final int     defaultTitleSize = 18;//sp
    private final int     defaultLeftTextSize = 14;//sp
    private final int     defaultMenuTextSize = 14;//sp
    private int     defaultBackImg = R.drawable.ic_arrow_back_white_24dp;

    private View.OnClickListener  onLeftClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Activity activity = getActivity();
            if (activity != null){
                activity.onBackPressed();
            }
        }
    };
    private OnMenuClickListener onMenuClickListener;

    public CommonToolbar(Context context) {
        super(context);
    }

    public CommonToolbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CommonToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public void setLeftClickListener(View.OnClickListener onLeftClickListener){
        this.onLeftClickListener = onLeftClickListener;
    }

    public void setOnMenuClickListener(OnMenuClickListener onMenuClickListener){
        this.onMenuClickListener = onMenuClickListener;
    }

    public void setTitle(String title){
        showTitle(title);
    }

    public void setLeftText(String left){
        showLeftText(left);
    }

    private void init(AttributeSet attrs){
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs,R.styleable.CommonToolbar);
        leftText = typedArray.getString(R.styleable.CommonToolbar_leftText);
        leftTextColor = typedArray.getColor(R.styleable.CommonToolbar_leftTextColor,Color.WHITE);

        title = typedArray.getString(R.styleable.CommonToolbar_title);
        titleColor = typedArray.getColor(R.styleable.CommonToolbar_titleColor,Color.WHITE);
        titleSize = typedArray.getDimension(R.styleable.CommonToolbar_titleSize,TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,defaultTitleSize,getResources().getDisplayMetrics()));
        showBackImg = typedArray.getBoolean(R.styleable.CommonToolbar_showDefaultBack,false);
        leftTextSize = typedArray.getDimension(R.styleable.CommonToolbar_titleSize,TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,defaultLeftTextSize,getResources().getDisplayMetrics()));


        menuTextSize = typedArray.getDimension(R.styleable.CommonToolbar_menuTextSize,TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,defaultMenuTextSize,getResources().getDisplayMetrics()));
        menuTextColor = typedArray.getColor(R.styleable.CommonToolbar_menuTextColor,Color.WHITE);


        if (showBackImg || !TextUtils.isEmpty(leftText)){
            rlLeft = new RelativeLayout(this.getContext());
            int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,55,getResources().getDisplayMetrics());
            RelativeLayout.LayoutParams  lp = new RelativeLayout.LayoutParams(width, LayoutParams.MATCH_PARENT);
            lp.addRule(RelativeLayout.ALIGN_LEFT);
            rlLeft.setLayoutParams(lp);

            final TypedArray a = getContext().obtainStyledAttributes(R.style.AppTheme, new int[] {R.attr.selectableItemBackground});
            int attributeResourceId = a.getResourceId(0, 0);
            Drawable drawable = ActivityCompat.getDrawable(getContext(),attributeResourceId);
            rlLeft.setBackground(drawable);
            addView(rlLeft);
            a.recycle();
            rlLeft.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onLeftClickListener != null){
                        onLeftClickListener.onClick(v);
                    }
                }
            });

        }
        if (!TextUtils.isEmpty(leftText)){
            showLeftText(leftText);
        }

        if (showBackImg){
            setShowBackImg(ActivityCompat.getDrawable(getContext(),defaultBackImg));
        }

        if (!TextUtils.isEmpty(title)){
            showTitle(title);
        }


        typedArray.recycle();
    }


    private void showLeftText(String text){
        if (tvLeft == null){
            TextView tv = new TextView(this.getContext());
            RelativeLayout.LayoutParams  lp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            lp.addRule(RelativeLayout.CENTER_IN_PARENT);
            tv.setLayoutParams(lp);
            rlLeft.addView(tv);
            tvLeft = tv;
            tvLeft.setText(text);
            tvLeft.setTextSize(TypedValue.COMPLEX_UNIT_PX, leftTextSize);
            tvLeft.setTextColor(leftTextColor);
        }else {
            tvLeft.setText(text);
        }


        if (ivLeft != null){
            rlLeft.removeView(ivLeft);
            ivLeft = null;
        }
    }


    private void showTitle(String text){
        if (tvTitle == null){
            TextView tv = new TextView(this.getContext());
            RelativeLayout.LayoutParams  lp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            lp.addRule(RelativeLayout.CENTER_IN_PARENT);
            tv.setLayoutParams(lp);
            addView(tv);
            tvTitle = tv;
            tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX,titleSize);
            tvTitle.setTextColor(titleColor);
            tvTitle.setText(title);
        }else {
            tvTitle.setText(text);
        }
    }

    private void setShowBackImg(Drawable leftDrawable){
        if (ivLeft == null){
            ImageView iv = new ImageView(this.getContext());
            RelativeLayout.LayoutParams  lp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            lp.addRule(RelativeLayout.CENTER_IN_PARENT);
            iv.setLayoutParams(lp);
            rlLeft.addView(iv);
            ivLeft = iv;
            ivLeft.setImageDrawable(leftDrawable);
        }else {
            ivLeft.setImageDrawable(leftDrawable);
        }

        if (tvLeft != null){
            rlLeft.removeView(tvLeft);
            tvLeft = null;
        }
    }


    public void setRightMenu(ArrayList<CommonToolbar.RightMenu> menus){
        if (llRight == null){
            llRight = new LinearLayout(this.getContext());
            RelativeLayout.LayoutParams  lp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            llRight.setLayoutParams(lp);
            addView(llRight);
        }

        llRight.removeAllViews();

        for (RightMenu menu : menus){
            setMenu(menu,llRight);
        }

    }


    private void setMenu(RightMenu menu,LinearLayout parent){
        int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,55,getResources().getDisplayMetrics());
        TypedArray a = getContext().obtainStyledAttributes(R.style.AppTheme, new int[] {R.attr.selectableItemBackground});
        int attributeResourceId = a.getResourceId(0, 0);
        Drawable drawable = ActivityCompat.getDrawable(getContext(),attributeResourceId);
        if (!TextUtils.isEmpty(menu.getText())){
            TextView tv = new TextView(this.getContext());
            RelativeLayout.LayoutParams  lp = new RelativeLayout.LayoutParams(width, LayoutParams.MATCH_PARENT);
            lp.addRule(RelativeLayout.CENTER_IN_PARENT);
            tv.setLayoutParams(lp);
            tv.setGravity(Gravity.CENTER);
            tv.setBackground(drawable);
            tv.setText(menu.getText());
            tv.setTextColor(menuTextColor);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,menuTextSize);
            parent.addView(tv);
            tv.setOnClickListener(new MenuClick(menu.getId(),this));
        }else if (menu.getDrawableId() > 0){
            RelativeLayout relativeImage = new RelativeLayout(getContext());
            RelativeLayout.LayoutParams  relativeLP = new RelativeLayout.LayoutParams(width, LayoutParams.MATCH_PARENT);
            relativeImage.setLayoutParams(relativeLP);
            parent.addView(relativeImage);

            ImageView iv = new ImageView(this.getContext());
            RelativeLayout.LayoutParams  lp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            lp.addRule(RelativeLayout.CENTER_IN_PARENT);
            iv.setLayoutParams(lp);
            iv.setImageDrawable(ActivityCompat.getDrawable(getContext(),menu.getDrawableId()));
            relativeImage.addView(iv);
            relativeImage.setOnClickListener(new MenuClick(menu.getId(),this));

        }
        a.recycle();
    }


    private Activity getActivity() {
        Context context = getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity)context;
            }
            context = ((ContextWrapper)context).getBaseContext();
        }
        return null;
    }

    @Override
    public void onClick(int id, View view) {
        if (onMenuClickListener != null){
            onMenuClickListener.onClick(id,view);
        }
    }

    public static class RightMenu{
        private int   id;
        private String text;
        private int    drawableId;

        public RightMenu(int id,String text, int drawableId) {
            this.id = id;
            this.text = text;
            this.drawableId = drawableId;
        }

        public String getText() {
            return text;
        }

        public int getDrawableId() {
            return drawableId;
        }

        public int getId() {
            return id;
        }
    }

    public class MenuClick implements View.OnClickListener{

        int id ;
        OnMenuClickListener onMenuClickListener;
        public MenuClick(int id,OnMenuClickListener onMenuClickListener){
            this.id = id;
            this.onMenuClickListener = onMenuClickListener;
        }
        @Override
        public void onClick(View v) {
            if (onMenuClickListener != null){
                onMenuClickListener.onClick(id,v);
            }
        }
    }


}