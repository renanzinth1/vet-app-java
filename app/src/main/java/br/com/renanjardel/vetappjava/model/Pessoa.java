package br.com.renanjardel.vetappjava.model;

import java.io.Serializable;

public class Pessoa implements Serializable {

    private Long codigo;

    private String nome;

    private String sobrenome;

    private String telefone;

    private String dataNascimento;

    private Sexo sexo;

    public Pessoa() {
    }

    public Pessoa(Long codigo, String nome, String sobrenome, String telefone, String dataNascimento, Sexo sexo) {
        this.codigo = codigo;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.telefone = telefone;
        this.dataNascimento = dataNascimento;
        this.sexo = sexo;
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

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }
}
