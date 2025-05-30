package com.chatBotStadistics.repository;

import com.chatBotStadistics.domain.Consulta;
import com.chatBotStadistics.dto.SubtemaEstadisticaDTO;
import com.chatBotStadistics.dto.TemaEstadisticaDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Integer> {

     // Contar consultas por subtema (id y nombre)
    @Query("SELECT s.id, s.nombre, COUNT(c) " +
            "FROM Consulta c, Subtema s " +
            "WHERE c.subtema_id = s.id " +
            "GROUP BY s.id, s.nombre")
    List<Object[]> countConsultasBySubtema();

    // Contar consultas por tema (id y nombre)
    @Query("SELECT t.id, t.nombre, COUNT(c) " +
            "FROM Consulta c, Tema t " +
            "WHERE c.tema_id = t.id " +
            "GROUP BY t.id, t.nombre")
    List<Object[]> countConsultasByTema();

    // Contar total de consultas
    @Query("SELECT COUNT(c) FROM Consulta c")
    Long countConsultas();

    @Query("SELECT new com.chatBotStadistics.dto.SubtemaEstadisticaDTO(s.nombre, COUNT(c)) " +
            "FROM Consulta c, Subtema s " +
            "WHERE s.id = c.subtema_id " +
            "AND (:year IS NULL OR c.year = :year) " +
            "AND (:month IS NULL OR c.month = :month) " +
            "GROUP BY s.nombre")
    List<SubtemaEstadisticaDTO> countConsultasBySubtemaDTO(@Param("year") Integer year,
                                                           @Param("month") Integer month);

    @Query("SELECT new com.chatBotStadistics.dto.TemaEstadisticaDTO(t.nombre, COUNT(c)) " +
            "FROM Consulta c, Tema t " +
            "WHERE t.id = c.tema_id " +
            "AND (:year IS NULL OR c.year = :year) " +
            "AND (:month IS NULL OR c.month = :month) " +
            "GROUP BY t.nombre")
    List<TemaEstadisticaDTO> countConsultasByTemaDTO(@Param("year") Integer year,
                                                     @Param("month") Integer month);

    @Query("SELECT COUNT(c) " +
            "FROM Consulta c " +
            "WHERE year IS NULL OR c.year = :year " +
            "AND (:month IS NULL OR c.month = :month)")
    Long countConsultasF(@Param("year") Integer year,
                         @Param("month") Integer month);

}
