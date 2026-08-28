package com.example.help_desk.dto.usuario;

import com.example.help_desk.model.UsuarioModel;
import com.example.help_desk.model.enums.PerfilUsuario;

public class UsuarioResponseDTO {

    private Long id;
    private String nome;
    private String email;
    private PerfilUsuario perfil;

    public UsuarioResponseDTO() {
    }

    public UsuarioResponseDTO(UsuarioModel usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.perfil = usuario.getPerfil();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public PerfilUsuario getPerfil() {
        return perfil;
    }
}
