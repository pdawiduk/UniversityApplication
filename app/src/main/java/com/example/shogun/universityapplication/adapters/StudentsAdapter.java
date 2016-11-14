package com.example.shogun.universityapplication.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shogun.universityapplication.R;
import com.example.shogun.universityapplication.domain.Consultation;
import com.example.shogun.universityapplication.fragments.ConsultationFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Krystian on 2016-11-14.
 */

public class StudentsAdapter extends RecyclerView.Adapter<StudentsAdapter.StudentHolder>  {
    private final Context context;
    private List<String> studentList;

    public StudentsAdapter(Context context, List<String> studentList) {
        this.context = context;
        this.studentList = studentList;
    }

    @Override
    public StudentsAdapter.StudentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.consultation_item, parent, false);
        StudentsAdapter.StudentHolder studentHolder = new StudentsAdapter.StudentHolder(v);
        return studentHolder;

    }

    @Override
    public void onBindViewHolder(StudentHolder holder, int position) {
        holder.textView.setText( studentList.get(position));
    }



    @Override
    public int getItemCount() {
        return studentList.size();
    }



    class StudentHolder extends RecyclerView.ViewHolder{

        TextView textView ;
        public StudentHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tvDate);
        }
    }
}
