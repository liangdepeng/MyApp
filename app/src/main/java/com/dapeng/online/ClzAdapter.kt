package com.dapeng.online

import android.content.Context
import android.content.Intent
import com.dapeng.base_lib.ClassBean
import com.dapeng.base_lib.adapter.BaseRecyclerViewAdapter
import com.dapeng.base_lib.adapter.BaseRecyclerViewHolder

/**
 * Created by ldp.
 *
 * Date: 2020-12-18
 *
 * Summary:类适配器
 *
 */
class ClzAdapter(context: Context?, list: MutableList<ClassBean>?) :
    BaseRecyclerViewAdapter<ClassBean, BaseRecyclerViewHolder>(context, list) {

    override fun getItemLayResId(): Int {
        return R.layout.item_class_layout
    }

    override fun onBindNormalItemData(holder: BaseRecyclerViewHolder?, t: ClassBean?, position: Int) {
        holder?.setText(R.id.title_tv, position.toString() + ".   " + t?.title)
    }

    override fun onNormalItemClick(holder: BaseRecyclerViewHolder?, item: ClassBean?, position: Int) {
        context?.startActivity(Intent(context, item?.clazz))
    }

}