package com.example.dragonsushi.Objects;

public class FormaPagamento {

    private int id;
    private String formaPag;

    public FormaPagamento(){}

    public FormaPagamento(int id, String formaPag){
        this.id = id;
        this.formaPag = formaPag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFormaPag() {
        return formaPag;
    }

    public void setFormaPag(String formaPag) {
        this.formaPag = formaPag;
    }
}
