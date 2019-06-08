package com.upp.piechartdemo;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author yunkun.wen
 * @Time 2019/6/5
 */
public class PieChartView extends View implements ValueAnimator.AnimatorUpdateListener {

    private Paint mPaint;
    private List<Integer> colors;
    private List<Float> datas;
    private float dataSum;
    private int colorAmount;
    private static final int fullDegree = 360;
    private static final float quarterDegree = 90F;
    private static final float halfDegree = 180F;
    private float rotateDegree = 0;
    private int radius;
    private float startAngle;
    private int curIndex = 0;
    private float degreeT = 0;
    private ValueAnimator valueAnimator;
    private long DURATION = 1000;
    private List<Float> startAngles = new ArrayList<>();
    private List<Float> sweepAngles = new ArrayList<>();
    private float curPointDegree, nextPointDegree;

    public PieChartView(Context context) {
        super(context);
        init();
    }

    public PieChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PieChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (datas == null) {
            return;
        }
        int width = getWidth();
        int height = getHeight();

        radius = Math.min(width, height) >> 1;

        // 将画布移动到中心
        canvas.translate(width >> 1, height >> 1);
        canvas.drawColor(Color.GRAY);
        canvas.rotate(rotateDegree);


        drawArc(canvas);
        canvas.rotate(-1 * rotateDegree);
        drawBitmap(canvas);
//        canvas.translate(-1 * width >> 1, -1 * width >> 1);
    }

    private void drawBitmap(Canvas canvas) {
        Bitmap bitmap;
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_bg);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        canvas.drawBitmap(bitmap, -1 * bitmap.getWidth() >> 1, -1 * bitmap.getHeight() >> 1, paint);
    }


    //
    private void drawArc(Canvas canvas) {
        RectF rectF = new RectF(-radius, -radius, radius, radius);
        for (int i = 0; i < datas.size(); i++) {
            canvas.save();
            if (i == 0) {
                startAngle = quarterDegree + halfDegree * datas.get(0) / dataSum;
                if (!startAngles.contains(startAngle)) {
                    startAngles.add(0, startAngle);
                }
            }
            mPaint.setColor(colors.get(i % colorAmount));
            float sweepAngle = datas.get(i) / dataSum * fullDegree;

            canvas.drawArc(rectF, startAngle, -sweepAngle, true, mPaint);
            startAngle = startAngle - sweepAngle;
            if (startAngles.size() < datas.size()) {
                startAngles.add(startAngle);
            }
            if (sweepAngles.size() < datas.size()) {
                sweepAngles.add(sweepAngle);
            }
            canvas.restore();
        }
    }

    public void setData(List<Integer> colors, List<Float> datas, float dataSum) {
        if (datas == null || colors == null) {
            return;
        }
        this.colors = colors;
        this.datas = datas;
        this.dataSum = dataSum;
        colorAmount = this.colors.size();
        invalidate();
    }


    /**
     * 当前选中数据的下标
     *
     * @param index
     */
    public void setIndex(int index) {
        if (datas == null || curIndex == index) {
            return;
        }


        nextPointDegree = startAngles.get(index) - sweepAngles.get(index) / 2;

        if (curIndex == 0) {
            curPointDegree = quarterDegree;
        }
        degreeT += curPointDegree -nextPointDegree; // 画布需要转的角度
        curIndex = index;
        curPointDegree = nextPointDegree;
        rotation(degreeT);

    }

    private void rotation(float degree) {
        if (rotateDegree != degree) {
            animateToValue(degree);
        }
    }

    private void animateToValue(float value) {
        if (valueAnimator == null) {
            valueAnimator = createAnimator(value);
        }
        valueAnimator.setFloatValues(rotateDegree, value);
        valueAnimator.setDuration(DURATION);
        if (valueAnimator.isRunning()) {
            valueAnimator.cancel();
        }
        valueAnimator.start();
    }

    private ValueAnimator createAnimator(float value) {
        ValueAnimator valueAnimator;
        valueAnimator = ValueAnimator.ofFloat(rotateDegree, value);
        valueAnimator.setDuration(DURATION);
        valueAnimator.setInterpolator(new OvershootInterpolator());
        valueAnimator.addUpdateListener(this);
        return valueAnimator;
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        rotateDegree = Float.valueOf(valueAnimator.getAnimatedValue().toString());
        invalidate();
    }
}
