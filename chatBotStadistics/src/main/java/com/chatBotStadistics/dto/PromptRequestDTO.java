package com.chatBotStadistics.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Represents a data transfer object for handling prompt-related requests.
 *
 * This record encapsulates the input data required to process a prompt operation,
 * specifically the content associated with the prompt. It includes validation
 * constraints to ensure the integrity of the input data.
 */
public record PromptRequestDTO(
        @NotBlank(message = "Content cannot be blank") // Ensures content is not null or empty
        String content){
}
