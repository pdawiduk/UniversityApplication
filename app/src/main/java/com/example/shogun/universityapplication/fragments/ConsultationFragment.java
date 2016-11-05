package com.example.shogun.universityapplication.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shogun.universityapplication.R;

import java.util.ArrayList;
import java.util.List;


public class ConsultationFragment extends Fragment {

    List<String> studentList = new ArrayList<>();
    public ConsultationFragment() {
        studentList.add("Jan kowalski");
        studentList.add("Adam Nowak");
        studentList.add("Malinowski Ikxinski");
        studentList.add("Mirek sprzedawca");
        studentList.add("Nergal");
        studentList.add("Behemoth");

    }

    public static ConsultationFragment newInstance(String param1, String param2) {
        ConsultationFragment fragment = new ConsultationFragment();
        Bundle args = new Bundle();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_consultation, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


}
