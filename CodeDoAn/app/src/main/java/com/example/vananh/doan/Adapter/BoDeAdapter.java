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
import com.example.vananh.doan.R;

import java.util.List;

public class BoDeAdapter extends ArrayAdapter<BoDe> {

    public BoDeAdapter(@NonNull Context context, List<BoDe> listBoDe) {
        super(context, 0, listBoDe);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_grid_bo_de, parent, false);
        }
        TextView textView = convertView.findViewById(R.id.btnBoDe);
        BoDe boDe = getItem(position);
        if (boDe != null) {
            textView.setText(boDe.getTenBoDe());
        }
        return convertView;
    }
}
