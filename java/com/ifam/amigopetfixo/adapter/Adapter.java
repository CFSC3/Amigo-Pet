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
import com.ifam.amigopetfixo.Model.bean.PostagemB;
import com.ifam.amigopetfixo.R;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private List<PostagemB> listaPostagemB;

    public Adapter(List<PostagemB> lista) {
    this.listaPostagemB = lista;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_lista,parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        PostagemB postagemB = listaPostagemB.get(position);

        holder.titulo.setText(postagemB.titulo);
        holder.local.setText(postagemB.local);
        holder.data.setText(postagemB.data);
        holder.imagem.setImageResource(postagemB.imagem);

        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference imagens = storageReference.child("BancoImagens").child("DoacoesAdocoes");
        final StorageReference imagemRef = imagens.child(postagemB.image+".png");

        final long MEGABYTE = 1024 * 1024;

        imagemRef.getBytes(MEGABYTE).addOnSuccessListener( new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                holder.imagem.setImageBitmap(bitmap);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listaPostagemB.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView titulo;
        private TextView local;
        private TextView data;
        private ImageView imagem;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            titulo = itemView.findViewById(R.id.textTitulo);
            local = itemView.findViewById(R.id.textLocal);
            data = itemView.findViewById(R.id.textData_post);
            imagem = itemView.findViewById(R.id.imageView);
        }
    }
}
