package br.com.renanjardel.vetappjava.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;

public class Consulta {

    private Long codigo;

    private LocalDateTime dataHora;

    private String resumo;

    private Veterinario veterinario;

    private Animal animal;

    private List<Tratamento> listaTratamentos;

    public Consulta() {
        super();
    }

    public Consulta(Long codigo, LocalDateTime dataHora, String resumo,
                    Veterinario veterinario, Animal animal, List<Tratamento> listaTratamentos) {
        super();
        this.codigo = codigo;
        this.dataHora = dataHora;
        this.resumo = resumo;
        this.veterinario = veterinario;
        this.animal = animal;
        this.listaTratamentos = listaTratamentos;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getResumo() {
        return resumo;
    }

    public void setResumo(String resumo) {
        this.resumo = resumo;
    }

    public Veterinario getVeterinario() {
        return veterinario;
    }

    public void setVeterinario(Veterinario veterinario) {
        this.veterinario = veterinario;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public List<Tratamento> getListaTratamentos() {
        return listaTratamentos;
    }

    public void setListaTratamentos(List<Tratamento> listaTratamentos) {
        this.listaTratamentos = listaTratamentos;
    }
}