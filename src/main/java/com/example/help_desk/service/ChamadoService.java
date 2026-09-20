package com.example.help_desk.service;

import com.example.help_desk.dto.chamado.ChamadoRequestDTO;
import com.example.help_desk.dto.chamado.ChamadoResponseDTO;
import com.example.help_desk.dto.chamado.EvidenciaRequestDTO;
import com.example.help_desk.dto.chamado.EvidenciaResponseDTO;
import com.example.help_desk.model.ChamadoModel;
import com.example.help_desk.model.EvidenciaModel;
import com.example.help_desk.model.UsuarioModel;
import com.example.help_desk.model.enums.PerfilUsuario;
import com.example.help_desk.repository.AtendimentoRepository;
import com.example.help_desk.repository.ChamadoRepository;
import com.example.help_desk.repository.EvidenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

    @Autowired
    private EvidenciaRepository evidenciaRepository;

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

        ChamadoResponseDTO dto = new ChamadoResponseDTO(chamado);
        dto.setEvidencias(listarEvidencias(chamado));
        return dto;
    }

    // Monta a lista completa de anexos do chamado: primeiro a foto de
    // abertura (que ainda vive no campo legado imagemChamado, de antes de
    // existir a tabela de evidências), depois cada evidência enviada depois
    // - assim nenhuma foto some quando outra é anexada.
    private List<EvidenciaResponseDTO> listarEvidencias(ChamadoModel chamado) {
        List<EvidenciaResponseDTO> lista = new ArrayList<>();

        if (chamado.getImagemChamado() != null && !chamado.getImagemChamado().isBlank()) {
            lista.add(new EvidenciaResponseDTO(
                    null,
                    chamado.getImagemChamado(),
                    "Foto de abertura do chamado",
                    null,
                    chamado.getDataAberturaChamado()
            ));
        }

        evidenciaRepository.findByChamadoIdOrderByDataEnvioAsc(chamado.getId())
                .forEach(evidencia -> lista.add(new EvidenciaResponseDTO(evidencia)));

        return lista;
    }

    @Transactional
    public EvidenciaResponseDTO adicionarEvidencia(Long chamadoId, UsuarioModel usuario, EvidenciaRequestDTO evidenciaDTO) {
        acessoService.validarAcessoChamado(usuario, chamadoId);

        ChamadoModel chamado = chamadoRepository.findById(chamadoId)
                .orElseThrow(() -> new IllegalArgumentException("Chamado não localizado ❌"));

        EvidenciaModel evidencia = new EvidenciaModel();
        evidencia.setChamado(chamado);
        evidencia.setImagem(evidenciaDTO.getImagem());
        evidencia.setNomeArquivo(evidenciaDTO.getNomeArquivo());
        evidencia.setEnviadoPor(usuario.getNome());

        return new EvidenciaResponseDTO(evidenciaRepository.save(evidencia));
    }

    @Transactional
    public ChamadoModel salvar(ChamadoRequestDTO salvarDTO) {
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
        novoRegistro.setImagemChamado(atualizarDTO.getImagemChamado());

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
