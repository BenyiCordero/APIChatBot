package com.chatBotStadistics.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * The AdminUser class represents an admin user in the system.
 * It is mapped to the "admin_user" table in the database and is a JPA entity.
 *
 * This class contains the following attributes:
 * - id: The unique identifier for the admin user, generated automatically.
 * - name: The name of the admin user, must not be null.
 * - email: The unique email address of the admin user, must not be null.
 * - password: The password of the admin user, must not be null.
 * - tokens: A list of Token entities associated with the admin user, establishing a one-to-many relationship.
 *
 * An AdminUser is associated with its tokens through the "adminUser" field in the Token entity.
 * This association allows tracking the admin user's issued tokens and their status.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AdminUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "adminUser")
    private List<Token> tokens;

}
