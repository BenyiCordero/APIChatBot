package com.chatBotStadistics.service;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface ConsultaService {

    Map<String, Double> getEstadisticasPorTema();
    Map<String, Double> getEstadisticasPorTema(Integer year, Integer month);
    Map<String, Double> getEstadisticasPorSubtema();
    Map<String, Double> getEstadisticasPorSubtema(Integer year, Integer month);
    Long countConsultas();
    Long countConsultas(Integer year, Integer month);


}
