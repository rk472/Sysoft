package com.studio.smarters.sysoft.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.studio.smarters.sysoft.POJO.Faculty;
import com.studio.smarters.sysoft.R;

import de.hdodenhof.circleimageview.CircleImageView;


public class FacultiesFragment extends Fragment {

    private AppCompatActivity main;
    private View root;
    private RecyclerView facultyList;
    private DatabaseReference facultyRef;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        main=(AppCompatActivity)getActivity();
        main.getSupportActionBar().setTitle("Faculties");
        root= inflater.inflate(R.layout.fragment_faculties, container, false);
        //Nav View
        NavigationView navigationView = (NavigationView) main.findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_faculties);
        facultyList=root.findViewById(R.id.faculty_list);
        facultyRef= FirebaseDatabase.getInstance().getReference().child("faculty");
        FirebaseRecyclerAdapter<Faculty,FacultyViewHolder> f=new FirebaseRecyclerAdapter<Faculty, FacultyViewHolder>(
                Faculty.class,
                R.layout.faculty_row,
                FacultyViewHolder.class,
                facultyRef
        ) {
            @Override
            protected void populateViewHolder(FacultyViewHolder viewHolder, Faculty model, int position) {
                viewHolder.setDesc(model.getDesc());
                viewHolder.setDp(getActivity(),model.getImage());
                viewHolder.setName(model.getName());
            }
        };
        facultyList.setAdapter(f);
        facultyList.setHasFixedSize(true);
        facultyList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false));
        // Inflate the layout for this fragment
        return root;
    }
    public static class FacultyViewHolder extends RecyclerView.ViewHolder{
        CircleImageView dp;
        TextView name,desc;
        public FacultyViewHolder(View itemView) {
            super(itemView);
            dp=itemView.findViewById(R.id.faculty_row_dp);
            name=itemView.findViewById(R.id.faculty_row_name);
            desc=itemView.findViewById(R.id.faculty_row_desc);
        }
        void setName(String s){
            name.setText(s);
        }
        void setDesc(String s){
            desc.setText(s);
        }
        void setDp(Context c,String s){
            Picasso.with(c).load(s).into(dp);
        }
    }

}
