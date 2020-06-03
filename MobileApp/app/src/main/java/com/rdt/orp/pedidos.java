package com.rdt.orp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class pedidos extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "RecyclerPrecoAdaper";
    BluetoothAdapter meuBluetoothAdapter = null;
    BluetoothDevice arduino = null;
    BluetoothSocket meuSocket = null;
    String pedidos[] = {"Aspargos", "Cebolinha", "Strognoff"};
    private static final int SOLICITA_ATIVACAO = 1;
    private static final int SOLICITA_CONEXAO = 2;

    private static final int MESSAGE_READ  = 3;
  //  Handler mHandler;
  //  StringBuilder dadosBluetooth = new StringBuilder();
    int AtivaBluetooth = 1;
    UUID arduinoUUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    Button btnConect, btnPedidos;

    pedidos.ConnectThread connectThread;
    boolean conexao = false;

    private static String MAC = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);
        getSupportActionBar().hide();

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.mesas, android.R.layout.simple_list_item_1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        btnConect = findViewById(R.id.btnBluetoothConect);
        btnPedidos = findViewById(R.id.btnLed2);

        meuBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (meuBluetoothAdapter == null){
            Toast.makeText(getApplicationContext(),"Seu dispositivo não possui bluetooth",Toast.LENGTH_LONG).show();
        }
        else if(!meuBluetoothAdapter.isEnabled()){
            Intent ativaBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(ativaBluetooth, AtivaBluetooth);
        }

        btnConect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (conexao) {
                    try {
                        meuSocket.close();
                        conexao = false;
                        btnConect.setText("Conectar");
                        Toast.makeText(getApplicationContext(),"Bluetooth foi desconectado",Toast.LENGTH_LONG).show();
                    }catch (IOException erro){
                        Toast.makeText(getApplicationContext(),"Ocooreu um erro: " + erro,Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Intent abreLista = new Intent(pedidos.this, ListaDispostivos.class);
                    startActivityForResult(abreLista, SOLICITA_CONEXAO);
                }
            }
        });

        btnPedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (conexao){
                    connectThread.enviar("novoPedido ");
                    for(int i = 0; i < pedidos.length; i++){
                        connectThread.enviar(pedidos[i]);
                    }
                    Toast.makeText(getApplicationContext(),"Pedido enviado!",Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),"Bluetooth não está conectado",Toast.LENGTH_LONG).show();
                }
            }
        });
/*
        mHandler = new Handler() {

            public void handleMessage(Message msg) {
                if(msg.what == MESSAGE_READ){

                    String recebidos = (String) msg.obj;

                    dadosBluetooth.append(recebidos);

                    int fimInformacao = dadosBluetooth.indexOf("}");

                    if (fimInformacao < 0){

                        String dadosCompletos = dadosBluetooth.substring(0,fimInformacao);

                        int tamInformacao = dadosCompletos.length();

                        if(dadosBluetooth.charAt(0) == '{'){

                            String dadosFinais = dadosBluetooth.substring(1,tamInformacao);

                            Log.d("Recebidos", dadosFinais);

                            if(dadosFinais.contains("pedidoRecebido")){
                               Toast.makeText(pedidos.this,"Pedidos entregues a cozinha!",Toast.LENGTH_LONG).show();
                            }
                        }

                        dadosBluetooth.delete(0,dadosBluetooth.length());
                    }
                }
            }
        };*/

    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case SOLICITA_ATIVACAO:
                if (resultCode == Activity.RESULT_OK){
                    Toast.makeText(getApplicationContext(),"Bluetooth ativado!",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(),"Bluetooth não ativado",Toast.LENGTH_LONG).show();
                    finish();
                }
                break;

            case SOLICITA_CONEXAO:
                if (resultCode == Activity.RESULT_OK){
                    MAC = data.getExtras().getString(ListaDispostivos.ENDERECO_MAC);
                    //Toast.makeText(getApplicationContext(),"MAC FINAL: " + MAC,Toast.LENGTH_LONG).show();
                    arduino = meuBluetoothAdapter.getRemoteDevice(MAC);

                    try {
                        meuSocket = arduino.createRfcommSocketToServiceRecord(arduinoUUID);
                        meuSocket.connect();
                        conexao = true;

                        connectThread = new ConnectThread(meuSocket);
                        connectThread.start();

                        btnConect.setText("Desconectar");
                        Toast.makeText(getApplicationContext(),"Você se conectou com: " + MAC,Toast.LENGTH_LONG).show();
                    }
                    catch (IOException erro){
                        Toast.makeText(getApplicationContext(),"Ocooreu um erro: " + erro,Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),"Falha ao obter endereço MAC",Toast.LENGTH_LONG).show();
                }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(pedidos.this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private class ConnectThread extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectThread(BluetoothSocket socket) {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                Log.e(TAG, "Socket's create() method failed", e);
            }
            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void enviar(String dadosEnviar){
            byte[] msgBuffer = dadosEnviar.getBytes();
            try {
                mmOutStream.write(msgBuffer);
            }
            catch (IOException e){
            }
        }

        public void run() {
            byte[] buffer = new byte[1024];
            int bytes;

           while(true){
                   try {
                     bytes = mmInStream.read(buffer);

                    // String dadosBt = new String(buffer, 0,bytes);

                     //mHandler.obtainMessage(MESSAGE_READ, bytes, -1, dadosBt).sendToTarget();

                   } catch (IOException connectException) {
                       break;
                   }

               }
        }
    }
}
