package com.example.help_desk.service;

import com.example.help_desk.dto.chamado.ChamadoRequestDTO;
import com.example.help_desk.dto.chamado.ChamadoResponseDTO;
import com.example.help_desk.model.ChamadoModel;
import com.example.help_desk.model.UsuarioModel;
import com.example.help_desk.model.enums.PerfilUsuario;
import com.example.help_desk.repository.AtendimentoRepository;
import com.example.help_desk.repository.ChamadoRepository;
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
    private AcessoService acessoService;

    @Transactional(readOnly = true)
    public List<ChamadoResponseDTO> listar(UsuarioModel usuario) {
        if (usuario.getPerfil() == PerfilUsuario.USUARIO) {
            return atendimentoRepository.findAllBySolicitanteId(usuario.getId())
                    .stream()
                    .map(atendimento -> new ChamadoResponseDTO(atendimento.getChamado()))
                    .toList();
        }

        return chamadoRepository.findAll().stream().map(ChamadoResponseDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public ChamadoResponseDTO buscarPorId(Long id, UsuarioModel usuario) {

        acessoService.validarAcessoChamado(usuario, id);

        ChamadoModel chamado = chamadoRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Chamado não localizado ❌"
                        )
                );

        return new ChamadoResponseDTO(chamado);
    }

    @Transactional
    public ChamadoModel salvar(ChamadoRequestDTO salvarDTO) {

        System.out.println("Imagem recebida no DTO: " + salvarDTO.getImagemChamado());

        ChamadoModel novoChamado = new ChamadoModel();
        novoChamado.setTituloChamado(salvarDTO.getTituloChamado());
        novoChamado.setOcorrenciaChamado(salvarDTO.getOcorrenciaChamado());
        novoChamado.setDescricaoChamado(salvarDTO.getDescricaoChamado());
        novoChamado.setPrioridadeChamado(salvarDTO.getPrioridadeChamado());
        novoChamado.setImagemChamado(salvarDTO.getImagemChamado());
        return chamadoRepository.save(novoChamado);

    }

    @Transactional
    public ChamadoModel atualizar(Long id, ChamadoRequestDTO atualizarDTO) {
        ChamadoModel novoRegistro = chamadoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Chamado não localizado ❌"));

        String dataHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        String descricaoAtual = novoRegistro.getDescricaoChamado() != null ? novoRegistro.getDescricaoChamado() : "";
        String novaAtualizacao = "[" + dataHora + "] " + atualizarDTO.getDescricaoChamado();

        novoRegistro.setTituloChamado(atualizarDTO.getTituloChamado());
        novoRegistro.setOcorrenciaChamado(atualizarDTO.getOcorrenciaChamado());
        novoRegistro.setDescricaoChamado(descricaoAtual + "\n" + novaAtualizacao);
        novoRegistro.setPrioridadeChamado(atualizarDTO.getPrioridadeChamado());

        // PROTEÇÃO: Só atualiza a imagem se uma nova imagem não nula/vazia for enviada no DTO.
        // Se vier null ou vazia no PUT, mantém a imagem que já está no banco de dados.
        if (atualizarDTO.getImagemChamado() != null && !atualizarDTO.getImagemChamado().isBlank()) {
            novoRegistro.setImagemChamado(atualizarDTO.getImagemChamado());
        }

        return chamadoRepository.save(novoRegistro);
    }

    @Transactional
    public void deletar(Long id) {
        if (!chamadoRepository.existsById(id)) {
            throw new RuntimeException("Chamado não localizado ❌");
        }
        atendimentoRepository.deleteByChamadoId(id);
        chamadoRepository.deleteById(id);
    }
}
