package com.zp.androidx.ext.toast;

import android.animation.AnimatorSet;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zp.androidx.ext.R;

/**
 * Create by zhaopan on 2020/6/29
 * 我们首先通过android.R.id.content来获得一个ViewGroup的实例，这个是DecorView内部的content布局，
 * 关于DecorView的内容读者可参考我之前的文章。这里简单说明一下：DecorView是我们Activity的根布局，
 * 是Activity初始化后生成的，而我们通过setContentView()方法添加的布局会添加到DecorView之内，
 * 而上面代码我们通过findViewById(android.R.id.content)获得了DecorView的一个子元素(名为content的布局)，
 * 在后面，我们会把相应的View都添加到这个布局里面。我们继续往下看：接着新建了一个LinearLayout布局，
 * 展现消息的View就是添加到该LinearLayout布局之内的，然后把LinearLayout添加到DecorView中，
 * 换句话来说：展现消息的View外面包裹了一层LinearLayout。
 * 但是，为什么要包裹一层LinearLayout呢？为什么不直接把View添加到DecorView呢？
 * 其实，这样做的目的是为了实现抽屉式的动画效果。我们只需要对mView的translationY属性进行操作就能轻松实现抽屉式效果了，
 * 这样非常方便。
 * ————————————————
 * 版权声明：本文为CSDN博主「程序员的自我反思」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
 * 原文链接：https://blog.csdn.net/a553181867/article/details/52254624
 */
public class XToast  {

    //general attributes:
    private Context mContext;
    private View mView;
    private ViewGroup mViewGroup;
    private ViewGroup mViewRoot;
    private LayoutInflater mInflater;
    private TextView mTextView;
    private int mTextColor = Color.WHITE;
    private int mTextSize = 20;
    private String message;
    private int mType = XTOAST_TYPE_NORMAL;
    private AnimatorSet mShowAnimatorSet;
    private AnimatorSet mHideAnimatorSet;
    private int mShowAnimationType;
    private int mHideAnimationType;
    private int mDuration;
    private int mBackgroundColor;
    private OnDisappearListener mOnDisappearListener;
    private XToastHandler mXToastHandler;

    //normal type attributes:
    private GradientDrawable mToastBackgound;

    //bottom type attributes:
    private Button mButton;
    private String mButtonText;
    private int mButtonTextSize = 20;
    private int mButtonTextColor = Color.WHITE;
    private OnButtonClickListener mOnClickListener;

    public static final int XTOAST_DURATION_LONG = 3500;
    public static final int XTOAST_DURATION_SHORT = 2000;
    public static final int XTOAST_DURATION_SPECIAL = 5000;

    public static final int XTOAST_TYPE_NORMAL = 1;
    public static final int XTOAST_TYPE_BOTTOM = 2;

    public interface OnDisappearListener{
        void onDisappear(XToast xToast);
    }

    public interface OnButtonClickListener{
        void click(XToast xToast);
    }

    public XToast(Context context)
    {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
    }

    public XToast(Context context,String message,int type)
    {
        this.mContext = context;
        this.message = message;
        this.mType = type;
        this.mInflater = LayoutInflater.from(context);
    }


    public static XToast create(Context context){
        return new XToast(context);
    }

    //create a normal XToast
    public static XToast create(Context context,String message){
        return new XToast(context,message,XTOAST_TYPE_NORMAL);
    }

    public static XToast create(Context context,String message,int type){
        return new XToast(context,message,type);
    }

    public XToast setType(int type){
        this.mType = type;
        return this;
    }

    public XToast setText(String message){
        this.message = message;
        return this;
    }

    public String getText(){
        return this.message;
    }

    public XToast setTextColor(int color){
        mTextColor = color;
        return this;
    }

    public XToast setTextSize(int size){
        mTextSize = size;
        return this;
    }

    public XToast setBackgroundColor(int color){
        this.mBackgroundColor = color;
        return this;
    }

    public XToast setView(View view){
        this.mView = view;
        return this;
    }

    public View getView(){
        return this.mView;
    }

    public ViewGroup getViewGroup(){
        return this.mViewGroup;
    }

    public ViewGroup getViewRoot(){
        return this.mViewRoot;
    }

    public XToast setDuration(int duration){
        this.mDuration = duration;
        return this;
    }

    public int getDuration(){
        return this.mDuration;
    }

    public XToast setAnimation(int animationType){
        this.mShowAnimationType = animationType;
        this.mHideAnimationType = animationType;
        return this;
    }

    public XToast setShowAnimation(int animationType){
        this.mShowAnimationType = animationType;
        return this;
    }

    public AnimatorSet getShowAnimatorSet(){
        return this.mShowAnimatorSet;
    }

