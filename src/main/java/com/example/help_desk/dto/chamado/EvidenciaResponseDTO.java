package com.example.help_desk.dto.chamado;

import com.example.help_desk.model.EvidenciaModel;

import java.time.LocalDateTime;

public class EvidenciaResponseDTO {

    private Long id;
    private String imagem;
    private String nomeArquivo;
    private String enviadoPor;
    private LocalDateTime dataEnvio;

    public EvidenciaResponseDTO() {
    }

    public EvidenciaResponseDTO(EvidenciaModel evidencia) {
        this.id = evidencia.getId();
        this.imagem = evidencia.getImagem();
        this.nomeArquivo = evidencia.getNomeArquivo();
        this.enviadoPor = evidencia.getEnviadoPor();
        this.dataEnvio = evidencia.getDataEnvio();
    }

    // Construtor usado para representar, como um item "legado", a foto que
    // ficou salva no antigo campo único ChamadoModel.imagemChamado, antes de
    // existir a lista de evidências.
    public EvidenciaResponseDTO(Long id, String imagem, String nomeArquivo, String enviadoPor, LocalDateTime dataEnvio) {
        this.id = id;
        this.imagem = imagem;
        this.nomeArquivo = nomeArquivo;
        this.enviadoPor = enviadoPor;
        this.dataEnvio = dataEnvio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getEnviadoPor() {
        return enviadoPor;
    }

    public void setEnviadoPor(String enviadoPor) {
        this.enviadoPor = enviadoPor;
    }

    public LocalDateTime getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(LocalDateTime dataEnvio) {
        this.dataEnvio = dataEnvio;
    }
}
