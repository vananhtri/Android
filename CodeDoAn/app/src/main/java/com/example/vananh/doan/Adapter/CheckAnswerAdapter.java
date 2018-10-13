package com.example.vananh.doan.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.vananh.doan.Model.CauHoi;
import com.example.vananh.doan.R;

import java.util.ArrayList;
import java.util.List;


public class CheckAnswerAdapter extends BaseAdapter {


    ArrayList lsData = new ArrayList();
    LayoutInflater inflater;

    public CheckAnswerAdapter(ArrayList lvData, Context context) {
        lsData = lvData;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return lsData.size();
    }

    @Override
    public Object getItem(int position) {
        return lsData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CauHoi data = (CauHoi) getItem(position);
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_grid_check_answer, null);
            holder.tvNumAns = convertView.findViewById(R.id.textCheckCauHoi);
            holder.tvYourAns = convertView.findViewById(R.id.textCauTraLoi);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        int pos = position + 1;
        holder.tvNumAns.setText("Câu " + pos + ": ");
        List<String> listCauTL = data.getListTraLoi();

        // get answer
        String ans = "";
        for (int i = 0; i < listCauTL.size(); i++) {
            if (i == listCauTL.size() - 1) {
                ans += listCauTL.get(i);
            } else {
                ans += "," + listCauTL.get(i);
            }
        }
        holder.tvYourAns.setText(ans);
        return convertView;
    }

    private static class ViewHolder {
        TextView tvNumAns, tvYourAns;
    }
}
