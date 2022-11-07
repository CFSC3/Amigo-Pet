package com.ifam.amigopetfixo.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.ifam.amigopetfixo.Dialog.DialogConfirmarExcluir;
import com.ifam.amigopetfixo.Dialog.DialogExcluir;
import com.ifam.amigopetfixo.Dialog.DialogTrocarSenha;
import com.ifam.amigopetfixo.R;

public class Configuracao extends AppCompatActivity {

    private FirebaseAuth usuario = FirebaseAuth.getInstance();
    private Button trocarSenha;
    private Button sobre;
    private Button politicaPrivacidade;
    private Button termosCondicoes;
    private Button excluir;
    private Button sair;
    private ImageView voltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracao);

        trocarSenha = findViewById(R.id.trocarSenha);
        sobre = findViewById(R.id.sobre);
        politicaPrivacidade = findViewById(R.id.politicaPrivacidade);
        termosCondicoes = findViewById(R.id.termosCondicoes);
        excluir = findViewById(R.id.excluir);
        sair = findViewById(R.id.sair);
        voltar = findViewById(R.id.imageConfVoltar);

    }

    public void trocarSenha(View view){
        DialogTrocarSenha dialogTrocarSenha = new DialogTrocarSenha();
        dialogTrocarSenha.show(getSupportFragmentManager(), "example dialog");
    }

    public void sobre(View view){
        Intent intent = new Intent(Configuracao.this,Sobre.class);
        startActivity(intent);
        finish();
    }

    public void voltar(View view){
                Intent intent = new Intent(Configuracao.this,Tela_principal.class);
                startActivity(intent);
                onBackPressed();
    }

    public void politicaPrivacidade(View view){
        Intent intent = new Intent(Configuracao.this,PoliticaPrivacidade.class);
        startActivity(intent);
        finish();
    }

    public void termosCondicoes(View view){
                Intent intent = new Intent(Configuracao.this,TermosUso.class);
                startActivity(intent);
                finish();
    }

    public void excluir(View view){
        DialogExcluir excluir = new DialogExcluir();
        excluir.show(getSupportFragmentManager(), "example dialog");
    }

    public void sair(View view){

                AlertDialog.Builder alert = new AlertDialog.Builder(Configuracao.this);
                alert.setTitle("Sair");
                alert.setIcon(R.drawable.logo_png);
                alert.setMessage("Tem certeza que deseja sair de sua conta?");
                alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        usuario.signOut();
                        Intent intent = new Intent(Configuracao.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                alert.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                alert.create().show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}