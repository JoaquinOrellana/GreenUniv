package com.example.greenuniv;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Random;


public class VermasdetallesArticuloFragment extends DialogFragment {

    String id;
    private FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    String universidad;
    String duenio;
    String precioStr;
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
        firebaseFirestore=FirebaseFirestore.getInstance();
        View view =inflater.inflate(R.layout.fragment_vermasdetalles_articulo, container, false);
        TextView name=view.findViewById(R.id.textViewNombreArti);
        TextView estado=view.findViewById(R.id.textViewEstado);
        TextView descripcion=view.findViewById(R.id.textViewDescripcion);
        TextView precio=view.findViewById(R.id.textViewPrecioAprox);
        ImageView imgview=view.findViewById(R.id.imageViewArticuloEstud);
        Button btnintercambiar=view.findViewById(R.id.buttonIntercamb);
        Button buttonCompra=view.findViewById(R.id.buttonCompr);
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        String correoEstudi = currentUser.getEmail();
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



        firebaseFirestore.collection("articulos").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String nameStr= documentSnapshot.getString("nombre");
                String estadoStr=documentSnapshot.getString("estado");
                String descripcioStr=documentSnapshot.getString("descripcion");
                precioStr=documentSnapshot.getString("precio");
                String imgurlStr=documentSnapshot.getString("imgurl");
                String tipo=documentSnapshot.getString("tipo");
                 duenio=documentSnapshot.getString("duenio");
                if(tipo.equals("1")){
                    buttonCompra.setVisibility(View.GONE);
                }else if(tipo.equals("2")){
                    btnintercambiar.setVisibility(View.GONE);
                }else{
                    //nada
                }



                name.setText(nameStr);
                estado.setText("Estado:"+estadoStr);
                descripcion.setText(descripcioStr);
                precio.setText(precioStr);
                Glide.with(imgview.getContext()).load(imgurlStr).into(imgview);

            }
        });


        btnintercambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();

                SolicitudIntercambiarFragment soliIntercambFragm=new SolicitudIntercambiarFragment();
                Bundle bunlde = new Bundle();
                bunlde.putString("id",id);
                soliIntercambFragm.setArguments(bunlde);
                AppCompatActivity activity = (AppCompatActivity) getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutEstudiante,soliIntercambFragm).addToBackStack(null).commit();
            }
        });

        buttonCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
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
                Solicitud solicitud=new Solicitud();
                solicitud.setArticuloIntercambiar("");
                solicitud.setMonto(precioStr);
                solicitud.setTipoSoli("Compra");
                solicitud.setUniver(universidad);
                solicitud.setNombreSolicitante(correoEstudi);
                solicitud.setNombreEstud(duenio);
                solicitud.setArticuloinicial(id);

                firebaseFirestore.collection("solicitudes").document(identificador).set(solicitud).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        //4. se resetean los valores
                        Toast.makeText(getContext(), "Solicitud de compra generada correctamente", Toast.LENGTH_SHORT).show();
                        AppCompatActivity activity = (AppCompatActivity) getContext();
                        activity.getSupportFragmentManager().beginTransaction()
                                .setReorderingAllowed(true)
                                .replace(R.id.frameLayoutEstudiante,new EstudianteListadoArticulosFragment(universidad))
                                .addToBackStack(null).commit();
                    }
                });


            }
        });




        return view;
    }
}