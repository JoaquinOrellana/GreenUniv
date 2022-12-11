package com.example.greenuniv;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MisArticulosEstudianteListaAdapter extends FirestoreRecyclerAdapter<Articulo, MisArticulosEstudianteListaAdapter.ViewHolder> {

    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();

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

        holder.textViewSoliArtia.setText("Solicitudes:"+String.valueOf("2"));
        Glide.with(holder.imageViewMisArticuloEstuda.getContext()).load(articulo.getImgurl()).into(holder.imageViewMisArticuloEstuda);
        holder.btnVerDetallesSolisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //send data fragment
                VermasdetallesArticuloFragment vermasdetallesArticuloFragment=new VermasdetallesArticuloFragment();
                Bundle bunlde = new Bundle();
                bunlde.putString("id",id);
                //vermasdetallesArticuloFragment.setArguments(bunlde);
                //vermasdetallesArticuloFragment.show(fm,"open fragment");
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
