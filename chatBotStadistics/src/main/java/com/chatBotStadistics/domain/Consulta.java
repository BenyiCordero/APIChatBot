package com.chatBotStadistics.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Consulta class represents a query or consultation record in the system.
 * It is mapped to the "consultas" table in the database and is a JPA entity.
 *
 * This class contains the following attributes:
 * - id: The unique identifier for the consulta, generated automatically.
 * - subtema_id: The identifier of the associated subtema.
 * - tema_id: The identifier of the associated tema.
 * - usuario_id: The identifier of the user performing the consulta.
 * - mensaje: The message or content of the consulta.
 * - day: The day of the consulta, represented as an integer.
 * - month: The month of the consulta, represented as an integer.
 * - year: The year of the consulta, represented as an integer.
 * - week: The week of the consulta, represented as an integer.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "consultas")
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Basic
    @Column(name = "subtema_id")
    private Integer subtema_id;
    @Column(name = "tema_id")
    private Integer tema_id;
    @Column(name = "usuario_id")
    private Integer usuario_id;
    @Column(name = "mensaje")
    private String mensaje;
    @Column(name = "day")
    private Integer day;
    @Column(name = "month")
    private Integer month;
    @Column(name = "year")
    private Integer year;
    @Column(name = "week")
    private Integer week;

}
