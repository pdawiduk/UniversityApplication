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

import com.example.shogun.universityapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

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

        password = etPassword.getText().toString();
        indexNo = etIndexno.getText().toString();
        mail = etMail.getText().toString();


        final RegisterUser RegisterUser = new RegisterUser();


        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(true){
                    Toast.makeText(getContext(),"udalo sie", Toast.LENGTH_LONG).show();
                    RegisterUser.execute(indexNo,password,mail);

                }
                else{
                    Toast.makeText(getContext()," nie udalo sie", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private class RegisterUser extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String[] params) {
            // do above Server call here
            final MediaType JSON
                    = MediaType.parse("application/json; charset=utf-8");

            OkHttpClient client = new OkHttpClient();

            String json = null;
            try {
                json = new JSONObject().put("login","test").put("password","test").put("email","test@edu.p.lodz.pl").toString();
                System.out.println(json);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            RequestBody body = RequestBody.create(JSON, json);
            Request request = new Request.Builder()
                    .url("http://10.7.2.10:8080/api/register")
                    .post(body)
                    .build();
            Response response = null;
            try {
                response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "some message";
        }

        @Override
        protected void onPostExecute(String message) {
            System.out.println(message);
        }
    }
}
