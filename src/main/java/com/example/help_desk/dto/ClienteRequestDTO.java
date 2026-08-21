package com.example.help_desk.dto;


import com.example.help_desk.model.enums.Setor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ClienteRequestDTO {

    @NotBlank(message = "Nome é obrigatório ❌")
    private String nome;

    @NotBlank(message = "Telefone é obrigatório ❌")
    @Email(message = "Informe um e-mail válido ❌")
    private String email;

    @NotNull(message = "Setor é obrigatório ❌")
    private Setor setor;

    public ClienteRequestDTO() {
    }

    public ClienteRequestDTO(String nome, String email, Setor setor) {
        this.nome = nome;
        this.email = email;
        this.setor = setor;
    }

    public @NotBlank(message = "Nome é obrigatório ❌") String getNome() {
        return nome;
    }

    public void setNome(@NotBlank(message = "Nome é obrigatório ❌") String nome) {
        this.nome = nome;
    }

    public @NotBlank(message = "Telefone é obrigatório ❌") @Email(message = "Informe um e-mail válido ❌") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "Telefone é obrigatório ❌") @Email(message = "Informe um e-mail válido ❌") String email) {
        this.email = email;
    }

    public @NotNull(message = "Setor é obrigatório ❌") Setor getSetor() {
        return setor;
    }

    public void setSetor(@NotNull(message = "Setor é obrigatório ❌") Setor setor) {
        this.setor = setor;
    }
}
