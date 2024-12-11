package com.example.dentistas;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

public class cardview extends AppCompatActivity {
    Button llamar;
    private Toolbar toolbar;
    SharedPreferences archivo;
    private TextView nomCompleto, licencia, fechanacimiento, telefono, email, direccion, calificacion, especialidad, horaapertura, horacierre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardview);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        archivo = this.getSharedPreferences("sesion", Context.MODE_PRIVATE);
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
        llamar = findViewById(R.id.llamar);

        llamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llamar();
            }
        });

        Intent intent = getIntent();
        int idDentista = intent.getIntExtra("posicion", 0);

        if (idDentista != -1) {
            // cargar los datos del dentista desde el servidor
            cargarDatosDentista(idDentista);
        } else {
            Toast.makeText(this, "Error al obtener el dentista seleccionado", Toast.LENGTH_SHORT).show();
        }
    }
    private void llamar() {
        Intent llamada = new Intent(Intent.ACTION_CALL);
        llamada.setData(Uri.parse(  "tel: "+ telefono.getText().toString()));
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.CALL_PHONE
            }, 10);
        }
        startActivity(llamada);
    }
    private void cargarDatosDentista(int idDentista) {
        String localhost = getString(R.string.localhost);
        String url = localhost + "obtener_dentista.php?id_dentista=" + idDentista;

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString("status").equals("success")) {
                                JSONObject dentista = response.getJSONObject("data");

                                nomCompleto.setText(dentista.getString("nombre_completo"));
                                licencia.setText(dentista.optString("licencia"));
                                fechanacimiento.setText(dentista.optString("fecha_nacimiento"));
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