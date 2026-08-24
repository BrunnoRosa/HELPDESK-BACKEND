package com.example.help_desk.model.enums;

public enum Ocorrencia {
    SISTEMAINCENDIO ("Extintores / Hidrante"),
    INFORMATICA ("Monitor / Computador / Mouse / Teclado"),
    IMPRESSORA ("Jato de Tinta / Fiscal / Laser"),
    MOBILIA ("Mesa / Cadeira / Baquenta / Armario"),
    ELETRICA ("Iluminação / Tomadas / Interruptores"),
    CLIMATIZACAO ("Ar-Condicionado / Exaustor / Ventilador");

    private String descricao;
    private Ocorrencia(String descricao) {
        this.descricao = descricao;
    }
    public String getDescricao() {
        return descricao;
    }
}
