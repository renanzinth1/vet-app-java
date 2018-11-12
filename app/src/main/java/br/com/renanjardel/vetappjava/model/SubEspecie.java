package br.com.renanjardel.vetappjava.model;

import java.io.Serializable;

public class SubEspecie implements Serializable {

    private Long codigo;

    private String nome;

    private Especie especie;

    public SubEspecie() {
        super();
    }

    public SubEspecie(Long codigo, String nome, Especie especie) {
        super();
        this.codigo = codigo;
        this.nome = nome;
        this.especie = especie;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Especie getEspecie() {
        return especie;
    }

    public void setEspecie(Especie especie) {
        this.especie = especie;
    }

    @Override
    public String toString() {
        return this.getNome();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SubEspecie other = (SubEspecie) obj;
        if (codigo == null) {
            if (other.codigo != null)
                return false;
        } else if (!codigo.equals(other.codigo))
            return false;
        return true;
    }
}
