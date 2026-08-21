package com.example.help_desk.model.enums;

public enum Perfil {
    USUARIO ("Usuário"),
    TECNICO ("Técnico");

    private String perfil;

    Perfil(String perfil) {
        this.perfil = perfil;
    }

    public String getPerfil() {
        return perfil;
    }
}
