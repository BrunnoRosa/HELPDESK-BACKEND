package com.example.help_desk.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

// Cada linha aqui é UM arquivo anexado ao chamado (a foto de abertura vira a
// primeira, e cada "nova evidência" pedida pelo técnico vira outra) - assim
// nenhum anexo novo apaga o anterior, como acontecia quando tudo dependia de
// um único campo imagemChamado.
@Entity
@Table(name = "tab_evidencias")
public class EvidenciaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "chamado_id", nullable = false)
    private ChamadoModel chamado;

    @Column(name = "imagem", columnDefinition = "LONGTEXT", nullable = false)
    private String imagem;

    @Column(name = "nome_arquivo")
    private String nomeArquivo;

    @Column(name = "enviado_por")
    private String enviadoPor;

    @CreationTimestamp
    @Column(name = "data_envio", updatable = false)
    private LocalDateTime dataEnvio;

    public EvidenciaModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ChamadoModel getChamado() {
        return chamado;
    }

    public void setChamado(ChamadoModel chamado) {
        this.chamado = chamado;
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
