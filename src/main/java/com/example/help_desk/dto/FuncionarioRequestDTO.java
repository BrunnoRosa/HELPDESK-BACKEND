package com.example.help_desk.dto;

import com.example.help_desk.model.enums.Funcionario;
import com.example.help_desk.model.enums.Perfil;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class FuncionarioRequestDTO {

    @NotBlank(message = "Nome Obrigatório ❌")
    @Size(min=8, message = "Informe Nome e Sobrenome ❌")
    private String nome;

    @NotBlank(message = "Email Obrigatório ❌")
    @Email(message = "Informe um email válido")
    private String email;

    @NotBlank(message = "Função Obrigatória ❌")
    @Size(min=5, message = "Informe sua Função ❌")
    private Funcionario funcao;

    @NotBlank(message = "Setor Obrigatório ❌")
    @Size(min=2, message = "Informe o Setor ❌")
    private Perfil perfil;

    public FuncionarioRequestDTO(String nome, String email, Funcionario funcao, Perfil perfil) {
        this.nome = nome;
        this.email = email;
        this.funcao = funcao;
        this.perfil = perfil;
    }

    public FuncionarioRequestDTO() {
    }

    public @NotBlank(message = "Nome Obrigatório ❌") @Size(min = 8, message = "Informe Nome e Sobrenome ❌") String getNome() {
        return nome;
    }

    public void setNome(@NotBlank(message = "Nome Obrigatório ❌") @Size(min = 8, message = "Informe Nome e Sobrenome ❌") String nome) {
        this.nome = nome;
    }

    public @NotBlank(message = "Email Obrigatório ❌") @Email(message = "Informe um email válido") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "Email Obrigatório ❌") @Email(message = "Informe um email válido") String email) {
        this.email = email;
    }

    public @NotBlank(message = "Função Obrigatória ❌") @Size(min = 5, message = "Informe sua Função ❌") Funcionario getFuncao() {
        return funcao;
    }

    public void setFuncao(@NotBlank(message = "Função Obrigatória ❌") @Size(min = 5, message = "Informe sua Função ❌") Funcionario funcao) {
        this.funcao = funcao;
    }

    public @NotBlank(message = "Setor Obrigatório ❌") @Size(min = 2, message = "Informe o Setor ❌") Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(@NotBlank(message = "Setor Obrigatório ❌") @Size(min = 2, message = "Informe o Setor ❌") Perfil perfil) {
        this.perfil = perfil;
    }
}
