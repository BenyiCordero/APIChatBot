package com.chatBotStadistics.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Token class represents a token entity in the system.
 * It is mapped to the "tokens" table in the database and serves as a JPA entity.
 *
 * This class includes the following attributes:
 * - id: The unique identifier for the token, generated automatically.
 * - token: A unique string representing the token's value.
 * - tokenType: The type of token, represented as an enumeration (default is BEARER).
 * - isRevoked: A boolean indicating whether the token has been revoked.
 * - isExpired: A boolean indicating whether the token has expired.
 * - adminUser: A Many-to-One relationship associating the token with an AdminUser entity.
 *
 * Tokens are primarily used to manage authentication and authorization within the system.
 * Each token is uniquely associated with an AdminUser and can have a specific state
 * (e.g., revoked or expired) to manage its validity and usage.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tokens")
public final class Token {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(unique = true)
    private String token;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private TokenType tokenType = TokenType.BEARER;

    @Column(nullable = false)
    private Boolean isRevoked;

    @Column(nullable = false)
    private Boolean isExpired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_user_id")
    private AdminUser adminUser;

    public enum TokenType {
        BEARER
    }

}
