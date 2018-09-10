package br.com.renanjardel.vetappjava.modelo;

import java.util.List;

public class Cliente {

    private String cpf;

    private List<Animal> listaAnimais;

    public Cliente() {
        super();
    }

    public Cliente(String cpf, List<Animal> listaAnimais) {
        super();
        this.cpf = cpf;
        this.listaAnimais = listaAnimais;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public List<Animal> getListaAnimais() {
        return listaAnimais;
    }

    public void setListaAnimais(List<Animal> listaAnimais) {
        this.listaAnimais = listaAnimais;
    }

}