package com.dapeng.online.contract;

import android.content.Context;
import android.view.View;

import com.dapeng.base_lib.adapter.BaseListAdapter;
import com.dapeng.base_lib.adapter.ViewHolder;
import com.dapeng.base_lib.toast.ToastUtils;
import com.dapeng.online.R;

import java.util.List;

public class ContractAdapter extends BaseListAdapter<ContractPersonBean> {

    public ContractAdapter(Context context, List<ContractPersonBean> list, int layoutResId) {
        super(context, list, layoutResId);
    }

    @Override
    protected void onBindItemData(int position, ViewHolder viewHolder, ContractPersonBean itemData) {
        viewHolder.setText(R.id.contract_name, itemData.getContractName())
                .setText(R.id.contract_phone, itemData.getContractPhone())
                .setText(R.id.sim_id, String.format("sim %s", itemData.getSimId()))
                .setClickListener(R.id.item_view, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // do Nothing
                        ToastUtils.show("clicked");
                    }
                });
    }
}
