package com.chatBotStadistics.service;

import org.springframework.stereotype.Service;
import org.springframework.data.repository.query.Param;

import java.util.Map;

/**
 * Service interface for managing statistical data and counts related to "consultas."
 *
 * This interface defines methods for obtaining statistics and counts categorized
 * by themes, subthemes, and temporal aspects such as year, month, and week.
 * It serves as the contract for implementing statistical functionality
 * in the application.
 */
@Service
public interface ConsultaService {

    Map<String, Double> getEstadisticasPorTema();
    Map<String, Double> getEstadisticasPorTema(Integer year, Integer month);
    Map<String, Double> getEstadisticasPorSubtema();
    Map<String, Double> getEstadisticasPorSubtema(Integer year, Integer month);
    Long countConsultas();
    Long countConsultas(Integer year, Integer month);
    Long countConsultasPorYearMonthWeek(Integer year, Integer month, Integer week);

    //New Services
    Map<String, Double> getEstadisticasPorTema(@Param("year") Integer year, @Param("month") Integer month, @Param("week") Integer week);
    Map<String, Double> getEstadisticasPorSubtema(@Param("year") Integer year, @Param("month") Integer month, @Param("week") Integer week);
}
