package com.ifam.amigopetfixo.Model.bean;

public class PostagemB {

    public String titulo;
    public String local;
    public String data;
    public String image;
    public int imagem;

    public PostagemB(){

    }

    public PostagemB(String titulo, String local, String data, String image) {
        this.titulo = titulo;
        this.local = local;
        this.data = data;
        this.image = image;
    }

    public PostagemB(String titulo, String local, int imagem, String data) {
        this.titulo = titulo;
        this.local = local;
        this.data = data;
        this.imagem = imagem;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getImagem() {
        return imagem;
    }

    public void setImagem(int imagem) {
        this.imagem = imagem;
    }
}
