package com.chatBotStadistics.service;

import com.chatBotStadistics.dto.SubtemaEstadisticaDTO;
import com.chatBotStadistics.dto.TemaEstadisticaDTO;
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

        if (resultados.isEmpty()) {
            return Collections.emptyMap();
        }

        long totalConsultas = resultados.stream()
                .mapToLong(row -> ((Number) row[2]).longValue())
                .sum();

        Map<String, Double> estadisticas = new HashMap<>();

        for (Object[] resultado : resultados) {
            String nombreSubtema = (String) resultado[1];
            Long count = ((Number) resultado[2]).longValue();

            double porcentaje = (double) count / totalConsultas * 100;
            estadisticas.put(nombreSubtema, porcentaje);
        }

        return estadisticas;
    }

    @Override
    public Map<String, Double> getEstadisticasPorTema() {
        List<Object[]> resultados = consultaRepository.countConsultasByTema();

        if (resultados.isEmpty()) {
            return Collections.emptyMap();
        }

        long totalConsultas = resultados.stream()
                .mapToLong(row -> ((Number) row[2]).longValue())
                .sum();

        Map<String, Double> estadisticas = new HashMap<>();

        for (Object[] resultado : resultados) {
            String nombreTema = (String) resultado[1];
            Long count = ((Number) resultado[2]).longValue();

            double porcentaje = (double) count / totalConsultas * 100;
            estadisticas.put(nombreTema, porcentaje);
        }

        return estadisticas;
    }

    @Override
    public Long countConsultas() {
        return consultaRepository.countConsultas();
    }

    @Override
    public Long countConsultas(Integer year, Integer month) {
        return consultaRepository.countConsultasF(year, month);
    }

    @Override
    public Map<String, Double> getEstadisticasPorSubtema(Integer year, Integer month) {
        List<SubtemaEstadisticaDTO> resultados = consultaRepository.countConsultasBySubtemaDTO(year, month);

        if (resultados.isEmpty()) {
            return Collections.emptyMap();
        }

        long totalConsultas = resultados.stream()
                .mapToLong(SubtemaEstadisticaDTO::count)
                .sum();

        if (totalConsultas == 0) {
            return Collections.emptyMap();
        }

        Map<String, Double> estadisticas = new HashMap<>();
        for (SubtemaEstadisticaDTO resultado : resultados) {
            double porcentaje = (double) resultado.count() / totalConsultas * 100;
            estadisticas.put(resultado.nombreSubtema(), porcentaje);
        }
        return estadisticas;
    }

    @Override
    public Map<String, Double> getEstadisticasPorTema(Integer year, Integer month) {
        List<TemaEstadisticaDTO> resultados = consultaRepository.countConsultasByTemaDTO(year,month);

        if (resultados.isEmpty()) {
            return Collections.emptyMap();
        }

        long totalConsultas = resultados.stream()
                .mapToLong(TemaEstadisticaDTO::count)
                .sum();

        if (totalConsultas == 0) {
            return Collections.emptyMap();
        }

        Map<String, Double> estadisticas = new HashMap<>();
        for (TemaEstadisticaDTO resultado : resultados) {
            double porcentaje = (double) resultado.count() / totalConsultas * 100;
            estadisticas.put(resultado.nombreTema(), porcentaje);
        }
        return estadisticas;
    }
}