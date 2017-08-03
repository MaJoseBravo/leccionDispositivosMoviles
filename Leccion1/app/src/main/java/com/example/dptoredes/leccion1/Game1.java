package com.example.dptoredes.leccion1;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Game1 extends AppCompatActivity {
    int random = (int) (Math.random()*5);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game1);

        String user1;
        final TextView text1 = (TextView) findViewById(R.id.textView);
        final TextView numero = (TextView) findViewById(R.id.textView3);
        final Button btncontinue = (Button) findViewById(R.id.button7);
        final Button btn1 = (Button) findViewById(R.id.button2);
        final Button btn2 = (Button) findViewById(R.id.button3);
        final Button btn3 = (Button) findViewById(R.id.button4);
        final Button btn4 = (Button) findViewById(R.id.button5);
        final Button btn5 = (Button) findViewById(R.id.button6);
        
        user1 = getIntent().getStringExtra("user");
        text1.setText("Hola, " +user1);

        Intent intent = new Intent(Game1.this, Score.class);
        intent.putExtra("text", text1.getText().toString());
        startActivity(intent);


        numero.setText(random);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            pressed(view);
        }
    };

    private void pressed(View view) {
        switch(view.getId()){
            case R.id.button2:
                if(random == 1){
                    Toast.makeText(this, "Correcto!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(this, "Número Incorrecto.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.button3:
                if(random == 2){
                    Toast.makeText(this, "Correcto!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(this, "Número Incorrecto.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.button4:
                if(random == 3){
                    Toast.makeText(this, "Correcto!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(this, "Número Incorrecto.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.button5:
                if(random == 4){
                    Toast.makeText(this, "Correcto!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(this, "Número Incorrecto.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.button6:
                if(random == 5){
                    Toast.makeText(this, "Correcto!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(this, "Número Incorrecto.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void fnContinuar(View view){
        Intent intent = new Intent(Game1.this, Score.class);
        startActivity(intent);
    }

}
