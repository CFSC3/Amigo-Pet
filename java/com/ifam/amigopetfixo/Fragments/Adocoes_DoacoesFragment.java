package com.ifam.amigopetfixo.Fragments;


import android.app.Activity;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.github.clans.fab.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ifam.amigopetfixo.activity.Adicionar_Doacao;
import com.ifam.amigopetfixo.Model.bean.PostagemB;
import com.ifam.amigopetfixo.R;
import com.ifam.amigopetfixo.Recycler.RecyclerItemClickListener;
import com.ifam.amigopetfixo.activity.VisualizarDoacoesAdocoes;
import com.ifam.amigopetfixo.adapter.Adapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Adocoes_DoacoesFragment extends Fragment {

    private FirebaseAuth usuario = FirebaseAuth.getInstance();
    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference usuarios;
    private com.github.clans.fab.FloatingActionButton galeria;
    private com.github.clans.fab.FloatingActionButton camera;
    private RecyclerView recyclerView;
    private PostagemB lista;
    private Adapter adapter;
    private List<PostagemB> listaPostagemB = new ArrayList<>();
    private View mexerHeaderFiltroD;
    private NavigationView navigationViewFiltroD;
    private CheckBox alimentacao, animais, brinquedos, higiene, moveis, remedios;
    private Button aplicarFiltroD;
    private int opcao=1;
    private Spinner spinnerCaputurar;
    private DrawerLayout dFiltro;
    private ActionBarDrawerToggle adbtFilter;

    public Adocoes_DoacoesFragment() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_adocoes__doacoes, container, false);
        recyclerView = view.findViewById(R.id.recyclerView1);
        galeria = (FloatingActionButton) view.findViewById(R.id.menu_galeria_Doacoes);
        camera = (FloatingActionButton) view.findViewById(R.id.menu_foto_Doacoes);

        navigationViewFiltroD = (NavigationView) getActivity().findViewById(R.id.nav_viewFiltroD);
        mexerHeaderFiltroD = navigationViewFiltroD.getHeaderView(0);

        alimentacao = mexerHeaderFiltroD.findViewById(R.id.checkBoxAlimentacao);
        animais = mexerHeaderFiltroD.findViewById(R.id.checkBoxAnimais);
        brinquedos = mexerHeaderFiltroD.findViewById(R.id.checkBoxBrinquedos);
        higiene = mexerHeaderFiltroD.findViewById(R.id.checkBoxHigiene);
        moveis = mexerHeaderFiltroD.findViewById(R.id.checkBoxMoveis);
        remedios = mexerHeaderFiltroD.findViewById(R.id.checkBoxRemedios);
        spinnerCaputurar = mexerHeaderFiltroD.findViewById(R.id.spinnerHeaderFiltroD);
        aplicarFiltroD = (Button) mexerHeaderFiltroD.findViewById(R.id.buttonAplicarFiltroD);

        //Listar
        this.criarLista();

        //configurar adapter
        adapter = new Adapter(listaPostagemB);

        //configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.hasFixedSize();
        recyclerView.addItemDecoration( new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));
        recyclerView.setAdapter(adapter);

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
                                PostagemB lista = listaPostagemB.get(position);

                                Intent intent = new Intent(getContext(), VisualizarDoacoesAdocoes.class);
                                intent.putExtra("dados", lista.getTitulo());
                                startActivity(intent);

                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                PostagemB lista = listaPostagemB.get(position);
                                Toast.makeText(getContext(),
                                        "Clique Longo: " + lista.getTitulo(),
                                        Toast.LENGTH_SHORT
                                ).show();
                            }
                        }
                ));
        adicionar();

        //fecharNavView

        aplicarFiltroD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dFiltro.closeDrawer(Gravity.RIGHT);
                opcao=2;
                onResume();
            }
        });


    return view;

    }

    public void aplicar(ActionBarDrawerToggle adbtFilter1, DrawerLayout dFiltro1){

        dFiltro = dFiltro1;
        adbtFilter = adbtFilter1;

        adbtFilter.setDrawerIndicatorEnabled(true);

        dFiltro.addDrawerListener(adbtFilter);
        adbtFilter.syncState();

    }

    public void adicionar(){
        galeria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Adicionar_Doacao.class);
                intent.putExtra("opcao", 1);
                startActivity(intent);
            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Adicionar_Doacao.class);
                intent.putExtra("opcao", 2);
                startActivity(intent);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void criarLista(){

        listaPostagemB.clear();

        if(opcao == 1) {

            usuarios = referencia.child("banco-dados").child("adocoesDoacoes");

            usuarios.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {

                        for (DataSnapshot s : dataSnapshot.getChildren()) {

                            lista = new PostagemB(s.child("titulo").getValue().toString(), s.child("bairro").getValue().toString(),
                                    s.child("data_post").getValue().toString(), s.child("imagem").getValue().toString());

                            listaPostagemB.add(lista);
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

        }else if(opcao == 2){

            if (alimentacao.isChecked()){
                filtroAlimentacao();
            }

            if (animais.isChecked()){
                filtroAnimais();
            }

            if (brinquedos.isChecked()){
                filtroBrinquedos();
            }

            if (higiene.isChecked()){
                filtroHigiene();
            }

            if (moveis.isChecked()){
                filtroMoveis();
            }

            if (remedios.isChecked()){
                filtroRemedios();
            }

            String teste = "dd/MM/yyyy HH:mm";

            String aux = teste.substring(11,16);

            Toast.makeText(getContext(),aux,Toast.LENGTH_LONG).show();

            //ordenData();
        }
    }

    public void filtroAlimentacao(){

        usuarios = referencia.child("banco-dados").child("adocoesDoacoes");

        Query pesquisaFiltro = usuarios.orderByChild("categoria").equalTo("Alimentação");

        pesquisaFiltro.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    for (DataSnapshot s : dataSnapshot.getChildren()) {

                        lista = new PostagemB(s.child("titulo").getValue().toString(), s.child("bairro").getValue().toString(),
                                s.child("data_post").getValue().toString(), s.child("imagem").getValue().toString());

                        listaPostagemB.add(lista);
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
    }

    public void filtroAnimais(){

        usuarios = referencia.child("banco-dados").child("adocoesDoacoes");

        Query pesquisaFiltro = usuarios.orderByChild("categoria").equalTo("Animais");

        pesquisaFiltro.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    for (DataSnapshot s : dataSnapshot.getChildren()) {

                        lista = new PostagemB(s.child("titulo").getValue().toString(), s.child("bairro").getValue().toString(),
                                s.child("data_post").getValue().toString(), s.child("imagem").getValue().toString());

                        listaPostagemB.add(lista);
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

    }

    public void filtroBrinquedos(){

        usuarios = referencia.child("banco-dados").child("adocoesDoacoes");

        Query pesquisaFiltro = usuarios.orderByChild("categoria").equalTo("Brinquedos");

        pesquisaFiltro.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    for (DataSnapshot s : dataSnapshot.getChildren()) {

                        lista = new PostagemB(s.child("titulo").getValue().toString(), s.child("bairro").getValue().toString(),
                                s.child("data_post").getValue().toString(), s.child("imagem").getValue().toString());

                        listaPostagemB.add(lista);
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

    }

    public void filtroHigiene(){

        usuarios = referencia.child("banco-dados").child("adocoesDoacoes");

        Query pesquisaFiltro = usuarios.orderByChild("categoria").equalTo("Higiene");

        pesquisaFiltro.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    for (DataSnapshot s : dataSnapshot.getChildren()) {

                        lista = new PostagemB(s.child("titulo").getValue().toString(), s.child("bairro").getValue().toString(),
                                s.child("data_post").getValue().toString(), s.child("imagem").getValue().toString());

                        listaPostagemB.add(lista);
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

    }

    public void filtroMoveis(){

        usuarios = referencia.child("banco-dados").child("adocoesDoacoes");

        Query pesquisaFiltro = usuarios.orderByChild("categoria").equalTo("Móveis");

        pesquisaFiltro.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    for (DataSnapshot s : dataSnapshot.getChildren()) {

                        lista = new PostagemB(s.child("titulo").getValue().toString(), s.child("bairro").getValue().toString(),
                                s.child("data_post").getValue().toString(), s.child("imagem").getValue().toString());

                        listaPostagemB.add(lista);
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

    }

    public void filtroRemedios(){

        usuarios = referencia.child("banco-dados").child("adocoesDoacoes");

        Query pesquisaFiltro = usuarios.orderByChild("categoria").equalTo("Remédios");

        pesquisaFiltro.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    for (DataSnapshot s : dataSnapshot.getChildren()) {

                        lista = new PostagemB(s.child("titulo").getValue().toString(), s.child("bairro").getValue().toString(),
                                s.child("data_post").getValue().toString(), s.child("imagem").getValue().toString());

                        listaPostagemB.add(lista);
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

    }

    public void ordenAlfa(){

        usuarios = referencia.child("banco-dados").child("adocoesDoacoes");

        Query pesquisaFiltro = usuarios.orderByChild("titulo").startAt("A");

        pesquisaFiltro.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    for (DataSnapshot s : dataSnapshot.getChildren()) {

                        lista = new PostagemB(s.child("titulo").getValue().toString(), s.child("bairro").getValue().toString(),
                                s.child("data_post").getValue().toString(), s.child("imagem").getValue().toString());

                        listaPostagemB.add(lista);
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
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void ordenData(){

        usuarios = referencia.child("banco-dados").child("adocoesDoacoes");

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = new Date();
        String data = dateFormat.format(date);

        String dia, mes, ano, hora, minuto;
        int auxDia, auxMes, auxAno, auxHora, auxMinuto;

        dia = data.substring(0,1);
        mes = data.substring(3,4);
        ano = data.substring(6,9);
        hora = data.substring(11,12);
        minuto = data.substring(15,16);

        Query pesquisaFiltro = usuarios.orderByChild("data_post").startAt("01/01/2020 00:00").endAt("31/12/2020 23:59");

        pesquisaFiltro.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    for (DataSnapshot s : dataSnapshot.getChildren()) {

                        lista = new PostagemB(s.child("titulo").getValue().toString(), s.child("bairro").getValue().toString(),
                                s.child("data_post").getValue().toString(), s.child("imagem").getValue().toString());

                        listaPostagemB.add(lista);
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
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onResume() {
        super.onResume();

        adapter.notifyDataSetChanged();

        if(opcao==2) {
            this.criarLista();
        }
        //Toast.makeText(getContext(), "OnResume está funcionando como eu queria", Toast.LENGTH_LONG).show();
    }
}
