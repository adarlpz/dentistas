package com.example.dentistas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
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
                                    dentista dentistaItem = new dentista();
                                    dentistaItem.setNombrecompleto(dentistaObj.getString("nombre_completo"));
                                    dentistaItem.setEspecialidad(dentistaObj.getString("especialidad"));

                                    dentistasList.add(dentistaItem);
                                }
                                adapter.notifyDataSetChanged();
                            } else {
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
