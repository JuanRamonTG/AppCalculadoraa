package com.example.myapplication;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    Button btn;
    TextView lblRespuesta, lblNum2;
    EditText txtNum1, txtNum2;
    Spinner spn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Referencias a los elementos de la UI
        btn = findViewById(R.id.btnCalcular);
        lblRespuesta = findViewById(R.id.lblRespuesta);
        txtNum1 = findViewById(R.id.txtNum1);
        txtNum2 = findViewById(R.id.txtNum2);
        lblNum2 = findViewById(R.id.lblNum2);
        spn = findViewById(R.id.spnOpciones);

        // Evento para ocultar txtNum2 cuando no es necesario
        spn.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                if (position == 4 || position == 7 || position == 8) { // Factorial, Raíz Cuadrada, Raíz Cúbica
                    txtNum2.setVisibility(View.GONE);
                    lblNum2.setVisibility(View.GONE);
                } else {
                    txtNum2.setVisibility(View.VISIBLE);
                    lblNum2.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) { }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    double num1 = Double.parseDouble(txtNum1.getText().toString());
                    double num2 = 0;

                    if (txtNum2.getVisibility() == View.VISIBLE) {
                        num2 = Double.parseDouble(txtNum2.getText().toString());
                    }

                    double respuesta = 0.0;

                    switch (spn.getSelectedItemPosition()) {
                        case 0: // Suma
                            respuesta = num1 + num2;
                            break;
                        case 1: // Resta
                            respuesta = num1 - num2;
                            break;
                        case 2: // Multiplicación
                            respuesta = num1 * num2;
                            break;
                        case 3: // División
                            if (num2 != 0) {
                                respuesta = num1 / num2;
                            } else {
                                lblRespuesta.setText("Error: División por cero");
                                return;
                            }
                            break;
                        case 4: // Factorial
                            if (num1 >= 0 && num1 == (int) num1) {
                                respuesta = calcularFactorial((int) num1);
                            } else {
                                lblRespuesta.setText("Error: Factorial solo acepta enteros positivos");
                                return;
                            }
                            break;
                        case 5: // Porcentaje (num1 % de num2)
                            respuesta = (num1 * num2) / 100;
                            break;
                        case 6: // Exponenciación (num1^num2)
                            respuesta = Math.pow(num1, num2);
                            break;
                        case 7: // Raíz Cuadrada
                            if (num1 >= 0) {
                                respuesta = Math.sqrt(num1);
                            } else {
                                lblRespuesta.setText("Error: No se puede calcular raíz cuadrada de un número negativo");
                                return;
                            }
                            break;
                        case 8: // Raíz Cúbica
                            respuesta = Math.cbrt(num1);
                            break;
                        case 9: // Raíz n-ésima (num1^(1/num2))
                            if (num2 != 0) {
                                respuesta = Math.pow(num1, 1 / num2);
                            } else {
                                lblRespuesta.setText("Error: No se puede calcular raíz con índice 0");
                                return;
                            }
                            break;
                    }

                    lblRespuesta.setText("Respuesta: " + respuesta);

                } catch (Exception e) {
                    lblRespuesta.setText("Error: Entrada no válida");
                }
            }
        });
    }

    private int calcularFactorial(int n) {
        int resultado = 1;
        for (int i = 1; i <= n; i++) {
            resultado *= i;
        }
        return resultado;
    }
}