package com.shhb.supermoon.pandamanager.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.shhb.supermoon.pandamanager.R;
import com.shhb.supermoon.pandamanager.activity.CreditActivity;
import com.shhb.supermoon.pandamanager.activity.LoginActivity;
import com.shhb.supermoon.pandamanager.tools.PrefShared;
import com.shhb.supermoon.pandamanager.view.GlideCircleTransform;

/**
 * Created by superMoon on 2017/8/24.
 */

public class Fragment4 extends BaseFragment implements View.OnClickListener {
    private ImageView frt4Icon;
    private TextView frt4LP;
    private Button frt4Btn;
    private RelativeLayout ft4Rlt1, ft4Rlt2, ft4Rlt3, ft4Rlt4, ft4Rlt5;
    private String iconUrl;
    private LinearLayout frt4Integral;

    public static Fragment4 newInstance() {
        Fragment4 fragment = new Fragment4();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment4_view, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeToLoadLayout = (SwipeToLoadLayout) view.findViewById(R.id.swipeToLoadLayout);
        swipeToLoadLayout.setRefreshEnabled(false);
        swipeToLoadLayout.setLoadMoreEnabled(false);
        swipeToLoadLayout.setOnRefreshListener(this);

        frt4Icon = (ImageView) view.findViewById(R.id.frt4_icon);
        frt4LP = (TextView) view.findViewById(R.id.frt4_l_p);
        frt4LP.setOnClickListener(this);
        frt4Integral = (LinearLayout) view.findViewById(R.id.frt4_integral);

        frt4Btn = (Button) view.findViewById(R.id.frt4_btn);
        frt4Btn.setOnClickListener(this);
        ft4Rlt1 = (RelativeLayout) view.findViewById(R.id.ft4_rlt1);
        ft4Rlt1.setOnClickListener(this);
        ft4Rlt2 = (RelativeLayout) view.findViewById(R.id.ft4_rlt2);
        ft4Rlt2.setOnClickListener(this);
        ft4Rlt3 = (RelativeLayout) view.findViewById(R.id.ft4_rlt3);
        ft4Rlt3.setOnClickListener(this);
        ft4Rlt4 = (RelativeLayout) view.findViewById(R.id.ft4_rlt4);
        ft4Rlt4.setOnClickListener(this);
        ft4Rlt5 = (RelativeLayout) view.findViewById(R.id.ft4_rlt5);
        ft4Rlt5.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        iconUrl = PrefShared.getString(context, "userImg");
        if (null != iconUrl) {
            frt4Integral.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(iconUrl)
                    .placeholder(R.mipmap.n_img_photo)
                    .error(R.mipmap.n_img_photo)//加载出错的图片
                    .priority(Priority.HIGH)//优先加载
                    .transform(new GlideCircleTransform(context))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)//设置缓存策略
                    .into(frt4Icon);
        } else {
            frt4LP.setText("登录/注册");
            frt4Integral.setVisibility(View.GONE);
            Glide.with(context)
                    .load(R.mipmap.n_img_photo)
                    .placeholder(R.mipmap.error_z)
                    .error(R.mipmap.error_z)//加载出错的图片
                    .priority(Priority.HIGH)//优先加载
                    .transform(new GlideCircleTransform(context))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)//设置缓存策略
                    .into(frt4Icon);
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.frt4_l_p:
                intent = new Intent(context, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.frt4_btn:
                intent = new Intent(context, CreditActivity.class);
                startActivity(intent);
                break;
            case R.id.ft4_rlt1:

                break;
            case R.id.ft4_rlt2:

                break;
            case R.id.ft4_rlt3:

                break;
            case R.id.ft4_rlt4:

                break;
            case R.id.ft4_rlt5:
                break;
        }
    }
}
