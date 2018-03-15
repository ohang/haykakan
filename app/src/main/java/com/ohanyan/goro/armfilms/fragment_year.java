package com.ohanyan.goro.armfilms;

/**
 * Created by Goro on 08.03.2018.
 */
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.io.IOException;
import java.util.ArrayList;

public class fragment_year extends Fragment {

    public InterstitialAd mInterstitialAd;
    DataBaseHelper myDbHelper;
    GridAdapter adapter;


    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        String strtext=getArguments().getString("message");

        View rootview =inflater.inflate(R.layout.fragment_foo, parent, false);

        mInterstitialAd = new InterstitialAd(getActivity());
        mInterstitialAd.setAdUnitId("ca-app-pub-9939890174467872/9550634079");


        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });


        myDbHelper = new DataBaseHelper(getActivity());

        try {

            myDbHelper.createDataBase();

        } catch (IOException ioe) {

            throw new Error("Unable to create database");

        }

        try {

            myDbHelper.openDataBase();

        } catch (SQLException sqle) {

            throw sqle;

        }

        ArrayList<String> names=new ArrayList<>();
        ArrayList<Integer> images=new ArrayList<>();

        //String[] names= new String[3];
        SQLiteDatabase mydb1 = myDbHelper.getReadableDatabase();
        Cursor info = mydb1.rawQuery("SELECT * FROM film WHERE year = " + strtext + "        " , null);
        while(info.moveToNext()){

            names.add(info.getString(1));
            images.add(getResources().getIdentifier((info.getString(info.getColumnIndex("img"))), "drawable", getActivity().getPackageName()));


        }

        String[] stockArr = new String[names.size()];
        stockArr = names.toArray(stockArr);

        Integer[] image = new Integer[images.size()];
        image = images.toArray(image);



        adapter= new GridAdapter(getActivity().getApplicationContext(),stockArr,image);

        GridView grid = (GridView) rootview.findViewById(R.id.gridview1);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener( new AdapterView.OnItemClickListener(){

                                         @Override
                                         public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                                             Intent myIntent = new Intent(getActivity(), film_activity.class);
                                             String film_name=(String) adapter.getItem(i);
                                             myIntent.putExtra("key", film_name); //Optional parameters
                                             getActivity().startActivity(myIntent);

                                             if (mInterstitialAd.isLoaded()) {
                                                 mInterstitialAd.show();
                                             }
                                         }
                                     }

        );

        return rootview;

    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
    }


}