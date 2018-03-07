package com.shhb.supermoon.pandamanager.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.jaeger.library.StatusBarUtil;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.shhb.supermoon.pandamanager.R;
import com.shhb.supermoon.pandamanager.application.MainApplication;
import com.shhb.supermoon.pandamanager.view.BlackStatusBar;

import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by superMoon on 2017/8/24.
 */

public class BaseActivity extends AppCompatActivity{

    public static Activity context;
    /** 刷新控件 */
    public SwipeToLoadLayout swipeToLoadLayout;
    /** 区分是否第二次进来 */
    public int lifeCycle = 1;
    /** 状态栏透明的View */
    public View mViewNeedOffset;
    /** 返回按钮,标题,邀请按钮 */
    public TextView onBack,title,onShare;
    /** 加载 */
    public KProgressHUD loadView;
    /** 显示 */
    public KProgressHUD showView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        MainApplication.getInstance().addActivity(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setStatusBar();
        createLoadView();
        createShowView();
        initView();
    }

    /**
     * 初始化布局
     */
    public void initView() {
    }

    /**
     * 设置透明状态栏
     */
    protected void setStatusBar() {
        StatusBarUtil.setTranslucentForImageView(this, 0, mViewNeedOffset);
    }

    /**
     * 设置状态栏为黑色图标
     */
    public void setBlackStatusBar() {
        BlackStatusBar.StatusBarLightMode(this);
    }

    /**
     * 设置状态栏为白色图标
     */
    public void clearBlackStatusBar() {
        BlackStatusBar.StatusBarDarkMode(this,3);
    }

    /**
     * 关闭swipeToLoadLayout的刷新
     * @param swipeToLoadLayout
     */
    public void cLoading(final SwipeToLoadLayout swipeToLoadLayout) {
        runOnUiThread(new Runnable() {
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
        showView.setCustomView(new ImageView(this));
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
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if(loadView.isShowing()){
                                loadView.dismiss();
                            }
                            showView.setLabel(content);
                            if (null != context) {
                                showView.show();
                            } else {
                                Log.d("Activity", "已关闭");
                            }
                            new HideThread().start();
                        } catch (Exception e) {
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
     * 1秒之后关掉提示并结束当前Activity
     */
    public class CloseThread extends Thread {
        private String activityName = "";
        public CloseThread(String activityName) {
            this.activityName = activityName;
        }

        public void run() {
            try {
                sleep(1000);
                if(showView.isShowing()){
                    showView.dismiss();
                }
                if(activityName.indexOf(",") > 0){
                    String names[] = activityName.split(",");
                    for(int i = 0;i < names.length;i++){
                        MainApplication.finishActivity(names[i]);
                    }
                } else {
                    MainApplication.finishActivity(activityName);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 显示Dialog弹框
     * @param title
     * @param content
     */
    public void showDialog(String title,String content){
        final MaterialDialog mMaterialDialog = new MaterialDialog(this);
        mMaterialDialog.setTitle(title);
        mMaterialDialog.setMessage(content);
        mMaterialDialog.setPositiveButton("OK", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMaterialDialog.dismiss();
            }
        });
        mMaterialDialog.show();
    }

    /**
     * 将页面传过来的数据给网页
     * @return
     */
    public String getJson(){
        return "";
    }

}
