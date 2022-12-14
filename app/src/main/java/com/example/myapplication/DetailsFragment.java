package com.example.myapplication;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 *
 */
public class DetailsFragment extends Fragment {

    ArrayList<Data> arr;
    ArrayList<Data> allData;
    Sql sql;
    String name;

    public DetailsFragment() {

    }

    public static DetailsFragment newInstance(String param1, String param2,String param3) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment,container,false);
        Resources res = getResources();
        this.setHasOptionsMenu(true);
        Name name = new Name();
        final EditText txtUrl = new EditText(getContext());
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MySharedPref",MODE_PRIVATE);
        name.setName(sharedPreferences.getString("name",""));
        if(name.getName()==null){
        new AlertDialog.Builder(getContext())
                    .setTitle("Welcome What is Your Name?")
                    .setView(txtUrl)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            name.setName(txtUrl.getText().toString());

                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                        }
                    })
                    .show();}



        ListView lv = container.findViewById(R.id.ListView1);
        SearchView et = view.findViewById(R.id.edittext);
        Switch sw = view.findViewById(R.id.switch2);
        Button btn = view.findViewById(R.id.button);
        Button btn2 = view.findViewById(R.id.button2);



        Context context = getContext();
        jsonRead jr = new jsonRead(context,lv);
//        public AlertDialog.Builder setTitle();
        sql = new Sql(context,8);



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


        arr = sql.getTasks();

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
                            sql.addFav(i,true);
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
                if(sw.isChecked()){
                    base.setBoo(true);
                }else {
                    base.setBoo(false);
                }
                base.getFilter().filter(s);
                base.notifyDataSetChanged();
                lv.invalidateViews();
                return true;
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar snackbar = Snackbar
                        .make(getView(), res.getString(R.string.snack), Snackbar.LENGTH_LONG);
                snackbar.show();

                arr.clear();
                arr = new ArrayList<Data>(sql.getFav());
                Base base1 = new Base(context,arr,lv);
                lv.setAdapter(base1);
                container.refreshDrawableState();
                base1.notifyDataSetChanged();
                lv.invalidateViews();
                container.requestLayout();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arr.clear();
                arr = new ArrayList<Data>(sql.getTasks());
                Base base1 = new Base(context,arr,lv);
                lv.setAdapter(base1);
                container.refreshDrawableState();
                base1.notifyDataSetChanged();
                lv.invalidateViews();
                container.requestLayout();
            }
        });

        return view;

    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.menu, menu);
        return;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Context context = getContext();

        switch (id) {
            case R.id.about:
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("BBC READER");
                builder.setMessage("1. Hold Clink On News Makes it Favorite, will display green\n" +
                                "2. Clicking the news pops Browser\n " +
                                "3. Search The News\n" +
                                "4. When Switch is on search only through favorites\n" +
                                "5. Button Show Favorites shows favorites\n" +
                                "6. Button refresh refresh\n")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                builder.show();
                return true;
        }
        return super.onOptionsItemSelected(item); // important line

    }

}