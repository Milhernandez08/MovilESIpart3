package com.example.esiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    EditText Correo, pass;
    Button   entrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Correo = (EditText) findViewById(R.id.Correo);
        pass   = (EditText) findViewById(R.id.pass);
        entrar = (Button) findViewById(R.id.entrar);

        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Iniciar_Sesion();
            }
        });
    }

    public void Ir_Otra_Ventana(){
        Intent Ventana_Nueva = new Intent(this, Principal.class);
        startActivity(Ventana_Nueva);
    }

    public void Iniciar_Sesion(){
        String correo = Correo.getText().toString();
        String passw  = pass.getText().toString();

        if (correo.isEmpty()){
            Toast.makeText(this, "El campo del correo esta vacio", Toast.LENGTH_LONG).show();
        }
        if (passw.isEmpty()){
            Toast.makeText(this, "El campo del contraseña esta vacio", Toast.LENGTH_LONG).show();
        }
        if (!validar_Email(correo)){
            Toast.makeText(this, "Correo invalido", Toast.LENGTH_LONG).show();
        }
        else if (!passw.isEmpty()){
            Ir_Otra_Ventana();
        }
    }

    private boolean validar_Email(String email){
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
}
