package com.example.greenuniv;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class MisArticulosEstudianteListaAdapter extends FirestoreRecyclerAdapter<Articulo, MisArticulosEstudianteListaAdapter.ViewHolder> {

    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    String universidad;
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */

    public MisArticulosEstudianteListaAdapter(@NonNull FirestoreRecyclerOptions<Articulo> options) {
        super(options);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listado_misarticulos_estudiantes,parent,false);
        return new ViewHolder(v);
    }

    @Override
    protected void onBindViewHolder(@NonNull MisArticulosEstudianteListaAdapter.ViewHolder holder, int position, @NonNull Articulo articulo) {
        DocumentSnapshot documentSnapshot = getSnapshots().getSnapshot(holder.getAdapterPosition());
        final String id = documentSnapshot.getId();
        holder.textViewNombMiArtia.setText(String.valueOf(articulo.getNombre()));
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        String correoEstudi = currentUser.getEmail();
        firebaseFirestore.collection("estudiantes").document(correoEstudi).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                universidad=documentSnapshot.getString("universidad");
                firebaseFirestore.collection("solicitudes").whereEqualTo("nombreEstud",correoEstudi).whereEqualTo("univer",universidad)
                        .whereEqualTo("articuloinicial",id).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                               int tamanio=queryDocumentSnapshots.size();


                                holder.textViewSoliArtia.setText("Solicitudes:"+String.valueOf(tamanio));

                            }
                        });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Toast.makeText(getContext(), "Error al obtener los datos", Toast.LENGTH_SHORT).show();
            }
        });





        Glide.with(holder.imageViewMisArticuloEstuda.getContext()).load(articulo.getImgurl()).into(holder.imageViewMisArticuloEstuda);
        holder.btnVerDetallesSolisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //send data fragment
                VermasdetallesArticuloFragment vermasdetallesArticuloFragment=new VermasdetallesArticuloFragment();
                Bundle bunlde = new Bundle();
                bunlde.putString("id",id);
                vermasdetallesArticuloFragment.setArguments(bunlde);
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutEstudiante,vermasdetallesArticuloFragment).addToBackStack(null).commit();
            }
        });
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNombMiArtia;
        TextView textViewSoliArtia;
        ImageButton btnVerDetallesSolisa;
        ImageView imageViewMisArticuloEstuda;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewNombMiArtia= itemView.findViewById(R.id.textViewNombMiArti);
            textViewSoliArtia = itemView.findViewById(R.id.textViewSoliArti);
            btnVerDetallesSolisa = itemView.findViewById(R.id.btnVerDetallesSolis);
            imageViewMisArticuloEstuda = itemView.findViewById(R.id.imageViewMisArticuloEstud);
        }
    }
}
