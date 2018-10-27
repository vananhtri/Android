package com.example.vananh.doan.Service;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.vananh.doan.Constant.Constant;
import com.example.vananh.doan.Model.CauHoi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CauHoiService {

    ArrayList<CauHoi> arrayList;

    public ArrayList<CauHoi> getCauHoi(Context context) {
        // Instantiate the RequestQueue.
        arrayList = new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = Constant.BASE_URL + "api/cauhois";
        final String[] res = new String[1];
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        arrayList = convertToListCauHoi(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", error.getMessage());
            }
        });

        queue.add(stringRequest);
        return arrayList;
    }

    private ArrayList<CauHoi> convertToListCauHoi(String response) {
        ArrayList<CauHoi> listCauHoi = new ArrayList<CauHoi>();
        try {
            JSONObject object = new JSONObject(response);
            JSONArray cauhoiArray = object.getJSONArray("");
            for (int i = 0; i < cauhoiArray.length(); ++i) {
                JSONObject obj = cauhoiArray.getJSONObject(i);
                String loai = obj.getString("TenLoaiCauHoi");
                String noiDung = obj.getString("NoiDungCauHoi");
                CauHoi cauHoi = new CauHoi(loai, noiDung);
                listCauHoi.add(cauHoi);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return         listCauHoi;
    }

}
