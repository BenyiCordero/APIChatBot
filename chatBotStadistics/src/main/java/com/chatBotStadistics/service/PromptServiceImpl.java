package com.chatBotStadistics.service;

import com.chatBotStadistics.domain.Prompt;
import com.chatBotStadistics.dto.PromptRequestDTO;
import com.chatBotStadistics.repository.PromptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of the PromptService interface for managing Prompt entities.
 *
 * This class provides methods to:
 * - Retrieve a prompt by its unique identifier.
 * - Update the content of an existing prompt using the provided data.
 * - Create a new prompt with the given content.
 *
 * This service interacts with the PromptRepository to handle data persistence
 * and retrieval operations. It ensures that input data is validated and updates
 * are only applied when relevant changes are provided.
 */
@Service
public class PromptServiceImpl implements PromptService {

    private final PromptRepository promptRepository;

    public PromptServiceImpl(PromptRepository promptRepository) {
        this.promptRepository = promptRepository;
    }

    @Override
    public Optional<Prompt> getPrompt(Integer id) {
        return promptRepository.findById(id);
    }

    @Override
    public Optional<Prompt> updatePrompt(Integer id, PromptRequestDTO promptRequestDTO) {
        return promptRepository.findById(id)
                .map(existingPrompt -> {
                    // Update only if content is provided in DTO
                    if (promptRequestDTO.content() != null && !promptRequestDTO.content().isBlank()) {
                        existingPrompt.setContent(promptRequestDTO.content());
                    }
                    return promptRepository.save(existingPrompt);
                });
    }

    @Override
    public Prompt createPrompt(PromptRequestDTO promptRequestDTO) { // Sin 'id' en la firma
        Prompt prompt = new Prompt();
        prompt.setContent(promptRequestDTO.content());
        return promptRepository.save(prompt);
    }
}
