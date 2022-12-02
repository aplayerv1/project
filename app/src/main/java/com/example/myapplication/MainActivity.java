package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {
    boolean isTablet = false;
    ArrayList<Data> arr = new ArrayList<Data>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);

        Base adapter = new Base(this,arr);

        Log.d("Respond:", "<Main>"+arr+adapter);

        Bundle bdl = new Bundle();
        DetailsFragment fragment = new DetailsFragment();
        fragment.setArguments(bdl);
        Boolean str = checkIsTablet();
        Log.d("Respond:", "<Tablet>"+ isTablet + " "+str);


        if (isTablet == false){

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
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
        if (diagonalInches >=6.0) {
            isTablet = true;
        }

        return isTablet;
    }

}