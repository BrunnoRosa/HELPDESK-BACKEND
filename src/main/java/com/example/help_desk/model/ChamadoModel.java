package com.example.help_desk.model;

import com.example.help_desk.model.enums.Criticidade;
import com.example.help_desk.model.enums.NivelSuporte;
import com.example.help_desk.model.enums.Ocorrencia;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "tab_chamados")
public class ChamadoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @Column(name = "data_abertura_chamado", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime dataAberturaChamado;

    public LocalDateTime getDataAberturaChamado() {
        return dataAberturaChamado;
    }


    @Column(nullable = false)
    private String tituloChamado;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Ocorrencia ocorrenciaChamado;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descricaoChamado;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Criticidade prioridadeChamado;

    // Relacionamento com o técnico que assumiu o chamado
    @ManyToOne
    @JoinColumn(name = "tecnico_responsavel_id")
    private UsuarioModel tecnicoResponsavel;

    // Status do chamado (ex: ABERTO, EM_ANDAMENTO, CONCLUIDO)
    @Column(name = "status_chamado")
    private String statusChamado = "ABERTO";

    // Fila de nível de suporte (ex: N1, N2, N3)
    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_suporte")
//    private String nivelSuporte = "N1";
    private NivelSuporte nivelSuporte;

    public ChamadoModel() {
    }

    public ChamadoModel(Long id, String tituloChamado, Ocorrencia ocorrenciaChamado, String descricaoChamado, Criticidade prioridadeChamado) {
        this.id = id;
        this.tituloChamado = tituloChamado;
        this.ocorrenciaChamado = ocorrenciaChamado;
        this.descricaoChamado = descricaoChamado;
        this.prioridadeChamado = prioridadeChamado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTituloChamado() {
        return tituloChamado;
    }

    public void setTituloChamado(String tituloChamado) {
        this.tituloChamado = tituloChamado;
    }

    public Ocorrencia getOcorrenciaChamado() {
        return ocorrenciaChamado;
    }

    public void setOcorrenciaChamado(Ocorrencia ocorrenciaChamado) {
        this.ocorrenciaChamado = ocorrenciaChamado;
    }

    public String getDescricaoChamado() {
        return descricaoChamado;
    }

    public void setDescricaoChamado(String descricaoChamado) {
        this.descricaoChamado = descricaoChamado;
    }

    public Criticidade getPrioridadeChamado() {
        return prioridadeChamado;
    }

    public void setPrioridadeChamado(Criticidade prioridadeChamado) {
        this.prioridadeChamado = prioridadeChamado;
    }

    public UsuarioModel getTecnicoResponsavel() {
        return tecnicoResponsavel;
    }

    public void setTecnicoResponsavel(UsuarioModel tecnicoResponsavel) {
        this.tecnicoResponsavel = tecnicoResponsavel;
    }

    public String getStatusChamado() {
        return statusChamado;
    }

    public void setStatusChamado(String statusChamado) {
        this.statusChamado = statusChamado;
    }

    public NivelSuporte getNivelSuporte() {
        return nivelSuporte;
    }

    public void setNivelSuporte(NivelSuporte nivelSuporte) {
        this.nivelSuporte = nivelSuporte;
    }
}