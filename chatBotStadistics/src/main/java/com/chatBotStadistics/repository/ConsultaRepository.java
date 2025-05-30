package com.chatBotStadistics.repository;

import com.chatBotStadistics.domain.Consulta;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

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


}
