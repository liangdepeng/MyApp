package com.dapeng.online.contract

import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dapeng.base_lib.toast.ToastUtil
import com.dapeng.online.R
import kotlinx.android.synthetic.main.activity_edit.*

public class EditActivity : AppCompatActivity(), RadioGroup.OnCheckedChangeListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        initData()

    }

    private var subIds: ArrayList<String>? = null

    private var subId: String? = null

    private fun initData() {
        subIds = intent?.getStringArrayListExtra("contract")
        if (subIds.isNullOrEmpty()) {
            contentView?.visibility = View.GONE
            empty_view?.visibility = View.VISIBLE
            return
        }
        subId = subIds?.get(0)
        initView()
    }

    private fun initView() {
        contentView?.visibility = View.VISIBLE
        empty_view?.visibility = View.GONE
        if (subIds?.size == 1) {
            radio_btn_01.isChecked = true
            radio_btn_02.isEnabled = false
        } else if (subIds?.size == 2) {
            radio_btn_01.isChecked = true
        }
        radio_group.setOnCheckedChangeListener(this)


        ok_btn?.setOnClickListener {

            val name = contract_name_et?.text?.toString()?.trim() ?: ""
            val phone = contract_phone_et?.text?.toString()?.trim() ?: ""

            val email = contract_email_et?.text?.toString()?.trim() ?: ""

            if (name.isEmpty() || phone.isEmpty()) {
                Toast.makeText(this, "姓名或手机号码不能为空", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val uri = "content://icc/adn/subId/$subId"
            insertContractPerson(uri, name, phone, email)

        }
    }

    private fun insertContractPerson(mUri: String, name: String, phone: String, email: String) {
        try {
            val contentValues = ContentValues()
            contentValues.put("tag", name)
            contentValues.put("number", phone)
            contentValues.put("emails", email)
            val insert = contentResolver.insert(Uri.parse(mUri), contentValues)
        } catch (e: Exception) {
            Toast.makeText(this, "添加失败", Toast.LENGTH_LONG).show()
            return
        }
        Toast.makeText(this, "添加成功", Toast.LENGTH_LONG).show()
        setResult(ContractMainActivity.EDIT_SIM_SUCCESS)
        finish()
    }

    //需要说明的是，删除和更新联系人上述方法如果运行不生效的话，需要在where中将所有的参数属性值都传进来应该就可以了。
    //eg 删除：where = "tag='" + name + " AND number='" + number + "AND emails="+""+"AND anrs="+""+"'";
    private fun deleteContractPerson(mUri: String, name: String, phone: String) {
        try {
            var where = "tag='$name'"
            where += " AND number='$phone'"
            contentResolver?.delete(Uri.parse(mUri), where, null)
        } catch (e: Exception) {
            e.printStackTrace()
            ToastUtil.show("删除失败")
        }
        ToastUtil.show("删除成功")
        setResult(ContractMainActivity.EDIT_SIM_SUCCESS)
        finish()
    }

    private fun updateContractPerson(mUri: String, name: String, phone: String, newName: String, newPhone: String) {
        try {
            val contentValues = ContentValues()
            contentValues.put("tag",name)
            contentValues.put("number",phone)
            contentValues.put("newTag",newName)
            contentValues.put("newNumber",newPhone)
            contentResolver.update(Uri.parse(mUri),contentValues,null,null)

        } catch (e: Exception) {
            e.printStackTrace()
            ToastUtil.show("更新失败")
        }
        ToastUtil.show("更新成功")
        setResult(ContractMainActivity.EDIT_SIM_SUCCESS)
        finish()
    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        when (checkedId) {
            R.id.radio_btn_01 -> {
                subId = subIds?.get(0)
            }
            R.id.radio_btn_02 -> {
                subId = subIds?.get(1)
            }
        }
    }
}