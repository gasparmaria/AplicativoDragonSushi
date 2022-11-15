package com.example.dragonsushi.Objects;

import java.io.Serializable;
import java.util.Date;

public class Delivery implements Serializable {

    private int id, fkPessoa, fkEndereco, fkComanda, fkPagamento;
    private String data;

    public Delivery(){}

    public Delivery(int id, String data, int fkPessoa, int fkEndereco, int fkComanda, int fkPagamento){
        this.id = id;
        this.data = data;
        this.fkPessoa = fkPessoa;
        this.fkEndereco = fkEndereco;
        this.fkComanda = fkComanda;
        this.fkPagamento = fkPagamento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFkPessoa() {
        return fkPessoa;
    }

    public void setFkPessoa(int fkPessoa) {
        this.fkPessoa = fkPessoa;
    }

    public int getFkEndereco() {
        return fkEndereco;
    }

    public void setFkEndereco(int fkEndereco) {
        this.fkEndereco = fkEndereco;
    }

    public int getFkComanda() {
        return fkComanda;
    }

    public void setFkComanda(int fkComanda) {
        this.fkComanda = fkComanda;
    }

    public int getFkPagamento() {
        return fkPagamento;
    }

    public void setFkPagamento(int fkPagamento) {
        this.fkPagamento = fkPagamento;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
