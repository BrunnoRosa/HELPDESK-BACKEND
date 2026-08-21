package com.example.help_desk.model.enums;

public enum Setor {

    RH ("Relações Humanas"),
    FINANCEIRO ("Financeiro"),
    ADMINISTRATIVO ("Administrativo");

    private String nome;

    Setor(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
