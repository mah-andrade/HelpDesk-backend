package com.matheus.helpdesk.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.matheus.helpdesk.domain.enums.Perfil;
import com.matheus.helpdesk.dtos.ClienteDTO;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.io.Serial;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Cliente extends Pessoa {

    @Serial
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    @OneToMany(mappedBy = "cliente")
    private List<Chamado> chamados = new ArrayList<>();

    public Cliente() {
        super();
        addPerfil(Perfil.CLIENTE);
    }

    public Cliente(Integer id, String nome, String cpf, String email, String senha) {
        super(id, nome, email, cpf, senha);
        addPerfil(Perfil.CLIENTE);
    }

    public Cliente(ClienteDTO obj) {
        this.id = obj.getId();
        this.nome = obj.getNome();
        this.cpf = obj.getCpf();
        this.email = obj.getEmail();
        this.senha = obj.getSenha();
        this.perfis = obj.getPerfis().stream().map(x -> x.getCodigo()).collect(Collectors.toSet());
        this.dataCriacao = obj.getDataCriacao();
    }

    public List<Chamado> getChamados() {
        return chamados;
    }

    public void setChamados(List<Chamado> chamados) {
        this.chamados = chamados;
    }

}
