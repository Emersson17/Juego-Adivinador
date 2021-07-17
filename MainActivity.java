package com.example.adivina;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int numeroAleatorio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instansciamos los id de la barra y el valor.
        SeekBar rango = findViewById(R.id.barraRango);
        TextView valorRango = findViewById(R.id.textRango);

        rango.setProgress(10);
        valorRango.setText("" + rango.getProgress());

        rango.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        valorRango.setText("" + progress); // Obtenemos el progreso de la barra.
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        Random aleatorio = new Random();
//                        Toast.makeText(MainActivity.this, "" + aleatorio.nextInt(seekBar.getProgress() + 1 ), Toast.LENGTH_SHORT).show();
                        numeroAleatorio = aleatorio.nextInt(seekBar.getProgress() + 1 );
                    }
                }
        );
    }

    public void verificarNumero(View v) {
        EditText verificable = findViewById(R.id.textNumero);
        int numeroVerificable = Integer.parseInt(verificable.getText().toString());

        if (numeroVerificable == numeroAleatorio) {
            Toast.makeText(this, "Felicidades, eres un ¡GANADOR!", Toast.LENGTH_SHORT).show();
        } else if (numeroVerificable > numeroAleatorio) {
            Toast.makeText(this, "El número es superio al número oculto", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "El número es inferior al número oculto", Toast.LENGTH_SHORT).show();
        }
    }
}