package com.rdt.orp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class lista extends AppCompatActivity {

    ArrayList<String> prato_pr = new ArrayList<>();
    RecyclerView pt1Cardapio;
    RecyclerView.LayoutManager pt1LayoutManager;
    RecyclerView.Adapter pt1Adapter;
    boolean first;
    Intent intentRecebe;
    Bundle parametros;
    int size = 0;
    private static String ARQUIVO_PREFERENCIA = "ArquivoPreferencia";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_lista);

        pt1Cardapio = findViewById(R.id.list_prato_p);


        intentRecebe = getIntent();
        parametros = intentRecebe.getExtras();
        if(parametros != null) {
            SharedPreferences sharedPreferences = getSharedPreferences("ArquivoPreferencia",0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            size = sharedPreferences.getInt("size", 0);
            String aux = parametros.getString("chave prato");
            String[] prato = new String[size+1];
            prato[size] = aux;
            editor.putString("nome", prato[size]);
            editor.commit();
            if(sharedPreferences.contains("nome")){
                for(int i = 0; i < size+1; i++){
                    prato[i] = sharedPreferences.getString("nome", "Sem conteudo, que fome");
                }
                for(int i = 0; i < size+1; i++){
                    prato_pr.add(i,prato[size]);
                }
                size++;
                Toast.makeText(this, "" + size, Toast.LENGTH_SHORT).show();
                editor.putInt("size", size);
                editor.commit();
            }
        }
        pt1Cardapio.setHasFixedSize(true);
        pt1LayoutManager = new LinearLayoutManager(this);
        pt1Adapter = new MainAdapter(prato_pr);
        pt1Cardapio.setLayoutManager(pt1LayoutManager);
        pt1Cardapio.setAdapter(pt1Adapter);
    }
    public void irParaAddCardapio(View view){
        Intent intent = new Intent(getApplicationContext(), add_cardapio.class);
        startActivity(intent);
    }

}
