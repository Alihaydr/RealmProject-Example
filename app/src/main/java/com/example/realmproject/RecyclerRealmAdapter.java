package com.example.realmproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerRealmAdapter extends RecyclerView.Adapter<RecyclerRealmAdapter.MyViewHolder> {

    Context context;
    ArrayList<QuotesModuleRealm> list;

    public RecyclerRealmAdapter(Context c,ArrayList<QuotesModuleRealm> list){

        this.context=c;
        this.list=list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        QuotesModuleRealm quotesModuleRealm = list.get(position);

        holder.txt_id.setText(String.valueOf(quotesModuleRealm.getId()));
        holder.txt_first.setText(quotesModuleRealm.getFirstName());
        holder.txt_last.setText(quotesModuleRealm.getLastName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{


        TextView txt_id,txt_first,txt_last;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_id=itemView.findViewById(R.id.id_);
            txt_first=itemView.findViewById(R.id.firstname_);
            txt_last=itemView.findViewById(R.id.lastname_);
        }
    }
}