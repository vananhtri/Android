package com.example.vananh.doan.Slide;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vananh.doan.Model.CauHoi;
import com.example.vananh.doan.R;

import java.util.ArrayList;


public class ScreenSlidePageFragment extends Fragment {

    //
    ArrayList<CauHoi> listCauHoi;
    int currentPage;

    TextView textCauHoi, textNumber;
    CheckBox chkA,chkB, chkC, chkD;
    ImageView imageCauHoi;

    public static final String PAGE = "page";

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
        currentPage = getArguments().getInt(PAGE); // nhan current page
    }

    public static ScreenSlidePageFragment createItem(int pageNumber) {
        ScreenSlidePageFragment screenSlidePageFragment = new ScreenSlidePageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(PAGE, pageNumber);
        screenSlidePageFragment.setArguments(bundle);
        return screenSlidePageFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        textNumber.setText("Câu " + String.valueOf(currentPage+1));
        CauHoi cauHoi = listCauHoi.get(currentPage);
        textCauHoi.setText(cauHoi.getNoiDung());
        byte[] hinhAnh = cauHoi.getHinhAnh();
        if(hinhAnh !=null){
            Bitmap bmp = BitmapFactory.decodeByteArray(hinhAnh, 0, hinhAnh.length);
            imageCauHoi.setImageBitmap(Bitmap.createScaledBitmap(bmp, imageCauHoi.getWidth(), imageCauHoi.getHeight(), false));
        }
        setVisibleCheckBox(cauHoi.getDapAnA(), chkA);
        setVisibleCheckBox(cauHoi.getDapAnB(), chkB);
        setVisibleCheckBox(cauHoi.getDapAnC(), chkC);
        setVisibleCheckBox(cauHoi.getDapAnD(), chkD);

    }
    private String getChoiceFromID(int ID) {
        if (ID == R.id.chkA) {
            return "A";
        } else if (ID == R.id.chkB) {
            return "B";
        } else if (ID == R.id.chkC) {
            return "C";
        } else if (ID == R.id.chkD) {
            return "D";
        } else return "";
    }


    private  void  setVisibleCheckBox(String dapAn, CheckBox chk){
        if(dapAn !=null && !dapAn.isEmpty()){
            chk.setVisibility(View.VISIBLE);
            chk.setText(dapAn);
        }
        else {
            chk.setVisibility(View.GONE);
        }
    }
}
