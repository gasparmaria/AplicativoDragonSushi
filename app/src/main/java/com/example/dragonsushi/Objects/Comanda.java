package com.example.dragonsushi.Objects;

public class Comanda {
    int idComanda;
    double subtotal;



    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public  Comanda(){

    }

    public int getIdComanda() {
        return idComanda;
    }

    public void setIdComanda(int idComanda) {
        this.idComanda = idComanda;
    }

    public Comanda(int idComanda, double subtotal) {
        this.idComanda = idComanda;
        this.subtotal = subtotal;
    }
}
