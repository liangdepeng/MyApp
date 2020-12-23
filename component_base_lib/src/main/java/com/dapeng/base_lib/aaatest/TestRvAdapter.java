package com.dapeng.base_lib.aaatest;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import androidx.core.content.ContextCompat;

import com.dapeng.base_lib.R;
import com.dapeng.base_lib.adapter.BaseRecyclerViewAdapter;
import com.dapeng.base_lib.adapter.BaseRecyclerViewHolder;
import com.dapeng.base_lib.interfaces.IMulTiType;

import java.util.List;

/**
 * Created by ldp.
 * <p>
 * Date: 2020-12-21
 * <p>
 * Summary:
 * <p>
 * api path:
 */
public class TestRvAdapter extends BaseRecyclerViewAdapter<TestRvBean, BaseRecyclerViewHolder> {


    public TestRvAdapter(Context context, List<TestRvBean> list, IMulTiType<TestRvBean> iMulTiType) {
        super(context, list, iMulTiType);
    }

    @Override
    protected void onBindNormalItemData(BaseRecyclerViewHolder holder, TestRvBean itemData, int position) {
        int realPos = position - getHeadCount();
        if (holder.getItemViewType() == R.layout.item_multype_test_one) {
            holder.setText(R.id.text1, realPos + " 、a类型");
        } else if (holder.getItemViewType() == R.layout.item_multype_test_two) {
            holder.setText(R.id.text2, realPos + " 、b类型");
        } else if (holder.getItemViewType() == R.layout.item_multype_test_three) {
            holder.setText(R.id.text3, realPos + " 、c类型");
        }
    }

    @Override
    protected void onBindHeadViewsItemData(BaseRecyclerViewHolder holder, int position, Object tag) {
        if (tag instanceof String) {
            String thisTag = (String) tag;
            if ("head01".equals(thisTag)) {
                holder.setText(R.id.text1, "head01010101", ContextCompat.getColor(context, R.color.white))
                        .setViewBackground(R.id.item_layout_one_cl, new ColorDrawable(ContextCompat.getColor(context, R.color.black)));
            } else if ("head02".equals(thisTag)) {
                holder.setText(R.id.text2, "head02020202", ContextCompat.getColor(context, R.color.black))
                        .setViewBackground(R.id.item_layout_two_cl, new ColorDrawable(ContextCompat.getColor(context, R.color.white)));
            }
        }
    }

    @Override
    protected void onBindFootViewsItemData(BaseRecyclerViewHolder holder, int position, Object tag) {
        if (tag instanceof String) {
            String thisTag = (String) tag;
            if ("foot01".equals(thisTag)) {
                holder.setText(R.id.text3, "foot", Color.WHITE, 20)
                        .setViewBackground(R.id.item_layout_three_cl, new ColorDrawable(Color.BLUE));
            }
        }
    }
}
