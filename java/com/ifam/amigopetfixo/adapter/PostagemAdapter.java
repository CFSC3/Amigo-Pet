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
import com.ifam.amigopetfixo.Model.bean.Postagem;
import com.ifam.amigopetfixo.R;

import java.util.List;

public class PostagemAdapter extends RecyclerView.Adapter<PostagemAdapter.MyViewHolder> {

    private List<Postagem> listapostagem;

    public PostagemAdapter(List<Postagem> p) {
        this.listapostagem = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.postagem_detalhe, parent, false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        Postagem postagem = listapostagem.get(position);
        holder.nome.setText(postagem.getNome());
        holder.postagem.setText(postagem.getPostagem());

        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference imagens = storageReference.child("BancoImagens").child("Postagens");
        final StorageReference imagemRef = imagens.child(postagem.getImage()+".png");

        final long MEGABYTE = 1024 * 1024;

        imagemRef.getBytes(MEGABYTE).addOnSuccessListener( new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                holder.imagem.setImageBitmap(bitmap);
            }
        });

        StorageReference storageReferences = FirebaseStorage.getInstance().getReference();
        StorageReference imagem = storageReferences.child("BancoImagens").child("Usuarios").child(postagem.getIdUsuario());
        final StorageReference imagemRefi = imagem.child(postagem.getImageUsuario()+".png");

        final long MEGABYTES = 1024 * 1024;

        imagemRefi.getBytes(MEGABYTES).addOnSuccessListener( new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                holder.image.setImageBitmap(bitmap);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.listapostagem.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView nome;
        private TextView postagem;
        private ImageView imagem;
        private de.hdodenhof.circleimageview.CircleImageView image;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            nome = itemView.findViewById(R.id.textNome);
            postagem = itemView.findViewById(R.id.textpostagem);
            imagem = itemView.findViewById(R.id.imagePostagem);
            image = itemView.findViewById(R.id.imageUsuario);
        }
    }

}
