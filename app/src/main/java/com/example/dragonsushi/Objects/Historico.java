package com.example.dragonsushi.Objects;

public class Historico {
    String dataDelivery, formaPag;
    double total;
    int numPedidos, idDelivery;

    public String getDataDelivery() {
        return dataDelivery;
    }

    public void setDataDelivery(String dataDelivery) {
        this.dataDelivery = dataDelivery;
    }

    public String getFormaPag() {
        return formaPag;
    }

    public void setFormaPag(String formaPag) {
        this.formaPag = formaPag;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getNumPedidos() {
        return numPedidos;
    }

    public void setNumPedidos(int numPedidos) {
        this.numPedidos = numPedidos;
    }

    public int getIdDelivery() {
        return idDelivery;
    }

    public void setIdDelivery(int idDelivery) {
        this.idDelivery = idDelivery;
    }
}
