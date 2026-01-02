package com.gestor.sistemalicencias.repository;

import com.gestor.sistemalicencias.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaRepository extends JpaRepository<Persona, Long> {

}
