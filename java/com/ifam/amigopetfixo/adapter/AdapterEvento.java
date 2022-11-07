package com.ifam.amigopetfixo.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ifam.amigopetfixo.Model.bean.Evento;
import com.ifam.amigopetfixo.R;

import java.util.List;

public class AdapterEvento extends RecyclerView.Adapter<AdapterEvento.MyViewHolder> {

    private List<Evento> evento;

    public AdapterEvento(List<Evento> lista) {
        this.evento = lista;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        Evento eventos = evento.get(position);

        holder.titulo.setText(eventos.getTitulo());
        holder.resumo.setText(eventos.getResumo());

        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference imagens = storageReference.child("BancoImagens").child("Eventos");
        final StorageReference imagemRef = imagens.child(eventos.getImagemEvento()+".png");

        final long MEGABYTE = 1024 * 1024;

        imagemRef.getBytes(MEGABYTE).addOnSuccessListener( new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                holder.imagem.setImageBitmap(bitmap);
            }
        });

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.eventos_detalhes,parent, false);
        return new AdapterEvento.MyViewHolder(itemLista);
    }

    @Override
    public int getItemCount() {
        return evento.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView titulo;
        private TextView resumo;
        private ImageView imagem;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            titulo = itemView.findViewById(R.id.textTituloE);
            imagem = itemView.findViewById(R.id.imageViewEventos);
            resumo = itemView.findViewById(R.id.textResumoE);
        }
    }
}

