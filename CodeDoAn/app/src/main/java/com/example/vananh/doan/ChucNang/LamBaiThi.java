package com.example.vananh.doan.ChucNang;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.RippleDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.vananh.doan.Adapter.BoDeAdapter;
import com.example.vananh.doan.Adapter.CauHoiAdapter;
import com.example.vananh.doan.Constant.Constant;
import com.example.vananh.doan.Database.SQLBoDe;
import com.example.vananh.doan.Database.SqlDataHelper;
import com.example.vananh.doan.Model.BoDe;
import com.example.vananh.doan.Model.CauHoi;
import com.example.vananh.doan.R;
import com.example.vananh.doan.Slide.ScreenSlidePagerActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    Button btnDeNgauNhien;
    public LamBaiThi() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Làm bài thi");
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Làm bài thi");
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
        btnDeNgauNhien = getView().findViewById(R.id.btnDeNgauNhien);
        SqlDataHelper sqlDataHelper = new SqlDataHelper(getContext());
        //try connection
        try {
            sqlDataHelper.deleteDatabase();
            sqlDataHelper.isCreatedDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        sqlBoDe = new SQLBoDe(getContext());
        listBoDe = new ArrayList<>();

        createListBoDe();

        gridBoDe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent   = new Intent(getActivity(), ScreenSlidePagerActivity.class);
                intent.putExtra("MaBoDe", listBoDe.get(position).getId() );
                startActivity(intent);
            }
        });


        btnDeNgauNhien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent   = new Intent(getActivity(), ScreenSlidePagerActivity.class);
                intent.putExtra("MaBoDe", 0);
                startActivity(intent);
            }
        });
    }

    private void createListBoDe() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = Constant.BASE_URL + "api/BoDe/ThiThu/GetAll";
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        listBoDe = convertToListCauHoi(response);
                        boDeAdapter = new BoDeAdapter(getContext(),listBoDe);
                        gridBoDe.setAdapter(boDeAdapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", error.getMessage());
            }
        });

        queue.add(stringRequest);
    }

    private ArrayList<BoDe> convertToListCauHoi(String response) {
        ArrayList<BoDe> listBoDe = new ArrayList<>();
        try {
            JSONArray boDeArray = new JSONArray(response);
            for (int i = 0; i < boDeArray.length(); ++i) {
                JSONObject obj = boDeArray.getJSONObject(i);
                int ma = obj.getInt("MaBoDe");
                String tenBoDe = obj.getString("TenBoDe");
                BoDe boDe = new BoDe(ma,tenBoDe);
                listBoDe.add(boDe);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listBoDe;
    }
}
