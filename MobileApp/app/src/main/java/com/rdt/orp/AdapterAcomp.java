package com.rdt.orp;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterAcomp extends RecyclerView.Adapter<AdapterAcomp.ViewHolder>{

    private ArrayList<String> acompanha;
    private ArrayList<Float> acompanha_preco;
    private RecyclerViewClickInterface recyclerViewClickInterface;
    private AdapterAcomp.OnItemClickListener mListener;
    public interface OnItemClickListener {
        void onItemClickListener(int position);
    }

    public void setOnItemClickListener(AdapterAcomp.OnItemClickListener listener){
        mListener = listener;
    }
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row,parent, false);
        return new ViewHolder(view, mListener);


    }

    public void onBindViewHolder(@NonNull AdapterAcomp.ViewHolder holder, int position){
        holder.precoPrato.setText("R$ " + acompanha_preco.get(position));
        holder.nomePrato.setText(acompanha.get(position));
    }

    public int getItemCount() {
        return acompanha.size();
    }


    AdapterAcomp(ArrayList<String> pratos,ArrayList<Float> precos, RecyclerViewClickInterface recyclerViewClickInterface) {
        this.acompanha_preco = precos;
        this.acompanha = pratos;
        this.recyclerViewClickInterface = recyclerViewClickInterface;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView nomePrato;
        TextView precoPrato;
        private RecyclerViewClickInterface recyclerViewClickInterface;

        ViewHolder(@NonNull View itemView, final AdapterAcomp.OnItemClickListener listener) {
            super(itemView);
            nomePrato = itemView.findViewById(R.id.nomePrato);
            precoPrato = itemView.findViewById(R.id.precoPrato);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClickListener(position);
                        }
                    }
                }
            });
        }
    }



}
