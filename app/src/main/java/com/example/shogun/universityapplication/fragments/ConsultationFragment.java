package com.example.shogun.universityapplication.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.shogun.universityapplication.MainActivity;
import com.example.shogun.universityapplication.R;
import com.example.shogun.universityapplication.adapters.ConsultationsAdapter;
import com.example.shogun.universityapplication.adapters.StudentsAdapter;
import com.example.shogun.universityapplication.domain.Consultation;
import com.example.shogun.universityapplication.requests.GetConsultations;
import com.example.shogun.universityapplication.requests.PutConsultation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;


public class ConsultationFragment extends Fragment {
    EditText consultationDate;
    CheckBox cancelled;
    Button editConsultation;

    List<String> studentList = new ArrayList<>();
    private int consultationId;
    private String token;
    private String result;
    private RecyclerView recyclerView;

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

        View view = inflater.inflate(R.layout.fragment_consultation, container, false);
        consultationDate = (EditText) view.findViewById(R.id.consultationDate);
        cancelled = (CheckBox) view.findViewById(R.id.cancelled);
        editConsultation = (Button) view.findViewById(R.id.editConsultation);

        recyclerView = (RecyclerView) view.findViewById(R.id.rvConsultationsList);
        StudentsAdapter adapter = new StudentsAdapter(getContext(),studentList);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);


        Bundle args = getArguments();
        consultationId = args.getInt("id");
        token = args.getString("token");
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
    public void onViewCreated(View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        GetConsultations getConsultations = new GetConsultations();
        final PutConsultation putConsultation = new PutConsultation();
        try {
            result = getConsultations.execute(token, String.valueOf(consultationId)).get();
            System.out.println(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        final SimpleDateFormat output = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy ");

            JSONObject jsonObject;
            try {
                jsonObject = new JSONObject(result);
                Date unformattedDate = sdf.parse(jsonObject.getString("dateTime"));
                String formattedTime = output.format(unformattedDate);
                consultationDate.setText(formattedTime);
                cancelled.setActivated(false);
                JSONArray jsonArray = jsonObject.getJSONArray("registeredStudents");
                Set<String> registeredStudents = new HashSet<>();
                for (int j = 0; j < jsonArray.length();j++) {
                    JSONObject student = jsonArray.getJSONObject(j);
                    registeredStudents.add(student.getString("firstName") + " " + student.getString("lastName"));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

        editConsultation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Date date = output.parse(String.valueOf(consultationDate.getText()));
                    String dateToPut = sdf.format(date);
                    System.out.println(putConsultation.execute(String.valueOf(token),String.valueOf(consultationId),String.valueOf(!cancelled.isEnabled()),dateToPut+".000Z").get());

                } catch (ParseException e) {
                    Toast.makeText(getContext(),"Wrong input!",Toast.LENGTH_LONG);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
