package com.chatBotStadistics.repository;

import com.chatBotStadistics.domain.AdminUser;
import com.chatBotStadistics.domain.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * TokenRepository is a repository interface for managing Token entities.
 * It extends JpaRepository to provide standard CRUD operations and additional
 * custom queries specific to Token management.
 *
 * Key functionality includes:
 * - Fetching all valid tokens associated with a specific AdminUser.
 * - Finding a token entity by its unique token value.
 *
 * Methods:
 * - `findAllValidTokenByUser(Integer id)`: Retrieves a list of tokens associated with
 *   a specific AdminUser, filtered to include only tokens that are not expired or revoked.
 * - `findByToken(String token)`: Finds and returns an optional Token entity based on its unique value.
 *
 * The repository facilitates integration with Spring's persistence framework and
 * supports dependency injection for transactional operations.
 */
public interface TokenRepository extends JpaRepository<Token, Integer> {

    @Query("""
      select t from Token t
      where t.adminUser.id = :id and (t.isExpired = false or t.isRevoked = false)
      """)
    List<Token> findAllValidTokenByUser(Integer id);

    Optional<Token> findByToken(String token);

    List<Token> findByAdminUser(AdminUser adminUser);
}
