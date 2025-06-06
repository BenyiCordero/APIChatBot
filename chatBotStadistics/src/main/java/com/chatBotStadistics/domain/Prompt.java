package com.chatBotStadistics.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Prompt class represents a prompt entity in the system.
 *
 * It is mapped to the "prompts" table in the database and is a JPA entity.
 * This class contains the following attributes:
 * - id: The unique identifier for the prompt, generated automatically.
 * - content: The textual content of the prompt, stored as a long text and must not be null.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "prompts")
public class Prompt {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "prompt", nullable = false, columnDefinition = "LONGTEXT")
    private String content;

}
