package com.dapeng.image_lib;

import android.widget.ImageView;

import org.xutils.image.ImageOptions;
import org.xutils.x;

public class XUtilLoadImageUtils {

    public static void loadImage(String imageUrl, ImageView imageView) {
        ImageOptions imageOptions = new ImageOptions.Builder()
                .setFailureDrawableId(R.drawable.ic_load_failed_1)
                .setFadeIn(true)
                .setIgnoreGif(false)
                .build();
        x.image().bind(imageView, imageUrl, imageOptions);
    }

    public static void loadImageWithCorner(String imageUrl, ImageView imageView, int radius) {
        ImageOptions imageOptions = new ImageOptions.Builder()
                .setFailureDrawableId(R.drawable.ic_load_failed_1)
                .setFadeIn(true)
                .setIgnoreGif(false)
                .setRadius(radius)
                .build();
        x.image().bind(imageView, imageUrl, imageOptions);
    }

    public static void loadCircleImage(String imageUrl, ImageView imageView) {
        ImageOptions imageOptions = new ImageOptions.Builder().setCircular(true)
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setFailureDrawableId(R.drawable.ic_load_failed_1)
                .setFadeIn(true)
                .setIgnoreGif(false)
                .build();
        x.image().bind(imageView, imageUrl, imageOptions);
    }
}
