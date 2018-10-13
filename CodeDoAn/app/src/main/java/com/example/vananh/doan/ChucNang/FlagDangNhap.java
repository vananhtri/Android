package com.example.vananh.doan.ChucNang;

import android.os.Bundle;
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

import com.example.vananh.doan.Database.SQLNguoiDung;
import com.example.vananh.doan.Database.SqlDataHelper;
import com.example.vananh.doan.Model.NguoiDung;
import com.example.vananh.doan.R;

import java.io.IOException;


public class FlagDangNhap extends android.support.v4.app.Fragment {


    public FlagDangNhap() {
        // Required empty public constructor
    }

    private EditText user, pass;
    private Button login, dangki;
    private SQLNguoiDung    sqlNguoiDung;
    private NguoiDung nguoiDung;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        user=getActivity().findViewById(R.id.EditUser);
        pass=getActivity().findViewById(R.id.EditPass);
        login=getActivity().findViewById(R.id.ButtonLogin);
        dangki=getActivity().findViewById(R.id.ButtonDangki);
        SqlDataHelper sqlDataHelper = new SqlDataHelper(getContext());
        //try connection
        try {
            sqlDataHelper.isCreatedDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        sqlNguoiDung = new SQLNguoiDung(getContext());
       login.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               login();
           }
       });
    }

    private void login() {
        if (!validate()) {
            onLoginFailed();
            return;
        }
        else
        {
            checkLogin();
            return;
        }
    }

    private void checkLogin() {
        nguoiDung = sqlNguoiDung.getNguoiDung(user.getText().toString(),pass.getText().toString());
        if(nguoiDung == null){
            onLoginFailed();
        }
        else if (nguoiDung.getTinhTrang()==0)
        {
            onLoginFailed();
        }
        else {
            onLoginSuccess();
        }
    }

    private void onLoginSuccess() {
        Toast.makeText(getContext(),"Login success",Toast.LENGTH_LONG).show();
         Home fragment = new Home();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_main, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();



    }

    private void onLoginFailed() {
        Toast.makeText(getContext(), "Login failed", Toast.LENGTH_LONG).show();

        login.setEnabled(true);
    }

    private boolean validate() {
        boolean valid=true;
        String username=user.getText().toString();
        String password=pass.getText().toString();
        if (username.isEmpty())
        {
            valid= false;
            user.setError("Không để trống tên đăng nhập");
        }
        if (password.isEmpty())
        {
            valid= false;
            pass.setError("Không để trống mật khẩu");
        }
        return valid;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_flag_dang_nhap, container, false);

    }



}
