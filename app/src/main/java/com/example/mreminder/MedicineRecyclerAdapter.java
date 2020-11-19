package com.example.mreminder;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MedicineRecyclerAdapter extends RecyclerView.Adapter<MedicineRecyclerAdapter.MyViewHolder>{

    private List<String> list;
    public  MedicineRecyclerAdapter(List<String> list){
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView textView = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.home_recycler_single_item_layout, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(textView);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.medicines.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView medicines;

        public MyViewHolder(@NonNull TextView itemView) {
            super(itemView);
            medicines = itemView;
        }
    }
}
