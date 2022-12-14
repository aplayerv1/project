package com.example.myapplication;

import static java.lang.Thread.sleep;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Base extends BaseAdapter implements Filterable {
    private Context context;
    private ListView lv;
    private TextView tv;
    public ArrayList<Data> arr;
    public ArrayList<Data> allData;

    public Base(Context context, ArrayList<Data> data, ListView lv) {
        this.context = context;
        this.arr = data;
        this.allData = data;
        this.lv = lv;

        // Log.d("TAG", "--> All Data Set " + allData.size());
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
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();

    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.row, parent, false);

        tv = convertView.findViewById(R.id.rowTextView11);
        tv.setText(Html.fromHtml(""+arr.get(i).getName()+"<div>"+arr.get(i).getUrl()+"</div>"));

        Base.this.notifyDataSetChanged();
        lv.invalidateViews();
        return convertView;
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            jsonRead jr = new jsonRead(context,lv);


            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String s = constraint.toString();
                FilterResults filterResults = new FilterResults();

                ArrayList<Data> d = new ArrayList<Data>();

                if(s.isEmpty()) {
                    filterResults.values = allData;
                    filterResults.count = allData.size();
                     Log.d("TAG", "Empty String "+ allData.size());
                } else {
                    for(Data data : allData){
                        if (data.getName().toLowerCase().contains(s)) {
                            d.add(data);
                        }
                    }
                    filterResults.values = d;
                    filterResults.count = d.size();
                     Log.d("TAG", "All Data " + allData.size());
                     Log.d("TAG", "Filtered Data "+ d.size());
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                 Log.d("TAG", "Count: " + results.count);
                if (results.count != 0) {
                    arr.clear();
                    arr = (ArrayList<Data>) results.values;

                } else {
                    try {
                        sleep(350);
                        Sql sql = new Sql(context,8);
                        allData=sql.getTasks();
                        arr = new ArrayList<Data>(sql.getTasks());
                        Log.d("TAG", "ARR has " + arr.size());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                Base.this.notifyDataSetChanged();
                lv.invalidateViews();
            }
        };
    }
}