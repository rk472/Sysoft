package com.studio.smarters.sysoft.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.studio.smarters.sysoft.POJO.Course;
import com.studio.smarters.sysoft.R;
import com.studio.smarters.sysoft.SyllabusActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class CoursesFragment extends Fragment {
    private AppCompatActivity main;
    private View root;
    private RecyclerView courseList;
    private DatabaseReference courseRef;
    private RelativeLayout progress;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        main=(AppCompatActivity)getActivity();
        main.getSupportActionBar().setTitle("Courses Offered");
        root=inflater.inflate(R.layout.fragment_courses, container, false);
        //Nav View
        NavigationView navigationView =  main.findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_courses);
        progress=root.findViewById(R.id.course_progress);
        courseList=root.findViewById(R.id.course_list);
        courseRef= FirebaseDatabase.getInstance().getReference().child("course");
        courseRef.keepSynced(true);
        FirebaseRecyclerAdapter<Course,CourseViewHolder> f=new FirebaseRecyclerAdapter<Course, CourseViewHolder>(
                Course.class,
                R.layout.course_row,
                CourseViewHolder.class,
                courseRef
        ) {
            @Override
            protected void populateViewHolder(CourseViewHolder viewHolder, final Course model, int position) {
                progress.setVisibility(View.GONE);
                viewHolder.setDp(getActivity(),model.getImage());
                viewHolder.setDuration(model.getDuration());
                viewHolder.setFee(model.getFee());
                viewHolder.setName(model.getName());
                viewHolder.syllabus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(model.getDoc().equals("none")){
                            Toast.makeText(main, "Syllabus is being modified..try again later...", Toast.LENGTH_SHORT).show();
                        }else {
                            Intent i = new Intent(getActivity(), SyllabusActivity.class);
                            i.putExtra("url", model.getDoc());
                            startActivity(i);
                        }
                    }
                });
            }
        };
        courseList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false));
        courseList.setHasFixedSize(true);
        courseList.setAdapter(f);

        // Inflate the layout for this fragment
        return root;
    }
    public static class CourseViewHolder extends RecyclerView.ViewHolder{
        ImageView dp;
        TextView name,duration,fee;
        Button syllabus;
        public CourseViewHolder(View itemView) {
            super(itemView);
            dp=itemView.findViewById(R.id.course_row_dp);
            name=itemView.findViewById(R.id.course_row_name);
            duration=itemView.findViewById(R.id.course_row_duration);
            fee=itemView.findViewById(R.id.course_row_fee);
            syllabus=itemView.findViewById(R.id.course_row_syllabus);
        }
        void setName(String s){
            name.setText(s);
        }
        void setDuration(String s){
            duration.setText(s);
        }
        void setFee(String s){
            fee.setText(s);
        }
        void setDp(final Context ctx, final String s){
            Picasso.with(ctx).load(s).networkPolicy(NetworkPolicy.OFFLINE).placeholder(R.drawable.dp)
                    .into(dp, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {
                            Picasso.with(ctx).load(s).placeholder(R.drawable.dp).into(dp);
                        }
                    });
        }
    }

}
