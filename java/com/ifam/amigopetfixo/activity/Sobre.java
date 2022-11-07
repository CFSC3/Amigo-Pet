package com.ifam.amigopetfixo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.ifam.amigopetfixo.R;

public class Sobre extends AppCompatActivity {

    private ImageView voltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre);

        voltar = findViewById(R.id.imageVoltarSobre);

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Sobre.this,Configuracao.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Sobre.this,Configuracao.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void finish() {
        super.finish();
    }
}