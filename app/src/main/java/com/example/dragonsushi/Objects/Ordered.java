package com.example.dragonsushi.Objects;

public class Ordered {

    private int id, quantidade, fkProduto, fkComanda;
    private String descricao;

    public Ordered(){}

    public Ordered(int id, int quantidade, String descricao, int fkProduto, int fkComanda){
        this.id = id;
        this.quantidade = quantidade;
        this.descricao = descricao;
        this.fkProduto = fkProduto;
        this.fkComanda = fkComanda;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getFkProduto() {
        return fkProduto;
    }

    public void setFkProduto(int fkProduto) {
        this.fkProduto = fkProduto;
    }

    public int getFkComanda() {
        return fkComanda;
    }

    public void setFkComanda(int fkComanda) {
        this.fkComanda = fkComanda;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
