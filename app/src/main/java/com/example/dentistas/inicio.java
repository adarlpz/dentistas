package com.example.dentistas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class inicio extends AppCompatActivity {

    EditText user, pass;
    Button ingresar;
    Context context;
    SharedPreferences archivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_inicio);

        user = findViewById(R.id.user);
        pass = findViewById(R.id.pass);
        ingresar = findViewById(R.id.ingresar);
        archivo = this.getSharedPreferences("sesion", Context.MODE_PRIVATE);

        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingresar();
            }
        });

    }

    private void ingresar() {
        String url = "http://192.168.56.1/dentistas/ingreso.php?usr=";
        url = url+user.getText().toString();
        url = url+"&pass=";
        url = url+pass.getText().toString();

        JsonObjectRequest pet = new JsonObjectRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getInt("usr") != -1) {
                        Intent i = new Intent(inicio.this, MainActivity.class);
                        SharedPreferences.Editor editor = archivo.edit();
                        editor.putInt("id_usuario", response.getInt("usr"));
                        editor.commit();
                        startActivity(i);
                        finish();
                    } else {
                        user.setText("");
                        pass.setText("");
                    }
                    Toast.makeText(inicio.this, response.toString(), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(inicio.this, volleyError.getMessage().toString(), Toast.LENGTH_SHORT).show();
                Log.d("yo", volleyError.getMessage());
            }
        });
        RequestQueue lanzarPeticion = Volley.newRequestQueue(this);
        lanzarPeticion.add(pet);
//        if(user.getText().toString().equals("GUSA") && pass.getText().toString().equals("123456")){
//            Intent in = new Intent(this, MainActivity.class);
//            SharedPreferences.Editor editor = archivo.edit();
//            editor.putString("usuario", "GUSA");
//            editor.putString("contra", "123456");
//            editor.putBoolean("valido", true);
//            editor.apply();
//            //editor.commit();
//            startActivity(in);
//            finish();
//        }else{
//            user.setText("");
//            pass.setText("");
//            Toast.makeText(this, "incorrecto", Toast.LENGTH_LONG);
//        }
    }

}