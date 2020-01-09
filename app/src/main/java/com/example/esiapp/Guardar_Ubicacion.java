package com.example.esiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Guardar_Ubicacion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardar__ubicacion);
    }

    public void Regresar_A_La_Ventana_Anterior(View view){
        Intent Ventana_Nueva = new Intent(this, Principal.class);
        startActivity(Ventana_Nueva);
    }

    public void Ir_Otra_Ventana(View view){
        Intent Ventana_Nueva = new Intent(this, Guardar_Ubicacion.class);
        startActivity(Ventana_Nueva);
    }
}
