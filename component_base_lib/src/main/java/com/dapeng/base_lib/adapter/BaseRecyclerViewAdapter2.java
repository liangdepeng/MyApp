package com.dapeng.base_lib.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dapeng.base_lib.interfaces.IMulTiType;

import java.util.List;

/**
 * Created by ldp.
 * <p>
 * Date: 2020-12-18
 * <p>
 * Summary: recyclerView 适配器 ，同时 支持多布局
 */
public abstract class BaseRecyclerViewAdapter2<T, V extends BaseRecyclerViewHolder> extends RecyclerView.Adapter<V> {

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

    private SparseArray<View> headViews;
    private SparseArray<View> footViews;

    /**
     * 单类型条目布局 初始化方法
     */
    public BaseRecyclerViewAdapter2(Context context, List<T> list) {
        this(context, list, null);
    }

    /**
     * 多类型条目布局 初始化方法
     */
    public BaseRecyclerViewAdapter2(Context context, List<T> list, IMulTiType<T> iMulTiType) {
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

    /**
     * 创建 布局
     */
    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public V onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (iMulTiType != null) {
            if (viewType == 0) {
                return ((V) new BaseRecyclerViewHolder(getErrorView()));
            }
            View view = LayoutInflater.from(context).inflate(viewType, parent, false);
            return (V) new BaseRecyclerViewHolder(view);
        }
        if (getItemLayResId() == 0) {
            return ((V) new BaseRecyclerViewHolder(getErrorView()));
        }
        View view = LayoutInflater.from(context).inflate(getItemLayResId(), parent, false);
        return ((V) new BaseRecyclerViewHolder(view));
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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick(holder, getItem(position), position);
            }
        });
        onBindItemData(holder, list.get(position), position);
    }

    /**
     * item 点击事件
     */
    protected void onItemClick(V holder, T item, int position) {

    }

    @Override
    public int getItemCount() {
        return list != null ? list.size()  : 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (iMulTiType != null) {
            return iMulTiType.getLayoutType(getItem(position), position);
        }
        return super.getItemViewType(position);
    }

    /**
     * 单条目布局 必须复写
     */
    protected int getItemLayResId() {
        return 0;
    }

    /**
     * 数据绑定
     */
    protected abstract void onBindItemData(V holder, T itemData, int position);

}
