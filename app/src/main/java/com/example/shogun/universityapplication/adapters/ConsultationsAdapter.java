package com.example.shogun.universityapplication.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shogun.universityapplication.MainActivity;
import com.example.shogun.universityapplication.R;
import com.example.shogun.universityapplication.domain.Consultation;
import com.example.shogun.universityapplication.fragments.ConsultationFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Shogun on 04.11.2016.
 */

public class ConsultationsAdapter extends RecyclerView.Adapter<ConsultationsAdapter.ConsultationHolder> {

    private FragmentActivity activity;
    private String token;
    private int consultationId;
    List<Consultation> dateLists;
    Context context;

    public ConsultationsAdapter(Context context, List<Consultation> dateLists, FragmentActivity activity) {
        this.dateLists = dateLists;
        this.context = context;
        this.activity = activity;
    }

    public ConsultationsAdapter(Context context, ArrayList<Consultation> cosnultationList, FragmentActivity activity, int consultationId, String token) {
        this.dateLists = cosnultationList;
        this.context = context;
        this.activity = activity;
        this.consultationId = consultationId;
        this.token = token;
    }

    @Override
    public ConsultationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.consultation_item, parent, false);
        ConsultationHolder consultationHolder = new ConsultationHolder(v);
        return consultationHolder;

    }

    @Override
    public int getItemCount() {
        return dateLists.size();
    }

    @Override
    public void onBindViewHolder(ConsultationHolder holder, int position) {

       holder.textView.setText( dateLists.get(position).getDateTime());
    }

    class ConsultationHolder extends RecyclerView.ViewHolder{

        TextView textView ;
        public ConsultationHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tvDate);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ConsultationFragment consultationFragment = new ConsultationFragment();
                    Bundle args = new Bundle();
                    for(Consultation consultation: dateLists){
                        if(consultation.getDateTime().equals(textView.getText())){
                            consultationId = consultation.getId();
                        }
                    }
                    args.putInt("id",consultationId);
                    args.putString("token",token);
                    consultationFragment.setArguments(args);
                    activity.getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragment_container, consultationFragment).commit();
                    Toast.makeText(context, "clicket at position" + getLayoutPosition(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
