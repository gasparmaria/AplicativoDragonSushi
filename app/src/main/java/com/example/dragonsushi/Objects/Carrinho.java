package com.example.dragonsushi.Objects;

import android.widget.ImageView;

public class Carrinho {
    String imgProd, nomeProd, obsPed;
    double subPed;

    public Carrinho(){}

    public Carrinho(String imgProd, String nomeProd, String obsPed, double subPed) {
        this.imgProd = imgProd;
        this.nomeProd = nomeProd;
        this.obsPed = obsPed;
        this.subPed = subPed;
    }

    public String getImgProd() {
        return imgProd;
    }

    public void setImgProd(String imgProd) {
        this.imgProd = imgProd;
    }

    public String getNomeProd() {
        return nomeProd;
    }

    public void setNomeProd(String nomeProd) {
        this.nomeProd = nomeProd;
    }

    public String getObsPed() {
        return obsPed;
    }

    public void setObsPed(String obsPed) {
        this.obsPed = obsPed;
    }

    public double getSubPed() {
        return subPed;
    }

    public void setSubPed(double subPed) {
        this.subPed = subPed;
    }
}
