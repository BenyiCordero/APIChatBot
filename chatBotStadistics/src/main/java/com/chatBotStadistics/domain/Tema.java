package com.chatBotStadistics.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Tema class represents a theme or topic entity in the system.
 * It is mapped to the "temas" table in the database and is a JPA entity.
 *
 * This class contains the following attributes:
 * - id: The unique identifier for the Tema.
 * - nombre: The name of the Tema.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Tema {

    @Id
    private Integer id;
    private String nombre;

}
