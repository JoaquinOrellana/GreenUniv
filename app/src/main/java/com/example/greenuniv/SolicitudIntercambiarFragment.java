package com.example.greenuniv;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Random;


public class SolicitudIntercambiarFragment extends Fragment {
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    FirebaseStorage storage;
    StorageReference StorRef;
    ImageView imageView;
    String imgurlFotoArtiInter;
    Button btnIntercambio;
    String universidad;
    ImageButton btnSubirFoto;
    Bitmap imgBitMap;
    boolean exist = false;
    int numero = (int)(Math.random()*11351+1);
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SolicitudIntercambiarFragment() {
        // Required empty public constructor
    }

    String id;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            id=getArguments().getString("id");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Random random = new Random();
        View view=inflater.inflate(R.layout.fragment_solicitud_intercambiar, container, false);
        storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        StorRef = storageReference.child("FotosArticulosSoliInter");
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        String correoEstudi = currentUser.getEmail();
        firebaseFirestore = FirebaseFirestore.getInstance();
        btnIntercambio = view.findViewById(R.id.btnAddArtiInter);
        btnSubirFoto=view.findViewById(R.id.imageButtonCamFotoInterArti);
        imageView=view.findViewById(R.id.imagenInterArti);
        EditText nombre = view.findViewById(R.id.editTextNameArtiInter);
        EditText precio = view.findViewById(R.id.editTextPrecioArtiInter);
        firebaseFirestore.collection("estudiantes").document(correoEstudi).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                universidad=documentSnapshot.getString("universidad");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Error al obtener los datos", Toast.LENGTH_SHORT).show();
            }
        });

        btnSubirFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,1);
            }
        });


        btnIntercambio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean fine = true;
                String nombreString = nombre.getText().toString().trim();
                String precioString = precio.getText().toString().trim();

                char randomizedCharacter1 = (char) (random.nextInt(26) + 'A');
                char randomizedCharacter2 = (char) (random.nextInt(26) + 'A');
                char randomizedCharacter3 = (char) (random.nextInt(26) + 'A');
                char randomizedCharacter4 = (char) (random.nextInt(26) + 'A');
                char randomizedCharacter5 = (char) (random.nextInt(26) + 'A');
                char randomizedCharacter6 = (char) (random.nextInt(26) + 'A');
                char randomizedCharacter7 = (char) (random.nextInt(26) + 'A');
                String identificador=String.valueOf(randomizedCharacter1)
                        +String.valueOf(randomizedCharacter2)+String.valueOf(randomizedCharacter3)+String.valueOf(randomizedCharacter4)
                        +String.valueOf(randomizedCharacter5)+String.valueOf(randomizedCharacter6)+String.valueOf(randomizedCharacter7);
                //validaciones
                if(nombreString.isEmpty()){
                    nombre.requestFocus();
                    nombre.setError("No dejar en blanco");
                    fine=false;
                }

                if(precioString.isEmpty()){
                    precio.requestFocus();
                    precio.setError("No dejar en blanco");
                    fine=false;
                }


                if(imgBitMap==null){
                    fine=false;
                    Toast.makeText(getContext(), "Debe subir una foto del articulo a registrar", Toast.LENGTH_SHORT).show();
                }


                if(fine){

                    StorageReference child = StorRef.child("photo" + numero + ".jpg");
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    imgBitMap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] data = baos.toByteArray();
                    child.putBytes(data).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            child.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    imgurlFotoArtiInter= String.valueOf(uri);
                                    Solicitud solicitud=new Solicitud();
                                    solicitud.setArticuloIntercambiar(nombreString);
                                    solicitud.setMonto(precioString);
                                    solicitud.setTipoSoli("Intercambio");
                                    solicitud.setUniver(universidad);
                                    solicitud.setNombreSolicitante(correoEstudi);
                                    firebaseFirestore.collection("articulos").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            String nameduenio= documentSnapshot.getString("duenio");
                                            solicitud.setNombreEstud(nameduenio);
                                            solicitud.setArticuloinicial(id);

                                            firebaseFirestore.collection("solicitudes").document(identificador).set(solicitud).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {

                                                    //4. se resetean los valores
                                                    nombre.setText("");
                                                    precio.setText("");
                                                    Toast.makeText(getContext(), "Solicitud de intercambio generada correctamente", Toast.LENGTH_SHORT).show();
                                                    AppCompatActivity activity = (AppCompatActivity) getContext();
                                                    activity.getSupportFragmentManager().beginTransaction()
                                                            .setReorderingAllowed(true)
                                                            .replace(R.id.frameLayoutEstudiante,new EstudianteListadoArticulosFragment(universidad))
                                                            .addToBackStack(null).commit();
                                                }
                                            });


                                        }
                                    });





                                }
                            });
                        }
                    });


                }else{
                    Toast.makeText(getContext(), "Debe llenar correctamente los datos pedidos", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == 1){
            Bundle extras = data.getExtras();
            imgBitMap = (Bitmap) extras.get("data");
            imageView.setBackground(null);
            imageView.setImageBitmap(imgBitMap);

        }
    }
}