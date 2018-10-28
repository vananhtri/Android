package com.example.vananh.doan.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.vananh.doan.Model.CauHoi;
import com.example.vananh.doan.Model.DiemThi;
import com.example.vananh.doan.R;

import java.util.List;

public class DiemThiAdapter extends ArrayAdapter<DiemThi> {
    public DiemThiAdapter(@NonNull Context context, @NonNull List<DiemThi> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.layout_diem_thi, parent, false);
        }
        TextView tvNgayThi = convertView.findViewById(R.id.tvNgayThi);
        TextView tvDiemThi = convertView.findViewById(R.id.tvDiemThi);
        TextView tvKet = convertView.findViewById(R.id.tvKetQua);
        DiemThi diemThi = getItem(position);
        if(diemThi !=null){
            tvNgayThi.setText(diemThi.getNgayThi());
            tvDiemThi.setText(diemThi.getDiemThi());
            tvKet.setText(diemThi.getKetQua());
        }
        return convertView;
    }
}
