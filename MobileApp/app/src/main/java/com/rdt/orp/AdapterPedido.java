package com.rdt.orp;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterPedido extends RecyclerView.Adapter<AdapterPedido.ViewHolder>{

    private ArrayList<String> pedido;
    private ArrayList<Float> pedido_preco;
    private RecyclerViewClickInterface recyclerViewClickInterface;

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row2,parent, false);
        return new ViewHolder(view);


    }

    public void onBindViewHolder(@NonNull AdapterPedido.ViewHolder holder, int position){
        holder.precoPrato.setText("R$ " + pedido_preco.get(position));
        holder.nomePrato.setText(pedido.get(position));
    }

    public int getItemCount() {
        return pedido.size();
    }


    AdapterPedido(ArrayList<String> pratos,ArrayList<Float> precos, RecyclerViewClickInterface recyclerViewClickInterface) {
        this.pedido_preco = precos;
        this.pedido = pratos;
        this.recyclerViewClickInterface = recyclerViewClickInterface;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView nomePrato;
        TextView precoPrato;
        private RecyclerViewClickInterface recyclerViewClickInterface;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            nomePrato = itemView.findViewById(R.id.nomePrato2);
            precoPrato = itemView.findViewById(R.id.precoPrato2);

        }
    }


}
