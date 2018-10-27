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
import android.widget.TextView;

import com.example.vananh.doan.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FragMeoThi extends android.support.v4.app.Fragment {
    public FragMeoThi() {
        // Required empty public constructor
    }
    TextView txtMeo;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        txtMeo=(TextView) getView().findViewById(R.id.TextMeo);
        ReadFileMeo();
    }

    private void ReadFileMeo() {
        String data;
        int id = getRawResIdByName("meothi");
        InputStream in= getActivity().getResources().openRawResource(id);
        InputStreamReader inreader=new InputStreamReader(in);
        BufferedReader bufreader=new BufferedReader(inreader);
        StringBuilder builder=new StringBuilder();
        if(in!=null) {
            try {
                while ((data = bufreader.readLine()) != null) {
                    builder.append(data);
                    builder.append("\n");
                }
                in.close();
                txtMeo.setText(builder.toString());
            } catch (IOException ex) {
                Log.e("ERROR", ex.getMessage());
            }
        }
    }

    private int getRawResIdByName(String meothi) {
        String pkgName = getActivity().getPackageName();
        // Trả về 0 nếu không tìm thấy.
        int resID = getActivity().getResources().getIdentifier(meothi  , "raw", pkgName);
        Log.i("AndroidVideoView", "Res Name: " + meothi + "==> Res ID = " + resID);
        return resID;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_frag_meo_thi, container, false);
    }


}
