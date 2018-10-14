package com.example.zhang.control;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    Socket socket = null;
    static private DataInputStream is;
    static private DataOutputStream os;
    String buffer = "";
    TextView txt1;
    Button send;
    EditText ed1;
    String geted1;
    Toast toast;
    final String host = "192.168.31.82";
    public Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x11) {
                Bundle bundle = msg.getData();
                txt1.append("server:" + bundle.getString("msg")+"\n");
            }
        }

    };
    public void showTextToast(String msg) {

        if (toast != null) toast.cancel();

        toast = Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT);

        toast.show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt1 = (TextView) findViewById(R.id.txt1);
        send = (Button) findViewById(R.id.send);
        ed1 = (EditText) findViewById(R.id.ed1);
        new MyThread("建立连接").start();
        send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //showTextToast("sendmessagenow");
                geted1 = ed1.getText().toString()+"\n";
                txt1.append("client:" + geted1);
                // 启动线程 向服务器发送和接收信息
                new MyThread(geted1).start();
            }
        });

    }

    class MyThread extends Thread {

        public String txt1;
        public char txt2;

        public MyThread(String str) {
            txt1 = str;
        }
        @Override
        public void run() {
            // 定义消息
            Message msg = new Message();
            msg.what = 0x11;
            Bundle bundle = new Bundle();
            bundle.clear();
            //Looper.prepare();
            //showTextToast("run");
            //Looper.loop();
            try {
                // 连接服务器 并设置连接超时为5秒
                socket = new Socket(host, 3333);


                // 获取输入输出流
                OutputStream ou = socket.getOutputStream();
                BufferedReader bff = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                //is = new DataInputStream(socket.getInputStream()); //hyq
                //os = new DataOutputStream(socket.getOutputStream()); //hyq

                String line = null;
                buffer = "";
                bundle.clear();
                // 向服务器发送信息
                ou.write((txt1+"\n").getBytes("gbk"));
                ou.flush();
               // Looper.prepare();
                //showTextToast("reading server");
               // Looper.loop();
                //os.writeChar('A'); //hyq
                //txt2=is.readChar();
                //send.setText(txt2);
                InputStream reader = socket.getInputStream();
                byte[] bbuf = new byte[1000];
                int count = reader.read(bbuf);
                buffer = new String(bbuf,"gbk");
                bundle.putString("msg", buffer);
                msg.setData(bundle);
                // 发送消息 修改UI线程中的组件
                myHandler.sendMessage(msg);
                // 关闭各种输入输出流
                bff.close();
                reader.close();
                ou.close();
                //is.close();
                //os.close();
                socket.close();

                // 读取发来服务器信息

                /*while ((line = bff.readLine()) != null) {
                    buffer = line + buffer;
                    Looper.prepare();
                    //System.exit(0);
                    showTextToast("recieved");
                    Looper.loop();
                }*/


            } catch (SocketTimeoutException aa) {
                // 连接超时 在UI界面显示消息
                //showTextToast( "服务器连接失败！请检查网络是否打开");
                //msg.setData(bundle);
                // 发送消息 修改UI线程中的组件
               //myHandler.sendMessage(msg);
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }
}
