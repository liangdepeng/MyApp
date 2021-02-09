package com.dapeng.utils_lib.device;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * 像素转换 工具类
 *
 * dp-px-dp
 * sp-px-sp
 *
 * 屏幕宽度-高度 状态栏高度等
 */
public class DensityUtil {

    /**
     * px 转 dp
     */
    public static int px2dip(Context context, float pxValue) {
        float scale = getScreenDendity(context);
        return (int) ((pxValue / scale) + 0.5f);
    }

    /**
     * dp转px  方法1
     *
     */
    public static int dip2px(float dipValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, Resources.getSystem().getDisplayMetrics());
    }

    /**
     * dp转px  方法2
     */
    public static int dip2px(Context context, float dpValue) {
        float scale = getScreenDendity(context);
        return (int) ((dpValue * scale) + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     * @param pxValue
     * @return
     */

    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }


    /**
     * 获取屏幕的宽度（像素）
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;//1080
    }

    /**
     * 获取屏幕的宽度（dp）
     */
    public static int getScreenWidthDp(Context context) {
        float scale = getScreenDendity(context);
        return (int) (context.getResources().getDisplayMetrics().widthPixels / scale);//360
    }

    /**
     * 获取屏幕的高度（像素）
     */
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;//1776
    }

    /**
     * 获取屏幕的高度（像素）
     */
    public static int getScreenHeightDp(Context context) {
        float scale = getScreenDendity(context);
        return (int) (context.getResources().getDisplayMetrics().heightPixels / scale);//592
    }

    /**
     * 屏幕密度比例
     */
    public static float getScreenDendity(Context context) {
        return context.getResources().getDisplayMetrics().density;//3
    }

    /**
     * 获取状态栏的高度 72px
     * http://www.2cto.com/kf/201501/374049.html
     */
    public static int getStatusBarHeight(Context context) {
        int statusHeight = -1;
        try {
            Class<?> aClass = Class.forName("com.android.internal.R$dimen");
            Object object = aClass.newInstance();
            int height = Integer.parseInt(aClass.getField("status_bar_height").get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;

        //依赖于WMS(窗口管理服务的回调)【不建议使用】
        /*Rect outRect = new Rect();
        ((Activity)context).getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect);
        return outRect.top;*/
    }

    /**
     * 指定机型（displayMetrics.xdpi）下dp转px
     * 18dp - 50px
     */
    public static int dpToPx(Context context, int dp) {
        return Math.round(((float) dp * getPixelScaleFactor(context)));
    }

    /**
     * 指定机型（displayMetrics.xdpi）下px 转 dp
     * 50px - 18dp
     */
    public static int pxToDp(Context context, int px) {
        return Math.round(((float) px / getPixelScaleFactor(context)));
    }

    /**
     * 获取水平方向的dpi的密度比例值
     * 2.7653186
     */
    public static float getPixelScaleFactor(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (displayMetrics.xdpi / 160.0f);
    }


    public static float RATIO = 0.95F;//缩放比例值

    /**
     * px 转 dp【按照一定的比例】
     */
    public static int px2dipRatio(Context context, float pxValue) {
        float scale = getScreenDendity(context) * RATIO;
        return (int) ((pxValue / scale) + 0.5f);
    }

    /**
     * dp转px【按照一定的比例】
     */
    public static int dip2pxRatio(Context context, float dpValue) {
        float scale = getScreenDendity(context) * RATIO;
        return (int) ((dpValue * scale) + 0.5f);
    }

}
