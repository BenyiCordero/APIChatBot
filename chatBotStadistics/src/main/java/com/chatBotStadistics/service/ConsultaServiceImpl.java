package com.chatBotStadistics.service;

import com.chatBotStadistics.repository.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service implementation for managing and retrieving statistics related to consultations.
 * This class integrates with the underlying repository to fetch data and perform calculations
 * for generating statistical insights about consultations, both at the theme and subtheme levels.
 * It provides functionality for aggregating and analyzing consultation data with optional filters
 * such as year, month, and week.
 *
 * Responsibilities:
 * - Fetch and calculate statistics for consultations grouped by themes or subthemes.
 * - Retrieve total consultation counts with or without date filters.
 * - Calculate percentage distributions of consultations among themes and subthemes.
 *
 * Key Methods:
 * - getEstadisticasPorSubtema(): Retrieves the percentage distribution of consultations by subtheme.
 * - getEstadisticasPorSubtema(Integer year, Integer month): Calculates subtheme consultation percentages for a specific time frame.
 * - getEstadisticasPorTema(): Retrieves the percentage distribution of consultations by theme.
 * - getEstadisticasPorTema(Integer year, Integer month): Calculates theme consultation percentages for a specific time frame.
 * - countConsultas(): Returns the total number of consultations recorded in the system.
 * - countConsultas(Integer year, Integer month): Counts consultations filtered by year and month.
 * - countConsultasPorYearMonthWeek(Integer year, Integer month, Integer week): Counts unique user consultations filtered by year, month, and week.
 *
 * This implementation relies on the `ConsultaRepository` to perform database operations
 * and obtain raw data required for analysis.
 *
 * Dependency Injection:
 * - The class uses the ConsultaRepository for repository operations, injected using the `@Autowired` annotation.
 *
 * Annotations:
 * - `@Service`: Marks this class as a Spring service to be managed by Spring's container and available for dependency injection.
 */
@Service
public class ConsultaServiceImpl implements ConsultaService {

    private final ConsultaRepository consultaRepository;

    public ConsultaServiceImpl(ConsultaRepository consultaRepository) {
        this.consultaRepository = consultaRepository;
    }

    //New Services.
    @Override
    public Map<String, Double> getEstadisticasPorTema(Integer year, Integer month, Integer week) {
        List<Object[]> resultados = consultaRepository.countConsultasByCategoria(year, month, week);

        if (resultados.isEmpty()) {
            return Collections.emptyMap();
        }

        long totalConsultas = resultados.stream()
                .mapToLong(row -> ((Number) row[1]).longValue())
                .sum();

        Map<String, Double> estadisticas = new HashMap<>();

        for (Object[] resultado : resultados) {
            String nombreCategoria = (String) resultado[0];
            Long count = ((Number) resultado[1]).longValue();

            double porcentaje = (double) count / totalConsultas * 100;
            estadisticas.put(nombreCategoria, porcentaje);
        }

        return estadisticas;
    }

    @Override
    public Map<String, Double> getEstadisticasPorSubtema(Integer year, Integer month, Integer week) {
        List<Object[]> resultados = consultaRepository.countConsultasBySubtema(year, month, week);

        if (resultados.isEmpty()) {
            return Collections.emptyMap();
        }

        long totalConsultas = resultados.stream()
                .mapToLong(row -> ((Number) row[1]).longValue())
                .sum();

        if (totalConsultas == 0) {
            return Collections.emptyMap();
        }

        Map<String, Double> estadisticas = new HashMap<>();
        for (Object[] resultado : resultados) {
            String nombreSubtema = (String) resultado[0]; // [0] es el nombre
            long cantidad = ((Number) resultado[1]).longValue(); // [1] es el count
            double porcentaje = (double) cantidad / totalConsultas * 100;
            estadisticas.put(nombreSubtema, porcentaje);
        }
        return estadisticas;
    }

    @Override
    public Long getConsultas(Integer year, Integer month, Integer week) {
        Long count = consultaRepository.countConsultas(year, month, week);
        return count != null ? count : 0L;
    }

    @Override
    public Long getUsuarios(Integer year, Integer month, Integer week) {
        Long count = consultaRepository.countUsuarios(year, month, week);
        return count != null ? count : 0L;
    }
}