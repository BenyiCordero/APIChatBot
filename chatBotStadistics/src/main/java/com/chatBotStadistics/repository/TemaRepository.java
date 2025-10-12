package com.chatBotStadistics.repository;

import com.chatBotStadistics.domain.Tema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemaRepository extends JpaRepository<Tema, Integer> {
}
