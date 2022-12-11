package com.example.greenuniv;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EstudianteRegistrarArticulo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EstudianteRegistrarArticulo extends Fragment {
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    FirebaseStorage storage;
    StorageReference StorRef;
    ImageView imageView;
    String imgurlFotoArti;
    Button btnregister;
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

    public EstudianteRegistrarArticulo() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EstudianteRegistrarArticulo.
     */
    // TODO: Rename and change types and number of parameters
    public static EstudianteRegistrarArticulo newInstance(String param1, String param2) {
        EstudianteRegistrarArticulo fragment = new EstudianteRegistrarArticulo();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Random random = new Random();
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_estudiante_registrar_articulo, container, false);
        storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        StorRef = storageReference.child("FotosArticulos");
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        String correoEstudi = currentUser.getEmail();
        firebaseFirestore = FirebaseFirestore.getInstance();
        btnregister = view.findViewById(R.id.btnAddArtiRegis);
        btnSubirFoto=view.findViewById(R.id.imageButtonCamFotoArti);
        imageView=view.findViewById(R.id.imagenRegistroArti);
        EditText nombre = view.findViewById(R.id.editTextNameArti);
        EditText estado = view.findViewById(R.id.editTextEstadoArti);
        EditText precio = view.findViewById(R.id.editTextPrecioArti);
        EditText descripcion = view.findViewById(R.id.editTextDescripArti);
        CheckBox checkBoxInterc=view.findViewById(R.id.checkBoxIntercamb);
        CheckBox checkBoxVenta=view.findViewById(R.id.checkBoxVentaArti);
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


        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean fine = true;
                String nombreString = nombre.getText().toString().trim();
                String estadoString = estado.getText().toString().trim();
                String precioString = precio.getText().toString().trim();
                String descripString = descripcion.getText().toString().trim();

                char randomizedCharacter1 = (char) (random.nextInt(26) + 'A');
                char randomizedCharacter2 = (char) (random.nextInt(26) + 'A');
                char randomizedCharacter3 = (char) (random.nextInt(26) + 'A');
                char randomizedCharacter4 = (char) (random.nextInt(26) + 'A');
                char randomizedCharacter5 = (char) (random.nextInt(26) + 'A');
                String identificador=String.valueOf(randomizedCharacter1)
                        +String.valueOf(randomizedCharacter2)+String.valueOf(randomizedCharacter3)+String.valueOf(randomizedCharacter4)
                        +String.valueOf(randomizedCharacter5);
                //validaciones
                if(nombreString.isEmpty()){
                    nombre.requestFocus();
                    nombre.setError("No dejar en blanco");
                    fine=false;
                }
                if(estadoString.isEmpty()){
                    estado.requestFocus();
                    estado.setError("No dejar en blanco");
                    fine=false;
                }
                if(precioString.isEmpty()){
                    precio.requestFocus();
                    precio.setError("No dejar en blanco");
                    fine=false;
                }

                if(descripString.isEmpty()){
                    descripcion.requestFocus();
                    descripcion.setError("No dejar en blanco");
                    fine=false;
                }
                //disponibilidad
                String tipo;
                if(checkBoxInterc.isChecked() && !(checkBoxVenta.isChecked())){
                    tipo="1";
                }else if(checkBoxVenta.isChecked() && !(checkBoxInterc.isChecked())){
                    tipo="2";
                }else{
                    tipo="3";
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
                                    imgurlFotoArti= String.valueOf(uri);
                                    Articulo articulo=new Articulo();
                                    articulo.setNombre(nombreString);
                                    articulo.setEstado(estadoString);
                                    articulo.setPrecio(precioString);
                                    articulo.setDescripcion(descripString);
                                    articulo.setTipo(tipo);
                                    articulo.setDuenio(correoEstudi);
                                    articulo.setImgurl(imgurlFotoArti);
                                    articulo.setUniversidad(universidad);
                                    firebaseFirestore.collection("articulos").document(identificador).set(articulo).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {

                                            //4. se resetean los valores
                                            nombre.setText("");
                                            estado.setText("");
                                            precio.setText("");
                                            descripcion.setText("");
                                            AppCompatActivity activity = (AppCompatActivity) getContext();
                                            activity.getSupportFragmentManager().beginTransaction()
                                                    .setReorderingAllowed(true)
                                                    .replace(R.id.frameLayoutEstudiante,new EstudianteListadoArticulosFragment(universidad))
                                                    .addToBackStack(null).commit();
                                        }
                                    });

                                    Toast.makeText(getContext(), "Articulo registrado correctamente", Toast.LENGTH_SHORT).show();
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