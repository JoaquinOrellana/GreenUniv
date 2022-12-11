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

public class VistaPrincipalAdmin extends AppCompatActivity {
    private ImageView logout;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    String nameadmin;
    Fragment fragmentAlumRegis=new AdminGestionAlumRegisFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_principal_admin);
        BottomNavigationView bottomNavigationViewAdmin = findViewById(R.id.bottomNavigationAdmin);
        loadFragment(fragmentAlumRegis);
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
        TextView txtname=findViewById(R.id.txtviewNombAdmin);
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        String correoEstudi = currentUser.getEmail();
        firebaseFirestore.collection("estudiantes").document(correoEstudi).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                nameadmin=documentSnapshot.getString("nombre");
                txtname.setText(nameadmin);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(VistaPrincipalAdmin.this, "Error al obtener los datos", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayoutAdmin,fragment);
        transaction.commit();
    }

}