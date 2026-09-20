package com.example.help_desk.dto.chamado;

import jakarta.validation.constraints.NotBlank;

public class EvidenciaRequestDTO {

    @NotBlank(message = "Selecione um arquivo para anexar")
    private String imagem;

    private String nomeArquivo;

    public EvidenciaRequestDTO() {
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }
}
