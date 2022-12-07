package com.example.greenuniv;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity{


    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    FirebaseStorage storage;
    StorageReference StorRef;
    String imgurlCarnet;
    Button btnregister;
    ImageButton btnSubirCarnet;
    List<String> listaDocuments = new ArrayList<>();
    //Para la foto de perfil
    private final String fotoPerfilUrl="https://firebasestorage.googleapis.com/v0/b/greenuniv-3846c.appspot.com/o/FotoUsers%2Fimgperfildefecto.png?alt=media&token=2fd3c8f6-8ab4-481f-ab8c-e38250952404";
    private ProgressBar progressBar;
    ImageView imageView;

    int numero = (int)(Math.random()*11351+1);
    ActivityResultLauncher<Intent> launcherPhotos = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() ==RESULT_OK) {
                    Uri uri = result.getData().getData();
                    Intent intent=result.getData();
                    if(intent!=null){
                        try {
                            Bitmap bitmap= MediaStore.Images.Media.getBitmap(this.getContentResolver(),intent.getData());
                            //set bitmap on image view
                            imageView.setImageBitmap(bitmap);
                            StorageReference child = StorRef.child("photo" + numero + ".jpg");

                            child.putFile(uri)
                                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(
                                                    new OnCompleteListener<Uri>() {

                                                        @Override
                                                        public void onComplete(@NonNull Task<Uri> task) {
                                                            String fileLink = task.getResult().toString();
                                                            Log.d("url", fileLink);
                                                            imgurlCarnet=fileLink;

                                                        }
                                                    });
                                        }
                                    })
                                    .addOnFailureListener(e -> Log.d("msg-test", "error"))
                                    .addOnCompleteListener(task -> {
                                        if (task.isSuccessful()) {
                                            Log.d("msg-test", "ruta archivo: " + task.getResult());
                                        }
                                    });


                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Debe seleccionar un archivo", Toast.LENGTH_SHORT).show();
                }
            }
    );




    boolean exist = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        StorRef = storageReference.child("CarnetUniv");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        btnregister = findViewById(R.id.btn_registrarse);
        btnSubirCarnet=findViewById(R.id.imageButtonCamCarnet);
        EditText nombre = findViewById(R.id.editTextNameRegistro);
        EditText apellido = findViewById(R.id.editTextApellRegistro);
        EditText edad = findViewById(R.id.editTextEdadlRegistro);
        EditText correo = findViewById(R.id.editTextCorreoRegistro);
        EditText telefono = findViewById(R.id.editTextTelefonoRegistro);
        Spinner univ = findViewById(R.id.spinnerUniversidad);
        EditText passwd = findViewById(R.id.passwordRegis);
        EditText passwdrepe = findViewById(R.id.passwordRepeatRegis);
        String rol="universitario";

        progressBar=(ProgressBar) findViewById(R.id.progressBarRegister);
        progressBar.setVisibility(View.GONE);
        imageView=findViewById(R.id.imgCarnetSubido);
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean fine = true;
                String nombreString = nombre.getText().toString().trim();
                String apellidoString = apellido.getText().toString().trim();
                String edadString = edad.getText().toString().trim();
                String correoString = correo.getText().toString().trim();
                String telefonoString = telefono.getText().toString().trim();
                String univString = univ.getSelectedItem().toString();
                String passwordString = passwd.getText().toString();
                String passwordRepeString = passwdrepe.getText().toString();

                //validaciones
                if(nombreString.isEmpty()){
                    nombre.requestFocus();
                    nombre.setError("No dejar en blanco");
                    fine=false;
                }
                if(apellidoString.isEmpty()){
                    apellido.requestFocus();
                    apellido.setError("No dejar en blanco");
                    fine=false;
                }
                if(edadString.isEmpty()){
                    edad.requestFocus();
                    edad.setError("No dejar en blanco");
                    fine=false;
                }

                if(correoString.isEmpty()){
                    correo.requestFocus();
                    correo.setError("No dejar en blanco");
                    fine=false;
                }
                if(telefonoString.isEmpty()){
                    telefono.requestFocus();
                    telefono.setError("No dejar en blanco");
                    fine=false;
                }
                if(passwordString.isEmpty()){
                    passwd.requestFocus();
                    passwd.setError("No dejar en blanco");
                    fine=false;
                }
                if(passwordRepeString.isEmpty()){
                    passwdrepe.requestFocus();
                    passwdrepe.setError("No dejar en blanco");
                    fine=false;
                }
                //validacion contraseña

                if(!passwordString.equals(passwordRepeString)){
                    passwdrepe.requestFocus();
                    passwdrepe.setError("No coinciden las contraseñas");
                    fine=false;
                }

                if(fine){
                    registrarUsuario(nombreString,apellidoString,edadString,correoString,telefonoString,univString,passwordString,passwordRepeString,rol);
                }else{
                    Toast.makeText(RegisterActivity.this,"Debe llenar los campos correctamente",Toast.LENGTH_SHORT).show();
                }

            }
        });


        firebaseFirestore.collection("estudiantes").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!=null){
                    return;
                }
                for (QueryDocumentSnapshot document : value){
                    listaDocuments.add(document.getId());
                }
            }
        });

        firebaseFirestore.collection("admins").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!=null){
                    return;
                }
                for (QueryDocumentSnapshot document : value){
                    listaDocuments.add(document.getId());
                }
            }
        });


        //btn foto
        btnSubirCarnet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.setType("image/jpeg");
                launcherPhotos.launch(intent);

            }
        });

    }




    public void backLogin(View view){
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void registrarUsuario(String nombre,String apellido,String edad,String correo,String telefono,String univer,String password,String passwordrepe,String rol){
        if(listaDocuments.contains(correo)){
            exist = true;
        }

        if(exist){
            Toast.makeText(RegisterActivity.this,"Este correo ya tiene una cuenta asociada",Toast.LENGTH_SHORT).show();
        }else if(telefono.length()!=9){
            Toast.makeText(RegisterActivity.this,"El teléfono debe contener 9 dígitos",Toast.LENGTH_SHORT).show();
        }else if(univer.equals("Seleccione su Universidad:")){
            Toast.makeText(RegisterActivity.this,"Seleccione una universidad disponible",Toast.LENGTH_SHORT).show();
        }else if(imgurlCarnet==null){
            Toast.makeText(RegisterActivity.this,"Debe subir una foto de su carnet universitario",Toast.LENGTH_SHORT).show();
        }else {
            progressBar.setVisibility(View.VISIBLE);
            firebaseAuth.createUserWithEmailAndPassword(correo,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    Usuario user = new Usuario(nombre,apellido,edad,correo,telefono,univer,password,rol,fotoPerfilUrl,imgurlCarnet,0);
                    firebaseFirestore.collection("estudiantes").document(correo).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(RegisterActivity.this,"Su cuenta ha sido creada exitosamente",Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                            finish();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RegisterActivity.this,"Hubo un error al registrar la cuenta",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }

    }


}