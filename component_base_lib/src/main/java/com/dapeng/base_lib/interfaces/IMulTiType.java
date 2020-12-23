package com.dapeng.base_lib.interfaces;


/**
 * Created by ldp.
 * <p>
 * Date: 2020-12-18
 * <p>
 * Summary: 多类型布局实现接口
 */
public interface IMulTiType<T> {

    /**
     * @param itemData item 数据bean
     * @param position 索引，位置
     * @return 根据数据 或者索引 返回需要展示的 layout id 形如 R.layout.item_xxx
     */
    int getLayoutType(T itemData,int position);
}
