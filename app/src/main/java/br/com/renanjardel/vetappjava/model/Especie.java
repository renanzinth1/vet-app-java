package br.com.renanjardel.vetappjava.model;

import java.util.List;

class Especie {

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

}
