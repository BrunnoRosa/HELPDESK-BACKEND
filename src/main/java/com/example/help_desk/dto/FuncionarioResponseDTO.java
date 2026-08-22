package com.example.help_desk.dto;

import com.example.help_desk.model.enums.Funcionario;
import com.example.help_desk.model.enums.Perfil;

public class FuncionarioResponseDTO {
    private String nome;
    private String email;
    private Funcionario funcao;
    private Perfil perfil;

    public FuncionarioResponseDTO(String nome, String email, Funcionario funcao, Perfil perfil) {
        this.nome = nome;
        this.email = email;
        this.funcao = funcao;
        this.perfil = perfil;
    }

    public FuncionarioResponseDTO() {
    }

    public static String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public static String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static Funcionario getFuncao() {
        return funcao;
    }

    public void setFuncao(Funcionario funcao) {
        this.funcao = funcao;
    }

    public static Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }
}
