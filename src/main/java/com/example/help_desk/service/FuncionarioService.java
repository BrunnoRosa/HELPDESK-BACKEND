package com.example.help_desk.service;

import com.example.help_desk.dto.FuncionarioResponseDTO;
import com.example.help_desk.dto.FuncionarioRequestDTO;
import com.example.help_desk.model.FuncionarioModel;
import com.example.help_desk.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuncionarioService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private FuncionarioRepository repository;

    // Construtor definido no FuncionarioResponseDTO para trazer mais simplicidade ao Service
    public List<FuncionarioResponseDTO> listar(){
        return repository.findAll().stream().map(FuncionarioResponseDTO::new)
                .toList();
    }
//Antiga forma de listar os gets vindo do FuncionarioResponseDTO
//    public List<FuncionarioResponseDTO> listar(){
//        return repository.findAll().stream().map(funcionario -> new FuncionarioResponseDTO(
//                        funcionario.getNome(),
//                        funcionario.getEmail(),
//                        funcionario.getFuncao(),
//                        funcionario.getPerfil()))
//                .toList();
//    }

    public FuncionarioModel salvar(FuncionarioRequestDTO salvarDTO){
        if(repository.findByEmail(salvarDTO.getEmail()).isPresent()){
            throw new RuntimeException("Funcionário já Cadastrado ❌");
        }
        FuncionarioModel novoCadastro = new FuncionarioModel();
        novoCadastro.setNome(salvarDTO.getNome());
        novoCadastro.setEmail(salvarDTO.getEmail());
        novoCadastro.setFuncao(salvarDTO.getFuncao());
        novoCadastro.setPerfil(salvarDTO.getPerfil());

        return repository.save(novoCadastro);
    }

    public FuncionarioModel atualizar(Long id, FuncionarioRequestDTO atualizarDTO){
        FuncionarioModel novoCadastro = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Cadastro não localizado ❌"));

        // Verifica se o e-mail já está em uso por OUTRO funcionário
        repository.findByEmail(atualizarDTO.getEmail()).ifPresent(funcionario -> {
            if (!funcionario.getId().equals(id)) {
                throw new RuntimeException("Funcionário já Cadastrado. ❌");
            }
        });
        novoCadastro.setNome(atualizarDTO.getNome());
        novoCadastro.setEmail(atualizarDTO.getEmail());
        novoCadastro.setFuncao(atualizarDTO.getFuncao());
        novoCadastro.setPerfil(atualizarDTO.getPerfil());

        return repository.save(novoCadastro);
    }
    public void deletar(Long id){
        if(!repository.existsById(id)){
            throw new RuntimeException("Cadastro não Localizado. ❌");
        }
        repository.deleteById(id);
    }

}
