package com.example.dragonsushi.Objects;

import java.io.Serializable;

public class FormaPagamento implements Serializable {

    private int id;
    private String formaPag;

    public FormaPagamento(){}

    public FormaPagamento(int id, String formaPag){
        this.id = id;
        this.formaPag = formaPag;
    }

    public FormaPagamento(String formaPag){
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
