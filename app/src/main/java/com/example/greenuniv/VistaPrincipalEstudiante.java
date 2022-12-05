package com.example.greenuniv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class VistaPrincipalEstudiante extends AppCompatActivity {
    private Button logout;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_principal_estudiante);
        firebaseAuth = FirebaseAuth.getInstance();
        logout=findViewById(R.id.btnLogOutEstudiante);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(VistaPrincipalEstudiante.this, MainActivity.class));
                finish();
            }
        });
    }



}