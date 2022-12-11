package com.example.greenuniv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    EditText editTextemailLogueo, editTextpasswordLogueo;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        //credenciales
        editTextemailLogueo = findViewById(R.id.editTextCorreoLogin);
        editTextpasswordLogueo = findViewById(R.id.passwordLogin);
        progressBar=(ProgressBar) findViewById(R.id.progressBarLogin);
        progressBar.setVisibility(View.GONE);

    }


    public void btnIngresar(View view){
        String email=editTextemailLogueo.getText().toString().trim();
        String password=editTextpasswordLogueo.getText().toString();
        if(email.isEmpty()){
            editTextemailLogueo.setError("Correo es obligatorio");
            editTextemailLogueo.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextemailLogueo.setError("Ingrese correo válido");
            editTextemailLogueo.requestFocus();
            return;
        }

        if(password.isEmpty()){
            editTextpasswordLogueo.setError("Contraseña es obligatorio");
            editTextpasswordLogueo.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);



        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){
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
                                                    Toast.makeText(LoginActivity.this,"No existe una cuenta asociada al correo",Toast.LENGTH_SHORT).show();
                                                }else{

                                                    startActivity(new Intent(LoginActivity.this, VistaPrincipalAdmin.class));
                                                    finish();
                                                    Toast.makeText(LoginActivity.this,"Bienvenido Administrador",Toast.LENGTH_SHORT).show();

                                                }
                                            }
                                        }
                                    });
                                }else{
                                    DocumentSnapshot ds =task.getResult().getDocuments().get(0);
                                    Double verificado=ds.getDouble("verificado");
                                    Log.d("msjtest",String.valueOf(verificado));
                                    if(String.valueOf(verificado).equals("1.0")){

                                        startActivity(new Intent(LoginActivity.this, VistaPrincipalEstudiante.class));
                                        finish();
                                        Toast.makeText(LoginActivity.this,"Bienvenido Estudiante",Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(LoginActivity.this,"El administrador aun no ha validado su cuenta",Toast.LENGTH_SHORT).show();
                                    }


                                }
                            }
                        }
                    });
                }else{
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this,"Correo o contraseña incorrecta",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }




}