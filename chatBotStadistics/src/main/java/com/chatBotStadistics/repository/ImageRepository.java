package com.chatBotStadistics.repository;

import com.chatBotStadistics.domain.Consulta;
import com.chatBotStadistics.domain.Subtema;
import com.chatBotStadistics.domain.SubtemaImagenes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<SubtemaImagenes, Integer> {
    @Query("SELECT s FROM SubtemaImagenes s WHERE s.subtema.id = :id_subtema")
    SubtemaImagenes getImageBySubtema(@Param("id_subtema") Integer id_subtema);
}
