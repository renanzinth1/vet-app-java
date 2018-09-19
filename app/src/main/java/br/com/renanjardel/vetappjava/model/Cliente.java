package br.com.renanjardel.vetappjava.model;

import java.io.Serializable;
import java.util.List;

public class Cliente extends Pessoa implements Serializable {

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

    public String getCpfFormatado() {
        return this.cpf.substring(0,3) + "." + this.cpf.substring(3, 6) + "." + this.cpf.substring(6, 9) + "-" + this.cpf.substring(9, 11);
    }
}