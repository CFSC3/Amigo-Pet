package com.ifam.amigopetfixo.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ifam.amigopetfixo.Model.bean.Perfil_pet;
import com.ifam.amigopetfixo.R;


public class VisualizarPet extends AppCompatActivity {

    private Perfil_pet pn = new Perfil_pet();
    private FirebaseAuth usuario = FirebaseAuth.getInstance();
    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference pets;

    private ImageView imaPet;
    private TextView nome, raca, especie, cor, castracao;
    private int posicao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_pet);

        imaPet = findViewById(R.id.imgPet);
        nome = findViewById(R.id.visualizarNomeVp);
        raca = findViewById(R.id.visualizarRacaVp);
        especie = findViewById(R.id.visualizarEspecieVp);
        cor = findViewById(R.id.visualizarCorVp);
        castracao = findViewById(R.id.visualizarCastracaoVp);

        Bundle dados = getIntent().getExtras();
        posicao = dados.getInt("dados");

        pets = referencia.child("banco-dados").child("usuario").child(usuario.getUid()).child("pet"+posicao);
        inicializando();

    }

    public void inicializando (){

        pets.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                pn.setNomePet(snapshot.child("nomePet").getValue().toString());
                pn.setEspecie(snapshot.child("especie").getValue().toString());
                pn.setRaca(snapshot.child("raca").getValue().toString());
                pn.setCor(snapshot.child("cor").getValue().toString());
                pn.setCastrado(snapshot.child("castrado").getValue().toString());
                mostrarInformacoes(pn.getNomePet(), pn.getEspecie(), pn.getRaca(), pn.getCor(), pn.getCastrado());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void mostrarInformacoes (String nome1, String especie1, String raca1, String cor1, String castrado1) {

        nome.setText(nome1);
        especie.setText(especie1);
        raca.setText(raca1);
        cor.setText(cor1);
        castracao.setText(castrado1);

        imagemBaixar(nome1);

    }

    public void imagemBaixar(String nome){

        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference imagens = storageReference.child("BancoImagens").child("Usuarios").child(usuario.getUid()).child("pet"+posicao);
        final StorageReference imagemRef = imagens.child("pet"+posicao+".png");

        final long MEGABYTE = 1024 * 1024;

        imagemRef.getBytes(MEGABYTE).addOnSuccessListener(VisualizarPet.this, new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imaPet.setImageBitmap(bitmap);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}