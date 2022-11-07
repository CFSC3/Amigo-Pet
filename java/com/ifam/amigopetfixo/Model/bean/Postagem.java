package com.ifam.amigopetfixo.Model.bean;

public class Postagem {

    private String nome;
    private String postagem;
    private int imagem;
    private String image;
    private String data_poste;
    private String imageUsuario;
    private String idUsuario;

    public Postagem(){

    }

    public Postagem(String nome, String postagem, int imagem) {
        this.nome = nome;
        this.postagem = postagem;
        this.imagem = imagem;
    }

    public Postagem(String nome, String postagem, String image, String imageUsuario, String idUsuario) {
        this.nome = nome;
        this.postagem = postagem;
        this.image = image;
        this.imageUsuario = imageUsuario;
        this.idUsuario = idUsuario;
    }

    public Postagem(String nome, String postagem, String image, String data_poste, String imageUsuario, String idUsuario) {
        this.nome = nome;
        this.postagem = postagem;
        this.image = image;
        this.data_poste = data_poste;
        this.imageUsuario = imageUsuario;
        this.idUsuario = idUsuario;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getImageUsuario() {
        return imageUsuario;
    }

    public void setImageUsuario(String imageUsuario) {
        this.imageUsuario = imageUsuario;
    }

    public String getData_poste() {
        return data_poste;
    }

    public void setData_poste(String data_poste) {
        this.data_poste = data_poste;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPostagem() {
        return postagem;
    }

    public void setPostagem(String postagem) {
        this.postagem = postagem;
    }

    public int getImagem() {
        return imagem;
    }

    public void setImagem(int imagem) {
        this.imagem = imagem;
    }
}
