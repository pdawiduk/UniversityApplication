package com.example.shogun.universityapplication.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shogun.universityapplication.MainActivity;
import com.example.shogun.universityapplication.R;

public class LoginFragment extends Fragment {

    private String URL = "10.0.2.2:8081";

    EditText etMail;
    EditText etPassword;
    Button btnLogin;

    String mail;
    String password;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        ((MainActivity) getActivity()).getActionBar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        etMail= (EditText) view.findViewById(R.id.etMailForm);
        etPassword = (EditText) view.findViewById(R.id.etPassword);
        btnLogin = (Button) view.findViewById(R.id.btnOK);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onResume() {
        super.onResume();



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mail = etMail.getText().toString();
                password = etPassword.getText().toString();

                if(mail.contains("@edu")){
                    StudentFragment studentFragment = new StudentFragment();
                    ((MainActivity) getActivity()).getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragment_container, studentFragment).commit();
                }

                else if(mail.contains("@p.lodz.pl")){
                    TeacherFragment teacherFragment = new TeacherFragment();
                    ((MainActivity) getActivity()).getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragment_container, teacherFragment).commit();
                }

                else{
                    Toast.makeText(getContext(), "nie poprawny email", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
