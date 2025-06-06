package com.chatBotStadistics.dto;

/**
 * Represents a data transfer object for statistical subtopic information.
 *
 * This record encapsulates the data related to a specific subtopic,
 * including its name and the count of related occurrences or events.
 * It is commonly used in statistics reporting or analysis contexts.
 */
public record SubtemaEstadisticaDTO(
        String nombreSubtema,
        Long count
) {
}
