package br.com.renanjardel.vetappjava.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class Medicamento implements Serializable {

    private Long codigo;

    private String nome;

    private List<Medicacao> listaMedicacoes;

    public Medicamento() {
        super();
    }

    public Medicamento(Long codigo, String nome, List<Medicacao> listaMedicacoes) {
        super();
        this.codigo = codigo;
        this.nome = nome;
        this.listaMedicacoes = listaMedicacoes;
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

    public List<Medicacao> getListaMedicacoes() {
        return listaMedicacoes;
    }

    public void setListaMedicacoes(List<Medicacao> listaMedicacoes) {
        this.listaMedicacoes = listaMedicacoes;
    }

    @Override
    public String toString() {
        return this.getNome();
    }
}