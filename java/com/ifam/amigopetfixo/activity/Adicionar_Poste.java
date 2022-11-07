package com.ifam.amigopetfixo.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ifam.amigopetfixo.Model.bean.Perfil_dono;
import com.ifam.amigopetfixo.Model.bean.Postagem;
import com.ifam.amigopetfixo.R;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.Random;

public class Adicionar_Poste extends AppCompatActivity {

    private FirebaseAuth usuario = FirebaseAuth.getInstance();
    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference usuarios;
    private Perfil_dono dn = new Perfil_dono();
    private Postagem postagem = new Postagem();
    private Button cancelar;
    private Button salvar;
    private ImageView foto;
    private EditText legenda;
    private int posicao;
    private Random random = new Random();
    private int qtd;
    private ImageView voltar;
    private final int GALERIA_IMAGEM = 1;
    private final int PERMISION_REQUEST = 2;
    private final int CAMERA = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar__poste);

        cancelar = findViewById(R.id.cancelarPost);
        salvar = findViewById(R.id.salvarPost);
        foto = findViewById(R.id.imagePost);
        legenda = findViewById(R.id.editTextPost);
        voltar = findViewById(R.id.imageVoltarAdicionarPoste);

        Bundle dados = getIntent().getExtras();

        int i = dados.getInt("opcao");

        if (i==1){
            recuperarImagem();
        }else if (i==2){
            tirarFoto();
        }else {
            Toast.makeText(Adicionar_Poste.this,"Algo deu errado: "+i, Toast.LENGTH_SHORT).show();
        }

        buttons();
        recuperandoQTD();

    }

    private void recuperandoQTD() {

        usuarios = referencia.child("banco-dados").child("Postagens");

        usuarios.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    qtd = Integer.parseInt(dataSnapshot.child("qtd").getValue().toString());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        /*usuarios.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    qtd = Integer.parseInt(snapshot.child("qtd").getValue().toString());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
    }

    private void buttons() {
        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nomeEposicao();
                Intent intent = new Intent(getApplicationContext(), Tela_principal.class);
                startActivity(intent);
                finish();
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Tela_principal.class);
                startActivity(intent);
                finish();
            }
        });

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Tela_principal.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void recuperarImagem(){

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, GALERIA_IMAGEM);

    }

    public void tirarFoto(){

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, CAMERA);}
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void salvar(String nome, int posicao, String image){
        usuarios = referencia.child("banco-dados");
        //Capturando a Data Atual
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = new Date();
        String data = dateFormat.format(date);

            String imagem = String.valueOf(Long.toHexString(random.nextLong()));

            //Colocando no Firebase

            postagem.setPostagem(legenda.getText().toString());
            postagem.setData_poste(data);
            postagem.setNome(nome);

            //Lista do Usuário

            usuarios.child("Postagens").child("Usuarios").child(usuario.getUid()).child(String.valueOf(posicao)).setValue(postagem);
            usuarios.child("Postagens").child("Usuarios").child(usuario.getUid()).child(String.valueOf(posicao)).child("imagem").setValue(imagem);
            usuarios.child("Postagens").child("Usuarios").child(usuario.getUid()).child(String.valueOf(posicao)).child("usuarioID").setValue(usuario.getUid());
            usuarios.child("Postagens").child("Usuarios").child(usuario.getUid()).child(String.valueOf(posicao)).child("imageUsuario").setValue(image);

            //Geral

            usuarios.child("Postagens").child("Lista").child("Postagem"+String.valueOf(qtd)).setValue(postagem);
            usuarios.child("Postagens").child("Lista").child("Postagem"+String.valueOf(qtd)).child("imagem").setValue(imagem);
            usuarios.child("Postagens").child("Lista").child("Postagem"+String.valueOf(qtd)).child("usuarioID").setValue(usuario.getUid());
            usuarios.child("Postagens").child("Lista").child("Postagem"+String.valueOf(qtd)).child("imageUsuario").setValue(image);
            usuarios.child("Postagens").child("qtd").setValue(qtd+1);


            salvandoImagem(imagem);

    }

    public void salvandoImagem(String nome){

        //configura para a imagem ser salva na memoria
        foto.setDrawingCacheEnabled(true);
        foto.buildDrawingCache();

        //Recupera o bitmap da imagem (a ser salva)
        Bitmap bitmap = foto.getDrawingCache();

        //comprimindo bitmap para um PNG/JPEG
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 75, byteArrayOutputStream);

        //converte o byteArrayOutputStream para pixel brutos em uma matrix de bytes
        //(dados de uma imagem)
        byte[] dadosImagem = byteArrayOutputStream.toByteArray();

        //define nós para o Storeg
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference imagens = storageReference.child("BancoImagens").child("Postagens");
        final StorageReference imagemRef = imagens.child(nome+".png");

        //Retorna o objeto que ira controlar o upload
        final UploadTask uploadTask = imagemRef.putBytes(dadosImagem);

        uploadTask.addOnFailureListener(Adicionar_Poste.this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(Adicionar_Poste.this, "Upload de Imagem falhou: " + e.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(Adicionar_Poste.this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                imagemRef.getDownloadUrl().addOnCompleteListener(Adicionar_Poste.this, new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {

                        Toast.makeText(Adicionar_Poste.this, "Upload de Imagem: " + task.getResult().toString(),
                                Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        atualizarPosicao(posicao+1);
    }

    public void nomeEposicao (){

        usuarios = referencia.child("banco-dados").child("usuario").child(usuario.getUid());

        usuarios.addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                dn.setNome(dataSnapshot.child("nome").getValue().toString());
                posicao = Integer.parseInt(dataSnapshot.child("postagens").getValue().toString());

                salvar(dn.getNome(), posicao, dataSnapshot.child("imageUsuario").getValue().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        /*usuarios.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                dn.setNome(snapshot.child("nome").getValue().toString());
                posicao = Integer.parseInt(snapshot.child("postagens").getValue().toString());

                salvar(dn.getNome(), posicao, snapshot.child("imageUsuario").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
    }

    public void atualizarPosicao(int posicao){
        usuarios = referencia.child("banco-dados").child("usuario").child(usuario.getUid());
        usuarios.child("postagens").setValue(posicao);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == GALERIA_IMAGEM){
            Uri selecionarImagem = data.getData();
            String[] filePath = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selecionarImagem, filePath,null,null,null);
            c.moveToFirst();
            int indexcoluna = c.getColumnIndex(filePath[0]);
            String picturePath = c.getString(indexcoluna);
            c.close();
            Bitmap imagemGaleria = (BitmapFactory.decodeFile(picturePath));
            foto.setImageBitmap(imagemGaleria);
        }

        if (requestCode== CAMERA && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            foto.setImageBitmap(imageBitmap);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        if (requestCode == PERMISION_REQUEST) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
// A permissão foi concedida. Pode continuar
            } else {
// A permissão foi negada. Precisa ver o que deve ser desabilitado
            }

            return;
        }
    }

}