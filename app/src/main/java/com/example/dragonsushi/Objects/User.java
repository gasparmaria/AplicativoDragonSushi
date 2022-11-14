package com.example.dragonsushi.Objects;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {

    int idUsuario;
    int fkPessoa;
    String login;
    String senha;


    public User(Integer idUsuario, Integer fkPessoa, String login, String senha) {
        this.idUsuario = idUsuario;
        this.fkPessoa = fkPessoa;
        this.login = login;
        this.senha = senha;
    }

    public User(String login, String senha) {
        this.login = login;
        this.senha = senha;
    }

    public User() {

    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public Integer getFkPessoa() {
        return fkPessoa;
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setFkPessoa(Integer fkPessoa) {
        this.fkPessoa = fkPessoa;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }


}
