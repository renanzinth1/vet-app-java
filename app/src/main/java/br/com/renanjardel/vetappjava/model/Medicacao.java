package br.com.renanjardel.vetappjava.model;

import java.io.Serializable;

public class Medicacao implements Serializable {

    private Long codigo;

    private Tratamento tratamento;

    private Medicamento medicamento;

    private float dosagem;

    public Medicacao() {
        super();
    }

    public Medicacao(Long codigo, float dosagem, Tratamento tratamento, Medicamento medicamento) {
        super();
        this.codigo = codigo;
        this.dosagem = dosagem;
        this.tratamento = tratamento;
        this.medicamento = medicamento;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public float getDosagem() {
        return dosagem;
    }

    public void setDosagem(float dosagem) {
        this.dosagem = dosagem;
    }

    public Tratamento getTratamento() {
        return tratamento;
    }

    public void setTratamento(Tratamento tratamento) {
        this.tratamento = tratamento;
    }
}