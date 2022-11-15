package com.example.dragonsushi.Objects;

public class Cidade {
    int id;
    String cidade;

    public Cidade(int id, String cidade) {
        this.id = id;
        this.cidade = cidade;
    }

    public Cidade(String cidade) {
        this.cidade = cidade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }
}
