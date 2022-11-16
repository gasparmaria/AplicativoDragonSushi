package com.example.dragonsushi.Objects;

import java.io.Serializable;

public class Person implements Serializable {

    private int idPessoa;
    private String nomePessoa, telefone, cpf;

    public Person(){}

    public Person(int idPessoa, String nomePessoa, String telefone, String cpf){
        this.idPessoa = idPessoa;
        this.nomePessoa = nomePessoa;
        this.telefone = telefone;
        this.cpf = cpf;
    }
    public Person(int idPessoa, String nomePessoa, String telefone){
        this.idPessoa = idPessoa;
        this.nomePessoa = nomePessoa;
        this.telefone = telefone;
    }

    public Person(String nomePessoa, String telefone){
        this.nomePessoa = nomePessoa;
        this.telefone = telefone;
    }

    public Person(String nomePessoa, String telefone, String cpf){
        this.nomePessoa = nomePessoa;
        this.telefone = telefone;
        this.cpf = cpf;
    }

    public int getId() {
        return idPessoa;
    }

    public void setId(int idPessoa) {
        this.idPessoa = idPessoa;
    }

    public String getNome() {
        return nomePessoa;
    }

    public void setNome(String nome) {
        this.nomePessoa = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}