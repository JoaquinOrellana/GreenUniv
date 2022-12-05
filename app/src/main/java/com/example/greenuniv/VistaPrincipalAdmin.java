package com.example.greenuniv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class VistaPrincipalAdmin extends AppCompatActivity {
    private Button logout;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_principal_admin);
        firebaseAuth = FirebaseAuth.getInstance();
        logout=findViewById(R.id.btnLogOutAdmin);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(VistaPrincipalAdmin.this, MainActivity.class));
                finish();
            }
        });


    }
}