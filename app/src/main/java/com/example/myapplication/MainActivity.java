package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.EditText;
import android.text.InputFilter;
import android.text.Spanned;



public class MainActivity extends AppCompatActivity {
    TabHost tbh;
    Button btn;
    TextView tempVal;
    Spinner spn;
    EditText txtCantidad;
    Conversores objConversores = new Conversores();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tbh = findViewById(R.id.tbhConversor);
        tbh.setup();

        // Crear los tabs
        tbh.addTab(tbh.newTabSpec("Area").setContent(R.id.tabArea).setIndicator("AREAS", null));
        tbh.addTab(tbh.newTabSpec("Agua").setContent(R.id.tabAgua).setIndicator("AGUA", null));

        txtCantidad = findViewById(R.id.txtCantidad);
        tempVal = findViewById(R.id.lblRespuesta);
        btn = findViewById(R.id.btnCalcular);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int opcion = tbh.getCurrentTab(); // Obtener la pestaña seleccionada

                if (opcion == 2) { // Si está en la pestaña de Agua
                    calcularPagoAgua();
                } else {
                    calcularConversion(opcion);
                }
            }
        });
    }

    private void calcularPagoAgua() {
        String consumoStr = txtCantidad.getText().toString();

        if (consumoStr.isEmpty()) {
            Toast.makeText(MainActivity.this, "Ingresa un consumo válido", Toast.LENGTH_SHORT).show();
            return;
        }

        int consumo = Integer.parseInt(consumoStr);
        double pagoTotal = calcularTarifaAgua(consumo);

        tempVal.setText("Total a pagar: $" + String.format("%.2f", pagoTotal));
    }

    private double calcularTarifaAgua(int consumo) {
        double cuotaFija = 6.0;
        double pagoTotal = cuotaFija;

        if (consumo > 28) {
            int excesoSobre28 = consumo - 28;
            pagoTotal += excesoSobre28 * 0.65;
            consumo = 28;
        }

        if (consumo > 18) {
            int excesoSobre18 = consumo - 18;
            pagoTotal += excesoSobre18 * 0.45;
        }

        return pagoTotal;
    }

    private void calcularConversion(int opcion) {
        int de = 0, a = 0;

        switch (opcion) {
            case 0: // MONEDAS
                spn = findViewById(R.id.spnDeArea);
                de = spn.getSelectedItemPosition();
                spn = findViewById(R.id.spnAArea);
                a = spn.getSelectedItemPosition();
                break;
            default:
                Toast.makeText(MainActivity.this, "Opción no válida", Toast.LENGTH_SHORT).show();
                return;
        }

        String cantidadTexto = txtCantidad.getText().toString();

        if (cantidadTexto.isEmpty()) {
            Toast.makeText(MainActivity.this, "Ingresa una cantidad", Toast.LENGTH_SHORT).show();
            return;
        }

        double cantidad;
        try {
            cantidad = Double.parseDouble(cantidadTexto);
        } catch (NumberFormatException e) {
            Toast.makeText(MainActivity.this, "Ingresa un número válido", Toast.LENGTH_SHORT).show();
            return;
        }

        double respuesta = objConversores.convertir(opcion, de, a, cantidad);
        tempVal.setText("Respuesta: " + String.format("%.2f", respuesta));
    }
}

class Conversores {
    double[][] valores = {
            {1, 0.97, 20.63, 0.81, 152.33, 1.59, 1.43, 0.91, 86.69, 7.31}, // Monedas
    };

    public double convertir(int opcion, int de, int a, double cantidad) {
        return valores[opcion][a] / valores[opcion][de] * cantidad;
    }
}
