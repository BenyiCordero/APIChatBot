package com.chatBotStadistics.repository;

import com.chatBotStadistics.domain.Prompt;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * PromptRepository is a repository interface for managing Prompt entities.
 * This interface provides methods for performing CRUD operations on the Prompt entity
 * by extending JpaRepository from Spring Data JPA.
 *
 * Key functionality includes:
 * - Default CRUD operations (save, find, delete, etc.) provided by JpaRepository.
 *
 * PromptRepository operates on the Prompt entity, with the primary key type as Integer.
 * It serves as a bridge between the application and the database, enabling seamless
 * interaction with the "prompts" table.
 */
public interface PromptRepository extends JpaRepository<Prompt, Integer> {
}
