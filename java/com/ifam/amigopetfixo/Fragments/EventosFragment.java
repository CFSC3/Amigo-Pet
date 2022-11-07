package com.ifam.amigopetfixo.Fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ifam.amigopetfixo.activity.Adicionar_Evento;
import com.ifam.amigopetfixo.Model.bean.Evento;
import com.ifam.amigopetfixo.R;
import com.ifam.amigopetfixo.Recycler.RecyclerItemClickListener;
import com.ifam.amigopetfixo.activity.VisualizarEvento;
import com.ifam.amigopetfixo.adapter.AdapterEvento;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventosFragment extends Fragment {

    private FirebaseAuth usuario = FirebaseAuth.getInstance();
    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference usuarios;
    private Evento lista;
    private AdapterEvento adapter;
    private com.github.clans.fab.FloatingActionButton galeria;
    private com.github.clans.fab.FloatingActionButton camera;
    private RecyclerView recyclerView;
    private List<Evento> evento = new ArrayList<>();


    public EventosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_eventos, container, false);
        galeria = (com.github.clans.fab.FloatingActionButton) view.findViewById(R.id.menu_galeria_Evento);
        camera = (com.github.clans.fab.FloatingActionButton) view.findViewById(R.id.menu_foto_Evento);
        recyclerView = view.findViewById(R.id.reciclerView2);

        //Listar
       this.criarLista();

        //configurar adapter
        adapter = new AdapterEvento(evento);

        //configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.hasFixedSize();
        recyclerView.addItemDecoration( new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));
        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        //evento de click
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getContext(),
                        recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }

                            @Override
                            public void onItemClick(View view, int position) {
                                Evento lista = evento.get(position);

                                Intent intent = new Intent(getContext(), VisualizarEvento.class);
                                intent.putExtra("dados", lista.getTitulo());
                                startActivity(intent);
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                Evento lista = evento.get(position);
                                Toast.makeText(getContext(),
                                        "Clique Longo: " + lista.getTitulo(),
                                        Toast.LENGTH_SHORT
                                ).show();
                            }
                        }
                ));

        button();

        return view;
    }

    public void button(){
        galeria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Adicionar_Evento.class);
                intent.putExtra("opcao", 1);
                startActivity(intent);
            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Adicionar_Evento.class);
                intent.putExtra("opcao", 2);
                startActivity(intent);
            }
        });
    }

    public void criarLista(){

        usuarios = referencia.child("banco-dados").child("Eventos");

        evento.clear();

        usuarios.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    for (DataSnapshot s : dataSnapshot.getChildren()) {

                        lista = new Evento(s.child("titulo").getValue().toString(), s.child("resumo").getValue().toString(), s.child("imagem").getValue().toString());
                        evento.add(lista);
                        adapter.notifyDataSetChanged();

                    }
                } else {
                    Log.i("MeuLOG", "erro na captura");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        /*
        for (int i = 0; i < 100; i++) {

            lista = new Evento(R.drawable.logo3, "Evento de Castração", "Um evento que ocorrerá na cidade de Manaus com intuito de incluir mais animais a faixa de castração.");
            evento.add(lista);

            lista = new Evento(R.drawable.logo1, "Reunião anual de cachorros de Manaus", "Um evento que ocorrerá na cidade de Manaus com intuito de reunir donos e seus pets.");
            evento.add(lista);

            lista = new Evento(R.drawable.logo2, "Exibição de novos produtos para pets", "Um evento que ocorrerá na cidade de Manaus com intuito de mostrar e exibir novos produtos do mercado pet.");
            evento.add(lista);

            lista = new Evento(R.drawable.logo10, "Adote-me", "Um evento que ocorrerá na cidade de Manaus com intuito de ajudar animais resgatados de rua a encontrar seu lar.");
            evento.add(lista);

            lista = new Evento(R.drawable.logo2, "Interação homem-pet", "Um evento que ocorrerá na cidade de Manaus com intuito de ajudar na interação de pets e seus donos a sociedade.");
            evento.add(lista);

        }*/

    }

}
