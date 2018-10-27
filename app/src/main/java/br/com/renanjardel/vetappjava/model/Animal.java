package br.com.renanjardel.vetappjava.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Animal implements Serializable {

    private Long codigo;

    private String nome;

    private String dataNascimento;

    private SexoAnimal sexo;

    private SubEspecie subEspecie;

    private Cliente cliente;

    public Animal() {
        super();
    }

    public Animal(Long codigo, String nome, String dataNascimento, SexoAnimal sexo, SubEspecie subEspecie, Cliente cliente) {
        this.codigo = codigo;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.sexo = sexo;
        this.subEspecie = subEspecie;
        this.cliente = cliente;
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

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public SexoAnimal getSexo() {
        return sexo;
    }

    public void setSexo(SexoAnimal sexo) {
        this.sexo = sexo;
    }

    public SubEspecie getSubEspecie() {
        return subEspecie;
    }

    public void setSubEspecie(SubEspecie subEspecie) {
        this.subEspecie = subEspecie;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public String toString() {
        return this.getNome();
    }
}
