package com.chatBotStadistics.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Respuesta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne
    @JoinColumn(name = "consulta_id", referencedColumnName = "id")
    private Consulta consulta;
    @Column(name = "mensaje", columnDefinition = "TEXT")
    private String mensaje;
}
