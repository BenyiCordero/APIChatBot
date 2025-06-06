package com.chatBotStadistics.service;

import com.chatBotStadistics.domain.Prompt;
import com.chatBotStadistics.dto.PromptRequestDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service interface for managing operations related to Prompt entities.
 *
 * This interface defines methods for creating, retrieving, and updating prompts.
 * It is designed to handle the business logic for prompts by interacting with
 * the repository layer or other services. Implementations of this interface
 * ensure data validation and proper manipulation of Prompt entities.
 *
 * Methods:
 * - getPrompt: Retrieves a prompt by its unique identifier.
 * - updatePrompt: Updates the content of an existing prompt identified by its ID.
 * - createPrompt: Creates a new prompt with the provided content data.
 */
@Service
public interface PromptService {

    Optional<Prompt> getPrompt(Integer id);
    Optional<Prompt> updatePrompt(Integer id, PromptRequestDTO promptRequestDTO);
    Prompt createPrompt(PromptRequestDTO promptRequestDTO);

}
