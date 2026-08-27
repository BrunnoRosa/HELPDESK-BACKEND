package com.example.help_desk.dto.chamado;

import com.example.help_desk.model.enums.Criticidade;
import com.example.help_desk.model.enums.Ocorrencia;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ChamadoRequestDTO {

    private Long id;

    @NotBlank(message = "Título de Obrigatório ❌")
    private String tituloChamado;

    @NotNull(message = "Defina uma Opção 🔎")
    private Ocorrencia ocorrenciaChamado;

    @NotBlank(message = "Descreva a Falha 📝")
    private String descricaoChamado;

    @NotNull(message = "Escolha a Prioridade 🔎")
    private Criticidade prioridadeChamado;

    public ChamadoRequestDTO() {
    }

    public ChamadoRequestDTO(Long id, String tituloChamado, Ocorrencia ocorrenciaChamado, String descricaoChamado,
                             Criticidade prioridadeChamado) {
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
}
