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
                setContentView(R.layout.activity_main);

                Button disPlusBtn = (Button) findViewById(R.id.distPlusBtn);
                disPlusBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //action
                        TextView distText = (TextView) findViewById(R.id.distText);

                        int distInt = Integer.parseInt(distText.getText().toString());
                        int distIntNew = distInt + 1;

                        distText.setText(distIntNew + "");
                    }
                });
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
