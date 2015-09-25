package com.zsl.customviewdemo.view2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.zsl.customviewdemo.view1.MyView;

/**
 * Created by zsl on 15/9/25.
 */
public class MyView2 extends View{
    Paint mPaint=new Paint();


    private float rx=0;

    public MyView2(Context context) {
        super(context);
    }

    public MyView2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setTextSize(30);
        mPaint.setColor(Color.parseColor("#0bd315"));
        rx=20;
        canvas.drawText("CustomView2",rx,30,mPaint);
    }
}
