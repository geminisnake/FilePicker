package com.alienleeh.filepicker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by AlienLeeH on 2016/2/15..Hour:01
 * Email:alienleeh@foxmail.com
 * Description:
 */
public class MyBaseAdapter extends BaseAdapter{
    Context context;
    List<?> list;
    protected LayoutInflater inflater;
    public MyBaseAdapter(Context context, List<?> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list == null? 0:list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        return initView(position,convertView,viewGroup);
    }

    public View initView(int position, View convertView, ViewGroup viewGroup){
        return null;
    }

}
