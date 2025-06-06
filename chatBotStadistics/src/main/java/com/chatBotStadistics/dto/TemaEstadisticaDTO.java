package com.chatBotStadistics.dto;

/**
 * Represents a data transfer object for statistical topic information.
 *
 * This record encapsulates the data related to a specific topic,
 * including its name and the count of associated occurrences or events.
 * It is commonly used in contexts of statistical reporting or analysis.
 */
public record TemaEstadisticaDTO(
        String nombreTema,
        Long count
) {
}
