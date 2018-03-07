package com.shhb.supermoon.pandamanager.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shhb.supermoon.pandamanager.R;

/**
 * Created by superMoon on 2017/8/28.
 */

public class CreditActivity extends BaseActivity implements View.OnClickListener{
    private RelativeLayout creditRlt1,creditRlt2;
    private RadioButton rbWechat,rbAlipay;
    private Button onBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.credit_activity);
    }

    @Override
    public void initView() {
        super.initView();
        title = (TextView) findViewById(R.id.on_title);
        title.setText("充值");
        onBack = (TextView) findViewById(R.id.on_back);
        Drawable drawable= context.getResources().getDrawable(R.mipmap.n_cz3);
        drawable.setBounds(0,0,drawable.getMinimumWidth(), drawable.getMinimumHeight());
        onBack.setCompoundDrawables(drawable,null,null,null);
        onBack.setOnClickListener(this);
        onShare = (TextView) findViewById(R.id.on_share);
        onShare.setText("明细");
        creditRlt1 = (RelativeLayout) findViewById(R.id.credit_rlt1);
        creditRlt1.setOnClickListener(this);
        creditRlt2 = (RelativeLayout) findViewById(R.id.credit_rlt2);
        creditRlt2.setOnClickListener(this);
        rbWechat = (RadioButton) findViewById(R.id.rb_wechat);
        rbAlipay = (RadioButton) findViewById(R.id.rb_alipay);
        onBtn = (Button) findViewById(R.id.on_btn);
        onBtn.setText("确认充值");
        onBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.on_back:
                finish();
                break;
            case R.id.credit_rlt1:
                rbWechat.setChecked(true);
                rbAlipay.setChecked(false);
                break;

            case R.id.credit_rlt2:
                rbAlipay.setChecked(true);
                rbWechat.setChecked(false);
                break;
        }
    }
}
