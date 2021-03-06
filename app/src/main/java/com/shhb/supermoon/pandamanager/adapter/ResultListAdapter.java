package com.shhb.supermoon.pandamanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shhb.supermoon.pandamanager.R;
import com.shhb.supermoon.pandamanager.model.CityInfo;

import java.util.List;


/**
 * Created by superMoon on 2017/7/31.
 */
public class ResultListAdapter extends BaseAdapter {
    private Context mContext;
    private List<CityInfo> mCities;

    public ResultListAdapter(Context mContext, List<CityInfo> mCities) {
        this.mCities = mCities;
        this.mContext = mContext;
    }

    public void changeData(List<CityInfo> list){
        if (mCities == null){
            mCities = list;
        }else{
            mCities.clear();
            mCities.addAll(list);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mCities == null ? 0 : mCities.size();
    }

    @Override
    public CityInfo getItem(int position) {
        return mCities == null ? null : mCities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ResultViewHolder holder;
        if (view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.city_search2_view, parent, false);
            holder = new ResultViewHolder();
            holder.name = (TextView) view.findViewById(R.id.tv_item_result_listview_name);
            view.setTag(holder);
        }else{
            holder = (ResultViewHolder) view.getTag();
        }
        holder.name.setText(mCities.get(position).getName());
        return view;
    }

    public static class ResultViewHolder{
        TextView name;
    }
}
