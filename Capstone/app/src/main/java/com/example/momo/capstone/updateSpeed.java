package com.example.momo.capstone;

import android.os.AsyncTask;
import android.widget.TextView;
import java.io.*;
import java.net.*;

public class updateSpeed extends AsyncTask {

    private TextView update;
    private int speed;
    private boolean run;

    protected void updateSpeed(TextView text){
        update = text;
        speed = 10;
        run = true;
    }


    @Override
    protected Object doInBackground(Object[] objects) {
        while(run){
            speed ++;
            update.setText(speed + "");

            try {
              this.wait(1000);
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
            //update.setText("We got here");
        }
        return null;
    }
/*
    //public Socket socket;
    public int speedWrite;
    TextView text;
    Boolean updatePlease;


    public updateSpeed(TextView object ){
        this.text = object;
        //this.socket = socket;
        this.speedWrite = 60;
        updatePlease = true;
    }

    public void setLoop(Boolean bool){
        updatePlease = bool;
    }

    @Override
    public void run(){
        //update speed

        //receive speed
        try {
            speed = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //update speed
/*
        while(updatePlease){
            speedWrite--;
            text.setText(speedWrite);*/
/*
            try {
                Thread.sleep(50000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }



        //update follow distance

    }
*/

}
