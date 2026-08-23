package com.example.help_desk.controller;

import com.example.help_desk.dto.FuncionarioRequestDTO;
import com.example.help_desk.dto.FuncionarioResponseDTO;
import com.example.help_desk.repository.FuncionarioRepository;
import com.example.help_desk.service.FuncionarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("funcionarios")

public class FuncionarioController {

    @Autowired
    private FuncionarioService service;

    @GetMapping
    public ResponseEntity<List<FuncionarioResponseDTO>> listar(){
        return ResponseEntity.status(HttpStatus.OK).body(service.listar());
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> salvar(@RequestBody @Valid FuncionarioRequestDTO salvarDTO){
        service.salvar(salvarDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("Mensagem","Cadastro Realizado com Sucesso. ✅"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> atualizar(@PathVariable Long id, @RequestBody @Valid FuncionarioRequestDTO salvarDTO){
        service.atualizar(id, salvarDTO);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("Mensagem","Cadastro Atualizado com Sucesso.✅"));
        }
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deletar(@PathVariable Long id){
        service.deletar(id);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("Mensagem","Cadastro deletado com Sucesso. ✅"));
    }

}
