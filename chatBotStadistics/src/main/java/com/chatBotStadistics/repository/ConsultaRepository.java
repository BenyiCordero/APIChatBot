package com.chatBotStadistics.repository;

import com.chatBotStadistics.domain.Consulta;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    @Query("SELECT c.subtema_id AS subtemaId, COUNT(c) AS count " +
            "FROM Consulta c GROUP BY c.subtema_id")
    List<Object[]> countConsultasBySubtema();

    @Query("SELECT c.tema_id AS temaId, COUNT(c) AS count " +
            "FROM Consulta c GROUP BY c.tema_id")
    List<Object[]> countConsultasByTema();

    @Query("SELECT COUNT(c) FROM Consulta c")
    Long countConsultas();

}
