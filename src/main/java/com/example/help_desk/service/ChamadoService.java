package com.example.help_desk.service;

import com.example.help_desk.dto.chamado.ChamadoRequestDTO;
import com.example.help_desk.dto.chamado.ChamadoResponseDTO;
import com.example.help_desk.model.ChamadoModel;
import com.example.help_desk.repository.ChamadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChamadoService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private ChamadoRepository chamadoRepository;

    // Construtor definido no ChamadoResponseDTO para trazer mais simplicidade ao Service
    public List<ChamadoResponseDTO> listar(){
        return chamadoRepository.findAll().stream().map(ChamadoResponseDTO::new).toList();
    }

    public ChamadoModel salvar(ChamadoRequestDTO salvarDTO){

        ChamadoModel novoChamado = new ChamadoModel();
        novoChamado.setTituloChamado(salvarDTO.getTituloChamado());
        novoChamado.setOcorrenciaChamado(salvarDTO.getOcorrenciaChamado());
        novoChamado.setDescricaoChamado(salvarDTO.getDescricaoChamado());
        novoChamado.setPrioridadeChamado(salvarDTO.getPrioridadeChamado());

        return chamadoRepository.save(novoChamado);
    }

    public ChamadoModel atualizar(Long id, ChamadoRequestDTO atualizarDTO){
        ChamadoModel novoRegistro = chamadoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Chamado não localizado ❌"));

        novoRegistro.setTituloChamado(atualizarDTO.getTituloChamado());
        novoRegistro.setOcorrenciaChamado(atualizarDTO.getOcorrenciaChamado());
        novoRegistro.setDescricaoChamado(atualizarDTO.getDescricaoChamado());
        novoRegistro.setPrioridadeChamado(atualizarDTO.getPrioridadeChamado());

        return chamadoRepository.save(novoRegistro);
    }

    public void deletar(Long id){
        if(!chamadoRepository.existsById(id)){
            throw new RuntimeException("Chamado não localizado. ❌");
        }
        chamadoRepository.deleteById(id);
    }

}
