package com.example.dentistas;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import pojo.datos;

public class MainActivity extends AppCompatActivity {
    EditText nomCompleto, licencia, fechanacimiento, telefono, email, direccion, calificacion, horaapertura, horacierre, item10;
    Spinner especialidad;
    Toolbar toolbar;
    Button guardar, limpiar;
    SharedPreferences archivo;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    datos dat = new datos();
    Integer pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        archivo = this.getSharedPreferences("sesion", Context.MODE_PRIVATE);
        nomCompleto = findViewById(R.id.item0);
        licencia = findViewById(R.id.item1);
        fechanacimiento = findViewById(R.id.item2);
        telefono = findViewById(R.id.item3);
        email = findViewById(R.id.item4);
        direccion = findViewById(R.id.item5);
        calificacion = findViewById(R.id.item6);
        especialidad = findViewById(R.id.item7);
        horaapertura = findViewById(R.id.item8);
        horacierre = findViewById(R.id.item9);
        guardar = findViewById(R.id.guardar);
        limpiar = findViewById(R.id.limpiar);
        item10= findViewById(R.id.item10);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        especialidad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                pos = i; // El índice seleccionado
                item10.setText(especialidad.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?>adapterView) {
            }
        });
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardar();
            }
        });
        limpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpiar();
            }
        });
        fechanacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fechanacimiento();
            }
        });
        horaapertura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                horaapertura();
            }
        });
        horacierre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                horacierre();
            }
        });
        mDateSetListener= new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayofMonth) {
                dat.dia=dayofMonth;
                dat.mes=month+1;
                dat.ano=year;
                String cadena;
                cadena = " "+dat.dia+"/"+dat.mes+"/"+dat.ano;
                fechanacimiento.setText(cadena);
            }
        };
    }
    private void horaapertura() {
        int hr, min;
        Calendar actual = Calendar.getInstance();
        hr = actual.get(Calendar.HOUR_OF_DAY);
        min = actual.get(Calendar.MINUTE);
        TimePickerDialog tpd = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourofday, int minute) {
                dat.minutos = minute;
                dat.horas = hourofday;
                String cadena;
                cadena = " " + dat.horas + ":" + dat.minutos;
                horaapertura.setText(cadena);
            }
        },hr,min,true);
        tpd.show();
    }
    private void horacierre() {
        int hr, min;
        Calendar actual = Calendar.getInstance();
        hr = actual.get(Calendar.HOUR_OF_DAY);
        min = actual.get(Calendar.MINUTE);
        TimePickerDialog tpd = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourofday, int minute) {
                dat.minutos = minute;
                dat.horas = hourofday;
                String cadena;
                cadena = " " + dat.horas + ":" + dat.minutos;
                horacierre.setText(cadena);
            }
        },hr,min,true);
        tpd.show();
    }
    private void fechanacimiento() {
        int dia, mes, ayo;
        Calendar actual=Calendar.getInstance();
        dia=actual.get(Calendar.DAY_OF_MONTH);
        mes=actual.get(Calendar.MONTH);
        ayo=actual.get(Calendar.YEAR);
        DatePickerDialog datPD = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayofmonth) {
                dat.dia=dayofmonth;
                dat.mes=month + 1; //los meses empiezan en 0
                dat.ano=year;
                String cadena;

                cadena = " " + dat.dia + "/" + dat.mes + "/" + dat.ano;
                fechanacimiento.setText(cadena);
            }
        }, ayo, mes, dia);
        datPD.show();
    }
    private void limpiar() {
        nomCompleto.setText("");
        licencia.setText("");
        fechanacimiento.setText("");
        telefono.setText("");
        email.setText("");
        direccion.setText("");
        calificacion.setText("");
        horaapertura.setText("");
        horacierre.setText("");
        especialidad.setSelection(0);
        Toast.makeText(this,"Entradas limpiadas",Toast.LENGTH_SHORT).show();
    }
    private void guardar() {
        String localhost = getString(R.string.localhost);
        String url = localhost + "registrar_dentista.php";

        String nombre = nomCompleto.getText().toString();
        String lic = licencia.getText().toString();
        String fecha = fechanacimiento.getText().toString();
        String telefon = telefono.getText().toString();
        String mail = email.getText().toString();
        String dir = direccion.getText().toString();
        String cali = calificacion.getText().toString();
        String especialidad = item10.getText().toString();
        String horaApertura = horaapertura.getText().toString();
        String horaCierre = horacierre.getText().toString();

        if (nombre.isEmpty() || lic.isEmpty() || fecha.isEmpty() || telefon.isEmpty() || mail.isEmpty() ||
                dir.isEmpty() || cali.isEmpty() || horaApertura.isEmpty() || horaCierre.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
        }

        // Crear un objeto JSON con los datos del formulario
        Map<String, String> params = new HashMap<>();
        params.put("nombre_completo", nombre);
        params.put("licencia", lic);
        params.put("fecha_nacimiento", fecha);
        params.put("telefono", telefon);
        params.put("email", mail);
        params.put("direccion", dir);
        params.put("calificacion", cali);
        params.put("especialidad", especialidad);
        params.put("hora_apertura", horaApertura);
        params.put("hora_cierre", horaCierre);

        JSONObject jsonObject = new JSONObject(params);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Procesar la respuesta JSON
                            String status = response.getString("status");
                            String message = response.getString("message");

                            if (status.equals("success")) {
                                Toast.makeText(MainActivity.this, "Datos guardados correctamente: " + message, Toast.LENGTH_SHORT).show();
                                limpiar(); // Limpiar los campos después de guardar
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Error al procesar la respuesta", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error al guardar los datos: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }
    @Override
    public void onOptionsMenuClosed(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        super.onOptionsMenuClosed(menu);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.opc1){
            Intent cambio = new Intent(this, ver.class);
            startActivity(cambio);
        }
        if(item.getItemId()==R.id.opc2){
            Intent cambio = new Intent(this, MainActivity.class);
            startActivity(cambio);
        }
        if(item.getItemId()==R.id.opc3){
            Intent cambio = new Intent(this, modificar.class);
            startActivity(cambio);
        }
        if(item.getItemId()==R.id.opc4){
            Intent cambio = new Intent(this, ayuda.class);
            startActivity(cambio);
        }
        if(item.getItemId()==R.id.opc5){
            Intent cambio = new Intent(this, autor.class);
            startActivity(cambio);
        }
        if(item.getItemId()==R.id.opc6){
            Intent cambio = new Intent(this, ver2.class);
            startActivity(cambio);
        }
        if(item.getItemId()==R.id.wazaa){
            if(archivo.contains("id_usuario"))  {
                SharedPreferences.Editor editor =  archivo.edit();
                editor.remove("id_usuario");
                editor.commit();
                Intent x = new Intent(this, inicio.class);
                startActivity(x);
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}