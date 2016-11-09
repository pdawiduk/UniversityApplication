package com.example.shogun.universityapplication.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.shogun.universityapplication.MainActivity;
import com.example.shogun.universityapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.ContentValues.TAG;


public class MainFragment extends Fragment {

    @BindView(R.id.btnLogin)
    Button btnLogin;

    @BindView(R.id.btnRegister)
    Button btnRegister;

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
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
        View view = inflater.inflate(R.layout.content_main, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick(R.id.btnLogin)
    void loginUser() {
        Log.d(TAG, "loginUser: click loginBUtton");
        LoginFragment loginFragment = new LoginFragment();
        ((MainActivity) getActivity()).getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragment_container, loginFragment).commit();
    }

    @OnClick(R.id.btnRegister)
    void registerUser() {
        Log.d(TAG, "RegisterUser: click registerButton");
        RegisterFragment registerFragment = new RegisterFragment();
        ((MainActivity) getActivity()).getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragment_container, registerFragment).commit();

    }
}
