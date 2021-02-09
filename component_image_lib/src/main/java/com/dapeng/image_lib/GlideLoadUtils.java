package com.dapeng.image_lib;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.dapeng.utils_lib.device.DensityUtil;


public class GlideLoadUtils {

    public static void loadImage(Context context, String imageUrl, ImageView imageView) {
        Glide.with(context)
                .load(imageUrl)
                .error(R.drawable.ic_load_failed_1)
                .placeholder(R.drawable.ic_load_failed_1)
                .into(imageView);
    }

    public static void loadImageWithCorner(Context context, String imageUrl, ImageView imageView, int corners) {

        RequestOptions requestOptions = new RequestOptions()
                .error(R.drawable.ic_load_failed_1)
                .placeholder(R.drawable.ic_load_failed_1)
                .transform(new CenterCrop(), new RoundedCorners(DensityUtil.dip2px(context, corners)));
        //    requestOptions.transform(new CenterCrop(),new GranularRoundedCorners(0,0,0,0));

        Glide.with(context)
                .load(imageUrl)
                .apply(requestOptions)
                .into(imageView);
    }

    public static void loadCircleImage(Context context, String imageUrl, ImageView imageView) {
        Glide.with(context)
                .load(imageUrl)
                .circleCrop()
                .error(R.drawable.ic_load_failed_1)
                .placeholder(R.drawable.ic_load_failed_1)
                .into(imageView);
    }
}
