package com.example.help_desk.controller;

import com.example.help_desk.dto.ClienteRequestDTO;
import com.example.help_desk.dto.ClienteResponseDTO;
import com.example.help_desk.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/clientes")
//@CrossOrigin(origins = "http://localhost:5173")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listarClientes(){
        return ResponseEntity.status(HttpStatus.OK).body(service.listarTodos());

    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> salvarCliente(@Valid @RequestBody ClienteRequestDTO requestDTO){
        service.salvarCliente(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("Mensagem", "Cliente cadastrado com sucesso. ✅"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> atualizarCliente(@PathVariable Long id, @Valid @RequestBody ClienteRequestDTO requestDTO){
        service.atualizarCliente(id, requestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("Mensagem", "Cadastro atualizado com sucesso. ✅"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deletarCliente(@PathVariable Long id){
        service.deletarCliente(id);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("Mensagem", "Cadastro de cliente deletado com sucesso. ✅"));
    }
}
