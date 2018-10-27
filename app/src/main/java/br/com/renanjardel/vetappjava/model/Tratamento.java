package br.com.renanjardel.vetappjava.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

public class Tratamento implements Serializable {

    private Long codigo;

    private String resumo;

    private Consulta consulta;

    private List<Medicacao> listaMedicacoes;

    public Tratamento() {
        super();
    }

    public Tratamento(Long codigo, String resumo, List<Medicacao> listaMedicacoes) {
        super();
        this.codigo = codigo;
        this.resumo = resumo;
        this.listaMedicacoes = listaMedicacoes;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getResumo() {
        return resumo;
    }

    public void setResumo(String resumo) {
        this.resumo = resumo;
    }

    public List<Medicacao> getListaMedicacoes() {
        return listaMedicacoes;
    }

    public void setListaMedicacoes(List<Medicacao> listaMedicacoes) {
        this.listaMedicacoes = listaMedicacoes;
    }
}
