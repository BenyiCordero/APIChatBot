package com.chatBotStadistics.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Subtema class represents a sub-theme or sub-topic entity in the system.
 * It is mapped to the "subtemas" table in the database and serves as a JPA entity.
 *
 * This class has the following attributes:
 * - id: The unique identifier for the Subtema.
 * - nombre: The name of the Subtema.
 * - tema: A Many-to-One relationship to the Tema entity, associating the Subtema with its parent Tema.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Subtema {

    @Id
    private Integer id;
    private String nombre;
    @ManyToOne
    @JoinColumn(name = "tema_id")  // si quieres relacionar subtema con tema
    private Tema tema;
}
