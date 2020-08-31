package com.dapeng.online.contract;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dapeng.online.R;

import java.util.ArrayList;

public class ContractPersonAdapter extends BaseAdapter {

    private ArrayList<ContractPersonBean> list;
    private Context context;

    public ContractPersonAdapter(Context context, ArrayList<ContractPersonBean> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ContractPersonBean item = (ContractPersonBean) getItem(position);

        ViewHolder viewHolder = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_contract, null,false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = ((ViewHolder) convertView.getTag());
        }

        viewHolder.contractName.setText(item.getContractName());
        viewHolder.contractPhone.setText(item.getContractPhone());
        viewHolder.simId.setText(String.format("sim %s", item.getSimId()));

        return convertView;
    }

    public static class ViewHolder {
        public View rootView;
        public TextView contractName;
        public TextView contractPhone;
        public TextView simId;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.contractName = (TextView) rootView.findViewById(R.id.contract_name);
            this.contractPhone = (TextView) rootView.findViewById(R.id.contract_phone);
            this.simId = (TextView) rootView.findViewById(R.id.sim_id);
        }

    }
}
