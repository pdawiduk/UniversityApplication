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
import android.widget.Toast;

import com.example.shogun.universityapplication.MainActivity;
import com.example.shogun.universityapplication.R;
import com.example.shogun.universityapplication.domain.User;
import com.example.shogun.universityapplication.requests.GetAccount;
import com.example.shogun.universityapplication.requests.LoginUser;

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
                String tokenValue = null;
                String account = null;
                String token;
                JSONObject userJSON = null;
                LoginUser loginUser = new LoginUser();
                GetAccount getAccount = new GetAccount();

                try {
                    token = loginUser.execute(mail,password,String.valueOf(false)).get();
                    JSONObject tokenJSON = new JSONObject(token);
                    tokenValue = tokenJSON.getString("id_token");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(),"Cannot log in", Toast.LENGTH_LONG).show();
                } catch (ExecutionException e) {
                    Toast.makeText(getContext(),"Cannot log in", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(),"Wrong login/password or account is not activated", Toast.LENGTH_LONG).show();
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

                    if((user.getAuthorities().contains("ROLE_ADMIN") && user.getAuthorities().contains("ROLE_USER")) || user.getAuthorities().contains("ROLE_TEACHER")) {
                        TeacherFragment teacherFragment = new TeacherFragment();
                        Bundle args = new Bundle();
                        args.putString("token",tokenValue);
                        teacherFragment.setArguments(args);
                        ((MainActivity) getActivity()).getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragment_container, teacherFragment).commit();
                    } else if(user.getAuthorities().contains("ROLE_USER") || user.getAuthorities().contains("ROLE_STUDENT")) {
                        StudentFragment studentFragment = new StudentFragment();
                        ((MainActivity) getActivity()).getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragment_container, studentFragment).commit();
                    }


            }
        });

    }
}
