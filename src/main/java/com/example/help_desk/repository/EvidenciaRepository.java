package com.example.help_desk.repository;

import com.example.help_desk.model.EvidenciaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvidenciaRepository extends JpaRepository<EvidenciaModel, Long> {

    List<EvidenciaModel> findByChamadoIdOrderByDataEnvioAsc(Long chamadoId);
}
