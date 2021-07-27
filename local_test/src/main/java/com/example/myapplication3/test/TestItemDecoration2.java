package com.example.myapplication3.test;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by ldp.
 * <p>
 * Date: 2021/7/15
 * <p>
 * Summary:
 */
public class TestItemDecoration2 extends RecyclerView.ItemDecoration {

    private final int spacePx;
    private final int spanCount;
    private int orientation;
    private final Paint paint;
    private boolean outSpace = true;

    public TestItemDecoration2(int spacePx, int spanCount) {
        this(spacePx,spanCount,0,true);
    }

    public TestItemDecoration2(int spacePx, int spanCount, int orientation, boolean outSpace) {
        this.spacePx = spacePx;
        this.spanCount = spanCount;
        this.orientation = orientation;
        this.outSpace = outSpace;
        paint = new Paint();
        paint.setColor(Color.BLUE);
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        drawVerticalLine(c, parent, state);
        drawHorizontalLine(c, parent, state);
    }

    /**
     * 画横线
     */
    private void drawHorizontalLine(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getChildCount();
        for (int childIndex = 0; childIndex < childCount; childIndex++) {
            View itemView = parent.getChildAt(childIndex);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) itemView.getLayoutParams();

            int top;
            int bottom;
            int left = itemView.getLeft() - layoutParams.leftMargin - spacePx;
            int right = itemView.getRight() + layoutParams.rightMargin + spacePx;

            if (childIndex < spanCount && outSpace) {
                // 第一行 上面的 列表最外面一层 顶部间隔
                top = itemView.getTop() - layoutParams.topMargin - spacePx;
                bottom = top + spacePx;
                c.drawRect(left, top, right, bottom, paint);
            }

            if (!outSpace && (childIndex / spanCount + 1) * spanCount >= childCount) {
                // 如果不画外圈 则判断 最后一行 就不用绘制
                return;
            }

            // 正常绘制下面的 间隔
            top = itemView.getBottom() + layoutParams.bottomMargin;
            bottom = top + spacePx;

            c.drawRect(left, top, right, bottom, paint);

        }
    }

    /**
     * 画竖线
     */
    private void drawVerticalLine(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getChildCount();
        for (int childIndex = 0; childIndex < childCount; childIndex++) {
            View itemView = parent.getChildAt(childIndex);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) itemView.getLayoutParams();

            int top = itemView.getTop() - layoutParams.topMargin;
            int bottom = itemView.getBottom() + layoutParams.bottomMargin;
            int left = 0;
            int right = 0;

            if (childIndex % spanCount == 0 && outSpace) {
                // 每行左边第一个 整个列表最左侧一列 间隔绘制
                left = itemView.getLeft() - layoutParams.leftMargin - spacePx;
                right = left + spacePx;
                // 最左边的线
                c.drawRect(left, top, right, bottom, paint);
            }

            if ((childIndex + 1) / spanCount == 1 && !outSpace) {
                // 如果不画外圈 则判断 最后一列 就不用绘制
                return;
            }

            // 正常绘制最右侧的一列
            left = itemView.getRight() + layoutParams.rightMargin;//
            right = left + spacePx;
            c.drawRect(left, top, right, bottom, paint);
        }
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int position = parent.getChildAdapterPosition(view);

        if (outSpace) {
            if (position == 0) {
                outRect.set(spacePx, spacePx, spacePx, spacePx);
            } else if (position < spanCount) {
                outRect.set(0, spacePx, spacePx, spacePx);
            } else if (position % spanCount == 0) {
                outRect.set(spacePx, 0, spacePx, spacePx);
            } else {
                outRect.set(0, 0, spacePx, spacePx);
            }
        } else {
            if (position == parent.getChildCount() - 1) {
                outRect.set(0, 0, 0, 0);
            } else if ((position + 1) / spanCount == 1) {
                outRect.set(0, 0, 0, spacePx);
            } else if ((position / spanCount + 1) * spanCount >= parent.getChildCount()) {
                outRect.set(0, 0, spacePx, 0);
            } else {
                outRect.set(0, 0, spacePx, spacePx);
            }
        }


    }

}
