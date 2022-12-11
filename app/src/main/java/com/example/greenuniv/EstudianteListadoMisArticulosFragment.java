package com.example.greenuniv;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EstudianteListadoMisArticulosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EstudianteListadoMisArticulosFragment extends Fragment {
    String universidad;
    RecyclerView recycleview;
    MisArticulosEstudianteListaAdapter adapter;
    FirebaseFirestore firebaseFirestore= FirebaseFirestore.getInstance();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirestoreRecyclerOptions<Articulo> firestoreRecyclerOptions;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EstudianteListadoMisArticulosFragment() {
        // Required empty public constructor
    }

    public EstudianteListadoMisArticulosFragment(String universidad) {
        this.universidad=universidad;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EstudianteListadoMisArticulosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EstudianteListadoMisArticulosFragment newInstance(String param1, String param2) {
        EstudianteListadoMisArticulosFragment fragment = new EstudianteListadoMisArticulosFragment();
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
        View view =inflater.inflate(R.layout.fragment_estudiante_listado_mis_articulos, container, false);

        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        String correoEstudi = currentUser.getEmail();
        recycleview = (RecyclerView) view.findViewById(R.id.recycleListadoMisArticulosInterVenta);
        recycleview.setLayoutManager(new LinearLayoutManager(getContext()));
        Query query = firebaseFirestore.collection("articulos").whereEqualTo("universidad",universidad).whereEqualTo("duenio",correoEstudi);
        firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Articulo>().setQuery(query,Articulo.class).build();
        adapter = new MisArticulosEstudianteListaAdapter(firestoreRecyclerOptions);
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