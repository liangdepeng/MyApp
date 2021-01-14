package com.dapeng.online.contract

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.telephony.SubscriptionManager
import android.util.Log
import androidx.core.app.ActivityCompat
import com.dapeng.base_lib.base.BaseActivity
import com.dapeng.base_lib.interfaces.PermissionCallback
import com.dapeng.base_lib.toast.ToastUtil
import com.dapeng.online.R
import com.dapeng.utils_lib.log.DPLogUtils
import kotlinx.android.synthetic.main.activity_contract_main.*


class ContractMainActivity : BaseActivity() {

    companion object {
        const val EDIT_SIM = 3456
        const val EDIT_SIM_SUCCESS = 4567
    }

    private val TAG = ContractMainActivity::class.java.simpleName
    private val dataList = arrayListOf<ContractPersonBean>()
    private val adapter by lazy {
        // ContractPersonAdapter(this, dataList)
        ContractAdapter(this, dataList, R.layout.item_contract)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contract_main)
        initView()
        requestPermission()
    }

    private fun initView() {
        showBackIvTitleAndFunction(true, "联系人")

        list_view.adapter = adapter

        floating_btn.setOnClickListener {
            val intent = Intent(this, EditActivity::class.java)
            intent.putStringArrayListExtra("contract", subIds)
            startActivityForResult(intent, EDIT_SIM)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == EDIT_SIM && resultCode == EDIT_SIM_SUCCESS) {
            requestPermission()
        }
    }

    private var uri: Uri? = null

    private fun requestPermission() {

        requestNeedPermissions("联系人读写权限和电话状态权限申请",
            object : PermissionCallback {
                override fun hasPermission() {
                    queryDoubleCards()
                }

                override fun noPermission() {
                    ToastUtil.show("权限被拒绝，可能会导致功能异常")
                }
            },
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS,
            Manifest.permission.READ_PHONE_STATE)

        //        val rxPermissions = RxPermissions(this)
        //        rxPermissions.request(
        //            Manifest.permission.READ_PHONE_STATE,
        //            Manifest.permission.WRITE_CONTACTS,
        //            Manifest.permission.READ_CONTACTS
        //        ).subscribe(object : DefaultRx3Subscribe<Boolean>() {
        //            override fun onNext(t: Boolean) {
        //                super.onNext(t)
        //                if (t)
        //                    queryDoubleCards()
        //                else
        //                    ToastUtils.show("权限被拒绝，可能会导致功能异常")
        //            }
        //
        //            override fun onError(e: Throwable?) {
        //                super.onError(e)
        //
        //            }
        //        })
    }

    private val subIds = arrayListOf<String>()


    private fun queryDoubleCards() {
        showLoadingDialog()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            val subscriptionManager = SubscriptionManager.from(this)
            val mSubcriptionInfos = if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                //  Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            } else subscriptionManager.activeSubscriptionInfoList

            val slotIds: MutableList<String> = ArrayList()
            subIds.clear()
            if (mSubcriptionInfos != null) {
                for (i in mSubcriptionInfos.indices) {
                    val info = mSubcriptionInfos[i]
                    if (info != null) {
                        subIds.add(info.subscriptionId.toString() + "")
                        slotIds.add((info.simSlotIndex + 1).toString() + "")
                    }
                    Log.e(TAG, "info : $info")
                }
            }
            for (i in subIds.indices) {
                getSimQuery("content://icc/adn/subId/" + subIds[i], slotIds[i], i + 1) //这里就是获取双卡的联系人详情void
            }

            DPLogUtils.errorLevel(TAG, adapter.count.toString())

            dismissLoadingDialog()

            adapter.notifyDataSetChanged()

        }


    }

    private fun getSimQuery(mUri: String, soltID: String, i: Int) {
        dataList.clear()
        //SIM的provider是IccProvider，IccProvider的Uri是content://icc/adn
        Log.e(TAG, "mUri : $mUri")
        var cursor: Cursor? = null
        try {
            val uri = Uri.parse(mUri)
            cursor = contentResolver.query(uri, null, null, null, null)
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    val contactName = cursor.getString(cursor.getColumnIndex("name")) //获取双卡中联系人的名字
                    val telNumber =
                        cursor.getString(cursor.getColumnIndex("number")) //获取双卡中联系人的电话号码
                    val emails = cursor.getString(cursor.getColumnIndex("emails")) //获取双卡中联系人的电话号码
                    val contactSimID = soltID //双卡中该联系人为与卡1还是卡2

                    val contractPersonBean = ContractPersonBean()
                    contractPersonBean.contractName = contactName
                    contractPersonBean.contractPhone = telNumber
                    contractPersonBean.email = emails
                    contractPersonBean.simId = i.toString()

                    dataList.add(contractPersonBean)

                    val keys = cursor.columnNames
                    Log.e(TAG, "开始 ===========================================")
                    for (key in keys) {
                        Log.e(TAG,
                            "key : $key , value : " + cursor.getString(cursor.getColumnIndex(key)))
                    }
                    Log.e(TAG, "结束 ===========================================")
                }
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        } finally {
            cursor?.close()
        }
    }
}


//fun querySIMContact() {
//    try {
//        val cursor: Cursor? = uri?.let { contentResolver.query(it, null, null, null, null) }
//        Log.e("FragmentActivity.TAG", "cursor count=" + cursor?.count)
//        cursor?.let {
//            while (cursor.moveToNext()) {
//                val columnNames: Array<String> = cursor.getColumnNames()
//                for (i in columnNames.indices) {
//                    val name: String = cursor.getString(0)
//                    val number: String = cursor.getString(1)
//                    val emails: String = cursor.getString(2)
//                    val id: String = cursor.getString(3)
//                    Log.e(
//                        "FragmentActivity.TAG",
//                        "simcardinfo=name=$name  number=$number  emails=$emails  id=$id"
//                    )
//                }
//            }
//            cursor.close()
//        }
//    } catch (e: Exception) {
//
//    }
//
//}