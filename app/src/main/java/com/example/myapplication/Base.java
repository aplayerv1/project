package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Base extends BaseAdapter {

    private Context context;
    private ListView lv;
    private TextView tv;
    private ArrayList<Data> arr;


    public Base(Context context, ArrayList<Data> data) {
        this.context = context;
        this.arr = data;
        Log.d("Respond","Here");
    }

    @Override
    public int getCount() {
        return arr == null ? 0 : arr.size();
    }

    @Override
    public Object getItem(int i) {
        return arr.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(context).inflate(R.layout.row, parent, false);

        tv = convertView.findViewById(R.id.rowTextView11);
        Log.d("Respond:","Base "+arr.get(i).getName());
        tv.setText(arr.get(i).getName());

        notifyDataSetChanged();
        return convertView;

    }
}