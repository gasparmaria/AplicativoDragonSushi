package com.example.dragonsushi.Objects;

import java.io.Serializable;

public class Bairro implements Serializable {
    int id;
    String bairro;

    public Bairro(int id, String bairro) {
        this.id = id;
        this.bairro = bairro;
    }

    public Bairro(String bairro) {
        this.bairro = bairro;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }
}
