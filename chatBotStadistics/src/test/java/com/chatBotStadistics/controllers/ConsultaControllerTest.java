package com.chatBotStadistics.controllers;

import com.chatBotStadistics.domain.Prompt;
import com.chatBotStadistics.dto.PromptRequestDTO;
import com.chatBotStadistics.service.ConsultaService;
import com.chatBotStadistics.service.PromptService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;

import java.util.Map;

/**
 * Test class for testing {@link ConsultaController}.
 *
 * This class focuses on verifying the functionality and correctness of the endpoints
 * defined in {@link ConsultaController}. The tests ensure that the REST APIs behave
 * as expected under various conditions, including valid and invalid inputs.
 *
 * Test cases include:
 * - Validation and response verification for creating prompts with different request attributes.
 * - Fetching prompts by their IDs to validate correct behavior for existing and non-existing IDs.
 * - Fetching statistical data by topic and subtopic, including cases where data exists or does not exist.
 *
 * Utilizes MockMvc for simulating HTTP requests and responses, and Mockito for mocking service dependencies.
 *
 * Dependencies:
 * - {@code PromptService} and {@code ConsultaService}: These are mocked using {@code @SpyBean}
 *   to simulate backend behavior without requiring actual implementations.
 *
 * Scenarios tested:
 * - Validation errors for missing, null, blank, or excessively long prompt content.
 * - Successful creation of prompts when valid data is provided.
 * - Correct retrieval of prompts or statistical data.
 * - Handling of non-existing resources and returning appropriate HTTP status codes.
 */
@WebMvcTest(ConsultaController.class)
class ConsultaControllerTest {

    @Test
    void createPrompt_ShouldReturnBadRequest_WhenContentIsNull() throws Exception {
        mockMvc.perform(post("/consultas/prompt/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"content\":null}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]", is("Content cannot be blank")));
    }

    @Test
    void createPrompt_ShouldReturnBadRequest_WhenContentIsTooLong() throws Exception {
        String longContent = "a".repeat(10001); // Assuming the system rejects content longer than 10,000 characters.

        mockMvc.perform(post("/consultas/prompt/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"content\":\"" + longContent + "\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]", is("Content exceeds the maximum length allowed")));
    }

    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    private PromptService promptService;

    @SpyBean
    private ConsultaService consultaService;

    @Test
    void createPrompt_ShouldReturnCreatedPrompt_WhenValidRequest() throws Exception {
        PromptRequestDTO promptRequestDTO = new PromptRequestDTO("Test prompt content");
        Prompt createdPrompt = new Prompt(1, "Test prompt content");

        Mockito.when(promptService.createPrompt(Mockito.any(PromptRequestDTO.class))).thenReturn(createdPrompt);

        mockMvc.perform(post("/consultas/prompt/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"content\": \"Test prompt content\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.content", is("Test prompt content")));
    }

    @Test
    void createPrompt_ShouldReturnBadRequest_WhenContentIsBlank() throws Exception {
        mockMvc.perform(post("/consultas/prompt/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"content\": \"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]", is("Content cannot be blank")));
    }

    @Test
    void createPrompt_ShouldReturnBadRequest_WhenContentIsMissing() throws Exception {
        mockMvc.perform(post("/consultas/prompt/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]", is("Content cannot be blank")));
    }

    @Test
    void getPrompt_ShouldReturnPrompt_WhenValidId() throws Exception {
        Prompt prompt = new Prompt(1, "Sample prompt content");

        Mockito.when(promptService.getPrompt(1)).thenReturn(java.util.Optional.of(prompt));

        mockMvc.perform(get("/consultas/prompt/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.content", is("Sample prompt content")));
    }

    @Test
    void getPrompt_ShouldReturnNotFound_WhenInvalidId() throws Exception {
        Mockito.when(promptService.getPrompt(99)).thenReturn(java.util.Optional.empty());

        mockMvc.perform(get("/consultas/prompt/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void obtenerEstadisticasPorTema_ShouldReturnValidData_WhenDataExists() throws Exception {
        Map<String, Double> mockStatistics = Map.of("Topic1", 5.0, "Topic2", 10.0);

        Mockito.when(consultaService.getEstadisticasPorTema()).thenReturn(mockStatistics);

        mockMvc.perform(get("/consultas/por-tema"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Topic1", is(5.0)))
                .andExpect(jsonPath("$.Topic2", is(10.0)));
    }

    @Test
    void obtenerEstadisticasPorTema_ShouldReturnEmptyMap_WhenNoData() throws Exception {
        Mockito.when(consultaService.getEstadisticasPorTema()).thenReturn(Map.of());

        mockMvc.perform(get("/consultas/por-tema"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(0)));
    }

    @Test
    void obtenerEstadisticasPorSubtema_ShouldReturnValidData_WhenDataExists() throws Exception {
        Map<String, Double> mockStatistics = Map.of("Subtopic1", 15.0, "Subtopic2", 20.0);

        Mockito.when(consultaService.getEstadisticasPorSubtema()).thenReturn(mockStatistics);

        mockMvc.perform(get("/consultas/por-subtema"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Subtopic1", is(15.0)))
                .andExpect(jsonPath("$.Subtopic2", is(20.0)));
    }

    @Test
    void obtenerEstadisticasPorSubtema_ShouldReturnEmptyMap_WhenNoData() throws Exception {
        Mockito.when(consultaService.getEstadisticasPorSubtema()).thenReturn(Map.of());

        mockMvc.perform(get("/consultas/por-subtema"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(0)));
    }
}