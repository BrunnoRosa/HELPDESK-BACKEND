package com.example.help_desk.dto.funcionario;

import com.example.help_desk.model.enums.Funcionario;
import com.example.help_desk.model.enums.Perfil;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class FuncionarioRequestDTO {

    @NotBlank(message = "Nome Obrigatório ❌")
    @Size(min=8, message = "Informe Nome e Sobrenome ❌")
    private String nome;

    @NotBlank(message = "Email Obrigatório ❌")
    @Email(message = "Informe um email válido")
    private String email;

    @NotNull(message = "Função Obrigatória ❌")
    //@Enumerated(EnumType.STRING)
    private Funcionario funcao;

    @NotNull(message = "Setor Obrigatório ❌")
//    @Enumerated(EnumType.STRING)
    private Perfil perfil;

    public FuncionarioRequestDTO(String nome, String email, Funcionario funcao, Perfil perfil) {
        this.nome = nome;
        this.email = email;
        this.funcao = funcao;
        this.perfil = perfil;
    }

    public FuncionarioRequestDTO() {
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
