package com.shhb.supermoon.pandamanager.fragment;


import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.kaopiz.kprogresshud.KProgressHUD;

/**
 * Created by superMoon on 2017/7/31.
 */

public class BaseFragment extends Fragment implements OnRefreshListener, OnLoadMoreListener {
    public Activity context;
    public TextView onBack,onTitle,onShare;
    /** 刷新控件 */
    public SwipeToLoadLayout swipeToLoadLayout;
    /** 然刷新是否进行的标示 */
    public int lifeCycle = 1;
    /**
     * 请求页码
     */
    public int mPageIndex = 1;
    /**
     * 每页请求数量
     */
    public int mPageNum = 10;
    /** 加载 */
    public KProgressHUD loadView;
    /** 显示 */
    public KProgressHUD showView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = getActivity();
        createLoadView();
        createShowView();
    }

    /**
     * 关闭swipeToLoadLayout的刷新
     * @param swipeToLoadLayout
     */
    public void cLoading(final SwipeToLoadLayout swipeToLoadLayout) {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (swipeToLoadLayout.isRefreshing()) {
                    swipeToLoadLayout.setRefreshing(false);
                }
                if (swipeToLoadLayout.isLoadingMore()) {
                    swipeToLoadLayout.setLoadingMore(false);
                }
            }
        });
    }

    /**
     * 创建loading
     */
    protected void createLoadView() {
        loadView = KProgressHUD.create(context);
        loadView.setCancellable(true);
    }

    /**
     * 创建Toast
     */
    protected void createShowView() {
        showView = KProgressHUD.create(context);
        showView.setCustomView(new ImageView(context));
        showView.setCancellable(true);
    }

    /**
     * 显示加载View
     * @param content
     */
    public void showToast(int type, final String content){
        if(!TextUtils.equals(content,"")){
            if(0 == type){
                loadView.setLabel(content);
                loadView.show();
            } else {
                if(loadView.isShowing()){
                    loadView.dismiss();
                }
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            showView.setLabel(content);
                            if(context != null){
                                showView.show();
                            }
                            new HideThread().start();
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
    }

    /**
     * 1.5秒之后关掉普通提示
     */
    private class HideThread extends Thread {
        public void run() {
            try {
                sleep(1500);
                if(showView.isShowing()){
                    showView.dismiss();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        mPageIndex = 1;
        swipeToLoadLayout.setRefreshing(true);
    }

    /**
     * 上拉加载
     */
    @Override
    public void onLoadMore() {
        ++mPageIndex;
        swipeToLoadLayout.setLoadingMore(true);
    }
}
