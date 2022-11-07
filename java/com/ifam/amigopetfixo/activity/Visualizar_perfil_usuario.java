package com.ifam.amigopetfixo.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
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

public class Visualizar_perfil_usuario extends AppCompatActivity {

    private TextView descricao;
    private TextView nome;
    private TextView id;
    private TextView celular;
    private de.hdodenhof.circleimageview.CircleImageView foto;
    private ImageView voltar;
    private de.hdodenhof.circleimageview.CircleImageView pet1, pet2, pet3, pet4;
    private TextView nomePet1, nomePet2, nomePet3, nomePet4;
    private int qtdPet = 0;
    private String aux;
    private Perfil_dono dn = new Perfil_dono();
    private FirebaseAuth usuario = FirebaseAuth.getInstance();
    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    private StorageReference imagens;
    private DatabaseReference usuarios;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_visualizar_perfil_usuario);
        nome = findViewById(R.id.textNome);
        descricao = findViewById(R.id.conteudoP);
        id = findViewById(R.id.textId);
        celular = findViewById(R.id.textCelular);
        foto = (de.hdodenhof.circleimageview.CircleImageView) findViewById(R.id.fotoD);
        voltar = findViewById(R.id.imageVoltarVisualizarPerfil);
        pet1 = findViewById(R.id.imgPet1);
        pet2 = findViewById(R.id.imgPet2);
        pet3 = findViewById(R.id.imgPet3);
        pet4 = findViewById(R.id.imgPet4);
        nomePet1 = findViewById(R.id.nomePet1);
        nomePet2 = findViewById(R.id.nomePet2);
        nomePet3 = findViewById(R.id.nomePet3);
        nomePet4 = findViewById(R.id.nomePet4);

        Bundle dados = getIntent().getExtras();
        String nomePasta = dados.getString("dados");
        aux = nomePasta;
        usuarios = referencia.child("banco-dados").child("usuario").child(nomePasta);
        imagens = storageReference.child("BancoImagens").child("Usuarios").child(nomePasta);

        inicializando();
        buttons();

    }

    public void inicializando (){

        usuarios.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                dn.setTexto(snapshot.child("texto").getValue().toString());
                dn.setNome(snapshot.child("nome").getValue().toString());
                dn.setId(snapshot.child("id").getValue().toString());
                dn.setCelular(snapshot.child("celular").getValue().toString());
                qtdPet = Integer.parseInt(snapshot.child("quatidadePet").getValue().toString());
                mostrarInformacoes(dn.getTexto(), dn.getNome(), dn.getId(), dn.getCelular(), snapshot.child("imageUsuario").getValue().toString());
                mostrarNomePets();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void mostrarInformacoes (String descricao1, String nome1, String id1, String celular1, String image) {

        descricao.setText(descricao1);
        nome.setText(nome1);
        id.setText(id1);
        celular.setText(celular1);

        imagemBaixar(image);
        baixarImagemPet();

    }

    public void mostrarNomePets(){

        DatabaseReference nomePets;

        for(int i = 1; i<=qtdPet; i++){

            nomePets = referencia.child("banco-dados").child("usuario").child(aux).child("pet"+i);

            final int finalI = i;

            nomePets.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    switch (finalI){
                        case 1: nomePet1.setText(dataSnapshot.child("nomePet").getValue().toString()); break;
                        case 2: nomePet2.setText(dataSnapshot.child("nomePet").getValue().toString()); break;
                        case 3: nomePet3.setText(dataSnapshot.child("nomePet").getValue().toString()); break;
                        case 4: nomePet4.setText(dataSnapshot.child("nomePet").getValue().toString()); break;
                        }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
    }

    public void imagemBaixar(String nome){
        final StorageReference imagemRef = imagens.child(nome+".png");

        final long MEGABYTE = 1024 * 1024;

        imagemRef.getBytes(MEGABYTE).addOnSuccessListener(Visualizar_perfil_usuario.this, new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                foto.setImageBitmap(bitmap);
            }
        });
    }

    public void baixarImagemPet(){

        StorageReference storageReferencepet = FirebaseStorage.getInstance().getReference();

        for (int i = 1; i<=qtdPet; i++) {

            StorageReference imagenspet = storageReferencepet.child("BancoImagens").child("Usuarios").child(usuario.getUid()).child("pet" + i);
            final StorageReference imagemRefpet = imagenspet.child("pet" + i + ".png");

            final long MEGABYTE = 1024 * 1024;

            final int finalI = i;
            imagemRefpet.getBytes(MEGABYTE).addOnSuccessListener(Visualizar_perfil_usuario.this, new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    if (finalI == 1) {
                        pet1.setImageBitmap(bitmap);
                    } else if (finalI == 2) {
                        pet2.setImageBitmap(bitmap);
                    } else if (finalI == 3) {
                        pet3.setImageBitmap(bitmap);
                    } else if (finalI == 4) {
                        pet4.setImageBitmap(bitmap);
                    }
                }
            });
        }
    }

    public void buttons(){
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        pet1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Visualizar_perfil_usuario.this, VisualizarPet.class);
                intent.putExtra("dados", 1);
                startActivity(intent);
            }
        });

        pet2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Visualizar_perfil_usuario.this, VisualizarPet.class);
                intent.putExtra("dados", 2);
                startActivity(intent);
            }
        });

        pet3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Visualizar_perfil_usuario.this, VisualizarPet.class);
                intent.putExtra("dados", 3);
                startActivity(intent);
            }
        });

        pet4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Visualizar_perfil_usuario.this, VisualizarPet.class);
                intent.putExtra("dados", 4);
                startActivity(intent);
            }
        });

    }
}