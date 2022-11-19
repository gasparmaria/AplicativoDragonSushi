package com.example.dragonsushi.Objects;

import android.widget.ImageView;

import java.io.Serializable;

public class Carrinho implements Serializable {
    int idPedido;
    String imgProd, nomeProd, obsPed;

    double subPed;

    public Carrinho(){}

    public Carrinho(int idPedido, String imgProd, String nomeProd, String obsPed, double subPed) {
        this.idPedido = idPedido;
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

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

}
