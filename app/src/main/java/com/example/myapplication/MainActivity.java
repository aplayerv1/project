package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {
    boolean isTablet = false;
    ArrayList<Data> arr = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);


        View convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_main,null);
        SearchView sv = convertView.findViewById(R.id.searchview);
        Bundle bdl = new Bundle();
        DetailsFragment fragment = new DetailsFragment();

        fragment.setArguments(bdl);
        Boolean str = checkIsTablet();
        Log.d("Respond:", "<Tablet>"+ isTablet + " "+str);



        if (isTablet == false){
            Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
            intent.putExtras(bdl);
            startActivity(intent);

        }else{
            androidx.fragment.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.layout, fragment);
            transaction.commit();
        }


    }
    private boolean checkIsTablet() {
        Display display = ((Activity) this).getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        float widthInches = metrics.widthPixels / metrics.xdpi;
        float heightInches = metrics.heightPixels / metrics.ydpi;
        double diagonalInches = Math.sqrt(Math.pow(widthInches, 2) + Math.pow(heightInches, 2));
        Log.d("Results",">>>   " +display+" "+diagonalInches);
        if (diagonalInches >=10.0) {
            isTablet = true;
        }

        return isTablet;
    }

}