package com.studio.smarters.sysoft.Fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.studio.smarters.sysoft.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnquiryFragment extends Fragment {

    private AppCompatActivity main;
    private View root;
    private EditText nameText,emailText,phoneText,addrText,clgText,courseText,yearText;
    private TextView subjectList;
    private Button submit,choose;
    private DatabaseReference enquiryRef,subjectRef;
    private List subjects;
    private String[] names;
    private boolean[] checks;
    private ValueEventListener v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        main=(AppCompatActivity)getActivity();
        main.getSupportActionBar().setTitle("Enquiry");
        root=inflater.inflate(R.layout.fragment_enquiry, container, false);
        //Nav View
        NavigationView navigationView = main.findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_enquiry);
        enquiryRef= FirebaseDatabase.getInstance().getReference().child("enquiry");
        subjectRef=FirebaseDatabase.getInstance().getReference().child("course");
        nameText=root.findViewById(R.id.enquiry_name);
        emailText=root.findViewById(R.id.enquiry_email);
        phoneText=root.findViewById(R.id.enquiry_phone);
        addrText=root.findViewById(R.id.enquiry_addr);
        clgText=root.findViewById(R.id.enquiry_clg);
        courseText=root.findViewById(R.id.enquiry_course);
        yearText=root.findViewById(R.id.enquiry_year);
        subjectList=root.findViewById(R.id.subject_list);
        submit=root.findViewById(R.id.enquiry_submit);
        choose=root.findViewById(R.id.enquiry_choose);

        v=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                subjects=new ArrayList();
                for(DataSnapshot d:dataSnapshot.getChildren()){
                    subjects.add(d.child("name").getValue().toString());
                }
                names=new String[subjects.size()];
                checks=new boolean[subjects.size()];
                for(int i=0;i<subjects.size();i++){
                    names[i]=subjects.get(i).toString();
                    checks[i]=false;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        subjectRef.addValueEventListener(v);
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Choose Subjects")
                        .setMultiChoiceItems(names, checks, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i, boolean b) {

                            }
                        })
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String s="";
                                for(int j=0;j<checks.length;j++){
                                    if(checks[j]){
                                        s+=names[j];
                                        s+=" ";
                                    }
                                }
                                subjectList.setText(s);
                            }
                        })
                        .setCancelable(false)
                        .show();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=nameText.getText().toString();
                String email=emailText.getText().toString();
                String phone=phoneText.getText().toString();
                String addr=addrText.getText().toString();
                String clg=clgText.getText().toString();
                String stream=courseText.getText().toString();
                String year=yearText.getText().toString();
                String subject=subjectList.getText().toString();
                if(TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(addr) || TextUtils.isEmpty(clg) || TextUtils.isEmpty(stream) || TextUtils.isEmpty(year)){
                    Toast.makeText(main, "you must fill all the fields..", Toast.LENGTH_SHORT).show();
                }else{
                    if(TextUtils.isEmpty(subject)){
                        Toast.makeText(main, "you must select at least one subject...", Toast.LENGTH_SHORT).show();
                    }else{
                        Map m=new HashMap();
                        m.put("name",name);
                        m.put("email",email);
                        m.put("phone",phone);
                        m.put("address",addr);
                        m.put("college",clg);
                        m.put("stream",stream);
                        m.put("year",year);
                        m.put("subjects",subject);
                        enquiryRef.push().updateChildren(m).addOnSuccessListener(new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {
                                Toast.makeText(main, "Enquiry successfully submitted..", Toast.LENGTH_SHORT).show();
                                nameText.setText("");
                                emailText.setText("");
                                phoneText.setText("");
                                addrText.setText("");
                                clgText.setText("");
                                courseText.setText("");
                                yearText.setText("");
                                subjectList.setText("");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(main, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });
        // Inflate the layout for this fragment
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        subjectRef.removeEventListener(v);
    }
}
