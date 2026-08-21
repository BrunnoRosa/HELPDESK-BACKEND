package com.example.help_desk.service;

import com.example.help_desk.dto.ClienteRequestDTO;
import com.example.help_desk.dto.ClienteResponseDTO;
import com.example.help_desk.model.ClienteModel;
import com.example.help_desk.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public List<ClienteResponseDTO> listarTodos(){
        return repository
                .findAll()
                .stream()
                .map(ClienteModel -> new ClienteResponseDTO(ClienteModel.getNome(),
                        ClienteModel.getEmail(),ClienteModel.getSetor())).toList();
    }

    public ClienteModel salvarCliente (ClienteRequestDTO requestDTO){
        if (repository.findByEmail(requestDTO.getEmail()).isPresent()){
            throw new RuntimeException("Cliente já cadastrado. ❌");
        }

        ClienteModel novoCliente = new ClienteModel();
        novoCliente.setNome(requestDTO.getNome());
        novoCliente.setEmail(requestDTO.getEmail());
        novoCliente.setSetor(requestDTO.getSetor());

        return  repository.save(novoCliente);
    }

    public ClienteModel atualizarCliente (Long id, ClienteRequestDTO requestDTO){
        ClienteModel novoCliente = repository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Cadastro de cliente não localizado. 🔎❌"));

        repository.findByEmail(requestDTO.getEmail()).ifPresent(clienteModel -> {
            if (!clienteModel.getId().equals(id)) {
                throw new RuntimeException("Cliente já cadastrado.❌");
            }
        });
        novoCliente.setNome(requestDTO.getNome());
        novoCliente.setEmail(requestDTO.getEmail());
        novoCliente.setEmail(requestDTO.getEmail());

        return  repository.save(novoCliente);
    }

    public void deletarCliente(Long id){
        if (!repository.existsById(id)) {
            throw new RuntimeException("Cadastro de cliente não localizado.🔎❌");
        }
        repository.deleteById(id);
    }
}
