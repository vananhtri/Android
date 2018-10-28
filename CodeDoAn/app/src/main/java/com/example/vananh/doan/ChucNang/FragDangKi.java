package com.example.vananh.doan.ChucNang;

import android.app.DatePickerDialog;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
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
import com.example.vananh.doan.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;



public class FragDangKi extends android.support.v4.app.Fragment {


    public FragDangKi() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    TextView NgaySinh;
    EditText edHoTen, edEmail, tenTK, MatKhau, ReMatKhau;
    Button btnDate, btnDangKi;
    Calendar cal;
    RadioButton rdNam, rdNu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_frag_dang_ki, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        AnhXa();
        getDefaultInfor();
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XuatNgayThangNam();
            }
        });
        btnDangKi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DangKi();
            }

            private void DangKi() {
                if (!validate()) {
                    onDangKiFailed();
                    return;
                } else {
                    checkDangKi();
                    return;
                }
            }


        });
    }

    private void checkDangKi() {
        String taikhoan = tenTK.getText().toString();
        String pass = MatKhau.getText().toString();
        String hoten = edHoTen.getText().toString();
        String email = edEmail.getText().toString();
        String ngaysinh = NgaySinh.getText().toString();
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = Constant.BASE_URL + "api/User/Register";
        if(!MatKhau.getText().toString().equals(ReMatKhau.getText().toString())){
            Toast.makeText(getContext(),"Nhập lại mật khẩu không chính xác",Toast.LENGTH_SHORT).show();
            return;
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("TaiKhoan", taikhoan);
            jsonObject.put("MatKhau", pass);
            jsonObject.put("HoTen", hoten);
            jsonObject.put("Email", email);
            jsonObject.put("NgaySinh", ngaysinh);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        onRegisSuccess();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                onDangKiFailed();
            }
        }) { //ep kieu
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
    private void onRegisSuccess() {
        Toast.makeText(getContext(), "Đăng kí thành công", Toast.LENGTH_LONG).show();
        FlagDangNhap fragment = new FlagDangNhap();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_main, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void onDangKiFailed() {
        Toast.makeText(getContext(), "Đăng kí thất bại", Toast.LENGTH_LONG).show();

    }

    private boolean validate() {
        boolean valid = true;
        String HoTen = edHoTen.getText().toString();
        String email = edEmail.getText().toString();
        if (!rdNam.isChecked() && !rdNu.isChecked()) {
            valid = false;
        }
        String ngaysinh = NgaySinh.getText().toString();
        String TenTK = tenTK.getText().toString();
        String pass = MatKhau.getText().toString();
        String repass = ReMatKhau.getText().toString();
        if (HoTen.isEmpty()) {
            valid = false;
            edHoTen.setError("Không để trống");
        }
        if (email.isEmpty()) {
            valid = false;
            edEmail.setError("Không để trống");
        }
        if (ngaysinh.isEmpty()) {
            valid = false;
            NgaySinh.setError("Không để trống");
        }
        if (TenTK.isEmpty()) {
            valid = false;
            tenTK.setError("Không để trống");
        }
        if (pass.isEmpty()) {
            valid = false;
            MatKhau.setError("Không để trống");
        }
        if (repass.isEmpty()) {
            valid = false;
            ReMatKhau.setError("Không để trống");
        }

        return valid;
    }

    public void XuatNgayThangNam() {
        DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear,
                                  int dayOfMonth) {
                //Mỗi lần thay đổi ngày tháng năm thì cập nhật lại TextView Date
                NgaySinh.setText(
                        (dayOfMonth) + "/" + (monthOfYear + 1) + "/" + year);
                //Lưu vết lại biến ngày hoàn thành
                cal.set(year, monthOfYear, dayOfMonth);
            }
        };
        //các lệnh dưới này xử lý ngày giờ trong DatePickerDialog
        //sẽ giống với trên TextView khi mở nó lên
        String s = NgaySinh.getText() + "";
        String strArrtmp[] = s.split("/");
        int ngay = Integer.parseInt(strArrtmp[0]);
        int thang = Integer.parseInt(strArrtmp[1]) - 1;
        int nam = Integer.parseInt(strArrtmp[2]);
        DatePickerDialog pic = new DatePickerDialog(
                getContext(),
                callback, nam, thang, ngay);
        pic.setTitle("Chọn ngày hoàn thành");
        pic.show();
    }

    public void getDefaultInfor() {
        //lấy ngày hiện tại của hệ thống
        cal = Calendar.getInstance();
        SimpleDateFormat dft = null;
        //Định dạng ngày / tháng /năm
        dft = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String strDate = dft.format(cal.getTime());
        //hiển thị lên giao diện
        NgaySinh.setText(strDate);
    }

    public void AnhXa() {
        NgaySinh = getActivity().findViewById(R.id.txtdate);
        edHoTen = getActivity().findViewById(R.id.EditHoTen);
        edEmail = getActivity().findViewById(R.id.EditEmail);
        tenTK = getActivity().findViewById(R.id.EditTenTaiKhoan);
        MatKhau = getActivity().findViewById(R.id.EditMatKhau);
        ReMatKhau = getActivity().findViewById(R.id.EditXacNhanMK);
        btnDate = getActivity().findViewById(R.id.btndate);
        rdNam = getActivity().findViewById(R.id.RadioNam);
        rdNu = getActivity().findViewById(R.id.RadioNu);
        btnDangKi = getActivity().findViewById(R.id.ButtonDK);
    }
}
