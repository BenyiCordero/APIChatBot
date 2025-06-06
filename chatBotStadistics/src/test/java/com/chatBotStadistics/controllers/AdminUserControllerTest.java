package com.chatBotStadistics.controllers;

import com.chatBotStadistics.dto.AdminUserResponse;
import com.chatBotStadistics.repository.AdminUserRepository;
import com.chatBotStadistics.domain.AdminUser;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit test class for the AdminUserController.
 *
 * This class is responsible for testing the behavior of the AdminUserController endpoints.
 * It is annotated with {@code @WebMvcTest} to enable testing of Spring MVC controllers in isolation.
 * The test cases focus on verifying the functionality of the endpoints defined in AdminUserController
 * using the {@code MockMvc} object.
 *
 * Key annotated components include:
 * - {@code @MockBean AdminUserRepository}: Mocking the AdminUserRepository to isolate the tests from database interactions.
 * - {@code @Autowired MockMvc}: Injecting a MockMvc instance to simulate web requests and responses.
 *
 * Test cases:
 * 1. {@code testChangePasswordReturnsEmptyListWhenNoUsersExist}:
 *    Tests that the endpoint returns an empty JSON array when no users are found in the repository.
 *
 * 2. {@code testChangePasswordReturnsListOfUsers}:
 *    Tests that the endpoint correctly returns a JSON array containing a list of users with their name and email
 *    fields when users exist in the repository.
 *
 * Assertions involve status codes, response structure, and values using MockMvc methods like
 * {@code perform}, {@code andExpect}, and {@code jsonPath}.
 */
@WebMvcTest(AdminUserController.class)
class AdminUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminUserRepository adminUserRepository;

    @Test
    void testChangePasswordReturnsEmptyListWhenNoUsersExist() throws Exception {
        // Mock repository behavior
        when(adminUserRepository.findAll()).thenReturn(List.of());

        // Perform GET request
        mockMvc.perform(get("/usuarios")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void testChangePasswordReturnsListOfUsers() throws Exception {
        // Mock data
        AdminUser user1 = new AdminUser();
        user1.setName("John Doe");
        user1.setEmail("john.doe@example.com");

        AdminUser user2 = new AdminUser();
        user2.setName("Jane Smith");
        user2.setEmail("jane.smith@example.com");

        // Mock repository behavior
        when(adminUserRepository.findAll()).thenReturn(List.of(user1, user2));

        // Perform GET request
        mockMvc.perform(get("/usuarios")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[0].email").value("john.doe@example.com"))
                .andExpect(jsonPath("$[1].name").value("Jane Smith"))
                .andExpect(jsonPath("$[1].email").value("jane.smith@example.com"));
    }
}