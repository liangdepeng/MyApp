package com.dapeng.online

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dapeng.image_lib.GlideLoadUtils
import com.dapeng.image_lib.XUtilLoadImageUtils
import com.dapeng.map_lib.MapTestActivity
import com.dapeng.online.banner.BannerTestActivity
import com.dapeng.online.contract.ContractMainActivity
import com.dapeng.online.scan.ScanActivity

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var i: Float = 0.1f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        text4.setOnClickListener {
            startActivity(Intent(this, MapTestActivity::class.java))
        }

        text3.setOnClickListener {
            startActivity(Intent(this, GroupListTestActivity::class.java))
        }

        text2.setOnClickListener {
            startActivity(Intent(this, BannerTestActivity::class.java))
        }

        text.setOnClickListener {
//         test crashInfo upload     CrashReport.testJavaCrash()
//            var s:String?=null
//            val length = s!!.length
//            CrashReport.testJavaCrash()
            startActivity(Intent(this, ScanActivity::class.java))

            //         startActivity(Intent(this, ContractMainActivity::class.java))
        }

        val url = "http://img5.mtime.cn/mg/2019/06/29/002009.16684021_120X90X4.jpg"
        //    XUtilLoadImageUtils.loadCircleImage(url, image)
        // GlideLoadUtils.loadImage(this,url,image3)

        //     GlideLoadUtils.loadCircleImage(this, url, image2)

        animationView.setAnimation("loading2.json")

//        animationView.playAnimation()
//        animationView.pauseAnimation()
    }
}