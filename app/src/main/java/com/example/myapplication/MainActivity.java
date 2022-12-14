package com.example.myapplication;

import static java.lang.Thread.sleep;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.zip.Inflater;

/**
 *
 */
public class MainActivity extends AppCompatActivity {
    boolean isTablet = false;
    ArrayList<Data> arr = new ArrayList<>();
    String name;
    String s1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
        // Pop up for name for SharedPreferences

        try {
            sleep(150);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        Bundle bdl = new Bundle();
        DetailsFragment fragment = new DetailsFragment();

        fragment.setArguments(bdl);
        Boolean str = checkIsTablet();
        Log.d("Respond:", "<Tablet>"+ s1 + " "+str);


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
        if (diagonalInches >=7.0) {
            isTablet = true;
        }

        return isTablet;
    }

    /**
     *
     */
    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        Name name = new Name();
        name.setName(sh.getString("name", ""));

    }

    /**
     *
     */
    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        Name name = new Name();

        myEdit.putString("name", name.getName());

        myEdit.apply();
    }
}