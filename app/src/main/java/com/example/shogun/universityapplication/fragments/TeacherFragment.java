package com.example.shogun.universityapplication.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.shogun.universityapplication.R;
import com.example.shogun.universityapplication.adapters.ConsultationsAdapter;

import java.util.ArrayList;


public class TeacherFragment extends Fragment {
    ArrayList<String> cosnultationList = new ArrayList<>();
    RecyclerView recyclerView;
    public TeacherFragment() {

        cosnultationList.add("2.11.16");
        cosnultationList.add("5.11.16");
        cosnultationList.add("9.11.16");
        cosnultationList.add("12.11.16");
        cosnultationList.add("19.11.16");
        cosnultationList.add("22.11.16");
    }


    public static TeacherFragment newInstance(String param1, String param2) {
        TeacherFragment fragment = new TeacherFragment();
        Bundle args = new Bundle();
 ;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_teacher, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rvConsultationsList);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ConsultationsAdapter adapter = new ConsultationsAdapter(getContext(),cosnultationList);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


}
