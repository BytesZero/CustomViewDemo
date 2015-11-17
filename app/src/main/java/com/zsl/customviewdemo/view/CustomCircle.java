package com.zsl.customviewdemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.zsl.customviewdemo.R;

/**
 * Created by zsl on 15/11/17.
 * 自定义圆环
 */
public class CustomCircle extends View {
    //第一圈的颜色
    private int mFirstColor;
    //第二圈的颜色
    private int mSecondColor;
    //圆环的宽度
    private int mCircleWidth;
    //当前进度
    private int mProgress=0;
    //速度
    private int mSpeed;
    //是否应该开始下一个
    private boolean isNext=false;
    //画笔
    private Paint mPaint;


    public CustomCircle(Context context) {
        this(context, null);
    }

    public CustomCircle(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * 获得自定义的值
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public CustomCircle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a=context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomCircle,defStyleAttr,0);
        int n=a.getIndexCount();
        for (int i=0;i<n;i++){
            int attr=a.getIndex(i);
            if (attr==R.styleable.CustomCircle_firstColor){
                mFirstColor=a.getColor(attr, Color.GREEN);
            }else if (attr==R.styleable.CustomCircle_secondColor){
                mSecondColor=a.getColor(attr,Color.RED);
            }else if (attr==R.styleable.CustomCircle_circleWith){
                mCircleWidth=a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 20, getResources().getDisplayMetrics()));
            }else if (attr==R.styleable.CustomCircle_speed){
                mSpeed=a.getInt(attr,50);
            }
        }
        a.recycle();
        mPaint=new Paint();

        new Thread(){
            @Override
            public void run() {
                while (true) {
                    mProgress++;
                    if (mProgress == 360) {
                        mProgress = 0;
                        if (!isNext) {
                            isNext = true;
                        } else {
                            isNext = false;
                        }

                    }
                    postInvalidate();
                    try {
                        Thread.sleep(mSpeed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int center=getWidth()/2;//获取圆心的x坐标
        int radius=center-mCircleWidth/2;//圆的半径
        mPaint.setStrokeWidth(mCircleWidth);//设置圆环的宽度
        mPaint.setAntiAlias(true);//消除锯齿
        mPaint.setStyle(Paint.Style.STROKE);//设置为空心
        RectF oval=new RectF(center-radius,center-radius,center+radius,center+radius);//定义圆弧的形状和大小界限

        if (!isNext){
            mPaint.setColor(mFirstColor);
            canvas.drawCircle(center, center, radius, mPaint);//画出圆环
            mPaint.setColor(mSecondColor);
            canvas.drawArc(oval,-90,mProgress,false,mPaint);
        }else {
            mPaint.setColor(mFirstColor);
            canvas.drawCircle(center, center, radius, mPaint);
            mPaint.setColor(mSecondColor);
            canvas.drawArc(oval,-90,mProgress,false,mPaint);
        }


    }
}
