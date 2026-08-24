package com.example.help_desk.repository;

import com.example.help_desk.model.FuncionarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FuncionarioRepository extends JpaRepository<FuncionarioModel, Long> {
    Optional<FuncionarioModel> findByEmail (String email);

}
