package com.taly.control_led;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {


    Button btn_iniciar;
    Button btn_crear;
    Button btn_recuperar;
    Button btn_off;
    EditText Correo,Pass;
    
    FirebaseAuth mAuth;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        //Definimos la variables
        btn_iniciar = (Button) findViewById(R.id.btn_iniciar);
        btn_crear = (Button) findViewById(R.id.btn_crearCuenta);
        btn_recuperar = (Button) findViewById(R.id.btn_RecuperarPass);

        btn_off = (Button) findViewById(R.id.btn_off);

        Correo =  findViewById(R.id.editMail);
        Pass = findViewById(R.id.editPassword);
        mAuth = FirebaseAuth.getInstance();
        //Funcion de btn_iniciar
        btn_iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Emailuser = Correo.getText().toString().trim();
                String UserPassword = Pass.getText().toString().trim();


                if(Emailuser.isEmpty() && UserPassword.isEmpty()){
                    Toast.makeText( MainActivity.this, "Ingresa los datos",Toast.LENGTH_SHORT).show();
                }else{
                   loginUser(Emailuser, UserPassword);
                }

            }
        });
        //Funcion de btn_crear
        btn_crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent e = new Intent(getApplicationContext(), Crear_cuenta.class);
                startActivity(e);
            }
        });
//Funcion de btn_recuperar
        btn_recuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Recuperar_passw.class);
                startActivity(i);
            }
        });


        //Funcion de btn_off para salir de la aplicacion
        btn_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });
    }

    private void loginUser(String EmailUser, String UserPassword){
        mAuth.signInWithEmailAndPassword(EmailUser, UserPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    finish();
                    Intent i = new Intent(getApplicationContext(), Inicio_App.class);
                    startActivity(i);
                    Toast.makeText(MainActivity.this, "Inicio sesion Exitosa", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Error al Inicio sesion", Toast.LENGTH_SHORT).show();

            }
        });

    }

}