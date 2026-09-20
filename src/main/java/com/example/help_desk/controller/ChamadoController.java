package com.example.help_desk.controller;

import com.example.help_desk.dto.chamado.ChamadoRequestDTO;
import com.example.help_desk.dto.chamado.ChamadoResponseDTO;
import com.example.help_desk.dto.chamado.EvidenciaRequestDTO;
import com.example.help_desk.dto.chamado.EvidenciaResponseDTO;
import com.example.help_desk.model.ChamadoModel;
import com.example.help_desk.model.UsuarioModel;
import com.example.help_desk.service.AcessoService;
import com.example.help_desk.service.AtendimentoService;
import com.example.help_desk.service.ChamadoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/chamados")
public class ChamadoController {

    @Autowired
    private ChamadoService service;

    @Autowired
    private AtendimentoService atendimentoService;

    @Autowired
    private AcessoService acessoService;

    @GetMapping
    public ResponseEntity<List<ChamadoResponseDTO>> listar(@AuthenticationPrincipal UsuarioModel usuario) {
        UsuarioModel model = (UsuarioModel) usuario;
        return ResponseEntity.status(HttpStatus.OK).body(service.listar(usuario));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChamadoResponseDTO> buscarPorId(
            @PathVariable Long id,
            @AuthenticationPrincipal UsuarioModel usuario
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(service.buscarPorId(id, usuario));
    }

    // @Transactional aqui é essencial: salvar o chamado e criar o atendimento
    // inicial são duas chamadas a serviços com transações próprias. Sem essa
    // anotação envolvendo as duas, se criarInicial() falhar depois que
    // service.salvar() já commitou, o chamado fica gravado no banco mesmo
    // com o cliente recebendo um erro - o usuário então tenta de novo achando
    // que falhou, e cada tentativa gera um chamado duplicado. Com a transação
    // única, ou as duas operações são gravadas juntas, ou nenhuma é.
    @PostMapping
    @Transactional
    public ResponseEntity<Map<String, Object>> salvar(
            @Valid @RequestBody ChamadoRequestDTO salvarDTO,
            @AuthenticationPrincipal UsuarioModel usuario
    ) {
        ChamadoModel chamado = service.salvar(salvarDTO);
        atendimentoService.criarInicial(chamado.getId(), usuario);

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "Mensagem", "Chamado salvo com sucesso",
                "id", chamado.getId()
        ));
    }

    // Libera também para USUARIO porque o solicitante precisa poder responder
    // no chat de diagnóstico e anexar evidências no próprio chamado.
    // A validação de que só o dono do chamado pode mexer nele (para quem não é
    // TECNICO/ADMINISTRADOR) é feita por acessoService.validarAcessoChamado.
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('TECNICO','ADMINISTRADOR','USUARIO')")
    public ResponseEntity<Map<String, Object>> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid ChamadoRequestDTO atualizarDTO,
            @AuthenticationPrincipal UsuarioModel usuario
    ) {
        acessoService.validarAcessoChamado(usuario, id);
        service.atualizar(id, atualizarDTO);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("Mensagem", "Chamado atualizado com sucesso"));
    }

    // Endpoint dedicado para anexar uma NOVA evidência sem apagar as fotos já
    // enviadas (a foto de abertura e cada evidência anterior continuam
    // existindo, ao contrário do PUT geral que substitui o chamado inteiro).
    @PostMapping("/{id}/evidencias")
    @PreAuthorize("hasAnyRole('TECNICO','ADMINISTRADOR','USUARIO')")
    public ResponseEntity<EvidenciaResponseDTO> adicionarEvidencia(
            @PathVariable Long id,
            @RequestBody @Valid EvidenciaRequestDTO evidenciaDTO,
            @AuthenticationPrincipal UsuarioModel usuario
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.adicionarEvidencia(id, usuario, evidenciaDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Map<String, Object>> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("Mensagem", "Chamado deletado"));
    }
}
