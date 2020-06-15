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
    boolean check = false;

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
        float preco;
        int tipoComida = 0;

        rb1 = findViewById(R.id.radio_prato_principal);
        rb2 = findViewById(R.id.radio_salada);
        rb3 = findViewById(R.id.radio_bebida);
        rb4 = findViewById(R.id.radio_sobremesa);
        rb5 = findViewById(R.id.radio_acompanha);
        prato = edtPrato.getText().toString();
        preco_s = edtPreco.getText().toString();

        if(rb1.isChecked()){
            tipoComida = 1;
            check = true;
        } else if(rb2.isChecked()){
            tipoComida = 2;
            check = true;
        } else if(rb3.isChecked()){
            tipoComida = 3;
            check = true;
        } else if(rb4.isChecked()){
            tipoComida = 4;
            check = true;
        }else if(rb5.isChecked()){
            tipoComida = 5;
            check = true;
        }

        if(prato.equals("")  || preco_s.equals("") || !check){
            Toast.makeText(add_cardapio.this, "Ops, parece que ainda falta preencher algo!",Toast.LENGTH_SHORT).show();
        }
        else {
            preco = Float.parseFloat(preco_s);
            Intent intentEnvio = new Intent(getApplicationContext(), lista.class);
            Bundle parametros = new Bundle();
            parametros.putInt("chave tipo", tipoComida);
            parametros.putString("chave prato",prato);
            parametros.putFloat("chave preco",preco);
            intentEnvio.putExtras(parametros);
            Toast.makeText(add_cardapio.this, prato + " de R$" + preco + " adicionado!", Toast.LENGTH_SHORT).show();
            startActivity(intentEnvio);
        }
    }

}
