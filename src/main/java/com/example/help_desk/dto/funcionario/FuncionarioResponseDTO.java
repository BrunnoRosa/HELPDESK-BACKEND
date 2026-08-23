package com.example.help_desk.dto.funcionario;

import com.example.help_desk.model.FuncionarioModel;
import com.example.help_desk.model.enums.Funcionario;
import com.example.help_desk.model.enums.Perfil;

public class FuncionarioResponseDTO {
    private String nome;
    private String email;
    private Funcionario funcao;
    private Perfil perfil;

    public FuncionarioResponseDTO() {
    }

    public FuncionarioResponseDTO(String nome, String email, Funcionario funcao, Perfil perfil) {
        this.nome = nome;
        this.email = email;
        this.funcao = funcao;
        this.perfil = perfil;
    }
    // Construtor definido atrelado direto do FuncionarioModel -> funcionario e chamado lá nas configurações do FuncionarioResponseDTO para trazer mais simplicidade ao Service
    public FuncionarioResponseDTO(FuncionarioModel funcionario) {
        this.nome = funcionario.getNome();
        this.email = funcionario.getEmail();
        this.funcao = funcionario.getFuncao();
        this.perfil = funcionario.getPerfil();
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

    public Funcionario getFuncao() {
        return funcao;
    }

    public void setFuncao(Funcionario funcao) {
        this.funcao = funcao;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }
}