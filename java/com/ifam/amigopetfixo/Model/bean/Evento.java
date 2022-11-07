package com.ifam.amigopetfixo.Model.bean;

import android.widget.ImageView;

import java.util.Date;

public class Evento {

    private int imagem;
    private String data_post;
    private String titulo;
    private String resumo;
    private String descricao;
    private String imagemEvento;

    public Evento() {
    }

    public Evento(String data_post, String titulo, String resumo, String descricao, String imagemEvento) {
        this.data_post = data_post;
        this.titulo = titulo;
        this.resumo = resumo;
        this.descricao = descricao;
        this.imagemEvento = imagemEvento;
    }

    public Evento(int imagem, String titulo, String resumo) {
        this.imagem = imagem;
        this.titulo = titulo;
        this.resumo = resumo;
    }

    public Evento(String titulo, String resumo, String imagemEvento) {
        this.titulo = titulo;
        this.resumo = resumo;
        this.imagemEvento = imagemEvento;
    }

    public String getImagemEvento() {
        return imagemEvento;
    }

    public void setImagemEvento(String imagemEvento) {
        this.imagemEvento = imagemEvento;
    }

    public int getImagem() {
        return imagem;
    }

    public void setImagem(int imagem) {
        this.imagem = imagem;
    }

    public String getData_post() {
        return data_post;
    }

    public void setData_post(String data_post) {
        this.data_post = data_post;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getResumo() {
        return resumo;
    }

    public void setResumo(String resumo) {
        this.resumo = resumo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
