package com.ifam.amigopetfixo.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ifam.amigopetfixo.R;

public class VisualizarDoacoesAdocoes extends AppCompatActivity {

    private FirebaseAuth usuario = FirebaseAuth.getInstance();
    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference usuarios;
    private TextView titulo;
    private TextView descricao;
    private TextView categoria;
    private TextView endereco;
    private TextView bairro;
    private TextView contato;
    private TextView data;
    private ImageView foto;
    private ImageView voltar;
    private FloatingActionButton fb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_doacoes_adocoes);

        titulo = findViewById(R.id.textTituloVizualizar);
        descricao = findViewById(R.id.textDescricaoVizualizar);
        categoria = findViewById(R.id.textCategoriaVizualizar);
        endereco = findViewById(R.id.textEndrecoVizualizar);
        bairro = findViewById(R.id.textBairroVizualizar);
        contato = findViewById(R.id.textContatoVizualizar);
        data = findViewById(R.id.textData);
        foto = findViewById(R.id.imageVizualizarAd);
        fb = findViewById(R.id.whatssap);

        voltar = findViewById(R.id.imageVoltarVizualizarDoacoes);

        Bundle dados = getIntent().getExtras();
        String nomePasta = dados.getString("dados");
        usuarios = referencia.child("banco-dados").child("adocoesDoacoes").child(nomePasta);

        inicializando();
        bottons();
    }

    private void inicializando() {

        usuarios.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                mostrarInformacoes(snapshot.child("titulo").getValue().toString(), snapshot.child("descricao").getValue().toString(),
                        snapshot.child("categoria").getValue().toString(), snapshot.child("endereco").getValue().toString(),
                        snapshot.child("bairro").getValue().toString(), snapshot.child("contato").getValue().toString(),
                        snapshot.child("imagem").getValue().toString(), snapshot.child("data_post").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void bottons() {

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri whatssap = Uri.parse("https://api.whatsapp.com/send?phone=55"+contato.getText().toString());
                Intent whatssapIntent = new Intent(Intent.ACTION_VIEW, whatssap);
                startActivity(whatssapIntent);
            }
        });

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void mostrarInformacoes (String titulo, String descricao, String categoria, String endereco,
                                    String bairro, String contato, String imagem, String data) {

        this.titulo.setText(titulo);
        this.descricao.setText(descricao);
        this.categoria.setText(categoria);
        this.endereco.setText(endereco);
        this.bairro.setText(bairro);
        this.contato.setText(contato);
        this.data.setText(data);

        imagemBaixar(imagem);

    }

    public void imagemBaixar(String nome){
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference imagens = storageReference.child("BancoImagens").child("DoacoesAdocoes");
        final StorageReference imagemRef = imagens.child(nome+".png");

        final long MEGABYTE = 1024 * 1024;

        imagemRef.getBytes(MEGABYTE).addOnSuccessListener(VisualizarDoacoesAdocoes.this, new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                foto.setImageBitmap(bitmap);
            }
        });
    }

}