package com.example.help_desk.repository;

import com.example.help_desk.model.ChamadoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChamadoRepository extends JpaRepository<ChamadoModel, Long> {
}
