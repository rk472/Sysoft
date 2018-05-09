package com.studio.smarters.sysoft.Fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.studio.smarters.sysoft.R;

public class InternshipFragment extends Fragment {
    private AppCompatActivity main;
    private View root;
    private StorageReference internRef;
    private RelativeLayout progress;
    private Button moduleButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        main = (AppCompatActivity) getActivity();
        main.getSupportActionBar().setTitle("Internship");
        root = inflater.inflate(R.layout.fragment_internship, container, false);
        //Nav View
        NavigationView navigationView = (NavigationView) main.findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_internship);
        progress=root.findViewById(R.id.intern_progress);
        moduleButton=root.findViewById(R.id.module_button);
        internRef= FirebaseStorage.getInstance().getReference().child("internship").child("module.pdf");
        moduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress.setVisibility(View.VISIBLE);
                internRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(uri, "application/pdf");
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        getActivity().startActivity(intent);
                        progress.setVisibility(View.GONE);
                    }
                });
            }
        });

        // Inflate the layout for this fragment
        return root;
    }
}
