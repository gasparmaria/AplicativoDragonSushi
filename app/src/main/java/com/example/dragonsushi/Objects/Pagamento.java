package com.example.dragonsushi.Objects;

public class Pagamento {
    int idPag, fkFormaPag;
    double total;

    public int getIdPag() {
        return idPag;
    }

    public void setIdPag(int idPag) {
        this.idPag = idPag;
    }

    public int getFkFormaPag() {
        return fkFormaPag;
    }

    public void setFkFormaPag(int fkFormaPag) {
        this.fkFormaPag = fkFormaPag;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
