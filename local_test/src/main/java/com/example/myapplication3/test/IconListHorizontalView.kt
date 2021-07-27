package com.example.myapplication3.test

import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.util.Log
import android.util.SparseArray
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.IdRes
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication3.R
import java.util.*

/**
 * Created by ldp.
 *
 * Date: 2021/7/23
 *
 * Summary: 由左到右，由上到下 网格布局 横向滑动列表 分页显示 可像viewpager一样 每次滑动一页
 */
class IconListHorizontalView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr) {

    /**
     * 单个 item 列表
     */
    private var iconRv: RecyclerView? = null

    /**
     * 指示器
     */
    private var indicatorRv: RecyclerView? = null

    /**
     * 宽度
     */
    private var measuredWidth: Int? = 0

    /**
     * 配置
     */
    private var config: Config = Config()

    /**
     * 单个item view 数据绑定
     */
    private var viewBindCallback: IViewBindCallback? = null

    /**
     * 指示器
     */
    private val indicators = arrayListOf<Int>()

    /**
     * RecyclerView 当前展示的itemPostion 用于显示指示器的位置
     */
    private var ckPosition = 0


    /**
     * 设置/更新 数据
     * @param config 配置参数 不能为空
     */
    fun setDataConfig(config: Config, callback: IViewBindCallback?, list: List<Any>?) {
        this.config = config
        this.viewBindCallback = callback

        // 做一个简单的分页效果
        val pages: Int
        val arrayList: MutableList<ArrayList<Any>> = arrayListOf()
        indicators.clear()
        if (list != null && list.isNotEmpty()) {
            val count = list.size / (config.spanCount * config.maxLines)
            val yu = list.size % (config.spanCount * config.maxLines)
            pages = if (yu == 0) count else count + 1
            for (i in 0 until pages) {
                arrayList.add(arrayListOf())
                indicators.add(if (i == ckPosition) 1 else 0)
            }
            var index = 0
            var data = arrayList[index]
            for (i in list.indices) {
                if (i > 0 && i % (config.spanCount * config.maxLines) == 0) {
                    data = arrayList[++index]
                }
                data.add(list[i])
            }
        }

        // post 保证能获取到view宽高 ，因为可能网络请求进来页面就会请求返回数据 但是view还没有测量布局完成
        post {
            if (iconRv?.adapter == null) {
                iconRv?.adapter = iconAdapter
            }
            iconAdapter.setList(arrayList)

            if (config.isOpenPageScrolled && iconRv?.onFlingListener == null) {
                PagerSnapHelper().attachToRecyclerView(iconRv)
            }

            if (indicatorRv?.adapter == null) {
                indicatorRv?.adapter = indicatorAdapter
            }
            indicatorAdapter.setIndicatorList(indicators)
            indicatorRv?.visibility = if (indicators.size > 1) VISIBLE else GONE
        }
    }


