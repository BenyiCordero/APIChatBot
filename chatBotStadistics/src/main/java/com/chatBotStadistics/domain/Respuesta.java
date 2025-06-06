package com.chatBotStadistics.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
    private Integer id;
    @Column(name = "consulta_id", nullable = false, columnDefinition = "BIGINT")
    private Long consulta_id;
    private String mensaje;
}
