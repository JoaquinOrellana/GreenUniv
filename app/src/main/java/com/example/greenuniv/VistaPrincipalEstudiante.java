package com.example.greenuniv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class VistaPrincipalEstudiante extends AppCompatActivity {
    private ImageView logout;
    String name;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    Fragment fragmentAlumRegis=new EstudianteRegistrarArticulo();
    String universidad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_principal_estudiante);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        logout=findViewById(R.id.btnLogOutEstudiante);
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigationEstudiante);

        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        String correoEstudi = currentUser.getEmail();
        firebaseFirestore.collection("estudiantes").document(correoEstudi).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                universidad=documentSnapshot.getString("universidad");
                TextView txtviewUnivEstuda=findViewById(R.id.txtviewUnivEstud);
                txtviewUnivEstuda.setText(universidad);
                TextView txtviewNombEstuda=findViewById(R.id.txtviewNombEstud);
                txtviewNombEstuda.setText(documentSnapshot.getString("nombre"));
                loadFragment(new EstudianteListadoArticulosFragment(universidad));

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(VistaPrincipalEstudiante.this, "Error al obtener los datos", Toast.LENGTH_SHORT).show();
            }
        });




        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.btn_listado_articulos:
                    loadFragment(new EstudianteListadoArticulosFragment(universidad));
                    return true;
                case R.id.btn_my_articles:
                    loadFragment(new EstudianteListadoMisArticulosFragment(universidad));
                    return true;
                case R.id.btn_registrar:
                    loadFragment(fragmentAlumRegis);
                    return true;
                case R.id.btn_historial:
                    //loadFragment(fragmentSolicTI);
                    return true;
                case R.id.btn_perfil_estudiante:
                    //loadFragment(fragmentSolicTI);
                    return true;
                default:
                    return false;
            }

        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(VistaPrincipalEstudiante.this, MainActivity.class));
                finish();
            }
        });




    }


    public void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayoutEstudiante,fragment);
        transaction.commit();
    }


}