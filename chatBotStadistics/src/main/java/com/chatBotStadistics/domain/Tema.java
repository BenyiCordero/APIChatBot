package com.chatBotStadistics.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "temas")
public class Tema {

    @Id
    private Integer id;
    private String nombre;

}
