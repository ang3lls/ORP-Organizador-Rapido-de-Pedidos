package com.rdt.orp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;

public class add_cardapio extends AppCompatActivity {

    private EditText edtPrato;
    private EditText edtPreco;
    RadioButton rb1,rb2,rb3,rb4,rb5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_add_cardapio);
        edtPrato = findViewById(R.id.add_prato);
        edtPreco = findViewById(R.id.add_preco);
    }
    public void irParaCardapio(View view){
        Intent intent = new Intent(getApplicationContext(), lista.class);
        startActivity(intent);
    }


    public void add_pratos(View view){
        String prato;
        String preco_s;
        boolean check = false;
        double preco;

        rb1 = findViewById(R.id.radio_prato_principal);
        rb2 = findViewById(R.id.radio_salada);
        rb3 = findViewById(R.id.radio_bebida);
        rb4 = findViewById(R.id.radio_sobremesa);
        rb5 = findViewById(R.id.radio_acompanha);
        prato = edtPrato.getText().toString();
        preco_s = edtPreco.getText().toString();

        if(rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked() || rb5.isChecked()){
            check = true;
        }
        if(prato.equals("")  || preco_s.equals("") || !check){
            Toast.makeText(add_cardapio.this, "Ops, parece que ainda falta preencher algo!",Toast.LENGTH_SHORT).show();
        }
        else {
            preco = Double.parseDouble(preco_s);
            Intent intentEnvio = new Intent(getApplicationContext(), lista.class);
            Bundle parametros = new Bundle();
            parametros.putString("chave prato",prato);
            parametros.putDouble("chave preco",preco);
            intentEnvio.putExtras(parametros);
            Toast.makeText(add_cardapio.this, prato + " de R$" + preco + " adicionado!", Toast.LENGTH_SHORT).show();
            startActivity(intentEnvio);
        }
    }

}