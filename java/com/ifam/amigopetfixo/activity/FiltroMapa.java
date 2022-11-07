package com.ifam.amigopetfixo.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ifam.amigopetfixo.R;

public class FiltroMapa extends AppCompatActivity {

    private Button opcaoPetShop, opcaoCasaAdocao, opcaoTudo;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtro_mapa);

        opcaoPetShop = findViewById(R.id.buttonPetVet);
        opcaoCasaAdocao = findViewById(R.id.buttonCasasOng);
        opcaoTudo = findViewById(R.id.buttonTudo);

        opcaoPetShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FiltroMapa.this,Tela_principal.class);
                intent.putExtra("opcaoTelaPrincipal", 2);
                intent.putExtra("opcaoMapa", 1);
                startActivity(intent);
                finish();

            }
        });

        opcaoCasaAdocao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FiltroMapa.this,Tela_principal.class);
                intent.putExtra("opcaoTelaPrincipal", 2);
                intent.putExtra("opcaoMapa", 2);
                startActivity(intent);
                finish();

            }
        });

        opcaoTudo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FiltroMapa.this,Tela_principal.class);
                intent.putExtra("opcaoTelaPrincipal", 2);
                intent.putExtra("opcaoMapa", 3);
                startActivity(intent);
                finish();

            }
        });

    }

}