package com.taly.control_led;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Crear_cuenta extends MainActivity{
    //Definimos las variables
    Button btn_exi;
    Button btn_crearC;
    TextView Correo;
    TextView contrasena;
    FirebaseFirestore mFirestore;
    FirebaseAuth mAuth;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crearcuenta);

        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        btn_exi = (Button)findViewById(R.id.btn_atras);
        btn_crearC = (Button)findViewById(R.id.btn_Eliminacion);
        Correo = (EditText)findViewById(R.id.editCorreoEle);
        contrasena = (EditText)findViewById(R.id.editTextPassword);





        //Creamos las funciones de los botones
        btn_crearC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String CorreoELE = Correo.getText().toString().trim();
                String Contrase = contrasena.getText().toString().trim();

                if(CorreoELE.isEmpty() && Contrase.isEmpty()){
                    Toast.makeText(Crear_cuenta.this, "Complete los datos", Toast.LENGTH_SHORT).show();
                }else{
                    Registros( CorreoELE, Contrase);
                }

            }



        });
        //Creamos la funcion de btn_exi de salir
        btn_exi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void Registros(String CorreoELE, String contrase) {
        mAuth.createUserWithEmailAndPassword(CorreoELE, contrase).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                String id = mAuth.getCurrentUser().getUid();
                Map<String, Object> map = new HashMap<>();
                map.put("id", id);
                map.put("Correo", CorreoELE);
                map.put("contrasena", contrase);

                mFirestore.collection("user").document(id).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        finish();
                        Intent e = new Intent(Crear_cuenta.this, MainActivity.class);
                        startActivity(e);

                        Toast.makeText(Crear_cuenta.this, "Cuenta creada con exito", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Crear_cuenta.this, "Error al guardar", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Crear_cuenta.this, "Registro Guardado", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
