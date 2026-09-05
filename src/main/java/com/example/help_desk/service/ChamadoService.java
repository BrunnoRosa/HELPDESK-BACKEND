package com.example.help_desk.service;

import com.example.help_desk.dto.chamado.ChamadoRequestDTO;
import com.example.help_desk.dto.chamado.ChamadoResponseDTO;
import com.example.help_desk.model.ChamadoModel;
import com.example.help_desk.model.AtendimentoModel;
import com.example.help_desk.model.UsuarioModel;
import com.example.help_desk.model.enums.PerfilUsuario;
import com.example.help_desk.repository.AtendimentoRepository;
import com.example.help_desk.repository.ChamadoRepository;
import com.example.help_desk.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ChamadoService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private ChamadoRepository chamadoRepository;

    @Autowired
    private AtendimentoRepository atendimentoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository; // <-- NOVA INJEÇÃO

    @Autowired
    private AcessoService acessoService;

    // ... (Mantenha os métodos listar, buscarPorId, salvar e deletar exatamente iguais) ...

    @Transactional
    public ChamadoModel atualizar(Long id, ChamadoRequestDTO atualizarDTO) {
        ChamadoModel chamado = chamadoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Chamado não localizado ❌"));

        // 1. Atualiza dados básicos do chamado se enviados (comum a Técnicos e Clientes)
        if (atualizarDTO.getTituloChamado() != null) {
            chamado.setTituloChamado(atualizarDTO.getTituloChamado());
        }
        if (atualizarDTO.getOcorrenciaChamado() != null) {
            chamado.setOcorrenciaChamado(atualizarDTO.getOcorrenciaChamado());
        }
        if (atualizarDTO.getPrioridadeChamado() != null) {
            chamado.setPrioridadeChamado(atualizarDTO.getPrioridadeChamado());
        }
        
        if (atualizarDTO.getDescricaoChamado() != null && !atualizarDTO.getDescricaoChamado().isBlank()) {
            String dataHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            String descricaoAtual = chamado.getDescricaoChamado() != null ? chamado.getDescricaoChamado() : "";
            chamado.setDescricaoChamado(descricaoAtual + "\n[" + dataHora + "] " + atualizarDTO.getDescricaoChamado());
        }

        // 2. Intervenção Administrativa no Atendimento
        // Busca o atendimento ativo vinculado ao chamado
        List<AtendimentoModel> atendimentos = atendimentoRepository.findAllByChamadoId(id);
        if (!atendimentos.isEmpty()) {
            AtendimentoModel atendimento = atendimentos.get(0); // Assume o atendimento mais recente/ativo

            if (atualizarDTO.getStatus() != null) {
                atendimento.setStatus(atualizarDTO.getStatus());
            }
            if (atualizarDTO.getNivelSuporte() != null) {
                atendimento.setNivelSuporte(atualizarDTO.getNivelSuporte());
            }
            
            // Tratamento da reatribuição de técnico
            if (atualizarDTO.getTecnicoId() != null) {
                UsuarioModel novoTecnico = usuarioRepository.findById(atualizarDTO.getTecnicoId())
                        .orElseThrow(() -> new IllegalArgumentException("Técnico não encontrado ❌"));
                atendimento.setTecnicoResponsavel(novoTecnico);
            } else if (atualizarDTO.getTecnicoId() != null && atualizarDTO.getTecnicoId() == 0) {
                // Se o front enviar ID 0 (ou null configurado para desatribuir), removemos o técnico
                atendimento.setTecnicoResponsavel(null);
            }

            atendimentoRepository.save(atendimento);
        }

        return chamadoRepository.save(chamado);
    }

    // ... (Mantenha o método deletar) ...
}