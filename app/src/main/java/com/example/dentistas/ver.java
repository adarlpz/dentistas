package com.example.dentistas;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import adaptador.adaptadorver;
import pojo.dentista;

public class ver extends AppCompatActivity {

    RecyclerView rv;
    Toolbar toolbar;
    SharedPreferences archivo;
    List<dentista> dentistasList = new ArrayList<>();
    adaptadorver adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ver);

        // Inicializar vistas
        archivo = this.getSharedPreferences("sesion", Context.MODE_PRIVATE);
        rv = findViewById(R.id.rv_lista);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Configurar RecyclerView
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new adaptadorver(this, dentistasList);
        rv.setAdapter(adapter);

        // Cargar datos
        cargarDatos();
    }

    private void cargarDatos() {
        String localhost = getString(R.string.localhost);
        String url = localhost + "obtener_dentistas.php";

        Log.d("URL", url); // Verificar URL

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Verificar el estado de la respuesta
                            String status = response.getString("status");
                            if (status.equals("success")) {
                                // Obtener el array "data" del JSON
                                JSONArray dataArray = response.getJSONArray("data");

                                dentistasList.clear();

                                for (int i = 0; i < dataArray.length(); i++) {
                                    JSONObject dentistaObj = dataArray.getJSONObject(i);

                                    // Crear un objeto dentista y llenarlo con los datos
                                    dentista dentistaItem = new dentista();
                                    dentistaItem.setNombrecompleto(dentistaObj.getString("nombre_completo"));
                                    dentistaItem.setEspecialidad(dentistaObj.getString("especialidad"));

                                    // Agregar el objeto a la lista
                                    dentistasList.add(dentistaItem);

                                    // Imprimir los datos en el log para verificar
                                    Log.d("Dentista", "Nombre: " + dentistaItem.getNombrecompleto());
                                    Toast.makeText(ver.this, "Nombre: " + dentistaItem.getNombrecompleto(), Toast.LENGTH_SHORT).show();
                                }

                                // Notificar al adaptador que los datos han cambiado
                                adapter.notifyDataSetChanged();
                            } else {
                                // Manejar el caso en que el servidor devuelva un error
                                String message = response.getString("message");
                                Toast.makeText(ver.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("JSONException", "Error al procesar el JSON: " + e.getMessage());
                            Toast.makeText(ver.this, "Error al procesar los datos", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VolleyError", "Error en la solicitud: " + error.getMessage());
                        Toast.makeText(ver.this, "Error de red: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        // Agregar la solicitud a la cola de peticiones
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

}
