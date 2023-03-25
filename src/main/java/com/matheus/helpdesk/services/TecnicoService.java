package com.matheus.helpdesk.services;

import com.matheus.helpdesk.domain.Pessoa;
import com.matheus.helpdesk.domain.Tecnico;
import com.matheus.helpdesk.dtos.TecnicoDTO;
import com.matheus.helpdesk.repositories.PessoaRepository;
import com.matheus.helpdesk.repositories.TecnicoRepository;
import com.matheus.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.matheus.helpdesk.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TecnicoService {

    @Autowired
    TecnicoRepository tecnicoRepository;

    @Autowired
    PessoaRepository pessoaRepository;

    public Tecnico findById(Integer id) {
        Optional<Tecnico> obj = tecnicoRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id));
    }

    public List<Tecnico> findAll() {
        return tecnicoRepository.findAll();
    }

    public Tecnico create(TecnicoDTO objDto) {
        objDto.setId(null);
        validaPorCpfEmail(objDto);
        Tecnico newObj = new Tecnico(objDto);
        return tecnicoRepository.save(newObj);
    }

    private void validaPorCpfEmail(TecnicoDTO objDto) {
        Optional<Pessoa> obj = pessoaRepository.findByCpf(objDto.getCpf());
        if (obj.isPresent() && obj.get().getId() != objDto.getId()) {
            throw new DataIntegrityViolationException("CPF já cadastrado no sistema!");
        }
        obj = pessoaRepository.findByEmail(objDto.getEmail());
        if (obj.isPresent() && obj.get().getId() != objDto.getId()) {
            throw new DataIntegrityViolationException("Email já cadastrado no sistema!");
        }

    }

    public Tecnico update(Integer id, TecnicoDTO objDto) {
        objDto.setId(id);
        Tecnico oldObj = findById(id);
        validaPorCpfEmail(objDto);
        oldObj = new Tecnico(objDto);
        return tecnicoRepository.save(oldObj);
    }

    public void delete(Integer id) {
        Tecnico obj = findById(id);
        if (obj.getChamados().size() > 0) {
            throw new DataIntegrityViolationException("Tecnico possui ordens de serviço e nao pode ser deletado!");
        }
        tecnicoRepository.deleteById(id);
    }
}
