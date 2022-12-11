package com.example.greenuniv;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ArticulosEstudianteListaAdapter  extends FirestoreRecyclerAdapter<Articulo, ArticulosEstudianteListaAdapter.ViewHolder> {
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    FragmentManager fm;
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */

    public ArticulosEstudianteListaAdapter(@NonNull FirestoreRecyclerOptions<Articulo> options,FragmentManager fm) {
        super(options);
        this.fm=fm;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listado_articulos_estudiante,parent,false);
        return new ViewHolder(v);
    }


    @Override
    protected void onBindViewHolder(@NonNull ArticulosEstudianteListaAdapter.ViewHolder holder, int position, @NonNull Articulo articulo) {
        DocumentSnapshot documentSnapshot = getSnapshots().getSnapshot(holder.getAdapterPosition());
        final String id = documentSnapshot.getId();
        holder.textViewNombArtia.setText(String.valueOf(articulo.getNombre()));
        holder.textViewEstadoArtia.setText("Estado:"+String.valueOf(articulo.getEstado()));
        Glide.with(holder.imageViewArticuloEstuda.getContext()).load(articulo.getImgurl()).into(holder.imageViewArticuloEstuda);
        holder.btnVerMasArtia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //send data fragment
                VermasdetallesArticuloFragment vermasdetallesArticuloFragment=new VermasdetallesArticuloFragment();
                Bundle bunlde = new Bundle();
                bunlde.putString("id",id);
                vermasdetallesArticuloFragment.setArguments(bunlde);
                vermasdetallesArticuloFragment.show(fm,"open fragment");
            }
        });
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNombArtia;
        TextView textViewEstadoArtia;
        ImageButton btnVerMasArtia;
        ImageView imageViewArticuloEstuda;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewNombArtia= itemView.findViewById(R.id.textViewNombArti);
            textViewEstadoArtia = itemView.findViewById(R.id.textViewEstadoArti);
            btnVerMasArtia = itemView.findViewById(R.id.btnVerMasArti);
            imageViewArticuloEstuda = itemView.findViewById(R.id.imageViewArticuloEstud);
        }
    }

}
