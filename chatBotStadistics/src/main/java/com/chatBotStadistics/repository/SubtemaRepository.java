package com.chatBotStadistics.repository;

import com.chatBotStadistics.domain.Subtema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubtemaRepository extends JpaRepository<Subtema, Integer> {
}
