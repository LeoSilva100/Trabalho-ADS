 package com.example.leosi.r_aid;
 import android.app.Activity;
 import android.bluetooth.BluetoothAdapter;
 import android.bluetooth.BluetoothDevice;
 import android.bluetooth.BluetoothSocket;
 import android.content.Intent;
 import android.os.Environment;
 import android.os.Handler;
 import android.os.Message;
 import android.support.v7.app.AppCompatActivity;
 import android.os.Bundle;
 import android.view.View;
 import android.widget.Button;
 import android.widget.Toast;
 import java.io.BufferedWriter;
 import java.io.File;
 import java.io.FileWriter;
 import java.io.IOException;
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.io.Writer;
 import java.text.SimpleDateFormat;
 import java.util.GregorianCalendar;
 import java.util.Locale;
 import java.util.UUID;
 import  java.util.concurrent.TimeUnit;

 public class MainActivity extends AppCompatActivity {

     public int id = 0;
     boolean stop;
     public String dados;
     public File diretorio;
     public String nomeDiretorio = "Download";
     public String diretorioApp;

     final String PACKET1 = "AAAAAAAAAAAAAA";
     final String PACKET2 = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
     final String PACKET3 = "CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC";

     Button btnConexao;
     Button btn32;
     Button btn64;
     Button btn84;

     ConnectedThread connectedThread;
     BluetoothAdapter meuBluetoothAdapter = null;
     BluetoothDevice meuDevice = null;
     BluetoothSocket meuSocket = null;
     boolean conexao =  false;
     private static String MAC = null;
     private static final int START_BLUETOOTH=1;
     private static final int SOLICITA_CONEXAO=2;
     UUID MEU_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main);
         btnConexao = (Button) findViewById(R.id.btnConexao);
         btn32  = (Button) findViewById(R.id.btn32);
         btn64 = (Button) findViewById(R.id.btn64);
         btn84 = (Button) findViewById(R.id.btn84);

         meuBluetoothAdapter =  BluetoothAdapter.getDefaultAdapter();
         if(meuBluetoothAdapter ==  null){
             Toast.makeText(getApplicationContext(),"Seu dispositivo não pussui bluetooth", Toast.LENGTH_LONG).show();
         }else if(!meuBluetoothAdapter.isEnabled()) {
             Intent ativaBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
             startActivityForResult(ativaBluetooth, START_BLUETOOTH);
         }
         btnConexao.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if (conexao){
                     try {
                         meuSocket.close();
                         conexao =  false;
                         btnConexao.setText("Conectar");
                         Toast.makeText(getApplicationContext(),"O Bluetooth foi desconectado!", Toast.LENGTH_LONG).show();
                     }catch (IOException erro){
                         Toast.makeText(getApplicationContext(),"Ocorreu um erro!"+erro, Toast.LENGTH_LONG).show();
                     }

                 }else{
                     Intent openList = new Intent(MainActivity.this, ListaDispositivos.class);
                     startActivityForResult(openList, SOLICITA_CONEXAO);
                 }
             }
         });
         btn32.setOnClickListener(new View.OnClickListener(){
             @Override
             public void onClick(View vk) {
                 for(int i = 0 ; i < 5 ; i++) {
                     if (conexao) {
                         Locale local = new Locale("pt", "BR");
                         GregorianCalendar calen = new GregorianCalendar();
                         SimpleDateFormat forma = new SimpleDateFormat("HH:mm:ss.S", local);
                         final String dataHora = forma.format(calen.getTime());
                         id++;
                         file_save_send(id + "-" + dataHora + "-" + PACKET1 + "-" + "#");
                         connectedThread.write(id + "-" + dataHora + "-" + PACKET1 + "-" + "#");
                     } else {
                         Toast.makeText(getApplicationContext(), "O bluetooth não está conectado!", Toast.LENGTH_LONG).show();
                     }
                     try {
                         TimeUnit.SECONDS.sleep(3);
                     } catch (InterruptedException e) {
                         e.printStackTrace();
                     }
                 }
             }
         });
         btn64.setOnClickListener(new View.OnClickListener(){
             @Override
             public void onClick(View vk) {
                 for(int i = 0 ; i < 5 ; i++) {
                     if (conexao) {
                         Locale local = new Locale("pt", "BR");
                         GregorianCalendar calen = new GregorianCalendar();
                         SimpleDateFormat forma = new SimpleDateFormat("HH:mm:ss.S", local);
                         final String dataHora = forma.format(calen.getTime());
                         id++;
                         file_save_send(id + "-" + dataHora + "-" + PACKET2 + "-" + "#");
                         connectedThread.write(id + "-" + dataHora + "-" + PACKET2 + "-" + "#");
                     } else {
                         Toast.makeText(getApplicationContext(), "O bluetooth não está conectado!", Toast.LENGTH_LONG).show();
                     }
                     try {
                         TimeUnit.SECONDS.sleep(3);
                     } catch (InterruptedException e) {
                         e.printStackTrace();
                     }
                 }
             }
         });
         btn84.setOnClickListener(new View.OnClickListener(){
             @Override
             public void onClick(View vk) {
                 for(int i = 0 ; i < 5 ; i++) {
                     if (conexao) {
                         Locale local = new Locale("pt", "BR");
                         GregorianCalendar calen = new GregorianCalendar();
                         SimpleDateFormat forma = new SimpleDateFormat("HH:mm:ss.S", local);
                         final String dataHora = forma.format(calen.getTime());
                         id++;
                         file_save_send(id + "-" + dataHora + "-" + PACKET3 + "-" + "#");
                         connectedThread.write(id + "-" + dataHora + "-" + PACKET3 + "-" + "#");
                     } else {
                         Toast.makeText(getApplicationContext(), "O bluetooth não está conectado!", Toast.LENGTH_LONG).show();
                     }
                     try {
                         TimeUnit.SECONDS.sleep(3);
                     } catch (InterruptedException e) {
                         e.printStackTrace();
                     }
                 }
             }
         });
     }
     @Override
     protected void onActivityResult(int requestCode, int resultCode, Intent data) {
         switch (requestCode){
             case  START_BLUETOOTH:
                 if (resultCode == Activity.RESULT_OK){
                     Toast.makeText(getApplicationContext(),"O bluetooth foi ativado", Toast.LENGTH_LONG).show();
                 }else{
                     Toast.makeText(getApplicationContext(),"O bluetooth não foi ativado o app será encerrado", Toast.LENGTH_LONG).show();
                     finish();
                 }
                 break;
             case SOLICITA_CONEXAO:
                 if (resultCode == Activity.RESULT_OK){
                     MAC = data.getExtras().getString(ListaDispositivos.ENDERECOMAC);
                     Toast.makeText(getApplicationContext(),"MAC FINAL:"+MAC, Toast.LENGTH_LONG).show();
                     meuDevice = meuBluetoothAdapter.getRemoteDevice(MAC);
                     try {
                         meuSocket = meuDevice.createRfcommSocketToServiceRecord(MEU_UUID);
                         meuSocket.connect();
                         conexao = true;
                         connectedThread =  new ConnectedThread(meuSocket);
                         connectedThread.start();
                         btnConexao.setText("Desconectar");
                         Toast.makeText(getApplicationContext(),"Você foi Conectado ao:"+MAC, Toast.LENGTH_LONG).show();
                     }catch (IOException erro){
                         conexao = false;
                         Toast.makeText(getApplicationContext(),"Ocorreu um erro!"+erro, Toast.LENGTH_LONG).show();
                     }
                 }else {
                     Toast.makeText(getApplicationContext(),"Falha ao Obter o MAC", Toast.LENGTH_LONG).show();
                 }
         }
     }
     Handler mHandler = new Handler() {
         @Override
         public void handleMessage(Message msg) {
         }
     };
     private class ConnectedThread extends Thread {
         private final InputStream mmInStream;
         private final OutputStream mmOutStream;

         public ConnectedThread(BluetoothSocket socket) {
             InputStream tmpIn = null;
             OutputStream tmpOut = null;

             try {
                 tmpIn = socket.getInputStream();
                 tmpOut = socket.getOutputStream();
             } catch (IOException e) { }

             mmInStream = tmpIn;
             mmOutStream = tmpOut;
         }


         public void run() {
             while (!stop) {
                 try {
                     int bytesAvailable  =  mmInStream.available();
                     byte[] buffer = new byte[bytesAvailable];
                     mmInStream.read(buffer);
                     if (bytesAvailable > 0) {
                         for (int i = 0; i < bytesAvailable; i++) {
                             byte b = buffer[i];
                             dados += (char) b;
                             if(buffer[i] == "#".getBytes()[0]){
                                 System.out.println("handler!!!");
                                 mHandler.post(new Runnable(){
                                     @Override
                                     public void run() {
                                         Toast.makeText(getApplicationContext(),dados, Toast.LENGTH_LONG).show();
                                         Locale locale = new Locale("pt", "BR");
                                         GregorianCalendar calendar = new GregorianCalendar();
                                         SimpleDateFormat formatador = new SimpleDateFormat("HH:mm:ss.S", locale);
                                         final String hora = formatador.format(calendar.getTime());
                                         file_save(dados+hora);
                                         dados = "";
                                     }});
                             }
                         }
                     }
                 } catch (Exception e) {
                     stop = true; e.printStackTrace();
                 }
             }
         }
         public void write(String dadosEnviar) {
             byte[] mgsBuffer = dadosEnviar.getBytes();
                 try {
                     mmOutStream.write(mgsBuffer);
                 } catch (IOException e) {
         }
         }
     }

     private void Mensagem(String msg)
     {
         Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
     }

     public void file_save(String loglog){
         String nomeArquivo = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/"+nomeDiretorio+"/"+"log_receive.txt";
         try{
             Writer arquivo = new BufferedWriter(new FileWriter(nomeArquivo, true));
             arquivo.append(loglog);
             arquivo.close();
         }
         catch (Exception e) {
             Mensagem("Erro : " + e.getMessage());
         }
     }
     public void file_save_send(String loglog){
         String nomeArquivo = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/"+nomeDiretorio+"/"+"log_send.txt";
         try{
             Writer arquivo = new BufferedWriter(new FileWriter(nomeArquivo, true));
             arquivo.append(loglog);
             arquivo.close();
         }
         catch (Exception e) {
             Mensagem("Erro : " + e.getMessage());
         }
     }

 }
