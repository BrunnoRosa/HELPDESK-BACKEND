package com.example.help_desk.controller;

import com.example.help_desk.dto.chamado.ChamadoRequestDTO;
import com.example.help_desk.dto.chamado.ChamadoResponseDTO;
import com.example.help_desk.service.ChamadoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("chamados")
public class ChamadoController {

    @Autowired
    private ChamadoService service;

    @GetMapping
    public ResponseEntity<List<ChamadoResponseDTO>> listar(){
        return ResponseEntity.status(HttpStatus.OK).body(service.listar());
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> salvar(@Valid @RequestBody ChamadoRequestDTO salvarDTO){
        service.salvar(salvarDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("Mensagem" , "Chamado salvo com sucesso"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> atualizar(@PathVariable Long id, @RequestBody @Valid ChamadoRequestDTO atualizarDTO){
        service.atualizar(id, atualizarDTO);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("Mensagem", "Chamado atualizado com sucesso"));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deletar(@PathVariable Long id){
        service.deletar(id);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("Mensagem", "Chamado deletado"));
    }

}
