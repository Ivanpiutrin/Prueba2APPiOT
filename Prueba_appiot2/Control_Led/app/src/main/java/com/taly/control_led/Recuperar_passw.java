package com.taly.control_led;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Recuperar_passw extends MainActivity{


    Button btn_volver;
    Button btn_Ingresar;
    EditText CorreoRestablecer;
    FirebaseAuth mAuth;
    ProgressDialog mDialog;
    private String email = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recuperarpassword);

        mAuth = FirebaseAuth.getInstance();
        mDialog = new ProgressDialog(this);
        btn_volver = (Button) findViewById(R.id.btn_volver);
        btn_Ingresar = (Button)findViewById(R.id.btn_restablecer);
        CorreoRestablecer = (EditText) findViewById(R.id.EditCorreoRE);


        //MOSTRAMOS POR MENSAJE QUE EL USUARIO HA CAMBIADO LA CONTRASEÑA
        btn_Ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = CorreoRestablecer.getText().toString();

                if(!email.isEmpty()){
                    mDialog.setMessage("Espera un momento...");
                    mDialog.setCanceledOnTouchOutside(false);
                    mDialog.show();
                    resetPassword();
                }else {

                    Toast.makeText(Recuperar_passw.this, "¡Debe Imgresar email!", Toast.LENGTH_SHORT).show();
                }
            }
        });




        //Creamos el btn_volver para volver al inicio de sesión
        btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void resetPassword(){
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()){
                    Toast.makeText(Recuperar_passw.this, "Se ha enviado un correo para restablecer contraseña", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Recuperar_passw.this, "No se pudo enviar el correo electronico", Toast.LENGTH_SHORT).show();
                }
                mDialog.dismiss();
            }
        });

    }
}
