package com.dapeng.online.banner

import android.os.Bundle
import com.dapeng.base_lib.base.BaseActivity
import com.dapeng.image_lib.GlideLoadUtils
import com.dapeng.online.R
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.config.IndicatorConfig
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.transformer.AlphaPageTransformer
import com.youth.banner.transformer.DepthPageTransformer
import kotlinx.android.synthetic.main.activity_banner.*
import java.util.*

class BannerTestActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_banner)
        showBackIvTitleAndFunction(true, "Banner")
        initView()
    }

    private fun initView() {
        banner.addBannerLifecycleObserver(this)
            .setIndicator(CircleIndicator(this))
            .isAutoLoop(true)
            .setLoopTime(2000)
            .setScrollTime(800)
           // .addPageTransformer(AlphaPageTransformer())
           // .addPageTransformer(DepthPageTransformer())
            .setIndicatorGravity(IndicatorConfig.Direction.LEFT)
            .setAdapter(object : BannerImageAdapter<String>(
                Arrays.asList(
                    "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1598958588978&di=05cc160005434ca97a88a0c740994c86&imgtype=0&src=http%3A%2F%2Fa3.att.hudong.com%2F14%2F75%2F01300000164186121366756803686.jpg",
                    "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1598958619767&di=8ebd99e4160fd2f5ada9dfd5b4cefbc3&imgtype=0&src=http%3A%2F%2Fa0.att.hudong.com%2F56%2F12%2F01300000164151121576126282411.jpg",
                    "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1598958633415&di=b55e1006696ec0686ee8703815b1da43&imgtype=0&src=http%3A%2F%2Fa4.att.hudong.com%2F52%2F52%2F01200000169026136208529565374.jpg"
                )) {
                override fun onBindView(holder: BannerImageHolder?, data: String?, position: Int, size: Int) {
                    GlideLoadUtils.loadImageWithCorner(this@BannerTestActivity, data, holder?.imageView, 10)
                }
            })
    }
}