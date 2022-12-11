package com.example.greenuniv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    DatabaseReference referenceUser,referenceTi,referenceAdmin;
    FirebaseFirestore firebaseFirestore;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.SplashTheme);
        try {
            Thread.sleep(1700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        progressBar=(ProgressBar) findViewById(R.id.progressBarMain);
        progressBar.setVisibility(View.GONE);
    }


    public void btnInicioSesion(View view){
        FirebaseAuth auth=FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        progressBar.setVisibility(View.VISIBLE);
        if(auth.getCurrentUser()!=null){
            String email=auth.getCurrentUser().getEmail();
            firebaseFirestore.collection("estudiantes").whereEqualTo("correo",email).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    progressBar.setVisibility(View.GONE);
                    if(task.isSuccessful()){
                        System.out.println(task.getResult().isEmpty());
                        if(task.getResult().isEmpty()){
                            firebaseFirestore.collection("admins").whereEqualTo("correo",email).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if(task.isSuccessful()){
                                        System.out.println(task.getResult().isEmpty());
                                        if(task.getResult().isEmpty()){
                                            Toast.makeText(MainActivity.this,"No existe una cuenta asociada al correo",Toast.LENGTH_SHORT).show();
                                        }else{

                                            startActivity(new Intent(MainActivity.this, VistaPrincipalAdmin.class));
                                            finish();
                                            Toast.makeText(MainActivity.this,"Bienvenido Administrador",Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                }
                            });
                        }else{
                            startActivity(new Intent(MainActivity.this, VistaPrincipalEstudiante.class));
                            finish();
                            Toast.makeText(MainActivity.this,"Bienvenido Estudiante",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

        }else{
            progressBar.setVisibility(View.GONE);
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

    }

    public void btnRegistro(View view){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

}