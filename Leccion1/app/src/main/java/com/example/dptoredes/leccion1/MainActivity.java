package com.example.dptoredes.leccion1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button ingresar = (Button) findViewById(R.id.button);
    }

    public void fnIngresar(View view){
        EditText user = (EditText) findViewById(R.id.editText);
        Intent intent = new Intent(MainActivity.this, Game1.class);
        intent.putExtra("user", user.getText().toString());
        startActivity(intent);
    }
}
