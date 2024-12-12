
package com.example.dentistas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import adaptador.adaptadoreliminar;
import pojo.dentista;
public class ver2 extends AppCompatActivity {
    RecyclerView rv2;
    Context context;
    Toolbar toolbar;
    SharedPreferences archivo;
    List<dentista> dentistasList = new ArrayList<>();
    adaptadoreliminar adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver2);

        archivo = this.getSharedPreferences("sesion", MODE_PRIVATE);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rv2 = findViewById(R.id.rv_lista2);

        adapter = new adaptadoreliminar(this, dentistasList);
        rv2.setLayoutManager(new LinearLayoutManager(this));
        rv2.setAdapter(adapter);

        cargarDatos();
    }
    public void del(View view) {
        List<dentista> itemsParaEliminar = new ArrayList<>();
        List<Integer> posicionesSeleccionadas = new ArrayList<>();

        for (int i = 0; i < dentistasList.size(); i++) {
            dentista item = dentistasList.get(i);
            if (item.isChecked()) {
                itemsParaEliminar.add(item);
                posicionesSeleccionadas.add(i);
            }
        }
        if (!itemsParaEliminar.isEmpty()) {
            for (dentista item : itemsParaEliminar) {
                // eliminarDentista(item); // Asegúrate de implementar este método correctamente
            }
            dentistasList.removeAll(itemsParaEliminar);
            adapter.notifyDataSetChanged();
            Toast.makeText(ver2.this, "Elementos eliminados exitosamente", Toast.LENGTH_SHORT).show();

            // Mostrar las posiciones de los elementos eliminados
            for (int posicion : posicionesSeleccionadas) {
                Toast.makeText(ver2.this, "Elemento eliminado en posición: " + posicion, Toast.LENGTH_SHORT).show();
                eliminarDentista(posicion);
            }
        } else {
            Toast.makeText(ver2.this, "No has seleccionado ningún elemento para eliminar", Toast.LENGTH_SHORT).show();
        }
    }
    private void eliminarDentista(int id) {
        String localhost = getString(R.string.localhost);
        String url = localhost + "eliminar_dentista.php?id=" + id;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        String status = response.getString("status");
                        if (!status.equals("success")) {
                            String message = response.getString("message");
                            Toast.makeText(ver2.this, message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(ver2.this, "Error al procesar la respuesta", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    Log.e("VolleyError", "Error en la solicitud: " + error.getMessage());
                    Toast.makeText(ver2.this, "Error de red: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }
    private void cargarDatos() {
        String localhost = getString(R.string.localhost);
        String url = localhost + "obtener_dentistas.php";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        String status = response.getString("status");
                        if (status.equals("success")) {
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
                            Toast.makeText(ver2.this, message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(ver2.this, "Error al procesar los datos", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    Log.e("VolleyError", "Error en la solicitud: " + error.getMessage());
                    Toast.makeText(ver2.this, "Error de red: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
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
