package com.gestor.sistemalicencias.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class contolador {

    @GetMapping("/hola")
    public String decirHola(){
        return "Mi nombre es Edwin Chusin y estoy probando Spring Boot";
    }
}
