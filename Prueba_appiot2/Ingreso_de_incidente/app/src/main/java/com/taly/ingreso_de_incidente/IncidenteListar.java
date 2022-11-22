package com.taly.ingreso_de_incidente;

import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class IncidenteListar extends MainActivity {

    TextView trut, nombre, descripcion;
    private DatabaseReference mDatabase;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listar_incidente);

        trut =(TextView) findViewById(R.id.txtrut);
        nombre=(TextView) findViewById(R.id.txtnombre);
        descripcion=(TextView) findViewById(R.id.txtdescripcion);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("incidentes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String rut = snapshot.child("rut").getValue().toString();
                    trut.setText("rut"+rut);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
