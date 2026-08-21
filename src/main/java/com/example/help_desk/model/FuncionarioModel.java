package com.example.help_desk.model;

import com.example.help_desk.model.enums.Funcionario;
import com.example.help_desk.model.enums.Perfil;
import jakarta.persistence.*;

@Entity
@Table(name = "tab_funcionarios")
public class FuncionarioModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private Funcionario funcao;

    @Column(nullable = false)
    private Perfil perfil;

    public FuncionarioModel(Long id, String nome, String email, Funcionario funcao, Perfil perfil) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.funcao = funcao;
        this.perfil = perfil;
    }

    public FuncionarioModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

