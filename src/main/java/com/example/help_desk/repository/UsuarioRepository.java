package com.example.help_desk.repository;

import com.example.help_desk.model.UsuarioModel;
import com.example.help_desk.model.enums.PerfilUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long> {

    Optional<UsuarioModel> findByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCase(String email);

    List<UsuarioModel> findAllByPerfil(PerfilUsuario perfil);
}
