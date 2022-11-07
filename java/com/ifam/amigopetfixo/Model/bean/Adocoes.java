package com.ifam.amigopetfixo.Model.bean;

import android.widget.ImageView;

import java.util.Date;

public class Adocoes {

    private String data_post;
    private String descricao;
    private String categoria;
    private String endereco;
    private String bairro;
    private String contato;
    private String titulo;

    public Adocoes() {
    }

    public Adocoes(String data_post, String descricao, String categoria, String endereco, String bairro, String contato, String titulo) {
        this.data_post = data_post;
        this.descricao = descricao;
        this.categoria = categoria;
        this.endereco = endereco;
        this.bairro = bairro;
        this.contato = contato;
        this.titulo = titulo;
    }

    public Adocoes(String data_post, String descricao, String categoria, String endereco, String bairro, String contato) {
        this.data_post = data_post;
        this.descricao = descricao;
        this.categoria = categoria;
        this.endereco = endereco;
        this.bairro = bairro;
        this.contato = contato;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getData_post() {
        return data_post;
    }

    public void setData_post(String data_post) {
        this.data_post = data_post;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }
}
