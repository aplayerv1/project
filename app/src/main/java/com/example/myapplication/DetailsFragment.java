package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DetailsFragment extends Fragment {

    ArrayList<Data> arr;
    ArrayList<Data> allData;
    Sql sql = new Sql(getContext(),2);

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "Name";
    private static final String ARG_PARAM2 = "Height";
    private static final String ARG_PARAM3 = "Mass";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mParam3;
    public DetailsFragment() {
        // Required empty public constructor
    }

    public static DetailsFragment newInstance(String param1, String param2,String param3) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment,container,false);
        Resources res = getResources();
        ListView lv = container.findViewById(R.id.ListView1);
        SearchView et = view.findViewById(R.id.edittext);

        Context context = getContext();
        jsonRead jr = new jsonRead(context,lv);

        Sql sql = new Sql(context,8);
        arr = sql.getTasks();

        if(sql.getTasks().isEmpty()){
            jr.execute();
        }else{
            if(sql.getTasks().size()<jr.getData().size()){
                Log.d("SQL","NOT EQUAL "+sql.getTasks().size()+" "+arr.size());

            }else{
                jr.execute();
                jr.getData();
                Log.d("SQL","NOT EMPTY "+jr.getData().size());}
        }

        Base base = new Base(context,arr, lv);
        lv.setAdapter(base);
        base.notifyDataSetChanged();
        lv.invalidateViews();

        lv.setOnItemLongClickListener((adapterView, view1, i, l) -> {
            new AlertDialog.Builder(getContext()).setTitle(R.string.mesg)
                    .setMessage(String.format(res.getString(R.string.row), i+1))
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            i+=1;

                        }
                    }).setNegativeButton(R.string.no,null).show();
            return false;
        });
        lv.setOnItemClickListener((adapterView, view12, i, l) -> {
            String url = arr.get(i).getUrl().toString();
            Intent ii = new Intent(Intent.ACTION_VIEW);
            ii.setData(Uri.parse(url));
            startActivity(ii);
            Log.d("ERROR"," "+arr.get(i).getUrl());

        });
        et.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                    base.getFilter().filter(s);
                    base.notifyDataSetChanged();
                    lv.invalidateViews();

                return true;
            }
        });

        return view;


    }

}