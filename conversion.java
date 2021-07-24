package com.example.adivina;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class conversion extends AppCompatActivity {
    int numeroAleatorio;
    final String[] datos = {"Dolar", "Euro", "Pesos Mexicanos"};

    private Spinner monedaActual;
    private Spinner monedaCambio;
    private EditText valorCambio;
    private TextView resultado;

    private final double factorDolarEuro = 0.84;
    final private double factorPesoDolar = 0.049;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversion3);

        // Instansciamos los id de la barra y el valor.
        SeekBar rango = findViewById(R.id.barraRango);
        TextView valorRango = findViewById(R.id.textRango);

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, datos);
        // Agregamos los id a con sus respectivos spinners
        monedaActual = findViewById(R.id.monedaActual);
        monedaActual.setAdapter(adaptador);
        monedaCambio = findViewById(R.id.monedaCambio);

        SharedPreferences preferencias = getSharedPreferences("Mis preferencias", Context.MODE_PRIVATE);

        String txpMonedaActual = preferencias.getString("monedaActual", "");
        String txpMonedaCambio = preferencias.getString("monedaCambio", "");

        if (!txpMonedaActual.equals("")) {
            int indice = adaptador.getPosition(txpMonedaActual);
            monedaActual.setSelection(indice);
        }

        if (!txpMonedaCambio.equals("")) {
            int indice = adaptador.getPosition(txpMonedaCambio);
            monedaCambio.setSelection(indice);
        }
    }

    public void clickConvertir(View v) {
        monedaActual = findViewById(R.id.monedaActual);
        monedaCambio = findViewById(R.id.monedaCambio);
        valorCambio = findViewById(R.id.textNumero);
        resultado = findViewById(R.id.resultado);
        // Obtenemos el resultado de lo seleccionado

        String monedaActualP = monedaActual.getSelectedItem().toString();
        String monedaCambioP = monedaCambio.getSelectedItem().toString();

        double valorCambioP = Double.parseDouble(valorCambio.getText().toString());
        double resultadoP = procesarConversion(monedaActualP, monedaCambioP, valorCambioP);

        if (resultadoP > 0) {
            resultado.setText(String.format("Por %5.2f %5, usted recibirá %5.2f %5", monedaActualP, resultadoP, monedaCambioP));
            valorCambio.setText("");

            SharedPreferences preferencias = getSharedPreferences("Mis preferencias", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferencias.edit();

            editor.putString("monedaActual", monedaActualP);
            editor.putString("monedaCambio", monedaCambioP);
            editor.commit();
        } else {
            resultado.setText(String.format("Usted recibirá"));
            Toast.makeText(this, "Las opciones elegidas no tienen un factor de conversión", Toast.LENGTH_SHORT).show();
        }
    }

    public double procesarConversion(String monedaActualP, String monedaCambioP, double valorCambioP) {
        double resultadoConversion = 0;

        switch (monedaActualP) {
            case "Dolar":
                if (monedaCambioP.equals("Euro")) {
                    resultadoConversion = valorCambioP * factorDolarEuro;
                }

                if (monedaCambioP.equals("Pesos Mexicanos")) {
                    resultadoConversion = valorCambioP / factorPesoDolar;
                }
                break;
            case "Euro":
                if (monedaCambioP.equals("Dolar")) {
                    resultadoConversion = valorCambioP / factorDolarEuro;
                }
                break;
            case "Pesos Mexicanos":
                if (monedaCambioP.equals("Dolar")) {
                    resultadoConversion = valorCambioP * factorPesoDolar;
                }
                break;
        }
        return resultadoConversion;
    }
}