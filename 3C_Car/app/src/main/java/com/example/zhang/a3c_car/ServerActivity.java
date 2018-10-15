package com.example.zhang.a3c_car;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.widget.TextView;

public class ServerActivity extends Activity {

    public static ServerSocket serverSocket = null;
    public static TextView mTextView, textView1;
    private String IP = "";
    Toast toast;
    String buffer = "";
    Integer cnt_trial=0;

    static private DataInputStream is;
    static private DataOutputStream os;

    public static Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            if (msg.what==0x11) {
                Bundle bundle = msg.getData();
                mTextView.append("client"+bundle.getString("msg")+"\n");
            }
        };
    };
    public void showTextToast(String msg) {

        if (toast != null) toast.cancel();

        toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);

        toast.show();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);
        mTextView = (TextView) findViewById(R.id.textsss);
        textView1 = (TextView) findViewById(R.id.textView1);
        IP = getlocalip();
        textView1.setText("IP addresss:"+IP);

        new Thread() {
            public void run() {
                Bundle bundle = new Bundle();
                bundle.clear();
                OutputStream output;
                String str = "通信成功";
                int cont = 0;
                try {
                    serverSocket = new ServerSocket(3333);
                    //Looper.prepare();
                    //showTextToast("server start");
                    //Looper.loop();

                    while (true) {
                        Message msg = new Message();
                        msg.what = 0x11;

                        try {
                            //Looper.prepare();
                            //showTextToast("accepted");
                            //Looper.loop();
                            Socket socket = serverSocket.accept();
                            //Looper.prepare();
                            //showTextToast("a");
                            //Looper.loop();
                            //is = new DataInputStream(socket.getInputStream());
                            //os = new DataOutputStream(socket.getOutputStream());

                            output = socket.getOutputStream();
                            output.write(cnt_trial.toString().getBytes("gbk"));
                            output.flush();
                            //socket.shutdownOutput();
                            cont++;
                            cnt_trial++;

                            BufferedReader bff = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                            //InputStream bff = socket.getInputStream();
                            String line = null;
                            buffer = "";

                            //byte[] buf = new byte[10];
                            //mTextView.append("waiting for client");
                            /*while ((line = bff.readLine()) != null) { //L
                                Looper.prepare();
                                showTextToast("reading client");
                                Looper.loop();
                                buffer = line + buffer;
                            }*/
                            InputStream reader = socket.getInputStream();
                            byte[] bbuf = new byte[1000];
                            int count = reader.read(bbuf);
                            buffer = new String(bbuf,"gbk");
                            //mTextView.append(buffer);
                            bundle.putString("msg", buffer);
                            msg.setData(bundle);
                            mHandler.sendMessage(msg);
                            mHandler.handleMessage(msg);
                            output.write("recievd\n".getBytes("gbk"));
                            output.flush();
                            bff.close();
                            reader.close();
                            output.close();
                            //char t;
                            //t=is.readChar();

                            //os.writeChar(t+1);
                            //is.close();
                            //os.close();
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                catch (IOException e1) {
                    // TODO Auto-generated catch block

                    e1.printStackTrace();
                }
            };
        }.start();
    }
    private String getlocalip(){
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        // Log.d(Tag, "int ip "+]\\]]]]]]ipAddress);
        if(ipAddress==0)return null;
        return ((ipAddress & 0xff)+"."+(ipAddress>>8 & 0xff)+"."
                +(ipAddress>>16 & 0xff)+"."+(ipAddress>>24 & 0xff));
    }


}

    /*
    private String getlocalip(){
        WifiManager wifiManager = (WifiManager)getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        // Log.d(Tag, "int ip "+ipAddress);
        if(ipAddress==0)return null;
        return ((ipAddress & 0xff)+"."+(ipAddress>>8 & 0xff)+"."
                +(ipAddress>>16 & 0xff)+"."+(ipAddress>>24 & 0xff));
    }
    */
