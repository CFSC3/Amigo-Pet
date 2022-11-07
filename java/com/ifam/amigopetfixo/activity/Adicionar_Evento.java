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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ifam.amigopetfixo.Model.bean.Evento;
import com.ifam.amigopetfixo.R;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.Random;

public class Adicionar_Evento extends AppCompatActivity {

    private FirebaseAuth usuario = FirebaseAuth.getInstance();
    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();
    private ImageView foto;
    private EditText titulo;
    private EditText resumo;
    private EditText descricao;
    private Button salvar;
    private Button cancelar;
    private Random random = new Random();
    private ImageView voltar;
    private final int GALERIA_IMAGEM = 1;
    private final int PERMISION_REQUEST = 2;
    private final int CAMERA = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar__evento);

        foto = findViewById(R.id.imageViewEvento);
        salvar = findViewById(R.id.salvarEvento);
        cancelar = findViewById(R.id.cancelarEvento);
        titulo = findViewById(R.id.editTextTituloEvento);
        resumo = findViewById(R.id.editTextResumo);
        descricao = findViewById(R.id.editTextDescricaoEvento);
        voltar = findViewById(R.id.imageVoltarAdicionarEvento);


        Bundle dados = getIntent().getExtras();

        int i = dados.getInt("opcao");

        if (i==1){
            recuperarImagem();
        }else if (i==2){
            tirarFoto();
        }else {
            Toast.makeText(Adicionar_Evento.this,"Algo deu errado: "+i, Toast.LENGTH_SHORT).show();
        }

        buttons();

    }

    private void buttons() {
        salvar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                salvar();
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void tirarFoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, CAMERA);}
    }

    private void recuperarImagem() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, GALERIA_IMAGEM);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void salvar(){

        DatabaseReference usuarios = referencia.child("banco-dados");

        //Capturando a Data Atual
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String data = dateFormat.format(date);


        if (titulo.getText().toString()!=null && titulo.getText().toString()!="" &&
                descricao.getText().toString()!=null && descricao.getText().toString()!="" &&
                resumo.getText().toString()!=null && resumo.getText().toString()!="") {

            String imagem = String.valueOf(Long.toHexString(random.nextLong()));

            //Colocando no Firebase
            Evento evento = new Evento();

            evento.setTitulo(titulo.getText().toString());
            evento.setResumo(resumo.getText().toString());
            evento.setDescricao(descricao.getText().toString());
            evento.setData_post(data);

            usuarios.child("Eventos").child(titulo.getText().toString()).setValue(evento);
            usuarios.child("Eventos").child(titulo.getText().toString()).child("imagem").setValue(imagem);
            usuarios.child("Eventos").child(titulo.getText().toString()).child("usuarioID").setValue(usuario.getUid());

            salvandoImagem(imagem);

            onBackPressed();

        }else {
            Toast.makeText(Adicionar_Evento.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();

        }
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
        StorageReference imagens = storageReference.child("BancoImagens").child("Eventos");
        final StorageReference imagemRef = imagens.child(nome+".png");

        //Retorna o objeto que ira controlar o upload
        final UploadTask uploadTask = imagemRef.putBytes(dadosImagem);

        uploadTask.addOnFailureListener(Adicionar_Evento.this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(Adicionar_Evento.this, "Upload de Imagem falhou: " + e.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(Adicionar_Evento.this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                imagemRef.getDownloadUrl().addOnCompleteListener(Adicionar_Evento.this, new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {

                        Toast.makeText(Adicionar_Evento.this, "Upload de Imagem: " + task.getResult().toString(),
                                Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

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