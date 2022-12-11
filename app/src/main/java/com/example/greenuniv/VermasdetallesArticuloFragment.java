package com.example.greenuniv;

import android.os.Bundle;

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

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class VermasdetallesArticuloFragment extends DialogFragment {

    String id;
    private FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();


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
        View view =inflater.inflate(R.layout.fragment_vermasdetalles_articulo, container, false);
        TextView name=view.findViewById(R.id.textViewNombreArti);
        TextView estado=view.findViewById(R.id.textViewEstado);
        TextView descripcion=view.findViewById(R.id.textViewDescripcion);
        TextView precio=view.findViewById(R.id.textViewPrecioAprox);
        ImageView imgview=view.findViewById(R.id.imageViewArticuloEstud);
        Button btnintercambiar=view.findViewById(R.id.buttonIntercamb);


        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseFirestore.collection("articulos").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String nameStr= documentSnapshot.getString("nombre");
                String estadoStr=documentSnapshot.getString("estado");
                String descripcioStr=documentSnapshot.getString("descripcion");
                String precioStr=documentSnapshot.getString("precio");
                String imgurlStr=documentSnapshot.getString("imgurl");
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



        return view;
    }
}