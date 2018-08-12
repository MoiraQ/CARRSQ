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
}
