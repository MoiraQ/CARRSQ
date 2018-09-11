package com.example.momo.capstone;

import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.os.StrictMode;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    //updateSpeed speed;
    //private boolean running = true;
    public int speed = 50;
    onClick connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manual_mode);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        connection = new onClick();
        try {
            connection.connectSocket();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void manualtoauto(View v){
        setContentView(R.layout.transition);

        TextView transition_text = (TextView) findViewById(R.id.transition_text);
        final TextView countdown = (TextView) findViewById(R.id.countdown);

        transition_text.setText("Switching to automated mode in...");



        new CountDownTimer(4000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int number = (int) millisUntilFinished / 1000;
                countdown.setText(String.valueOf(number));
            }
            @Override
            public void onFinish() {
                connection.sendMessage("switchstatus");
                setContentView(R.layout.activity_main);


                    final int minFollowDist = 1;
                    final int maxFollowDist = 10; //seconds
                    final int minSpeed = 0;
                    final int maxSpeed = 60; //km/h

                // micromanagement button declarations
                final Button distPlusBtn = (Button) findViewById(R.id.distPlusBtn);
                final Button distNegBtn = (Button) findViewById(R.id.distNegBtn);
                final Button spdPlusBtn = (Button) findViewById(R.id.spdPlusBtn);
                final Button spdNegBtn = (Button) findViewById(R.id.spdNegBtn);

                final TextView spdDisplay = (TextView) findViewById(R.id.spdDisplay);




                distPlusBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //action
                        TextView distDisplay = (TextView) findViewById(R.id.distDisplay);
                        int distInt = Integer.parseInt(distDisplay.getText().toString());

                        if (distInt < maxFollowDist) {
                            distInt++;
                            distDisplay.setText(distInt + "");
                        }

                        if (distInt >= maxFollowDist){
                            distPlusBtn.setVisibility(View.INVISIBLE);
                        }
                        if (distInt > minFollowDist){
                            distNegBtn.setVisibility(View.VISIBLE);
                        }

                    }
                });


                distNegBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //action
                        TextView distDisplay = (TextView) findViewById(R.id.distDisplay);
                        int distInt = Integer.parseInt(distDisplay.getText().toString());

                        if (distInt > minFollowDist) {
                            distInt--;
                            distDisplay.setText(distInt + "");
                        }

                        if (distInt < maxFollowDist){
                            distPlusBtn.setVisibility(View.VISIBLE);
                        }
                        if (distInt <= minFollowDist){
                            distNegBtn.setVisibility(View.INVISIBLE);
                        }

                    }
                });



                spdPlusBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //action

                        int spdInt = Integer.parseInt(spdDisplay.getText().toString());

                        if (spdInt < maxSpeed) {
                            spdInt++;
                            spdDisplay.setText(spdInt + "");
                        }

                        if (spdInt >= maxSpeed){
                            spdPlusBtn.setVisibility(View.INVISIBLE);
                        }
                        if (spdInt > minSpeed){
                            spdNegBtn.setVisibility(View.VISIBLE);
                        }
                    }
                });


                spdNegBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //action
                        TextView spdDisplay = (TextView) findViewById(R.id.spdDisplay);
                        int spdInt = Integer.parseInt(spdDisplay.getText().toString());

                        if (spdInt > minSpeed) {
                            spdInt--;
                            spdDisplay.setText(spdInt + "");
                        }

                        if (spdInt < maxSpeed){
                            spdPlusBtn.setVisibility(View.VISIBLE);
                        }
                        if (spdInt <= minSpeed){
                            spdNegBtn.setVisibility(View.INVISIBLE);
                        }
                    }
                });

                Button laneLeftBtn = (Button) findViewById(R.id.laneLeftBtn);
                Button laneRightBtn = (Button) findViewById(R.id.laneRightBtn);

                final Handler handler = new Handler();

                final int period = 10000; // repeat every 10 sec.

                Timer timer = new Timer();
                TimerTask running = (new TimerTask() {
                    public void run() {
                        handler.post(new Runnable() {
                            public void run() {
                                speed++;
                                spdDisplay.setText(speed + "");
                            }

                        });
                    }
                }
                    );
                //timer.schedule(running, period, 1000);




            }

        }.start();
    }

    public void autotomanual(View v){
        setContentView(R.layout.transition);

        TextView transition_text = (TextView) findViewById(R.id.transition_text);
        final TextView countdown = (TextView) findViewById(R.id.countdown);

        transition_text.setText("Switching to manual mode in...");

        new CountDownTimer(4000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int number = (int) millisUntilFinished / 1000;
                countdown.setText(String.valueOf(number));
            }

            @Override
            public void onFinish() {
                setContentView(R.layout.manual_mode);
                connection.sendMessage("switchstatus");
            }
        }.start();
    }
}
