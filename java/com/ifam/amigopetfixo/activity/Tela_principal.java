package com.ifam.amigopetfixo.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.ifam.amigopetfixo.Fragments.Adocoes_DoacoesFragment;
import com.ifam.amigopetfixo.Fragments.MapaFragment;
import com.ifam.amigopetfixo.Fragments.Poste_EventoFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ifam.amigopetfixo.Model.bean.Perfil_dono;
import com.ifam.amigopetfixo.R;

public class Tela_principal extends AppCompatActivity {

    private FirebaseAuth usuario = FirebaseAuth.getInstance();
    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference usuarios;
    private Perfil_dono dn = new Perfil_dono();
    private DrawerLayout d1;
    private DrawerLayout dFiltro;
    private View mexerHeader;
    private View mexerHeaderFiltroD, viewFiltro;
    private NavigationView navigationView;
    private NavigationView navigationViewFiltroD;
    private ActionBarDrawerToggle abdt;
    private ActionBarDrawerToggle adbtFilter;
    private Poste_EventoFragment poste_eventoFragment;
    private MapaFragment mapaFragment;
    private Adocoes_DoacoesFragment adocoes_doacoesFragment;
    private BottomNavigationView botaoNavegacao;
    private androidx.appcompat.widget.Toolbar tolbar;
    private de.hdodenhof.circleimageview.CircleImageView myMenu;
    private de.hdodenhof.circleimageview.CircleImageView imagelateral;
    private ImageButton voltarLateral;
    private ImageView myChat, locations, filterD;
    private String imagem;
    private Button editar;
    private Button exibir;
    private Button configuracoes;
    private int opcaoMapa;
    private int opcaoTelaPricipal;
    private Spinner spinnerFiltroD;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);

        LayoutInflater inflater = Tela_principal.this.getLayoutInflater();
        View view = inflater.inflate(R.layout.navegation_header, null);

        LayoutInflater inflaterFiltro = Tela_principal.this.getLayoutInflater();
        viewFiltro = inflaterFiltro.inflate(R.layout.navegation_headerdoacao,null);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        mexerHeader = navigationView.getHeaderView(0);

        navigationViewFiltroD = (NavigationView) findViewById(R.id.nav_viewFiltroD);
        mexerHeaderFiltroD = navigationViewFiltroD.getHeaderView(0);

        spinnerFiltroD = (Spinner) mexerHeaderFiltroD.findViewById(R.id.spinnerHeaderFiltroD);

        botaoNavegacao = findViewById(R.id.botao_navegacao);
        myMenu = findViewById(R.id.myMenU);
        voltarLateral = findViewById(R.id.imageVoltarLateral);
        myChat = (ImageView) findViewById(R.id.myChat);
        locations = (ImageView) findViewById(R.id.locations);
        filterD = (ImageView) findViewById(R.id.filterD);
        tolbar = findViewById(R.id.toolbar);
        usuarios = referencia.child("banco-dados").child("usuario").child(usuario.getUid());

        configuracoes = view.findViewById(R.id.configuracoes);
        editar = view.findViewById(R.id.editar_perfil);
        exibir = view.findViewById(R.id.visualizar_perfil);
        imagelateral = (de.hdodenhof.circleimageview.CircleImageView) mexerHeader.findViewById(R.id.imagmLateral);
        imagelateral.setImageResource(R.drawable.padrao);

        myChat.setImageResource(R.drawable.logo_png);

        //voltarLateral.setElevation(0);

        inicializandoInf();

        getSupportActionBar().hide();

        Bundle dados = getIntent().getExtras();

        if (dados!=null){
            opcaoMapa = dados.getInt("opcaoMapa");
            opcaoTelaPricipal = dados.getInt("opcaoTelaPrincipal");
        }else{
            opcaoTelaPricipal = 1;
        }

        inicio();
        navegacao();
        menu_lateral();
        filtroDoacoes();
    }

    public void menu_lateral(){

        d1 = (DrawerLayout) findViewById(R.id.d1);

        abdt = new ActionBarDrawerToggle(this,d1,R.string.Open,R.string.close);
        abdt.setDrawerIndicatorEnabled(true);

        d1.addDrawerListener(abdt);
        abdt.syncState();

        myMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                d1.openDrawer(GravityCompat.START);
            }
        });

    }

    public void inicio(){

        Bundle dados = new Bundle();

        switch (opcaoTelaPricipal){
            case 1:

                poste_eventoFragment = new Poste_EventoFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

                fragmentTransaction.replace(R.id.conteudo, poste_eventoFragment );
                fragmentTransaction.commit();
                myChat.setVisibility(View.VISIBLE);
                locations.setVisibility(View.INVISIBLE);
                filterD.setVisibility(View.INVISIBLE);

                break;

            case 2:

                mapaFragment = new MapaFragment();

                dados.putInt("dados", opcaoMapa);
                mapaFragment.setArguments(dados);

                FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
                fragmentTransaction2.replace(R.id.conteudo, mapaFragment);
                fragmentTransaction2.commit();

                myChat.setVisibility(View.INVISIBLE);
                locations.setVisibility(View.VISIBLE);
                filterD.setVisibility(View.INVISIBLE);

                locations.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Tela_principal.this,FiltroMapa.class);
                        startActivity(intent);
                        finish();
                    }
                });

                break;

            case 3:

                adocoes_doacoesFragment = new Adocoes_DoacoesFragment();
                FragmentTransaction fragmentTransaction3 = getSupportFragmentManager().beginTransaction();
                fragmentTransaction3.replace(R.id.conteudo, adocoes_doacoesFragment );
                fragmentTransaction3.commit();

                myChat.setVisibility(View.INVISIBLE);
                locations.setVisibility(View.INVISIBLE);
                filterD.setVisibility(View.VISIBLE);

                break;

            default:Toast.makeText(getApplicationContext(), "Algo deu errado!!!",Toast.LENGTH_LONG).show();
        }
    }

    public void navegacao(){
        botaoNavegacao.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Bundle dados = new Bundle();

                switch (item.getItemId()){
                    case R.id.principal: poste_eventoFragment = new Poste_EventoFragment();
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.conteudo, poste_eventoFragment );
                        fragmentTransaction.commit();

                        myChat.setVisibility(View.VISIBLE);
                        locations.setVisibility(View.INVISIBLE);
                        filterD.setVisibility(View.INVISIBLE);

                        break;

                    case R.id.mapa: mapaFragment = new MapaFragment();
                        FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction2.replace(R.id.conteudo, mapaFragment);
                        fragmentTransaction2.commit();

                        myChat.setVisibility(View.INVISIBLE);
                        locations.setVisibility(View.VISIBLE);
                        filterD.setVisibility(View.INVISIBLE);

                        locations.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(Tela_principal.this,FiltroMapa.class);
                                startActivity(intent);
                                finish();
                            }
                        });

                        break;

                    case R.id.adocoes_doacoes: adocoes_doacoesFragment = new Adocoes_DoacoesFragment();
                        FragmentTransaction fragmentTransaction3 = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction3.replace(R.id.conteudo, adocoes_doacoesFragment );
                        fragmentTransaction3.commit();

                        myChat.setVisibility(View.INVISIBLE);
                        locations.setVisibility(View.INVISIBLE);
                        filterD.setVisibility(View.VISIBLE);

                       adocoes_doacoesFragment.aplicar(adbtFilter, dFiltro);

                        break;

                    default: Toast.makeText(Tela_principal.this, "Erro inesperado", Toast.LENGTH_SHORT).show();
                }

                return true;
            }
        });
    }

    public void voltar(View view){
        if (d1.isDrawerOpen(GravityCompat.START)) {
            d1.closeDrawer(GravityCompat.START);
        }
    }

    public void inicializandoInf (){
        usuarios.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                dn.setTexto(snapshot.child("texto").getValue().toString());
                dn.setNome(snapshot.child("nome").getValue().toString());
                dn.setId(snapshot.child("id").getValue().toString());
                dn.setCelular(snapshot.child("celular").getValue().toString());
                // qtdPet = Integer.parseInt(snapshot.child("quatidadPet").getValue().toString());
                imagem = snapshot.child("imageUsuario").getValue().toString();
                imagemBaixar(imagem);
                imagem(imagem);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void imagemBaixar(String nome){
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference imagens = storageReference.child("BancoImagens").child("Usuarios").child(usuario.getUid());
        StorageReference imagemRef = imagens.child(nome+".png");

        final long MEGABYTE = 1024 * 1024;

        imagemRef.getBytes(MEGABYTE).addOnSuccessListener(Tela_principal.this,new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                myMenu.setImageBitmap(bitmap);
                imagelateral.setImageBitmap(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Toast.makeText(Tela_principal.this, "Erro inesperado", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void imagem(String nome){
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference imagens = storageReference.child("BancoImagens").child("Usuarios").child(usuario.getUid());
        StorageReference imagemRef = imagens.child(nome+".png");

        final long MEGABYTE = 1024 * 1024;

        imagemRef.getBytes(MEGABYTE).addOnSuccessListener(Tela_principal.this,new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imagelateral.setImageBitmap(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
               // Toast.makeText(Tela_principal.this, "Erro inesperado", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void configuracao(View view){
        Intent intent = new Intent(this,Configuracao.class);
        startActivity(intent);
    }

    public void editar(View view){
        Intent intent = new Intent(this,Tela_Editar_Usuario.class);
        startActivity(intent);
    }

    public void exibir(View view){
        Intent intent = new Intent(this,Visualizar_perfil_usuario.class);
        intent.putExtra("dados", usuario.getUid());
        startActivity(intent);
    }

    public void filtroDoacoes(){

        ArrayAdapter data = ArrayAdapter.createFromResource(this,R.array.datas_tempos_filto,R.layout.support_simple_spinner_dropdown_item);

        spinnerFiltroD.setAdapter(data);

        dFiltro = (DrawerLayout) findViewById(R.id.d1);

        adbtFilter = new ActionBarDrawerToggle(this,dFiltro,R.string.Open,R.string.close);
        adbtFilter.setDrawerIndicatorEnabled(true);

        dFiltro.addDrawerListener(adbtFilter);
        adbtFilter.syncState();

        filterD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dFiltro.openDrawer(Gravity.RIGHT);
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return abdt.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }


}
