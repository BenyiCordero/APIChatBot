package com.chatBotStadistics.controllers;

import com.chatBotStadistics.service.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    @Autowired
    ConsultaService consultaService;

    @GetMapping("/por-subtema")
    public Map<String, Double> obtenerEstadisticasPorSubtema() {
        Map<String, Double> result = consultaService.getEstadisticasPorSubtema();
        System.out.println("Controller Result: " + result); // Debugging
        return result;
    }
    @GetMapping("/por-tema")
    public Map<String, Double> obtenerEstadisticasPorTema() {
        Map<String, Double> result = consultaService.getEstadisticasPorTema();
        System.out.println("Controller Result: " + result); // Debugging
        return result;
    }

    @GetMapping
    public Long obtenerTotalConsultas() {
        return consultaService.countConsultas();
    }

}
