package com.example.vananh.doan.ChucNang;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.vananh.doan.Adapter.CauHoiAdapter;
import com.example.vananh.doan.CauHoiDetailActivity;
import com.example.vananh.doan.Constant.Constant;
import com.example.vananh.doan.Model.CauHoi;
import com.example.vananh.doan.R;
import com.example.vananh.doan.Service.CauHoiService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FragCauHoi extends android.support.v4.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    ListView lvCauHoi;
    CauHoiAdapter cauHoiAdapter;
    List<CauHoi> listCauHoi;
    CauHoiService cauHoiService;
    Button btnThem;

    public FragCauHoi() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Danh sách câu hỏi");

    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Danh sách câu hỏi");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_frag_cau_hoi, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lvCauHoi = getView().findViewById(R.id.lvCauHoi);
        btnThem = getView().findViewById(R.id.btnThem);
        listCauHoi = new ArrayList<>();
        cauHoiService = new CauHoiService();
        readData();


        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CauHoiDetailActivity.class);
                startActivityForResult(intent, 100);
            }
        });

        lvCauHoi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CauHoi c = listCauHoi.get(position);
                Intent i = new Intent(getContext(), CauHoiDetailActivity.class);
                i.putExtra("cauHoi", c);
                startActivityForResult(i,100); //
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == 101) {
//            String loaiCauHoi = data.getStringExtra("TenLoaiCauHoi");
//            String noiDung = data.getStringExtra("NoiDungCauHoi");
//            CauHoi c =new CauHoi(loaiCauHoi, noiDung);
//            listCauHoi.add(0, c);
//            cauHoiAdapter.notifyDataSetChanged();
            int status = data.getIntExtra("Status", 0);
            if (status == 1) // succes
            {
                readData(); // load lai listview
            }
        }
    }

    private void readData() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = Constant.BASE_URL + "api/cauhois";
        final String[] res = new String[1];
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        listCauHoi = convertToListCauHoi(response);
                        //set adapter;
                        cauHoiAdapter = new CauHoiAdapter(getContext(), listCauHoi);
                        lvCauHoi.setAdapter(cauHoiAdapter);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", error.getMessage());
            }
        });

        queue.add(stringRequest);
    }

    private ArrayList<CauHoi> convertToListCauHoi(String response) {
        ArrayList<CauHoi> listCauHoi = new ArrayList<CauHoi>();
        try {
            JSONArray cauhoiArray = new JSONArray(response);
            for (int i = 0; i < cauhoiArray.length(); ++i) {
                JSONObject obj = cauhoiArray.getJSONObject(i);
                int maCauHoi = obj.getInt("MaCauHoi");
                int maLoai = obj.getInt("MaLoaiCauHoi");
                String loai = obj.getString("TenLoaiCauHoi");
                String noiDung = obj.getString("NoiDungCauHoi");
                String daA = obj.getString("DapAnA");
                String daB = obj.getString("DapAnB");
                String daC = obj.getString("DapAnC");
                String daD = obj.getString("DapAnD");
                String cauTraLoi = obj.getString("DapAnDung");
                CauHoi cauHoi = new CauHoi(maCauHoi, maLoai, loai, noiDung, daA, daB, daC, daD, cauTraLoi);
                listCauHoi.add(cauHoi);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listCauHoi;
    }
}
