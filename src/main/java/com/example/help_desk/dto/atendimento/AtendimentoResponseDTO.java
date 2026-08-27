package com.example.help_desk.dto.atendimento;

import com.example.help_desk.model.AtendimentoModel;
import com.example.help_desk.model.enums.NivelSuporte;
import com.example.help_desk.model.enums.StatusChamado;

public class AtendimentoResponseDTO {

    private Long id;
    private Long chamadoId;
    private StatusChamado status;
    private NivelSuporte nivelSuporte;
    private String usuarioVinculado;
    private String equipamentoVinculado;
    private Long solicitanteId;
    private String solicitanteNome;
    private Long tecnicoResponsavelId;
    private String tecnicoResponsavelNome;

    public AtendimentoResponseDTO() {
    }

    public AtendimentoResponseDTO(AtendimentoModel atendimento) {
        this.id = atendimento.getId();
        this.chamadoId = atendimento.getChamado().getId();
        this.status = atendimento.getStatus();
        this.nivelSuporte = atendimento.getNivelSuporte();
        this.usuarioVinculado = atendimento.getUsuarioVinculado();
        this.equipamentoVinculado = atendimento.getEquipamentoVinculado();
        this.solicitanteId = atendimento.getSolicitante().getId();
        this.solicitanteNome = atendimento.getSolicitante().getNome();
        if (atendimento.getTecnicoResponsavel() != null) {
            this.tecnicoResponsavelId = atendimento.getTecnicoResponsavel().getId();
            this.tecnicoResponsavelNome = atendimento.getTecnicoResponsavel().getNome();
        }
    }

    public Long getId() {
        return id;
    }

    public Long getChamadoId() {
        return chamadoId;
    }

    public StatusChamado getStatus() {
        return status;
    }

    public NivelSuporte getNivelSuporte() {
        return nivelSuporte;
    }

    public String getUsuarioVinculado() {
        return usuarioVinculado;
    }

    public String getEquipamentoVinculado() {
        return equipamentoVinculado;
    }

    public Long getSolicitanteId() {
        return solicitanteId;
    }

    public String getSolicitanteNome() {
        return solicitanteNome;
    }

    public Long getTecnicoResponsavelId() {
        return tecnicoResponsavelId;
    }

    public String getTecnicoResponsavelNome() {
        return tecnicoResponsavelNome;
    }
}
