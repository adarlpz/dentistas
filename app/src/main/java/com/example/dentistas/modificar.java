package com.example.dentistas;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import pojo.datos;

public class modificar extends AppCompatActivity {
    EditText nomCompleto, licencia, fechanacimiento, telefono, email, direccion, calificacion, horaapertura, horacierre, item10;
    Button anterior, actualizar, siguiente;
    Integer posicion;
    Integer tamaño;
    TextView tama;
    Toolbar toolbar;
    SharedPreferences archivo;
    Spinner especialidad;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    datos dat = new datos();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_modificar);

        archivo = this.getSharedPreferences("sesion", Context.MODE_PRIVATE);
        nomCompleto =findViewById(R.id.nomCompleto);
        licencia =findViewById(R.id.licencia);
        fechanacimiento =findViewById(R.id.fechanacimiento);
        telefono =findViewById(R.id.telefono);
        email =findViewById(R.id.email);
        direccion = findViewById(R.id.direccion);
        calificacion = findViewById(R.id.calificacion);
        especialidad = findViewById(R.id.item7);
        horaapertura = findViewById(R.id.horaapertura);
        horacierre = findViewById(R.id.horacierre);
        anterior = findViewById(R.id.anterior);
        actualizar = findViewById(R.id.actualizar);
        siguiente = findViewById(R.id.siguiente);
        toolbar = findViewById(R.id.toolbar);
        item10 = findViewById(R.id.item10);
        setSupportActionBar(toolbar);

        posicion = 0;
        tama = findViewById(R.id.tamaño);

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

        especialidad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String position;
                position = especialidad.getItemAtPosition(i).toString();
                item10.setText(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        anterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anterior();
            }
        });
        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizar();
            }
        });
        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                siguiente();
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
        mostrarEquipo();
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
    private void siguiente() {
        String tam;
        tam = tama.getText().toString();
        int tama = Integer.parseInt(tam);

        Toast.makeText(this, "" + posicion + "" + tama, Toast.LENGTH_SHORT).show();

        if (tama == 0) {
            Toast.makeText(this, "Lista vacía", Toast.LENGTH_SHORT).show();
            return;
        }
        posicion++;
        // Incrementamos la posición
        // Si la posición supera el tamaño de la lista, volvemos al inicio
        if (posicion >= tama) {
            posicion = 0;
        }

        // Mostrar el dentista actual
        mostrarEquipo();
    }
    private void actualizar() {
        // Obtener los datos editados del formulario
        String nombre = nomCompleto.getText().toString().trim();
        String lic = licencia.getText().toString().trim();
        String fecha = fechanacimiento.getText().toString().trim();
        String telefon = telefono.getText().toString().trim();
        String mail = email.getText().toString().trim();
        String dir = direccion.getText().toString().trim();
        String cali = calificacion.getText().toString().trim();
        String especialidad = item10.getText().toString().trim();
        String horaApertura = horaapertura.getText().toString().trim();
        String horaCierre = horacierre.getText().toString().trim();

        // Logcat para verificar los valores obtenidos
        Log.d("Actualizar", "Nombre: " + nombre);
        Log.d("Actualizar", "Licencia: " + lic);
        Log.d("Actualizar", "Fecha de nacimiento: " + fecha);
        Log.d("Actualizar", "Teléfono: " + telefon);
        Log.d("Actualizar", "Email: " + mail);
        Log.d("Actualizar", "Dirección: " + dir);
        Log.d("Actualizar", "Calificación: " + cali);
        Log.d("Actualizar", "Especialidad: " + especialidad);
        Log.d("Actualizar", "Hora de apertura: " + horaApertura);
        Log.d("Actualizar", "Hora de cierre: " + horaCierre);

        // Validar que no haya campos vacíos
        if (nombre.isEmpty() || lic.isEmpty() || fecha.isEmpty() || telefon.isEmpty() || mail.isEmpty() ||
                dir.isEmpty() || horaApertura.isEmpty() || horaCierre.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos obligatorios.", Toast.LENGTH_SHORT).show();
            Log.d("Actualizar", "Error: Hay campos vacíos.");
            return;
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

        // Logcat para verificar el objeto JSON
        Log.d("Actualizar", "JSON enviado: " + jsonObject.toString());

        String idDentistaString = String.valueOf(posicion + 1);
        String localhost = getString(R.string.localhost);
        String url = localhost + "actualizar_dentista.php?id=" + idDentistaString;

        // Logcat para verificar la URL
        Log.d("Actualizar", "URL: " + url);

        // Usar JsonObjectRequest para enviar los datos al servidor
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonObject,
                response -> {
                    try {
                        String status = response.getString("status");
                        String message = response.getString("message");

                        // Logcat para verificar la respuesta del servidor
                        Log.d("Actualizar", "Respuesta del servidor: " + response.toString());

                        if (status.equals("success")) {
                            Toast.makeText(this, "Datos actualizados correctamente.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Error: " + message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Error al procesar la respuesta del servidor.", Toast.LENGTH_SHORT).show();
                        Log.e("Actualizar", "Error al procesar la respuesta del servidor", e);
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(this, "Error en la solicitud: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("Actualizar", "Error en la solicitud", error);
                }
        );

        // Agregar la solicitud a la cola de Volley
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }


    private void anterior() {
        if (tamaño == 0) {
            Toast.makeText(this, "Lista vacía", Toast.LENGTH_SHORT).show();
            return;
        }
        posicion = (posicion - 1 + tamaño) % tamaño;
        Toast.makeText(this, "" + posicion, Toast.LENGTH_SHORT).show();
        mostrarEquipo();
    }
    private void mostrarEquipo() {
        String idDentistaString = String.valueOf(posicion);

        String localhost = getString(R.string.localhost);
        String url = localhost + "obtener_dentista.php?id_dentista=" + idDentistaString;

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString("status").equals("success")) {
                                JSONObject dentista = response.getJSONObject("data");
                                tamaño = response.getInt("total");

                                // Establecer los datos en los TextView
                                nomCompleto.setText(dentista.getString("nombre_completo"));
                                licencia.setText(dentista.optString("licencia", "N/A"));
                                fechanacimiento.setText(dentista.optString("fecha_nacimiento", "N/A"));
                                telefono.setText(dentista.getString("telefono"));
                                email.setText(dentista.getString("email"));
                                direccion.setText(dentista.getString("direccion"));
                                calificacion.setText(dentista.getString("calificacion"));
                                horaapertura.setText(dentista.getString("hora_apertura"));
                                horacierre.setText(dentista.getString("hora_cierre"));

                                // Obtener la especialidad del dentista desde el servidor
                                String especialidadActual = dentista.getString("especialidad");

                                // Buscar la posición de la especialidad en el adaptador del Spinner
                                ArrayAdapter adapter = (ArrayAdapter) especialidad.getAdapter();
                                int position = adapter.getPosition(especialidadActual);

                                // Seleccionar la especialidad en el Spinner
                                if (position >= 0) { // Asegurarse de que la posición sea válida
                                    especialidad.setSelection(position);
                                }

                                tama.setText("" + tamaño);
                            } else {
                                Toast.makeText(modificar.this, "No se encontró información del dentista", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(modificar.this, "Error al procesar los datos", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(modificar.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(request);
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
            Intent cambio = new Intent(this, autor.class);
            startActivity(cambio);
        }
        if(item.getItemId()==R.id.opc3){
            Intent cambio = new Intent(this, contacto.class);
            startActivity(cambio);
        }
        if(item.getItemId()==R.id.opc4){
            Intent cambio = new Intent(this, MainActivity.class);
            startActivity(cambio);
        }
        if(item.getItemId()==R.id.opc5){
            Intent cambio = new Intent(this, modificar.class);
            startActivity(cambio);
        }
        if(item.getItemId()==R.id.opc6){
            Intent cambio = new Intent(this, ver2.class);
            startActivity(cambio);
        }
        if(item.getItemId()==R.id.opc7){
            Intent cambio = new Intent(this, ayuda.class);
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