package com.example.vananh.doan.Slide;

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
        textNumber.setText("Câu " + String.valueOf(currentPage + 1));
        CauHoi cauHoi = listCauHoi.get(currentPage);
        textCauHoi.setText(cauHoi.getNoiDung());
        byte[] hinhAnh = cauHoi.getHinhAnh();
        if (hinhAnh != null) {
            Bitmap bmp = BitmapFactory.decodeByteArray(hinhAnh, 0, hinhAnh.length);
            imageCauHoi.setImageBitmap(Bitmap.createScaledBitmap(bmp, imageCauHoi.getWidth(), imageCauHoi.getHeight(), false));
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

    private void getCheckAns(String ans) {
        if (ans == null) {
            ans ="";
        }
        List<String> listAns = new ArrayList<String>(Arrays.asList(ans.split(",")));
//        for (String answer : listAns) {
//            if (answer.equals("A")) {
//                btnAnswerA.setVisibility(View.VISIBLE);
//                btnAnswerA.setBackgroundColor(Color.GREEN);
//            }
//            if (answer.equals("B")) {
//                btnAnswerB.setVisibility(View.VISIBLE);
//                btnAnswerB.setBackgroundColor(Color.GREEN);
//            }
//            if (answer.equals("C")) {
//                btnAnswerC.setVisibility(View.VISIBLE);
//                btnAnswerC.setBackgroundColor(Color.GREEN);
//            }
//            if (answer.equals("D")) {
//                btnAnswerD.setVisibility(View.VISIBLE);
//                btnAnswerD.setBackgroundColor(Color.GREEN);
//            }
//        }
//        btnAnswerA.setVisibility(View.VISIBLE);
//        btnAnswerB.setVisibility(View.VISIBLE);
//        btnAnswerC.setVisibility(View.VISIBLE);
//        btnAnswerD.setVisibility(View.VISIBLE);
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
        if (dapAn != null && !dapAn.isEmpty()) {
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
}
