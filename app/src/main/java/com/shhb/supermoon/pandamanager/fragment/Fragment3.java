package com.shhb.supermoon.pandamanager.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shhb.supermoon.pandamanager.R;
import com.shhb.supermoon.pandamanager.activity.CreditActivity;

/**
 * Created by superMoon on 2017/8/24.
 */

public class Fragment3 extends BaseFragment implements View.OnClickListener{
    private TextView title;
    private RelativeLayout frt3Rlt1,frt3Rlt2,frt3Rlt3;
    private RadioButton frt3Rb1,frt3Rb2,frt3Rb3;
    private Button frt3Btn,onBtn;

    public static Fragment3 newInstance() {
        Fragment3 fragment = new Fragment3();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment3_view, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        title = (TextView) view.findViewById(R.id.on_title);
        title.setText("意向推荐");
        frt3Btn = (Button) view.findViewById(R.id.frt3_btn);
        frt3Btn.setOnClickListener(this);
        frt3Rlt1 = (RelativeLayout) view.findViewById(R.id.frt3_rlt1);
        frt3Rlt1.setOnClickListener(this);
        frt3Rlt2 = (RelativeLayout) view.findViewById(R.id.frt3_rlt2);
        frt3Rlt2.setOnClickListener(this);
        frt3Rlt3 = (RelativeLayout) view.findViewById(R.id.frt3_rlt3);
        frt3Rlt3.setOnClickListener(this);

        frt3Rb1 = (RadioButton) view.findViewById(R.id.frt3_rb1);
        frt3Rb1.setOnClickListener(this);
        frt3Rb2 = (RadioButton) view.findViewById(R.id.frt3_rb2);
        frt3Rb2.setOnClickListener(this);
        frt3Rb3 = (RadioButton) view.findViewById(R.id.frt3_rb3);
        frt3Rb3.setOnClickListener(this);

        onBtn = (Button) view.findViewById(R.id.on_btn);
        onBtn.setText("提交意愿");
        onBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.frt3_btn:
                Intent intent = new Intent(context, CreditActivity.class);
                startActivity(intent);
                break;
            case R.id.frt3_rlt1:
                frt3Rb1.setChecked(true);
                frt3Rb2.setChecked(false);
                frt3Rb3.setChecked(false);
                break;
            case R.id.frt3_rlt2:
                frt3Rb2.setChecked(true);
                frt3Rb1.setChecked(false);
                frt3Rb3.setChecked(false);
                break;
            case R.id.frt3_rlt3:
                frt3Rb3.setChecked(true);
                frt3Rb1.setChecked(false);
                frt3Rb2.setChecked(false);
                break;
        }
    }
}
