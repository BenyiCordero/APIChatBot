package com.chatBotStadistics.repository;

import com.chatBotStadistics.domain.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * AdminUserRepository is a repository interface for managing AdminUser entities.
 * This interface provides methods for performing CRUD operations on the AdminUser entity,
 * as well as additional query methods.
 *
 * It extends JpaRepository, leveraging Spring Data JPA to facilitate database interaction.
 *
 * Key functionality includes:
 * - Default CRUD operations (save, find, delete, etc.) provided by JpaRepository.
 * - A custom query method for finding an admin user by their email address.
 *
 * Methods:
 * - `findByEmail(String email)`: Finds and returns an optional AdminUser by their unique email address.
 *
 * AdminUserRepository is annotated as a Spring Repository, allowing its integration
 * as a bean in Spring's Application Context.
 */
@Repository
public interface AdminUserRepository extends JpaRepository<AdminUser, Long> {

    //Optional<AdminUser> findByEmail(String email);

}
