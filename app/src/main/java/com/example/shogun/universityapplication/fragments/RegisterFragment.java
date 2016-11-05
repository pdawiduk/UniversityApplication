package com.example.shogun.universityapplication.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shogun.universityapplication.R;

import retrofit2.http.POST;

public class RegisterFragment extends Fragment {
    EditText etIndexno;
    EditText etMail;
    EditText etPassword;
    Button btnConfirm;

    private static String password;
    private static String mail;
    private static String indexNo;


    public RegisterFragment() {
        // Required empty public constructor
    }

    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
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
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        etIndexno = (EditText) view.findViewById(R.id.etIndexNo);
        etMail = (EditText) view.findViewById(R.id.etMailForm);
        etPassword = (EditText) view.findViewById(R.id.etPassword);
        btnConfirm = (Button) view.findViewById(R.id.btnOK);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        password = etPassword.getText().toString();
        indexNo = etIndexno.getText().toString();
        mail = etMail.getText().toString();

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((!password.isEmpty())&&(!mail.isEmpty())&&(!indexNo.isEmpty())){
                    Toast.makeText(getContext(),"udalo sie", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getContext()," nie udalo sie", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
//    private void registerUser(){
//        @POST
//
//    }
}
