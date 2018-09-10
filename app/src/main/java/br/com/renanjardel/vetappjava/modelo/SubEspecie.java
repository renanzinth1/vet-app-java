package br.com.renanjardel.vetappjava.modelo;

class SubEspecie {

    private Long codigo;

    private String nome;

    private Especie especie;

    public SubEspecie() {
        super();
    }

    public SubEspecie(Long codigo, String nome, Especie especie) {
        super();
        this.codigo = codigo;
        this.nome = nome;
        this.especie = especie;
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

    public Especie getEspecie() {
        return especie;
    }

    public void setEspecie(Especie especie) {
        this.especie = especie;
    }

}
