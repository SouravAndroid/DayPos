package com.daypos.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.daypos.R;

import java.util.ArrayList;

public class CustomSpinnerAdapter extends BaseAdapter {

    private ArrayList<String> alertList;
    private LayoutInflater mInflater;

    public CustomSpinnerAdapter(Context context, ArrayList<String> results) {
        alertList = results;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return alertList.size();
    }

    @Override
    public Object getItem(int position) {
        return alertList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.spinner_item, null);
            holder = new ViewHolder();
            holder.spinnerValue = convertView.findViewById(R.id.tv_item);

            convertView.setTag(holder);


        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.spinnerValue.setText(alertList.get(position));
        return convertView;
    }




    static class ViewHolder {
        TextView spinnerValue; //spinner name
    }
}
