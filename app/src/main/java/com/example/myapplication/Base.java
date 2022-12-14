package com.example.myapplication;

import static com.example.myapplication.R.*;
import static java.lang.Thread.sleep;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 *
 */
public class Base extends BaseAdapter implements Filterable {
    public Boolean boo;
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
        this.boo=false;
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
        convertView = LayoutInflater.from(context).inflate(layout.row, parent, false);

        tv = convertView.findViewById(id.rowTextView11);

        tv.setText(Html.fromHtml("" + arr.get(i).getName() + "<div>" + arr.get(i).getUrl() + "</div>"));

        if(arr.get(i).getFav()){
            convertView.setBackgroundColor(Color.GREEN);
        }
        Base.this.notifyDataSetChanged();
        lv.invalidateViews();
        return convertView;
    }
    public void setBoo(Boolean o){
        this.boo=o;
    }

    /**
     * @return
     */
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

            /**
             * @param constraint
             * @param results
             */
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                 Log.d("TAG", "Count: " + results.count);
                if (results.count != 0) {
                    arr.clear();
                    arr = (ArrayList<Data>) results.values;

                } else {
                    try {
                        sleep(100);
                        Sql sql = new Sql(context,8);

                        arr = new ArrayList<Data>(sql.getTasks());



                        if(boo==true){
                           allData=sql.getFav();
                           Log.d("TAG", "CHECKED " + arr.size());

                       }else {
                           allData = sql.getTasks();
                           Log.d("TAG", "REGULAR " + arr.size());

                       }
                        notifyDataSetChanged();
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