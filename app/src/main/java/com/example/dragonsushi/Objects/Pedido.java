package com.example.dragonsushi.Objects;

public class Pedido {
    int idPedido, qtdProd, fkProd, fkComanda;
    String descrPedido;

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getQtdProd() {
        return qtdProd;
    }

    public void setQtdProd(int qtdProd) {
        this.qtdProd = qtdProd;
    }

    public int getFkProd() {
        return fkProd;
    }

    public void setFkProd(int fkProd) {
        this.fkProd = fkProd;
    }

    public int getFkComanda() {
        return fkComanda;
    }

    public void setFkComanda(int fkComanda) {
        this.fkComanda = fkComanda;
    }

    public String getDescrPedido() {
        return descrPedido;
    }

    public void setDescrPedido(String descrPedido) {
        this.descrPedido = descrPedido;
    }
}
