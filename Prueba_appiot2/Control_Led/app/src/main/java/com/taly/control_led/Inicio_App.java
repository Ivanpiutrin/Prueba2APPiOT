package com.taly.control_led;


import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AlertDialog;

public class Inicio_App extends MainActivity{
//Definimos las variables
    Button btn_salir;
    ImageButton btn_blu;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio_app);
        btn_salir = (Button)findViewById(R.id.btnsalir);
        btn_blu = (ImageButton) findViewById(R.id.btn_Bluetooh);
        ///////////////////////

        //Ingresamos la Imagen button del bluetooh con su mensaje si desea activarlo
        btn_blu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Mostrara mensaje a la hora de presionar el boton bluetooh
                AlertDialog.Builder alerta = new AlertDialog.Builder(Inicio_App.this);
                alerta.setMessage("¿Quiere activar el bluetooh?")
                        .setCancelable(false)
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //finish();
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
        });




        //creamos el btn_salir con su señal de advertencia
        btn_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Mostrara mensaje a la hora de presionar el boton salir
                AlertDialog.Builder alerta = new AlertDialog.Builder(Inicio_App.this);
                alerta.setMessage("¿Está seguro que desea salir?")
                        .setCancelable(false)
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();

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

        });
    }
}
