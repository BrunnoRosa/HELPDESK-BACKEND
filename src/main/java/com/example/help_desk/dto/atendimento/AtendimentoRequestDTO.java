package com.example.help_desk.dto.atendimento;

import com.example.help_desk.model.enums.NivelSuporte;
import com.example.help_desk.model.enums.StatusChamado;
import jakarta.validation.constraints.NotNull;

public class AtendimentoRequestDTO {

    @NotNull(message = "Informe o chamado ❌")
    private Long chamadoId;

    @NotNull(message = "Informe o status ❌")
    private StatusChamado status;

    @NotNull(message = "Informe o nível de suporte ❌")
    private NivelSuporte nivelSuporte;

    private String usuarioVinculado;
    private String equipamentoVinculado;
    private Long tecnicoResponsavelId;

    public AtendimentoRequestDTO() {
    }

    public Long getChamadoId() {
        return chamadoId;
    }

    public void setChamadoId(Long chamadoId) {
        this.chamadoId = chamadoId;
    }

    public StatusChamado getStatus() {
        return status;
    }

    public void setStatus(StatusChamado status) {
        this.status = status;
    }

    public NivelSuporte getNivelSuporte() {
        return nivelSuporte;
    }

    public void setNivelSuporte(NivelSuporte nivelSuporte) {
        this.nivelSuporte = nivelSuporte;
    }

    public String getUsuarioVinculado() {
        return usuarioVinculado;
    }

    public void setUsuarioVinculado(String usuarioVinculado) {
        this.usuarioVinculado = usuarioVinculado;
    }

    public String getEquipamentoVinculado() {
        return equipamentoVinculado;
    }

    public void setEquipamentoVinculado(String equipamentoVinculado) {
        this.equipamentoVinculado = equipamentoVinculado;
    }

    public Long getTecnicoResponsavelId() {
        return tecnicoResponsavelId;
    }

    public void setTecnicoResponsavelId(Long tecnicoResponsavelId) {
        this.tecnicoResponsavelId = tecnicoResponsavelId;
    }
}
