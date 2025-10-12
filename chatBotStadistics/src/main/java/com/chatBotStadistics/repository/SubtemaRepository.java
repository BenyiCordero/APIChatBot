package com.chatBotStadistics.repository;

import com.chatBotStadistics.domain.Subtema;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubtemaRepository extends JpaRepository<Subtema, Integer> {
    Subtema getSubtemaById(Integer id);
}
