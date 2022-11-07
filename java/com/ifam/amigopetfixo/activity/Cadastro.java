package com.ifam.amigopetfixo.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ifam.amigopetfixo.Model.bean.Perfil_dono;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ifam.amigopetfixo.R;

import java.util.Random;

public class Cadastro extends AppCompatActivity {

    private TextInputLayout textoEmail;
    private TextInputLayout textoSenha;
    private TextInputLayout textoConfSenha;
    private FirebaseAuth usuario = FirebaseAuth.getInstance();
    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();
    private TextView textoErro;
    private Random random = new Random();
   // private CadastroQ cd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        textoEmail = (TextInputLayout) findViewById(R.id.textInputLayout2);
        textoSenha = (TextInputLayout) findViewById(R.id.textInputLayout4);
        textoConfSenha = (TextInputLayout) findViewById(R.id.textInputLayout5);
        textoErro = findViewById(R.id.erro);
        //cd = new CadastroQ(textoEmail.getText().toString(), toString(textoSenha.getText()),toString(textoConfSenha.getText()));
    }

    public void cadastrar(View view){

        if (validateEmailFormat(textoEmail.getEditText().getText().toString())) {
            if (textoSenha.getEditText().getText().toString().equals(textoConfSenha.getEditText().getText().toString())) {

                usuario.createUserWithEmailAndPassword(textoEmail.getEditText().getText().toString(),textoSenha.getEditText().getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            usuarioP(textoEmail.getEditText().getText().toString());
                            Intent intent = new Intent(Cadastro.this, Apresentacao.class);
                            startActivity(intent);
                            finish();
                        }else{

                        }
                    }
                });
                ;
            } else {
                textoErro.setVisibility(View.VISIBLE);
                //textoErro.setCursorVisible(true);
            }
        }

    }

    public void usuarioP(String email)  {

        DatabaseReference usuarios = referencia.child("banco-dados");
        String imagem = String.valueOf(Long.toHexString(random.nextLong()));

        Perfil_dono pf = new Perfil_dono();
        pf.setNome("");
        pf.setCelular("");
        pf.setId("");
        pf.setTexto("");
        pf.setEmail(email);

        usuarios.child("usuario").child(usuario.getUid()).setValue(pf);
        usuarios.child("usuario").child(usuario.getUid()).child("quatidadePet").setValue("0");
        usuarios.child("usuario").child(usuario.getUid()).child("imageUsuario").setValue(imagem);
        usuarios.child("usuario").child(usuario.getUid()).child("postagens").setValue("0");

    }

    private boolean validateEmailFormat(final String email){
        if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return true;
        }
        Toast.makeText(Cadastro.this,"Email Invalido", Toast.LENGTH_SHORT).show();
        return false;
    }
}
