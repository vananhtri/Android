package com.example.vananh.doan.Slide;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.vananh.doan.R;

public class TestDoneActivity extends AppCompatActivity {

    Button btnThoat, btnLuu;
    TextView textDung, textSai, textChuaLam, textKetQua;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_done);

        //
        btnThoat = findViewById(R.id.btnThoatKQThi);
        btnLuu = findViewById(R.id.btnLuuDiemKQThi);
        textDung = findViewById(R.id.textSoCauDung);
        textSai = findViewById(R.id.textSoCauSai);
        textChuaLam = findViewById(R.id.textSoCauChuaLam);
        textKetQua = findViewById(R.id.textKQThi);
        //


        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder=new AlertDialog.Builder(TestDoneActivity.this);
               // builder.setIcon(R.drawable.exit);
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn có muốn thoát hay không?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
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
}
