package com.dapeng.online.scan

import android.Manifest
import android.content.Intent
import android.os.Bundle
import com.dapeng.base_lib.base.BaseActivity
import com.dapeng.base_lib.interfaces.PermissionCallback
import com.dapeng.online.R
import com.dapeng.utils_lib.log.DPLogUtils
import com.huawei.hms.hmsscankit.ScanUtil
import com.huawei.hms.ml.scan.HmsScan
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions
import kotlinx.android.synthetic.main.activity_sacn_text.*

class ScanActivity : BaseActivity() {

    private val REQUEST_CODE_SCAN = 666

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sacn_text)
        initView()
    }

    private fun initView() {
        scan_btn.setOnClickListener {
            requestNeedPermissions("权限申请", object : PermissionCallback {
                override fun hasPermission() {
                    // 扫码类型 可不写 全都支持
                    val hmsScanTypes = HmsScanAnalyzerOptions.Creator()
                        .setHmsScanTypes(HmsScan.QRCODE_SCAN_TYPE, HmsScan.DATAMATRIX_SCAN_TYPE)

                    ScanUtil.startScan(this@ScanActivity, REQUEST_CODE_SCAN, null)
                }

                override fun noPermission() {
                    // do Noting
                }
            }, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

        scan_btn2.setOnClickListener {
            requestNeedPermissions("权限申请", object : PermissionCallback {
                override fun hasPermission() {
                    startActivityForResult(Intent(this@ScanActivity,
                        CustomScanActivity::class.java), CustomScanActivity.REQUEST_CUSTOM_SCAN)
                }

                override fun noPermission() {
                    // do Noting
                }
            }, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK || data == null) {
            return
        }

        if (requestCode == REQUEST_CODE_SCAN) {
            val scanResult = data.getParcelableExtra<HmsScan>(ScanUtil.RESULT)
            result_tv.text = scanResult?.getShowResult()
            DPLogUtils.errorLevel(tag, scanResult?.getShowResult())
        } else if (requestCode == CustomScanActivity.REQUEST_CUSTOM_SCAN) {
            val result = data.getParcelableExtra<HmsScan>(CustomScanActivity.SCAN_RESULT)
            result_tv.text = result?.getShowResult()
            DPLogUtils.errorLevel(tag, result?.getShowResult())
        }
    }
}