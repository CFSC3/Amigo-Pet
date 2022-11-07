package com.ifam.amigopetfixo.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.ifam.amigopetfixo.activity.Configuracao;
import com.ifam.amigopetfixo.R;


public class DialogConfirmarExcluir extends AppCompatDialogFragment {

    private FirebaseAuth usuario = FirebaseAuth.getInstance();
    private Configuracao c = new Configuracao();
    private TextView email;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialogtexcluir, null);
        email = view.findViewById(R.id.editTextTextEmailExcl);



        builder.setView(view)
                .setTitle("Excluir")
                .setMessage("Quer excluir sua conta? /n Todos os seus dados serão deletados permanentemente.")
                .setIcon(R.drawable.logo_png)
                .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(email.equals(usuario.getCurrentUser().getEmail()) || email.toString() == usuario.getCurrentUser().getEmail()) {

                            usuario.getCurrentUser().delete();

                        }else{
                            Toast.makeText(getActivity(),"Email digitado não coincidiu com o email desta conta esta conta!", Toast.LENGTH_LONG ).show();
                        }
                    }
                });

        return builder.create();
    }
}
