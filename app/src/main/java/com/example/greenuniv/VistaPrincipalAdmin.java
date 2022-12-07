package com.example.greenuniv;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class VistaPrincipalAdmin extends AppCompatActivity {
    private ImageView logout;
    FirebaseAuth firebaseAuth;
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


    }
    public void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayoutAdmin,fragment);
        transaction.commit();
    }

}