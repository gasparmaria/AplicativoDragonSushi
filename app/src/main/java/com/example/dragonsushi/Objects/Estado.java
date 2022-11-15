package com.example.dragonsushi.Objects;

import java.io.Serializable;

public class Estado implements Serializable {
    String uf;

    public Estado(String uf) {
        this.uf = uf;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }
}
