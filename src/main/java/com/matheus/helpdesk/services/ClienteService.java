package com.matheus.helpdesk.services;

import com.matheus.helpdesk.domain.Cliente;
import com.matheus.helpdesk.domain.Pessoa;
import com.matheus.helpdesk.dtos.ClienteDTO;
import com.matheus.helpdesk.repositories.ClienteRepository;
import com.matheus.helpdesk.repositories.PessoaRepository;
import com.matheus.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.matheus.helpdesk.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    ClienteRepository ClienteRepository;

    @Autowired
    PessoaRepository pessoaRepository;

    public Cliente findById(Integer id) {
        Optional<Cliente> obj = ClienteRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id));
    }

    public List<Cliente> findAll() {
        return ClienteRepository.findAll();
    }

    public Cliente create(ClienteDTO objDto) {
        objDto.setId(null);
        validaPorCpfEmail(objDto);
        Cliente newObj = new Cliente(objDto);
        return ClienteRepository.save(newObj);
    }

    private void validaPorCpfEmail(ClienteDTO objDto) {
        Optional<Pessoa> obj = pessoaRepository.findByCpf(objDto.getCpf());
        if (obj.isPresent() && obj.get().getId() != objDto.getId()) {
            throw new DataIntegrityViolationException("CPF já cadastrado no sistema!");
        }
        obj = pessoaRepository.findByEmail(objDto.getEmail());
        if (obj.isPresent() && obj.get().getId() != objDto.getId()) {
            throw new DataIntegrityViolationException("Email já cadastrado no sistema!");
        }

    }

    public Cliente update(Integer id, ClienteDTO objDto) {
        objDto.setId(id);
        Cliente oldObj = findById(id);
        validaPorCpfEmail(objDto);
        oldObj = new Cliente(objDto);
        return ClienteRepository.save(oldObj);
    }

    public void delete(Integer id) {
        Cliente obj = findById(id);
        if (obj.getChamados().size() > 0) {
            throw new DataIntegrityViolationException("Cliente possui ordens de serviço e nao pode ser deletado!");
        }
        ClienteRepository.deleteById(id);
    }
}
