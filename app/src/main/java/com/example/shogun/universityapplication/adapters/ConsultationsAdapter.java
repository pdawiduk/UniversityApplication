package com.example.shogun.universityapplication.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shogun.universityapplication.R;

import java.util.List;

/**
 * Created by Shogun on 04.11.2016.
 */

public class ConsultationsAdapter extends RecyclerView.Adapter<ConsultationsAdapter.ConsultationHolder> {

    List<String> dateLists;
    Context context;

    public ConsultationsAdapter(Context context, List<String> dateLists) {
        this.dateLists = dateLists;
        this.context = context;
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

       holder.textView.setText( dateLists.get(position));
    }

    class ConsultationHolder extends RecyclerView.ViewHolder{

        TextView textView ;
        public ConsultationHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tvDate);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "clicket at position" + getLayoutPosition(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
