package com.ifam.amigopetfixo.Model.bean;

import android.widget.ImageView;

public class Perfil_pet {

    private String nomePet;
    private String especie;
    private String raca;
    private String cor;
    private String castrado;
    private ImageView foto;

    public Perfil_pet() {
    }

    public Perfil_pet(String nomePet, String especie, String raca, String cor, String castrado, ImageView foto) {
        this.nomePet = nomePet;
        this.especie = especie;
        this.raca = raca;
        this.cor = cor;
        this.castrado = castrado;
        this.foto = foto;
    }

    public String getNomePet() {
        return nomePet;
    }

    public void setNomePet(String nomePet) {
        this.nomePet = nomePet;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getCastrado() {
        return castrado;
    }

    public void setCastrado(String castrado) {
        this.castrado = castrado;
    }

    public ImageView getFoto() {
        return foto;
    }

    public void setFoto(ImageView foto) {
        this.foto = foto;
    }
}
