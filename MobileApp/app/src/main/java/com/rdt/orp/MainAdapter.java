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

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder>{

    private ArrayList<String> prato_pr;
    private ArrayList<Float> prato_pr_preco;
    private RecyclerViewClickInterface recyclerViewClickInterface;

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row,parent, false);
        return new ViewHolder(view);


    }

    public void onBindViewHolder(@NonNull MainAdapter.ViewHolder holder, int position){
        holder.precoPrato.setText("R$ " + prato_pr_preco.get(position));
        holder.nomePrato.setText(prato_pr.get(position));
    }

    public int getItemCount() {
        return prato_pr.size();
    }


    MainAdapter(ArrayList<String> pratos,ArrayList<Float> precos, RecyclerViewClickInterface recyclerViewClickInterface) {
        this.prato_pr_preco = precos;
        this.prato_pr = pratos;
        this.recyclerViewClickInterface = recyclerViewClickInterface;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView nomePrato;
        TextView precoPrato;
        private RecyclerViewClickInterface recyclerViewClickInterface;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            nomePrato = itemView.findViewById(R.id.nomePrato);
            precoPrato = itemView.findViewById(R.id.precoPrato);

            itemView.setOnClickListener(this);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerViewClickInterface.onItemClick(getAdapterPosition());
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    recyclerViewClickInterface.onLongItemClick(getAdapterPosition());
                    return true;
                }
            });
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(v.getContext(), prato_pr.get(getAdapterPosition()),Toast.LENGTH_SHORT).show();
        }
    }


}
