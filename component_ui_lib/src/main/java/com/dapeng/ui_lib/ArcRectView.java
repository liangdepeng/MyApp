package com.dapeng.ui_lib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

/**
 * 背景 上面是矩形 _底部是圆弧 _可定制矩形和圆弧的高度比例
 *
 * @author ldp
 */
public class ArcRectView extends View {

    /**
     * 画笔
     */
    private Paint paint;
    /**
     * 颜色
     */
    private int color;
    /**
     * 矩形高度占的比例
     */
    private int topRectWeight;
    /**
     * 圆弧高度占的比例
     */
    private int bottomArcWeight;
    /**
     * 圆弧高度占的比例
     */
    private RectF topRectF;
    /**
     * 弧形 路径
     */
    private Path path;

    public ArcRectView(Context context) {
        this(context, null);
    }

    public ArcRectView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcRectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context, attrs);
    }


    private void initData(Context context, AttributeSet attrs) {

        if (attrs != null) {
            TypedArray typed = context.obtainStyledAttributes(attrs, R.styleable.ArcRectFView);
            color = typed.getColor(R.styleable.ArcRectFView_arc_rect_view_color, Color.parseColor("#75EEFA"));
            topRectWeight = typed.getInt(R.styleable.ArcRectFView_arc_rect_view_rect_weight, 5);
            bottomArcWeight = typed.getInt(R.styleable.ArcRectFView_arc_rect_view_arc_weight, 1);
            typed.recycle();
        }

        // 避免在 onDraw 方法中 new 对象 造成性能上的问题

        // 设置画笔
        paint = new Paint();
        paint.setColor(color);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);

        // 设置 弧形和矩形实例
        topRectF = new RectF();
        path = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 获取 view 宽度
        float width = getWidth();
        // 获取 view 高度
        float height = getHeight();

        // 全部高度 所占的比例 顶部矩形+底部圆弧
        float allWeight = topRectWeight + bottomArcWeight;

        // 矩形 的 上下左右
        topRectF.left = 0;
        topRectF.top = 0;
        topRectF.right = width;
        topRectF.bottom = height / allWeight * topRectWeight;

        // 移动 到起点  根据比例计算 相当于 矩形底部 ，为了合理优化计算误差 贴合紧密 特意 -1 也就是向上移动一点点
        path.moveTo(0, height / allWeight * topRectWeight - 1);
        path.quadTo(width / 2, height, width, height / allWeight * topRectWeight - 1);

        // 画顶部矩形
        canvas.drawRect(topRectF, paint);
        // 画弧形
        canvas.drawPath(path, paint);

    }

    /**
     * 代码设置 相关 UI
     *
     * @param topRectFWeight  顶部 矩形高度 比例
     * @param bottomArcWeight 底部 弧形高度 比例
     * @param color           整体颜色
     */
    public void resetArcView(int topRectFWeight, int bottomArcWeight, @ColorInt int color) {
        this.topRectWeight = topRectFWeight;
        this.bottomArcWeight = bottomArcWeight;
        this.color = color;
        if (paint != null) {
            paint.setColor(color);
            invalidate();
        }
    }

    /**
     * 可设置背景颜色 跟随一定的规则 变化 重绘
     *
     * @param color 颜色
     */
    public void setPaintColor(@ColorInt int color) {
        if (paint != null) {
            paint.setColor(color);
            invalidate();
        }
    }
}
