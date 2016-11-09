package com.example.shogun.universityapplication.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.shogun.universityapplication.MainActivity;
import com.example.shogun.universityapplication.R;
import com.example.shogun.universityapplication.domain.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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

        final LoginUser loginUser = new LoginUser();
        final GetAccount getAccount = new GetAccount();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mail = etMail.getText().toString();
                password = etPassword.getText().toString();
                String tokenValue = null;
                String account = null;
                String token;
                JSONObject userJSON = null;

                try {
                    token = loginUser.execute(mail,password,String.valueOf(false)).get();
                    JSONObject tokenJSON = new JSONObject(token);
                    tokenValue = tokenJSON.getString("id_token");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }




                try {
                    account = getAccount.execute(String.valueOf(tokenValue)).get();
                    userJSON = new JSONObject(account);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println("To jest account: " + account);

                User user = new User();
                try {
                    user.setLogin(userJSON.getString("login"));
                    user.setFirstName(userJSON.getString("firstName"));
                    user.setLastName(userJSON.getString("lastName"));
                    user.setEmail(userJSON.getString("email"));
                    user.setActivated(userJSON.getBoolean("activated"));
                    user.setLangKey(userJSON.getString("langKey"));
                    JSONArray jsonArray = userJSON.getJSONArray("authorities");
                    Set<String> authoritySet = new HashSet<>();
                    for (int i = 0; i < jsonArray.length();i++) {
                        authoritySet.add((jsonArray.getString(i)));
                    }
                    user.setAuthorities(authoritySet);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                    if(user.getAuthorities().contains("ROLE_ADMIN") && user.getAuthorities().contains("ROLE_USER")) {
                        TeacherFragment teacherFragment = new TeacherFragment();
                        ((MainActivity) getActivity()).getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragment_container, teacherFragment).commit();
                    } else if(user.getAuthorities().contains("ROLE_USER")) {
                        StudentFragment studentFragment = new StudentFragment();
                        ((MainActivity) getActivity()).getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragment_container, studentFragment).commit();
                    }


            }
        });

    }

    private class LoginUser extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String[] params) {
            // do above Server call here
            final MediaType JSON
                    = MediaType.parse("application/json; charset=utf-8");

            OkHttpClient client = new OkHttpClient();

            String json = null;
            try {
                json = new JSONObject().put("username",params[0]).put("password",params[1]).put("rememberMe",params[2]).toString();
                System.out.println(json);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            RequestBody body = RequestBody.create(JSON, json);
            Request request = new Request.Builder()
                    .url("http://10.7.2.10:8080/api/authenticate")
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

        }
    }

    private class GetAccount extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String[] params) {
            // do above Server call here
            final MediaType JSON
                    = MediaType.parse("application/json; charset=utf-8");

            OkHttpClient client = new OkHttpClient();

            String json = null;
            try {
                json = new JSONObject().put("id_token",params[0]).toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            RequestBody body = RequestBody.create(JSON, json);
            Request request = new Request.Builder()
                    .url("http://10.7.2.10:8080/api/account")
                    .addHeader("Authorization","Bearer " + params[0])
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
        }
    }


}
