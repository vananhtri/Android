package com.example.vananh.doan.ChucNang;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.vananh.doan.Adapter.CauHoiAdapter;
import com.example.vananh.doan.Adapter.DiemThiAdapter;
import com.example.vananh.doan.Constant.Constant;
import com.example.vananh.doan.Model.CauHoi;
import com.example.vananh.doan.Model.DiemThi;
import com.example.vananh.doan.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class FragDiemThi extends android.support.v4.app.Fragment {


    private ListView listView ;
    private  int maNguoiDung;
    private DiemThiAdapter diemThiAdapter;
    private ArrayList<DiemThi> listDiemThi;
    public FragDiemThi() {
        // Required empty public constructor
        maNguoiDung = FlagDangNhap.maNguoiDung;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Điểm thi");
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Điểm thi");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_frag_diem_thi, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = getView().findViewById(R.id.lvDiemThi);
        listDiemThi = new ArrayList<>();

        readData();
    }

    private void readData() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = Constant.BASE_URL + "api/KetQua/get/" + maNguoiDung;
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        listDiemThi = convertToListDiemThi(response);
                        //set adapter;
                        diemThiAdapter = new DiemThiAdapter(getContext(), listDiemThi);
                        listView.setAdapter(diemThiAdapter);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", error.getMessage());
            }
        });

        queue.add(stringRequest);
    }

    private ArrayList<DiemThi> convertToListDiemThi(String response) {
        ArrayList<DiemThi> list = new ArrayList<>();
        try {
            JSONArray diemThiArray = new JSONArray(response);
            for (int i = 0; i < diemThiArray.length(); ++i) {
                JSONObject obj = diemThiArray.getJSONObject(i);
                String ngayThi = obj.getString("NgayThi");
                String diemThi = obj.getString("DiemThi");
                String ket = obj.getString("KetQua");
                DiemThi d  = new DiemThi(ngayThi, diemThi,ket);
                list.add(d);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  list;
    }
}
