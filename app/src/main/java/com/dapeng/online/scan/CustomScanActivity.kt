package com.dapeng.online.scan

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.view.View
import android.widget.FrameLayout
import com.dapeng.base_lib.base.BaseActivity
import com.dapeng.online.R
import com.dapeng.utils_lib.device.DensityUtil
import com.huawei.hms.hmsscankit.RemoteView
import com.huawei.hms.hmsscankit.ScanUtil
import com.huawei.hms.ml.scan.HmsScan
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions
import kotlinx.android.synthetic.main.activity_custom_scan.*
import java.io.IOException

class CustomScanActivity : BaseActivity() {

    companion object {
        const val SCAN_RESULT = "SCAN_RESULT"
        const val REQUEST_CODE_PHOTO=888
        const val REQUEST_CUSTOM_SCAN=345
    }


    private var screenWidth = 0
    private var screenHeight = 0
    private var scanFrameSize = 0

    //The width and height of scan_view_finder is both 240 dp.
    private val SCAN_FRAME_SIZE = 240
    private val rect = Rect()
    private val remoteView by lazy {
        RemoteView.Builder()
            .setContext(this)
            .setBoundingBox(rect)
            .setFormat(HmsScan.ALL_SCAN_TYPE)
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_scan)
        initData()
        initView(savedInstanceState)
    }

    private fun initData() {
        screenWidth = DensityUtil.getScreenWidth(this)
        screenHeight = DensityUtil.getScreenHeight(this)

        //1. Obtain the screen density to calculate the viewfinder's rectangle.

        //1. Obtain the screen density to calculate the viewfinder's rectangle.
        val dm = resources.displayMetrics
        val density = dm.density

        scanFrameSize = (SCAN_FRAME_SIZE * density).toInt()

        //3. Calculate the viewfinder's rectangle, which in the middle of the layout.
        //Set the scanning area. (Optional. Rect can be null. If no settings are specified, it will be located in the middle of the layout.)

        //3. Calculate the viewfinder's rectangle, which in the middle of the layout.
        //Set the scanning area. (Optional. Rect can be null. If no settings are specified, it will be located in the middle of the layout.)
        rect.left = screenWidth / 2 - scanFrameSize / 2
        rect.right = screenWidth / 2 + scanFrameSize / 2
        rect.top = screenHeight / 2 - scanFrameSize / 2
        rect.bottom = screenHeight / 2 + scanFrameSize / 2
    }

    private fun initView(savedInstanceState: Bundle?) {

        showBackIvTitleAndFunction(true, "自定义扫码界面")

        remoteView.setOnResultCallback { result -> //Check the result.
            if (result != null && result.isNotEmpty() && result[0] != null && !TextUtils.isEmpty(
                    result[0].getOriginalValue()
                )) {
                val intent = Intent()
                intent.putExtra(SCAN_RESULT, result[0])
                setResult(RESULT_OK, intent)
                finish()
            }
        }

        remoteView.onCreate(savedInstanceState)

        val params = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
        frame_ll.addView(remoteView, params)

        setPictureScanOperation()
        setLightOperation()
    }

    private fun setLightOperation() {
        light_iv.setOnClickListener {
//            if (remoteView.lightStatus) {
//                remoteView.switchLight()
//            } else {
//                remoteView.switchLight()
//            }

            remoteView.switchLight()
        }
    }

    private fun setPictureScanOperation() {

        photo_iv.setOnClickListener(View.OnClickListener {
            val pickIntent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
            startActivityForResult(pickIntent, REQUEST_CODE_PHOTO)
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_PHOTO) {
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, data?.data)
                val hmsScans = ScanUtil.decodeWithBitmap(
                    this, bitmap, HmsScanAnalyzerOptions.Creator().setPhotoMode(
                        true
                    ).create()
                )
                if (hmsScans != null && hmsScans.isNotEmpty() && hmsScans[0] != null && !TextUtils.isEmpty(
                        hmsScans[0]!!.getOriginalValue()
                    )) {
                    val intent = Intent()
                    intent.putExtra(SCAN_RESULT, hmsScans[0])
                    setResult(RESULT_OK, intent)
                    finish()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

    }

    /**
     * Call the lifecycle management method of the remoteView activity.
     */
    override fun onStart() {
        super.onStart()
        remoteView.onStart()
    }

    override fun onResume() {
        super.onResume()
        remoteView.onResume()
    }

    override fun onPause() {
        super.onPause()
        remoteView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        remoteView.onDestroy()
    }

    override fun onStop() {
        super.onStop()
        remoteView.onStop()
    }

}