    private fun initView(context: Context) {
        iconRv = findViewById(R.id.icon_list_rv)
        iconRv?.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        iconRv?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                Log.e("IconListView_state", newState.toString())

                if (!config.isOpenPageScrolled)
                    return

                // 滑动停止 更新指示器的位置
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager?
                    if (layoutManager != null) {
                        // 查找第一个完全可见的item的position 因为单个item是撑满布局的 所以即为 所展示的这一个
                        val position = layoutManager.findFirstCompletelyVisibleItemPosition()
                        //  WHHLog.e("IconListView_pos", ckPosition.toString())

                        if (position != RecyclerView.NO_POSITION && position != ckPosition) {
                            // 上一个选中的重置
                            indicators[ckPosition] = 0
                            // 赋值给当前position
                            ckPosition = position
                            // 设置选中
                            indicators[position] = 1
                            // 刷新
                            indicatorAdapter.setIndicatorList(indicators)
                        }
                    }
                }
            }
        })
        //  iconRv.setAdapter();
        indicatorRv = findViewById(R.id.indicator_rv)
        indicatorRv?.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        // indicatorRv.setAdapter();
    }

   private fun dp2px(dipValue: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, Resources.getSystem().displayMetrics).toInt()
    }

    private val iconAdapter by lazy { IconAdapter() }

    private val indicatorAdapter by lazy {
        object : RecyclerView.Adapter<IconCommonViewHolder>() {

            private val indicatorList = arrayListOf<Int>()

            fun setIndicatorList(indicators: List<Int>?) {
                indicatorList.clear()
                indicatorList.addAll(indicators ?: emptyList())
                notifyDataSetChanged()
            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IconCommonViewHolder {
                // 设置指示器 动态生成View
                val view = View(context)
                val layoutParams = RecyclerView.LayoutParams(dp2px(5f), dp2px(5f))
                layoutParams.rightMargin = dp2px(4f)
                view.layoutParams = layoutParams
                view.setBackgroundResource(R.drawable.shape_unselect_page_indicator_style)
                return IconCommonViewHolder(view)
            }

            override fun onBindViewHolder(holder: IconCommonViewHolder, position: Int) {
                // 设置背景 设置选中和不选中

                holder.itemView.setBackgroundResource(
                        if (indicatorList[position] == 1)
                            config.selectDrawableId
                        else
                            config.unSelectDrawableId
                )
            }

            override fun getItemCount(): Int {
                return indicatorList.size
            }
        }
    }

    class Config {
        // 单个 item 布局id
        var itemLayoutResId = R.layout.item_child_icon__layout

        // x列
        var spanCount = 5

        // 最多几行
        var maxLines = 2

        // 是否像viewpager一样 一次滚动一屏
        var isOpenPageScrolled = true

        // todo 可优化 可以自定义drawable 用来设置背景可以省去写drawable文件
        // 选中展示的样式
        var selectDrawableId = R.drawable.shape_select_page_indicator_style
        // 未选中展示的样式
        var unSelectDrawableId = R.drawable.shape_unselect_page_indicator_style
    }

    interface IViewBindCallback {
        fun bindItemView(holder: IconItemViewHolder, item: Any?)
    }

    private inner class IconAdapter : RecyclerView.Adapter<IconCommonViewHolder>() {

        private var list: List<ArrayList<Any>>? = null

        fun setList(list: List<ArrayList<Any>>?) {
            this.list = list
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IconCommonViewHolder {
            val inflate = LayoutInflater.from(context).inflate(R.layout.ui_item_icon_item_list, parent, false)
            return IconCommonViewHolder(inflate)
        }

        override fun onBindViewHolder(holder: IconCommonViewHolder, position: Int) {

            val arrayList = list?.get(position)
            val recyclerView = holder.getViewOrNull<RecyclerView>(R.id.recyclerView)
            if (recyclerView?.adapter == null) {
                recyclerView?.layoutManager = GridLayoutManager(context, config.spanCount)
                val itemAdapter = IconItemAdapter()
                recyclerView?.adapter = itemAdapter
                itemAdapter.setList(arrayList)
            } else {
                (recyclerView.adapter as IconItemAdapter).setList(arrayList)
            }
        }

        override fun getItemCount(): Int {
            return if (list != null) list?.size ?: 0 else 0
        }
    }

    internal inner class IconItemAdapter : RecyclerView.Adapter<IconItemViewHolder>() {
        private var list: ArrayList<Any>? = null
        fun setList(list: ArrayList<Any>?) {
            this.list = list
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IconItemViewHolder {
            val inflate = LayoutInflater.from(context).inflate(config.itemLayoutResId, parent, false)
            return IconItemViewHolder(inflate)
        }

        override fun onBindViewHolder(holder: IconItemViewHolder, position: Int) {
            viewBindCallback?.bindItemView(holder, list?.get(position))
        }

        override fun getItemCount(): Int {
            return if (list != null) list?.size ?: 0 else 0
        }
    }

    open class IconCommonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val views: SparseArray<View?> = SparseArray(10)

        @Suppress("UNCHECKED_CAST")
        fun <T : View> getViewOrNull(@IdRes viewId: Int): T? {
            val view = views.get(viewId)
            if (view == null) {
                itemView.findViewById<T>(viewId)?.let {
                    views.put(viewId, it)
                    return it
                }
            }
            return view as? T
        }

        fun <T : View> getView(@IdRes viewId: Int): T {
            val view = getViewOrNull<T>(viewId)
            checkNotNull(view) { "No view found with id $viewId" }
            return view
        }

    }

    inner class IconItemViewHolder(itemView: View) : IconCommonViewHolder(itemView) {
        init {
            //  val layoutParams = itemView.layoutParams
            //  layoutParams.width = measuredWidth ?: 0 / config.spanCount
            //layoutParams.width = dp2px(70f)
            //  itemView.layoutParams = layoutParams
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        measuredWidth = getMeasuredWidth()
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.ui_icon_list_view, this, true)
        initView(context)
    }
}