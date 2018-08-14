package com.example.momo.capstone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manual_mode);

        final int maxFollowDist = 10; //seconds
        final int maxSpeed = 200; //km/h

        Button switchauto = (Button) findViewById(R.id.switchauto);
        switchauto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //action
                setContentView(R.layout.activity_main);

                Button disPlusBtn = (Button) findViewById(R.id.distPlusBtn);
                disPlusBtn.setOnClickListener(new View.OnClickListener() {
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
            }
        });


    }
}
