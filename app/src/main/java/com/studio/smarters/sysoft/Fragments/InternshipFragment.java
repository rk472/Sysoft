package com.studio.smarters.sysoft.Fragments;


import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.studio.smarters.sysoft.R;

public class InternshipFragment extends Fragment {
    private AppCompatActivity main;
    private View root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        main = (AppCompatActivity) getActivity();
        main.getSupportActionBar().setTitle("Internship");
        root = inflater.inflate(R.layout.fragment_internship, container, false);
        //Nav View
        NavigationView navigationView = (NavigationView) main.findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_internship);

        // Inflate the layout for this fragment
        return root;
    }
}
