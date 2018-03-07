package com.shhb.supermoon.pandamanager.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.shhb.supermoon.pandamanager.R;
import com.shhb.supermoon.pandamanager.model.PhoneInfo;
import com.shhb.supermoon.pandamanager.tools.BaseTools;
import com.shhb.supermoon.pandamanager.tools.Constants;
import com.shhb.supermoon.pandamanager.tools.OkHttpUtils;
import com.shhb.supermoon.pandamanager.tools.PrefShared;
import com.shhb.supermoon.pandamanager.tools.UMShare;
import com.umeng.message.PushAgent;
import com.umeng.socialize.UMShareAPI;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by superMoon on 2017/8/31.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener{
    private EditText phoneEdt,passWEdt;
    private TextView wLogin, forgetPassword,goRegister;
    private Button onBtn;

    private String telphoneNum,telPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
    }

    @Override
    public void initView() {
        super.initView();
        title = (TextView) findViewById(R.id.on_title);
        title.setText("登录");
        onBack = (TextView) findViewById(R.id.on_back);
        Drawable drawable= context.getResources().getDrawable(R.mipmap.n_cz3);
        drawable.setBounds(0,0,drawable.getMinimumWidth(), drawable.getMinimumHeight());
        onBack.setCompoundDrawables(drawable,null,null,null);
        onBack.setOnClickListener(this);
        onShare = (TextView) findViewById(R.id.on_share);
        onShare.setText("验证码登录");

        phoneEdt = (EditText) findViewById(R.id.phone_number);
        passWEdt = (EditText) findViewById(R.id.password);
        wLogin = (TextView) findViewById(R.id.wLogin);
        wLogin.setOnClickListener(this);
        forgetPassword = (TextView) findViewById(R.id.forget_password);
        forgetPassword.setOnClickListener(this);
        goRegister = (TextView) findViewById(R.id.go_register);
        goRegister.setOnClickListener(this);
        onBtn = (Button) findViewById(R.id.on_btn);
        onBtn.setText("立即登录");
        onBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.on_back:
                finish();
                break;
            case R.id.on_btn:
                btnLogin();
                break;
            case R.id.wLogin:
                new UMShare(this,"微信登录").login();
                break;
        }
    }

    private void btnLogin() {
        telphoneNum = phoneEdt.getText().toString();
        telPassword = passWEdt.getText().toString();
        if(TextUtils.equals(telphoneNum,"")){
            showToast(1,context.getResources().getString(R.string.msg_input_phone));
        } else {
            if(BaseTools.isNumeric(telphoneNum) && telphoneNum.length() == 11) {
                if (TextUtils.equals(telPassword, "")) {
                    showToast(1, context.getResources().getString(R.string.msg_input_pw));
                } else {
                    if(6 > telPassword.length()){
                        showToast(1,context.getResources().getString(R.string.msg_pw_error));
                    } else {
//                        usLogin();
                    }
                }
            } else {
                showToast(1,context.getResources().getString(R.string.msg_phone_error));
            }
        }
    }

    /**
     * 用户登录
     */
    private void usLogin() {
        showToast(0,context.getResources().getString(R.string.msg_jz));
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("phone",telphoneNum);
        jsonObject.put("password",telPassword);
        jsonObject.put("device_token", PushAgent.getInstance(this).getRegistrationId());
        jsonObject.put("vcode","");
        jsonObject.put("uid_type",1);
        jsonObject.put("msys",1);
        jsonObject.put("address", PrefShared.getString(context,"position"));
        PhoneInfo phoneInfo = new PhoneInfo(context);
        Map<String,Object> map = phoneInfo.getPhoneMsg();
        map.put("type","1");
        for(Map.Entry<String, Object> m : map.entrySet()){
            jsonObject.put(m.getKey(),m.getValue());
        }
        String parameter = BaseTools.encodeJson(jsonObject.toString());
        new OkHttpUtils(20).postEnqueue(Constants.USER_LOGIN, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                showToast(1,context.getResources().getString(R.string.msg_net));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String msg = "";
                try {
                    JSONObject jsonObject = BaseTools.jsonObject(context,response,"用户登录");
                    int status = jsonObject.getInteger("status");
                    msg = jsonObject.getString("msg");
                    if(1 == status){
                        jsonObject = jsonObject.getJSONObject("data");
                        PrefShared.saveString(context,"phoneNum",telphoneNum);
                        PrefShared.saveString(context,"userId",jsonObject.getString("uid"));
                        PrefShared.saveString(context,"push",jsonObject.getString("allow_push"));
                        PrefShared.saveString(context,"weChat",jsonObject.getString("wx_openid"));
                        PrefShared.saveString(context,"userImg",jsonObject.getString("head_img"));
                        PrefShared.saveString(context,"userName",jsonObject.getString("nickname"));
                        PrefShared.saveString(context,"userGrade",jsonObject.getString("grade"));
                        PrefShared.saveString(context,"userPrice",jsonObject.getString("price"));
                        new CloseThread("LoginActivity").start();
                    }
                } catch (Exception e){
                    msg = context.getResources().getString(R.string.msg_send);
                    e.printStackTrace();
                }
                showToast(1,msg);
            }
        },parameter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

    }
}
