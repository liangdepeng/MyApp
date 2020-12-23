package com.dapeng.base_lib.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.dapeng.base_lib.interfaces.IMulTiType;

import java.util.List;

/**
 * RecyclerView Adapter 基本的封装 添加头尾可以再优化，这边只是图简单举个例子，没有复杂需求可以直接用 原理一样的 可根据需要自由修改
 * <p>
 * Created by dp.
 * <p>
 * Date: 2020-12-18
 * <p>
 * @param <T> item 数据类型 model 数据Bean
 * @param <V> ViewHolder 如果无需自定义 可直接传入{@link BaseRecyclerViewHolder}
 * <p>
 * Summary: recyclerView 通用 适配器，
 *          同时  1、支持多布局
 *                2、支持添加多个头尾布局
 * <p>
 * Use: 1、一般的列表 初始化{@link #BaseRecyclerViewAdapter(Context, List )} 复写 {@link #getItemLayResId()} 即可
 *      2、多种item类型 初始化{@link #BaseRecyclerViewAdapter(Context, List,IMulTiType)} 外部实现IMulTiType 接口传入布局类型 即可
 *      3、添加头尾布局 {@link #addHeadView(View, Object)} {@link #addFootView(View, Object)} object为设置一个tag 区分添加的view
 */
public abstract class BaseRecyclerViewAdapter<T, V extends BaseRecyclerViewHolder> extends RecyclerView.Adapter<V> {

    /**
     * 全局维持一个数据 list
     */
    protected final List<T> list;
    /**
     * 上下文 Context
     */
    protected final Context context;
    /**
     * 多布局类型实现接口 返回对应的布局类型
     */
    private final IMulTiType<T> iMulTiType;
    /**
     * headViews缓存
     */
    private SparseArray<View> headViews;
    /**
     * footViews缓存
     */
    private SparseArray<View> footViews;
    /**
     * 头布局类型
     */
    public static final int TYPE_HEADER = -1;
    /**
     * 尾布局类型
     */
    public static final int TYPE_FOOTER = -2;

    /**
     * SparseArray tag 按照添加顺序 0 ↑
     */
    private int headViewTag = 0;
    private int footViewTag = 0;
    private int headIndex = 0;
    private int footIndex = 0;

    /**
     * 单类型条目布局 初始化方法
     */
    public BaseRecyclerViewAdapter(Context context, List<T> list) {
        this(context, list, null);
    }

    /**
     * 多类型条目布局 初始化方法
     */
    public BaseRecyclerViewAdapter(Context context, List<T> list, IMulTiType<T> iMulTiType) {
        this.list = list;
        this.context = context;
        this.iMulTiType = iMulTiType;
    }

    /**
     * item 数据
     */
    public T getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    /**
     * 创建 布局
     */
    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public V onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //------------------------- 头尾布局（如果有）----------------------
        if (getHeadCount() > 0 && viewType == TYPE_HEADER) {
            return (V) new BaseRecyclerViewHolder(getHeadView(headIndex++));
        } else if (getFootCount() > 0 && viewType == TYPE_FOOTER) {
            return (V) new BaseRecyclerViewHolder(getFootView(footIndex++));
        }
        //------------------------------------------------------------------

        // ------------------------- 多布局（如果设置了）--------------------
        if (iMulTiType != null) {
            if (viewType == 0) {
                return ((V) new BaseRecyclerViewHolder(getErrorView()));
            }
            View view = LayoutInflater.from(context).inflate(viewType, parent, false);
            return (V) new BaseRecyclerViewHolder(view);
        }
        //------------------------------------------------------------------


        //------------------------------一般的列表 item----------------------
        if (getItemLayResId() == 0) {
            return ((V) new BaseRecyclerViewHolder(getErrorView()));
        }
        View view = LayoutInflater.from(context).inflate(getItemLayResId(), parent, false);
        return ((V) new BaseRecyclerViewHolder(view));
        //-------------------------------------------------------------------
    }

    /**
     * 容错 防止view找不到 崩溃
     */
    private View getErrorView() {
        return new View(context);
    }

    /**
     * 数据绑定
     */
    @Override
    public void onBindViewHolder(@NonNull final V holder, final int position) {
        if (holder.getItemViewType() == TYPE_HEADER) {
            onBindHeadViewsItemData(holder, position, holder.itemView.getTag());
            return;
        } else if (holder.getItemViewType() == TYPE_FOOTER) {
            onBindFootViewsItemData(holder, position, holder.itemView.getTag());
            return;
        }
        final int dataRealPosition = position - getHeadCount();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNormalItemClick(holder, getItem(dataRealPosition), dataRealPosition);
            }
        });
        onBindNormalItemData(holder, getItem(dataRealPosition), position);
    }

    /**
     * item 点击事件
     */
    protected void onNormalItemClick(V holder, T item, int position) {

    }

    @Override
    public int getItemCount() {
        return getNormalItemCount() + getHeadCount() + getFootCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (position < getHeadCount())
            return TYPE_HEADER;
        int realPosition = position - getHeadCount();
        if (realPosition < list.size()) {
            if (iMulTiType != null) {
                return iMulTiType.getLayoutType(getItem(realPosition), realPosition);
            }
            return super.getItemViewType(position);
        }
        return TYPE_FOOTER;
    }

    /**
     * 单条目布局 必须复写
     */
    protected int getItemLayResId() {
        return 0;
    }

    /**
     * 除了头部 尾部布局 其他item 数据绑定
     */
    protected abstract void onBindNormalItemData(V holder, T itemData, int position);

    /**
     * 头部数据绑定  按需要复写
     */
    protected void onBindHeadViewsItemData(V holder, int position, Object tag) {
    }

    /**
     * 尾部数据绑定  按需要复写
     */
    protected void onBindFootViewsItemData(V holder, int position, Object tag) {
    }


    /**
     * 添加头布局 可添加 多个 以 tag做标记 区分
     */
    public void addHeadView(@NonNull View view, Object viewTag) {
        if (view == null)
            return;
        if (headViews == null) {
            headViews = new SparseArray<>();
        }
        view.setTag(viewTag);
        headViews.put(headViewTag++, view);
        notifyItemInserted(getHeadCount() - 1);
    }


    /**
     * 添加尾布局 可添加多个 以 tag 做标记 区分
     */
    public void addFootView(@NonNull View view, Object viewTag) {
        if (view == null)
            return;
        if (footViews == null) {
            footViews = new SparseArray<>();
        }
        view.setTag(viewTag);
        footViews.put(footViewTag++, view);
        notifyItemInserted(getItemCount());
    }

    @Nullable
    private View getHeadView(int headViewTag) {
        if (headViews == null) {
            return null;
        }
        return headViews.get(headViewTag, getErrorView());
    }

    @Nullable
    private View getFootView(int footViewTag) {
        if (footViews == null) {
            return null;
        }
        return footViews.get(footViewTag, getErrorView());
    }

    public int getHeadCount() {
        return headViews != null ? headViews.size() : 0;
    }

    public int getFootCount() {
        return footViews != null ? footViews.size() : 0;
    }

    public int getNormalItemCount() {
        return list != null ? list.size() : 0;
    }

}
