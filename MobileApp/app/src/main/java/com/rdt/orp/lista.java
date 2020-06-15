package com.rdt.orp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class lista extends AppCompatActivity implements RecyclerViewClickInterface{

    ArrayList<String> prato_pr = new ArrayList<>();
    ArrayList<Float> prato_pr_preco = new ArrayList<>();
    ArrayList<String> salada = new ArrayList<>();
    ArrayList<Float> salada_preco = new ArrayList<>();
    ArrayList<String> bebida = new ArrayList<>();
    ArrayList<Float> bebida_preco = new ArrayList<>();
    ArrayList<String> sobremesa = new ArrayList<>();
    ArrayList<Float> sobremesa_preco = new ArrayList<>();
    ArrayList<String> acompanha = new ArrayList<>();
    ArrayList<Float> acompanha_preco = new ArrayList<>();
    ArrayList<Integer> tipos = new ArrayList<>();


    RecyclerView pt1Cardapio;
    RecyclerView pt2Cardapio;
    RecyclerView pt3Cardapio;
    RecyclerView pt4Cardapio;
    RecyclerView pt5Cardapio;

    RecyclerView.LayoutManager pt1LayoutManager;
    RecyclerView.LayoutManager pt2LayoutManager;
    RecyclerView.LayoutManager pt3LayoutManager;
    RecyclerView.LayoutManager pt4LayoutManager;
    RecyclerView.LayoutManager pt5LayoutManager;

    MainAdapter pt1Adapter;
    AdapterSalada pt2Adapter;
    AdapterBebida pt3Adapter;
    AdapterSobremesa pt4Adapter;
    AdapterAcomp pt5Adapter;

    RecyclerViewClickInterface recyclerViewClickInterface;


    Intent intentRecebe;
    Bundle parametros;

    final String pratos = "prato ";
    final String precosC = "preco ";
    final String tiposC = "tipo ";

    int index = 0;
    int pos;
    int num = 0;
    int[] quantidades = {0,0,0,0,0};
    String aux;
    float preco;
    int tipo;
    boolean removido = false;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private static String ARQUIVO_PREFERENCIA = "ArquivoPreferencia";



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_lista);


        pt1Cardapio = findViewById(R.id.list_prato_p);
        pt2Cardapio = findViewById(R.id.list_salada);
        pt3Cardapio = findViewById(R.id.list_bebidas);
        pt4Cardapio = findViewById(R.id.list_sobremesa);
        pt5Cardapio = findViewById(R.id.list_acompanha);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);

        pt1Cardapio.addItemDecoration(dividerItemDecoration);
        pt2Cardapio.addItemDecoration(dividerItemDecoration);
        pt3Cardapio.addItemDecoration(dividerItemDecoration);
        pt4Cardapio.addItemDecoration(dividerItemDecoration);
        pt5Cardapio.addItemDecoration(dividerItemDecoration);

        intentRecebe = getIntent();
        parametros = intentRecebe.getExtras();
        sharedPreferences = getSharedPreferences("ArquivoPreferencia",0);
        editor = sharedPreferences.edit();

        if(parametros != null) {
            index = sharedPreferences.getInt("index", 0);
            aux = parametros.getString("chave prato");
            preco = parametros.getFloat("chave preco");
            tipo = parametros.getInt("chave tipo");
            editor.putFloat(precosC + index, preco);
            editor.putString(pratos + index, aux);
            editor.putInt(tiposC + index, tipo);
            editor.commit();
        }
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(pt1Cardapio);
            if(sharedPreferences.contains("rm") ){
                pos = sharedPreferences.getInt("pos", 0);
                removido = sharedPreferences.getBoolean("rm", false);
                quantidades[0] = sharedPreferences.getInt("quantidade1", 0);
            }
            if(removido){
                for(int i = pos; i <= quantidades[0]+1; i++){
                    tipo = sharedPreferences.getInt(tiposC + i, 0);
                    aux = sharedPreferences.getString(pratos + i, "Que fome...");
                    preco = sharedPreferences.getFloat(precosC + i, 0);
                    editor.putFloat(precosC + (i-1), preco);
                    editor.putString(pratos + (i-1), aux);
                    editor.putInt(tiposC + (i-1), tipo);
                }
                index--;
                removido = false;
                    editor.putBoolean("rm",removido);
                    editor.commit();
            }
                quantidades[0] = 0;
                quantidades[1] = 0;
                quantidades[2] = 0;
                quantidades[3] = 0;
                quantidades[4] = 0;
                for(int i = 0; i < index+1; i++){
                    tipo = sharedPreferences.getInt(tiposC + i, 0);
                    aux = sharedPreferences.getString(pratos + i, "Que fome...");
                    preco = sharedPreferences.getFloat(precosC + i, 0);
                    tipos.add(tipo);
                    switch (tipos.get(i)){
                        case 1:
                            prato_pr.add(aux);
                            prato_pr_preco.add(preco);
                            quantidades[0]++;
                            break;
                        case 2:
                            salada.add(aux);
                            salada_preco.add(preco);
                            quantidades[1]++;
                            break;
                        case 3:
                            bebida.add(aux);
                            bebida_preco.add(preco);
                            quantidades[2]++;
                            break;
                        case 4:
                            sobremesa.add(aux);
                            sobremesa_preco.add(preco);
                            quantidades[3]++;
                            break;
                        case 5:
                            acompanha.add(aux);
                            acompanha_preco.add(preco);
                            quantidades[4]++;
                            break;
                    }
                }
                index++;
                editor.putInt("index", index);
                editor.putInt("quantidade1", quantidades[0]);
                editor.putInt("quantidade2", quantidades[1]);
                editor.putInt("quantidade3", quantidades[2]);
                editor.putInt("quantidade4", quantidades[3]);
                editor.putInt("quantidade5", quantidades[4]);
                editor.commit();

            pt1Cardapio.setHasFixedSize(true);
            pt2Cardapio.setHasFixedSize(true);
            pt3Cardapio.setHasFixedSize(true);
            pt4Cardapio.setHasFixedSize(true);
            pt5Cardapio.setHasFixedSize(true);

            pt1LayoutManager = new LinearLayoutManager(this);
            pt2LayoutManager = new LinearLayoutManager(this);
            pt3LayoutManager = new LinearLayoutManager(this);
            pt4LayoutManager = new LinearLayoutManager(this);
            pt5LayoutManager = new LinearLayoutManager(this);

            pt1Adapter = new MainAdapter(prato_pr, prato_pr_preco,this);
            pt2Adapter = new AdapterSalada(salada, salada_preco,this);
            pt3Adapter = new AdapterBebida(bebida, bebida_preco,this);
            pt4Adapter = new AdapterSobremesa(sobremesa, sobremesa_preco,this);
            pt5Adapter = new AdapterAcomp(acompanha, acompanha_preco,this);

            pt1Cardapio.setLayoutManager(pt1LayoutManager);
            pt2Cardapio.setLayoutManager(pt2LayoutManager);
            pt3Cardapio.setLayoutManager(pt3LayoutManager);
            pt4Cardapio.setLayoutManager(pt4LayoutManager);
            pt5Cardapio.setLayoutManager(pt5LayoutManager);

            pt1Cardapio.setAdapter(pt1Adapter);
            pt2Cardapio.setAdapter(pt2Adapter);
            pt3Cardapio.setAdapter(pt3Adapter);
            pt4Cardapio.setAdapter(pt4Adapter);
            pt5Cardapio.setAdapter(pt5Adapter);

            pt1Adapter.setOnItemClickListener(new MainAdapter.OnItemClickListener() {
                @Override
                public void onItemClickListener(int position) {
                    Intent intentEnvio = new Intent(getApplicationContext(), pedidos.class);
                    Bundle parametros = new Bundle();
                    parametros.putString("nome Pedidos", prato_pr.get(position));
                    parametros.putFloat("preco Pedidos", prato_pr_preco.get(position));
                    intentEnvio.putExtras(parametros);
                    Toast.makeText(lista.this, prato_pr.get(position) + " de R$" + prato_pr_preco.get(position), Toast.LENGTH_SHORT).show();
                    startActivity(intentEnvio);
                }
            });
        pt2Adapter.setOnItemClickListener(new AdapterSalada.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position) {
                Intent intentEnvio = new Intent(getApplicationContext(), pedidos.class);
                Bundle parametros = new Bundle();
                parametros.putString("nome Pedidos", salada.get(position));
                parametros.putFloat("preco Pedidos", salada_preco.get(position));
                intentEnvio.putExtras(parametros);
                Toast.makeText(lista.this, salada.get(position) + " de R$" + salada_preco.get(position), Toast.LENGTH_SHORT).show();
                startActivity(intentEnvio);
            }
        });
        pt3Adapter.setOnItemClickListener(new AdapterBebida.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position) {
                Intent intentEnvio = new Intent(getApplicationContext(), pedidos.class);
                Bundle parametros = new Bundle();
                parametros.putString("nome Pedidos", bebida.get(position));
                parametros.putFloat("preco Pedidos", bebida_preco.get(position));
                intentEnvio.putExtras(parametros);
                Toast.makeText(lista.this, bebida.get(position) + " de R$" + bebida_preco.get(position), Toast.LENGTH_SHORT).show();
                startActivity(intentEnvio);
            }
        });
        pt4Adapter.setOnItemClickListener(new AdapterSobremesa.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position) {
                Intent intentEnvio = new Intent(getApplicationContext(), pedidos.class);
                Bundle parametros = new Bundle();
                parametros.putString("nome Pedidos", sobremesa.get(position));
                parametros.putFloat("preco Pedidos", sobremesa_preco.get(position));
                intentEnvio.putExtras(parametros);
                Toast.makeText(lista.this, sobremesa.get(position) + " de R$" + sobremesa_preco.get(position), Toast.LENGTH_SHORT).show();
                startActivity(intentEnvio);
            }
        });
        pt5Adapter.setOnItemClickListener(new AdapterAcomp.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position) {
                Intent intentEnvio = new Intent(getApplicationContext(), pedidos.class);
                Bundle parametros = new Bundle();
                parametros.putString("nome Pedidos", acompanha.get(position));
                parametros.putFloat("preco Pedidos", acompanha_preco.get(position));
                intentEnvio.putExtras(parametros);
                Toast.makeText(lista.this, acompanha.get(position) + " de R$" + acompanha_preco.get(position), Toast.LENGTH_SHORT).show();
                startActivity(intentEnvio);
            }
        });
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            switch (tipos.get(position)){
                case 1:
                    prato_pr.remove(position);
                    prato_pr_preco.remove(position);
                    break;
                case 2:
                    salada.remove(position);
                    salada_preco.remove(position);
                    break;
                case 3:
                    bebida.remove(position);
                    bebida_preco.remove(position);
                    break;
                case 4:
                    sobremesa.remove(position);
                    sobremesa_preco.remove(position);
                    break;
                case 5:
                    acompanha.remove(position);
                    acompanha_preco.remove(position);
                    break;
            }
            if (!removido){
                pos = position;
            }
            if(position < pos){
                pos = position;
            }
            pt1Adapter.notifyItemRemoved(position);
            removido = true;
            if(sharedPreferences.contains(pratos + index)){
                editor.putInt("pos",pos);
                editor.putBoolean("rm",removido);
                editor.commit();
            }
        }
    };

    public void irParaAddCardapio(View view){
        Intent intent = new Intent(getApplicationContext(), add_cardapio.class);
        startActivity(intent);
    }
    public void irParaPedidos(View view){
        Intent intent = new Intent(getApplicationContext(), pedidos.class);
        Bundle parametros = new Bundle();
        parametros.putString("nennotame", "nennotame");
        intent.putExtras(parametros);
        startActivity(intent);
    }

    @Override
    public void onItemClick(int posistion) {
    }

    @Override
    public void onLongItemClick(int position) {
    }
}