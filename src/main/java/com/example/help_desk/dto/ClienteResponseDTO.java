package com.example.help_desk.dto;

import com.example.help_desk.model.enums.Setor;

public class ClienteResponseDTO {

    private String nome;
    private String email;
    private Setor setor;

    public ClienteResponseDTO() {
    }

    public ClienteResponseDTO(String nome, String email, Setor setor) {
        this.nome = nome;
        this.email = email;
        this.setor = setor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Setor getSetor() {
        return setor;
    }

    public void setSetor(Setor setor) {
        this.setor = setor;
    }
}
