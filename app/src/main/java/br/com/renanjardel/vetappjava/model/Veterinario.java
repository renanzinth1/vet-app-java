package br.com.renanjardel.vetappjava.model;

import java.io.Serializable;

public class Veterinario extends Pessoa implements Serializable {

    private String cfmv;

    public Veterinario() {
        super();
    }

    public Veterinario(String cfmv) {
        super();
        this.cfmv = cfmv;
    }

    public String getCfmv() {
        return cfmv;
    }

    public void setCfmv(String cfmv) {
        this.cfmv = cfmv;
    }
}