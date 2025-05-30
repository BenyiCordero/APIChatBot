package com.chatBotStadistics.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "subtemas")
public class Subtema {

    @Id
    private Long id;

    private String nombre;

    @ManyToOne
    @JoinColumn(name = "tema_id")  // si quieres relacionar subtema con tema
    private Tema tema;


}
