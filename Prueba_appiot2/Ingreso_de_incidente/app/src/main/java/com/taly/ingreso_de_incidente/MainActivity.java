package com.taly.ingreso_de_incidente;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements  SensorEventListener {

    Spinner spLista;
    Button  btnGrabar, btnActualizar, btnEliminar,btnlistare;
    EditText editRut;
    EditText editNombre;
    EditText editDescripcion;
    TextView x, txtmensaje;
    private SensorEvent event;
    private FirebaseFirestore mFirestore;




    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingreso_incidente);


        mFirestore = FirebaseFirestore.getInstance();
//DEfinimos las variables
        editRut=(EditText)findViewById(R.id.Editrute);
        editNombre=(EditText)findViewById(R.id.editNombre);
        editDescripcion=(EditText)findViewById(R.id.editDesc);
        btnGrabar=(Button)findViewById(R.id.btnListares);
        btnActualizar=(Button)findViewById(R.id.btnActualizar);
        btnEliminar = (Button) findViewById(R.id.btnatras);
        btnlistare = (Button)findViewById(R.id.btnListares);

        spLista=(Spinner) findViewById(R.id.spLista);
        x = (TextView)findViewById(R.id.xID);
        txtmensaje=(TextView)findViewById(R.id.txtmensaje);



        btnlistare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), IncidenteListar.class);
                startActivity(i);
            }
        });

//Agregamos el sensor vertical
        super.onResume();
        SensorManager sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> sensors = sm.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if (sensors.size() > 0) //verificamos que exista acelerometro
        {
            sm.registerListener((SensorEventListener) this, sensors.get(0), SensorManager.SENSOR_DELAY_GAME);
        }



       //Agregamos ArrayAdapter para mostrar la listas
       ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.lista, android.R.layout.simple_list_item_1);
        spLista.setAdapter(adapter);
        //Agregamos la funcion del boton Grabar incidente

       btnGrabar.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.N)
            public void onClick(View view){

                String rut = editRut.getText().toString().trim();
                String nombre = editNombre.getText().toString().trim();
                String descripcion = editDescripcion.getText().toString().trim();

                if (rut.isEmpty() && nombre.isEmpty() && descripcion.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Ingresar los Datos", Toast.LENGTH_SHORT).show();
                }else{
                    incident(rut,nombre,descripcion);

                    //Mostrara mensaje a la hora de presionar el boton Grabar incidente
                    AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);
                    alerta.setMessage("¿Está seguro que desea grabar?")
                            .setCancelable(false)
                            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {



                                    //Capturamps la fecha y hora actual del sistemas al presionar el boton si
                                    DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm:ss z");

                                    String FechaHora = dateFormat.format(new Date());

                                    System.out.println(FechaHora);
                                    TextView textViewDate = findViewById(R.id.textview_date);
                                    textViewDate.setText(FechaHora);
                                }


                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                    AlertDialog titulo = alerta.create();
                    titulo.setTitle("Advertencia");
                    titulo.show();
                }

            }

       });


    }

    private void incident(String rut, String nombre, String descripcion) {
        Map<String, Object> incidente = new HashMap<>();
        incidente.put("rut", rut);
        incidente.put("nombre", nombre);
        incidente.put("descripcion",descripcion);

        mFirestore.collection("incidentes").add(incidente).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getApplicationContext(), "Creadosss", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error al ingresar", Toast.LENGTH_SHORT).show();
            }
        });



    }

    public void onSensorChanged(SensorEvent event) {
        Integer x_entero;


        String texto_mensaje;


        x_entero = (int) (event.values[SensorManager.DATA_X]);

        txtmensaje.setText(" ");
        if ((x_entero==0)){
            texto_mensaje="La pantalla esta en modo vertical";
            txtmensaje.setText(texto_mensaje);
        }


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }




}