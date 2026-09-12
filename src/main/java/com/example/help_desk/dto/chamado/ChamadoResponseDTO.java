package com.example.help_desk.dto.chamado;

import com.example.help_desk.model.ChamadoModel;
import com.example.help_desk.model.enums.Criticidade;
import com.example.help_desk.model.enums.NivelSuporte;
import com.example.help_desk.model.enums.Ocorrencia;

public class ChamadoResponseDTO {
    private Long id;
    private String tituloChamado;
    private Ocorrencia ocorrenciaChamado;
    private String descricaoChamado;
    private Criticidade prioridadeChamado;

    // Novos campos adicionados
    private String statusChamado;
    private NivelSuporte nivelSuporte;
    private TecnicoDTO tecnicoResponsavel;

    public ChamadoResponseDTO() {
    }

    public ChamadoResponseDTO(ChamadoModel chamado) {
        this.id = chamado.getId();
        this.tituloChamado = chamado.getTituloChamado();
        this.ocorrenciaChamado = chamado.getOcorrenciaChamado();
        this.descricaoChamado = chamado.getDescricaoChamado();
        this.prioridadeChamado = chamado.getPrioridadeChamado();

        // Mapeamento dos novos campos
        this.statusChamado = chamado.getStatusChamado();
        this.nivelSuporte = chamado.getNivelSuporte();

        if (chamado.getTecnicoResponsavel() != null) {
            this.tecnicoResponsavel = new TecnicoDTO(
                    chamado.getTecnicoResponsavel().getId(),
                    chamado.getTecnicoResponsavel().getNome()
            );
        }
    }

    // Getters e Setters
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

    public String getStatusChamado() { return statusChamado; }
    public void setStatusChamado(String statusChamado) { this.statusChamado = statusChamado; }

    public NivelSuporte getNivelSuporte() { return nivelSuporte; }
    public void setNivelSuporte(NivelSuporte nivelSuporte) { this.nivelSuporte = nivelSuporte; }

    public TecnicoDTO getTecnicoResponsavel() { return tecnicoResponsavel; }
    public void setTecnicoResponsavel(TecnicoDTO tecnicoResponsavel) { this.tecnicoResponsavel = tecnicoResponsavel; }

    // DTO interno para formatar a resposta do técnico sem expor a senha ou dados sensíveis
    public static class TecnicoDTO {
        private Long id;
        private String nome;

        public TecnicoDTO(Long id, String nome) {
            this.id = id;
            this.nome = nome;
        }

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getNome() { return nome; }
        public void setNome(String nome) { this.nome = nome; }
    }
}