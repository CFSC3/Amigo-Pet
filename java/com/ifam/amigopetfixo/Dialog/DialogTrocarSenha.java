package com.ifam.amigopetfixo.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.ifam.amigopetfixo.R;


public class DialogTrocarSenha extends AppCompatDialogFragment {

    private FirebaseAuth usuario = FirebaseAuth.getInstance();
    private EditText senhaNova;
    private EditText confSenha;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialogtrocarsenha, null);

        senhaNova = view.findViewById(R.id.editTextTextPasswordSenha);
        confSenha = view.findViewById(R.id.editTextTextPasswordConf);

        builder.setView(view)
                .setTitle("Alterar senha")
                .setIcon(R.drawable.logo_png)
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (senhaNova.getText().toString().equals(confSenha.getText().toString())){
                            usuario.getCurrentUser().updatePassword(senhaNova.getText().toString());
                        }else{
                            Toast.makeText(getActivity(), "Erro: Senha errada!", Toast.LENGTH_LONG).show();
                        }

                    }
                });

        return builder.create();
    }
}
