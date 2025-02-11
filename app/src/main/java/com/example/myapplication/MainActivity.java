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

public class MainActivity extends AppCompatActivity {
    TabHost tbh;
    Button btn;
    TextView tempVal;
    Spinner spn;
    Conversores objConversores = new Conversores();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tbh = findViewById(R.id.tbhConversor);
        tbh.setup();

        // Crear los tabs
        tbh.addTab(tbh.newTabSpec("Monedas").setContent(R.id.tabMonedas).setIndicator("MONEDAS", null));
        tbh.addTab(tbh.newTabSpec("Masa").setContent(R.id.tabMasa).setIndicator("MASA", null));
        tbh.addTab(tbh.newTabSpec("Volumen").setContent(R.id.tabVolumen).setIndicator("VOLUMEN", null));
        tbh.addTab(tbh.newTabSpec("Longitud").setContent(R.id.tabLongitud).setIndicator("LONGITUD", null));
        tbh.addTab(tbh.newTabSpec("Almacenamiento").setContent(R.id.tabAlmacenamiento).setIndicator("ALMACENAMIENTO", null));
        tbh.addTab(tbh.newTabSpec("Tiempo").setContent(R.id.tabTiempo).setIndicator("TIEMPO", null));
        tbh.addTab(tbh.newTabSpec("Transferencia de Datos").setContent(R.id.tabTransferencia).setIndicator("TRANSFERENCIA DE DATOS", null));

        btn = findViewById(R.id.btnCalcular);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int opcion = tbh.getCurrentTab(); // Obtener la pestaña seleccionada

                int de = 0, a = 0; // Variables para las posiciones seleccionadas en los Spinner

                // Obtener los valores de los Spinner según la pestaña seleccionada
                switch (opcion) {
                    case 0: // Monedas
                        spn = findViewById(R.id.spnDeMonedas);
                        de = spn.getSelectedItemPosition();
                        spn = findViewById(R.id.spnAMonedas);
                        a = spn.getSelectedItemPosition();
                        break;
                    case 1: // Masa
                        spn = findViewById(R.id.spnDeMasa);
                        de = spn.getSelectedItemPosition();
                        spn = findViewById(R.id.spnAMasa);
                        a = spn.getSelectedItemPosition();
                        break;
                    // Repite para otras pestañas
                }

                // Obtener la cantidad ingresada por el usuario
                tempVal = findViewById(R.id.txtCantidad);
                String cantidadTexto = tempVal.getText().toString();

                // Validar que el campo no esté vacío
                if (cantidadTexto.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Ingresa una cantidad", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Convertir la cantidad a double
                double cantidad = Double.parseDouble(cantidadTexto);

                // Realizar la conversión
                double respuesta = objConversores.convertir(opcion, de, a, cantidad);

                // Mostrar el resultado
                tempVal = findViewById(R.id.lblRespuesta);
                tempVal.setText("Respuesta: " + respuesta);
            }
        });
    }
}
class Conversores{
    // Valores de ejemplo, deberías llenarlos con los valores correctos
    double[][] valores = {
            {1, 0.98, 7.73}, // Monedas (Ejemplo)
            {1, 1000, 2.20462, 35.274, 0.001, 15432.4, 0.157473, 0.01, 1e+6, 1e+9}, // Masa (Kilos, Gramos, Libras, onzas)
            {1, 1000, 0.264172}, // Volumen (Litros, Mililitros, Galones)
            {1, 0.001, 0.453592}, // Longitud (Metros, Centímetros, Pulgadas)
            {1, 1024, 1048576}, // Almacenamiento (Bytes, Kilobytes, Megabytes)
            {1, 60, 3600}, // Tiempo (Segundos, Minutos, Horas)
            {1, 0.125, 0.000125}, // Transferencia de Datos (Bit, Byte, Kbps)
    };

    public double convertir(int opcion, int de, int a, double cantidad){
        return valores[opcion][a] / valores[opcion][de] * cantidad;
    }
}

