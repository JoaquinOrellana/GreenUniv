package com.example.greenuniv;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class EstudiantesAdminAdapter extends FirestoreRecyclerAdapter<Usuario, EstudiantesAdminAdapter.ViewHolder>{
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */


    public EstudiantesAdminAdapter(@NonNull FirestoreRecyclerOptions<Usuario> options) {
        super(options);

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listado_alumnos_admin,parent,false);
        return new ViewHolder(v);
    }



    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Usuario usuario) {
        DocumentSnapshot documentSnapshot = getSnapshots().getSnapshot(holder.getAdapterPosition());
        final String id = documentSnapshot.getId();
        holder.nombre.setText(String.valueOf(usuario.getNombre()));
        holder.univer.setText(String.valueOf(usuario.getUniversidad()));
        holder.correo.setText(String.valueOf(usuario.getCorreo()));
        holder.teléfono.setText(String.valueOf(usuario.getTelefono()));
        Glide.with(holder.imagenCarnet.getContext()).load(usuario.getImgurlCarnet()).into(holder.imagenCarnet);

        holder.btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog=new AlertDialog.Builder(view.getContext());
                alertDialog.setTitle("GreenUniv");
                alertDialog.setMessage("¿Estás seguro de validar la cuenta del estudiante?");
                alertDialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        firebaseFirestore.collection("estudiantes").document(id).update("verificado",1).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(view.getContext(), "Se validó la cuenta correctamente", Toast.LENGTH_SHORT).show();
                                //view.getContext().startActivity(new Intent(view.getContext(), VistaPrincipalAdmin.class));
                            }
                        });

                    }
                });
                alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(view.getContext(), "Accion cancelada", Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialog.show();
            }
        });

        holder.btnRechazar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog=new AlertDialog.Builder(view.getContext());
                alertDialog.setTitle("GreenUniv");
                alertDialog.setMessage("¿Estás seguro de rechazar y eliminar la cuenta del estudiante?");
                alertDialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        firebaseFirestore.collection("estudiantes").document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(view.getContext(), "Se rechazo y elimino la cuenta correctamente", Toast.LENGTH_SHORT).show();
                                //view.getContext().startActivity(new Intent(view.getContext(), VistaPrincipalAdmin.class));
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(view.getContext(), "Hubo un error al rechazar y borrar la cuenta", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });
                alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(view.getContext(), "Accion cancelada", Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialog.show();
            }
        });

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombre;
        TextView univer;
        TextView correo;
        TextView teléfono;
        ImageButton btnRechazar;
        ImageButton btnAceptar;
        ImageView imagenCarnet;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            btnRechazar= itemView.findViewById(R.id.btnRechazarEstud);
            btnAceptar = itemView.findViewById(R.id.btnAceptarEstud);
            nombre = itemView.findViewById(R.id.nombreEstud);
            univer = itemView.findViewById(R.id.UniverEstud);
            correo= itemView.findViewById(R.id.correoEstud);
            teléfono = itemView.findViewById(R.id.teléfonoEstudi);
            imagenCarnet = itemView.findViewById(R.id.imageViewCarnetEstud);
        }
    }

}
