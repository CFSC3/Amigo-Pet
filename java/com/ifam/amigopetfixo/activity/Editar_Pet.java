package com.ifam.amigopetfixo.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.ifam.amigopetfixo.Model.bean.Perfil_dono;
import com.ifam.amigopetfixo.Model.bean.Perfil_pet;
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
import com.ifam.amigopetfixo.R;

import java.io.ByteArrayOutputStream;

public class Editar_Pet extends AppCompatActivity {

    private Perfil_pet pn = new Perfil_pet();
    private Perfil_dono pd = new Perfil_dono();
    private FirebaseAuth usuario = FirebaseAuth.getInstance();
    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference pets;
    private DatabaseReference usuarios;
    private RecyclerView pet;
    private EditText nome;
    private EditText especie;
    private EditText raca;
    private EditText cor;
    private de.hdodenhof.circleimageview.CircleImageView foto;
    private RadioGroup sn;
    private int posicao;
    private int opcaoAcao;
    private ImageView voltar;
    private com.github.clans.fab.FloatingActionButton galeria;
    private com.github.clans.fab.FloatingActionButton camera;
    private final int GALERIA_IMAGEM = 1;
    private final int PERMISION_REQUEST = 2;
    private final int CAMERA = 3;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar__pet);

        especie = (EditText) findViewById(R.id.especie);
        nome = (EditText) findViewById(R.id.nomePet);
        raca = (EditText) findViewById(R.id.raca);
        cor = (EditText) findViewById(R.id.cor);
        foto = (de.hdodenhof.circleimageview.CircleImageView) findViewById(R.id.fotoPet);
        galeria = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.menu_galeria);
        camera = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.menu_foto);
        usuarios = referencia.child("banco-dados").child("usuario").child(usuario.getUid());
        sn =  findViewById(R.id.grupoButton);
        voltar = findViewById(R.id.imageVoltarPet);

        Bundle dados = getIntent().getExtras();
        posicao = dados.getInt("dados");
        opcaoAcao = dados.getInt("opcaoAcao");

        if (opcaoAcao==2){
            pets = referencia.child("banco-dados").child("usuario").child(usuario.getUid()).child("pet"+posicao);
            inicializando();
        }

        permissao();
        buttonMenu();
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

    public void recuperandoDados(){

        pets.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                pn.setNomePet(snapshot.child("nomePet").getValue().toString());
                pn.setEspecie(snapshot.child("especie").getValue().toString());
                pn.setRaca(snapshot.child("raca").getValue().toString());
                pn.setCor(snapshot.child("cor").getValue().toString());

                atualizarInformacao(pn.getNomePet(), pn.getEspecie(), pn.getRaca(), pn.getCor(), pn.getCastrado());
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
        StorageReference imagens = storageReference.child("BancoImagens").child("Usuarios").child(usuario.getUid()).child("pet"+posicao);
        final StorageReference imagemRef = imagens.child("pet"+posicao+".png");

        //Retorna o objeto que ira controlar o upload
        final UploadTask uploadTask = imagemRef.putBytes(dadosImagem);

        uploadTask.addOnFailureListener(Editar_Pet.this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(Editar_Pet.this, "Upload de Imagem falhou: " + e.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(Editar_Pet.this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                imagemRef.getDownloadUrl().addOnCompleteListener(Editar_Pet.this, new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {

                        Toast.makeText(Editar_Pet.this, "Upload de Imagem: " + task.getResult().toString(),
                                Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }

    public void salvar (View view){

        if (opcaoAcao==1) {

            pets = referencia.child("banco-dados").child("usuario").child(usuario.getUid()).child("pet"+posicao);

            int itemSelecionado = sn.getCheckedRadioButtonId();

            RadioButton categoriaSelecionada = findViewById(itemSelecionado);
            String selecionado = categoriaSelecionada.getText().toString();

            pets.child("nomePet").setValue(nome.getText().toString());
            pets.child("especie").setValue(especie.getText().toString());
            pets.child("raca").setValue(raca.getText().toString());
            pets.child("cor").setValue(cor.getText().toString());
            pets.child("castrado").setValue(selecionado);
            usuarios.child("quatidadePet").setValue(posicao);
            recuperandoImagem("pet"+posicao);


            Toast.makeText(Editar_Pet.this,"Salvo com sucesso quantidade: "+posicao,Toast.LENGTH_SHORT).show();

        }else if (opcaoAcao==3){
            recuperandoDados();
            //usuarios.child("quatidadePet").setValue(posicao);
        }

        Intent intent = new Intent(Editar_Pet.this, Tela_Editar_Usuario.class);
        startActivity(intent);
        finish();

    }

    public void atualizarInformacao (String nome1, String especie1, String raca1, String cor1, String castrado1) {

        deletarImagem(nome1);

        int itemSelecionado = sn.getCheckedRadioButtonId();

        if (nome.getText().toString() != nome1 && raca.getText().toString() != raca1
                && especie.getText().toString() != especie1 && cor.getText().toString() != cor1 && itemSelecionado != -1) {

            RadioButton categoriaSelecionada = findViewById(itemSelecionado);
            String selecionado = categoriaSelecionada.getText().toString();

            pets.child("nomePet").setValue(nome.getText().toString());
            pets.child("especie").setValue(especie.getText().toString());
            pets.child("raca").setValue(raca.getText().toString());
            pets.child("cor").setValue(cor.getText().toString());
            pets.child("castrado").setValue(selecionado);
            recuperandoImagem(nome1);

        } else if (raca.getText().toString() != raca1 && especie.getText().toString()
                != especie1 && cor.getText().toString() != cor1 && itemSelecionado != -1) {

            RadioButton categoriaSelecionada = findViewById(itemSelecionado);
            String selecionado = categoriaSelecionada.getText().toString();

            pets.child("especie").setValue(especie.getText().toString());
            pets.child("raca").setValue(raca.getText().toString());
            pets.child("cor").setValue(cor.getText().toString());
            pets.child("castrado").setValue(selecionado);
            recuperandoImagem(nome1);

        } else if (especie.getText().toString() != especie1 && cor.getText().toString() != cor1 && itemSelecionado != -1) {

            RadioButton categoriaSelecionada = findViewById(itemSelecionado);
            String selecionado = categoriaSelecionada.getText().toString();

            pets.child("especie").setValue(especie.getText().toString());
            pets.child("cor").setValue(cor.getText().toString());
            pets.child("castrado").setValue(selecionado);
            recuperandoImagem(nome1);

        } else if (cor.getText().toString() != cor1 && itemSelecionado != -1){

            RadioButton categoriaSelecionada = findViewById(itemSelecionado);
            String selecionado = categoriaSelecionada.getText().toString();

            pets.child("cor").setValue(cor.getText().toString());
            pets.child("castrado").setValue(selecionado);
            recuperandoImagem(nome1);


        }else {

            RadioButton categoriaSelecionada = findViewById(itemSelecionado);
            String selecionado = categoriaSelecionada.getText().toString();

            pets.child("castrado").setValue(selecionado);
            recuperandoImagem(nome1);
        }

    }

    public void mostrarInformacoes (String nome1, String especie1, String raca1, String cor1, String castrado1) {

        nome.setText(nome1);
        especie.setText(especie1);
        raca.setText(raca1);
        cor.setText(cor1);

        if(castrado1.equals("Sim")){
            sn.check(R.id.radioSim);
        }else if(castrado1.equals("Não")){
            sn.check(R.id.radioNao);
        }

        imagemBaixar(nome1);

    }

    public void recuperarImagem(){

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, GALERIA_IMAGEM);

    }

    public void deletarImagem(String nome){
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference imagens = storageReference.child("BancoImagens").child("Usuarios").child(usuario.getUid()).child("pet"+posicao);
        StorageReference imagemRef = imagens.child("pet"+posicao+".png");

        imagemRef.delete().addOnFailureListener(Editar_Pet.this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Editar_Pet.this, "Erro ao deletar imagem: "+e, Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(Editar_Pet.this, new OnSuccessListener<Void>() {
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
        if (ContextCompat.checkSelfPermission(Editar_Pet.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

            if (ActivityCompat.shouldShowRequestPermissionRationale(Editar_Pet.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)){

            }else{
                ActivityCompat.requestPermissions(Editar_Pet.this,
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
    }

    public void imagemBaixar(String nome){

                StorageReference storageReference = FirebaseStorage.getInstance().getReference();
                StorageReference imagens = storageReference.child("BancoImagens").child("Usuarios").child(usuario.getUid()).child("pet"+posicao);
                final StorageReference imagemRef = imagens.child("pet"+posicao+".png");

                final long MEGABYTE = 1024 * 1024;

        imagemRef.getBytes(MEGABYTE).addOnSuccessListener(Editar_Pet.this, new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                foto.setImageBitmap(bitmap);
            }
        });
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


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if(opcaoAcao==1){
            deletarImagem("pet"+posicao);
            Intent intent = new Intent(Editar_Pet.this, Tela_Editar_Usuario.class);
            startActivity(intent);
        }else if (opcaoAcao==2 || opcaoAcao==3){
            Intent intent = new Intent(Editar_Pet.this, Tela_Editar_Usuario.class);
            startActivity(intent);
        }

    }

}

