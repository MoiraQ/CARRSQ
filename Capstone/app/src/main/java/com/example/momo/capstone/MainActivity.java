package com.example.momo.capstone;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manual_mode);

<<<<<<< HEAD

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

=======
        final int minFollowDist = 1;
        final int maxFollowDist = 10; //seconds
        final int minSpeed = 0;
        final int maxSpeed = 200; //km/h

        Button switchauto = (Button) findViewById(R.id.switchauto);
        switchauto.setOnClickListener(new View.OnClickListener() {
>>>>>>> 42661d980379249a2741df25179cc761b5730437
            @Override
            public void onFinish() {
                setContentView(R.layout.activity_main);

                Button distPlusBtn = (Button) findViewById(R.id.distPlusBtn);
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

                    }
                });

                Button distNegBtn = (Button) findViewById(R.id.distNegBtn);
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

                    }
                });


                Button spdPlusBtn = (Button) findViewById(R.id.spdPlusBtn);
                spdPlusBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //action
                        TextView spdDisplay = (TextView) findViewById(R.id.spdDisplay);
                        int spdInt = Integer.parseInt(spdDisplay.getText().toString());

                        if (spdInt < maxSpeed) {
                            spdInt++;
                            spdDisplay.setText(spdInt + "");
                        }
                    }
                });

                Button spdNegBtn = (Button) findViewById(R.id.spdNegBtn);
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
                    }
                });

                Button laneLeftBtn = (Button) findViewById(R.id.laneLeftBtn);
                Button laneRightBtn = (Button) findViewById(R.id.laneRightBtn);
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

            }
        }.start();
    }
}
