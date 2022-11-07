package com.ifam.amigopetfixo.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ifam.amigopetfixo.Model.bean.Perfil_dono;
import com.ifam.amigopetfixo.Model.bean.Perfil_pet;
import com.ifam.amigopetfixo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Random;

public class Tela_Editar_Usuario extends AppCompatActivity {

    private Perfil_dono dn = new Perfil_dono();
    private FirebaseAuth usuario = FirebaseAuth.getInstance();
    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference usuarios;
    private EditText descricao;
    private EditText nome;
    private EditText id;
    private EditText celular;
    private de.hdodenhof.circleimageview.CircleImageView foto;
    private ImageView fotoPet1, fotoPet2, fotoPet3, fotoPet4;
    private int qtdPet = 0;
    private int i;
    private String imagemUsuario;
    private ImageView voltar;
    private com.github.clans.fab.FloatingActionButton galeria;
    private com.github.clans.fab.FloatingActionButton camera;
    private FloatingActionButton pets;
    private Random random = new Random();
    private final int GALERIA_IMAGEM = 1;
    private final int PERMISION_REQUEST = 2;
    private final int CAMERA = 3;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela__editar__usuario);

        descricao = (EditText) findViewById(R.id.descricao);
        nome = (EditText) findViewById(R.id.nome);
        id = (EditText) findViewById(R.id.id);
        celular = (EditText) findViewById(R.id.celular);
        foto = (de.hdodenhof.circleimageview.CircleImageView) findViewById(R.id.fotoD);
        fotoPet1 = (ImageView) findViewById(R.id.pet1);
        fotoPet2 = (ImageView) findViewById(R.id.pet2);
        fotoPet3 = (ImageView) findViewById(R.id.pet3);
        fotoPet4 = (ImageView) findViewById(R.id.pet4);
        galeria = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.menu_galeria);
        camera = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.menu_foto);
        pets = (FloatingActionButton) findViewById(R.id.adicionarPet);
        voltar = findViewById(R.id.imageVoltareditar);
        usuarios = referencia.child("banco-dados").child("usuario").child(usuario.getUid());

        inicializando();
        //quantidadePet();
        permissao();
        buttonMenu();
    }

    /*private void quantidadePet() {
        if (qtdPet==1){
            fotoPet1.setVisibility(View.VISIBLE);
        }else if (qtdPet==2){
            fotoPet1.setVisibility(View.VISIBLE);
            fotoPet2.setVisibility(View.VISIBLE);
        }else if (qtdPet==3){
            fotoPet1.setVisibility(View.VISIBLE);
            fotoPet2.setVisibility(View.VISIBLE);
            fotoPet3.setVisibility(View.VISIBLE);
        }else if (qtdPet==4){
            fotoPet1.setVisibility(View.VISIBLE);
            fotoPet2.setVisibility(View.VISIBLE);
            fotoPet3.setVisibility(View.VISIBLE);
            fotoPet4.setVisibility(View.VISIBLE);
        }
    }*/

    public void inicializando (){

        usuarios.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                dn.setTexto(snapshot.child("texto").getValue().toString());
                dn.setNome(snapshot.child("nome").getValue().toString());
                dn.setId(snapshot.child("id").getValue().toString());
                dn.setCelular(snapshot.child("celular").getValue().toString());
                qtdPet = Integer.parseInt(snapshot.child("quatidadePet").getValue().toString());
                imagemUsuario = snapshot.child("imageUsuario").getValue().toString();

                mostrarInformacoes(dn.getTexto(), dn.getNome(), dn.getId(), dn.getCelular(), imagemUsuario);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void recuperandoDados(){

        usuarios.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                dn.setTexto(snapshot.child("texto").getValue().toString());
                dn.setNome(snapshot.child("nome").getValue().toString());
                dn.setId(snapshot.child("id").getValue().toString());
                dn.setCelular(snapshot.child("celular").getValue().toString());
                imagemUsuario = snapshot.child("imageUsuario").getValue().toString();

                atualizarInformacao(dn.getNome(), dn.getId(), dn.getCelular(), imagemUsuario);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void recuperandoImagem(String nome){

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
        StorageReference imagens = storageReference.child("BancoImagens").child("Usuarios").child(usuario.getUid());
        final StorageReference imagemRef = imagens.child(nome+".png");

        //Retorna o objeto que ira controlar o upload
        final UploadTask uploadTask = imagemRef.putBytes(dadosImagem);

        uploadTask.addOnFailureListener(Tela_Editar_Usuario.this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(Tela_Editar_Usuario.this, "Upload de Imagem falhou: " + e.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(Tela_Editar_Usuario.this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                imagemRef.getDownloadUrl().addOnCompleteListener(Tela_Editar_Usuario.this, new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {

                        Toast.makeText(Tela_Editar_Usuario.this, "Upload de Imagem: " + task.getResult().toString(),
                                Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }

    public void salvar (View view){

        recuperandoDados();
        Intent intent = new Intent(Tela_Editar_Usuario.this, Tela_principal.class);
        startActivity(intent);
        finish();

    }

    public void atualizarInformacao (String nome1, String id1, String celular1, String imagemUsuario) {

        deletarImagem(imagemUsuario);

        if (nome.getText().toString() != nome1 && id.getText().toString() != id1
                && celular.getText().toString() != celular1) {

            usuarios.child("texto").setValue(descricao.getText().toString());
            usuarios.child("nome").setValue(nome.getText().toString());
            usuarios.child("id").setValue(id.getText().toString());
            usuarios.child("celular").setValue(celular.getText().toString());
            recuperandoImagem(imagemUsuario);

        } else if (id.getText().toString() != id1
                && celular.getText().toString() != celular1) {

            usuarios.child("texto").setValue(descricao.getText().toString());
            usuarios.child("id").setValue(id.getText().toString());
            usuarios.child("celular").setValue(celular.getText().toString());
            recuperandoImagem(imagemUsuario);

        } else if (celular.getText().toString() != celular1) {

            usuarios.child("texto").setValue(descricao.getText().toString());
            usuarios.child("celular").setValue(celular.getText().toString());
            recuperandoImagem(imagemUsuario);

        } else {

            usuarios.child("texto").setValue(descricao.getText().toString());
            recuperandoImagem(imagemUsuario);

        }

    }

    public void mostrarInformacoes (String descricao1, String nome1, String id1, String celular1, String imagemUsuario) {

        descricao.setText(descricao1);
        nome.setText(nome1);
        id.setText(id1);
        celular.setText(celular1);

        imagemBaixar(imagemUsuario);
        baixarImagemPet();

    }

    public void recuperarImagem(){

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, GALERIA_IMAGEM);

    }

    public void deletarImagem(String nome){
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference imagens = storageReference.child("BancoImagens").child("Usuarios").child(usuario.getUid());
        StorageReference imagemRef = imagens.child(nome+".png");

        imagemRef.delete().addOnFailureListener(Tela_Editar_Usuario.this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Tela_Editar_Usuario.this, "Erro ao deletar imagem: "+e, Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(Tela_Editar_Usuario.this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });

    }

    public void tirarFoto(){

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, CAMERA);}
    }

    public void permissao(){
        if (ContextCompat.checkSelfPermission(Tela_Editar_Usuario.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

            if (ActivityCompat.shouldShowRequestPermissionRationale(Tela_Editar_Usuario.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)){

            }else{
                ActivityCompat.requestPermissions(Tela_Editar_Usuario.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISION_REQUEST);
            }
        }

    }

    public void buttonMenu(){

        galeria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recuperarImagem();
            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tirarFoto();
            }
        });

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        pets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qtdPet+=1;

                if(qtdPet<=4){

                Intent intent = new Intent(Tela_Editar_Usuario.this, Editar_Pet.class);
                intent.putExtra("dados", qtdPet);
                intent.putExtra("opcaoAcao", 1);
                startActivity(intent);
                finish();
                }else{
                    Toast.makeText(Tela_Editar_Usuario.this, "Limite de cadastro excedido!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        fotoPet1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tela_Editar_Usuario.this, Editar_Pet.class);
                intent.putExtra("dados", 1);
                intent.putExtra("opcaoAcao", 2);
                startActivity(intent);
                finish();
            }
        });

        fotoPet2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tela_Editar_Usuario.this, Editar_Pet.class);
                intent.putExtra("dados", 2);
                intent.putExtra("opcaoAcao", 2);
                startActivity(intent);
                finish();
            }
        });

        fotoPet3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tela_Editar_Usuario.this, Editar_Pet.class);
                intent.putExtra("dados", 3);
                intent.putExtra("opcaoAcao", 2);
                startActivity(intent);
                finish();
            }
        });

        fotoPet4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tela_Editar_Usuario.this, Editar_Pet.class);
                intent.putExtra("dados", 4);
                intent.putExtra("opcaoAcao", 2);
                startActivity(intent);
                finish();
            }
        });

    }

    public void imagemBaixar(String nome){
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference imagens = storageReference.child("BancoImagens").child("Usuarios").child(usuario.getUid());
        final StorageReference imagemRef = imagens.child(nome+".png");

        final long MEGABYTE = 1024 * 1024;

        imagemRef.getBytes(MEGABYTE).addOnSuccessListener(Tela_Editar_Usuario.this, new OnSuccessListener<byte[]>() {
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
            imagemRefpet.getBytes(MEGABYTE).addOnSuccessListener(Tela_Editar_Usuario.this, new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    if (finalI == 1) {
                        fotoPet1.setImageBitmap(bitmap);
                    } else if (finalI == 2) {
                        fotoPet2.setImageBitmap(bitmap);
                    } else if (finalI == 3) {
                        fotoPet3.setImageBitmap(bitmap);
                    } else if (finalI == 4) {
                        fotoPet4.setImageBitmap(bitmap);
                    }
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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

