package com.example.vananh.doan.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.vananh.doan.Model.BoDe;
import com.example.vananh.doan.Model.CauHoi;
import com.example.vananh.doan.R;

import java.util.List;

public class CauHoiAdapter extends ArrayAdapter<CauHoi> {
    public CauHoiAdapter(@NonNull Context context, List<CauHoi> listCauHoi) {
        super(context, 0, listCauHoi);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.layout_cauhoi, parent, false);
        }
        TextView tvLoaiCauHoi = convertView.findViewById(R.id.tvLoaiCH);
        TextView tvNoiDungCH = convertView.findViewById(R.id.tvNDCauHoi);
        CauHoi ch = getItem(position);
        if(ch !=null){
            tvLoaiCauHoi.setText(ch.getTenLoaiCauHoi());
            tvNoiDungCH.setText(ch.getNoiDung());
        }

//        BoDe boDe = getItem(position);
//        if (boDe != null) {
//            textView.setText(boDe.getTenBoDe());
//        }
        return convertView;
    }
}
