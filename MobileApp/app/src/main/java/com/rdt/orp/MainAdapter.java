package com.rdt.orp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private ArrayList<String> prato_pr;

    MainAdapter(ArrayList<String> pratos) {
        prato_pr = pratos;
    }

    @NonNull
    public MainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent, false);
        return new ViewHolder(view);
    }


    public void onBindViewHolder(@NonNull MainAdapter.ViewHolder holder, int position) {
        holder.nomePrato.setText(prato_pr.get(position));
    }

    public int getItemCount() {
        return prato_pr.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView nomePrato;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            nomePrato = itemView.findViewById(R.id.nomePrato);
        }
    }
}
