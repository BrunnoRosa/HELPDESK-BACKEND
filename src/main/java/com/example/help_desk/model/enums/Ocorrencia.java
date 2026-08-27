package com.example.help_desk.model.enums;

public enum Ocorrencia {
    INCIDENTE("Incidente"),
    REQUISICAO("Requisição"),
    PROBLEMA("Problema"),
    DUVIDA("Dúvida");

    private final String descricao;

    Ocorrencia(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
