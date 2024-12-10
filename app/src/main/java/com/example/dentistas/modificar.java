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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import global.info;
import pojo.datos;
import pojo.dentista;

public class modificar extends AppCompatActivity {
    EditText nomCompleto, licencia, fechanacimiento, telefono, email, direccion, calificacion, horaapertura, horacierre, item10;
    Spinner spinner;
    Button anterior, actualizar, siguiente;
    Integer posicion = 1;
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
        //especialidad = findViewById(R.id.item7);
        horaapertura = findViewById(R.id.horaapertura);
        horacierre = findViewById(R.id.horacierre);
        anterior = findViewById(R.id.anterior);
        actualizar = findViewById(R.id.actualizar);
        siguiente = findViewById(R.id.siguiente);

        toolbar = findViewById(R.id.toolbar);
        item10 = findViewById(R.id.item10);
        setSupportActionBar(toolbar);

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

//        especialidad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                String position, nombreSeleccion;
//
//                position = especialidad.getItemAtPosition(i).toString();
//                item10.setText(position);
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

        siguiente();
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
        Integer tamaño = info.lista.size();
        if (tamaño == 0) {
            Toast.makeText(this, "Lista vacía", Toast.LENGTH_SHORT).show();
            return;
        }

        posicion = (posicion + 1) % tamaño; // si sobrepasa vuelve a cero
        //mostrarEquipo();
    }
    private void actualizar() {
        if (nomCompleto.getText().toString().trim().isEmpty() ||
                licencia.getText().toString().trim().isEmpty() ||
                fechanacimiento.getText().toString().trim().isEmpty() ||
                telefono.getText().toString().trim().isEmpty() ||
                email.getText().toString().trim().isEmpty() ||
                direccion.getText().toString().trim().isEmpty() ||
                calificacion.getText().toString().trim().isEmpty() ||
                horaapertura.getText().toString().trim().isEmpty() ||
                horacierre.getText().toString().trim().isEmpty()) {

            Toast.makeText(this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show();
        } else  {
            dentista equipoActual = info.lista.get(posicion);
            equipoActual.setNombrecompleto(nomCompleto.getText().toString());
            equipoActual.setLicencia(licencia.getText().toString());
            equipoActual.setFechanacimiento(fechanacimiento.getText().toString());
            equipoActual.setTelefono(telefono.getText().toString());
            equipoActual.setEmail(email.getText().toString());
            equipoActual.setDireccion(direccion.getText().toString());
            equipoActual.setCalificacion(calificacion.getText().toString());
            //equipoActual.setEspecialidad(item10.getText().toString());
            equipoActual.setHoraapertura(horaapertura.getText().toString());
            equipoActual.setHoracierre(horacierre.getText().toString());
            Toast.makeText(this, "Dentista actualizado-", Toast.LENGTH_SHORT).show();
        }
    }
    private void anterior() {
        Integer tamaño = info.lista.size();
        if (tamaño == 0) {
            Toast.makeText(this, "Lista vacía", Toast.LENGTH_SHORT).show();
            return;
        }
        posicion = (posicion - 1 + tamaño) % tamaño;
        //mostrarEquipo();
    }
    private void mostrarEquipo() {
        dentista equipoActual = info.lista.get(posicion);
        Toast.makeText(this, "Posición " + posicion, Toast.LENGTH_SHORT).show();

        int idDentista = 1;

        String localhost = getString(R.string.localhost);
        String url = localhost + "obtener_dentista.php?id_dentista=" + idDentista;

        // Crear una cola de solicitudes
        RequestQueue queue = Volley.newRequestQueue(this);

        // Crear una solicitud JSON
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Verificar si la respuesta es exitosa
                            if (response.getString("status").equals("success")) {
                                JSONObject dentista = response.getJSONObject("data");

                                // Establecer los datos en los TextView
                                nomCompleto.setText(dentista.getString("nombre_completo"));
                                licencia.setText(dentista.optString("licencia", "N/A"));
                                fechanacimiento.setText(dentista.optString("fecha_nacimiento", "N/A"));
                                telefono.setText(dentista.getString("telefono"));
                                email.setText(dentista.getString("email"));
                                direccion.setText(dentista.getString("direccion"));
                                calificacion.setText(dentista.getString("calificacion"));
                                //especialidad.setText(dentista.getString("especialidad"));
                                horaapertura.setText(dentista.getString("hora_apertura"));
                                horacierre.setText(dentista.getString("hora_cierre"));
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
        // Agregar la solicitud a la cola
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