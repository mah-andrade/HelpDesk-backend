package com.matheus.helpdesk.resources;

import com.matheus.helpdesk.domain.Chamado;
import com.matheus.helpdesk.dtos.ChamadoDTO;
import com.matheus.helpdesk.services.ChamadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/Chamados")
public class ChamadoResource {

    @Autowired
    private ChamadoService service;

    @GetMapping(value ="/{Id}")
    public ResponseEntity<ChamadoDTO> findById(@PathVariable Integer Id){
        Chamado obj = service.findById(Id);
        return ResponseEntity.ok().body(new ChamadoDTO(obj));
    }

    @GetMapping
    public ResponseEntity<List<ChamadoDTO>> findAll(){
        List<Chamado> chamados = service.findAll();
        List<ChamadoDTO> listDTO = chamados.stream().map(ChamadoDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }


}
