package com.shhb.supermoon.pandamanager.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.shhb.supermoon.pandamanager.R;
import com.shhb.supermoon.pandamanager.activity.CityActivity;
import com.shhb.supermoon.pandamanager.adapter.BaseAdapter;
import com.shhb.supermoon.pandamanager.adapter.Fragment1Adapter;
import com.shhb.supermoon.pandamanager.model.BannerInfo;
import com.shhb.supermoon.pandamanager.tools.BaseTools;
import com.shhb.supermoon.pandamanager.tools.Constants;
import com.shhb.supermoon.pandamanager.tools.OkHttpUtils;
import com.shhb.supermoon.pandamanager.tools.PrefShared;
import com.shhb.supermoon.pandamanager.view.DividerItemDecoration;
import com.shhb.supermoon.pandamanager.view.SwipeItemLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.app.Activity.RESULT_OK;

/**
 * Created by superMoon on 2017/8/24.
 */

public class Fragment1 extends BaseFragment implements BaseAdapter.OnClickListener, View.OnClickListener {
    private RecyclerView recyclerView;
    private Fragment1Adapter mAdapter;

    public static Fragment1 newInstance() {
        Fragment1 fragment = new Fragment1();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new Fragment1Adapter(1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.t_recycler_view, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeToLoadLayout = (SwipeToLoadLayout) view.findViewById(R.id.swipeToLoadLayout);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);

        onBack = (TextView) view.findViewById(R.id.on_back);
        onBack.setOnClickListener(this);
        String location = PrefShared.getString(context,"position");
        if(null == location){
            location = context.getString(R.string.cp_located_failed);
        }
        onBack.setText(location);
        Drawable drawable = context.getResources().getDrawable(R.mipmap.location);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        onBack.setCompoundDrawables(drawable, null, null, null);

        onTitle = (TextView) view.findViewById(R.id.on_title);
        onTitle.setText("熊猫帮");

        recyclerView = (RecyclerView) view.findViewById(R.id.swipe_target);
        GridLayoutManager layoutManager = new GridLayoutManager(context, 1, LinearLayoutManager.VERTICAL, false);
        recyclerView.addItemDecoration(
                new DividerItemDecoration(context, DividerItemDecoration.BOTH_SET, 12, ContextCompat.getColor(context, R.color.webBg))
        );
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(getContext()));
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnClickListener(this);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                RecyclerView.LayoutManager mLayoutManager = recyclerView.getLayoutManager();
                int lastVisibleItem = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
                int totalItemCount = mLayoutManager.getItemCount();
                // dy>0 表示向下滑动
                if (lastVisibleItem >= totalItemCount - 4 && dy > 0) {//lastVisibleItem >= totalItemCount - 4 表示剩下4个item自动加载
                    if (!swipeToLoadLayout.isLoadingMore()) {
                        swipeToLoadLayout.setLoadingMore(true);
                    }
                }
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (!ViewCompat.canScrollVertically(recyclerView, 1)) {
                        swipeToLoadLayout.setLoadingMore(true);
                    }
                }
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (lifeCycle == 1) {
            swipeToLoadLayout.post(new Runnable() {
                @Override
                public void run() {
                    swipeToLoadLayout.setRefreshing(true);
                    lifeCycle = 2;
                }
            });
        }
    }


    @Override
    public void onLoadMore() {
        findByData();
    }

    @Override
    public void onRefresh() {
        findByData();
    }

    /**
     * 查询首页信息
     */
    private void findByData() {
        final List<Map<String, Object>> listMap = new ArrayList<>();
        final Map<String, Object> mapBanners = new HashMap<>();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msys", "1");
        String parameter = BaseTools.encodeJson(jsonObject.toString());
        new OkHttpUtils(20).postEnqueue(Constants.FIND_BY_HOME, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                cLoading(swipeToLoadLayout);
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        listMap.add(mapBanners);
                        mAdapter.addRecyclerData(listMap, mPageIndex,false);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    cLoading(swipeToLoadLayout);
                    JSONObject jsonObject = BaseTools.jsonObject(context, response, "查询首页信息");
                    JSONObject dataObject = jsonObject.getJSONObject("data");
                    JSONArray jsonArray = dataObject.getJSONArray("banner");
                    final List<BannerInfo> bannerList = new ArrayList<>();
                    for (int i = 0; i < jsonArray.size(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        BannerInfo banner = new BannerInfo();
                        banner.setAvatar(jsonObject.getString("icon_url"));
                        banner.setName(jsonObject.getString("link"));
                        bannerList.add(banner);
                    }
                    mapBanners.put("banners", bannerList);
                    listMap.add(mapBanners);
                    for (int i = 0; i < 30; i++) {
                        Map<String, Object> mapHoms = new HashMap<>();
                        listMap.add(mapHoms);
                    }
                    recyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.addRecyclerData(listMap, mPageIndex,true);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, parameter);
    }

    @Override
    public void onClick(View view, int position, List<Map<String, Object>> listMap) {
        switch (view.getId()) {
            case R.id.text_rob:

                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.on_back:
                Intent intent = new Intent(context, CityActivity.class);
                startActivityForResult(intent, Constants.REQUEST_CODE_PICK_CITY);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_CODE_PICK_CITY && resultCode == RESULT_OK) {
            if (data != null) {
                String city = data.getStringExtra(CityActivity.KEY_PICKED_CITY);
                onBack.setText(city);
            }
        }
    }
}
