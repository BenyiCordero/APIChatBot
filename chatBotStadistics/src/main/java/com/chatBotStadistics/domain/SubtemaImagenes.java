package com.chatBotStadistics.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "subtema_imagenes")
public class SubtemaImagenes {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "url_image", nullable = false)
    private String urlImage;
    @JoinColumn(name = "subtema_id", referencedColumnName = "id")
    @OneToOne
    private Subtema subtema;
}
