package com.ifam.amigopetfixo.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.ifam.amigopetfixo.R;


public class MainActivity extends AppCompatActivity {

    private FirebaseAuth usuario = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (usuario.getCurrentUser()==null) {
            setContentView(R.layout.activity_main);
        }else{
            Intent intent = new Intent(this,Tela_principal.class);
            startActivity(intent);
            finish();
        }
    }

    public void login(View view){
        Intent intent = new Intent(this,LoginT.class);
        startActivity(intent);
    }

    public void visitante(View view){
        Intent intent = new Intent(this,Tela_principal.class);
        startActivity(intent);
    }

    public void cadastro(View view){
        Intent intent = new Intent(this,Cadastro.class);
        startActivity(intent);
    }
}
