package com.example.help_desk.controller;

import com.example.help_desk.dto.usuario.PerfilUpdateDTO;
import com.example.help_desk.dto.usuario.UsuarioRequestDTO;
import com.example.help_desk.dto.usuario.UsuarioResponseDTO;
import com.example.help_desk.model.UsuarioModel;
import com.example.help_desk.model.enums.StatusChamado;
import com.example.help_desk.repository.AtendimentoRepository;
import com.example.help_desk.repository.ChamadoRepository;
import com.example.help_desk.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMINISTRADOR')") // TRAVA: Apenas usuários com Authority ADMINISTRADOR acessam este controller
public class AdminController {

    private final UsuarioService usuarioService;
    private final ChamadoRepository chamadoRepository;
    private final AtendimentoRepository atendimentoRepository;

    public AdminController(
            UsuarioService usuarioService,
            ChamadoRepository chamadoRepository,
            AtendimentoRepository atendimentoRepository
    ) {
        this.usuarioService = usuarioService;
        this.chamadoRepository = chamadoRepository;
        this.atendimentoRepository = atendimentoRepository;
    }

    // NOVO ENDPOINT: Recebe os dados do front-end e cria o usuário
    @PostMapping("/usuarios")
    public ResponseEntity<UsuarioResponseDTO> criarUsuario(@Valid @RequestBody UsuarioRequestDTO dto) {
        UsuarioResponseDTO novoUsuario = usuarioService.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
    }

    @GetMapping("/usuarios")
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listar());
    }

    @GetMapping("/tecnicos")
    public ResponseEntity<List<UsuarioResponseDTO>> listarTecnicos() {
        return ResponseEntity.ok(usuarioService.listarTecnicos());
    }

    @PutMapping("/usuarios/{id}/perfil")
    public ResponseEntity<UsuarioResponseDTO> atualizarPerfil(
            @PathVariable Long id,
            @Valid @RequestBody PerfilUpdateDTO dto
    ) {
        return ResponseEntity.ok(usuarioService.atualizarPerfil(id, dto));
    }

    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<Map<String, Object>> deletarUsuario(
            @PathVariable Long id,
            @AuthenticationPrincipal UsuarioModel usuarioAtual
    ) {
        usuarioService.deletar(id, usuarioAtual.getId());
        return ResponseEntity.ok(Map.of("Mensagem", "Usuário removido com sucesso"));
    }

    @GetMapping("/relatorios/resumo")
    public ResponseEntity<Map<String, Object>> resumo() {
        Map<String, Long> porStatus = new LinkedHashMap<>();
        for (StatusChamado status : StatusChamado.values()) {
            porStatus.put(status.name(), atendimentoRepository.countByStatus(status));
        }

        return ResponseEntity.ok(Map.of(
                "totalChamados", chamadoRepository.count(),
                "totalUsuarios", usuarioService.listar().size(),
                "porStatus", porStatus
        ));
    }
}