package com.matheus.helpdesk.services;

import com.matheus.helpdesk.domain.Tecnico;
import com.matheus.helpdesk.dtos.TecnicoDTO;
import com.matheus.helpdesk.repositories.TecnicoRepository;
import com.matheus.helpdesk.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TecnicoService {

    @Autowired
    TecnicoRepository tecnicoRepository;

    public Tecnico findById(Integer id){
        Optional<Tecnico> obj = tecnicoRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: "+ id));
    }

    public List<Tecnico> findAll() {
        return tecnicoRepository.findAll();
    }

    public Tecnico create(TecnicoDTO objDto) {
        objDto.setId(null);
        Tecnico newObj = new Tecnico(objDto);
        return tecnicoRepository.save(newObj);
    }
}
