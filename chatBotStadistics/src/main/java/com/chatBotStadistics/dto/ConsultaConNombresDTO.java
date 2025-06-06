package com.chatBotStadistics.dto;

/**
 * Represents a query or consultation that includes associated topic and subtopic names.
 *
 * This record encapsulates the data related to a specific consultation, including its
 * unique identifier, the name of the topic, the name of the subtopic, and the message
 * content associated with the consultation.
 */
public record ConsultaConNombresDTO(
        Long consultaId,
        String temaNombre,
        String subtemaNombre,
        String mensaje
) {
}
