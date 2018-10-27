package com.example.vananh.doan;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.vananh.doan.Constant.Constant;
import com.example.vananh.doan.Model.CauHoi;
import com.example.vananh.doan.Model.LoaiCauHoi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;

public class CauHoiDetailActivity extends AppCompatActivity {
    EditText editNoiDung, editA, editB, editC, editD;
    Button btnSave, btnDelete;
    CheckBox chkCHA, chkCHB, chkCHC, chkCHD;
    Spinner spinLoaiCauHoi;
    ArrayList<String> listLoaiCH;
    ArrayAdapter<String> adapter;
    ArrayList<LoaiCauHoi> arrLoaiCauHoi;
    String loaiCH;
    ArrayList<String> listchecked;
    CauHoi ch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cau_hoi_detail);

        //Anh xa
        spinLoaiCauHoi = findViewById(R.id.snipLoaiCH);
        editNoiDung = findViewById(R.id.editndCauHoi);
        editA = findViewById(R.id.editA);
        editB = findViewById(R.id.editB);
        editC = findViewById(R.id.editC);
        editD = findViewById(R.id.editD);
        btnDelete = findViewById(R.id.btnXoaCauHoi);
        btnSave = findViewById(R.id.btnSaveCauHoi);
        chkCHA = findViewById(R.id.chkCauHoiA);
        chkCHB = findViewById(R.id.chkCauHoiB);
        chkCHC = findViewById(R.id.chkCauHoiC);
        chkCHD = findViewById(R.id.chkCauHoiD);

        listchecked = new ArrayList<>();
        arrLoaiCauHoi = new ArrayList<>();
        //
        spinLoaiCauHoi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loaiCH = listLoaiCH.get(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // Lay loai cau hoi
        GetLoaiCauHoi();
        // get Intent khi bam vao item

        Intent intents = getIntent();
        ch = (CauHoi) intents.getSerializableExtra("cauHoi");
        if (ch != null) {
            editNoiDung.setText(ch.getNoiDung());

            editA.setText(ch.getDapAnA());
            editB.setText(ch.getDapAnB());
            editC.setText(ch.getDapAnC() == null ? null: ch.getDapAnC());
            editD.setText(ch.getDapAnD());
            btnDelete.setVisibility(View.VISIBLE);
            listchecked = ch.ConvertCauTraLoiToList();
            //set checked
            if (listchecked.contains("A")) {
                chkCHA.setChecked(true);
            }
            if (listchecked.contains("B")) {
                chkCHB.setChecked(true);
            }
            if (listchecked.contains("C")) {
                chkCHC.setChecked(true);
            }
            if (listchecked.contains("D")) {
                chkCHD.setChecked(true);
            }
            loaiCH = ch.getTenLoaiCauHoi();
            if (adapter != null) {
                int spinnerPosition = adapter.getPosition(loaiCH);
                spinLoaiCauHoi.setSelection(spinnerPosition);
            }

        }


        checkedCheckBox(chkCHA, "A");
        checkedCheckBox(chkCHB, "B");
        checkedCheckBox(chkCHC, "C");
        checkedCheckBox(chkCHD, "D");
        //
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkValid()) {
                    return;
                }

                CauHoi cauHoi = new CauHoi();
                cauHoi.setNoiDung(editNoiDung.getText().toString());
                cauHoi.setDapAnA(editA.getText().toString());
                cauHoi.setDapAnB(editB.getText().toString());
                cauHoi.setDapAnC(editC.getText().toString());
                cauHoi.setDapAnD(editD.getText().toString());

                //set dap an

                //
                //get Loai cau hoi
                LoaiCauHoi loaiCauHoi = getMaLoaiCauHoi();
                cauHoi.setTenLoaiCauHoi(loaiCauHoi.getTenLoaiCauHoi());
                cauHoi.setMaLoaiCauHoi(loaiCauHoi.getMaLoaiCauHoi());
                //
                String ans = "";
                for (int i = 0; i < listchecked.size(); i++) {
                    if (i == listchecked.size() - 1) {
                        ans += listchecked.get(i);
                    } else {
                        ans += listchecked.get(i) + ",";
                    }
                }
                cauHoi.setCauTraLoi(ans);
                //them moi xuong database
                if(ch != null){
                    cauHoi.setId(ch.getId());
                    updateCauHoi(cauHoi);
                }
                else  {
                    saveCauHoi(cauHoi);
                }

            }

        });

        // xoa

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final android.app.AlertDialog.Builder builder=new android.app.AlertDialog.Builder(CauHoiDetailActivity.this);
                // builder.setIcon(R.drawable.exit);
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn có muốn xóa không?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        xoaCauHoi(ch);
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                builder.show();
            }
        });
    }

    private void xoaCauHoi(CauHoi cauHoi) {
        RequestQueue queue = Volley.newRequestQueue(getBaseContext());
        String url = Constant.BASE_URL + "api/CauHois/" + cauHoi.getId();
        final StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String respo) {
                        Toast.makeText(getBaseContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = getIntent();
                        intent.putExtra("Status", 1);
                        setResult(101, intent);
                        finish();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getBaseContext(), "Xóa thất bại", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
    }

    private void updateCauHoi(CauHoi cauHoi) {
        RequestQueue queue = Volley.newRequestQueue(getBaseContext());
        String url = Constant.BASE_URL + "api/CauHois/" + cauHoi.getId();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("MaCauHoi", cauHoi.getId());
            jsonObject.put("MaLoaiCauHoi", cauHoi.getMaLoaiCauHoi());
            jsonObject.put("NoiDungCauHoi", cauHoi.getNoiDung());
            jsonObject.put("DapAnA", cauHoi.getDapAnA());
            jsonObject.put("DapAnB", cauHoi.getDapAnB());
            jsonObject.put("DapAnC", cauHoi.getDapAnC());
            jsonObject.put("DapAnD", cauHoi.getDapAnD());
            jsonObject.put("DapAnDung", cauHoi.getCauTraLoi());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        final JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.PUT, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Toast.makeText(getBaseContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = getIntent();
                        intent.putExtra("Status", 1);
                        setResult(101, intent);
                        finish();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getBaseContext(), "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
            }


        }

        ){
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                try {
                    String jsonString = new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));

                    JSONObject result = null;

                    if (jsonString != null && jsonString.length() > 0)
                        result = new JSONObject(jsonString);

                    return Response.success(result,
                            HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                } catch (JSONException je) {
                    return Response.error(new ParseError(je));
                }
            }
        };
        queue.add(stringRequest);
    }

    private LoaiCauHoi getMaLoaiCauHoi() {
        for (LoaiCauHoi loai : arrLoaiCauHoi
                ) {
            if (loai.getTenLoaiCauHoi().equals(loaiCH)) {
                return loai;
            }
        }
        return new LoaiCauHoi();
    }

    private void saveCauHoi(final CauHoi cauHoi) {
        RequestQueue queue = Volley.newRequestQueue(getBaseContext());
        String url = Constant.BASE_URL + "api/CauHois";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("MaLoaiCauHoi", cauHoi.getMaLoaiCauHoi());
            jsonObject.put("NoiDungCauHoi", cauHoi.getNoiDung());
            jsonObject.put("DapAnA", cauHoi.getDapAnA());
            jsonObject.put("DapAnB", cauHoi.getDapAnB());
            jsonObject.put("DapAnC", cauHoi.getDapAnC());
            jsonObject.put("DapAnD", cauHoi.getDapAnD());
            jsonObject.put("DapAnDung", cauHoi.getCauTraLoi());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        final JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Toast.makeText(getBaseContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = getIntent();
                        intent.putExtra("Status", 1);
                        setResult(101, intent);
                        finish();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getBaseContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
    }

    private void checkedCheckBox(CheckBox chk, final String dapAn) {
        chk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (!listchecked.contains(dapAn)) {
                        listchecked.add(dapAn);
                    }
                } else {
                    listchecked.remove(dapAn);
                }
                Collections.sort(listchecked);
            }
        });
    }

    private boolean checkValid() {
        boolean valid = true;
        if (editNoiDung.getText().toString().isEmpty()) {
            valid = false;
            editNoiDung.setError("Nội dung này không đuọc trống");
        }
        if (editA.getText().toString().isEmpty()) {
            valid = false;
            editA.setError("Nội dung này không đuọc trống");
        }
        if (editB.getText().toString().isEmpty()) {
            valid = false;
            editB.setError("Nội dung này không đuọc trống");
        }
        if (!chkCHA.isChecked() && !chkCHB.isChecked() && !chkCHC.isChecked() && !chkCHD.isChecked()) {
            valid = false;
            Toast.makeText(getBaseContext(), "Bạn chưa chọn đáp án đúng nào", Toast.LENGTH_SHORT).show();
        }
        return valid;
    }


    private void GetLoaiCauHoi() {

        RequestQueue queue = Volley.newRequestQueue(getBaseContext());
        String url = Constant.BASE_URL + "api/LoaiCauHois";
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        listLoaiCH = convertToLoaiCH(response);
                        //set adapter;
                        adapter = new ArrayAdapter<String>(getBaseContext(), R.layout.support_simple_spinner_dropdown_item, listLoaiCH);
                        spinLoaiCauHoi.setAdapter(adapter);

                        //

                        if (loaiCH != null && !loaiCH.isEmpty()) {
                            int spinnerPosition = adapter.getPosition(loaiCH);
                            spinLoaiCauHoi.setSelection(spinnerPosition);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", error.getMessage());
            }
        });

        queue.add(stringRequest);
    }

    private ArrayList<String> convertToLoaiCH(String response) {
        ArrayList<String> listCauHoi = new ArrayList<>();
        try {
            JSONArray cauhoiArray = new JSONArray(response);
            for (int i = 0; i < cauhoiArray.length(); ++i) {
                JSONObject obj = cauhoiArray.getJSONObject(i);
                String loai = obj.getString("TenLoaiCauHoi");
                int maLoai = obj.getInt("MaLoaiCauHoi");
                //
                //luu data
                arrLoaiCauHoi.add(new LoaiCauHoi(maLoai, loai));

                // hien thi cho spinner
                listCauHoi.add(loai);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listCauHoi;
    }
}
