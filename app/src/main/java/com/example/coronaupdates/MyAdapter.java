package com.example.coronaupdates;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private Context context;
    private List<Countries> list;

    public MyAdapter(Context context, List<Countries> list){
        this.context=context;
        this.list=list;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.singlerow,parent,false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        Countries countries=list.get(position);
        holder.Name.setText(countries.getCountry());
        holder.tconfirmed.setText("Total Confirmed Cases : "+countries.getTotalConfirmed());
        holder.tdeath.setText("Total Death : "+countries.getTotalDeaths());
        holder.trecovered.setText("Total Recovered : "+countries.getTotalRecovered());
    }

    @Override
    public int getItemCount()
    {
        int size;
        //size=list.size();
        return this.list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Name,tconfirmed,tdeath,trecovered;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Name=itemView.findViewById(R.id.country_name);
            tconfirmed=itemView.findViewById(R.id.total_confirmed_Cases);
            tdeath=itemView.findViewById(R.id.total_confirmed_death);
            trecovered=itemView.findViewById(R.id.total_confirmed_recovered);
        }
    }
}
