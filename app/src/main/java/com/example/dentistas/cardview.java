package com.example.dentistas;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
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

public class cardview extends AppCompatActivity {

    // Declarar los TextView y otros componentes
    private Toolbar toolbar;
    private TextView nomCompleto, licencia, fechanacimiento, telefono, email, direccion, calificacion, especialidad, horaapertura, horacierre;

    // URL del archivo PHP

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardview);

        // Inicializar la barra de herramientas
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Inicializar los TextView
        nomCompleto = findViewById(R.id.nomCompleto);
        licencia = findViewById(R.id.licencia);
        fechanacimiento = findViewById(R.id.fechanacimiento);
        telefono = findViewById(R.id.telefono);
        email = findViewById(R.id.email);
        direccion = findViewById(R.id.direccion);
        calificacion = findViewById(R.id.calificacion);
        especialidad = findViewById(R.id.especialidad);
        horaapertura = findViewById(R.id.horaapertura);
        horacierre = findViewById(R.id.horacierre);

        // Obtener el ID del dentista seleccionado del Intent
        Intent intent = getIntent();
        int idDentista = intent.getIntExtra("posicion", 0);

        Toast.makeText(this, " " + idDentista, Toast.LENGTH_SHORT).show();

        if (idDentista != -1) {
            // Cargar los datos del dentista desde el servidor
            cargarDatosDentista(idDentista);
        } else {
            Toast.makeText(this, "Error al obtener el dentista seleccionado", Toast.LENGTH_SHORT).show();
        }
    }

    private void cargarDatosDentista(int idDentista) {
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
                                especialidad.setText(dentista.getString("especialidad"));
                                horaapertura.setText(dentista.getString("hora_apertura"));
                                horacierre.setText(dentista.getString("hora_cierre"));
                            } else {
                                Toast.makeText(cardview.this, "No se encontró información del dentista", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(cardview.this, "Error al procesar los datos", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(cardview.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                    }
                });
        // Agregar la solicitud a la cola
        queue.add(request);
    }
}
