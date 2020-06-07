package com.rdt.orp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.UUID;

public class lista extends AppCompatActivity{

    ArrayList<String> prato_pr = new ArrayList<>();
    ArrayList<Float> prato_pr_preco = new ArrayList<>();

    RecyclerView pt1Cardapio;
    RecyclerView pt1Preco;
    RecyclerView pt2Cardapio;
    RecyclerView pt3Cardapio;
    RecyclerView pt4Cardapio;
    RecyclerView pt5Cardapio;

    RecyclerView.LayoutManager pt1LayoutManager;
    RecyclerView.LayoutManager pt1PrecoLayoutManager;
    RecyclerView.Adapter pt1Adapter;
    RecyclerView.Adapter pt1_preco_Adapter;
    RecyclerViewClickInterface recyclerViewClickInterface;

    Intent intentRecebe;
    Bundle parametros;

    final String pratos = "prato ";
    final String precosC = "preco ";
    final String tiposC = "tipo ";

    int size = 0;

    private static String ARQUIVO_PREFERENCIA = "ArquivoPreferencia";



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_lista);


        pt1Cardapio = findViewById(R.id.list_prato_p);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);

        pt1Cardapio.addItemDecoration(dividerItemDecoration);

        intentRecebe = getIntent();
        parametros = intentRecebe.getExtras();
        if(parametros != null) {
            SharedPreferences sharedPreferences = getSharedPreferences("ArquivoPreferencia",0);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            size = sharedPreferences.getInt("size", 0);
            String aux = parametros.getString("chave prato");
            float preco = parametros.getFloat("chave preco");
            int tipo = parametros.getInt("cahve tipo");
            Float[] precos = new Float[size+1];
            String[] prato = new String[size+1];
             int[] tipos = new int[size+1];
            prato[size] = aux;
            precos[size] = preco;
            tipos[size] = tipo;
            editor.putFloat(precosC + size, precos[size]);
            editor.putString(pratos + size, prato[size]);
              editor.putInt(tiposC + size, tipos[size]);
            editor.commit();
            if(sharedPreferences.contains(pratos + size)){
                for(int i = 0; i < size+1; i++){
                    prato[i] = sharedPreferences.getString(pratos + i, "Sem conteudo, que fome");
                    precos[i] = sharedPreferences.getFloat(precosC + i, 0);
                }
                for(int i = 0; i < size+1; i++){
                    prato_pr.add(i,prato[i]);
                             prato_pr_preco.add(i, precos[i]);
                }
                size++;
                editor.putInt("size", size);
                editor.commit();
            }
            pt1Cardapio.setHasFixedSize(true);
            pt1LayoutManager = new LinearLayoutManager(this);
            pt1Adapter = new MainAdapter(prato_pr, prato_pr_preco,recyclerViewClickInterface);
            pt1Cardapio.setLayoutManager(pt1LayoutManager);
            pt1Cardapio.setAdapter(pt1Adapter);
        }
    }
    public void irParaAddCardapio(View view){
        Intent intent = new Intent(getApplicationContext(), add_cardapio.class);
        startActivity(intent);
    }
    public void irParaPedidos(View view){
        Intent intent = new Intent(getApplicationContext(), pedidos.class);
        startActivity(intent);
    }
}