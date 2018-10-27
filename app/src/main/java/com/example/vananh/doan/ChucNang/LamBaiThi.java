package com.example.vananh.doan.ChucNang;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.RippleDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.example.vananh.doan.Adapter.BoDeAdapter;
import com.example.vananh.doan.Database.SQLBoDe;
import com.example.vananh.doan.Database.SqlDataHelper;
import com.example.vananh.doan.Model.BoDe;
import com.example.vananh.doan.R;
import com.example.vananh.doan.Slide.ScreenSlidePagerActivity;

import java.io.IOException;
import java.sql.SQLData;
import java.util.ArrayList;

/**
     * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class LamBaiThi extends android.support.v4.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private ArrayList<BoDe> listBoDe;
    SQLBoDe sqlBoDe ;
    GridView gridBoDe;
    BoDeAdapter boDeAdapter;
    public LamBaiThi() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lam_bai_thi, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gridBoDe = getView().findViewById(R.id.gridBoDe);
        SqlDataHelper sqlDataHelper = new SqlDataHelper(getContext());
        //try connection
        try {
            sqlDataHelper.deleteDatabase();
            sqlDataHelper.isCreatedDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        sqlBoDe = new SQLBoDe(getContext());
        createListBoDe();
        boDeAdapter = new BoDeAdapter(getContext(),listBoDe);
        gridBoDe.setAdapter(boDeAdapter);
        gridBoDe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent   = new Intent(getActivity(), ScreenSlidePagerActivity.class);
                intent.putExtra("MaBoDe", listBoDe.get(position).getId() );
                startActivity(intent);
            }
        });

    }

    private void createListBoDe() {
        listBoDe = sqlBoDe.GetListBoDe();
    }
}
