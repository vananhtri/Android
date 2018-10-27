package com.example.vananh.doan.Slide;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.vananh.doan.Adapter.CauHoiAdapter;
import com.example.vananh.doan.Adapter.CheckAnswerAdapter;
import com.example.vananh.doan.Constant.Constant;
import com.example.vananh.doan.Database.SQLCauHoi;
import com.example.vananh.doan.Interface.ServerCallback;
import com.example.vananh.doan.KetQuaActivity;
import com.example.vananh.doan.Model.CauHoi;
import com.example.vananh.doan.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ScreenSlidePagerActivity extends FragmentActivity {
    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final int NUM_PAGES = 20;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;

    //
    TextView textKiemTra;
    ArrayList<CauHoi> listCauHoi;
    SQLCauHoi sqlCauHoi;
    TextView textTime;
    TextView textXemDiem;
    int maBoDe;
    int checkAnswer = 0;
    CounterClass timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide_pager);
        //
        Intent intent = getIntent();
        maBoDe = intent.getIntExtra("MaBoDe", 0);
        textKiemTra = findViewById(R.id.textKiemTra);
        textTime = findViewById(R.id.tvTimer);
        textXemDiem = findViewById(R.id.tvXemDiem);
        timer = new CounterClass(15 * 60 * 1000, 1000);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = findViewById(R.id.pager);

        //
        listCauHoi = new ArrayList<>();
        sqlCauHoi = new SQLCauHoi(getBaseContext());
        String url = Constant.BASE_URL + "api/BoDe/ThiThu/get/" + maBoDe;
        if(maBoDe == 0 ) //get ngau nhien
        {
            url  =  Constant.BASE_URL + "api/BoDe/ThiThu/getRanDom";

        }
        getListCauHoiByBoDe(url);
        //event

        textKiemTra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
            }
        });

        textXemDiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setKetQuaThi();
            }
        });
        textTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        timer.start();
    }

    private void getListCauHoiByBoDe(String url) {
        ArrayList<CauHoi> cauHoiArrayList = new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(getBaseContext());
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        //set adapter;
                        listCauHoi = convertToListCauHoi(response);
                        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
                        mPager.setAdapter(mPagerAdapter);
                        mPager.setPageTransformer(true, new DepthPageTransformer());

                        // cauHoiAdapter = new CauHoiAdapter(getContext(), listCauHoi);
                        // lvCauHoi.setAdapter(cauHoiAdapter);

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
                String noiDung = obj.getString("NoiDungCauHoi");
                String daA = obj.getString("DapAnA");
                String daB = obj.getString("DapAnB");
                String daC = obj.getString("DapAnC");
                String daD = obj.getString("DapAnD");
                String cauTraLoi = obj.getString("DapAnDung");
                String tenHinhAnh = obj.getString("HinhAnh");
                int hinhAnh = getResources().getIdentifier(tenHinhAnh, "drawable", getPackageName());
                CauHoi cauHoi = new CauHoi(maCauHoi,maBoDe,noiDung,daA,daB,daC, daD,cauTraLoi,maLoai,hinhAnh);
                listCauHoi.add(cauHoi);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listCauHoi;
    }
    private void setKetQuaThi() {
        List<CauHoi> listCauHoi = getData();
        int dapAnDung = 0;
        int dapAnSai = 0;
        int chuaChon = 0;
        for (CauHoi cauHoi : listCauHoi) {
            if(cauHoi.getTraLoiNguoiDung() == null || cauHoi.getTraLoiNguoiDung().isEmpty()){
                chuaChon ++;
            }
            else if (cauHoi.getTraLoiNguoiDung().equals(cauHoi.getCauTraLoi())) {
                dapAnDung++;
            }
            else  dapAnSai ++;
        }

        Intent in = new Intent(getBaseContext(), KetQuaActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("dapAnDung", dapAnDung);
        bundle.putInt("dapAnSai", dapAnSai);
        bundle.putInt("chuaChon", chuaChon);
        in.putExtra("ketqua",bundle);
        startActivity(in);
    }


    public ArrayList<CauHoi> getData() {
        return listCauHoi;
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
                return ScreenSlidePageFragment.createItem(position, checkAnswer);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    public class DepthPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.75f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0f);

            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when moving to the left page
                view.setAlpha(1f);
                view.setTranslationX(0f);
                view.setScaleX(1f);
                view.setScaleY(1f);

            } else if (position <= 1) { // (0,1]
                // Fade the page out.
                view.setAlpha(1 - position);

                // Counteract the default slide transition
                view.setTranslationX(pageWidth * -position);

                // Scale the page down (between MIN_SCALE and 1)
                float scaleFactor = MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0f);
            }
        }
    }


    public void checkAnswer() {
        final Dialog dialog = new Dialog(ScreenSlidePagerActivity.this);
        dialog.setContentView(R.layout.check_answer_dialog);
        dialog.setTitle("Danh sách câu trả lời");

        CheckAnswerAdapter answerAdapter = new CheckAnswerAdapter(listCauHoi, ScreenSlidePagerActivity.this);

        GridView gridCheckAnswer = dialog.findViewById(R.id.gridCheckAnswer);
        gridCheckAnswer.setAdapter(answerAdapter);

        gridCheckAnswer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPager.setCurrentItem(position);
                dialog.dismiss();
            }
        });

        Button btnCancle, btnFinish;
        btnCancle = dialog.findViewById(R.id.btnDong);
        btnFinish = dialog.findViewById(R.id.btnKetThuc);
        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnFinish.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                /////
                final AlertDialog.Builder builder = new AlertDialog.Builder(ScreenSlidePagerActivity.this);
                // builder.setIcon(R.drawable.exit);
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn có muốn kết thúc không?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogs, int which) {
                        timer.cancel();
                        result();
                        dialogs.dismiss();
                        dialog.dismiss();
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

        dialog.show();
    }

    private void result() {
        checkAnswer = 1;
        if (mPager.getCurrentItem() >= 4) mPager.setCurrentItem(mPager.getCurrentItem() - 4);
        else if (mPager.getCurrentItem() <= 4) mPager.setCurrentItem(mPager.getCurrentItem() + 4);
        textXemDiem.setVisibility(View.VISIBLE);
        textKiemTra.setVisibility(View.GONE);
    }


    // COUNT TIME
    public class CounterClass extends CountDownTimer {
        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */


        //millisInFuture: 15 * 60 *1000
        //countDownInterval:  1000
        public CounterClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            String countTime = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished), TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
            textTime.setText(countTime); //SetText cho textview hiện thị thời gian.
        }

        @Override
        public void onFinish() {
            textTime.setText("00:00");  //SetText cho textview hiện thị thời gian.
            result();
        }
    }
}
