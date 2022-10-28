package com.example.dragonsushi.Objects;

public class User {

    private int id, fkPessoa;
    private String login, senha;

    public User(){}

    public User(int id, String login, String senha, int fkPessoa){
        this.id = id;
        this.login = login;
        this.senha = senha;
        this.fkPessoa = fkPessoa;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
