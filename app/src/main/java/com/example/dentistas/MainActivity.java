package com.example.dentistas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import global.info;
import pojo.dentista;

public class MainActivity extends AppCompatActivity {
    EditText nomCompleto, licencia, fechanacimiento, telefono, email, direccion, calificacion, especialidad, horaapertura, horacierre;
    Toolbar toolbar;
    Button guardar, limpiar;
    SharedPreferences archivo;
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
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
    }
    private void limpiar() {
        nomCompleto.setText("");
        licencia.setText("");
        fechanacimiento.setText("");
        telefono.setText("");
        email.setText("");
        direccion.setText("");
        calificacion.setText("");
        especialidad.setText("");
        horaapertura.setText("");
        horacierre.setText("");
    }
    private void guardar() {
        dentista unequipo =new dentista();
        unequipo.setNombrecompleto(nomCompleto.getText().toString());
        unequipo.setLicencia(licencia.getText().toString());
        unequipo.setFechanacimiento(fechanacimiento.getText().toString());
        unequipo.setTelefono(telefono.getText().toString());
        unequipo.setEmail(email.getText().toString());
        unequipo.setDireccion(direccion.getText().toString());
        unequipo.setCalificacion(calificacion.getText().toString());
        unequipo.setEspecialidad(especialidad.getText().toString());
        unequipo.setHoraapertura(horaapertura.getText().toString());
        unequipo.setHoracierre(horacierre.getText().toString());
        info.lista.add(unequipo);
        Toast.makeText(this,"Guardando",Toast.LENGTH_SHORT).show();
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