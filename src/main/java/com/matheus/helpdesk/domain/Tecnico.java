package com.matheus.helpdesk.domain;

import com.matheus.helpdesk.domain.enums.Perfil;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Tecnico extends Pessoa {

    @Serial
    private static final long serialVersionUID = 1L;

    @OneToMany(mappedBy = "tecnico")
    private List<Chamado> chamados = new ArrayList<>();

    public Tecnico() {
        super();
        addPerfil(Perfil.CLIENTE);
    }

    public Tecnico(Integer id, String nome, String email, String cpf, String senha) {
        super(id, nome, email, cpf, senha);
        addPerfil(Perfil.CLIENTE);
    }

    public List<Chamado> getChamados() {
        return chamados;
    }

    public void setChamados(List<Chamado> chamados) {
        this.chamados = chamados;
    }

}
