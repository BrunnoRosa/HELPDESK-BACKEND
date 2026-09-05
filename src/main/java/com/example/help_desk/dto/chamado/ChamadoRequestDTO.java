package com.example.help_desk.dto.chamado;

import com.example.help_desk.model.enums.Criticidade;
import com.example.help_desk.model.enums.NivelSuporte;
import com.example.help_desk.model.enums.Ocorrencia;
import com.example.help_desk.model.enums.StatusChamado;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ChamadoRequestDTO {

    private Long id;

    // Campos originais para abertura do chamado
    @NotBlank(message = "Título é Obrigatório ❌")
    private String tituloChamado;

    @NotNull(message = "Defina uma Opção 🔎")
    private Ocorrencia ocorrenciaChamado;

    @NotBlank(message = "Descreva a Falha 📝")
    private String descricaoChamado;

    @NotNull(message = "Escolha a Prioridade 🔎")
    @JsonProperty("prioridade") // Mapeia o JSON do frontend para esta variável
    private Criticidade prioridadeChamado;

    // NOVOS CAMPOS PARA INTERVENÇÃO DO ADMIN (Sem @NotNull, pois são opcionais na criação)
    private StatusChamado status;
    private NivelSuporte nivelSuporte;
    private Long tecnicoId;

    public ChamadoRequestDTO() {
    }

    // Getters e Setters dos campos originais (mantenha os que você já tinha)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTituloChamado() { return tituloChamado; }
    public void setTituloChamado(String tituloChamado) { this.tituloChamado = tituloChamado; }
    public Ocorrencia getOcorrenciaChamado() { return ocorrenciaChamado; }
    public void setOcorrenciaChamado(Ocorrencia ocorrenciaChamado) { this.ocorrenciaChamado = ocorrenciaChamado; }
    public String getDescricaoChamado() { return descricaoChamado; }
    public void setDescricaoChamado(String descricaoChamado) { this.descricaoChamado = descricaoChamado; }
    public Criticidade getPrioridadeChamado() { return prioridadeChamado; }
    public void setPrioridadeChamado(Criticidade prioridadeChamado) { this.prioridadeChamado = prioridadeChamado; }

    // Getters e Setters dos novos campos
    public StatusChamado getStatus() { return status; }
    public void setStatus(StatusChamado status) { this.status = status; }
    public NivelSuporte getNivelSuporte() { return nivelSuporte; }
    public void setNivelSuporte(NivelSuporte nivelSuporte) { this.nivelSuporte = nivelSuporte; }
    public Long getTecnicoId() { return tecnicoId; }
    public void setTecnicoId(Long tecnicoId) { this.tecnicoId = tecnicoId; }
}