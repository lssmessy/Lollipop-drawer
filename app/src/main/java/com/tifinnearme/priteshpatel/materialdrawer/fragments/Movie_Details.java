package com.tifinnearme.priteshpatel.materialdrawer.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tifinnearme.priteshpatel.materialdrawer.R;

/**
 * Created by PRITESH on 15-04-2015.
 */
public class Movie_Details extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.movie_details,container,false);

        return view;
    }
}
