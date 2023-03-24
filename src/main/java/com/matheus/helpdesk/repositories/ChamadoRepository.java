package com.matheus.helpdesk.repositories;

import com.matheus.helpdesk.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChamadoRepository extends JpaRepository<Cliente, Integer> {

}