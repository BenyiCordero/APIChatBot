package com.chatBotStadistics.service;

import com.chatBotStadistics.repository.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ConsultaServiceImpl implements ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Override
    public Map<String, Double> getEstadisticasPorSubtema() {
        List<Object[]> resultados = consultaRepository.countConsultasBySubtema();

        // Verificar tipos
        System.out.println("Resultados: " + resultados);

        if (resultados.isEmpty()) {
            return Collections.emptyMap();
        }

        long totalConsultas = resultados.stream()
                .mapToLong(row -> ((Number) row[1]).longValue()) // Convertir de forma segura
                .sum();

        Map<String, Double> estadisticas = new HashMap<>();

        for (Object[] resultado : resultados) {
            // Manejar tipos genéricos
            Long subtemaId = ((Number) resultado[0]).longValue(); // Convertir a Long de forma segura
            Long count = ((Number) resultado[1]).longValue();

            double porcentaje = (double) count / totalConsultas * 100;
            estadisticas.put("Subtema " + subtemaId, porcentaje);
        }

        return estadisticas;
    }

    @Override
    public Map<String, Double> getEstadisticasPorTema() {
        List<Object[]> resultados = consultaRepository.countConsultasByTema();

        // Verificar tipos
        System.out.println("Resultados: " + resultados);

        if (resultados.isEmpty()) {
            return Collections.emptyMap();
        }

        long totalConsultas = resultados.stream()
                .mapToLong(row -> ((Number) row[1]).longValue()) // Convertir de forma segura
                .sum();

        Map<String, Double> estadisticas = new HashMap<>();

        for (Object[] resultado : resultados) {
            // Manejar tipos genéricos
            Long subtemaId = ((Number) resultado[0]).longValue(); // Convertir a Long de forma segura
            Long count = ((Number) resultado[1]).longValue();

            double porcentaje = (double) count / totalConsultas * 100;
            estadisticas.put("Tema " + subtemaId, porcentaje);
        }

        return estadisticas;
    }

    @Override
    public Long countConsultas() {
        return consultaRepository.countConsultas();
    }
}