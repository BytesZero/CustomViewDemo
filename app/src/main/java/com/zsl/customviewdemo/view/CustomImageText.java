package com.zsl.customviewdemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.zsl.customviewdemo.R;

/**
 * Created by zsl on 15/11/15.
 */
public class CustomImageText extends View {

    private final int IMAGE_SCALE_FITXY = 0;

    //属性
    String mImageText;
    int mImageTextColor;
    float mImageTextSize;
    Bitmap mImage;
    int mImageScaleType;

    //绘制
    Rect mRect;
    Paint mPaint;
    Rect mTextBound;

    //宽高
    int mWidth, mHeight;

    public CustomImageText(Context context) {
        this(context, null);
    }

    public CustomImageText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomImageText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomImageText, defStyleAttr, 0);
        int n = a.getIndexCount();

        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.CustomImageText_imageText) {
                mImageText = a.getString(attr);
            } else if (attr == R.styleable.CustomImageText_imageTextColor) {
                mImageTextColor = a.getColor(attr, Color.WHITE);
            } else if (attr == R.styleable.CustomImageText_imageTextSize) {
                mImageTextSize = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
            } else if (attr == R.styleable.CustomImageText_imageSrc) {
                mImage = BitmapFactory.decodeResource(getResources(), a.getResourceId(attr, 0));
            } else if (attr == R.styleable.CustomImageText_imageScaleType) {
                mImageScaleType = a.getInt(attr, 0);
            }
        }
        a.recycle();

        mRect = new Rect();
        mPaint = new Paint();
        mTextBound = new Rect();
        // 设置字体大小
        mPaint.setTextSize(mImageTextSize);
        //计算绘制字体所需的范围
        mPaint.getTextBounds(mImageText, 0, mImageText.length(), mTextBound);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        /**
         * 计算宽度
         */
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            mWidth = specSize;
        } else {
            //图片的高度
            int desiredImg = mImage.getWidth() + getPaddingLeft() + getPaddingRight();
            //字体的高度
            int desireadText = mTextBound.width() + getPaddingRight() + getPaddingLeft();

            if (specMode == MeasureSpec.AT_MOST) {
                int desired = Math.max(desiredImg, desireadText);
                mWidth = Math.min(specSize, desired);
            }


        }

        /**
         * 计算高度
         */
        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize = MeasureSpec.getSize(heightMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            mHeight = specSize;
        } else {
            int desired = getPaddingTop() + getPaddingBottom() + mTextBound.height() + mImage.getHeight();
            if (specMode == MeasureSpec.AT_MOST) {
                mHeight = Math.min(specSize, desired);
            }
        }

        setMeasuredDimension(mWidth, mHeight);

    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);

        //绘制边框
        mPaint.setStrokeWidth(4);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.GREEN);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);

        mRect.left = getPaddingLeft();
        mRect.right = mWidth - getPaddingRight();
        mRect.top = getPaddingTop();
        mRect.bottom = mHeight - getPaddingBottom();

        mPaint.setColor(mImageTextColor);
        mPaint.setStyle(Paint.Style.FILL);
        //绘制字体
        if (mTextBound.width() > mWidth) {
            TextPaint paint = new TextPaint(mPaint);
            String content = TextUtils.ellipsize(mImageText, paint, mWidth - getPaddingLeft() - getPaddingRight(), TextUtils.TruncateAt.END).toString();
            canvas.drawText(content, getPaddingLeft(), mHeight - getPaddingBottom(), mPaint);

        } else {
            canvas.drawText(mImageText, (mWidth - mTextBound.width() * 1.0f) / 2, (mHeight - getPaddingBottom()), mPaint);
        }
        mRect.bottom -= mTextBound.height();
        //绘制图片
        if (mImageScaleType == IMAGE_SCALE_FITXY) {
            canvas.drawBitmap(mImage, null, mRect, mPaint);
        } else {
            mRect.left = (mWidth - mImage.getWidth()) / 2;
            mRect.right = (mWidth + mImage.getWidth()) / 2;
            mRect.top = (mHeight - mTextBound.height() - mImage.getHeight()) / 2;
            mRect.bottom = (mHeight - mTextBound.height() + mImage.getHeight()) / 2;
            canvas.drawBitmap(mImage, null, mRect, mPaint);
        }


    }
}
