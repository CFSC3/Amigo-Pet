package com.ifam.amigopetfixo.Fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ifam.amigopetfixo.activity.Adicionar_Poste;
import com.ifam.amigopetfixo.Model.bean.Postagem;
import com.ifam.amigopetfixo.R;
import com.ifam.amigopetfixo.Recycler.RecyclerItemClickListener;
import com.ifam.amigopetfixo.activity.Visualizar_perfil_usuario;
import com.ifam.amigopetfixo.adapter.PostagemAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PosteFragment extends Fragment {

    private FirebaseAuth usuario = FirebaseAuth.getInstance();
    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference usuarios;
    private Postagem p;
    private PostagemAdapter adapter;
    private com.github.clans.fab.FloatingActionButton galeria;
    private com.github.clans.fab.FloatingActionButton camera;
    private RecyclerView recyclerpostagem;
    private List<Postagem> postagens = new ArrayList<>();


    public PosteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_poste, container, false);

        recyclerpostagem = view.findViewById(R.id.recyclerPostagem);
        galeria = (com.github.clans.fab.FloatingActionButton) view.findViewById(R.id.menu_galeria_Poste);
        camera = (com.github.clans.fab.FloatingActionButton) view.findViewById(R.id.menu_foto_Poste);

        //definir Layout
        //RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        //LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
       // layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager( getContext(), 1);
        recyclerpostagem.setLayoutManager(layoutManager);

        //definir adapter
        this.prepararPostagens();
        adapter = new PostagemAdapter(postagens);
        recyclerpostagem.setAdapter(adapter);

        //evento de click
        recyclerpostagem.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getContext(),
                        recyclerpostagem,
                        new RecyclerItemClickListener.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }

                            @Override
                            public void onItemClick(View view, int position) {
                                Postagem lista = postagens.get(position);

                                Intent intent = new Intent(getContext(), Visualizar_perfil_usuario.class);
                                intent.putExtra("dados", lista.getIdUsuario());
                                startActivity(intent);
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                Postagem lista = postagens.get(position);
                                Toast.makeText(getContext(),
                                        "Clique Longo: " + lista.getIdUsuario(),
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
                Intent intent = new Intent(getActivity(), Adicionar_Poste.class);
                intent.putExtra("opcao", 1);
                startActivity(intent);
            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Adicionar_Poste.class);
                intent.putExtra("opcao", 2);
                startActivity(intent);
            }
        });
    }

    public void prepararPostagens(){

        usuarios = referencia.child("banco-dados").child("Postagens").child("Lista");

        this.postagens.clear();

        usuarios.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    for (DataSnapshot s : dataSnapshot.getChildren()) {

                        p = new Postagem(
                                s.child("nome").getValue().toString(),
                                s.child("postagem").getValue().toString(),
                                s.child("imagem").getValue().toString(),
                                s.child("imageUsuario").getValue().toString(),
                                s.child("usuarioID").getValue().toString()
                        );

                        postagens.add(p);

                        adapter.notifyDataSetChanged();

                    }
                } else {
                    Log.i("MeuLOG", "erro na captura");
                    Toast.makeText(getContext(), "Erro Esperado", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

       /* for(int i=0; i<100;i++) {

            p = new Postagem("Carlos Felipe", "#tbt viagem legal!", R.drawable.logo1);
            this.postagens.add(p);
            p = new Postagem("Hotel CF", "Viagem e aproveite nossos descontos", R.drawable.logo2);
            this.postagens.add(p);
            p = new Postagem("Maria Luiza", "#Paris!!", R.drawable.logo3);
            this.postagens.add(p);
            p = new Postagem("Marcos Paulo", "Que foto linda", R.drawable.logo1);
            this.postagens.add(p);
        }

        /*p = new Postagem("Carlos Felipe", "#tbt viagem legal!", R.drawable.logo1);
        this.postagens.add(p);
        p = new Postagem("Hotel CF", "Viagem e aproveite nossos descontos", R.drawable.logo2);
        this.postagens.add(p);
        p = new Postagem("Maria Luiza", "#Paris!!", R.drawable.logo3);
        this.postagens.add(p);

        p = new Postagem("Carlos Felipe", "#tbt viagem legal!", R.drawable.logo1);
        this.postagens.add(p);
        p = new Postagem("Hotel CF", "Viagem e aproveite nossos descontos", R.drawable.logo2);
        this.postagens.add(p);
        p = new Postagem("Maria Luiza", "#Paris!!", R.drawable.logo3);
        this.postagens.add(p);

        p = new Postagem("Carlos Felipe", "#tbt viagem legal!", R.drawable.logo1);
        this.postagens.add(p);
        p = new Postagem("Hotel CF", "Viagem e aproveite nossos descontos", R.drawable.logo2);
        this.postagens.add(p);
        p = new Postagem("Maria Luiza", "#Paris!!", R.drawable.logo3);
        this.postagens.add(p);*/

    }

}
