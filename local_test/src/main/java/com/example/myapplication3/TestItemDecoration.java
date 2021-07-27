package com.example.myapplication3;

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
public class TestItemDecoration extends RecyclerView.ItemDecoration {

    private int space;
    private int spanCount;
    private int orientation;
    private Paint paint;

    public TestItemDecoration(int space, int spanCount, int orientation) {
        this.space = space;
        this.spanCount = spanCount;
        this.orientation = orientation;
        paint = new Paint();
        paint.setColor(Color.BLUE);
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);

        drawVerticalLine(c, parent, state);
        drawHorizontalLine(c, parent, state);
    }

    private void drawHorizontalLine(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getChildCount();
        for (int childIndex = 0; childIndex < childCount; childIndex++) {
            View itemView = parent.getChildAt(childIndex);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) itemView.getLayoutParams();

            int top = 0;
            int bottom = 0;
            int left = itemView.getLeft() - layoutParams.leftMargin - space;
            int right = itemView.getRight() + layoutParams.rightMargin + space;

            if (childIndex < spanCount) {
                // 第一行
                top = itemView.getTop() - layoutParams.topMargin - space;
                bottom = top + space;
                c.drawRect(left, top, right, bottom, paint);
            }

            top = itemView.getBottom() + layoutParams.bottomMargin;
            bottom = top + space;

            c.drawRect(left, top, right, bottom, paint);

        }
    }

    private void drawVerticalLine(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getChildCount();
        for (int childIndex = 0; childIndex < childCount; childIndex++) {
            View itemView = parent.getChildAt(childIndex);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) itemView.getLayoutParams();

            int top = itemView.getTop() - layoutParams.topMargin;
            int bottom = itemView.getBottom() + layoutParams.bottomMargin;
            int left = 0;
            int right = 0;

            if (childIndex % spanCount == 0) {
                // 左边第一个
                left = itemView.getLeft() - layoutParams.leftMargin - space;
                right = left + space;

                // 最左边的线
                c.drawRect(left, top, right, bottom, paint);

                left = itemView.getRight() + layoutParams.rightMargin;//
                right = left + space;

            } else {

                left = itemView.getRight() + layoutParams.rightMargin;//
                right = left + space;
            }

            c.drawRect(left, top, right, bottom, paint);

        }
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
//        outRect.set(0, 0, space, space);

        int position = parent.getChildAdapterPosition(view);
        if (position == 0) {
            outRect.set(space, space, space, space);
        } else if (position < spanCount) {
            outRect.set(0, space, space, space);
        } else if (position % spanCount == 0) {
            outRect.set(space, 0, space, space);
        } else {
            outRect.set(0, 0, space, space);
        }
    }

}
