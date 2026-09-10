package com.example.help_desk.controller;


import com.example.help_desk.dto.senha.ChangePasswordDTO;
import com.example.help_desk.model.UsuarioModel;
import com.example.help_desk.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PutMapping("/alterar-senha")
    public ResponseEntity<Void> alterarSenha(
            @Valid @RequestBody ChangePasswordDTO dto,
            @AuthenticationPrincipal UsuarioModel usuarioLogado
    ) {
        usuarioService.alterarSenha(usuarioLogado.getId(), dto);
        return ResponseEntity.noContent().build();
    }
}
