package com.ifam.amigopetfixo.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.ifam.amigopetfixo.R;

public class LoginT extends AppCompatActivity {

    private FirebaseAuth usuario = FirebaseAuth.getInstance();
    private TextInputLayout textoEmail;
    private TextInputLayout textoSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_t);

        textoEmail = (TextInputLayout) findViewById(R.id.textInputLayout3);
        textoSenha = (TextInputLayout) findViewById(R.id.textInputLayout7);
    }

    public void login(View view){

        usuario.signInWithEmailAndPassword(textoEmail.getEditText().getText().toString(),textoSenha.getEditText().getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Intent intent = new Intent(LoginT.this,Tela_principal.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(LoginT.this, "Dados informados estão incorretos ou não existem", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
