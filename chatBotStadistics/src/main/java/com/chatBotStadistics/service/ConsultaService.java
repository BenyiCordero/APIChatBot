package com.chatBotStadistics.service;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface ConsultaService {

    Map<String, Double> getEstadisticasPorSubtema();
    Map<String, Double> getEstadisticasPorTema();
    Long countConsultas();


}
