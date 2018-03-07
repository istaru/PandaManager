package com.shhb.supermoon.pandamanager.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shhb.supermoon.pandamanager.R;
import com.shhb.supermoon.pandamanager.model.BannerInfo;
import com.shhb.supermoon.pandamanager.view.CustomViewPager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by superMoon on 2017/8/25.
 */

public class Fragment1Adapter extends BaseAdapter {
    private int type;
    /**
     * recycler的数据
     */
    private List<Map<String, Object>> listMap;
    private LoopViewPagerAdapter mPagerAdapter;

    public Fragment1Adapter(int type) {
        this.type = type;
        listMap = new ArrayList<>();
    }

    @Override
    public void addRecyclerData(List<Map<String, Object>> datas, int pageIndex, boolean flag) {
        this.flag = flag;
        if (pageIndex == 1) {
            listMap.clear();
        }
        listMap.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listMap.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (flag) {
            if (1 == type) {
                if (0 == position) {
                    return TYPE_0;
                } else {
                    return TYPE_1;
                }
            } else {
                return TYPE_1;
            }
        } else {
            return TYPE_2;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView;
        int type = getItemViewType(i);
        switch (type) {
            case TYPE_0:
                itemView = inflate(viewGroup, R.layout.banner_view);
                return new BannerHolder(itemView);
            case TYPE_1:
                itemView = inflate(viewGroup, R.layout.recycler_item);
                return new RecyclerHolder(itemView);
            case TYPE_2:
                itemView = inflate(viewGroup, R.layout.empty_view);
                return new EmptyHolder(itemView);
        }
        throw new IllegalArgumentException("Wrong type!");
    }

    private class BannerHolder extends RecyclerView.ViewHolder {
        CustomViewPager viewPager;
        ViewGroup indicators;

        /**
         * 获取到banner中的每一个View
         */
        public BannerHolder(View itemView) {
            super(itemView);
            viewPager = (CustomViewPager) itemView.findViewById(R.id.viewPager);
            viewPager.setScanScroll(true);
            indicators = (ViewGroup) itemView.findViewById(R.id.indicators);
        }
    }

    private class RecyclerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textRob;
        ImageView upDown;
        LinearLayout item, down, delete;
        RelativeLayout openClose;

        /**
         * 获取到recycler中的每一个View
         */
        public RecyclerHolder(View itemView) {
            super(itemView);
            item = (LinearLayout) itemView.findViewById(R.id.item_mian);
            item.setOnClickListener(this);
            textRob = (TextView) itemView.findViewById(R.id.text_rob);
            textRob.setOnClickListener(this);
            upDown = (ImageView) itemView.findViewById(R.id.up_down);
            upDown.setTag(0);
            openClose = (RelativeLayout) itemView.findViewById(R.id.open_or_close);
            openClose.setOnClickListener(this);
            down = (LinearLayout) itemView.findViewById(R.id.down);
            delete = (LinearLayout) itemView.findViewById(R.id.delete);
            delete.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.item_mian:
                    onClickListener.onClick(view, getPosition(), listMap);
                    break;
                case R.id.text_rob:
                    onClickListener.onClick(view, getPosition(), listMap);
                    break;
                case R.id.open_or_close:
                    if ((int) upDown.getTag() == 0) {
                        upDown.setImageResource(R.mipmap.n_ico_up);
                        down.setVisibility(View.VISIBLE);
                        upDown.setTag(1);
                    } else {
                        upDown.setImageResource(R.mipmap.n_ico_down);
                        down.setVisibility(View.GONE);
                        upDown.setTag(0);
                    }
                    break;
                case R.id.delete:
                    int pos = getAdapterPosition();
                    listMap.remove(pos);
                    notifyItemRemoved(pos);
                    break;
            }
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        int type = getItemViewType(position);
        switch (type) {
            case TYPE_0:
                onBindBannerHolder((BannerHolder) viewHolder, position);
                break;
            case TYPE_1:
                onBindRecyclerHolder((RecyclerHolder) viewHolder, position);
                break;
        }
    }

    /**
     * 将数据填充到banner上
     *
     * @param viewHolder
     * @param position
     */
    private void onBindBannerHolder(BannerHolder viewHolder, int position) {
        List<BannerInfo> bannerInfos = (List<BannerInfo>) listMap.get(position).get("banners");
        if (viewHolder.viewPager.getAdapter() == null) {
            mPagerAdapter = new LoopViewPagerAdapter(viewHolder.viewPager, viewHolder.indicators);
            viewHolder.viewPager.setAdapter(mPagerAdapter);
            viewHolder.viewPager.addOnPageChangeListener(mPagerAdapter);
            mPagerAdapter.setList(bannerInfos);
        } else {
            mPagerAdapter.setList(bannerInfos);
        }
    }

    /**
     * 将数据填充到recycler上
     *
     * @param viewHolder
     * @param position
     */
    private void onBindRecyclerHolder(RecyclerHolder viewHolder, int position) {
//        viewHolder.credit.setText(listMap.get(position).get("creditRating") + "");
//        viewHolder.quota.setText(listMap.get(position).get("creditLine") + "");
//        viewHolder.privilege.setText(listMap.get(position).get("privilege") + "个");
//        viewHolder.success.setText(listMap.get(position).get("successRate") + "");
    }

}
