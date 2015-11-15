package com.zsl.customviewdemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.zsl.customviewdemo.R;

/**
 * Created by zsl on 15/11/14.
 */
public class CustomTitleView extends View {

    //文本内容
    private String mTitleText;
    //文本颜色
    private int mTitleTextColor;
    //文本大小
    private int mTitleTextSize;


    /**
     * 绘制时控制文本绘制的范围
     */
    private Rect mBound;
    private Paint mPaint;


    public CustomTitleView(Context context) {
        this(context, null);
    }

    public CustomTitleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        /**
         * 获得自定义样式属性
         */
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomTitleView, defStyleAttr, 0);
        int n = typedArray.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = typedArray.getIndex(i);
            if (R.styleable.CustomTitleView_titleText == attr) {
                //获得文本
                mTitleText = typedArray.getString(attr);
            } else if (R.styleable.CustomTitleView_titleTextColor == attr) {
                //获得颜色＊＊默认为黑色
                mTitleTextColor = typedArray.getColor(attr, Color.BLACK);
            } else if (R.styleable.CustomTitleView_titleTextSize == attr) {
                //获得文本大小＊＊默认是16sp
                mTitleTextSize = typedArray.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
            }
        }
        //回收
        typedArray.recycle();

        /**
         * 获得绘制文本的宽和高
         */
        mPaint = new Paint();
        mPaint.setTextSize(mTitleTextSize);

        mBound = new Rect();
        mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBound);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            mPaint.setTextSize(mTitleTextSize);
            mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBound);
            float textWidth = mBound.width();
            int desired = (int) (getPaddingLeft() + textWidth + getPaddingRight());
            width = desired;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            mPaint.setTextSize(mTitleTextSize);
            mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBound);
            float textHeight = mBound.height();
            int desired = (int) (getPaddingTop() + textHeight + getPaddingBottom());
            height = desired;
        }


        setMeasuredDimension(width, height);
    }

    /**
     * 绘制方法
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        //绘制一个矩形颜色为黄色
        mPaint.setColor(Color.rgb(21, 96, 243));
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);
        //绘制一个text颜色为设置的颜色
        mPaint.setColor(mTitleTextColor);
        mPaint.setTextSize(mTitleTextSize);
        canvas.drawText(mTitleText, (getWidth() - mBound.width()) / 2, (getHeight() + mBound.height()) / 2, mPaint);
    }
}
