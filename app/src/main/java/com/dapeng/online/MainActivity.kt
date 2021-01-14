package com.dapeng.online

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dapeng.base_lib.ClassBean
import com.dapeng.base_lib.adapter.BaseRecyclerViewAdapter
import com.dapeng.base_lib.adapter.BaseRecyclerViewHolder
import com.dapeng.base_lib.base.BaseActivity
import com.dapeng.online.launcher.ClassEnum
import com.hjq.toast.ToastUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.system.exitProcess

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showBackIvTitleAndFunction(false, "")
        val list = arrayListOf<ClassBean>()
        for (clz in ClassEnum.values()) {
            list.add(ClassBean(clz.clz, clz.title))
        }
        recyclerview?.layoutManager = LinearLayoutManager(this)
        recyclerview?.addItemDecoration(DividerItemDecoration(this, RecyclerView.HORIZONTAL))
        // recyclerview?.adapter = ClzAdapter(this, list)

        recyclerview?.adapter =
            object : BaseRecyclerViewAdapter<ClassBean, BaseRecyclerViewHolder>(this, list) {

                override fun onBindNormalItemData(holder: BaseRecyclerViewHolder?,
                    itemData: ClassBean?,
                    position: Int) {
                    holder?.setText(R.id.title_tv, position.toString() + ".   " + itemData?.title)
                }

                override fun getItemLayResId(): Int {
                    return R.layout.item_class_layout
                }

                override fun onNormalItemClick(holder: BaseRecyclerViewHolder?,
                    item: ClassBean?,
                    position: Int) {
                    this@MainActivity.startActivity(Intent(this@MainActivity, item?.clazz))
                }
            }

        animationView.setAnimation("loading2.json")

        //        web_test_tv.setOnClickListener {
        //            startActivity(Intent(this, WebTestActivity::class.java))
        //        }
        //
        //        text4.setOnClickListener {
        //            startActivity(Intent(this, MapTestActivity::class.java))
        //        }
        //
        //        text3.setOnClickListener {
        //            startActivity(Intent(this, GroupListTestActivity::class.java))
        //        }
        //
        //        text2.setOnClickListener {
        //            startActivity(Intent(this, BannerTestActivity::class.java))
        //        }
        //
        //        text.setOnClickListener {
        //            //         test crashInfo upload     CrashReport.testJavaCrash()
        //            //            var s:String?=null
        //            //            val length = s!!.length
        //            //            CrashReport.testJavaCrash()
        //            startActivity(Intent(this, ScanActivity::class.java))
        //
        //            //         startActivity(Intent(this, ContractMainActivity::class.java))
        //        }

        //  val url = "http://img5.mtime.cn/mg/2019/06/29/002009.16684021_120X90X4.jpg"
        //    XUtilLoadImageUtils.loadCircleImage(url, image)
        // GlideLoadUtils.loadImage(this,url,image3)

        //     GlideLoadUtils.loadCircleImage(this, url, image2)


        //        animationView.playAnimation()
        //        animationView.pauseAnimation()
    }


    private fun initData() {

    }

    private var firstClickMills: Long = 0

    override fun onBackPressed() {
        val secondClickMills = System.currentTimeMillis()
        if (secondClickMills - firstClickMills < 2000) {
            exitProcess(0)
        } else {
            firstClickMills = secondClickMills
            ToastUtils.show("2秒内再按一次退出")
        }
    }
}