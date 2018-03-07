package com.shhb.supermoon.pandamanager.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shhb.supermoon.pandamanager.R;

/**
 * Created by superMoon on 2017/8/24.
 */

public class Fragment2 extends BaseNavPagerFragment{

    public static Fragment2 newInstance() {
        Fragment2 fragment = new Fragment2();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.t_layout_recycler_view, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onTitle = (TextView) view.findViewById(R.id.on_title);
        onTitle.setText("订单");
    }

    @Override
    protected String[] getTitles() {
        return new String[]{"待处理", "已完成"};
    }

    @Override
    protected Fragment getFragment(int position) {
        return Fragment5.newInstance(position);
    }

}
