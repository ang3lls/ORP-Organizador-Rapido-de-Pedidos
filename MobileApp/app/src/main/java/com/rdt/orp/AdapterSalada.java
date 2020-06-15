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

public class AdapterSalada extends RecyclerView.Adapter<AdapterSalada.ViewHolder>{

    private ArrayList<String> salada;
    private ArrayList<Float> salada_preco;
    private RecyclerViewClickInterface recyclerViewClickInterface;
    private AdapterSalada.OnItemClickListener mListener;
    public interface OnItemClickListener {
        void onItemClickListener(int position);
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row,parent, false);
        return new ViewHolder(view, mListener);


    }

    public void onBindViewHolder(@NonNull AdapterSalada.ViewHolder holder, int position){
        holder.precoPrato.setText("R$ " + salada_preco.get(position));
        holder.nomePrato.setText(salada.get(position));
    }

    public int getItemCount() {
        return salada.size();
    }

    public void setOnItemClickListener(AdapterSalada.OnItemClickListener listener){
        mListener = listener;
    }


    AdapterSalada(ArrayList<String> pratos,ArrayList<Float> precos, RecyclerViewClickInterface recyclerViewClickInterface) {
        this.salada_preco = precos;
        this.salada = pratos;
        this.recyclerViewClickInterface = recyclerViewClickInterface;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView nomePrato;
        TextView precoPrato;
        private RecyclerViewClickInterface recyclerViewClickInterface;

        ViewHolder(@NonNull View itemView, final AdapterSalada.OnItemClickListener listener) {
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
