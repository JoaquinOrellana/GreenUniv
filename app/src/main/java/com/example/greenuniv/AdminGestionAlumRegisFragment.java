package com.example.greenuniv;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminGestionAlumRegisFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminGestionAlumRegisFragment extends Fragment {
    RecyclerView recycleview;
    EstudiantesAdminAdapter adapter;
    FirebaseFirestore firebaseFirestore;
    FirestoreRecyclerOptions<Usuario> firestoreRecyclerOptions;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AdminGestionAlumRegisFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminGestionAlumRegisFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminGestionAlumRegisFragment newInstance(String param1, String param2) {
        AdminGestionAlumRegisFragment fragment = new AdminGestionAlumRegisFragment();
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
        firebaseFirestore = FirebaseFirestore.getInstance();
        View view=inflater.inflate(R.layout.fragment_admin_gestion_alum_regis, container, false);
        recycleview = (RecyclerView) view.findViewById(R.id.recycleListadoAlumnos);
        recycleview.setLayoutManager(new LinearLayoutManager(getContext()));
        Query query = firebaseFirestore.collection("estudiantes").whereEqualTo("verificado",0);
        firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Usuario>().setQuery(query,Usuario.class).build();
        adapter = new EstudiantesAdminAdapter(firestoreRecyclerOptions);
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