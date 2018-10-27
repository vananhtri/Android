package com.example.vananh.doan.ChucNang;


import android.icu.text.RelativeDateTimeFormatter;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.vananh.doan.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * A simple {@link Fragment} subclass.
 */
public class HuongDan extends android.support.v4.app.Fragment {

    public HuongDan() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Hướng dẫn thi thực hành");
    }

    private VideoView videoView;
    private int position = 0;
    private MediaController mediaController;
    private TextView txtHuongDan;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        videoView = (VideoView) getView().findViewById(R.id.videoHuongDanThucHanh);
        txtHuongDan = (TextView) getView().findViewById(R.id.textHuongDan);
        CreateVideoView();
        ReadFileHuongDanThucHanh();


    }

    private void ReadFileHuongDanThucHanh() {
        String data;
        int id = getRawResIdByName("huongdan");
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
                txtHuongDan.setText(builder.toString());
            } catch (IOException ex) {
                Log.e("ERROR", ex.getMessage());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_huong_dan, container, false);

        // Inflate the layout for this fragment
        return  view;
    }

    private void CreateVideoView() {
        // Tạo bộ điều khiển
        if (mediaController == null) {
            mediaController = new MediaController(getContext());

            // Neo vị trí của MediaController với VideoView.
            mediaController.setAnchorView(videoView);


            // Sét đặt bộ điều khiển cho VideoView.
            videoView.setMediaController(mediaController);
        }
        try {
            // ID của file video.
            int id = this.getRawResIdByName("videothithuchanh");
            videoView.setVideoURI(Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + id));

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        videoView.requestFocus();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                videoView.seekTo(position);
                if (position == 0) {
                    videoView.start();
                }
                mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                        mediaController.setAnchorView(videoView);
                    }
                });
            }
        });
    }

    private int getRawResIdByName(String myvideo) {
        String pkgName = getActivity().getPackageName();
        // Trả về 0 nếu không tìm thấy.
        int resID = getActivity().getResources().getIdentifier(myvideo  , "raw", pkgName);
        Log.i("AndroidVideoView", "Res Name: " + myvideo + "==> Res ID = " + resID);
        return resID;
    }
    // Khi bạn xoay điện thoại, phương thức này sẽ được gọi
    // nó lưu trữ lại ví trí file video đang chơi.
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        // Lưu lại vị trí file video đang chơi.
        savedInstanceState.putInt("CurrentPosition", videoView.getCurrentPosition());
        videoView.pause();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState !=null){
            // Lấy lại ví trí video đã chơi.
            position = savedInstanceState.getInt("CurrentPosition");
            videoView.seekTo(position);
        }

    }
}
