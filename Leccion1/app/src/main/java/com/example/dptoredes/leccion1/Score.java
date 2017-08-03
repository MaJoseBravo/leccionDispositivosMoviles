package com.example.dptoredes.leccion1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import static android.R.id.text1;

public class Score extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        final TextView usuario = (TextView) findViewById(R.id.textView4);

        usuario.setText("Hola, " +text1);


    }
}
