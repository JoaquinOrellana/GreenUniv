package com.example.greenuniv;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EstudianteListadoArticulosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EstudianteListadoArticulosFragment extends Fragment {
    RecyclerView recycleview;
    ArticulosEstudianteListaAdapter adapter;
    FirebaseFirestore firebaseFirestore= FirebaseFirestore.getInstance();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    String universidad;
    FirestoreRecyclerOptions<Articulo> firestoreRecyclerOptions;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EstudianteListadoArticulosFragment() {
        // Required empty public constructor
    }

    public EstudianteListadoArticulosFragment(String universidad) {
        this.universidad=universidad;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EstudianteListadoArticulosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EstudianteListadoArticulosFragment newInstance(String param1, String param2) {
        EstudianteListadoArticulosFragment fragment = new EstudianteListadoArticulosFragment();
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
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_estudiante_listado_articulos, container, false);



        recycleview = (RecyclerView) view.findViewById(R.id.recycleListadoArticulosInterVenta);
        recycleview.setLayoutManager(new LinearLayoutManager(getContext()));
        Toast.makeText(getContext(), universidad, Toast.LENGTH_SHORT).show();
        Query query = firebaseFirestore.collection("articulos").whereEqualTo("universidad",universidad);
        firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Articulo>().setQuery(query,Articulo.class).build();
        AppCompatActivity activity = (AppCompatActivity) getContext();
        adapter = new ArticulosEstudianteListaAdapter(firestoreRecyclerOptions,activity.getSupportFragmentManager());
        recycleview.setAdapter(adapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}