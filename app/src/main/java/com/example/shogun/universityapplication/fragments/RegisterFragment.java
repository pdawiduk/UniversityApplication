package com.example.shogun.universityapplication.fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shogun.universityapplication.MainActivity;
import com.example.shogun.universityapplication.R;
import com.example.shogun.universityapplication.requests.RegisterUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterFragment extends Fragment {
    EditText etIndexno;
    EditText etMail;
    EditText etPassword;
    Button btnConfirm;

    String password;
    String mail;
    String indexNo;


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
    public void onResume() {
        super.onResume();


        btnConfirm.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                password = etPassword.getText().toString();
                indexNo = etIndexno.getText().toString();
                mail = etMail.getText().toString();
                RegisterUser registerUser = new RegisterUser();
                String response = null;


                try {
                    response = registerUser.execute(indexNo, password, mail).get();
                    System.out.println(response);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
                }

                if (response.isEmpty()) {
                    LoginFragment loginFragment = new LoginFragment();
                    ((MainActivity) getActivity()).getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragment_container, loginFragment).commit();
                    Toast.makeText(getContext(), "Account created. Please log in", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "Wronga data. Try again", Toast.LENGTH_LONG).show();
                }

            }


        });
    }

}