    public XToast setHideAnimation(int animationType){
        this.mHideAnimationType = animationType;
        return this;
    }

    public AnimatorSet getHideAnimatorSet(){
        return this.mHideAnimatorSet;
    }

    public XToast setOnDisappearListener(OnDisappearListener onDisappearListener){
        this.mOnDisappearListener = onDisappearListener;
        return this;
    }

    public OnDisappearListener getOnDisappearListener(){
        return this.mOnDisappearListener;
    }

    public XToast setButtonText(String message){
        mButtonText = message;
        return this;
    }

    public XToast setButtonTextColor(int color){
        mButtonTextColor = color;
        return this;
    }

    public XToast setButtonTextSize(int size){
        mButtonTextSize = size;
        return this;
    }

    public XToast setButtonOnClickListener(OnButtonClickListener listener){
        mOnClickListener = listener;
        return this;
    }

    public boolean isShowing() {
        return mView != null && mView.isShown();
    }

    private void dismiss(){
        mXToastHandler.hideToast(this);
    }

    private void initViews() {
        mViewRoot = (ViewGroup) ((Activity)mContext).findViewById(android.R.id.content);

        switch (mType){
            case XTOAST_TYPE_NORMAL:
                initNormalViews();
                break;

            case XTOAST_TYPE_BOTTOM:
                initBottomViews();
                break;

            default:
                break;
        }

        //对mView的大小进行测量
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec((1<<30) -1, View.MeasureSpec.AT_MOST);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec((1<<30) -1,View.MeasureSpec.AT_MOST);
        mView.measure(widthMeasureSpec,heightMeasureSpec);

    }

    private void initNormalViews(){
        mViewGroup = new LinearLayout(mContext);
        FrameLayout.LayoutParams mViewGroupParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        mViewGroupParams.gravity = Gravity.BOTTOM | Gravity.CENTER;
        mViewGroupParams.bottomMargin = 200;
        mViewGroup.setLayoutParams(mViewGroupParams);
        mViewRoot.addView(mViewGroup);

        //如果用户没有使用自己的View，那么使用默认的mView
        if(mView == null){
            mView = mInflater.inflate(R.layout.ext_xtoast_normal,mViewGroup,false);
            mToastBackgound = (GradientDrawable) mView.getBackground();
            mTextView = (TextView) mView.findViewById(R.id.message);
            mTextView.setTextColor(mTextColor);
            mTextView.setTextSize(mTextSize);
            mTextView.setText(message);
            if(mBackgroundColor != 0){
                mToastBackgound.setColor(mBackgroundColor);
            }
        }
    }

    private void initBottomViews(){
        mViewGroup = new LinearLayout(mContext);
        FrameLayout.LayoutParams mViewGroupParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        mViewGroupParams.gravity = Gravity.BOTTOM;
        mViewGroup.setLayoutParams(mViewGroupParams);
        mViewRoot.addView(mViewGroup);

        mView = mInflater.inflate(R.layout.ext_xtoast_bottom, mViewGroup, false);
        mView.setBackgroundColor(mBackgroundColor != 0 ? mBackgroundColor : 0xFF2F5DFF);
        mTextView = (TextView) mView.findViewById(R.id.message);
        mTextView.setTextColor(mTextColor);
        mTextView.setTextSize(mTextSize);
        mTextView.setText(message);
        mButton = (Button) mView.findViewById(R.id.button);
        mButton.setTextSize(mButtonTextSize);
        mButton.setTextColor(mButtonTextColor);
        mButton.setText(mButtonText);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                mOnClickListener.click(XToast.this);
            }
        });

        //set the appropriate animation
        mShowAnimationType = AnimationUtils.ANIMATION_PULL;
        mHideAnimationType = AnimationUtils.ANIMATION_PULL;

        //set the show duration.
        setDuration(XTOAST_DURATION_SPECIAL);
    }

    public void show(){

        //准备工作
        initViews();
        if(this.mShowAnimationType == 0)
            this.mShowAnimatorSet = AnimationUtils.getShowAnimation(this,AnimationUtils.ANIMATION_DEFAULT);
        else
            this.mShowAnimatorSet = AnimationUtils.getShowAnimation(this,mShowAnimationType);

        if(this.mHideAnimationType == 0)
            this.mHideAnimatorSet = AnimationUtils.getHideAnimation(this, AnimationUtils.ANIMATION_DEFAULT);
        else
            this.mHideAnimatorSet = AnimationUtils.getHideAnimation(this,mHideAnimationType);

        if(mDuration == 0)
            mDuration = XTOAST_DURATION_SHORT;

        mXToastHandler = XToastHandler.getInstance();
        mXToastHandler.add(this);
    }
}