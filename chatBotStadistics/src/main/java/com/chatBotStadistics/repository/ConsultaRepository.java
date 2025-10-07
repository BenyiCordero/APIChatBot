package com.chatBotStadistics.repository;

import com.chatBotStadistics.domain.Consulta;
import com.chatBotStadistics.dto.SubtemaEstadisticaDTO;
import com.chatBotStadistics.dto.TemaEstadisticaDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ConsultaRepository is a repository interface for managing Consulta entities.
 * It extends JpaRepository to provide standard CRUD operations and defines
 * custom queries for statistical analysis related to consultations, subtopics,
 * and topics.
 *
 * Key features:
 * - Fetching the number of consultations grouped by subtopics and topics.
 * - Aggregating consultation counts with filtering options by year, month, and week.
 * - Counting unique users based on their consultation activities.
 * - Fetching data transfer objects (DTO) for detailed statistical reporting.
 *
 * Methods:
 * - countConsultasBySubtema: Retrieves the count of consultations grouped by subtheme.
 * - countConsultasByTema: Retrieves the count of consultations grouped by theme.
 * - countConsultas: Counts the total consultations recorded in the system.
 * - countConsultasBySubtemaDTO: Fetches counts grouped by subtheme with optional time filters.
 * - countConsultasByTemaDTO: Fetches counts grouped by theme with optional time filters.
 * - countConsultasF: Counts consultations filtered by year and/or month.
 * - countUsuariosConsultasF: Counts the number of unique users who made consultations,
 *   filtered by year, month, and/or week.
 *
 * This repository is annotated as a Spring Repository to facilitate the integration
 * with Spring's persistence framework and dependency injection.
 */
@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Integer> {

    @Query("SELECT c.tema.nombre, COUNT(c) " +
            "FROM Consulta c " +
            "WHERE c.tema IS NOT NULL " +
            "  AND (:year IS NULL OR c.year = :year) " +
            "  AND (:month IS NULL OR c.month = :month) " +
            "  AND (:week IS NULL OR c.week = :week) " +
            "GROUP BY c.tema.nombre")
    List<Object[]> countConsultasByCategoria(@Param("year") Integer year,
                                             @Param("month") Integer month,
                                             @Param("week") Integer week);

    /*
     * Aquí unimos Subtema s con Consulta c por la relación lógica:
     *   s.tema = c.tema
     * Esto funciona cuando Subtema tiene: @ManyToOne Tema tema;
     * y Consulta tiene: @ManyToOne Tema tema;
     */
    @Query("SELECT s.nombre, COUNT(c) " +
            "FROM Consulta c, Subtema s " +
            "WHERE c.tema IS NOT NULL " +
            "  AND s.tema = c.tema " +
            "  AND (:year IS NULL OR c.year = :year) " +
            "  AND (:month IS NULL OR c.month = :month) " +
            "  AND (:week IS NULL OR c.week = :week) " +
            "GROUP BY s.nombre")
    List<Object[]> countConsultasBySubtema(@Param("year") Integer year,
                                           @Param("month") Integer month,
                                           @Param("week") Integer week);

    @Query("SELECT COUNT(c) " +
            "FROM Consulta c " +
            "WHERE (:year IS NULL OR c.year = :year) " +
            "  AND (:month IS NULL OR c.month = :month) " +
            "  AND (:week IS NULL OR c.week = :week)")
    Long countConsultas(@Param("year") Integer year,
                        @Param("month") Integer month,
                        @Param("week") Integer week);

    @Query("SELECT COUNT(DISTINCT c.usuario.id) " +
            "FROM Consulta c " +
            "WHERE (:year IS NULL OR c.year = :year) " +
            "  AND (:month IS NULL OR c.month = :month) " +
            "  AND (:week IS NULL OR c.week = :week)")
    Long countUsuarios(@Param("year") Integer year,
                       @Param("month") Integer month,
                       @Param("week") Integer week);
}