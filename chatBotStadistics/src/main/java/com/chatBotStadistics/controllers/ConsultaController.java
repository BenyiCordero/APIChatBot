package com.chatBotStadistics.controllers;

import com.chatBotStadistics.service.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping ("/filtrado")
    public Long obtenerTotalConsultas(
            @RequestParam(required = true) Integer year,
            @RequestParam(required = true) Integer month
    ) {
        return consultaService.countConsultas(year, month);
    }
    @GetMapping ("/por-year-month-week/filtrado")
    public Long obtenerEstadisticasPorYearMonthWeek(
            @RequestParam(required = true) Integer year,
            @RequestParam(required = true) Integer month,
            @RequestParam(required = true) Integer week) {
        return consultaService.countConsultasPorYearMonthWeek(year, month, week);
    }

    @GetMapping("/por-subtema/filtrado")
    public Map<String, Double> obtenerEstadisticasPorSubtema(
            @RequestParam(required = true) Integer year,
            @RequestParam(required = true) Integer month) {
        Map<String, Double> result = consultaService.getEstadisticasPorSubtema(year, month);
        System.out.println("Controller Result: " + result); // Debugging
        return result;
    }

    @GetMapping("/por-tema/filtrado")
    public Map<String, Double> obtenerEstadisticasPorTema(
            @RequestParam(required = true) Integer year,
            @RequestParam(required = true) Integer month) {
        Map<String, Double> result = consultaService.getEstadisticasPorTema(year, month);
        System.out.println("Controller Result: " + result); // Debugging
        return result;
    }

}
