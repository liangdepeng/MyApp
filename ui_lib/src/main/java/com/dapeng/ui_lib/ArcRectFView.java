package com.dapeng.ui_lib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 背景 上面是矩形 _底部是圆弧 _可定制矩形和圆弧的显示比例
 */
public class ArcRectFView extends View {

    private Paint paint;
    // 颜色
    private int color;
    // 矩形占的比例
    private int topRectFWeight;
    // 圆弧占的比例
    private int bottomArcWeight;
    private RectF topRectF;
    private RectF bottomRectF;

    public ArcRectFView(Context context) {
        this(context, null);
    }

    public ArcRectFView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcRectFView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context, attrs);
    }

    private void initData(Context context, AttributeSet attrs) {

        if (attrs != null) {
            TypedArray typed = context.obtainStyledAttributes(attrs, R.styleable.ArcRectFView);
            color = typed.getColor(R.styleable.ArcRectFView_default_color, Color.parseColor("#75EEFA"));
            topRectFWeight = typed.getInt(R.styleable.ArcRectFView_top_rectf_weight, 5);
            bottomArcWeight = typed.getInt(R.styleable.ArcRectFView_bottom_arc_weight, 1);
            typed.recycle();
        }

        paint = new Paint();
        paint.setColor(color);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);

        topRectF = new RectF();
        bottomRectF = new RectF();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float width = getWidth();
        float height = getHeight();

        float allWeight = topRectFWeight + bottomArcWeight;

        topRectF.left = 0;
        topRectF.top = 0;
        topRectF.right = width;
        topRectF.bottom = (height / (allWeight * 2)) * (allWeight * 2 - 1);

        bottomRectF.left = 0;
        bottomRectF.top = (height / allWeight) * topRectFWeight;
        bottomRectF.right = width;
        bottomRectF.bottom = height;

        // 画弧形
        canvas.drawArc(bottomRectF, 0, 180, false, paint);
        // 画顶部矩形
        canvas.drawRect(topRectF, paint);

    }

    public void setPaintColor(int color) {
        if (paint != null) {
            paint.setColor(color);
            invalidate();
        }
    }
}
