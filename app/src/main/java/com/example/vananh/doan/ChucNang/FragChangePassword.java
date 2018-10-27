package com.example.vananh.doan.ChucNang;

import android.content.Context;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.vananh.doan.Constant.Constant;
import com.example.vananh.doan.Model.NguoiDung;
import com.example.vananh.doan.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;


public class FragChangePassword extends android.support.v4.app.Fragment {

    public FragChangePassword() {

    }

    EditText edMatKhauCu, edMatKhauMoi, edNhapLaiMKMoi;
    Button btnThayDoi;
    int id;
    String matKhau;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            id  = bundle.getInt("id", 0);
            matKhau = bundle.getString("matKhau");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_frag_change_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AnhXa();
        btnThayDoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validate()) {
                    onChangePasswordFailed();
                    return;
                } else {
                    checkChangePassword();
                    return;
                }
            }
        });
    }

    private void checkChangePassword() {

        //get tai khoan mk
        String matKhauCu = edMatKhauCu.getText().toString();
        String mkMoi = edMatKhauMoi.getText().toString();
        String reMkMoi = edNhapLaiMKMoi.getText().toString();

        if(!matKhauCu.equals(matKhau)){
            Toast.makeText(getContext(), "Mật khẩu cũ không chính xác", Toast.LENGTH_LONG).show();
            return;
        }
        if(!mkMoi.equals(reMkMoi)){
            Toast.makeText(getContext(), "Mật khẩu mới khác Nhập lại mật khẩu", Toast.LENGTH_LONG).show();
            return;
        }

        // update password
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = Constant.BASE_URL + "api/User/ChangePassword/"+id;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("MaNguoiDung", id);
            jsonObject.put("MatKhau", matKhau);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.PUT, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        onChangePasswordSuccess();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getContext(), "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
            }


        }

        ){ //ep kieu
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

    private void onChangePasswordSuccess() {
        Toast.makeText(getContext(), "Đổi mật khẩu thành công", Toast.LENGTH_LONG).show();
        FlagDangNhap fragment = new FlagDangNhap();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_main, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    private void onChangePasswordFailed() {
        Toast.makeText(getContext(), "Đổi mật khẩu thất bại", Toast.LENGTH_LONG).show();

    }

    private boolean validate() {
        boolean valid=true;

        if (edMatKhauCu.getText().toString().isEmpty()) {
            valid = false;
            edMatKhauCu.setError("Không để trống");
        }
        if (edMatKhauMoi.getText().toString().isEmpty()) {
            valid = false;
            edMatKhauMoi.setError("Không để trống");
        }
        if (edNhapLaiMKMoi.getText().toString().isEmpty()) {
            valid = false;
            edNhapLaiMKMoi.setError("Không để trống");
        }
        return valid;
    }

    private void AnhXa() {
        edMatKhauCu=getActivity().findViewById(R.id.EditMatKhauCu);
        edMatKhauMoi=getActivity().findViewById(R.id.EditMatKhauMoi);
        edNhapLaiMKMoi=getActivity().findViewById(R.id.EditXacNhanMatKhauMoi);
        btnThayDoi=getActivity().findViewById(R.id.ButtonThayDoi);
    }
}
