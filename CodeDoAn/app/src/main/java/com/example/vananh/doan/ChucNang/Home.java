package com.example.vananh.doan.ChucNang;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.vananh.doan.R;
import com.example.vananh.doan.menuActivity;

public class Home extends android.support.v4.app.Fragment {

    public Home() {
        // Required empty public constructor
    }
    LinearLayout lnLamBai, lnHuongDan, lnMeo;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Trang chủ");
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Trang chủ");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        lnLamBai=getActivity().findViewById(R.id.LinearLamBai);
        lnHuongDan=getActivity().findViewById(R.id.LinearHươngDan);
        lnMeo=getActivity().findViewById(R.id.LinearMeo);
        lnLamBai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LamBaiThi fragment = new LamBaiThi();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_main, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        lnHuongDan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HuongDan fragment = new HuongDan();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_main, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        lnMeo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragMeoThi fragment = new FragMeoThi();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_main, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

}
