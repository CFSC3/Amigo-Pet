package com.ifam.amigopetfixo.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.ifam.amigopetfixo.activity.Configuracao;
import com.ifam.amigopetfixo.R;


public class DialogExcluir extends AppCompatDialogFragment {

    private FirebaseAuth usuario = FirebaseAuth.getInstance();
    private Configuracao c;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setTitle("Excluir")
                .setMessage("Quer excluir sua conta?"
                        + "Todos os seus dados serão deletados permanentemente.")
                .setIcon(R.drawable.logo_png)
                .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            DialogConfirmarExcluir confirmarExcluir = new DialogConfirmarExcluir();
                            confirmarExcluir.show(getParentFragmentManager(), "example dialog");
                    }
                });

        return builder.create();
    }
}
