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
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import global.info;

public class cardview extends AppCompatActivity {
    Button llamar;
    Toolbar toolbar;
    TextView nomCompleto, licencia, fechanacimiento, telefono, email, direccion, calificacion, especialidad, horaapertura, horacierre;
    SharedPreferences archivo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cardview);

        archivo = this.getSharedPreferences("sesion", Context.MODE_PRIVATE);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        llamar = findViewById(R.id.llamar);
        nomCompleto =findViewById(R.id.nomCompleto);
        licencia = findViewById(R.id.licencia);
        fechanacimiento = findViewById(R.id.fechanacimiento);
        telefono = findViewById(R.id.telefono);
        email = findViewById(R.id.email);
        direccion = findViewById(R.id.direccion);
        calificacion = findViewById(R.id.calificacion);
        especialidad = findViewById(R.id.especialidad);
        horaapertura = findViewById(R.id.horaapertura);
        horacierre = findViewById(R.id.horacierre);

        llamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llamar();
            }
        });
        int posicion;
        posicion=getIntent().getIntExtra("posicion", -1);
        nomCompleto.setText(info.lista.get(posicion).getNombrecompleto());
        licencia.setText(info.lista.get(posicion).getLicencia());
        fechanacimiento.setText(info.lista.get(posicion).getFechanacimiento());
        telefono.setText(info.lista.get(posicion).getTelefono());
        email.setText(info.lista.get(posicion).getEmail());
        direccion.setText(info.lista.get(posicion).getDireccion());
        calificacion.setText(info.lista.get(posicion).getCalificacion());
        especialidad.setText(info.lista.get(posicion).getEspecialidad());
        horaapertura.setText(info.lista.get(posicion).getHoraapertura());
        horacierre.setText(info.lista.get(posicion).getHoracierre());
    }
    private void llamar() {
        Intent llamada = new Intent(Intent.ACTION_CALL);
        llamada.setData(Uri.parse(  "tel: "+ telefono.getText().toString()));
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.CALL_PHONE
            }, 10);
        }
        startActivity(llamada);
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