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
    EditText txtCantidad; // Referencia al EditText
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

        // Referencia al EditText y agregar filtro de entrada
        txtCantidad = findViewById(R.id.txtCantidad);
        InputFilter[] filters = new InputFilter[1];
        filters[0] = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                // Permitir solo números y un punto decimal
                for (int i = start; i < end; i++) {
                    char c = source.charAt(i);
                    if (!Character.isDigit(c) && c != '.') {
                        return ""; // Rechazar caracteres no válidos
                    }
                }
                return null; // Aceptar caracteres válidos
            }
        };
        txtCantidad.setFilters(filters);

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
                    case 2: // Volumen
                        spn = findViewById(R.id.spnDeVolumen);
                        de = spn.getSelectedItemPosition();
                        spn = findViewById(R.id.spnAVolumen);
                        a = spn.getSelectedItemPosition();
                        break;
                    case 3: // Longitud
                        spn = findViewById(R.id.spnDeLongitud);
                        de = spn.getSelectedItemPosition();
                        spn = findViewById(R.id.spnALongitud);
                        a = spn.getSelectedItemPosition();
                        break;
                    case 4: // Almacenamiento
                        spn = findViewById(R.id.spnDeAlmacenamiento);
                        de = spn.getSelectedItemPosition();
                        spn = findViewById(R.id.spnAAlmacenamiento);
                        a = spn.getSelectedItemPosition();
                        break;
                    case 5: // Tiempo
                        spn = findViewById(R.id.spnDeTiempo);
                        de = spn.getSelectedItemPosition();
                        spn = findViewById(R.id.spnATiempo);
                        a = spn.getSelectedItemPosition();
                        break;
                    case 6: // Transferencia
                        spn = findViewById(R.id.spnDeTransferencia);
                        de = spn.getSelectedItemPosition();
                        spn = findViewById(R.id.spnATransferencia);
                        a = spn.getSelectedItemPosition();
                        break;
                    default:
                        Toast.makeText(MainActivity.this, "Opción no válida", Toast.LENGTH_SHORT).show();
                        return;
                }

                // Obtener la cantidad ingresada por el usuario
                String cantidadTexto = txtCantidad.getText().toString();

                // Validar que el campo no esté vacío
                if (cantidadTexto.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Ingresa una cantidad", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Convertir la cantidad a double
                double cantidad;
                try {
                    cantidad = Double.parseDouble(cantidadTexto);
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Ingresa un número válido", Toast.LENGTH_SHORT).show();
                    return;
                }

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
            {1, 0.97, 20.63, 0.81, 152.33, 1.59, 1.43, 0.91, 86.69, 7.31}, // Monedas (Ejemplo)
            {1, 1000, 2.20462, 35.274, 0.001, 15432.4, 0.157473, 0.01, 1e+6, 1e+9}, // Masa (Kilos, Gramos, Libras, onzas)
            {1, 1000, 0.264172, 33.814, 2.11338, 1.05669, 1000, 0.001, 0.0353147, 0.00130795}, // Volumen (Litros, Mililitros, Galones)
            {1, 100, 0.001, 0.000621371, 3.28084, 1.09361, 39.3701, 0.000539957, 1e+9, 1e+6}, // Longitud (Metros, Centímetros, Pulgadas)
            {1, 0.001, 1e-6, 1e-9, 1e-12, 1e-15, 1e-18, 1e-21, 1e-24, 8}, // Almacenamiento (Bytes, Kilobytes, Megabytes)
            {1, 0.0166667, 0.000277778, 1.15741e-5, 1.65344e-6, 3.80265e-7, 3.16888e-8, 1000, 1e+6, 1e+9}, // Tiempo (Segundos, Minutos, Horas)
            {1, 0.001, 1e-6, 1e-9, 1e-12, 0.125, 0.000125, 1.25e-7, 1.25e-10, 1.25e-13}, // Transferencia de Datos (Bit, Byte, Kbps)
    };

    public double convertir(int opcion, int de, int a, double cantidad){
        return valores[opcion][a] / valores[opcion][de] * cantidad;
    }
}

