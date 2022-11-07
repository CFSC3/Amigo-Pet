package com.ifam.amigopetfixo.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
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
import com.ifam.amigopetfixo.R;

public class VisualizarEvento extends AppCompatActivity {

    private FirebaseAuth usuario = FirebaseAuth.getInstance();
    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference usuarios;
    private TextView titulo;
    private TextView descricao;
    private TextView data;
    private ImageView foto;
    private ImageView voltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_evento);
        
        titulo = findViewById(R.id.textVtitulo);
        descricao = findViewById(R.id.textVdescricao);
        data = findViewById(R.id.textVdata);
        foto = findViewById(R.id.imageVevento);
        voltar = findViewById(R.id.imageVoltarVizualizarEvento);

        Bundle dados = getIntent().getExtras();
        String nomePasta = dados.getString("dados");
        usuarios = referencia.child("banco-dados").child("Eventos").child(nomePasta);
        
        inicializando();

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void inicializando() {

        usuarios.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                mostrarInformacoes(snapshot.child("titulo").getValue().toString(), snapshot.child("descricao").getValue().toString(),
                        snapshot.child("imagem").getValue().toString(), snapshot.child("data_post").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void mostrarInformacoes (String titulo, String descricao,  String imagem, String data) {

        this.titulo.setText(titulo);
        this.descricao.setText(descricao);
        this.data.setText(data);

        imagemBaixar(imagem);

    }


    public void imagemBaixar(String nome){
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference imagens = storageReference.child("BancoImagens").child("Eventos");
        final StorageReference imagemRef = imagens.child(nome+".png");

        final long MEGABYTE = 1024 * 1024;

        imagemRef.getBytes(MEGABYTE).addOnSuccessListener(VisualizarEvento.this, new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                foto.setImageBitmap(bitmap);
            }
        });
    }

}