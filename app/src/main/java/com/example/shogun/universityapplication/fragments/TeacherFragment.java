package com.example.shogun.universityapplication.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.shogun.universityapplication.MainActivity;
import com.example.shogun.universityapplication.R;
import com.example.shogun.universityapplication.adapters.ConsultationsAdapter;
import com.example.shogun.universityapplication.domain.Consultation;
import com.example.shogun.universityapplication.domain.User;
import com.example.shogun.universityapplication.requests.GetAccount;
import com.example.shogun.universityapplication.requests.GetConsultations;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class TeacherFragment extends Fragment {
    ArrayList<String> cosnultationList = new ArrayList<>();
    RecyclerView recyclerView;
    String token;
    JSONArray consultationJSON;

    private JSONObject userJSON;
    private String account;
    private int consultationId;

    public TeacherFragment() {


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
        Bundle args = getArguments();
        token = args.getString("token");
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

        GetConsultations getConsultations = new GetConsultations();
        GetAccount getAccount = new GetAccount();
        JSONObject jsonObject = null;
        ArrayList<Consultation> consultations = new ArrayList<>();
        try {
            String result = getConsultations.execute(token).get();
            System.out.println("To jest result: " + result);
            consultationJSON = new JSONArray(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            account = getAccount.execute(String.valueOf(token)).get();
            userJSON = new JSONObject(account);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        User teacher = new User();
        try {
            teacher.setLogin(userJSON.getString("login"));
            teacher.setFirstName(userJSON.getString("firstName"));
            teacher.setLastName(userJSON.getString("lastName"));
            teacher.setEmail(userJSON.getString("email"));
            teacher.setActivated(userJSON.getBoolean("activated"));
            teacher.setLangKey(userJSON.getString("langKey"));
            JSONArray jsonArray = userJSON.getJSONArray("authorities");
            Set<String> authoritySet = new HashSet<>();
            for (int i = 0; i < jsonArray.length();i++) {
                authoritySet.add((jsonArray.getString(i)));
            }
            teacher.setAuthorities(authoritySet);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat output = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy ");


        for(int i = 0; i < consultationJSON.length(); i++) {

            try {
                jsonObject = consultationJSON.getJSONObject(i);
                consultationId = jsonObject.getInt("id");
                String dateTime = jsonObject.getString("dateTime");
                Date d = sdf.parse(dateTime);
                String formattedTime = output.format(d);
                /*boolean cancelled = jsonObject.getBoolean("cancelled");*/
                String teacherLogin = jsonObject.getString("teacherLogin");
                JSONArray jsonArray = jsonObject.getJSONArray("registeredStudents");
                Set<String> registeredStudents = new HashSet<>();
                for (int j = 0; j < jsonArray.length();j++) {
                    JSONObject student = jsonArray.getJSONObject(j);
                    registeredStudents.add(student.getString("firstName") + " " + student.getString("lastName"));
                }

                consultations.add(new Consultation(consultationId,formattedTime,false,teacher,registeredStudents));
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

        for (Consultation consultation:consultations) {
            cosnultationList.add(consultation.getDateTime());
        }
        ConsultationsAdapter adapter = new ConsultationsAdapter(getContext(),consultations,getActivity(),consultationId,token);
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
