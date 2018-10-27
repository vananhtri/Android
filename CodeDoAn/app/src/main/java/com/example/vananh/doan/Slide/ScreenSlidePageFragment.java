package com.example.vananh.doan.Slide;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vananh.doan.Model.CauHoi;
import com.example.vananh.doan.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class ScreenSlidePageFragment extends Fragment {

    //
    ArrayList<CauHoi> listCauHoi;
    int currentPage;

    TextView textCauHoi, textNumber;
    CheckBox chkA, chkB, chkC, chkD;
    Button btnAnswerA, btnAnswerB, btnAnswerC, btnAnswerD;
    ImageView imageCauHoi;
    int checkAns;
    public static final String PAGE = "page";
    public static final String CHECK = "check_answer";

    public ScreenSlidePageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_screen_slide_page, container, false);
        textCauHoi = rootView.findViewById(R.id.textCauHoi);
        textNumber = rootView.findViewById(R.id.textNumber);
        btnAnswerA = rootView.findViewById(R.id.btnAnswerA);
        btnAnswerB = rootView.findViewById(R.id.btnAnswerB);
        btnAnswerC = rootView.findViewById(R.id.btnAnswerC);
        btnAnswerD = rootView.findViewById(R.id.btnAnswerD);
        imageCauHoi = rootView.findViewById(R.id.imageView);
        chkA = rootView.findViewById(R.id.chkA);
        chkB = rootView.findViewById(R.id.chkB);
        chkC = rootView.findViewById(R.id.chkC);
        chkD = rootView.findViewById(R.id.chkD);
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listCauHoi = new ArrayList<>();
        ScreenSlidePagerActivity slidePagerActivity = (ScreenSlidePagerActivity) getActivity();
        listCauHoi = slidePagerActivity.getData();
        checkAns = getArguments().getInt(CHECK);
        currentPage = getArguments().getInt(PAGE); // nhan current page
    }

    public static ScreenSlidePageFragment createItem(int pageNumber, int checkAns) {
        ScreenSlidePageFragment screenSlidePageFragment = new ScreenSlidePageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(PAGE, pageNumber);
        bundle.putInt(CHECK, checkAns);
        screenSlidePageFragment.setArguments(bundle);
        return screenSlidePageFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (listCauHoi != null && listCauHoi.size() > 0) {
            textNumber.setText("Câu " + String.valueOf(currentPage + 1));
            CauHoi cauHoi = listCauHoi.get(currentPage);
            textCauHoi.setText(cauHoi.getNoiDung());
            int hinhAnh = cauHoi.getHinhAnh();
            if (hinhAnh != 0) {
                imageCauHoi.setImageBitmap(decodeSampledBitmapFromResource(getResources(), hinhAnh, 150,150));
            }
            setVisibleCheckBox(cauHoi.getDapAnA(), chkA, btnAnswerA, "A");
            setVisibleCheckBox(cauHoi.getDapAnB(), chkB, btnAnswerB, "B");
            setVisibleCheckBox(cauHoi.getDapAnC(), chkC, btnAnswerC, "C");
            setVisibleCheckBox(cauHoi.getDapAnD(), chkD, btnAnswerD, "D");

            if (checkAns != 0) {
                chkA.setClickable(false);
                chkB.setClickable(false);
                chkC.setClickable(false);
                chkD.setClickable(false);
                getCheckAns(getItem(currentPage).getCauTraLoi());
            }
        }

    }
    public Bitmap readBitmapAndScale(int id){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; //Chỉ đọc thông tin ảnh, không đọc dữ liwwuj
        BitmapFactory.decodeResource(getContext().getResources(),id); //Đọc thông tin ảnh
        options.inSampleSize = 8; //Scale bitmap xuống 8 lần
        options.inJustDecodeBounds=false; //Cho phép đọc dữ liệu ảnh ảnh
        return BitmapFactory.decodeResource(getContext().getResources(),id);
    }
    private void getCheckAns(String ans) {
        if (ans == null) {
            ans = "";
        }
        List<String> listAns = new ArrayList<String>(Arrays.asList(ans.split(",")));
        if (listAns.contains("A")) {

            btnAnswerA.setBackgroundColor(Color.GREEN);
        } else {
            btnAnswerA.setBackgroundColor(Color.RED);
        }
        if (listAns.contains("B")) {

            btnAnswerB.setBackgroundColor(Color.GREEN);
        } else {
            btnAnswerB.setBackgroundColor(Color.RED);
        }
        if (listAns.contains("C")) {

            btnAnswerC.setBackgroundColor(Color.GREEN);
        } else {
            btnAnswerC.setBackgroundColor(Color.RED);
        }
        if (listAns.contains("D")) {

            btnAnswerD.setBackgroundColor(Color.GREEN);
        } else {
            btnAnswerD.setBackgroundColor(Color.RED);
        }
    }

    public CauHoi getItem(int posotion) {
        return listCauHoi.get(posotion);
    }

    private void setVisibleCheckBox(String dapAn, CheckBox chk, Button btn, final String traLoi) {
        if (dapAn != null && !dapAn.isEmpty() && !dapAn.equalsIgnoreCase("null")) {
            chk.setVisibility(View.VISIBLE);
            chk.setText(dapAn);
            if (checkAns != 0) {
                btn.setVisibility(View.VISIBLE);
            }
        } else {
            chk.setVisibility(View.GONE);
            btn.setVisibility(View.INVISIBLE);
        }

        chk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ArrayList<String> list = getItem(currentPage).getListTraLoi();
                if (isChecked) {
                    if (!list.contains(traLoi)) {
                        list.add(traLoi);
                    }
                } else {
                    list.remove(traLoi);
                }
                Collections.sort(list);
            }
        });
    }
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }
}
