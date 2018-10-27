package com.example.vananh.doan;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.vananh.doan.ChucNang.Home;

public class KetQuaActivity extends AppCompatActivity {

    Button btnThoat, btnLuu;
    TextView textDung, textSai, textChuaLam, textKetQua;
    int dapAnDung, dapAnSai, chuaChon;
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


        //get Inten
        Intent intent = getIntent();
        Bundle bundle   = intent.getBundleExtra("ketqua");
        dapAnDung = bundle.getInt("dapAnDung");
        dapAnSai = bundle.getInt("dapAnSai");
        chuaChon = bundle.getInt("chuaChon");

        //set text
        textDung.setText(String.valueOf(dapAnDung));
        textSai.setText(String.valueOf(dapAnSai));
        textDung.setText(String.valueOf(dapAnDung));
        textChuaLam.setText(String.valueOf(chuaChon));
        textKetQua.setText(dapAnDung >=16? "ĐẬU": "RỚT");

        //events
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder=new AlertDialog.Builder(KetQuaActivity.this);
               // builder.setIcon(R.drawable.exit);
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn có muốn thoát hay không?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Home flagHome = new Home();
//                        FragmentManager manager =   getSupportFragmentManager();
//                        manager.beginTransaction().replace(R.id.content_main, flagHome)
//                                .addToBackStack(null).commit();
                        Intent intent1 = new Intent(getBaseContext(), menuActivity.class);
                        intent1.putExtra("ketQua", 1);
                        startActivity(intent1);

                      //  finish();
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      //  finish();
                    }
                });

                builder.show();
            }
        });
    }
}
