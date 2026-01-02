package com.gestor.sistemalicencias.controller;

import com.gestor.sistemalicencias.model.Persona;
import com.gestor.sistemalicencias.repository.PersonaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/")
public class PersonaController {
    private final PersonaRepository personaRepository;

    public PersonaController(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    @PostMapping
    public Persona crear(){
        Persona p = new Persona();
        p.setNombre("Edwin Chusin");
        p.setEmail("edwin.chusin@epn.edu.ec");
        return personaRepository.save(p);
    }

    @GetMapping
    public List<Persona> listar(){
        return personaRepository.findAll();
    }
}
