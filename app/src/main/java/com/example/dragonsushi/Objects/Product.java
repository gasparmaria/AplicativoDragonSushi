package com.example.dragonsushi.Objects;

import org.json.JSONArray;

public class Product extends JSONArray {

    private int id;
    private byte[] imagem;
    private double preco;
    private String nome, descricao;

    public Product(){}

    public Product(int id, String nome, byte[] imagem, String descricao, double preco){
        this.id = id;
        this.nome = nome;
        this.imagem = imagem;
        this.descricao = descricao;
        this.preco = preco;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
