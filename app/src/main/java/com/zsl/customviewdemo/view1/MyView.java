package com.zsl.customviewdemo.view1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.zsl.customviewdemo.R;

/**
 * Created by zsl on 15/9/25.
 */
public class MyView extends View {

    Paint paint = new Paint();

    Bitmap bitmap;

    public MyView(Context context) {
        super(context);
        init();
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        bitmap= BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
    }


    @Override
    protected void onDraw(Canvas canvas) {

        //设置字体大小
        paint.setTextSize(30);
        //设置颜色为白色
        paint.setColor(Color.parseColor("#ffffff"));
        //绘制文字
        canvas.drawText("CustomView Demo", 0, 30, paint);
//        绘制直线
        canvas.drawLine(0, 60, 100, 60, paint);
//        绘制矩形
        canvas.drawRect(0, 80, 100, 200, paint);

        //rect
        Rect mRect=new Rect(0,290,200,500);
        //绘制矩形
        canvas.drawRect(mRect,paint);

        RectF rectF=new RectF(0,520,300,700);
        //绘制一个圆角矩形
        canvas.drawRoundRect(rectF,10,10,paint);

        //绘制一个圆形
        canvas.drawCircle(50,800,50,paint);

        //绘制一个图片
        canvas.drawBitmap(bitmap,0,900,paint);



    }
}
