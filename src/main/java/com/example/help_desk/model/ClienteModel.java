package com.example.help_desk.model;


import com.example.help_desk.model.enums.Setor;
import jakarta.persistence.*;

@Entity
@Table(name = "tab_clientes")
public class ClienteModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String email;

    private Setor setor;

    public ClienteModel() {

    }

    public ClienteModel(Long id, String nome, String email, Setor setor) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.setor = setor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Setor getSetor() {
        return setor;
    }

    public void setSetor(Setor setor) {
        this.setor = setor;
    }
}
