package com.exemplo.obra.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Orcamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeUsuario;
    private Double comprimentoParede;
    private Double alturaParede;
    private Integer quantidadeTijolos;

    public Orcamento() {
    }

    public Orcamento(String nomeUsuario, Double comprimentoParede, Double alturaParede, Integer quantidadeTijolos) {
        this.nomeUsuario = nomeUsuario;
        this.comprimentoParede = comprimentoParede;
        this.alturaParede = alturaParede;
        this.quantidadeTijolos = quantidadeTijolos;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNomeUsuario() { return nomeUsuario; }
    public void setNomeUsuario(String nomeUsuario) { this.nomeUsuario = nomeUsuario; }

    public Double getComprimentoParede() { return comprimentoParede; }
    public void setComprimentoParede(Double comprimentoParede) { this.comprimentoParede = comprimentoParede; }

    public Double getAlturaParede() { return alturaParede; }
    public void setAlturaParede(Double alturaParede) { this.alturaParede = alturaParede; }

    public Integer getQuantidadeTijolos() { return quantidadeTijolos; }
    public void setQuantidadeTijolos(Integer quantidadeTijolos) { this.quantidadeTijolos = quantidadeTijolos; }
}