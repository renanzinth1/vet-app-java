package br.com.renanjardel.vetappjava.model;

import java.io.Serializable;
import java.util.List;

public class Especie implements Serializable {

    private Long codigo;

    private String nome;

    private List<SubEspecie> listaSubEspecies;

    public Especie() {
        super();
    }

    public Especie(Long codigo, String nome, List<SubEspecie> listaSubEspecies) {
        super();
        this.codigo = codigo;
        this.nome = nome;
        this.listaSubEspecies = listaSubEspecies;
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

    public List<SubEspecie> getListaSubEspecies() {
        return listaSubEspecies;
    }

    public void setListaSubEspecies(List<SubEspecie> listaSubEspecies) {
        this.listaSubEspecies = listaSubEspecies;
    }

    @Override
    public String toString() {
        return getNome();
    }
}
