package com.rdt.orp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class cadastro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_cadastro);
    }
    public void irParaCadastro2(View view){
        Intent intent = new Intent(getApplicationContext(), restaurante_cadastro.class);
        startActivity(intent);
    }
}
