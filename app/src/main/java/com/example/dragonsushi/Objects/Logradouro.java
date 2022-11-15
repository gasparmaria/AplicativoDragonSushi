package com.example.dragonsushi.Objects;

public class Logradouro {
    int id;
    String logradouro;

    public Logradouro(int id, String logradouro) {
        this.id = id;
        this.logradouro = logradouro;
    }

    public Logradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }
}
