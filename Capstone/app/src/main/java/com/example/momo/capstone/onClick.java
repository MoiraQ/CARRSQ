package com.example.momo.capstone;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.io.*;
import java.net.*;



public class onClick extends AppCompatActivity {

    public static String server = "131.181.139.10";
    public static int port_id = 39501;
    public static Socket car;
    public BufferedReader in;
    public OutputStream out;

    public static void connectSocket() throws IOException {
        car = new Socket(server, port_id);

    }

    //receive speed&&follow distance constantly
    public void modeUpdater(){
        String msg = "Sup Fam";

        try {
            out = car.getOutputStream ( );
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.write (msg.getBytes ( ), 0, msg.length ( ));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            in = new BufferedReader (new InputStreamReader (car.getInputStream ( )));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String msg){
        try {
            out = car.getOutputStream ( );
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.write (msg.getBytes ( ), 0, msg.length ( ));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
